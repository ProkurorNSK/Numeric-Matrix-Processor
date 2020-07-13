package processor;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        while (true) {
            System.out.println("1. Add matrices");
            System.out.println("2. Multiply matrix to a constant");
            System.out.println("3. Multiply matrices");
            System.out.println("4. Transpose matrix");
            System.out.println("5. Calculate a determinant");
            System.out.println("6. Inverse matrix");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");
            int answer = scanner.nextInt();
            switch (answer) {
                case 0:
                    return;
                case 1:
                    print(addition());
                    break;
                case 2:
                    print(multiplicationByNumber());
                    break;
                case 3:
                    print(multiplicationByMatrix());
                    break;
                case 4:
                    print(transpose());
                    break;
                case 5:
                    System.out.println(determined());
                    break;
                case 6:
                    print(inverse());
                    break;
                default:
                    System.out.println("ERROR");
            }
        }
    }

    private static double[][] inverse() {
        System.out.print("Enter matrix size: ");
        int aRows = scanner.nextInt();
        int aColumns = scanner.nextInt();

        System.out.println("Enter matrix:");
        double[][] a = init(aRows, aColumns);

        System.out.println("The result is:");
        if (aColumns != aRows) {
            System.out.println("ERROR");
            return null;
        }
        return inverseMatrix(a);
    }

    private static double determined() {
        System.out.print("Enter matrix size: ");
        int aRows = scanner.nextInt();
        int aColumns = scanner.nextInt();

        System.out.println("Enter matrix:");
        double[][] a = init(aRows, aColumns);

        System.out.println("The result is:");
        if (aColumns != aRows) {
            System.out.println("ERROR");
            return 0;
        }
        return determinedMatrix(a);
    }

    private static double[][] transpose() {
        System.out.println("1. Main diagonal");
        System.out.println("2. Side diagonal");
        System.out.println("3. Vertical line");
        System.out.println("4. Horizontal line");
        System.out.print("Your choice: ");
        int answer = scanner.nextInt();

        System.out.print("Enter matrix size: ");
        int aRows = scanner.nextInt();
        int aColumns = scanner.nextInt();

        System.out.println("Enter matrix:");
        double[][] a = init(aRows, aColumns);

        System.out.println("The result is:");
        if (aColumns != aRows) {
            System.out.println("ERROR");
            return null;
        }
        return transposeMatrix(a, answer);
    }

    private static double[][] multiplicationByMatrix() {
        System.out.print("Enter size of first matrix: ");
        int aRows = scanner.nextInt();
        int aColumns = scanner.nextInt();
        System.out.println("Enter first matrix:");
        double[][] a = init(aRows, aColumns);
        System.out.print("Enter size of second matrix: ");
        int bRows = scanner.nextInt();
        int bColumns = scanner.nextInt();
        System.out.println("Enter second matrix:");
        double[][] b = init(bRows, bColumns);
        System.out.println("The multiplication result is:");
        if (aColumns != bRows) {
            System.out.println("ERROR");
            return null;
        }
        return multiplicationM(a, b);
    }

    private static double[][] multiplicationByNumber() {
        System.out.print("Enter size of matrix: ");
        int aRows = scanner.nextInt();
        int aColumns = scanner.nextInt();
        System.out.println("Enter matrix:");
        double[][] a = init(aRows, aColumns);
        System.out.print("Enter number:");
        int number = scanner.nextInt();
        System.out.println("The multiplication result is:");
        return multiplicationN(a, number);
    }

    private static double[][] addition() {
        System.out.print("Enter size of first matrix: ");
        int aRows = scanner.nextInt();
        int aColumns = scanner.nextInt();
        System.out.println("Enter first matrix:");
        double[][] a = init(aRows, aColumns);
        System.out.print("Enter size of second matrix: ");
        int bRows = scanner.nextInt();
        int bColumns = scanner.nextInt();
        System.out.println("Enter second matrix:");
        double[][] b = init(bRows, bColumns);
        System.out.println("The addition result is:");
        if (aRows != bRows || aColumns != bColumns) {
            System.out.println("ERROR");
            return null;
        }
        return add(a, b);
    }

    private static double[][] inverseMatrix(double[][] a) {
        double det = determinedMatrix(a);
        if (det == 0) {
            System.out.println("ERROR");
            return null;
        }
        int size = a.length;
        double[][] c = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                c[i][j] = Math.pow(-1, i + j) * determinedMatrix(minor(a, i, j));
            }
        }
        return multiplicationN(transposeMatrix(c, 1), 1d / det);
    }

    private static double determinedMatrix(double[][] a) {
        int size = a.length;
        if (size == 2) {
            return a[0][0] * a[1][1] - a[0][1] * a[1][0];
        } else {
            double result = 0d;
            for (int k = 0; k < size; k++) {
                result += a[0][k] * Math.pow(-1, k) * determinedMatrix(minor(a, 0, k));
            }
            return result;
        }
    }

    private static double[][] minor(double[][] a, int l, int k) {
        int size = a.length;
        double[][] c = new double[size - 1][size - 1];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < k; j++) {
                c[i][j] = a[i][j];
            }
            for (int j = k + 1; j < size; j++) {
                c[i][j - 1] = a[i][j];
            }
        }
        for (int i = l + 1; i < size; i++) {
            for (int j = 0; j < k; j++) {
                c[i - 1][j] = a[i][j];
            }
            for (int j = k + 1; j < size; j++) {
                c[i - 1][j - 1] = a[i][j];
            }
        }
        return c;
    }

    private static double[][] transposeMatrix(double[][] a, int answer) {
        int size = a.length;
        double[][] c = new double[size][size];
        switch (answer) {
            case 1:
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        c[i][j] = a[j][i];
                    }
                }
                break;
            case 2:
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        c[i][j] = a[size - 1 - j][size - 1 - i];
                    }
                }
                break;
            case 3:
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        c[i][j] = a[i][size - 1 - j];
                    }
                }
                break;
            case 4:
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        c[i][j] = a[size - 1 - i][j];
                    }
                }
                break;
            default:
                System.out.println("ERROR");
        }
        return c;
    }

    private static double[][] multiplicationM(double[][] a, double[][] b) {
        int aRows = a.length;
        int aColumns = a[0].length;
        int bColumns = b[0].length;
        double[][] c = new double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                for (int k = 0; k < aColumns; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return c;
    }

    private static double[][] multiplicationN(double[][] a, double number) {
        int rows = a.length;
        int columns = a[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                a[i][j] *= number;
            }
        }
        return a;
    }

    private static double[][] add(double[][] a, double[][] b) {
        int rows = a.length;
        int columns = a[0].length;
        double[][] c = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                c[i][j] = a[i][j] + b[i][j];
            }
        }
        return c;
    }

    private static double[][] init(int aRows, int aColumns) {
        double[][] a = new double[aRows][aColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < aColumns; j++) {
                a[i][j] = scanner.nextDouble();
            }
        }
        return a;
    }

    private static void print(double[][] c) {
        if (c == null) return;
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[0].length; j++) {
                System.out.printf("%.2f ", c[i][j]);
            }
            System.out.println();
        }
    }
}
