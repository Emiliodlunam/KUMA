package com.example.proyecto_kuma.memo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_kuma.R

class ScoreListActivity : AppCompatActivity() {

    private lateinit var scoreListTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_list)

        scoreListTextView = findViewById(R.id.scoreListTextView)

        // Obtener y mostrar los puntajes
        val scores = getScoresFromDatabase()
        displayScores(scores)
    }

    private fun getScoresFromDatabase(): List<String> {
        val dbHelper = GameDatabaseHelper(this)
        val db = dbHelper.readableDatabase

        val query = """
            SELECT player_name, time, attempts, difficulty
            FROM game_results
            ORDER BY time ASC, attempts ASC
        """
        val cursor = db.rawQuery(query, null)

        val scores = mutableListOf<String>()
        while (cursor.moveToNext()) {
            val playerName = cursor.getString(cursor.getColumnIndexOrThrow("player_name"))
            val time = cursor.getLong(cursor.getColumnIndexOrThrow("time"))
            val attempts = cursor.getInt(cursor.getColumnIndexOrThrow("attempts"))
            val difficulty = cursor.getString(cursor.getColumnIndexOrThrow("difficulty"))
            scores.add("Jugador: $playerName | Tiempo: $time s | Intentos: $attempts | Dificultad: $difficulty")
        }
        cursor.close()
        db.close()
        return scores
    }

    private fun displayScores(scores: List<String>) {
        if (scores.isEmpty()) {
            scoreListTextView.text = "No hay puntajes disponibles."
        } else {
            scoreListTextView.text = scores.joinToString("\n\n")
        }
    }
}
