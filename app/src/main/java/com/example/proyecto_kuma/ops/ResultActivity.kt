package com.example.proyecto_kuma.ops

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import com.example.proyecto_kuma.MainActivity
import com.example.proyecto_kuma.maze.HighScoresActivity
import com.example.proyecto_kuma.R
import com.example.proyecto_kuma.ScoreDatabaseHelper

class ResultActivity : AppCompatActivity() {

    private lateinit var dbHelper: ScoreDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Inicializar la base de datos
        dbHelper = ScoreDatabaseHelper(this)

        // Recuperar datos del Intent
        val score = intent.getIntExtra("SCORE", 0)
        val correctAnswers = intent.getIntExtra("CORRECT_ANSWERS", 0)

        // Vincular vistas
        val gameCompletedText: TextView = findViewById(R.id.gameCompletedText)
        val accuracyText: TextView = findViewById(R.id.accuracyText)
        val scoreText: TextView = findViewById(R.id.scoreText)

        // Mostrar los resultados en las vistas
        gameCompletedText.text = "¡Felicidades!"
        accuracyText.text = "Aciertos: $correctAnswers"
        scoreText.text = "Puntaje: $score"

        // Configurar botones
        findViewById<Button>(R.id.retryButton).setOnClickListener {
            // Reiniciar el juego
            val intent = Intent(this, ActivityOps::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.mainMenuButton).setOnClickListener {
            // Ir al menú principal
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.highScoresButton).setOnClickListener {
            // Ver los mejores puntajes
            val intent = Intent(this, HighScoresActivity::class.java)
            startActivity(intent)
        }

        // Mostrar diálogo para guardar el puntaje
        showEnterNameDialog(score, correctAnswers)
    }

    private fun showEnterNameDialog(score: Int, correctAnswers: Int) {
        // Inflar el diseño del diálogo personalizado
        val dialogView = layoutInflater.inflate(R.layout.dialog_enter_name, null)
        val playerNameEditText = dialogView.findViewById<EditText>(R.id.playerNameEditText)

        // Configurar el diálogo
        val dialog = AlertDialog.Builder(this)
            .setTitle("Guardar Puntaje")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val playerName = playerNameEditText.text.toString().trim()
                if (playerName.isNotBlank()) {
                    saveScore(playerName, score, correctAnswers)
                } else {
                    Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar") { _, _ ->
                Toast.makeText(this, "Puntaje no guardado", Toast.LENGTH_SHORT).show()
            }
            .create()

        dialog.show()
    }

    private fun saveScore(playerName: String, score: Int, correctAnswers: Int) {
        // Guardar el puntaje en la base de datos
        val result = dbHelper.insertScore(playerName, score, correctAnswers, 0, 0)
        if (result != -1L) {
            Toast.makeText(this, "Puntaje guardado exitosamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al guardar el puntaje", Toast.LENGTH_SHORT).show()
        }
    }
}

