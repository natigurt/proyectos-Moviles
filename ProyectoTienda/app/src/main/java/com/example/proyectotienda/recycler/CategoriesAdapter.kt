package com.example.proyectotienda.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectotienda.databinding.ItemCategoryBinding
import com.example.proyectotienda.model.Category

class CategoriesAdapter(
    private var listaCategorias: List<Category>,
    private val onCategoryClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        // Inflamos el xml de cada fila
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoria = listaCategorias[position]
        // pintamos los datos de la categoría usando el viewholder
        holder.bind(categoria)

        // Manejamos el click en la fila
        holder.itemView.setOnClickListener {
            onCategoryClick(categoria)
            //que lleve a otro fragment, donde se encuentran los productos de esa categoria.
        }
    }

    override fun getItemCount(): Int = listaCategorias.size

    fun actualizarCategorias(nuevaLista: List<Category>) {
        this.listaCategorias = nuevaLista
        notifyDataSetChanged()
    }
}
