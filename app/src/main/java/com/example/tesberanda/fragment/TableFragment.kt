package com.example.tesberanda.fragment

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import com.example.tesberanda.R
import com.example.tesberanda.SensorData
import com.example.tesberanda.SensorResponse
import com.example.tesberanda.api.ApiService
import com.example.tesberanda.databinding.FragmentTableBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class TableFragment : Fragment() {
    lateinit var binding: FragmentTableBinding
    private lateinit var gson: Gson
    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTableBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gson = Gson()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://c-greenproject.org:3333")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        fetchData()
    }

    private fun fetchData() {
        val call: Call<SensorResponse> = apiService.getData()
        call.enqueue(object : Callback<SensorResponse> {
            override fun onResponse(
                call: Call<SensorResponse>,
                response: Response<SensorResponse>
            ) {
                if (response.isSuccessful) {
                    val dataEntries = response.body()
                    dataEntries?.let { displayData(it.result) }
                }
            }
            override fun onFailure(call: Call<SensorResponse>, t: Throwable) {
                Log.d("TAG", t.message.toString());
            }
        })
    }

    private fun displayData(dataEntries: List<SensorData>) {
        binding.tableLayout.removeAllViews()

        // Add table headers
        val headerRow = TableRow(requireContext())
        val headers = arrayOf("Timestamp", "Nutrient Pump", "Cooling System", "pH", "TDS (mg/L)", "Water Temperature (°C)",
            "Air Pressure (hPa)", "Humidity (%RH)", "Room Temperature (°C)",
            "Temperature DHT (°C)", "Wind Direction", "Wind Speed (m/s)",
            "Soil Moisture 1 (%)", "Soil Moisture 2 (%)", "Soil Moisture 3 (%)", "Soil Moisture 4 (%)",
            "Water Flow 1 (L/H)", "Water Flow 2 (L/H)", "Water Flow 3 (L/H)", "Water Flow 4 (L/H)",
            "Weight 1 (g)", "Weight 2 (g)", "Weight 3 (g)", "Weight 4 (g)", "AVP (kPa)", "SVP (kPa)", "VPD (kPa)")

        for (header in headers) {
            val textView = TextView(requireContext())
            textView.text = header
            textView.setPadding(9, 9, 9, 9)
            textView.setBackgroundColor(Color.BLUE)
            textView.setTextColor(Color.WHITE)
            textView.setBackgroundResource(R.drawable.table_header_cell_border)
            textView.gravity = Gravity.CENTER
            textView.setTypeface(null, Typeface.BOLD)
            headerRow.addView(textView)
        }

        binding.tableLayout.addView(headerRow)

        // Add data rows
        for ((index, entry) in dataEntries.withIndex()) {
            val row = TableRow(requireContext())

            val values = arrayOf(
                entry.timestamp, entry.pompaNutrisi.toString(), entry.pompaAir.toString(), entry.ph.toString(), entry.tds.toString(), entry.suhuAir.toString(),
                entry.tekananUdara.toString(), entry.humidity.toString(), entry.suhu.toString(), entry.temperatureDht.toString(),
                entry.windDirection.toString(), entry.kecepatanAngin.toString(), entry.soilMoisture1.toString(),
                entry.soilMoisture2.toString(), entry.soilMoisture3.toString(), entry.soilMoisture4.toString(),
                entry.waterFlow1.toString(), entry.waterFlow2.toString(), entry.waterFlow3.toString(),
                entry.waterFlow4.toString(), entry.berat1.toString(), entry.berat2.toString(), entry.berat3.toString(),
                entry.berat4.toString(), entry.avp.toString(), entry.svp.toString(), entry.vpd.toString(),
            )

            for (value in values) {
                val textView = TextView(requireContext())
                textView.text = value
                textView.setPadding(12, 12, 12, 12)
                textView.setBackgroundResource(R.drawable.table_body_cell_border)
                textView.gravity = Gravity.CENTER
                row.addView(textView)
            }
            // Set alternating row colors
            if (index % 2 == 0) {
                row.setBackgroundColor(Color.WHITE)
            } else {
                row.setBackgroundColor(Color.parseColor("#F0F0F0"))
            }

            binding.tableLayout.addView(row)
        }
    }

    private fun createTextView(text: String): TextView {
        val textView = TextView(requireContext())
        textView.text = text
        textView.setPadding(12, 12, 12, 12)
        return textView
    }
}

