package com.akin.e_commerce

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CategoriesAdapter(private  val context: Context, val categoriesList:ArrayList<CategoriesModel>):
    RecyclerView.Adapter<CategoriesAdapter.PostHolder>() {



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesAdapter.PostHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.categories_item, parent, false)

        return CategoriesAdapter.PostHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriesAdapter.PostHolder, position: Int) {
        Glide.with(context)
            .load(categoriesList[position].picture)
            .centerCrop()
            .fitCenter()
            .into(holder.image);

        holder.title.text = categoriesList[position].title
    }

    override fun getItemCount(): Int {
        return  categoriesList.size
    }
    class PostHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val image : ImageView = itemView.findViewById(R.id.picture_categories)
        val title : TextView = itemView.findViewById(R.id.title_categories)

    }
}