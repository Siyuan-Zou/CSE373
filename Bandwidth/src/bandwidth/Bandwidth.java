/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bandwidth;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Spirors
 */
public class Bandwidth {
    private static int[] nums;
    private static boolean[] used;
    private static ArrayList<Integer> bestSolutionSoFar;
    private static Graph G;
    private static int V;
    private static int E;
    private static boolean finished = false;
    private static int L; //minimum possible bandwidth or LowerBound
    private static int U; //upper bound
    
    public static void main(String[] args) throws FileNotFoundException {
        String filePath = new File("g-bt-11-10.txt").getAbsolutePath();
        Scanner sc = new Scanner(new File(filePath));
        
        V = sc.nextInt();
        nums = new int[V]; 
        used = new boolean[V]; //All false by default
        bestSolutionSoFar = new ArrayList<Integer>();
        
        for(int i=0; i<V; i++){
            nums[i]=i+1;
            bestSolutionSoFar.add(i);
        }
        
        G = new Graph(V);
        E = sc.nextInt();
        sc.nextLine();
        for(int i=0; i<E; i++){
            String line = sc.nextLine();
            String[] edge = line.split("\\s+");
            G.addEdge(Integer.parseInt(edge[0])-1, Integer.parseInt(edge[1])-1);
        }
        L = lowerBoundBand(G);
        U = maxBand(bestSolutionSoFar, G);
        
        ArrayList<Integer> curr = new ArrayList<Integer>();
        permutation(0, curr);
        System.out.println("Minimum Bandwidth: "+U);
        System.out.println("Permutation: ");
        System.out.println(Arrays.toString(bestSolutionSoFar.toArray()));
    }
    
    public static int maxBand(ArrayList<Integer> v, Graph g){
        LinkedList<Integer>[] graph = g.getAdjList();
        int max = 0; //No negative bandwidth
        for(int i=0;i<graph.length;i++){
            for(Integer j : graph[i]){
                int d = dist(v, i+1, j+1);
                if(max < d)
                    max = d;
            }
        }
        return max;
    }
    public static int dist(ArrayList<Integer> v, int a, int b){
        int idx_a = 0;
        int idx_b = 0;
        for(int i=0;i<v.size();i++){
            if(v.get(i)==a)
                idx_a=i;
            if(v.get(i)==b)
                idx_b=i;
        }
        return Math.abs(idx_a-idx_b);
    }
    public static void permutation(int d, ArrayList<Integer> curr) {
        if(d==V){
            int a = maxBand(curr, G);
            if(a==L){
                bestSolutionSoFar = new ArrayList(curr);
                finished = true;
            }
            if(a<U){
                U=a;
                System.out.println("Upper Bound is: "+U);
                bestSolutionSoFar = new ArrayList(curr);
            }
        }
        for(int i=0; i<V; i++){
            if(used[i]==false){
                used[i]=true;
                curr.add(0, nums[i]);
                permutation(d+1, curr);
                curr.remove(0);
                used[i]=false;
                if(finished==true)
                    return;
            }
        }  
    }
    //A general Lower Bound May not apply sometime
    public static int lowerBoundBand(Graph g){
        LinkedList<Integer>[] graph = g.getAdjList();
        double a = 0;
        for(int i=0;i<graph.length;i++){
            if(a<graph[i].size())
                a = graph[i].size();
        }
        return (int)Math.ceil(a/2);
    }
}
