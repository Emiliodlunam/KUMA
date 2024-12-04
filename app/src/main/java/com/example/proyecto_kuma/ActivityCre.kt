package com.example.proyecto_kuma

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ActivityCre : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits) // Asocia el layout de créditos
    }
}