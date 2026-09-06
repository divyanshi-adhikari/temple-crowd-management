package com.temple.crowdmanagement.core.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

data class RemoteAarti(
    val name: String,
    val time: String,
    val status: String
)

data class RemoteTemple(
    val id: String,
    val name: String,
    val fullName: String,
    val city: String,
    val activeFestival: String,
    val dailyCapacity: Int,
    val currentOccupancy: Int,
    val emergencyContact: String,
    val policeIncharge: String,
    val sdrfTeam: String,
    val aartis: List<RemoteAarti>
)

data class RemoteQueue(
    val id: String,
    val templeId: String,
    val name: String,
    val category: String,
    val currentPilgrims: Int,
    val maxCapacity: Int,
    val waitingTimeMin: Int,
    val risk: String,
    val status: String,
    val aiRecommendations: List<String>
)

data class RemoteVolunteer(
    val id: String,
    val templeId: String,
    val role: String,
    val teamName: String,
    val status: String,
    val zone: String,
    val battery: Int,
    val radioStatus: String,
    val phone: String
)

object NetworkClient {
    // Priority order: adb reverse localhost -> local Wi-Fi LAN IP -> Android emulator
    // The YatraFlow Bridge Server must be running: cd bridge-server && node server.js
    private val CANDIDATE_BASE_URLS = listOf(
        "http://localhost:8000",
        "http://10.0.2.2:8000",
        "http://172.25.185.155:8000"
    )

    @Volatile
    private var activeBaseUrl: String? = null

    private suspend fun resolveBaseUrl(): String = withContext(Dispatchers.IO) {
        activeBaseUrl?.let { return@withContext it }

        for (candidate in CANDIDATE_BASE_URLS) {
            try {
                val url = URL("$candidate/api/health")
                val connection = (url.openConnection() as HttpURLConnection).apply {
                    connectTimeout = 800
                    readTimeout = 800
                    requestMethod = "GET"
                }
                if (connection.responseCode == 200) {
                    activeBaseUrl = candidate
                    return@withContext candidate
                }
            } catch (_: Exception) {
                // Try next
            }
        }
        val defaultUrl = CANDIDATE_BASE_URLS[0]
        activeBaseUrl = defaultUrl
        defaultUrl
    }

    private suspend fun httpGet(endpoint: String): String? = withContext(Dispatchers.IO) {
        val baseUrl = resolveBaseUrl()
        try {
            val url = URL("$baseUrl$endpoint")
            val conn = (url.openConnection() as HttpURLConnection).apply {
                connectTimeout = 3000
                readTimeout = 4000
                requestMethod = "GET"
                setRequestProperty("Accept", "application/json")
            }
            if (conn.responseCode == 200) {
                BufferedReader(InputStreamReader(conn.inputStream)).use { it.readText() }
            } else {
                null
            }
        } catch (_: Exception) {
            // Reset activeBaseUrl on failure so next call can re-probe
            activeBaseUrl = null
            null
        }
    }

    private suspend fun httpPost(endpoint: String, payload: JSONObject): String? = withContext(Dispatchers.IO) {
        val baseUrl = resolveBaseUrl()
        try {
            val url = URL("$baseUrl$endpoint")
            val conn = (url.openConnection() as HttpURLConnection).apply {
                connectTimeout = 3000
                readTimeout = 4000
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Accept", "application/json")
                doOutput = true
            }
            OutputStreamWriter(conn.outputStream).use { it.write(payload.toString()) }
            if (conn.responseCode in 200..299) {
                BufferedReader(InputStreamReader(conn.inputStream)).use { it.readText() }
            } else {
                null
            }
        } catch (_: Exception) {
            activeBaseUrl = null
            null
        }
    }

    suspend fun getTemples(): List<RemoteTemple> {
        val response = httpGet("/api/temples") ?: return emptyList()
        val result = mutableListOf<RemoteTemple>()
        try {
            val json = JSONObject(response)
            val data = json.optJSONArray("data") ?: JSONArray()
            for (i in 0 until data.length()) {
                val t = data.getJSONObject(i)
                val aartisList = mutableListOf<RemoteAarti>()
                val aartisJson = t.optJSONArray("aartis") ?: JSONArray()
                for (j in 0 until aartisJson.length()) {
                    val a = aartisJson.getJSONObject(j)
                    aartisList.add(
                        RemoteAarti(
                            name = a.optString("name"),
                            time = a.optString("time"),
                            status = a.optString("status")
                        )
                    )
                }
                result.add(
                    RemoteTemple(
                        id = t.optString("id"),
                        name = t.optString("name"),
                        fullName = t.optString("full_name"),
                        city = t.optString("city"),
                        activeFestival = t.optString("active_festival"),
                        dailyCapacity = t.optInt("daily_capacity", 75000),
                        currentOccupancy = t.optInt("current_occupancy", 25000),
                        emergencyContact = t.optString("emergency_contact", "+91 2876 231200 / 112"),
                        policeIncharge = t.optString("police_incharge", "Police Control Room"),
                        sdrfTeam = t.optString("sdrf_team", "SDRF Unit"),
                        aartis = aartisList
                    )
                )
            }
        } catch (_: Exception) {}
        return result
    }

    suspend fun getQueues(templeId: String? = null): List<RemoteQueue> {
        val endpoint = if (!templeId.isNullOrEmpty()) "/api/queues?templeId=$templeId" else "/api/queues"
        val response = httpGet(endpoint) ?: return emptyList()
        val result = mutableListOf<RemoteQueue>()
        try {
            val json = JSONObject(response)
            val data = json.optJSONArray("data") ?: JSONArray()
            for (i in 0 until data.length()) {
                val q = data.getJSONObject(i)
                val aiRecs = mutableListOf<String>()
                val recsJson = q.optJSONArray("aiRecommendations") ?: JSONArray()
                for (j in 0 until recsJson.length()) {
                    aiRecs.add(recsJson.getString(j))
                }
                result.add(
                    RemoteQueue(
                        id = q.optString("id"),
                        templeId = q.optString("temple_id"),
                        name = q.optString("name"),
                        category = q.optString("category", "General"),
                        currentPilgrims = q.optInt("current_pilgrims", 0),
                        maxCapacity = q.optInt("max_capacity", 1000),
                        waitingTimeMin = q.optInt("waiting_time_min", 15),
                        risk = q.optString("risk", "Normal"),
                        status = q.optString("status", "OPEN"),
                        aiRecommendations = aiRecs
                    )
                )
            }
        } catch (_: Exception) {}
        return result
    }

    suspend fun postQueueAction(queueId: String, action: String): Boolean {
        val payload = JSONObject().apply {
            put("action", action)
        }
        val response = httpPost("/api/queues/$queueId/action", payload)
        return response != null
    }

    suspend fun createIncident(
        templeId: String,
        title: String,
        description: String,
        severity: String = "CRITICAL",
        location: String = "Campus Perimeter"
    ): Boolean {
        val payload = JSONObject().apply {
            put("temple_id", templeId)
            put("title", title)
            put("description", description)
            put("severity", severity)
            put("location", location)
            put("reported_by", "Pilgrim Mobile App SOS")
        }
        val response = httpPost("/api/incidents", payload)
        return response != null
    }

    suspend fun getVolunteers(templeId: String? = null): List<RemoteVolunteer> {
        val endpoint = if (!templeId.isNullOrEmpty()) "/api/volunteers?templeId=$templeId" else "/api/volunteers"
        val response = httpGet(endpoint) ?: return emptyList()
        val result = mutableListOf<RemoteVolunteer>()
        try {
            val json = JSONObject(response)
            val data = json.optJSONArray("data") ?: JSONArray()
            for (i in 0 until data.length()) {
                val v = data.getJSONObject(i)
                result.add(
                    RemoteVolunteer(
                        id = v.optString("id"),
                        templeId = v.optString("temple_id"),
                        role = v.optString("role", "Volunteer"),
                        teamName = v.optString("team_name", "Support Team"),
                        status = v.optString("status", "On Duty"),
                        zone = v.optString("zone", "Zone 1"),
                        battery = v.optInt("battery", 90),
                        radioStatus = v.optString("radio_status", "Connected"),
                        phone = v.optString("phone", "+91 98765 00000")
                    )
                )
            }
        } catch (_: Exception) {}
        return result
    }

    suspend fun registerPilgrim(name: String, templeId: String, phone: String): String? {
        val payload = JSONObject().apply {
            put("name", name)
            put("templeId", templeId)
            put("phone", phone)
            put("entryGate", "Gate 1")
        }
        val response = httpPost("/api/pilgrims", payload) ?: return null
        return try {
            JSONObject(response).optString("token")
        } catch (_: Exception) {
            null
        }
    }

    /**
     * Sync a confirmed booking to the bridge server so the web admin
     * Booking Operations page shows it in real-time.
     */
    suspend fun postBooking(
        templeId: String,
        slotTime: String,
        devoteeCount: Int,
        name: String
    ): Boolean {
        val payload = JSONObject().apply {
            put("templeId", templeId)
            put("time", slotTime)
            put("devoteeCount", devoteeCount)
            put("name", name)
            put("source", "mobile")
        }
        val response = httpPost("/api/bookings", payload)
        return response != null
    }

    /**
     * Dispatch an SOS event directly to the bridge server's /api/incidents
     * endpoint (same as createIncident but explicit for SOS use).
     */
    suspend fun postSOS(
        templeId: String,
        alertType: String,
        location: String
    ): Boolean {
        val payload = JSONObject().apply {
            put("temple_id", templeId)
            put("templeId", templeId)
            put("title", "EMERGENCY SOS: $alertType")
            put("description", "Pilgrim activated mobile panic SOS at $location")
            put("severity", "CRITICAL")
            put("location", location)
            put("reported_by", "Pilgrim Mobile App (SOS Button)")
        }
        val response = httpPost("/api/incidents", payload)
        return response != null
    }
}
