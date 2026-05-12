package com.example.proyectotienda.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.proyectotienda.databinding.FragmentProductDetailBinding
import com.example.proyectotienda.model.Product
import com.example.proyectotienda.viewmodel.CartViewModel
import com.example.proyectotienda.viewmodel.ProductDetailViewModel

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private var productId: Long = -1
    private var currentProduct: Product? = null
    private val viewModel: ProductDetailViewModel by viewModels()
    // activityViewModels porque queremos que sea compartido entre varios fragments
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // recuperar productId x el bundle
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
        configBotonCarrito()

        val token = obtenerToken()
        if (token.isNotEmpty() && productId != -1L) {
            viewModel.loadProductDetail(token, productId)
        }
    }

    // observar cambios viewmodel
    private fun configObservadores() {
        viewModel.productDetail.observe(viewLifecycleOwner) { product ->
            if (product != null) {
                currentProduct = product
                mostrarProducto(product)
            }
        }
        // Observar errores
        cartViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun mostrarProducto(product: Product) {
        binding.txtDetailName.text = product.name
        binding.txtDetailPrice.text = String.format("%.2f €", product.price)
        binding.txtDetailDescription.text = product.description
    }

    private fun configBotonCarrito() {
        binding.btnAddToCart.setOnClickListener {
            val product = currentProduct
            val token = obtenerToken()
            if (product != null && token.isNotEmpty()) {
                cartViewModel.addItemToCart(token, product.id, 1)
                Toast.makeText(requireContext(), "Añadido al carrito!", Toast.LENGTH_SHORT).show()
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
