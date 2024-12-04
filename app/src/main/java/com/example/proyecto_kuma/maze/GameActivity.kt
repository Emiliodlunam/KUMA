package com.example.proyecto_kuma.maze

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_kuma.R

class GameActivity : AppCompatActivity() {

    private lateinit var mazeView: MazeView
    private lateinit var timerTextView: TextView
    private lateinit var livesTextView: TextView

    private var lives = 3
    private var time = 0
    private lateinit var handler: Handler

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_maze)

        mazeView = findViewById(R.id.mazeView)
        timerTextView = findViewById(R.id.timerTextView)
        livesTextView = findViewById(R.id.livesTextView)

        // Inicializar el tamaño del laberinto basado en la dificultad seleccionada
        val mazeSize = intent.getIntExtra("MAZE_SIZE", 5)
        mazeView.initMaze(mazeSize)

        // Configurar el temporizador
        handler = Handler(Looper.getMainLooper())
        startTimer()

        // Actualizar el texto de vidas al inicio
        updateLivesText()

        // Manejar botones de movimiento
        findViewById<Button>(R.id.buttonUp).setOnClickListener { movePlayer(0, -1) }
        findViewById<Button>(R.id.buttonDown).setOnClickListener { movePlayer(0, 1) }
        findViewById<Button>(R.id.buttonLeft).setOnClickListener { movePlayer(-1, 0) }
        findViewById<Button>(R.id.buttonRight).setOnClickListener { movePlayer(1, 0) }

        // Evento cuando el jugador llega al final
        mazeView.setOnPlayerReachedEndListener {
            gameCompleted()
        }

        // Evento cuando el jugador colisiona con una pared
        mazeView.setOnPlayerCollisionListener {
            onCollision()
        }
    }

    private fun movePlayer(dx: Int, dy: Int) {
        mazeView.movePlayer(dx, dy)
    }

    private fun startTimer() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                time++
                timerTextView.text = "Tiempo: $time"
                handler.postDelayed(this, 1000)
            }
        }, 1000)
    }



    private fun updateLivesText() {
        livesTextView.text = "Vidas: $lives"
    }

    private fun onCollision() {
        lives--
        updateLivesText()

        if (lives <= 0) {
            handler.removeCallbacksAndMessages(null)
            showGameOverScreen()
        }
    }

    private fun gameCompleted() {
        handler.removeCallbacksAndMessages(null)
        showGameCompletedScreen()
    }


    private fun showGameOverScreen() {
        val intent = Intent(this, GameOverActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showGameCompletedScreen() {
        val intent = Intent(this, GameCompletedActivity::class.java)
        intent.putExtra("TIME", time)
        intent.putExtra("LIVES", lives)
        intent.putExtra("DIFFICULTY", intent.getIntExtra("MAZE_SIZE", 1)) // Dificultad basada en tamaño
        startActivity(intent)
        finish()
    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

}
