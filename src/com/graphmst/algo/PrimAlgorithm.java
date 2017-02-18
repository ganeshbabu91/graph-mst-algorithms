package com.graphmst.algo;

import java.util.*;
import com.graphmst.model.*;
import com.graphmst.datastructures.*;

public class PrimAlgorithm {

    /**
     * Main method of Prim's algorithm.
     */
    public List<Edge<Integer>> primMST(Graph<Integer> graph){

        //binary heap + map data structure
        BinaryMinHeap<Vertex<Integer>> minHeap = new BinaryMinHeap<>();

        //map of vertex to edge which gave minimum weight to this vertex.
        Map<Vertex<Integer>,Edge<Integer>> vertexToEdge = new HashMap<>();

        //stores final result
        List<Edge<Integer>> result = new ArrayList<>();

        //insert all vertices with infinite value initially.
        for(Vertex<Integer> v : graph.getAllVertex()){
            minHeap.add(Integer.MAX_VALUE, v);
        }

        //start from any random vertex
        Vertex<Integer> startVertex = graph.getAllVertex().iterator().next();

        //for the start vertex decrease the value in heap + map to 0
        minHeap.decrease(startVertex, 0);

        //iterate till heap + map has elements in it
        while(!minHeap.empty()){
            //extract min value vertex from heap + map
            Vertex<Integer> current = minHeap.extractMin();

            //get the corresponding edge for this vertex if present and add it to final result.
            //This edge wont be present for first vertex.
            Edge<Integer> spanningTreeEdge = vertexToEdge.get(current);
            if(spanningTreeEdge != null) {
                result.add(spanningTreeEdge);
            }

            //iterate through all the adjacent vertices
            for(Edge<Integer> edge : current.getEdges()){
                Vertex<Integer> adjacent = getVertexForEdge(current, edge);
                //check if adjacent vertex exist in heap + map and weight attached with this vertex is greater than this edge weight
                if(minHeap.containsData(adjacent) && minHeap.getWeight(adjacent) > edge.getWeight()){
                    //decrease the value of adjacent vertex to this edge weight.
                    minHeap.decrease(adjacent, edge.getWeight());
                    //add vertex->edge mapping in the graph.
                    vertexToEdge.put(adjacent, edge);
                }
            }
        }
        return result;
    }

    private Vertex<Integer> getVertexForEdge(Vertex<Integer> v, Edge<Integer> e){
        return e.getVertex1().equals(v) ? e.getVertex2() : e.getVertex1();
    }

    public static void main(String args[]){
        Graph<Integer> graph = generateGraph(1000, false);
    	long startTime = System.currentTimeMillis();

        // 7ms - 8ms upper bound : 12.40
        /*graph.addEdge(0, 1, 4);
        graph.addEdge(1, 2, 8);
        graph.addEdge(2, 3, 7);
        graph.addEdge(3, 4, 9);
        graph.addEdge(4, 5, 10);
        graph.addEdge(2, 5, 4);
        graph.addEdge(1, 7, 11);
        graph.addEdge(0, 7, 8);
        graph.addEdge(2, 8, 2);
        graph.addEdge(3, 5, 14);
        graph.addEdge(5, 6, 2);
        graph.addEdge(6, 8, 6);
        graph.addEdge(6, 7, 1);
        graph.addEdge(7, 8, 7);*/

        
        /*
        //6ms - 7ms upper 7.00ms
        graph.addEdge(1, 2, 3);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 1, 1);
        graph.addEdge(1, 4, 1);
        graph.addEdge(2, 4, 3);
        graph.addEdge(4, 5, 6);
        graph.addEdge(5, 6, 2);
        graph.addEdge(3, 5, 5);
        graph.addEdge(3, 6, 4);*/
        
        /*
        // 6ms - 7ms upper bound : 8.45ms
        graph.addEdge(1, 2, 4);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 5, 1);
        graph.addEdge(2, 6, 3);
        graph.addEdge(2, 4, 2);
        graph.addEdge(6, 5, 2);
        graph.addEdge(6, 4, 3);
        graph.addEdge(4, 7, 2);
        graph.addEdge(3, 4, 5);
        graph.addEdge(3, 7, 8);*/
        

        PrimAlgorithm prims = new PrimAlgorithm();
        Collection<Edge<Integer>> edges = prims.primMST(graph);
        for(Edge<Integer> edge : edges){
            System.out.println(edge);
        }
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime + "ms");
    }
    
    public static Graph<Integer> generateGraph(int n, boolean isDense)
	{
    	
        Graph<Integer> graph = new Graph<Integer>(false);
		Random rand = new Random();
		int counter = 0;
		if(isDense){
			for( int i=1;i<=n;i++)
			{
				for(int j=i+1;j<=n;j++)
				{
					graph.addEdge(i,j,rand.nextInt(10) + 1);
					counter++;
				}
			}
		}
		else{
			for(int i=1;i<=n;i++)
			 {
				
				graph.addEdge(i, i+1, (rand.nextInt(10)+1));
				counter++;

			 }
		}
		System.out.println("counter = "+counter);
		return graph;
	}
    
}
