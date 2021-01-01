
/**
 * @author Hong Yue Wang: 300105373
 * @version 27 Nov 2020
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Objects;

public class Main {
    /*
     * Global variables to the procedure Bakctrack: integers bestK; arrays
     * currX[0..n], bestX[0..n].
     */
    private static int bestK;
    private static int[] bestX;
    private static int[] currX;
    private static int[] length;
    private static int L;
    private static int n;
    private static int newS;
    private static HashMap visited;

    public static void main(String[] args) {
        // Read data and initialize variables: n, length[0..n-1], L; bestK=-1
        Scanner input = new Scanner(System.in);
        int nOfCases = Integer.valueOf(input.nextLine().trim());

        // For every case
        for (int i = 0; i < nOfCases; i++) {
            // Skip the blank line
            input.nextLine();
            L = Integer.valueOf(input.nextLine().trim()) * 100;
            bestK = -1;
            n = 0;
            ArrayList<Integer> lengthList = new ArrayList<Integer>();

            String line = null;
            // While the line isn't 0
            while (Integer.parseInt(line = input.nextLine().trim()) != 0) {
                lengthList.add(Integer.parseInt(line));
                n++;
            }

            // Convert ArrayList to int[]
            length = new int[n];
            for (int j = 0; j < n; j++) {
                length[j] = lengthList.get(j);
            }

            bestX = new int[n + 1];
            visited = new HashMap<Integer, Boolean>(tableSize());
            currX = new int[n + 1];
            Arrays.fill(currX, -1);

            backtrackSolve(0, L);

            System.out.println(bestK);

            for (int k = 0; k < bestK; k++) {
                if (bestX[k] == 1) {
                    System.out.println("port");
                } else if (bestX[k] == 0) {
                    System.out.println("starboard");
                } else {
                    continue;
                }
            }

            if (i != nOfCases - 1) {
                System.out.println();
            }
        }
        input.close();
    }

    public static void backtrackSolve(int currK, int currS) {

        if (currK > bestK) {
            bestK = currK;
            for (int i = 0; i < bestX.length; i++) {
                bestX[i] = currX[i];
            }
        }

        if (currK < n) {

            Object leftPairValue = visited.get(new Pair(currK + 1, currS - length[currK]).hashCode());
            boolean leftStateIsVisited;

            if (leftPairValue == null) {
                leftStateIsVisited = false;
            } else {
                leftStateIsVisited = (boolean) leftPairValue;
            }

            if (currS >= length[currK] && !leftStateIsVisited) {
                currX[currK] = 1;
                newS = currS - length[currK];
                backtrackSolve(currK + 1, newS);
                visited.put(new Pair(currK + 1, newS).hashCode(), true);
            }

            Object rightPairValue = visited.get(new Pair(currK + 1, currS).hashCode());
            boolean rightStateIsVisited;

            if (rightPairValue == null) {
                rightStateIsVisited = false;
            } else {
                rightStateIsVisited = (boolean) rightPairValue;
            }

            if (findFreeRight(currK, currS) >= length[currK] && !rightStateIsVisited) {
                currX[currK] = 0;
                backtrackSolve(currK + 1, currS);
                visited.put(new Pair(currK + 1, currS).hashCode(), true);
            }
        }

    }

    public static int tableSize() {
        int size = (n + 1) * (L + 1);
        size = size / 1000;
        return size;
    }

    public static int findFreeRight(int k, int s) {
        int occupiedLeft = L - s;
        int lengthBoarded = 0;
        for (int i = 0; i < k; i++) {
            lengthBoarded += length[i];
        }

        return L - (lengthBoarded - occupiedLeft);
    }

    public static class Pair<K extends Comparable, V> {
        K key;
        V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        K getKey() {
            return key;
        }

        V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            final Pair other = (Pair) obj;

            return this.key.equals(other.getKey()) && this.value.equals(other.getValue());
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 31 * hash + (this.key != null ? this.key.hashCode() : 0);
            hash = 31 * hash + (this.value != null ? this.value.hashCode() : 0);

            return hash;
        }
    }

}