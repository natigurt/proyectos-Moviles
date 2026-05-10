package com.example.proyectotienda.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.proyectotienda.databinding.FragmentProductDetailBinding
import com.example.proyectotienda.model.Product
import com.example.proyectotienda.viewmodel.ProductDetailViewModel

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private var productId: Long = -1
    private val viewModel: ProductDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productId = requireArguments().getLong("productId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configObservadores()

        //cargar detalle del producto
        val token = obtenerToken()
        if (token.isNotEmpty() && productId != -1L) {
            viewModel.loadProductDetail(token, productId)
        } else {
            Toast.makeText(requireContext(), "Token o productId nulo", Toast.LENGTH_SHORT).show()
        }


    }

    private fun configObservadores() {
        // observar producto
        viewModel.productDetail.observe(viewLifecycleOwner) { product ->
            if (product == null) {
                // No hay producto (error o no encontrado)
                Toast.makeText(requireContext(), "Producto no encontrado", Toast.LENGTH_SHORT).show()
            } else {
                // Producto cargado correctamente
                mostrarProducto(product)
            }
        }

        // observar errores
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarProducto(product: Product) {
        binding.txtDetailName.text = product.name
        binding.txtDetailPrice.text = "${product.price} €"
        binding.txtDetailDescription.text = product.description
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
