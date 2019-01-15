/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alignment;

/**
 *
 * @author Spirors
 */
public class Alignment {
    
    private static String backtrace ="";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String a = "leonard skiena";
        String b = "lynard skynard";
        System.out.println(editDistance(a,b));
        System.out.println(backtrace);
        String alignment ="";
        int c = 0;
        for(int i=0; i<backtrace.length(); i++){
            if(backtrace.charAt(i)=='M'){
                alignment+=a.charAt(c);
                c++;
            }
            if(backtrace.charAt(i)=='D'){
                alignment+='-';
                c++;
            }
            if(backtrace.charAt(i)=='I'){
                alignment+='~';
            }
            if(backtrace.charAt(i)=='S'){
                alignment+='=';
                c++;
            }
        }
        System.out.println(alignment);
    }
    public static int editDistance(String a, String b){
        int[][] matrix = new int[a.length()+1][b.length()+1];
        
        //Make row_0 and col_0 equal to i for sake of empty string
        for(int i=0; i<matrix.length;i++){
            matrix[i][0]=i;
        }
        for(int i=0; i<matrix[0].length;i++){
            matrix[0][i]=i;
        }
        for(int i=1;i<=a.length(); i++){
            for(int j=1; j<=b.length(); j++){
                if(a.charAt(i-1)==b.charAt(j-1))
                    matrix[i][j]=matrix[i-1][j-1];
                else{
                    matrix[i][j]=1+min(matrix[i-1][j-1], //Subsitute
                                       matrix[i-1][j],   //Delete
                                       matrix[i][j-1]);  //Insert
                }
            }
        }
        backtrace = actualEdits(matrix, a, b);
        return matrix[a.length()][b.length()];
    }
    public static int min(int a, int b, int c){
        int d = Math.min(a, b);
        return Math.min(d, c);
    }
    public static String actualEdits(int[][] M, String a, String b) {
        String out = "";
        int i = M.length - 1;
        int j = M[0].length - 1;
        while(true) {
            if (i == 0 || j == 0) {
                break;
            }
            if (a.charAt(i-1) == b.charAt(j-1)) {
                out+="M";
                i = i-1;
                j = j-1;
            } else if (M[i][j] == M[i-1][j-1] + 1){
                out+="S";
                i = i-1;
                j = j-1;
            } else if (M[i][j] == M[i-1][j] + 1) {
                out+="D";
                i = i-1;
            } else if (M[i][j] == M[i][j-1] + 1){
                out+="I";
                j = j-1;
            } else {
                throw new IllegalArgumentException("Some wrong with given data");
            }
        }
        return new StringBuilder(out).reverse().toString();
    }
    public static void printMatrix(int[][] arr){
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[0].length; j++){
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }       
    }
}
