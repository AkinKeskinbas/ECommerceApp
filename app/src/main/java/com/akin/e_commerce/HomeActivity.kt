package com.akin.e_commerce

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.reflect.Field

class HomeActivity : AppCompatActivity() {
    companion object {

        var liste: ArrayList<String> ?=null
       var  suggestedModel = ArrayList<SuggestedModel>()
       var  categoriesModel = ArrayList<CategoriesModel>()
       var  specialOfferModel = ArrayList<SpecialOfferModel>()

    }
    private lateinit var auth: FirebaseAuth;
    private val rcSuggested: RecyclerView by lazy {findViewById(R.id.suggestedRc)}
    private val rcCategories: RecyclerView by lazy {findViewById(R.id.categorydRc)}
    private val rcSpecial: RecyclerView by lazy {findViewById(R.id.specialdRc)}
    private  val lottiAnim : LottieAnimationView by lazy { findViewById(R.id.animationView) }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //rc logic
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rcSuggested.layoutManager = layoutManager
        rcSuggested.setHasFixedSize(true)

        val layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rcCategories.layoutManager = layoutManager2
        rcCategories.setHasFixedSize(true)

        val layoutManager3 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rcSpecial.layoutManager = layoutManager3
        rcSpecial.setHasFixedSize(true)


        //firebase logic
        auth = Firebase.auth





        //functions
        getFavCollection()
        getSuggestedItems()
        getCategories()
        getSpecialOffers()




    }

    fun  getFavCollection() {
        val db = Firebase.firestore

        db.collection("users").document(auth.currentUser!!.uid).collection("favoritsCollection").get()
            .addOnSuccessListener { result->

                for (document in result) {
                     liste  = document.get("favorites") as ArrayList<String>



                }
                println(liste)

            }
            .addOnFailureListener { exception ->
                println(exception)

            }

    }

    private fun getSuggestedItems(){
        val db = Firebase.firestore

        db.collection("suggestedProducts").get()
            .addOnSuccessListener { result->

                for (document in result) {
                    var title:String = document.get("title") as String
                    var oldprice:String  = document.get("oldPrice") as String
                    var newprice:String  = document.get("currentPrice") as String
                    var picture :String = document.get("picture") as String
                    var bgColor :String  = document.get("bgColor") as String
                    var id :String  = document.get("id") as String
                    val model =   SuggestedModel(title , oldprice,  newprice, bgColor, picture,id)
                    suggestedModel.add(model)
//                  var  gelenListe: ArrayList<String>  = document.get("favorites") as ArrayList<String>

                    val adapter = SuggestedAdapter(this, suggestedModel)
                    rcSuggested.adapter = adapter

                }




            }
            .addOnFailureListener { exception ->
                println(exception)

            }
    }
    private fun getCategories(){
        val db = Firebase.firestore

        db.collection("categories").get()
            .addOnSuccessListener { result->

                for (document in result) {
                    var title:String = document.get("title") as String
                    var picture :String = document.get("picture") as String
                    var id :String  = document.get("id") as String
                    val model =   CategoriesModel(picture , id,  title)
                    categoriesModel.add(model)


                    val adapter = CategoriesAdapter(this, categoriesModel)
                    rcCategories.adapter = adapter

                }




            }
            .addOnFailureListener { exception ->
                println(exception)

            }
    }
    private fun getSpecialOffers(){
        val db = Firebase.firestore

        db.collection("specialItems").get()
            .addOnSuccessListener { result->

                for (document in result) {
                    var title:String = document.get("title") as String
                    var picture :String = document.get("picture") as String
                    var desc :String  = document.get("desc") as String
                    var price :String  = document.get("price") as String
                    val model =   SpecialOfferModel(title , price ,  desc, picture)
                    println(title)
                    specialOfferModel.add(model)


                    val adapter = SpecialItemAdapter(this, specialOfferModel)
                    rcSpecial.adapter = adapter

                }




            }
            .addOnFailureListener { exception ->
                println(exception)

            }
    }

    fun playLottie(){
        lottiAnim.isVisible = true
        lottiAnim.playAnimation()
        lottiAnim.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                lottiAnim.isVisible = false
                //Add your code here for animation end
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })

    }


}