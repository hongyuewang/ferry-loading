
/**
 * @author Hong Yue Wang: 300105373
 * @version 24 Nov 2020
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static int bestK;
    private static int[] bestX;
    private static int[] currX;
    private static int[] length;
    private static int L;
    private static int n;
    private static int newS;
    private static boolean[][] visited;

    public static void main(String[] args) {
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
            visited = new boolean[n + 1][L + 1];
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
            if (currS >= length[currK] && !visited[currK + 1][currS - length[currK]]) {
                currX[currK] = 1;
                newS = currS - length[currK];
                backtrackSolve(currK + 1, newS);
                visited[currK + 1][newS] = true;
            }

            if (findFreeRight(currK, currS) >= length[currK] && !visited[currK + 1][currS]) {
                currX[currK] = 0;
                backtrackSolve(currK + 1, currS);
                visited[currK + 1][currS] = true;
            }
        }

    }

    public static int findFreeRight(int k, int s) {
        int occupiedLeft = L - s;
        int lengthBoarded = 0;
        for (int i = 0; i < k; i++) {
            lengthBoarded += length[i];
        }

        return L - (lengthBoarded - occupiedLeft);
    }
}
