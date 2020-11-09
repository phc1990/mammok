package org.phc1990.mammok.problems.singleobjective.convex

import org.phc1990.mammok.optimization.Candidate
import org.phc1990.mammok.optimization.Iteration
import org.phc1990.mammok.optimization.OptimizationCriterion
import org.phc1990.mammok.problems.OptimizationTestProblem
import org.phc1990.mammok.topology.space.implementation.DoubleInterval

/**
 * Rastrigin function.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Nov 9, 2020
 */
class RastriginFunction(private val dimensions: Int, private val tolerance: Double): OptimizationTestProblem() {

    private val interval = DoubleInterval(-5.12, 5.12, tolerance, false)

    init {
        variables = Array(dimensions){interval}
        objectives = Array(1){OptimizationCriterion.MINIMIZE}
        name = "Rastrigin Function, dimensions: $dimensions, tolerance: $tolerance"
    }

    override fun evaluate(candidate: Candidate) {
        var f = 0.0
        for (i in variables.indices) {
            f += candidate.getVariable(i, Double::class.java)
        }
        candidate.objectives[0] = f
    }

    override fun validate(iteration: Iteration) {
        val candidate = iteration.candidates[0]
        for (i in variables.indices) {
            validateVariable(interval, candidate.getVariable(0, Double::class.java), 0.0, tolerance)
        }
    }
}