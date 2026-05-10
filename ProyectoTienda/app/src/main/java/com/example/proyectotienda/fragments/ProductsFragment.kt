package com.example.proyectotienda.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.proyectotienda.R
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
        //obtener el id de la categoria, si es nulo usa -1
        categoryId = arguments?.getLong("categoryId", -1) ?: -1
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
        configRecyclerView()
        configObservadorViewModel()

        val token = obtenerToken()
        if (token.isNotEmpty() && categoryId != -1L) {
            viewModel.loadProductsByCategory(token, categoryId)
        }
    }

    private fun configRecyclerView() {
        //inicializar el adaptador
        adapter = ProductsAdapter(emptyList()) { product ->
            //al hacer click en una categoria..
            Toast.makeText(requireContext(), "Producto: ${product.name}", Toast.LENGTH_SHORT).show()
            val detalleFragment = ProductDetailFragment().apply {
                //*recordatorio = bundle es como un contenedor de datos clave-valor
                // para la info entre componentes.
                arguments = Bundle().apply {
                    putLong("productId", product.id)
                }
            }
            //cambiar fragmento al detalle productos
            parentFragmentManager.beginTransaction()
                .replace(R.id.contenedor_fragmentos, detalleFragment)
                .addToBackStack(null)
                .commit()
        }
        //asignar el adaptador al recycler.
        binding.recyclerProducts.adapter = adapter
    }

    private fun configObservadorViewModel() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            //si no hay productos..
            if (products.isNullOrEmpty()) {
                binding.recyclerProducts.visibility = View.GONE
                binding.tvEmptyProducts.visibility = View.VISIBLE
            } else {
                binding.recyclerProducts.visibility = View.VISIBLE
                binding.tvEmptyProducts.visibility = View.GONE
                adapter.actualizarProductos(products)
            }
        }
        //observar errores y si hay un error..
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
