package solution

import java.io.File
import java.math.BigInteger

class Day6 {
    private val inputFile = File(this.javaClass.classLoader.getResource("day6.txt").path)

    fun part1(): BigInteger {
        val rows = mutableListOf<List<String>>()
        inputFile.forEachLine { line ->
            rows.add(line.split(" ").filter { it.isNotBlank() })
        }

        val operators = rows[rows.size - 1]
        rows.removeAt(rows.size - 1)

        val results = mutableListOf<BigInteger>()
        for (x in 0 until rows[0].size) {
            val operator = operators[x]
            results.add(if (operator == "+") BigInteger("0") else BigInteger("1"))
        }

        for (y in rows.size - 1 downTo 0) {
            for (x in 0 until rows[0].size) {
                results[x] = when (operators[x]) {
                    "+" -> results[x] + BigInteger(rows[y][x])
                    else -> results[x] * BigInteger(rows[y][x])
                }
            }
        }

        return results.fold(BigInteger("0")) { acc, i -> acc + i }
    }

    fun part2(): BigInteger {
        val rows = mutableListOf<String>()
        var maxX = 0
        inputFile.forEachLine { line ->
            rows.add(line)
            maxX = Math.max(maxX, line.length)
        }

        val operators = rows[rows.size - 1].split(" ").filter { it.isNotBlank() }
        rows.removeAt(rows.size - 1)

        val results = mutableListOf<BigInteger>()
        for (x in 0 until operators.size) {
            val operator = operators[x]
            results.add(if (operator == "+") BigInteger("0") else BigInteger("1"))
        }

        val numbers = Array<MutableList<String>>(operators.size) { mutableListOf() }
        var groupIdx = 0
        for (x in 0 until maxX) {
            var number = ""
            for (y in 0 until rows.size) {
                if (x >= rows[y].length || rows[y][x] == ' ') continue
                number += rows[y][x]
            }
            if (number.isEmpty()) {
                groupIdx++
                continue
            }

            if (numbers[groupIdx] == null) {
                numbers[groupIdx] = mutableListOf(number)
            } else numbers[groupIdx].add(number)
        }

        for (i in 0 until operators.size) {
            results[i] = numbers[i].fold(results[i]) { ac, element ->
                when (operators[i]) {
                    "+" -> ac + BigInteger(element)
                    else -> ac * BigInteger(element)
                }
            }
        }

        return results.fold(BigInteger("0")) { acc, i -> acc + i }
    }
}
