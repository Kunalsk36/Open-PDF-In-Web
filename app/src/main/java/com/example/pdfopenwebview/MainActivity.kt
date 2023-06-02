package com.example.pdfopenwebview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn = findViewById<Button>(R.id.btn)
        var btn2 = findViewById<Button>(R.id.btn2)
        var btn3 = findViewById<Button>(R.id.btn3)


        btn.setOnClickListener {
            Toast.makeText(this, "Please wait, loading...", Toast.LENGTH_LONG).show()
            val intent = Intent(applicationContext, MainActivity2::class.java)
            intent.putExtra("pdf_url", "https://drive.google.com/file/d/1jHFMO9DBuH9SoVcB10Tqf22yf90byB9z/view?usp=drive_link")
            startActivity(intent)
        }

        btn2.setOnClickListener {
            Toast.makeText(this, "Please wait, loading...", Toast.LENGTH_LONG).show()
            val intent = Intent(applicationContext, MainActivity2::class.java)
            intent.putExtra("pdf_url", "https://drive.google.com/file/d/1XWJY78BNqzjeZcqygdoGxm2ckMkNHVtu/view?usp=drive_link")
            startActivity(intent)
        }

        btn3.setOnClickListener {
            Toast.makeText(this, "Please wait, loading...", Toast.LENGTH_LONG).show()
            val intent = Intent(applicationContext, MainActivity2::class.java)
            intent.putExtra("pdf_url", "https://drive.google.com/file/d/1STFpZAx30zO1OOmPnprbqVw5d_eUZw4f/view?usp=drive_link")
            startActivity(intent)
        }
    }
}