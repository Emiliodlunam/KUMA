package com.example.proyecto_kuma.maze

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


class MazeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private var maze: Array<Array<Int>> = arrayOf()
    private var playerX = 0
    private var playerY = 0
    private var exitX = 0
    private var exitY = 0
    private var onPlayerReachedEnd: (() -> Unit)? = null
    private var onPlayerCollision: (() -> Unit)? = null

    private val wallPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
    }
    private val pathPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }
    private val playerPaint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
    }
    private val exitPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    private var cellSize = 0f


    fun initMaze(size: Int) {
        maze = MazeGenerator.generateMaze(size)
        playerX = 0
        playerY = 0
        exitX = size - 1
        exitY = size - 1
        invalidate()
    }

    fun setOnPlayerReachedEndListener(listener: () -> Unit) {
        onPlayerReachedEnd = listener
    }

    fun setOnPlayerCollisionListener(listener: () -> Unit) {
        onPlayerCollision = listener
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        cellSize = width.toFloat() / maze.size

        // Dibujar el laberinto
        for (y in maze.indices) {
            for (x in maze[y].indices) {
                val left = x * cellSize
                val top = y * cellSize
                val right = left + cellSize
                val bottom = top + cellSize
                if (maze[y][x] == 1) { // 1 representa una pared
                    canvas.drawRect(left, top, right, bottom, wallPaint)
                } else { // 0 representa un camino
                    canvas.drawRect(left, top, right, bottom, pathPaint)
                }
            }
        }

        // Dibujar jugador
        val playerLeft = playerX * cellSize
        val playerTop = playerY * cellSize
        canvas.drawRect(playerLeft, playerTop, playerLeft + cellSize, playerTop + cellSize, playerPaint)

        // Dibujar salida
        val exitLeft = exitX * cellSize
        val exitTop = exitY * cellSize
        canvas.drawRect(exitLeft, exitTop, exitLeft + cellSize, exitTop + cellSize, exitPaint)
    }

    fun movePlayer(dx: Int, dy: Int) {
        val newX = playerX + dx
        val newY = playerY + dy

        // Verificar si la nueva posición está dentro del laberinto
        if (newX in maze[0].indices && newY in maze.indices) {
            if (maze[newY][newX] == 0) { // Es un camino
                playerX = newX
                playerY = newY
                invalidate()

                // Si el jugador alcanza la salida
                if (playerX == exitX && playerY == exitY) {
                    onPlayerReachedEnd?.invoke()
                }
            } else if (maze[newY][newX] == 1) { // Es una pared
                onPlayerCollision?.invoke()
            }
        }
    }
}
