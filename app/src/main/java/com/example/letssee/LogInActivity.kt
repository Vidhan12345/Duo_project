package com.example.letssee


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.letssee.databinding.ActivityLogInBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class LogInActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance().getReference("Users")

        binding.signupText.setOnClickListener {
            val openSignUpActivity = Intent(this, SignUpActivity::class.java)
            startActivity(openSignUpActivity)
        }

        // LogIn Button Click Listener
        binding.loginButton.setOnClickListener {
            // Getting data
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            // Check if any field is empty
            if (email.isEmpty() || password.isEmpty()) {
                // Display an error message if any field is empty
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else {
                // Validate the user's credentials
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener{
                        if (it.isSuccessful){
                            startActivity(Intent(this,MainActivity::class.java))
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

}