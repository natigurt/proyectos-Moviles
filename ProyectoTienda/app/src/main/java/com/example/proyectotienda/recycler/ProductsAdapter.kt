package com.example.proyectotienda.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectotienda.databinding.ItemProductBinding
import com.example.proyectotienda.model.Product

class ProductsAdapter(
    private var listaProductos: List<Product>,
    private val onProductClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val producto = listaProductos[position]
        holder.bind(producto)
        holder.itemView.setOnClickListener {
            onProductClick(producto)
        }
    }

    override fun getItemCount(): Int = listaProductos.size

    fun updateProducts(nuevaLista: List<Product>) {
        this.listaProductos = nuevaLista
        notifyDataSetChanged()
    }
}
