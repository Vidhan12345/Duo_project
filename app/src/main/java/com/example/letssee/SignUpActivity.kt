package com.example.letssee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.letssee.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        //Admin Sigup Button function
        binding.adminSignUp.setOnClickListener{
            if (binding.id.text.toString().isEmpty()){
                Toast.makeText(this, "Please enter Unique ID", Toast.LENGTH_SHORT).show()
            }
            else{

            }
        }
        //Student Sigup Button function
        binding.studentSignUp.setOnClickListener {
            // Getting data
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.pass.text.toString()

            // Check if any field is empty
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                // Display an error message if any field is empty
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else if (password == confirmPassword) {
                // Passwords match, proceed with registration
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Registration successful
                            val user = task.result?.user

                            // Store user data in Firebase Database
                            storeUserData(email, password)

                            Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show()

                            // Redirect to the desired activity
                            val openLogInActivity = Intent(this, LogInActivity::class.java)
                            startActivity(openLogInActivity)
                        } else {
                            // Registration failed
                            Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                // Passwords do not match
                Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun storeUserData(email: String, password: String) {
        // Get a reference to the "Users" node in the database
        val usersRef: DatabaseReference = database.reference.child("Users")

        // Create a unique key for the user
        val userId: String = usersRef.push().key ?: ""

        // Create a User object with the provided data
        val user = User(userId, email,password)

        // Store the user data under the unique key
        usersRef.child(userId).setValue(user)
    }
}