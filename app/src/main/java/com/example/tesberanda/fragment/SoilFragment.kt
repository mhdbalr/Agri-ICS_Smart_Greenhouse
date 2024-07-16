package com.example.tesberanda.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tesberanda.DataTopic1Response
import com.example.tesberanda.api.RetrofitClient
import com.example.tesberanda.databinding.FragmentSoilBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat


class SoilFragment : Fragment() {

    private var _binding: FragmentSoilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSoilBinding.inflate(inflater, container, false)
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
                                tvWaterFlow1.text="${sensor.waterFlow1} L/H"
                                tvWaterFlow2.text="${sensor.waterFlow2} L/H"
                                tvWaterFlow3.text="${sensor.waterFlow3} L/H"
                                tvWaterFlow4.text="${sensor.waterFlow4} L/H"
                                tvSoilMoisture1.text="${sensor.soilMoisture1} %"
                                tvSoilMoisture2.text="${sensor.soilMoisture2} %"
                                tvSoilMoisture3.text="${sensor.soilMoisture3} %"
                                tvSoilMoisture4.text="${sensor.soilMoisture4} %"
                                tvWeight1.text="${sensor.berat1} g"
                                tvWeight2.text="${sensor.berat2} g"
                                tvWeight3.text="${sensor.berat3} g"
                                tvWeight4.text="${sensor.berat4} g"

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}