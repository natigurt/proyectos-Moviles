package com.example.practicafragmentos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.practicafragmentos.databinding.FragmentFragmentoDivisibleBinding
import com.example.practicafragmentos.viewmodel.MainViewModel

class FragmentoDivisible : Fragment() {

    private var _binding: FragmentFragmentoDivisibleBinding? = null
    private val binding get() = _binding!!;

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFragmentoDivisibleBinding.inflate(inflater, container, false);
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnValidarD.setOnClickListener {
            viewModel.validarDivisible(
                binding.chkDiv2.isChecked,
                binding.chkDiv3.isChecked,
                binding.chkDiv5.isChecked,
                binding.chkDiv10.isChecked,
                binding.chkNinguno.isChecked
            )
        }

        viewModel.misDatos.observe(viewLifecycleOwner) { datos ->
            binding.txtResultadoD.text = datos.State
            if (datos.State == "Pendiente") {
                resetearFrag()
            }
        }
    }

    private fun resetearFrag() {
        binding.chkDiv2.isChecked = false
        binding.chkDiv3.isChecked = false
        binding.chkDiv5.isChecked = false
        binding.chkDiv10.isChecked = false
        binding.chkNinguno.isChecked = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}