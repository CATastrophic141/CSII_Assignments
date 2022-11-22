package main;

import java.util.*;
import static java.lang.Integer.MAX_VALUE;

class Vertex implements Comparable<Vertex> {
 int vertexNumber;
 int distance = MAX_VALUE;
 Vertex predecessor = null;
 //Serves as linked-list, storing the respective Vertex and weight of edge
 Map<Vertex, Integer> adjacentVertexList = new HashMap<>();

 //Constructor
 public Vertex(int vertexNumber) {this.vertexNumber = vertexNumber;}

 //Map building method
 public void placeAdjacent(Vertex adjVertex, int edgeWeight){
     adjacentVertexList.put(adjVertex, edgeWeight);
 }

 //Queue comparator override
 @Override public int compareTo(Vertex vertex){
    return Integer.compare(this.distance, vertex.distance);
 }
}

class Graph {
    //Main functionality method
   public void dijkstraMinBinHeap(List<Vertex> vertexList, Vertex sourceVertex){
       //Initialize source vertex. All other vertexes have already been initialized to 0
       sourceVertex.distance = 0;
       //Set of vertices that have been dequeued
       Set<Vertex> handledVertices = new HashSet<>(); //May be redundant
       handledVertices.add(sourceVertex); //Source is already "handled"
       //Queue initialization. Collections additive was recommended to correct queue comparatives
       Queue<Vertex> queue = new PriorityQueue<>(Collections.singleton(sourceVertex));
       //Add every vertex to the queue
       queue.addAll(vertexList);
       //While there are still elements in the queue
       while(!queue.isEmpty()){
           //Dequeue the top priority item
           Vertex current = queue.poll();
           //Dequeued item is "handled"
           handledVertices.add(current);
           //For each item in the adjacency list/map
           for (Map.Entry<Vertex,Integer> v : current.adjacentVertexList.entrySet()){
               //Store current distance in temporary register
               int temp = v.getKey().distance;
               //Relax the currently inspected edge/adjacent vertex
               relaxEdge(current, v.getKey(), v.getValue());
               //If the edge/vertex was adjusted
               if (v.getKey().distance < temp){
                   /* Removal and re-addition from queue
                    *  serves as a primitive way
                    *  of adjusting the item in queue */
                   queue.remove(v.getKey());
                   queue.add(v.getKey());
               }
           }
       }
   }

    //Fibonacci queue version follows same structure as minimum binary heap queue version
    public void dijkstraFibHeap(List<Vertex> vertexList, Vertex sourceVertex){
        sourceVertex.distance = 0;
        Set<Vertex> handledVertices = new HashSet<>(); //May be redundant
        handledVertices.add(sourceVertex);
        //Fibonacci queue implementation from Keith Schwarz's library
        FibonacciHeap<Vertex> queue = new FibonacciHeap<>();
        //Enqueue each vertex with its distance as its key
        //Map reference to each entry is created
        Map<Vertex, FibonacciHeap.Entry<Vertex>> entryMap = new HashMap<>();
        for (Vertex v : vertexList) {
            FibonacciHeap.Entry<Vertex> temp;
            temp = queue.enqueue(v, v.distance);
            entryMap.put(v, temp);
        }
        while(!queue.isEmpty()){
            Vertex current = queue.dequeueMin().getValue();
            handledVertices.add(current);
            for (Map.Entry<Vertex,Integer> v : current.adjacentVertexList.entrySet()){
                int temp = v.getKey().distance;
                relaxEdge(current, v.getKey(), v.getValue());
                if (v.getKey().distance < temp){
                    //Use reference map
                    //Implementation of queue functionalities are limited to "Entry<T>" classes
                    //"Entry<T>" classes do not properly cast to Vertex classes
                    queue.decreaseKey(entryMap.get(v), v.getValue());
                }
            }
        }
    }

    //Edge/vertex relaxing method. Lessens vertex distance if better path exists
   void relaxEdge(Vertex currentV, Vertex nextV, int weight){
       if (nextV.distance > currentV.distance + weight) {
           nextV.distance = currentV.distance + weight;
           nextV.predecessor = currentV;
       }
   }
}

public class HomeWork6 {
    public static void main (String[] args) {
        List<Vertex> vList = new ArrayList<>();

        Vertex vertex0 = new Vertex(0);
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        Vertex vertex3 = new Vertex(3);
        Vertex vertex4 = new Vertex(4);
        Vertex vertex5 = new Vertex(5);
        Vertex vertex6 = new Vertex(6);
        Vertex vertex7 = new Vertex(7);
//        Vertex vertex8 = new Vertex(8);
//        Vertex vertex9 = new Vertex(9);
//        Vertex vertex10 = new Vertex(10);
//        Vertex vertex11 = new Vertex(11);
//        Vertex vertex12 = new Vertex(12);
//        Vertex vertex13 = new Vertex(13);
//        Vertex vertex14 = new Vertex(14);
//        Vertex vertex15 = new Vertex(15);
//        Vertex vertex16 = new Vertex(16);
//        Vertex vertex17 = new Vertex(17);

        vertex0.placeAdjacent(vertex3, 5);

        vertex1.placeAdjacent(vertex2, 9);
        vertex1.placeAdjacent(vertex5, 7);

        vertex2.placeAdjacent(vertex0, 5);
        vertex2.placeAdjacent(vertex5, 6);
        vertex2.placeAdjacent(vertex6, 6);

        vertex3.placeAdjacent(vertex5, 6);

        vertex4.placeAdjacent(vertex2, 9);
        vertex4.placeAdjacent(vertex6, 9);
        vertex4.placeAdjacent(vertex7, 7);

        vertex5.placeAdjacent(vertex6, 8);

        vertex6.placeAdjacent(vertex2, 8);
        vertex6.placeAdjacent(vertex4, 2);
        vertex6.placeAdjacent(vertex5, 9);

        vertex7.placeAdjacent(vertex5, 2);
        vertex7.placeAdjacent(vertex6, 3);

//        vertex8.placeAdjacent(vertex4, 1);
//        vertex8.placeAdjacent(vertex5, 8);
//        vertex8.placeAdjacent(vertex12, 4);
//
//        vertex9.placeAdjacent(vertex10, 4);
//        vertex9.placeAdjacent(vertex12, 3);
//
//        vertex10.placeAdjacent(vertex3, 1);
//        vertex10.placeAdjacent(vertex6, 1);
//
//        //
//
//        vertex12.placeAdjacent(vertex8, 1);
//        vertex12.placeAdjacent(vertex11, 2);
//        vertex12.placeAdjacent(vertex16, 3);
//
//        vertex13.placeAdjacent(vertex9, 3);
//        vertex13.placeAdjacent(vertex10, 3);
//        vertex13.placeAdjacent(vertex16, 6);
//
//        vertex14.placeAdjacent(vertex11, 7);
//
//        vertex15.placeAdjacent(vertex11, 2);
//        vertex15.placeAdjacent(vertex14, 3);
//
//        vertex16.placeAdjacent(vertex12, 5);
//        vertex16.placeAdjacent(vertex13, 3);
//        vertex16.placeAdjacent(vertex15, 1);
//        vertex16.placeAdjacent(vertex17, 4);
//
//        vertex17.placeAdjacent(vertex16, 8);


        vList.add(vertex0);
        vList.add(vertex1);
        vList.add(vertex2);
        vList.add(vertex3);
        vList.add(vertex4);
        vList.add(vertex5);
        vList.add(vertex6);
        vList.add(vertex7);
//        vList.add(vertex8);
//        vList.add(vertex9);
//        vList.add(vertex10);
//        vList.add(vertex11);
//        vList.add(vertex12);
//        vList.add(vertex13);
//        vList.add(vertex14);
//        vList.add(vertex15);
//        vList.add(vertex16);
//        vList.add(vertex17);

        Graph graph = new Graph();
        double startTimeMinHeapQueue = System.nanoTime();
        /***/
        graph.dijkstraMinBinHeap(vList, vertex1);
        double endTimeMinHeapQueue = System.nanoTime();
        double durationMinHeapQueue =  endTimeMinHeapQueue - startTimeMinHeapQueue;

        for (Vertex v : vList){
            Vertex current = v;
            System.out.printf("Path from vertex %d: ", v.vertexNumber);
            if (v.distance == MAX_VALUE){
                System.out.println("No path");
                continue;
            }
            while(current.predecessor != null){
                System.out.printf("%d -> ", current.vertexNumber);
                current = current.predecessor;
            }
            System.out.println("source");
        }
        System.out.printf("Binary-heap queue implementation duration in nanoseconds: %.2f%n", durationMinHeapQueue);

        double startTimeFibQueue = System.nanoTime();
        /***/
        graph.dijkstraFibHeap(vList, vertex1);
        double endTimeFibQueue = System.nanoTime();
        double durationFibQueue =  endTimeFibQueue - startTimeFibQueue;
        for (Vertex v : vList){
            Vertex current = v;
            System.out.printf("Path from vertex %d: ", v.vertexNumber);
            if (v.distance == MAX_VALUE){
                System.out.println("No path");
                continue;
            }
            while(current.predecessor != null){
                System.out.printf("%d -> ", current.vertexNumber);
                current = current.predecessor;
            }
            System.out.println("source");
        }
        System.out.printf("Fibonacci-heap queue implementation duration in nanoseconds: %.2f", durationFibQueue);
    }
}