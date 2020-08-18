//-----------------------------------------------------------------------------
// Name: Anthony Wong
// CruzID: 1652596
// Class: 12B
// Date: 4 Feb 2019
// Desc: Calculates number of solutions to n-Queens problem.
// File Name: Queens.java
//-----------------------------------------------------------------------------

import java.util.Scanner;

class HalfQueens {

  static void placeQueen(int[][] B, int i, int j) {
    int k, l;

    B[i][j] += 1;
    B[i][0] = j;

    // decrements attacking spaces below queen
    k = i + 1;
    l = j;
    while (k <= boardSize(B)) {
      B[k][l] -= 1;
      k++;
    }

    // decrements attacking spaces diagonally left of queen
    k = i + 1;
    l = j - 1;
    while (k <= boardSize(B) && l > 0) {
      B[k][l] -= 1;
      k++;
      l--;
    }

    // decrements attacking spaces diagonally right of queen
    k = i + 1;
    l = j + 1;
    while (k <= boardSize(B) && l <= boardSize(B)) {
      B[k][l] -= 1;
      k++;
      l++;
    }
  }

  static void removeQueen(int[][] B, int i, int j) {
    int k, l;

    B[i][j] -= 1;
    B[i][0] = 0;

    // increments attacking spaces below queen
    k = i + 1;
    l = j;
    while (k <= boardSize(B)) {
      B[k][l] += 1;
      k++;
    }

    // increments attacking spaces diagonally left of queen
    k = i + 1;
    l = j - 1;
    while (k <= boardSize(B) && l > 0) {
      B[k][l] += 1;
      k++;
      l--;
    }

    // increments attacking spaces diagonally right of queen
    k = i + 1;
    l = j + 1;
    while (k <= boardSize(B) && l <= boardSize(B)) {
      B[k][l] += 1;
      k++;
      l++;
    }
  }

  static void printBoard(int[][] B) {
    // if the board has an odd number of squares and the queen in the first row is in the middle of the board then don't mirror the solution
    if (boardSize(B) % 2 == 1 && B[1][boardSize(B) / 2 + 1] == 1) {
      System.out.print("(");
      for (int i = 1; i <= boardSize(B) - 1; i++) {
         System.out.print(B[i][0] + ", ");
      }
      System.out.println(B[boardSize(B)][0] + ")");
    }
    else {  // print original solution and mirrored solution
      System.out.print("(");
      for (int i = 1; i <= boardSize(B) - 1; i++) {
         System.out.print(B[i][0] + ", ");
      }
      System.out.println(B[boardSize(B)][0] + ")");

      // displays mirrored version
      System.out.print("(");
      for (int i = 1; i <= boardSize(B) - 1; i++) {
         System.out.print(boardSize(B) - B[i][0] + 1 + ", ");
      }
      System.out.println(boardSize(B) - B[boardSize(B)][0] + 1 + ")");
    }
  }

  static int findSolutions(int[][] B, int i, String mode) {
    int sum = 0;

    if (i > boardSize(B)) { // a solution was found
      if (mode.equals("-v")) { // if in verbose mode
        printBoard(B);
      }
      return 1;
    }
    else if (i == 1) {
      // checks first half of problems so it doesn't have to compute mirrored problems
      for (int square = 1; square <= boardSize(B) / 2; square++) {
        if (B[i][square] == 0) { // square is safe
          placeQueen(B, i, square);
          sum += 2 * findSolutions(B, i + 1, mode);
          removeQueen(B, i, square);
        }
      }
      if (boardSize(B) % 2 == 1) { // if n is odd compute middle branch
        int mid = boardSize(B) / 2 + 1;
        if (B[i][mid] == 0) {
          placeQueen(B, i, mid);
          sum += findSolutions(B, i + 1, mode);
          removeQueen(B, i, mid);
        }
      }
    }
    else {
      for (int square = 1; square <= boardSize(B); square++) {
        if (B[i][square] == 0) { // square is safe
          placeQueen(B, i, square);
          sum += findSolutions(B, i + 1, mode);
          removeQueen(B, i, square);
        }
      }
    }

    return sum;
  }

  private static void usageError() {
    System.err.println("Usage: Queens [-v] number");
    System.err.println("Option: -v verbose output, print all solutions");
    System.exit(1);
  }

  private static int boardSize(int[][] B) {
    return B.length - 1;
  }

  public static void main(String[] args) {
    String mode = "";
    int n = 0;  // size of board (n x n)
    int[][] B;

    if (args.length == 1) {
      mode = "";
      try {
        n = Integer.parseInt(args[0]);
      }
      catch (NumberFormatException e) {
        usageError();
      }
    }
    else if (args.length == 2) {
      mode = args[0];
      n = Integer.parseInt(args[1]);
    }
    else {
      usageError();
    }

    // constructs empty board
    B = new int[n + 1][n + 1];
    for (int i = 0; i < B.length; i++) {
      for (int j = 0; j < B[i].length; j++) {
        B[i][j] = 0;
      }
    }

    System.out.println(n + "-Queens has " + findSolutions(B, 1, mode) + " solutions");
  }

}
