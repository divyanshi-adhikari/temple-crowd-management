package com.temple.crowdmanagement.features.queue.data

import com.temple.crowdmanagement.core.model.QueueState
import com.temple.crowdmanagement.core.model.TempleSite
import com.temple.crowdmanagement.core.network.NetworkClient
import com.temple.crowdmanagement.core.network.RemoteQueue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class QueueEngine(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {

    private val _liveGates = MutableStateFlow<List<RemoteQueue>>(emptyList())
    val liveGates: StateFlow<List<RemoteQueue>> = _liveGates.asStateFlow()

    private val _activeQueue = MutableStateFlow<QueueState?>(
        QueueState(
            activeToken = "TK-" + Random.nextInt(1000, 9999),
            temple = TempleSite.SOMNATH,
            currentPosition = 14,
            estimatedWaitMinutes = 20,
            isNotifyEnabled = true
        )
    )
    val activeQueue: StateFlow<QueueState?> = _activeQueue.asStateFlow()

    init {
        refreshLiveGates(TempleSite.SOMNATH)
    }

    fun refreshLiveGates(temple: TempleSite) {
        scope.launch(Dispatchers.IO) {
            val gates = NetworkClient.getQueues(temple.backendId)
            _liveGates.value = gates
        }
    }

    fun joinQueue(temple: TempleSite): QueueState {
        val newToken = "TK-" + Random.nextInt(1000, 9999)
        val initialPos = Random.nextInt(12, 35)
        
        // Use live gate wait time if available
        val matchedGate = _liveGates.value.firstOrNull { it.status == "OPEN" }
        val waitTime = matchedGate?.waitingTimeMin ?: (initialPos * 1.3).toInt()

        val newState = QueueState(
            activeToken = newToken,
            temple = temple,
            currentPosition = initialPos,
            estimatedWaitMinutes = waitTime
        )
        _activeQueue.value = newState

        // Sync with ops backend pilgrim registry
        scope.launch(Dispatchers.IO) {
            NetworkClient.registerPilgrim(
                name = "Pilgrim Devotee",
                templeId = temple.backendId,
                phone = "+91 98765 ${Random.nextInt(10000, 99999)}"
            )
        }

        return newState
    }

    fun toggleNotification(enabled: Boolean) {
        _activeQueue.value = _activeQueue.value?.copy(isNotifyEnabled = enabled)
    }

    fun leaveQueue() {
        _activeQueue.value = null
    }

    fun simulateQueueProgress() {
        _activeQueue.value?.let { current ->
            if (current.currentPosition > 1) {
                val nextPos = current.currentPosition - 1
                val nextWait = (nextPos * 1.3).toInt()
                _activeQueue.value = current.copy(
                    currentPosition = nextPos,
                    estimatedWaitMinutes = nextWait
                )
            }
        }
    }
}
