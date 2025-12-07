package solution

import java.awt.Point
import java.io.File

class Day7 {
    private val inputFile = File(this.javaClass.classLoader.getResource("day7.txt").path)

    fun part1(): Int {
        val rows = mutableListOf<CharArray>()
        inputFile.forEachLine { rows.add(it.toCharArray()) }

        var initialX = rows[0].indexOf('S')
        val list = mutableListOf(Point(initialX, 0))
        val visited = mutableSetOf<Point>()

        var result = 0
        while (list.isNotEmpty()) {
            val curr = list[0]
            list.removeAt(0)

            val splitPoint = move(curr, rows)
            splitPoint?.let { sp ->
                if (visited.add(Point(sp.x, sp.y))) {
                    result++
                    list.add(Point(sp.x + 1, sp.y))
                    list.add(Point(sp.x - 1, sp.y))
//                    rows[sp.y][sp.x + 1] = '|'
//                    rows[sp.y][sp.x - 1] = '|'
                }
            }
        }
        return result
    }

    private val cache = mutableMapOf<Point, Long>()
    fun part2(): Long {
        val rows = mutableListOf<CharArray>()
        inputFile.forEachLine { rows.add(it.toCharArray()) }

        var initialX = rows[0].indexOf('S')
        return solvePart2(Point(initialX, 0), rows)
    }

    private fun solvePart2(curr: Point, rows: List<CharArray>): Long {
        val cacheValue = cache[curr]
        if (cacheValue != null)
            return cacheValue

        val sp = move(curr, rows) ?: return 1
        val result = if (sp != null) {
            listOf(-1, 1).map { solvePart2(Point(sp.x + it, sp.y), rows) }.sum()
        } else 1
        cache[curr] = result
        return result
    }

    private fun move(point: Point, map: List<CharArray>): Point? {
        var currY = point.y
        while (currY + 1 < map.size) {
            val nextY = currY + 1
            if (map[nextY][point.x] == '^') {
                return Point(point.x, nextY)
            }
            currY++
        }
        return null
    }

    // use this method to visualize part 1
    private fun move1(point: Point, map: List<CharArray>): Point? {
        var currY = point.y
        while (currY + 1 < map.size) {
            val nextY = currY + 1
            when (map[nextY][point.x]) {
                '.' -> map[nextY][point.x] = '|'
                '^' -> return Point(point.x, nextY)
            }
            currY++
        }
        return null
    }
}
