package com.example.proyectotienda.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.proyectotienda.databinding.ItemCategoryBinding
import com.example.proyectotienda.model.Category

class CategoryViewHolder(private val binding: ItemCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
        //para pintar los datos de la categoria
    fun bind(categoria: Category) {
        binding.txtCategoryName.text = categoria.name
        binding.txtCategoryDescription.text = categoria.description
    }
}
