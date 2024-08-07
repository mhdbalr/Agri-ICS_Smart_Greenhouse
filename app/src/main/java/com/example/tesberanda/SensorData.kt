package com.example.tesberanda

import com.google.gson.annotations.SerializedName
data class SensorData(
        @SerializedName("timestamp") val timestamp: String,
        @SerializedName("id") val id: Int,
        @SerializedName("ph") val ph: Double,
        @SerializedName("tds") val tds: Int,
        @SerializedName("suhu_air") val suhuAir: Double,
        @SerializedName("winddirection") val windDirection: Double,
        @SerializedName("kecepatan_angin") val kecepatanAngin: Double,
        @SerializedName("berat1") val berat1: Int,
        @SerializedName("waterflow1") val waterFlow1: Int,
        @SerializedName("waterflow2") val waterFlow2: Int,
        @SerializedName("waterflow3") val waterFlow3: Int,
        @SerializedName("waterflow4") val waterFlow4: Int,
        @SerializedName("soilmoisture1") val soilMoisture1: Double,
        @SerializedName("soilmoisture2") val soilMoisture2: Double,
        @SerializedName("soilmoisture3") val soilMoisture3: Double,
        @SerializedName("soilmoisture4") val soilMoisture4: Double,
        @SerializedName("berat2") val berat2: Int,
        @SerializedName("berat3") val berat3: Int,
        @SerializedName("berat4") val berat4: Int,
        @SerializedName("suhu") val suhu: Double,
        @SerializedName("tekanan_udara") val tekananUdara: Double,
        @SerializedName("pompanutrisi") val pompaNutrisi: Int,
        @SerializedName("pompaair") val pompaAir: Int,
        @SerializedName("humidity") val humidity: Double,
        @SerializedName("svp") val svp: Double,
        @SerializedName("avp") val avp: Double,
        @SerializedName("vpd") val vpd: Double,
        @SerializedName("temperaturedht") val temperatureDht: Double
)
