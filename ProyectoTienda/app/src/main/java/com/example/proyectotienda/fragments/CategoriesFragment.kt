package com.example.proyectotienda.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.proyectotienda.FunActivity
import com.example.proyectotienda.databinding.FragmentCategoriesBinding
import com.example.proyectotienda.recycler.CategoriesAdapter
import com.example.proyectotienda.viewmodel.ProductsViewModel

class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductsViewModel by viewModels()
    private lateinit var adapter: CategoriesAdapter

    //para crear la vista del frag
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    //cuando la vista ya esta creada..
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        val token = obtenerToken()
        if (token.isNotEmpty()) {
            viewModel.loadCategories(token)
        } else {
            Toast.makeText(requireContext(), "No hay sesión activa", Toast.LENGTH_SHORT).show()
        }
    }

    //configurar el recycler (es este el lugar para hacerlo?)
    private fun setupRecyclerView() {
        // inicializar el adaptador con una lista vacía
        adapter = CategoriesAdapter(emptyList()) { category ->
            // instancia de ProductsFragment pasando el id de la categoría seleccionada
            val fragmentoProductos = ProductsFragment.newInstance(category.id)
            // cambiar al fragmento de productos (revisar si hay otra manera de hacerlo)
            (requireActivity() as FunActivity).cambiarFragmento(fragmentoProductos)
        }
        binding.recyclerCategories.adapter = adapter
    }

    //observar viewModel
    private fun observeViewModel() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            adapter.actualizarCategorias(categories)
        }
        //observar errores
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

    //limpiar binding
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
