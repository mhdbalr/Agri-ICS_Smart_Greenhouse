package com.example.tesberanda.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tesberanda.DataTopic1Response
import com.example.tesberanda.R
import com.example.tesberanda.api.RetrofitClient
import com.example.tesberanda.databinding.FragmentMicroclimateBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class MicroclimateFragment : Fragment() {

    private var _binding: FragmentMicroclimateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMicroclimateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed(object : Runnable {
            override fun run() {
                loadData()
                Handler().postDelayed(this, 1000)
            }
        }, 1000)

        setupListener()
    }

    private fun setupListener() {

    }

    private fun loadData() {
        val currentBinding = _binding // Simpan binding saat ini dalam variabel lokal

        RetrofitClient.apiServiceInstance
            .getDataTopic1()
            .enqueue(object : Callback<DataTopic1Response> {
                override fun onResponse(
                    call: Call<DataTopic1Response>,
                    response: Response<DataTopic1Response>
                ) {
                    if (_binding != null && _binding == currentBinding) {
                        response.body()?.let {
                            val sensor = it.result[0]
                            val formattedDate = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(
                                SimpleDateFormat("dd-MM-yy HH:mm:ss").parse(sensor.timestamp)
                            )

                            binding.apply {
                                tvTimeStamp.text = formattedDate
                                tvWindDirection.text = "${sensor.windDirection}°, ${getWindDirection(sensor.windDirection)}"
                                tvWindSpeed.text="${sensor.kecepatanAngin} m/s"
                                tvRoomTemperature.text = "${sensor.suhu} ° C"
                                tvHumidity.text = "${sensor.humidity}  %RH"
                                tvBarometricPressure.text = "${sensor.tekananUdara}  hPa"

                                if (sensor.suhu < 18 || sensor.suhu > 36) {
                                    cvRoomTemperature.setCardBackgroundColor(
                                        requireContext().getColor(R.color.merah)
                                    )
                                } else if (sensor.suhu in 25.0..32.0) {
                                    cvRoomTemperature.setCardBackgroundColor(
                                        requireContext().getColor(R.color.white)
                                    )
                                } else {
                                    cvRoomTemperature.setCardBackgroundColor(
                                        requireContext().getColor(R.color.kuning)
                                    )
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<DataTopic1Response>, t: Throwable) {
                    t.message?.let {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

    private fun getWindDirection(windDirection: Double): String {
        return when (windDirection) {
            in 0.0..22.5 -> "N"
            in 22.5..45.0 -> "NNE"
            in 45.0..67.5 -> "NE"
            in 67.5..90.0 -> "ENE"
            in 90.0..112.5 -> "E"
            in 112.5..135.0 -> "ESE"
            in 135.0..157.5 -> "SE"
            in 157.5..180.0 -> "SSE"
            in 180.0..202.5 -> "S"
            in 202.5..225.0 -> "SSW"
            in 225.0..247.5 -> "SW"
            in 247.5..270.0 -> "WSW"
            in 270.0..292.5 -> "W"
            in 292.5..315.0 -> "WNW"
            in 315.0..337.5 -> "NW"
            in 337.5..360.0 -> "NNW"
            else -> "Arah angin tidak valid"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}