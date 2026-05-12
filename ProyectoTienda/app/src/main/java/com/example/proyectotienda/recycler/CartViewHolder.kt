package com.example.proyectotienda.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.proyectotienda.databinding.ItemCartBinding
import com.example.proyectotienda.model.CartItem

class CartViewHolder(private val binding: ItemCartBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CartItem, onDeleteClick: (CartItem) -> Unit) {
        binding.tvProductName.text = item.productName ?: "Producto"
        binding.tvQuantity.text = "Cantidad: ${item.units}"
        binding.tvPrice.text = String.format("%.2f€", item.subtotal)

        binding.btnDelete.setOnClickListener { onDeleteClick(item) }
    }
}
