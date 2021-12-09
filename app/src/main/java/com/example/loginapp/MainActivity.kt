package com.example.loginapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.internal.api.FirebaseNoSignedInUserException
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var cnfPassword: EditText
    private lateinit var fAuth:FirebaseAuth
    private lateinit var register: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        cnfPassword = findViewById(R.id.confirmPassword)
        fAuth = Firebase.auth
        register = findViewById(R.id.button)
        register.setOnClickListener{Validate()}


    }

    private fun  firebaseSignUp(){

        fAuth.createUserWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener{
            task->
            if (task.isSuccessful){

                Toast.makeText(this,"Register successful",Toast.LENGTH_SHORT).show()

            }
            else {

                Toast.makeText(this,task.exception?.message,Toast.LENGTH_SHORT).show()

            }
        }
    }
    private fun Validate(){
        when{
            TextUtils.isEmpty(email.text.toString().trim())-> {
                email.setError("Please enter email")
            }
            TextUtils.isEmpty(password.text.toString().trim())-> {
                password.setError("Please enter password")
            }
            TextUtils.isEmpty(cnfPassword.text.toString().trim())-> {
                cnfPassword.setError("Please confirm password")
            }

            email.text.toString().isNotEmpty() && password.text.toString().isNotEmpty() && cnfPassword.text.toString().isNotEmpty() ->{

                if (email.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))) {
                    if (password.text.toString().length >= 9){
                        
                        if (password.text.toString() == cnfPassword.text.toString()){

                            firebaseSignUp()
                        }
                        else{
                            cnfPassword.setError("Passwords dont match")
                        }

                    }else{
                        password.setError("Please enter at least 9 charachters")
                    }

                }else {
                        email.setError("Please enter a valid email")
                }
                }
            }

        }



    }
