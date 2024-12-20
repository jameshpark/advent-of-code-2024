package org.jameshpark

import org.jameshpark.util.deserializeFromJsonFile
import kotlin.math.absoluteValue

object DayTwo {
    fun runPartOne() {
        val result = partOne(input)
        println(result)
    }

    fun runPartTwo() {
        val result = partTwo(input)
        println(result)
    }

    private fun partOne(reports: List<List<Int>>): Int = reports.removeUnsafeReports().size

    private fun partTwo(reports: List<List<Int>>): Int = reports.removeUnsafeReports(strict = false).size

    @OptIn(kotlin.ExperimentalStdlibApi::class)
    private fun List<List<Int>>.removeUnsafeReports(strict: Boolean = true) =
        filter { report ->
            val safe = isSafe(report)

            when {
                safe -> true
                !safe && strict -> false
                else -> { // !safe && !strict
                    var safeAfterRemoval = false

                    for (i in 0..report.lastIndex) {
                        val removed = report.slice(0..<i) + report.slice(i + 1..report.lastIndex)
                        if (isSafe(removed)) {
                            safeAfterRemoval = true
                            break
                        }
                    }

                    safeAfterRemoval
                }
            }
        }

    private fun isSafe(report: List<Int>): Boolean {
        var increasing: Boolean? = null
        var safe = true

        report.windowed(size = 2) { (first, second) ->
            if (!safe) return@windowed

            val diff = second - first
            safe =
                when {
                    diff.absoluteValue !in 1..3 -> false
                    increasing != null -> increasing == diff > 0
                    else ->
                        run {
                            increasing = diff > 0
                            true
                        }
                }
        }

        return safe
    }

    private val input = deserializeFromJsonFile<List<List<Int>>>("DayTwoInput.json")
}
