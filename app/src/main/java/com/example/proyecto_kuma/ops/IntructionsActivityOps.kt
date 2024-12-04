package com.example.proyecto_kuma.ops

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_kuma.R

class IntructionsActivityOps : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_operations_instructions)

        val instructionsTextView: TextView = findViewById(R.id.instructionsTextView)
        val backButton: Button = findViewById(R.id.backButton)
        val startGameButton: Button = findViewById(R.id.startGameButton)

        // Instrucciones específicas para el juego de laberinto
        val instructions = """
           Instrucciones del juego de Operaciones Básicas:

            1. Se te mostrará una operación matemática incompleta (suma, resta, multiplicación o división).
            2. Selecciona la respuesta correcta entre cuatro opciones disponibles.
            3. Si tu respuesta es correcta, recibirás una felicitación y aparecerá una nueva operación.
            4. Si tu respuesta es incorrecta, podrás intentarlo de nuevo.

            ¡Buena suerte resolviendo las operaciones!
        """.trimIndent()

        instructionsTextView.text = instructions

        // Botón para regresar al menú principal
        backButton.setOnClickListener {
            finish() // Cierra la actividad y regresa a la anterior
        }

        // Botón para iniciar el juego
        startGameButton.setOnClickListener {
            val intent = Intent(this, ActivityOps::class.java)
            startActivity(intent)
        }
    }
}
