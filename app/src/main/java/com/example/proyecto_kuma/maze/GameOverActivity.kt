package com.example.proyecto_kuma.maze

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_kuma.MainActivity
import com.example.proyecto_kuma.R

class GameOverActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over_maze)

        findViewById<Button>(R.id.retryButton).setOnClickListener {
            // Reiniciar el nivel actual
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.mainMenuButton).setOnClickListener {
            // Regresar al menú principal
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.LabButton).setOnClickListener {
            // Regresar al menú principal
            val intent = Intent(this, ActivityMaze::class.java)
            startActivity(intent)
            finish()
        }
    }
}
