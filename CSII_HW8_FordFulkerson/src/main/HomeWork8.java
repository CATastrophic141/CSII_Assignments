package main;
import java.util.*;
import static java.lang.Integer.MAX_VALUE;

class Graph {
    /**As the preferred datatype for flow capacity graphs is edge matrices
    due to the fact that residual graph matrices may be easily copied,
    an adjusted methodology for BFS focusing on the 2D matrix must be used.
    This modified BFS search will return a value dependent on
    if the sink has been reached**/
    private final int numVertices;
    public Graph(int numOfVertexes){
        numVertices = numOfVertexes;
    }
    /**Of the int[][] edge capacity matrices, the X value indicates the starting vertex
    /and Y indicates the vertex the edge points to**/
    //This modified BFS method returns "true" or 1 if a path to sink exists. path[] stores the vertex path from source to sink
    public int modifiedBFS(int[][] residual, int source, int sink, int[] path){
        int[] handled = new int[numVertices]; //To keep track of already subsourced vertices
        //Start off each vertex as not being handled
        for(int i = 0; i < numVertices; i++){
            handled[i] = 0;
        }
        LinkedList<Integer> fifoQueue = new LinkedList<>(); //First in, first out queue
        fifoQueue.add(source); //Start with source vertex
        handled[source] = 1; //Source vertex has now been encountered
        path[source] = -1; //Source has no predecessor, assign it an impossible corresponding value
        while(!fifoQueue.isEmpty()){ //While queue has elements
            int current = fifoQueue.poll(); //Take next vertex ID val
            //For each possible vertex value
            for (int j = 0; j < numVertices; j++){
                //If the vertex has not already been reached and if there is an edge present
               if(handled[j] == 0 && residual[current][j] > 0){
                   //If the sink has been reached, continued searching is unnecessary
                   if (j == sink){
                       path[j] = current; //Set predecessor vertex value
                       return 1; //return true flag
                   }
                   fifoQueue.add(j); //Continue search with next possible vertex
                   path[j] = current; //Current overhead is predecessor
                   handled[j] = 1; //Vertex has been encountered
               }
            }
        }
        //If BFS is completed without reaching the sink,
        //the sink cannot be reached by the currently present paths via the source.
        return 0;
    }
    int findMaxFlow(int[][] capacityGraph, int source, int sink){
        //A residual edge graph is created from the same possible dimensions as the main graph
        int[][] residual = capacityGraph.clone(); //Initialize residual graph to be equivalent to capacity graph
        int[] path = new int[numVertices]; //Creates possible path array, for later possible use
        int maxFlow = 0; //Initialize maximum flow to 0
        while (modifiedBFS(residual, source, sink, path) == 1){ //BFS returns a "true" flag if sink is reachable
            int currentRFlow = MAX_VALUE; //Minimum residual flow is desired, initialize to max for later comparison
            int reversePathCurrent = sink; //Init first vertex that reverse traversal starts on to be sink
            while (reversePathCurrent != source) { //While the traversed path has not reached the source
                int j = path[reversePathCurrent]; //Get predecessor of current to get edge indices
                //Set the current residual flow to be the minimum of the current r flow or the inspected residual
                currentRFlow = Math.min(currentRFlow, residual[j][reversePathCurrent]);
                reversePathCurrent = path[reversePathCurrent]; //Move along path for next iteration
            }
            //Reset reverse tracing start node to be sink
            reversePathCurrent = sink;
            while (reversePathCurrent != source) { //While the traversed path has not reached the source
                int j = path[reversePathCurrent]; //Get predecessor of current to get edge indices
                residual[j][reversePathCurrent] = residual[j][reversePathCurrent] - currentRFlow; //Decrease back edge
                residual[reversePathCurrent][j] = residual[reversePathCurrent][j] + currentRFlow; //Increase forward edge
                reversePathCurrent = path[reversePathCurrent]; //Move along path for next iteration
            }
            maxFlow = maxFlow + currentRFlow; //Add to max possible flow the best residual flow result
        }
        return maxFlow; //Return the maximum flow amount
    }
}
public class HomeWork8 {
    public static void main(String[] args) {
        Graph graph = new Graph(6);
        int[][] edgeMatrix = new int[][] {
                //Vertex correlation: { V0, V1, V2, V3, V4, V5, ...}
                { 0, 40, 0, 20, 5, 0 }, { 0, 0, 10, 0, 0, 0 },
                { 0, 0, 0, 0, 30, 0 }, { 0, 5, 5, 0, 10, 0 },
                { 0, 0, 0, 0, 0, 99}, { 0, 0, 0, 0, 0, 0 },
        };
        int source = 0;
        int sink = 5;
        double timeStart = System.nanoTime();
        int test = graph.findMaxFlow(edgeMatrix, source, sink);
        double timeEnd = System.nanoTime();
        double totalTime = timeEnd - timeStart;
        System.out.printf("The max flow is: %d%n", test);
        System.out.printf("Time taken: %.2f nanoseconds", totalTime);
    }
}