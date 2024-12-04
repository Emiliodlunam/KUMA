package com.example.proyecto_kuma.maze

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_kuma.R

class InstructionsActivityMaze : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maze_instructions)

        val instructionsTextView: TextView = findViewById(R.id.instructionsTextView)
        val backButton: Button = findViewById(R.id.backButton)
        val startGameButton: Button = findViewById(R.id.startGameButton)

        // Instrucciones específicas para el juego de laberinto
        val instructions = """
            Instrucciones del juego de laberinto:

            1. Usa los sensores de movimiento para mover la bola a través del laberinto.
            2. Evita los obstáculos como paredes y trampas (agujeros negros, asteroides).
            3. Llega al objetivo (estación espacial o portal) para completar el nivel.
            4. Cada nivel tiene un límite de tiempo; muévete rápido pero con cuidado.
            5. ¡Recoge puntos extras si encuentras objetos especiales!

            Controles:
            - Inclina tu dispositivo en la dirección en la que deseas mover la bola.
            - Ajusta tu precisión para evitar perder tiempo chocando contra los bordes.

            ¡Buena suerte y diviértete!
        """.trimIndent()

        instructionsTextView.text = instructions

        // Botón para regresar al menú principal
        backButton.setOnClickListener {
            finish() // Cierra la actividad y regresa a la anterior
        }

        // Botón para iniciar el juego
        startGameButton.setOnClickListener {
            val intent = Intent(this, ActivityMaze::class.java)
            startActivity(intent)
        }
    }
}
