package com.myapp.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.myapp.myapplication.databinding.ActivityProductSelectionBinding
import com.myapp.myapplication.model.UserModel

class ProductSelectionActivity : AppCompatActivity() {
    private val binding: ActivityProductSelectionBinding by lazy {
        ActivityProductSelectionBinding.inflate(layoutInflater)
    }
    private var email: String? = null
    private var password: String? = null
    private lateinit var listOfProducts: String
    private var quantityOfProducts: Int = 0
    //private lateinit var quantityOfProducts: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


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
        database = FirebaseDatabase.getInstance()

        val productList = arrayOf(
            "Product 1 - Widget A",
            "Product 1 - Widget B",
            "Product 1 - Widget C",
            "Product 1 - Widget D"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, productList)
        val autoCompleteTextView = binding.listOfProduct
        autoCompleteTextView.setAdapter(adapter)

        binding.uplaodButton.setOnClickListener {
            listOfProducts = binding.listOfProduct.text.toString()
            //quantityOfProducts = binding.quantityEdtTxt.text.toString().trim()
            quantityOfProducts = binding.quantityEdtTxt.text.toString().trim().toIntOrNull() ?: 0
            if (listOfProducts.isBlank() || quantityOfProducts <= 0) {
                Toast.makeText(this, "Please enter the details", Toast.LENGTH_SHORT).show()
            } else {
                uploadData()
                Toast.makeText(this, "Product details uploaded successfully", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this, ConfirmationScreenActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }

    private fun uploadData() {
        listOfProducts = binding.listOfProduct.text.toString()
        //quantityOfProducts = binding.quantityEdtTxt.text.toString().trim()
        quantityOfProducts = binding.quantityEdtTxt.text.toString().trim().toIntOrNull() ?: 0

        val products = UserModel(email, password, listOfProducts, quantityOfProducts)
        val userId = auth.currentUser!!.uid
        userId?.let {
            database.reference.child("user").child(userId).child("products").push()
                .setValue(products)
        }
    }
}