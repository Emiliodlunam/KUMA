package com.example.proyecto_kuma.maze

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_kuma.MainActivity
import com.example.proyecto_kuma.R
import com.example.proyecto_kuma.ScoreDatabaseHelper

class GameCompletedActivity : AppCompatActivity() {

    private lateinit var dbHelper: ScoreDatabaseHelper


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_completed_maze)

        dbHelper = ScoreDatabaseHelper(this)

        // Recuperar datos pasados
        val time = intent.getIntExtra("TIME", 0)
        val lives = intent.getIntExtra("LIVES", 0)
        val difficulty = intent.getIntExtra("DIFFICULTY", 1) // 1 = Fácil, 2 = Medio, 3 = Difícil

        // Calcular puntaje
        val score = calculateScore(time, lives, difficulty)

        // Mostrar datos en la interfaz
        findViewById<TextView>(R.id.timeText).text = "Tiempo: $time segundos"
        findViewById<TextView>(R.id.livesText).text = "Vidas restantes: $lives"
        findViewById<TextView>(R.id.scoreText).text = "Puntaje: $score"

        showEnterNameDialog(score, time, lives, difficulty)

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

        findViewById<Button>(R.id.highScoresButton).setOnClickListener {
            val intent = Intent(this, HighScoresActivity::class.java)
            startActivity(intent)
        }

    }

    private fun calculateScore(time: Int, lives: Int, difficulty: Int): Int {
        require(time >= 0) { "Time cannot be negative." }
        require(lives >= 0) { "Lives cannot be negative." }

        val baseScore = 100 // Puntaje base inicial más significativo
        val difficultyMultiplier = when (difficulty) {
            1 -> 1      // Fácil
            2 -> 3      // Medio (incremento mayor)
            3 -> 5      // Difícil (bonificación máxima)
            else -> throw IllegalArgumentException("Invalid difficulty level: $difficulty")
        }
        val difficultyBonus = difficulty * 200 // Bono adicional basado en la dificultad
        val timePenalty = time * 5 // Penalización por tiempo
        val livesBonus = lives * 150 // Mayor incentivo por vidas

        return (baseScore * difficultyMultiplier) + difficultyBonus - timePenalty + livesBonus
    }

    private fun showEnterNameDialog(score: Int, time: Int, lives: Int, difficulty: Int) {
        // Inflar el diseño del diálogo personalizado
        val dialogView = layoutInflater.inflate(R.layout.dialog_enter_name, null)
        val playerNameEditText = dialogView.findViewById<EditText>(R.id.playerNameEditText)

        // Configurar el diálogo
        val dialog = AlertDialog.Builder(this)
            .setTitle("Guardar Puntaje")
            .setView(dialogView) // Usa la vista inflada aquí
            .setPositiveButton("Guardar") { _, _ ->
                val playerName = playerNameEditText.text.toString().trim()
                if (playerName.isNotBlank()) {
                    saveScore(playerName, score, time, lives, difficulty)
                } else {
                    Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar") { _, _ ->
                Toast.makeText(this, "Puntaje no guardado", Toast.LENGTH_SHORT).show()
            }
            .create()

        // Mostrar el diálogo
        dialog.show()
    }


    private fun saveScore(playerName: String, score: Int, time: Int, lives: Int, difficulty: Int) {
        val result = dbHelper.insertScore(playerName, score, time, lives, difficulty)
        if (result != -1L) {
            Toast.makeText(this, "Puntaje guardado exitosamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al guardar el puntaje", Toast.LENGTH_SHORT).show()
        }
    }

}
