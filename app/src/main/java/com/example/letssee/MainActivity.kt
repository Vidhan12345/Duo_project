package com.example.letssee
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var signOutButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        signOutButton = findViewById(R.id.signOutButton)

        signOutButton.setOnClickListener {
            signOut()
        }
    }

    // Function to sign out the user
    private fun signOut() {
        auth.signOut() // This signs out the current user

        // For example, you might want to navigate to a login screen
         val intent = Intent(this, LogInActivity::class.java)
         startActivity(intent)
         finish() // Finish the current activity
    }
}
