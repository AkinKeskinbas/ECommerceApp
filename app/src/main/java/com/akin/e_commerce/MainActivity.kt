package com.akin.e_commerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    val merhabaText: TextView by lazy {findViewById(R.id.merhabaText)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_main)
        getFavList()






    }



    public override fun onStart() {
        super.onStart()

       val currentUser = auth.currentUser
        if (currentUser!=null){
            println(currentUser.email+"+++++++++")
            merhabaText.text="Merhaba ${currentUser.email}"
            Handler().postDelayed({

                println("kulaaniciGirmis")
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }, 3000)

        }else{
            merhabaText.text="Merhaba Kayit Sayfasina Yonlendiriliyorsunuz..."
           Handler().postDelayed({
               val intent = Intent(this, RegisterActivity::class.java)
               startActivity(intent)
               println("girisYapilmamis")
                //doSomethingHere()
            }, 3000)

        }


    }


}

fun getFavList(){
    val db = Firebase.firestore
    db.collection("Users").document("users").collection("favCollection").get()
        .addOnSuccessListener { result->

            for (document in result) {
                val list : ArrayList<String> = document.get("favArrays") as ArrayList<String>

                println(list)

            }
        }
        .addOnFailureListener { exception ->
            println(exception)

        }
}

