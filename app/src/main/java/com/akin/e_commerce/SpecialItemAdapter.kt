package com.akin.e_commerce

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SpecialItemAdapter(private  val context: Context, val specialItemsList:ArrayList<SpecialOfferModel>) : RecyclerView.Adapter<SpecialItemAdapter.PostHolder>() {

    private lateinit var auth: FirebaseAuth;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.special_items, parent, false)
        auth = Firebase.auth
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        Glide.with(context)
            .load(specialItemsList[position].picture)
            .centerCrop()
            .fitCenter()
            .into(holder.image);
        holder.header.text = specialItemsList[position].title
        holder.currenPrice.text = specialItemsList[position].price
        holder.desc.text = specialItemsList[position].desc

    }

    override fun getItemCount(): Int {
        return specialItemsList.size
    }

    class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageSpecial)

        val header: TextView = itemView.findViewById(R.id.titleSpecial)
        val currenPrice: TextView = itemView.findViewById(R.id.priceSpecial)
        val desc: TextView = itemView.findViewById(R.id.descSpecial)

    }


}
