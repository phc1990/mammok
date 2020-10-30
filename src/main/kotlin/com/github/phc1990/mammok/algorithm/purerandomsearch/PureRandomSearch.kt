package com.github.phc1990.mammok.algorithm.purerandomsearch

import com.github.phc1990.mammok.optimization.*
import com.github.phc1990.mammok.optimization.ObjectiveFactory
import com.github.phc1990.mammok.optimization.VariableFactory
import com.github.phc1990.mammok.topology.Space

/**
 * Pure Random Search algorithm.
 *
 * @author [Pau Hebrero Casasayas](https://github.com/phc1990) - Jun 1, 2020
 */
class PureRandomSearch(private val maxIterations: Int ? = null): Algorithm {

    override val name: String = "Pure Random Search"
    private val variables: MutableList<Variable<*>> = mutableListOf()
    private val objectives: MutableList<Objective> = mutableListOf()
    private lateinit var best: InternalCandidate

    fun <T, S: Space<T>> addVariable(name: String, space: S): Variable<T> =
        VariableFactory.get(name, space).also { variables.add(it) }

    fun addObjective(name: String, criterion: OptimizationCriterion): Objective =
        ObjectiveFactory.get(name, criterion).also { objectives.add(it) }

    override fun solve(evaluator: BlackBoxEvaluator, processor: IterationProcessor) {

        var i = 0
        var stop = false
        while (!stop) {

            // Initialise new candidate
            val candidate = InternalCandidate.uniform(i, i, variables)

            // Compare against best candidate
            if (this::best.isInitialized) {
                val candidateValue = candidate.objectives.values.toList()[0]
                val bestValue = best.objectives.values.toList()[0]
                if (candidateValue > bestValue) {best = candidate}
            } else {
                best = candidate
            }

            // Process iteration
            maxIterations?.let { stop = (i >= maxIterations-1) }
            if (processor.process(InternalIteration(i, stop, listOf(best)))) {stop = true}
            i++
        }
    }
}