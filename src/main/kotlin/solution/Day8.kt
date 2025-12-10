package solution

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sqrt

class Day8 {
    private val inputFile = File(this.javaClass.classLoader.getResource("day8.txt").path)

    class Edge(val from: Int, val to: Int, val distance: Double)

    fun part1(): Int {
        val points = mutableListOf<List<Int>>()
        inputFile.forEachLine {
            points.add(it.split(",").map { it.toInt() })
        }

        val edges = mutableListOf<Edge>()
        val graph = Array(points.size) { Array(points.size) { 0.0 } }

        for (i in 0 until points.size) {
            for (j in i + 1 until points.size) {
                graph[i][j] = calculateDistance(points[i], points[j])
                graph[j][i] = graph[i][j]
                edges.add(Edge(i, j, graph[i][j]))
            }
        }
        edges.sortBy { it.distance }

        val parents = IntArray(points.size) { -1 }
        var connectionCount = 0
        for (edge in edges) {
            val parentFrom = findParent(edge.from, parents)
            val parentTo = findParent(edge.to, parents)
            connectionCount++
            if (connectionCount == 1000) break
            if (parentFrom == parentTo) continue
            parents[parentFrom] = parentTo
        }

        val circuits = mutableMapOf<Int, Int>()
        for (i in 0 until points.size) {
            val parent = findParent(i, parents)
            circuits[parent] = (circuits[parent] ?: 0) + 1
        }

        return circuits.map { it.value }.sortedDescending().take(3).fold(1, { acc, i -> acc * i })
    }

    fun part2(): Long {
        val points = mutableListOf<List<Int>>()
        inputFile.forEachLine {
            points.add(it.split(",").map { it.toInt() })
        }

        val edges = mutableListOf<Edge>()
        val graph = Array<Array<Double>>(points.size) { Array(points.size) { 0.0 } }

        for (i in 0 until points.size) {
            for (j in i + 1 until points.size) {
                graph[i][j] = calculateDistance(points[i], points[j])
                graph[j][i] = graph[i][j]
                edges.add(Edge(i, j, graph[i][j]))
            }
        }
        edges.sortBy { it.distance }

        val parents = IntArray(points.size) { -1 }
        var finalEdge: Edge? = null
        for (edge in edges) {
            val parentFrom = findParent(edge.from, parents)
            val parentTo = findParent(edge.to, parents)
            if (parentFrom == parentTo) continue
            parents[parentFrom] = parentTo
            finalEdge = edge
        }

        return finalEdge?.let { finalEdge ->
            points[finalEdge.from][0].toLong() * points[finalEdge.to][0].toLong()
        } ?: 0
    }

    private fun calculateDistance(point1: List<Int>, point2: List<Int>): Double {
        var total = 0.0
        for (i in point1.indices) {
            total += (point1[i] - point2[i]).absoluteValue.toDouble().pow(2)
        }
        return sqrt(total)
    }

    private fun findParent(pointIdx: Int, parents: IntArray): Int {
        var currIdx = pointIdx
        while (parents[currIdx] != -1) {
            currIdx = parents[currIdx]
        }
        if (pointIdx != currIdx) {
            parents[pointIdx] = currIdx
        }
        return currIdx
    }
}
