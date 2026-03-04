package com.example.recyclerviewcolores.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewcolores.R

class MiVista(miFila: View) : RecyclerView.ViewHolder(miFila) {
    //castear los controles para trabajar
    val root: View = itemView.findViewById(R.id.root)
    val txtNombre: TextView = itemView.findViewById(R.id.txtNombre)
    val txtHexadec: TextView = itemView.findViewById(R.id.txtHexadec)
}