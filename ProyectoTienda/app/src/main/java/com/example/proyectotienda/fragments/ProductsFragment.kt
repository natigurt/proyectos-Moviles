package com.example.proyectotienda.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.proyectotienda.databinding.FragmentProductsBinding
import com.example.proyectotienda.recycler.ProductsAdapter
import com.example.proyectotienda.viewmodel.ProductsViewModel

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductsViewModel by viewModels()
    private lateinit var adapter: ProductsAdapter

    private var categoryId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryId = it.getLong(ARG_CATEGORY_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        val token = obtenerToken()
        if (token.isNotEmpty() && categoryId != -1L) {
            viewModel.loadProductsByCategory(token, categoryId)
        }
    }

    private fun setupRecyclerView() {
        adapter = ProductsAdapter(emptyList()) { product ->
            Toast.makeText(requireContext(), "Producto: ${product.name}", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerProducts.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.actualizarProductos(products)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
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

    companion object {
        private const val ARG_CATEGORY_ID = "category_id"

        @JvmStatic
        fun newInstance(categoryId: Long) =
            ProductsFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_CATEGORY_ID, categoryId)
                }
            }
    }
}
