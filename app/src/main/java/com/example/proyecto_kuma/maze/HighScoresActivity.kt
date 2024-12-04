package com.example.proyecto_kuma.maze

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_kuma.R
import com.example.proyecto_kuma.ScoreAdapter
import com.example.proyecto_kuma.ScoreDatabaseHelper

class HighScoresActivity : AppCompatActivity() {

    private lateinit var dbHelper: ScoreDatabaseHelper
    private lateinit var scoreAdapter: ScoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_scores)

        dbHelper = ScoreDatabaseHelper(this)

        val highScoresRecyclerView = findViewById<RecyclerView>(R.id.highScoresRecyclerView)
        val scores = dbHelper.getTopScores()

        // Configura el RecyclerView
        scoreAdapter = ScoreAdapter(scores)
        highScoresRecyclerView.layoutManager = LinearLayoutManager(this)
        highScoresRecyclerView.adapter = scoreAdapter
    }
}
