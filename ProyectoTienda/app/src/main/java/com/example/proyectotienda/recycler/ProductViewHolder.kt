package com.example.proyectotienda.recycler

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectotienda.R
import com.example.proyectotienda.databinding.ItemProductBinding
import com.example.proyectotienda.model.Product

class ProductViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(producto: Product) {
        binding.txtProductName.text = producto.name
        binding.txtProductDescription.text = producto.description
        binding.txtProductPrice.text = "${producto.price} €"
    }
}
