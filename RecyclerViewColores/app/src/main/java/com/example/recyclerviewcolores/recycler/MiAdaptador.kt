package com.example.recyclerviewcolores.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewcolores.R
import com.example.recyclerviewcolores.model.Colour
import com.example.recyclerviewcolores.model.Datos

//esta clase es como un puente entre los datos y el recyclerview.
class MiAdaptador(var misDatos: Datos) : RecyclerView.Adapter<MiVista>() {

    var posicionSeleccionada = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MiVista { //infla la fila y devuelve ese objeto a la vista.
        var miVista = LayoutInflater.from(parent.context).inflate(R.layout.my_row, parent, false)
        return MiVista(miVista)
    }

    override fun onBindViewHolder(
        holder: MiVista,
        position: Int
    ) {
        val color = misDatos.lista[position] //obtener el objeto colour correspondiente
        val colorInt = Color.parseColor(color.hexadecimal)

        holder.txtNombre.text = color.nombre
        holder.txtHexadec.text = color.hexadecimal

        //cuando user haga click, guardamos la posicion anterior y actualizamos la seleccion nueva.
        if (position == posicionSeleccionada) {
            holder.root.setBackgroundColor(Color.WHITE)
            holder.txtNombre.setTextColor(colorInt)
            holder.txtHexadec.setTextColor(colorInt)
        } else {
            holder.root.setBackgroundColor(colorInt)
            holder.txtNombre.setTextColor(Color.WHITE)
            holder.txtHexadec.setTextColor(Color.WHITE)
        }

        holder.root.setOnClickListener {
            val anterior = posicionSeleccionada
            posicionSeleccionada = position

            if (anterior != RecyclerView.NO_POSITION) {
                notifyItemChanged(anterior)
            }
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return misDatos.lista.size
    }
}