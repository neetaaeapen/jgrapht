/*
 * (C) Copyright 2016-2016, by Dimitrios Michail and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
package org.jgrapht.alg.matching;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.WeightedMatchingAlgorithm;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.jgrapht.graph.WeightedPseudograph;

/**
 * Unit tests for the PathGrowingWeightedMatching without heuristics algorithm
 * 
 * @author Dimitrios Michail
 */
public class NoHeuristicsPathGrowingWeightedMatchingTest
    extends BasePathGrowingWeightedMatchingTest
{

    @Override
    public WeightedMatchingAlgorithm<Integer, DefaultWeightedEdge> getApproximationAlgorithm(
        Graph<Integer, DefaultWeightedEdge> graph)
    {
        return new PathGrowingWeightedMatching<>(graph, false);
    };

    @Override
    public void testGraph1()
    {
        WeightedPseudograph<Integer, DefaultWeightedEdge> g =
            new WeightedPseudograph<>(DefaultWeightedEdge.class);

        Graphs.addAllVertices(g, IntStream.range(0, 15).boxed().collect(Collectors.toList()));
        Graphs.addEdge(g, 0, 1, 5.0);
        Graphs.addEdge(g, 1, 2, 2.5);
        Graphs.addEdge(g, 2, 3, 5.0);
        Graphs.addEdge(g, 3, 4, 2.5);
        Graphs.addEdge(g, 4, 0, 2.5);
        Graphs.addEdge(g, 0, 13, 2.5);
        Graphs.addEdge(g, 13, 14, 5.0);
        Graphs.addEdge(g, 1, 11, 2.5);
        Graphs.addEdge(g, 11, 12, 5.0);
        Graphs.addEdge(g, 2, 9, 2.5);
        Graphs.addEdge(g, 9, 10, 5.0);
        Graphs.addEdge(g, 3, 7, 2.5);
        Graphs.addEdge(g, 7, 8, 5.0);
        Graphs.addEdge(g, 4, 5, 2.5);
        Graphs.addEdge(g, 5, 6, 5.0);

        WeightedMatchingAlgorithm<Integer, DefaultWeightedEdge> mm = getApproximationAlgorithm(g);

        assertEquals(5, mm.getMatching().size());
        assertEquals(22.5, mm.getMatchingWeight(), WeightedMatchingAlgorithm.DEFAULT_EPSILON);
        assertTrue(isMatching(g, mm.getMatching()));
    }

    @Override
    public void testSelfLoops()
    {
        WeightedPseudograph<Integer, DefaultWeightedEdge> g =
            new WeightedPseudograph<>(DefaultWeightedEdge.class);

        Graphs.addAllVertices(g, Arrays.asList(0, 1, 2, 3));
        Graphs.addEdge(g, 0, 1, 1.0);
        Graphs.addEdge(g, 1, 2, 1.0);
        Graphs.addEdge(g, 2, 3, 1.0);
        Graphs.addEdge(g, 3, 0, 1.0);
        Graphs.addAllVertices(g, Arrays.asList(4, 5, 6, 7));
        Graphs.addEdge(g, 4, 5, 1.0);
        Graphs.addEdge(g, 5, 6, 1.0);
        Graphs.addEdge(g, 6, 7, 1.0);
        Graphs.addEdge(g, 7, 4, 1.0);

        // add self loops
        Graphs.addEdge(g, 0, 0, 100.0);
        Graphs.addEdge(g, 1, 1, 200.0);
        Graphs.addEdge(g, 2, 2, -200.0);
        Graphs.addEdge(g, 3, 3, -100.0);
        Graphs.addEdge(g, 4, 4, 0.0);

        WeightedMatchingAlgorithm<Integer, DefaultWeightedEdge> mm = getApproximationAlgorithm(g);

        assertEquals(3, mm.getMatching().size());
        assertEquals(3.0, mm.getMatchingWeight(), WeightedMatchingAlgorithm.DEFAULT_EPSILON);
        assertTrue(isMatching(g, mm.getMatching()));
    }

    @Override
    public void test3over4Approximation()
    {
        WeightedPseudograph<Integer, DefaultWeightedEdge> g =
            new WeightedPseudograph<>(DefaultWeightedEdge.class);

        Graphs.addAllVertices(g, Arrays.asList(0, 1, 2, 3));
        Graphs.addEdge(g, 0, 1, 1.0);
        Graphs.addEdge(g, 1, 2, 1.0);
        Graphs.addEdge(g, 2, 3, 1.0);
        Graphs.addEdge(g, 3, 0, 1.0);
        Graphs.addAllVertices(g, Arrays.asList(4, 5, 6, 7));
        Graphs.addEdge(g, 4, 5, 1.0);
        Graphs.addEdge(g, 5, 6, 1.0);
        Graphs.addEdge(g, 6, 7, 1.0);
        Graphs.addEdge(g, 7, 4, 1.0);

        WeightedMatchingAlgorithm<Integer, DefaultWeightedEdge> mm =
            new PathGrowingWeightedMatching<>(g, false);

        // maximum here is 4.0
        // path growing algorithm gets 3.0
        assertEquals(3, mm.getMatching().size());
        assertEquals(3.0, mm.getMatchingWeight(), WeightedMatchingAlgorithm.DEFAULT_EPSILON);
        assertTrue(isMatching(g, mm.getMatching()));
    }

    @Override
    public void testMultiGraph()
    {
        WeightedPseudograph<Integer, DefaultWeightedEdge> g =
            new WeightedPseudograph<>(DefaultWeightedEdge.class);

        Graphs.addAllVertices(g, Arrays.asList(0, 1, 2, 3));
        Graphs.addEdge(g, 0, 1, 1.0);
        Graphs.addEdge(g, 1, 2, 1.0);
        Graphs.addEdge(g, 2, 3, 1.0);
        Graphs.addEdge(g, 3, 0, 1.0);
        Graphs.addAllVertices(g, Arrays.asList(4, 5, 6, 7));
        Graphs.addEdge(g, 4, 5, 1.0);
        Graphs.addEdge(g, 5, 6, 1.0);
        Graphs.addEdge(g, 6, 7, 1.0);
        Graphs.addEdge(g, 7, 4, 1.0);

        // add multiple edges
        Graphs.addEdge(g, 0, 1, 2.0);
        Graphs.addEdge(g, 1, 2, 2.0);
        Graphs.addEdge(g, 2, 3, 2.0);
        Graphs.addEdge(g, 3, 0, 2.0);
        Graphs.addEdge(g, 4, 5, 2.0);
        Graphs.addEdge(g, 5, 6, 2.0);
        Graphs.addEdge(g, 6, 7, 2.0);
        Graphs.addEdge(g, 7, 4, 2.0);

        WeightedMatchingAlgorithm<Integer, DefaultWeightedEdge> mm =
            new PathGrowingWeightedMatching<>(g, false);

        // maximum here is 8.0
        // path growing algorithm gets 6.0
        assertEquals(3, mm.getMatching().size());
        assertEquals(6.0, mm.getMatchingWeight(), WeightedMatchingAlgorithm.DEFAULT_EPSILON);
        assertTrue(isMatching(g, mm.getMatching()));
    }

    @Override
    public void testDirected()
    {
        DirectedWeightedPseudograph<Integer, DefaultWeightedEdge> g =
            new DirectedWeightedPseudograph<>(DefaultWeightedEdge.class);

        Graphs.addAllVertices(g, Arrays.asList(0, 1, 2, 3));
        Graphs.addEdge(g, 0, 1, 1.0);
        Graphs.addEdge(g, 1, 2, 1.0);
        Graphs.addEdge(g, 2, 3, 1.0);
        Graphs.addEdge(g, 3, 0, 1.0);
        Graphs.addAllVertices(g, Arrays.asList(4, 5, 6, 7));
        Graphs.addEdge(g, 4, 5, 1.0);
        Graphs.addEdge(g, 5, 6, 1.0);
        Graphs.addEdge(g, 6, 7, 1.0);
        Graphs.addEdge(g, 7, 4, 1.0);

        // add multiple edges
        Graphs.addEdge(g, 0, 1, 2.0);
        Graphs.addEdge(g, 1, 2, 2.0);
        Graphs.addEdge(g, 2, 3, 2.0);
        Graphs.addEdge(g, 3, 0, 2.0);
        Graphs.addEdge(g, 4, 5, 2.0);
        Graphs.addEdge(g, 5, 6, 2.0);
        Graphs.addEdge(g, 6, 7, 2.0);
        Graphs.addEdge(g, 7, 4, 2.0);

        WeightedMatchingAlgorithm<Integer, DefaultWeightedEdge> mm =
            new PathGrowingWeightedMatching<>(g, false);

        // maximum here is 8.0
        // path growing algorithm gets 6.0
        assertEquals(3, mm.getMatching().size());
        assertEquals(6.0, mm.getMatchingWeight(), WeightedMatchingAlgorithm.DEFAULT_EPSILON);
        assertTrue(isMatching(g, mm.getMatching()));
    }

}

// End PathGrowingWeightedMatchingTest.java
