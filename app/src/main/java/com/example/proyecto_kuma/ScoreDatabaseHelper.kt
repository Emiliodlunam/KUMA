package com.example.proyecto_kuma

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ScoreDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "game_scores.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_SCORES = "scores"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "player_name"
        const val COLUMN_SCORE = "score"
        const val COLUMN_TIME = "time"
        const val COLUMN_LIVES = "lives"
        const val COLUMN_DIFFICULTY = "difficulty"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_SCORES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_SCORE INTEGER,
                $COLUMN_TIME INTEGER,
                $COLUMN_LIVES INTEGER,
                $COLUMN_DIFFICULTY INTEGER
            )
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SCORES")
        onCreate(db)
    }

    // Insertar un puntaje
    fun insertScore(playerName: String, score: Int, time: Int, lives: Int, difficulty: Int): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, playerName)
            put(COLUMN_SCORE, score)
            put(COLUMN_TIME, time)
            put(COLUMN_LIVES, lives)
            put(COLUMN_DIFFICULTY, difficulty)
        }
        return db.insert(TABLE_SCORES, null, values)
    }

    // Obtener los mejores puntajes
    fun getTopScores(limit: Int = 10): List<Score> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_SCORES,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_SCORE DESC",
            limit.toString()
        )
        val scores = mutableListOf<Score>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val score = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORE))
            val time = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TIME))
            val lives = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LIVES))
            val difficulty = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DIFFICULTY))
            scores.add(Score(id, name, score, time, lives, difficulty))
        }
        cursor.close()
        return scores
    }
}

// Clase para manejar los datos del puntaje
data class Score(
    val id: Int,
    val playerName: String,
    val score: Int,
    val time: Int,
    val lives: Int,
    val difficulty: Int
)
