/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bandwidth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Spirors
 */
public class Graph {
    private int vertex;
    private LinkedList<Integer>[] adjList;
    private boolean[] visited;
        
    Graph(int v){
        this.vertex = v;
        adjList = new LinkedList[v];
        visited = new boolean[v];
        
        for(int i=0; i<v; i++){
            adjList[i] = new LinkedList<Integer>();
        }
    }
    
    void addEdge(int from, int to){
        adjList[from].add(to);
    }
    
    LinkedList<Integer>[] getAdjList(){
        return adjList;
    }
    
    boolean[] getVisited(){
        return visited;
    }
    
    void dfsUtil(int v, boolean visited[], ArrayList<Integer> component){
        visited[v] = true; 
        component.add(v+1);

        Iterator<Integer> i = adjList[v].listIterator(); 
        while (i.hasNext()){ 
            int n = i.next(); 
            if (!visited[n]) 
                dfsUtil(n, visited, component); 
        } 
    }
    ArrayList<Integer> dfs(int v){
        ArrayList<Integer> component = new ArrayList<>();
  
        // Call the recursive helper function to print DFS traversal 
        dfsUtil(v, visited, component);
        return component;
    }
    @Override
    public String toString(){
        String s = "";
        for(int i=0;i<adjList.length;i++){
            s = s.concat(i+1+"->{");
            for(Integer j : adjList[i]){
                s = s.concat(j.intValue()+1+"");
            }
            s = s.concat("},");
        }
        return s;
    }
}