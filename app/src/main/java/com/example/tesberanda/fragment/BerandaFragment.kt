package com.example.tesberanda.fragment

import android.content.SharedPreferences
import android.icu.text.IDNA.Info
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.tesberanda.databinding.FragmentBerandaBinding
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.example.tesberanda.DataTopic1Response
import com.example.tesberanda.MainActivity
import com.example.tesberanda.R
import com.example.tesberanda.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat


class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Setup Listener for ImageView (btn_nutrient_tank)
        binding.btnNutrientTank.setOnClickListener {
            (requireActivity() as MainActivity).openFragment(NutrientFragment())
//            findNavController().navigate(R.id.action_berandaFragment_to_nutrientFragment)
        }

        binding.btnMicroclimate.setOnClickListener {
            (requireActivity() as MainActivity).openFragment(MicroclimateFragment())
//            findNavController().navigate(R.id.action_berandaFragment_to_microclimateFragment)
        }

        binding.btnSoil.setOnClickListener {
            (requireActivity() as MainActivity).openFragment(SoilFragment())
//            findNavController().navigate(R.id.action_berandaFragment_to_soilFragment)
        }

        binding.btnTable.setOnClickListener {
            (requireActivity() as MainActivity).openFragment(TableFragment())
//            findNavController().navigate(R.id.action_berandaFragment_to_tableFragment)
        }

        binding.btnGraph.setOnClickListener {
            (requireActivity() as MainActivity).openFragment(GrafikFragment())
        }

        // Setup data loading
        loadData()
    }


    private fun loadData() {
        val currentBinding = _binding // Simpan binding saat ini dalam variabel lokal

        // Load data from API or other sources
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
                                tvAvp.text = "${sensor.avp} kPa"
                                tvSvp.text = "${sensor.suhuAir} kPa"
                                tvVpd.text= "${sensor.vpd} kPa"
                                // Update UI elements based on sensor data
                                if (sensor.pompaNutrisi == 0) {
                                    tvNutrientPump.text = "OFF"
                                    cvNutrientPump.setCardBackgroundColor(
                                        requireContext().getColor(R.color.merah)
                                    )
                                } else {
                                    tvNutrientPump.text = "ON"
                                    cvNutrientPump.setCardBackgroundColor(
                                        requireContext().getColor(R.color.ijomuda)
                                    )
                                }

                                if (sensor.pompaAir == 0) {
                                    tvCoolingSystem.text = "OFF"
                                    cvNutrientPump.setCardBackgroundColor(
                                        requireContext().getColor(R.color.merah)
                                    )
                                } else {
                                    tvCoolingSystem.text = "ON"
                                    cvCoolingSystem.setCardBackgroundColor(
                                        requireContext().getColor(R.color.ijomuda)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}