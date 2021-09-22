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

class SuggestedAdapter (private  val context: Context, val suggestedList:ArrayList<SuggestedModel>) : RecyclerView.Adapter<SuggestedAdapter.PostHolder>() {

    private lateinit var auth: FirebaseAuth;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.suggested_item, parent, false)
        auth = Firebase.auth
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        Glide.with(context)
            .load(suggestedList[position].picture)
            .centerCrop()
            .fitCenter()
            .into(holder.image);
        holder.header.text = suggestedList[position].title
        holder.currenPrice.text = suggestedList[position].currentPrice
        holder.oldPrice.text = suggestedList[position].oldPrice
        holder.favIcon.setOnClickListener(View.OnClickListener {

            println(position)


            addFavCollection( suggestedList[position].id)

        })
    }

    override fun getItemCount(): Int {
        return  suggestedList.size
    }
    class PostHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val image : ImageView = itemView.findViewById(R.id.suggestedImage)
        val favIcon : ImageButton = itemView.findViewById(R.id.suggestedFavIcon)
        val header : TextView = itemView.findViewById(R.id.suggestedTitle)
        val currenPrice : TextView = itemView.findViewById(R.id.suggestedNewPrice)
        val oldPrice : TextView = itemView.findViewById(R.id.suggestedOldPrice)

    }
    fun addFavCollection(id:String){
        val db = Firebase.firestore
        val favRef = db.collection("users").document(auth.currentUser!!.uid).collection("favoritsCollection").document("favoritesDc")


        favRef
            .update("favorites", FieldValue.arrayUnion(id))
            .addOnSuccessListener {
                ( context as HomeActivity).playLottie()
                Log.d("home", "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w("home", "Error updating document", e) }
    }
    //array verilen degeri iceriyor mu diye kontrol eden fonksiyon
    fun  isPresent(arr: ArrayList<String>, target: String): Boolean {
        return arr.contains(target)
    }
}