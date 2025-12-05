package solution

import java.io.File

class Day3 {
    private val inputFile = File(this.javaClass.classLoader.getResource("day3.txt").path)

    fun part1(): Long {
        var result = 0L
        inputFile.forEachLine { line ->
//            result += max2digits(line)
            result += maxNDigits(line, 2)
        }
        return result
    }

    fun part2(): Long {
        var result = 0L
        inputFile.forEachLine { line ->
            result += maxNDigits(line, 12)
        }
        return result
    }

    fun maxNDigits(line: String, k: Int = 12): Long {
        var list = mutableListOf<Int>()
        var startIdx = 0
        for (kIdx in k - 1 downTo 0) {
            var maxIdx = startIdx
            for (i in startIdx until line.length - kIdx) {
                if (line[i] > line[maxIdx]) {
                    maxIdx = i
                }
            }
            list.add(line[maxIdx] - '0')
            startIdx = maxIdx + 1
        }

        return list.fold(0L) { acc, i ->
            acc * 10 + i
        }
    }

    private fun max2digits(line: String): Int {
        var max = 0
        for (i in 0 until line.length) {
            for (j in i + 1 until line.length) {
                val n = "${line[i]}${line[j]}".toInt()
                if (n > max) max = n
            }
        }
        return max
    }
}
