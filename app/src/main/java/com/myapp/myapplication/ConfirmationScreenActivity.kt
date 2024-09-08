package com.myapp.myapplication

import com.myapp.myapplication.databinding.ActivityConfirmationScreenBinding
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ConfirmationScreenActivity : AppCompatActivity() {
    private val binding: ActivityConfirmationScreenBinding by lazy {
        ActivityConfirmationScreenBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()
        binding.productSelectionButton.setOnClickListener {
            val intent = Intent(this, ProductSelectionActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.logoutButton.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginScreenActivity::class.java))
            finish()
        }
    }
}