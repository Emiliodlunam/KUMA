package com.example.proyecto_kuma.ops

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_kuma.R
import kotlin.random.Random

class ActivityOps : AppCompatActivity() {

    private lateinit var operationTextView: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var submitButton: Button
    private lateinit var feedbackTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var counterTextView: TextView

    private var correctAnswer: Int = 0
    private var score: Int = 0
    private var correctAnswers: Int = 0
    private var operationCount: Int = 0
    private val maxOperations = 7
    private var startTime: Long = 0L
    private var totalElapsedTime: Long = 0L
    private val bonusTimeThreshold = 5000 // 5 segundos en milisegundos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_operations)

        // Vincular vistas desde el XML
        operationTextView = findViewById(R.id.operationTextView)
        radioGroup = findViewById(R.id.radioGroup)
        submitButton = findViewById(R.id.submitButton)
        feedbackTextView = findViewById(R.id.feedbackTextView)
        scoreTextView = findViewById(R.id.scoreTextView)
        counterTextView = findViewById(R.id.counterTextView)

        // Configurar vistas iniciales
        updateScoreTextView()
        updateCounterTextView()

        // Generar la primera operación
        generateOperation()

        // Configurar botón de validación
        submitButton.setOnClickListener {
            validateAnswer()
        }
    }

    private fun generateOperation() {
        // Limpiar selección previa y registrar tiempo de inicio
        radioGroup.clearCheck()
        feedbackTextView.text = ""
        startTime = System.currentTimeMillis()

        // Generar números y operación
        val num1 = Random.nextInt(1, 10)
        val num2 = Random.nextInt(1, 10)
        val operationType = Random.nextInt(4) // 0: suma, 1: resta, 2: multiplicación, 3: división

        val operationSymbol: String
        val possibleAnswers = mutableListOf<Int>()

        when (operationType) {
            0 -> { // Suma
                operationSymbol = "+"
                correctAnswer = num1 + num2
            }
            1 -> { // Resta
                operationSymbol = "-"
                correctAnswer = num1 - num2
            }
            2 -> { // Multiplicación
                operationSymbol = "×"
                correctAnswer = num1 * num2
            }
            3 -> { // División
                operationSymbol = "÷"
                if (num2 == 0 || num1 % num2 != 0) { // Evitar divisiones inválidas
                    generateOperation() // Intentar otra operación
                    return
                }
                correctAnswer = num1 / num2
            }
            else -> throw IllegalStateException("Operación no válida")
        }

        // Mostrar operación
        operationTextView.text = "$num1 $operationSymbol $num2 = ?"

        // Generar opciones
        possibleAnswers.add(correctAnswer)
        while (possibleAnswers.size < 4) {
            val randomAnswer = Random.nextInt(correctAnswer - 5, correctAnswer + 5)
            if (randomAnswer !in possibleAnswers && randomAnswer >= 0) {
                possibleAnswers.add(randomAnswer)
            }
        }

        // Mezclar y asignar opciones a los RadioButtons
        possibleAnswers.shuffle()
        for (i in 0 until radioGroup.childCount) {
            val radioButton = radioGroup.getChildAt(i) as RadioButton
            radioButton.text = possibleAnswers[i].toString()
        }
    }

    private fun validateAnswer() {
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId

        if (selectedRadioButtonId != -1) {
            val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
            val selectedAnswer = selectedRadioButton.text.toString().toInt()
            val elapsedTime = System.currentTimeMillis() - startTime

            if (selectedAnswer == correctAnswer) {
                correctAnswers++
                totalElapsedTime += elapsedTime
                feedbackTextView.text = "¡Correcto!"
                feedbackTextView.setTextColor(resources.getColor(android.R.color.holo_green_dark))

                // Incrementar puntaje y verificar bonificación
                score += 10
                if (elapsedTime <= bonusTimeThreshold) {
                    score += 5
                    Toast.makeText(this, "¡Bonificación por rapidez! +5 puntos", Toast.LENGTH_SHORT).show()
                }
                updateScoreTextView()
            } else {
                feedbackTextView.text = "Respuesta incorrecta."
                feedbackTextView.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            }

            operationCount++
            updateCounterTextView()

            if (operationCount < maxOperations) {
                generateOperation()
            } else {
                endGame()
            }
        } else {
            Toast.makeText(this, "Por favor, selecciona una opción", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateScoreTextView() {
        scoreTextView.text = "Puntaje: $score"
    }

    private fun updateCounterTextView() {
        counterTextView.text = "Operación ${operationCount + 1} de $maxOperations"
    }

    private fun endGame() {
        // Ir a la actividad de resultados
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("SCORE", score)
            putExtra("CORRECT_ANSWERS", correctAnswers)
        }
        startActivity(intent)
        finish()
    }
}
