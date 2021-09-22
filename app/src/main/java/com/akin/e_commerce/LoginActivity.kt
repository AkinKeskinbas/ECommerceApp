package com.akin.e_commerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {

        val emailText: TextView by lazy {findViewById(R.id.emailText)}
        val passwordText: TextView by lazy {findViewById(R.id.passwordText)}
        val girisBtn: TextView by lazy {findViewById(R.id.girisBtn)}
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
        girisBtn.setOnClickListener(View.OnClickListener {
            val receivedemail = emailText.text.toString()
            val receivedpass = passwordText.text.toString()
            when {

                receivedemail.isNullOrEmpty() -> {
                    emailText.error = "e-mail bos olamaz"
                }
                receivedpass.isNullOrEmpty() -> {

                    passwordText.error = "e-mail bos olamaz"
                }
                else -> {
                    println(" $receivedemail $receivedpass")
                    auth.signInWithEmailAndPassword(receivedemail, receivedpass)
                        .addOnSuccessListener {
                            println("Giris Basarili")
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)

                        }.addOnFailureListener {
                        showAlert("Hata!", it.message.toString())
                        println(it.toString())
                    }

                }
            }
        })
    }
    private fun showAlert(title:String, message:String){
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
}