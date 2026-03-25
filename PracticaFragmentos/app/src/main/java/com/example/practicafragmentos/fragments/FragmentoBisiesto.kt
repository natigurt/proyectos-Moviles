package com.example.practicafragmentos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.practicafragmentos.databinding.FragmentFragmentoBisiestoBinding
import com.example.practicafragmentos.viewmodel.MainViewModel

class FragmentoBisiesto : Fragment() {

    private var _binding: FragmentFragmentoBisiestoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentoBisiestoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnValidarB.setOnClickListener {
            viewModel.validarBisiesto(binding.btnSiBisiesto.isChecked)
        }

        viewModel.misDatos.observe(viewLifecycleOwner) { datos ->
            binding.txtResultadoB.text = datos.State
            if (datos.State == "Pendiente") {
                binding.rgBisiesto.clearCheck()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
