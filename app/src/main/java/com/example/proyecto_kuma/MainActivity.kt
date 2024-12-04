package com.example.proyecto_kuma


import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.proyecto_kuma.maze.InstructionsActivityMaze
import com.example.proyecto_kuma.memo.InstructionsActivityMemo
import com.example.proyecto_kuma.ops.IntructionsActivityOps

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a las tarjetas
        val cardActivity1: CardView = findViewById(R.id.cardActivity1)
        val cardActivity2: CardView = findViewById(R.id.cardActivity2)
        val cardActivity3: CardView = findViewById(R.id.cardActivity3)
        val creditsText: TextView = findViewById(R.id.creditsText)

        // Evento para la primera tarjeta
        cardActivity1.setOnClickListener {
            val intent = Intent(this, InstructionsActivityMemo::class.java)
            startActivity(intent)
        }

        // Evento para la segunda tarjeta
        cardActivity2.setOnClickListener {
            val intent = Intent(this, InstructionsActivityMaze::class.java)
            startActivity(intent)
        }

        // Evento para la tercera tarjeta
        cardActivity3.setOnClickListener {
            val intent = Intent(this, IntructionsActivityOps::class.java)
            startActivity(intent)
        }

        // Evento para la pagina de creditos
        creditsText.setOnClickListener {
            val intent = Intent(this, ActivityCre::class.java)
            startActivity(intent)
        }
    }
}
