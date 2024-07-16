package com.example.tesberanda.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tesberanda.SensorData
import com.example.tesberanda.SensorResponse
import com.example.tesberanda.api.ApiService
import com.example.tesberanda.databinding.FragmentGrafikBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class GrafikFragment : Fragment() {
    lateinit var binding: FragmentGrafikBinding
    private lateinit var gson: Gson
    private lateinit var apiService: ApiService
    private val listSensorData =ArrayList<SensorData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGrafikBinding.inflate(inflater, container, false)
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
                    dataEntries?.let {
                        listSensorData.clear()
                        listSensorData.addAll(it.result)
                        loadPh()
                        loadTds()
                        loadSuhuAir()
                        loadKecepatanAngin()
                        loadWinddirection()
                        loadBerat1()
                        loadBerat1()
                        loadBerat3()
                        loadBerat4()
                        loadWaterflow1()
                        loadWaterflow2()
                        loadWaterflow3()
                        loadWaterflow4()
                        loadSoilmoisture1()
                        loadSoilmoisture2()
                        loadSoilmoisture3()
                        loadSoilmoisture4()
                        loadSuhu()
                        loadTekananUdara()
                        loadPompaNutrisi()
                        loadHumidity()
                        loadSvp()
                        loadAvp()
                        loadVpd()
                        loadTemperaturedht()
                    }
                }
            }
            override fun onFailure(call: Call<SensorResponse>, t: Throwable) {
                Log.d("TAG", t.message.toString());
            }
        })
    }

    private fun loadPh() {
        binding.phChart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.ph.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "PH").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.phChart.data = lineData
        binding.phChart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.phChart.invalidate() // Refresh the chart
    }

    private fun loadTds() {
        binding.tdsChart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.tds.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Tds").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.tdsChart.data = lineData
        binding.tdsChart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.tdsChart.invalidate() // Refresh the chart
    }

    private fun loadSuhuAir() {
        binding.suhuAirChart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.suhuAir.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "SuhuAir").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.suhuAirChart.data = lineData
        binding.suhuAirChart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.suhuAirChart.invalidate() // Refresh the chart
    }

    private fun loadWinddirection() {
        binding.windDirectionChart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.windDirection.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Winddirection").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.windDirectionChart.data = lineData
        binding.windDirectionChart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.windDirectionChart.invalidate() // Refresh the chart
    }

    private fun loadKecepatanAngin() {
        binding.kecepatanAnginChart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.kecepatanAngin.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "KecepatanAngin").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.kecepatanAnginChart.data = lineData
        binding.kecepatanAnginChart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.kecepatanAnginChart.invalidate() // Refresh the chart
    }

    private fun loadBerat1() {
        binding.berat1Chart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.berat1.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Berat1").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.berat1Chart.data = lineData
        binding.berat1Chart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.berat1Chart.invalidate() // Refresh the chart
    }

    private fun loadBerat2() {
        binding.berat2Chart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.berat2.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Berat2").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.berat2Chart.data = lineData
        binding.berat2Chart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.berat2Chart.invalidate() // Refresh the chart
    }

    private fun loadBerat3() {
        binding.berat3Chart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.berat3.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Berat3").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.berat3Chart.data = lineData
        binding.berat3Chart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.berat3Chart.invalidate() // Refresh the chart
    }

    private fun loadBerat4() {
        binding.berat4Chart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.berat4.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Berat4").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.berat4Chart.data = lineData
        binding.berat4Chart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.berat4Chart.invalidate() // Refresh the chart
    }

    private fun loadWaterflow1() {
        binding.waterflow1Chart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.waterFlow1.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Waterflow1").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.waterflow1Chart.data = lineData
        binding.waterflow1Chart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.waterflow1Chart.invalidate() // Refresh the chart
    }

    private fun loadWaterflow2() {
        binding.waterflow2Chart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.waterFlow2.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Waterflow2").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.waterflow2Chart.data = lineData
        binding.waterflow2Chart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.waterflow2Chart.invalidate() // Refresh the chart
    }

    private fun loadWaterflow3() {
        binding.waterflow3Chart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.waterFlow3.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Waterflow3").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.waterflow3Chart.data = lineData
        binding.waterflow3Chart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.waterflow3Chart.invalidate() // Refresh the chart
    }

    private fun loadWaterflow4() {
        binding.waterflow4Chart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.waterFlow4.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Waterflow4").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.waterflow4Chart.data = lineData
        binding.waterflow4Chart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.waterflow4Chart.invalidate() // Refresh the chart
    }

    private fun loadSoilmoisture1() {
        binding.soilmoisture1Chart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.soilMoisture1.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Soilmoisture1").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.soilmoisture1Chart.data = lineData
        binding.soilmoisture1Chart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.soilmoisture1Chart.invalidate() // Refresh the chart
    }

    private fun loadSoilmoisture2() {
        binding.soilmoisture2Chart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.soilMoisture2.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Soilmoisture2").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.soilmoisture2Chart.data = lineData
        binding.soilmoisture2Chart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.soilmoisture2Chart.invalidate() // Refresh the chart
    }

    private fun loadSoilmoisture3() {
        binding.soilmoisture3Chart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.soilMoisture3.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Soilmoisture3").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.soilmoisture3Chart.data = lineData
        binding.soilmoisture3Chart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.soilmoisture3Chart.invalidate() // Refresh the chart
    }

    private fun loadSoilmoisture4() {
        binding.soilmoisture4Chart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.soilMoisture4.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Soilmoisture4").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.soilmoisture4Chart.data = lineData
        binding.soilmoisture4Chart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.soilmoisture4Chart.invalidate() // Refresh the chart
    }

    private fun loadSuhu() {
        binding.suhuChart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.suhu.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Suhu").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.suhuChart.data = lineData
        binding.suhuChart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.suhuChart.invalidate() // Refresh the chart
    }

    private fun loadTekananUdara() {
        binding.tekananUdaraChart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.tekananUdara.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "TekananUdara").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.tekananUdaraChart.data = lineData
        binding.tekananUdaraChart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.tekananUdaraChart.invalidate() // Refresh the chart
    }

    private fun loadPompaNutrisi() {
        binding.pompaNutrisiChart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, (sensor.pompaNutrisi?:0).toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "PompaNutrisi").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.pompaNutrisiChart.data = lineData
        binding.pompaNutrisiChart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.pompaNutrisiChart.invalidate() // Refresh the chart
    }

    private fun loadHumidity() {
        binding.humidityChart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.humidity.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Humidity").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.humidityChart.data = lineData
        binding.humidityChart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.humidityChart.invalidate() // Refresh the chart
    }

    private fun loadSvp() {
        binding.svpChart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.svp.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Svp").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.svpChart.data = lineData
        binding.svpChart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.svpChart.invalidate() // Refresh the chart
    }

    private fun loadAvp() {
        binding.avpChart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.avp.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Avp").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.avpChart.data = lineData
        binding.avpChart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.avpChart.invalidate() // Refresh the chart
    }

    private fun loadVpd() {
        binding.vpdChart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.vpd.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Vpd").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.vpdChart.data = lineData
        binding.vpdChart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.vpdChart.invalidate() // Refresh the chart
    }

    private fun loadTemperaturedht() {
        binding.temperaturedhtChart.apply {
            setDrawGridBackground(false)
            setDrawBorders(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = 90f
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            extraBottomOffset = 100f
        }

        val entries = mutableListOf<Entry>().apply {
            var i = 0f
            for (sensor in listSensorData) {
                add(Entry(i, sensor.temperatureDht.toFloat()))
                i += 1f
            }
        }

        val labels = listSensorData.map { it.timestamp }

        val dataSet = LineDataSet(entries, "Temperaturedht").apply {
            valueTextColor = Color.BLACK
            setCircleColor(Color.parseColor("#0366fc"))
            setColor(Color.parseColor("#0366fc"))
            circleRadius = 4f
            setDrawValues(true)
            setDrawCircles(true)
        }

        val lineData = LineData(dataSet)
        binding.temperaturedhtChart.data = lineData
        binding.temperaturedhtChart.xAxis.valueFormatter = MyXAxisFormatter(labels)
        binding.temperaturedhtChart.invalidate() // Refresh the chart
    }

    class MyXAxisFormatter(private val labels: List<String>) : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return labels[value.toInt()]
        }
    }


}