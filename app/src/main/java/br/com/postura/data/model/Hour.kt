package br.com.postura.data.model

data class Hour (
        val hour_id: String,
        val wake_up_hour: String,
        val wake_up_minute: String,
        val sleep_hour: String,
        val sleep_minute: String
)

data class  HourCalculed(
        val generatedHour: String,
        val generatedMinute: String
)

data class  AllHourCalculed(
        val allHourCalculed: ArrayList<HourCalculed>
)