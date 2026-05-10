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
import com.example.proyectotienda.databinding.FragmentCategoriesBinding
import com.example.proyectotienda.recycler.CategoriesAdapter
import com.example.proyectotienda.viewmodel.CategoryViewModel
import kotlin.apply

class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CategoryViewModel by viewModels()
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

        configRecyclerView()
        configObservadorViewModel()

        val token = obtenerToken()
        if (token.isNotEmpty()) {
            viewModel.loadCategories(token)
        } else {
            Toast.makeText(requireContext(), "No hay sesión activa", Toast.LENGTH_SHORT).show()
        }
    }

    //configurar el recycler
    private fun configRecyclerView() {
        //inicializar el adaptador
        adapter = CategoriesAdapter(emptyList()) { category ->
            //al hacer click en una categoria..
            val fragmentoProductos = ProductsFragment().apply {
                //*recordatorio = bundle es como un contenedor de datos clave-valor
                // para la info entre componentes.
                arguments = Bundle().apply {
                    putLong("categoryId", category.id)
                }
            }
            //cambiar de fragmento a los productos
            parentFragmentManager.beginTransaction()
                .replace(R.id.contenedor_fragmentos, fragmentoProductos)
                .addToBackStack(null)
                .commit()
        }
        //asignar el adaptador al recycler
        binding.recyclerCategories.adapter = adapter
    }

    //observar viewModel
    private fun configObservadorViewModel() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            adapter.actualizarCategorias(categories)
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

    //limpiar binding
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
