package com.example.proyecto_kuma.memo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_kuma.R

class ActivityMemo : AppCompatActivity() {
    private val imageResources = arrayOf(
        R.drawable.leon, R.drawable.mono, R.drawable.rino,
        R.drawable.elefante, R.drawable.cocodrilo, R.drawable.jirafa,
        R.drawable.perico, R.drawable.zebra
    )

    private lateinit var cards: Array<Array<Card?>>
    private lateinit var shuffledImages: IntArray
    private var firstCard: Card? = null
    private var secondCard: Card? = null
    private var isBusy = false
    private var attempts = 0
    private var startTime: Long = 0

    private lateinit var timerTextView: TextView
    private lateinit var attemptsTextView: TextView
    private lateinit var gridLayout: GridLayout

    private val handler = Handler(Looper.getMainLooper())
    private var timerRunning = false

    data class Card(val imageView: ImageView, val imageResId: Int, var isFlipped: Boolean = false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.memo_main)

        gridLayout = findViewById(R.id.gridLayout)
        timerTextView = findViewById(R.id.timerTextView)
        attemptsTextView = findViewById(R.id.attemptsTextView)

        val rows = intent.getIntExtra("rows", 4)
        val columns = intent.getIntExtra("columns", 4)

        startGame(rows, columns)
    }

    private fun startGame(rows: Int, columns: Int) {
        gridLayout.removeAllViews()
        gridLayout.columnCount = columns
        gridLayout.rowCount = rows

        cards = Array(rows) { arrayOfNulls(columns) }
        shuffledImages = shuffleImages(rows * columns)

        attempts = 0
        startTime = System.currentTimeMillis()
        timerRunning = true
        startTimer()

        populateGrid(rows, columns)
        updateUI()
    }

    private fun shuffleImages(totalCards: Int): IntArray {
        val numPairs = totalCards / 2
        val images = IntArray(totalCards)
        for (i in 0 until numPairs) {
            images[2 * i] = imageResources[i % imageResources.size]
            images[2 * i + 1] = imageResources[i % imageResources.size]
        }
        images.shuffle()
        return images
    }

    private fun populateGrid(rows: Int, columns: Int) {
        val inflater = LayoutInflater.from(this)
        var imageIndex = 0

        for (row in 0 until rows) {
            for (col in 0 until columns) {
                val cardView = inflater.inflate(R.layout.card_view, gridLayout, false)
                val imageView = cardView.findViewById<ImageView>(R.id.imageCard)
                val imageResId = shuffledImages[imageIndex++]

                val card = Card(imageView, imageResId)
                cards[row][col] = card

                imageView.setImageResource(R.drawable.sea)
                imageView.setOnClickListener {
                    if (!isBusy && !card.isFlipped) {
                        flipCard(card)
                    }
                }
                gridLayout.addView(cardView)
            }
        }
    }

    private fun flipCard(card: Card) {
        card.isFlipped = true
        animateFlip(card.imageView) {
            card.imageView.setImageResource(card.imageResId)
        }

        if (firstCard == null) {
            firstCard = card
        } else if (secondCard == null) {
            secondCard = card
            isBusy = true
            attempts++
            updateUI()

            if (firstCard!!.imageResId == secondCard!!.imageResId) {
                resetSelection()
                checkGameCompletion()
            } else {
                handler.postDelayed({
                    firstCard?.let {
                        it.isFlipped = false
                        animateFlip(it.imageView) { it.imageView.setImageResource(R.drawable.sea) }
                    }
                    secondCard?.let {
                        it.isFlipped = false
                        animateFlip(it.imageView) { it.imageView.setImageResource(R.drawable.sea) }
                    }
                    resetSelection()
                }, 1000)
            }
        }
    }

    private fun animateFlip(imageView: ImageView, onAnimationEnd: () -> Unit) {
        val flipOut = AnimationUtils.loadAnimation(this, R.anim.flip_out)
        val flipIn = AnimationUtils.loadAnimation(this, R.anim.flip_in)

        flipOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                imageView.post {
                    onAnimationEnd()
                    imageView.startAnimation(flipIn)
                }
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

        imageView.startAnimation(flipOut)
    }

    private fun resetSelection() {
        firstCard = null
        secondCard = null
        isBusy = false
    }

    private fun checkGameCompletion() {
        if (cards.flatten().all { it?.isFlipped == true }) {
            timerRunning = false
            val elapsedTime = (System.currentTimeMillis() - startTime) / 1000

            val difficulty = when {
                cards.size == 2 && cards[0].size == 3 -> "Fácil"
                cards.size == 4 && cards[0].size == 3 -> "Medio"
                cards.size == 4 && cards[0].size == 4 -> "Difícil"
                else -> "Personalizado"
            }

            val intent = Intent(this, GameOverActivity::class.java).apply {

                putExtra("TIME", elapsedTime)
                putExtra("ATTEMPTS", attempts)
                putExtra("DIFFICULTY", difficulty)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun updateUI() {
        val elapsedTime = (System.currentTimeMillis() - startTime) / 1000
        timerTextView.text = "Tiempo: ${elapsedTime}s"
        attemptsTextView.text = "Intentos: $attempts"
    }

    private fun startTimer() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (timerRunning) {
                    updateUI()
                    handler.postDelayed(this, 1000)
                }
            }
        }, 1000)
    }
}
