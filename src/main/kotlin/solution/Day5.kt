package solution

import java.io.File

class Day5 {
    private val inputFile = File(this.javaClass.classLoader.getResource("day5.txt").path)

    private class PointLong(var x: Long, var y: Long) {
        fun isEmpty(): Boolean {
            return x == 0L && y == 0L
        }

        fun setToEmpty() {
            x = 0L
            y = 0L
        }
    }

    fun part1(): Long {
        val ranges = mutableListOf<PointLong>()
        val inputs = mutableListOf<Long>()

        var startInput = false
        inputFile.forEachLine { line ->
            if (line == "") {
                startInput = true
            } else if (startInput) {
                inputs.add(line.toLong())
            } else {
                val split = line.split("-")
                ranges.add(PointLong(split[0].toLong(), split[1].toLong()))
            }
        }

        var result = 0L
        for (i in inputs) {
            for (range in ranges) {
                if (i >= range.x && i <= range.y) {
                    result++
                    break
                }
            }
        }
        return result
    }

    fun part2(): Long {
        val ranges = mutableListOf<PointLong>()
        inputFile.bufferedReader().useLines { lines ->
            for (line in lines) {
                if (line == "") {
                    break
                }
                val split = line.split("-")
                ranges.add(PointLong(split[0].toLong(), split[1].toLong()))
            }
        }
        ranges.sortBy { it.x }

        for (i in 0 until ranges.size - 1) {
            check(ranges[i], ranges[i + 1])
        }

        var result = 0L
        for (range in ranges.filter { !it.isEmpty() }) {
            result += (range.y - range.x + 1)
        }

        return result
    }

    private fun check(a: PointLong, b: PointLong) {
        if (a.y >= b.x) {
            b.y = a.y.coerceAtLeast(b.y)
            b.x = a.x.coerceAtMost(b.x)
            a.setToEmpty()
        }
    }
}
