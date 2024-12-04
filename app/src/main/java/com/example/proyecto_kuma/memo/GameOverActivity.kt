package com.example.proyecto_kuma.memo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_kuma.MainActivity
import com.example.proyecto_kuma.R


class GameOverActivity : AppCompatActivity() {

    private lateinit var gameOverMessage: TextView
    private lateinit var difficultyTextView: TextView
    private lateinit var restartButton: Button
    private lateinit var menuMemoButton: Button
    private lateinit var menuPrincipalButton: Button
    private lateinit var viewScoresButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_over_memo)

        // Vinculación de variables con los elementos del diseño
        gameOverMessage = findViewById(R.id.gameOverMessage)
        difficultyTextView = findViewById(R.id.difficultyTextView)
        restartButton = findViewById(R.id.restartButton)
        menuMemoButton = findViewById(R.id.menuMemoButton)
        menuPrincipalButton = findViewById(R.id.menuPrincipalButton)
        viewScoresButton = findViewById(R.id.viewScoresButton)

        // Obtener datos pasados desde la actividad del juego
        val playerName = intent.getStringExtra("PLAYER_NAME") ?: "Desconocido"
        val time = intent.getLongExtra("TIME", 0)
        val attempts = intent.getIntExtra("ATTEMPTS", 0)
        val difficulty = intent.getStringExtra("DIFFICULTY") ?: "Desconocido"

        // Mostrar el mensaje con los datos
        gameOverMessage.text = "Tiempo: $time segundos\nIntentos: $attempts"
        difficultyTextView.text = "Dificultad: $difficulty"

        // Guardar automáticamente los datos en SQLite
        saveGameData(playerName, time, attempts, difficulty)

        // Configurar el botón para reiniciar el juego
        restartButton.setOnClickListener {
            val intent = Intent(this, ActivityMemo::class.java)
            startActivity(intent)
            finish()
        }

        // Configurar el botón para ir al menú de Memo
        menuMemoButton.setOnClickListener {
            val intent = Intent(this, LevelSelectionActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Configurar el botón para ir al menú principal
        menuPrincipalButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Configurar el botón para ver los puntajes
        viewScoresButton.setOnClickListener {
            val intent = Intent(this, ScoreListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveGameData(playerName: String, time: Long, attempts: Int, difficulty: String) {
        val dbHelper = GameDatabaseHelper(this)
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put("player_name", playerName)
            put("time", time)
            put("attempts", attempts)
            put("difficulty", difficulty)
        }

        val result = db.insert("game_results", null, values)
        db.close()

        if (result == -1L) {
            Toast.makeText(this, "Error al guardar los datos.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Datos guardados correctamente.", Toast.LENGTH_SHORT).show()
        }
    }
}
