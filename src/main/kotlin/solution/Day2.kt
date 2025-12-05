package solution

import java.io.File

class Day2 {
    private val inputFile = File(this.javaClass.classLoader.getResource("day2.txt").path)

    fun part1(): Long {
        var result = 0L
        inputFile.forEachLine { line ->
            val ranges = line.split(",")
            for (range in ranges) {
                val split = range.split("-")
                val from = split[0].toLong()
                val to = split[1].toLong()
                for (i in from..to) {
                    if (isSilly(i)) {
                        result += i
                    }
                }
            }
        }
        return result
    }

    private fun isSilly(n: Long): Boolean {
        val str = n.toString()
        if (str.length % 2 == 1) return false
        val firstWord = str.substring(0, str.length / 2)
        val secondWord = str.substring(str.length / 2)
        return firstWord == secondWord
    }

    fun part2(): Long {
        var result = 0L
        inputFile.forEachLine { line ->
            val ranges = line.split(",")
            for (range in ranges) {
                val split = range.split("-")
                val from = split[0].toLong()
                val to = split[1].toLong()
                for (i in from..to) {
                    if (isSillyPart2(i)) {
                        result += i
                    }
                }
            }
        }
        return result
    }

    private fun isSillyPart2(n: Long): Boolean {
        val str = n.toString()
        for (length in 1..str.length / 2) {
            if (str.length % length != 0) continue

            var isBreak = false
            for (i in 0 until (str.length / length) - 1) {
                val firstWord = str.substring(i * length, i * length + length)
                val secondWord = str.substring((i + 1) * length, (i + 1) * length + length)

                if (firstWord != secondWord) {
                    isBreak = true
                    break
                }
            }
            if (!isBreak) return true
        }
        return false
    }
}
