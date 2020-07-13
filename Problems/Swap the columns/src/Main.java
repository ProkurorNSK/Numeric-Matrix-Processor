import java.util.Scanner;

class Main {
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        int aRows = scanner.nextInt();
        int aColumns = scanner.nextInt();
        int[][] a = init(aRows, aColumns);

        int i = scanner.nextInt();
        int j = scanner.nextInt();

        for (int k = 0; k < aRows; k++) {
            int tmp = a[k][i];
            a[k][i] = a[k][j];
            a[k][j] = tmp;
        }

        print(a);
    }

    private static int[][] init(int aRows, int aColumns) {
        int[][] a = new int[aRows][aColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < aColumns; j++) {
                a[i][j] = scanner.nextInt();
            }
        }
        return a;
    }

    private static void print(int[][] c) {
        if (c == null) return;
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[0].length; j++) {
                System.out.print(c[i][j] + " ");
            }
            System.out.println();
        }
    }
}