package com.example.proyecto_kuma.memo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_kuma.R

class InstructionsActivityMemo : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo_instructions)

        val instructionsTextView: TextView = findViewById(R.id.instructionsTextView)
        val backButton: Button = findViewById(R.id.backButton)

        // Instrucciones del juego
        val instructions = """
            Instrucciones del juego:
            
            1. Selecciona la dificultad (Fácil, Medio o Difícil).
            2. Memoriza la posición de las cartas para encontrar los pares iguales.
            3. Toca una carta para voltearla y luego otra para buscar un par.
            4. Si las cartas coinciden, permanecerán visibles; si no, se voltearán nuevamente.
            5. Completa el tablero encontrando todos los pares en el menor tiempo posible y con la menor cantidad de intentos.
            
            ¡Buena suerte y diviértete!
        """.trimIndent()

        instructionsTextView.text = instructions

        // Botón para regresar a la pantalla principal
        backButton.setOnClickListener {
            finish() // Cierra la actividad y regresa a la anterior
        }

        // Boton para ir a la actividad del juego
        val playButton: Button = findViewById(R.id.memoGame)
        playButton.setOnClickListener {
            val intent = Intent(this, LevelSelectionActivity::class.java)
            startActivity(intent)
        }
    }
}
