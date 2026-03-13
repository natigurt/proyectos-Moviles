package com.example.apidogview.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apidogview.R
import com.example.apidogview.model.DogResponse

class MyAdapter (var dataSet: DogResponse) : RecyclerView.Adapter<MyView>() {

    private lateinit var contexto: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyView {
        contexto = parent.context
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.my_row, parent, false
        )
        return MyView(view)
    }

    override fun onBindViewHolder(
        holder: MyView,
        position: Int
    ) {
        val url : String = dataSet.message!![position]
        Glide.with(contexto).load(url).into(holder.imV1)
    }

    override fun getItemCount(): Int {
        return dataSet.message?.size ?: 0
    }

}