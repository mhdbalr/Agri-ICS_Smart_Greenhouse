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
import com.example.tesberanda.databinding.FragmentNutrientBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat


class NutrientFragment : Fragment() {

    private var _binding: FragmentNutrientBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNutrientBinding.inflate(inflater, container, false)
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
                                tvPh.text = sensor.ph.toString()
                                tvTimeStamp.text = formattedDate
                                tvTds.text = "${sensor.tds} mg/L"
                                tvWaterTemperature.text = "${sensor.suhuAir}° C"

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