package fas.algorithms.sifting;

import java.util.List;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import fas.algorithms.eades.LinearArrangementAlgorithm;

public class MultipleSiftingAlgorithm implements LinearArrangementAlgorithm {

    @Override
    public List<Integer> linearArrangement(DirectedGraph<Integer, DefaultEdge> graph) {
        return linearArrangement(graph, graph.vertexSet()).asList();
    }

    public LinearArrangement linearArrangement(DirectedGraph<Integer, DefaultEdge> graph, Iterable<Integer> vertexes) {
        LinearArrangement newArr = arrangementBah(graph, vertexes);
        LinearArrangement oldArr = null;
        do {
            oldArr = newArr;
            newArr = arrangementBah(graph, oldArr.asList());
        } while (oldArr.score() != newArr.score());
        return newArr;
    }

    LinearArrangement arrangementBah(DirectedGraph<Integer, DefaultEdge> graph, Iterable<Integer> vertexList) {
        LinearArrangement arrangement = new LinearArrangement(graph);
        for (Integer vertex : vertexList) {
            int[] scores = arrangement.addScore(vertex);
            int index = 0;
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < scores.length; i++) {
                if (scores[i] < min) {
                    index = i;
                    min = scores[i];
                }
            }
            arrangement.add(index, vertex);
        }

        return arrangement;
    }

}