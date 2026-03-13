package com.example.apidogview.recycler

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.apidogview.R

class MyView (miFila: View) : RecyclerView.ViewHolder(miFila){
    //castear controles
    val root: View = itemView.findViewById(R.id.root)
    val cv1: CardView = itemView.findViewById(R.id.cv1)
    val imV1: ImageView = itemView.findViewById(R.id.imV1)

}