package com.example.proyecto_kuma.maze

object MazeGenerator {
    fun generateMaze(size: Int): Array<Array<Int>> {
        val maze = Array(size) { Array(size) { 1 } }

        // Crear un camino válido del inicio a la salida
        var x = 0
        var y = 0
        maze[y][x] = 0

        while (x < size - 1 || y < size - 1) {
            if (x < size - 1 && (y == size - 1 || Math.random() > 0.5)) {
                x++
            } else {
                y++
            }
            maze[y][x] = 0
        }

        // Añadir caminos adicionales aleatorios
        for (i in 0 until size * size / 4) {
            val randomX = (0 until size).random()
            val randomY = (0 until size).random()
            maze[randomY][randomX] = 0
        }

        return maze
    }
}
