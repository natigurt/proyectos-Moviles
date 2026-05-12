package com.example.proyectotienda.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectotienda.databinding.FragmentCartBinding
import com.example.proyectotienda.recycler.CartAdapter
import com.example.proyectotienda.viewmodel.CartViewModel

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by activityViewModels()
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configRecyclerView()
        configObservadorViewModel()

        val token = obtenerToken()
        if (token.isNotEmpty()) {
            viewModel.loadCart(token)
        }
    }

    private fun configRecyclerView() {
        binding.recyclerCart.layoutManager = LinearLayoutManager(requireContext())

        adapter = CartAdapter(emptyList()) { item ->
            val token = obtenerToken()
            if (token.isNotEmpty()) {
                if (item.id != 0L) {
                    viewModel.deleteItemFromCart(token, item.id)
                } else {
                    Toast.makeText(requireContext(), "Error: ID de producto nulo", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.recyclerCart.adapter = adapter
    }

    private fun configObservadorViewModel() {
        viewModel.cart.observe(viewLifecycleOwner) { cart ->
            if (cart == null || cart.items.isEmpty()) {
                binding.recyclerCart.visibility = View.GONE
                binding.tvEmptyCart.visibility = View.VISIBLE
                binding.tvTotalCart.text = "Total: 0.00€"
            } else {
                binding.recyclerCart.visibility = View.VISIBLE
                binding.tvEmptyCart.visibility = View.GONE
                
                adapter.updateItems(ArrayList(cart.items))
                binding.tvTotalCart.text = String.format("Total: %.2f€", cart.totalPrice)
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun obtenerToken(): String {
        val prefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return prefs.getString("token", "") ?: ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
