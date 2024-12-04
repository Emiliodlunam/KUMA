package com.example.proyecto_kuma

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScoreAdapter(private val scores: List<Score>) : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    class ScoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val playerName: TextView = view.findViewById(R.id.playerName)
        val score: TextView = view.findViewById(R.id.score)
        val time: TextView = view.findViewById(R.id.time)
        val lives: TextView = view.findViewById(R.id.lives)
        val difficulty: TextView = view.findViewById(R.id.difficulty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_score, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = scores[position]
        holder.playerName.text = score.playerName
        holder.score.text = score.score.toString()
        holder.time.text = "${score.time}s"
        holder.lives.text = score.lives.toString()
        holder.difficulty.text = score.difficulty.toString()
    }

    override fun getItemCount(): Int = scores.size
}
