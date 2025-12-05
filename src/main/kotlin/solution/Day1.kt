package solution

import java.io.File

class Day1 {
    private val inputFile = File(this.javaClass.classLoader.getResource("day1.txt").path)

    fun part1(): Int {
        var pos = 50
        var result = 0
        inputFile.forEachLine { line ->
            val rotationType = line[0]
            val rotation = line.substring(1).toInt()
            pos += if (rotationType == 'R') rotation else -rotation
            pos %= 100
            if (pos < 0) {
                pos += 100
            }
            if (pos == 0) {
                result++
            }
        }
        return result
    }

    fun part2(): Int {
        var pos = 50
        var result = 0
        inputFile.forEachLine { line ->
            val rotationType = line[0]
            val rotation = line.substring(1).toInt()
            for (i in 0 until rotation) {
                pos += if (rotationType == 'R') 1 else -1
                pos %= 100
                if (pos < 0) {
                    pos += 100
                }
                if (pos == 0) {
                    result++
                }
            }
        }
        return result
    }
}
