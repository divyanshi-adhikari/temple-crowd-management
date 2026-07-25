package com.temple.crowdmanagement.features.queue.data

import com.temple.crowdmanagement.core.model.QueueState
import com.temple.crowdmanagement.core.model.TempleSite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class QueueEngine {

    private val _activeQueue = MutableStateFlow<QueueState?>(
        QueueState(
            activeToken = "TK-" + Random.nextInt(100, 999),
            temple = TempleSite.SOMNATH,
            currentPosition = 14,
            estimatedWaitMinutes = 18,
            isNotifyEnabled = true
        )
    )
    val activeQueue: StateFlow<QueueState?> = _activeQueue.asStateFlow()

    fun joinQueue(temple: TempleSite): QueueState {
        val newToken = "TK-" + Random.nextInt(100, 999)
        val initialPos = Random.nextInt(12, 35)
        val waitTime = (initialPos * 1.3).toInt()

        val newState = QueueState(
            activeToken = newToken,
            temple = temple,
            currentPosition = initialPos,
            estimatedWaitMinutes = waitTime
        )
        _activeQueue.value = newState
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
