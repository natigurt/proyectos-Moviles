package com.example.proyectotienda.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.proyectotienda.R
import com.example.proyectotienda.databinding.FragmentProductsBinding
import com.example.proyectotienda.model.Category
import com.example.proyectotienda.recycler.ProductsAdapter
import com.example.proyectotienda.viewmodel.ProductsViewModel

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductsViewModel by viewModels()
    private lateinit var adapter: ProductsAdapter
    
    private var categoriesList: List<Category> = emptyList()

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
        desplegableCategorias()
        
        val token = obtenerToken()
        if (token.isNotEmpty()) {
            viewModel.loadCategories(token)
            viewModel.loadAllProducts(token)
        } else {
            Toast.makeText(requireContext(), "No hay sesión activa", Toast.LENGTH_SHORT).show()
        }
    }

    private fun configRecyclerView() {
        adapter = ProductsAdapter(emptyList()) { product ->
            val detalleFragment = ProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong("productId", product.id)
                }
            }
            // reemplazar el fragmento actual por el fragmento del detalle
            parentFragmentManager.beginTransaction()
                .replace(R.id.contenedor_fragmentos, detalleFragment)
                .addToBackStack(null)
                .commit()
        }
        binding.recyclerProducts.adapter = adapter
    }

    private fun configObservadorViewModel() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            //si no hay productos, mostramos un mensaje
            if (products.isNullOrEmpty()) {
                binding.recyclerProducts.visibility = View.GONE
                binding.tvEmptyProducts.visibility = View.VISIBLE
            } else {
                //si hay productos, mostramos la lista
                binding.recyclerProducts.visibility = View.VISIBLE
                binding.tvEmptyProducts.visibility = View.GONE
                adapter.updateProducts(products)
            }
        }

        //escuchamos las livedata categories
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            //si las categorias estan vacias, las rellenamos
            if (!categories.isNullOrEmpty()) {
                this.categoriesList = categories
                val categorias = categories.map { it.name }
                
                val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categorias)
                binding.spinnerCategories.adapter = spinnerAdapter
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    //desplegable de categorias
    private fun desplegableCategorias() {
        binding.spinnerCategories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //cuando el usuario selecciona una categoria
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val token = obtenerToken()
                // si no hay token o no hay categorias, no hacemos nada
                if (token.isEmpty() || categoriesList.isEmpty()) return
                // si no hay categorias, no hacemos nada
                val selectedCategory = categoriesList[position]
                if (selectedCategory.id == -1L) {
                    // ID -1 = "Todos los productos"
                    viewModel.loadAllProducts(token)
                } else {
                    // cargar productos de la categoria seleccionada
                    viewModel.loadProductsByCategory(token, selectedCategory.id)
                }
            }
            // cuando no hay nada seleccionado (object)
            override fun onNothingSelected(parent: AdapterView<*>?) {}
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
