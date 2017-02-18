package com.graphmst.algo;

import java.util.*;
import com.graphmst.model.*;
import com.graphmst.datastructures.*;

public class KruskalAlgorithm {
    /**
     * Comparator to sort edges by weight in non decreasing order
     */
    public class EdgeComparator implements Comparator<Edge<Integer>> {
        @Override
        public int compare(Edge<Integer> edge1, Edge<Integer> edge2) {
            if (edge1.getWeight() < edge2.getWeight()) {
                return -1;
            } else if(edge1.getWeight() == edge2.getWeight()){
            	return 0;
            }else {
                return 1;
            }
        }
    }

    public List<Edge<Integer>> getMST(Graph<Integer> graph) {
        List<Edge<Integer>> allEdges = graph.getAllEdges();
        EdgeComparator edgeComparator = new EdgeComparator();

        //sort all edges in non decreasing order
        Collections.sort(allEdges, edgeComparator);
        DisjointSet disjointSet = new DisjointSet();

        //create as many disjoint sets as the total vertices
        for (Vertex<Integer> vertex : graph.getAllVertex()) {
            disjointSet.makeSet(vertex.getId());
        }

        List<Edge<Integer>> resultEdge = new ArrayList<Edge<Integer>>();

        for (Edge<Integer> edge : allEdges) {
            //get the sets of two vertices of the edge
            long root1 = disjointSet.findSet(edge.getVertex1().getId());
            long root2 = disjointSet.findSet(edge.getVertex2().getId());

            //check if the vertices are in same set or different set
            //if verties are in same set then ignore the edge
            if (root1 == root2) {
                continue;
            } else {
                //if vertices are in different set then add the edge to result and union these two sets into one
                resultEdge.add(edge);
                disjointSet.union(edge.getVertex1().getId(), edge.getVertex2().getId());
            }

        }
        return resultEdge;
    }

    public static void main(String args[]) {
        Graph<Integer> graph = generateGraph(1000, true);
    	long startTime = System.currentTimeMillis();

        /*graph.addEdge(1, 2, 4);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 5, 1);
        graph.addEdge(2, 6, 3);
        graph.addEdge(2, 4, 2);
        graph.addEdge(6, 5, 2);
        graph.addEdge(6, 4, 3);
        graph.addEdge(4, 7, 2);
        graph.addEdge(3, 4, 5);
        graph.addEdge(3, 7, 8);*/
        
                
        KruskalAlgorithm mst = new KruskalAlgorithm();
        List<Edge<Integer>> result = mst.getMST(graph);
        int treecount = 0;
        for (Edge<Integer> edge : result) {
            System.out.println(edge.getVertex1() + " " + edge.getVertex2());
            treecount++;
        }
        System.out.println("number of vertices in final MST = "+treecount);
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