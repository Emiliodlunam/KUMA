package com.example.proyecto_kuma.maze

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_kuma.R

class ActivityMaze : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.laberinto_main)

        findViewById<Button>(R.id.btnEasy).setOnClickListener {
            startGame(5) // Laberinto fácil, tamaño 5x5
        }

        findViewById<Button>(R.id.btnMedium).setOnClickListener {
            startGame(10) // Laberinto medio, tamaño 10x10
        }

        findViewById<Button>(R.id.btnHard).setOnClickListener {
            startGame(15) // Laberinto difícil, tamaño 15x15
        }
    }

    private fun startGame(size: Int) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("MAZE_SIZE", size)
        startActivity(intent)
    }
}
