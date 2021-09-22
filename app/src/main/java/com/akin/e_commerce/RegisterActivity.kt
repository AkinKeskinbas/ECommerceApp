package com.akin.e_commerce

import android.content.Intent
import android.content.LocusId
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        val nameText: TextView by lazy {findViewById(R.id.nameText)}
        val emailText: TextView by lazy {findViewById(R.id.emailText)}
        val passwordText: TextView by lazy {findViewById(R.id.passwordText)}
        val kayitBtn: View by lazy {findViewById(R.id.kayitOlBtn)}
        val hesabimVarBtn: View by lazy {findViewById(R.id.hesabimVarBtn)}
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth


        hesabimVarBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })
        kayitBtn.setOnClickListener(View.OnClickListener {
            val receivedName = nameText.text.toString()
            val receivedemail = emailText.text.toString()
            val receivedpass = passwordText.text.toString()
            when {
                receivedName.isNullOrEmpty() -> {
                    nameText.error= "Isim bos olamaz!"
                }
                receivedemail.isNullOrEmpty() -> {
                    emailText.error = "e-mail bos olamaz"
                }
                receivedpass.isNullOrEmpty() -> {

                    passwordText.error = "e-mail bos olamaz"
                }
                else -> {
                    println("$receivedName $receivedemail $receivedpass")
                    auth.createUserWithEmailAndPassword(receivedemail,receivedpass).addOnSuccessListener {
                        println("kayit Basarili")
                        kullaniciKaydet(receivedName,receivedemail, auth.currentUser!!.uid)
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)

                    }.addOnFailureListener {
                       showAlert("Hata!",it.message.toString())
                        println(it.toString())
                    }

                }
            }

        })
    }
    fun showAlert(title:String, message:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)


        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.yes, Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.no, Toast.LENGTH_SHORT).show()
        }


        builder.show()

    }
    private fun kullaniciKaydet(name:String, email:String, id: String){
        val docData = hashMapOf(
            "name" to name,
            "email" to email,
            "id" to id,

        )
        val favData = hashMapOf(

            "favorites" to arrayListOf("1","2")

        )

        val db = Firebase.firestore
        db.collection("users").document(id)
            .set(docData)
            .addOnSuccessListener { Log.d("Register", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("Register", "Error writing document", e) }
        db.collection("users").document(id).collection("favoritsCollection").document("favoritesDc")
            .set(favData)
            .addOnSuccessListener { Log.d("Register", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("Register", "Error writing document", e) }
    }
}