package com.example.letssee

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.letssee.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("Users")

        // Retrieve current user details
        val currentUser = auth.currentUser

        // Set current user details to the UI
        binding.editName.setText(currentUser?.displayName)
        binding.editEmail.setText(currentUser?.email)

        // Save Button Click Listener
        binding.saveButton.setOnClickListener {
            // Get updated user details
            val newDisplayName = binding.editName.text.toString()
            val newEmail = binding.editEmail.text.toString()
            val newUsername = binding.editUsername.text.toString()
            val newPassword = binding.editPassword.text.toString()

            // Update user details in Firebase Authentication
            currentUser?.updateProfile(UserProfileChangeRequest.Builder()
                .setDisplayName(newDisplayName)
                // Add other details as needed
                .build())
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Update user details in Realtime Database
                        currentUser?.uid?.let { uid ->
                            val updatedUser = User(uid, newEmail, newPassword)
                            database.child(uid).setValue(updatedUser)
                                .addOnCompleteListener { dbTask ->
                                    if (dbTask.isSuccessful) {
                                        // Update successful
                                        // You can add additional logic or UI updates here
                                    } else {
                                        // Update in database failed
                                    }
                                }
                        }
                    } else {
                        // Update in Firebase Authentication failed
                    }
                }
        }
    }
}

