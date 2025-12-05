package solution

import java.io.File

class Day4 {
    private val inputFile = File(this.javaClass.classLoader.getResource("day4.txt").path)
    private val dirX = listOf(1, 1, -1, -1, 1, -1, 0, 0)
    private val dirY = listOf(1, -1, 1, -1, 0, 0, 1, -1)

    fun part1(): Int {
        val map = mutableListOf<CharArray>()
        inputFile.forEachLine { line ->
            map.add(line.toCharArray())
        }

        var result = 0
        for (y in 0 until map.size) {
            for (x in 0 until map[y].size) {
                if (map[y][x] != '@') continue
                if (accessible(x, y, map[y].size - 1, map.size - 1, map))
                    result++
            }
        }
        return result
    }

    fun part2(): Int {
        val map = mutableListOf<CharArray>()
        inputFile.forEachLine { line ->
            map.add(line.toCharArray())
        }

        var result = 0
        while (true) {
            var localResult = 0
            for (y in 0 until map.size) {
                for (x in 0 until map[y].size) {
                    if (map[y][x] != '@') continue
                    if (accessible(x, y, map[y].size - 1, map.size - 1, map)) {
                        localResult++
                        map[y][x] = 'X'
                    }
                }
            }
            if (localResult == 0) break
            result += localResult
        }
        return result
    }

    private fun accessible(x: Int, y: Int, maxX: Int, maxY: Int, map: List<CharArray>): Boolean {
        var count = 0
        for (i in dirX.indices) {
            val nextX = x + dirX[i]
            val nextY = y + dirY[i]

            if (nextX < 0 || nextX > maxX || nextY < 0 || nextY > maxY) continue

            if (map[nextY][nextX] == '@') {
                count++
                if (count >= 4) break
            }
        }
        return count < 4
    }
}
