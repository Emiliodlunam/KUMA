package com.example.proyecto_kuma.memo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_kuma.R

class LevelSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_selection)

        // Botones para cada nivel
        val easyButton: Button = findViewById(R.id.easyButton)
        val mediumButton: Button = findViewById(R.id.mediumButton)
        val hardButton: Button = findViewById(R.id.hardButton)

        // Navegar al juego con diferentes configuraciones según el nivel
        easyButton.setOnClickListener {
            startGame(2, 3) // Nivel fácil: 2x3
        }

        mediumButton.setOnClickListener {
            startGame(4, 3) // Nivel medio: 4x3
        }

        hardButton.setOnClickListener {
            startGame(4, 4) // Nivel difícil: 4x4
        }
    }

    private fun startGame(rows: Int, columns: Int) {
        val intent = Intent(this, ActivityMemo::class.java)
        intent.putExtra("rows", rows)
        intent.putExtra("columns", columns)
        startActivity(intent)
    }
}
