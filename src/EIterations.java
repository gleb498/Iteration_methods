import java.util.Scanner;

public class EIterations {
    private Matrix A;
    private int amountStrings;
    private double[] f;
    private double[] x2;
    private double[][] B;
    private double[] g;
    private double e;
    private double[] x1;
    private int numberOfIterations;

    public EIterations() {
        amountStrings = 5;
        A = new Matrix(amountStrings, amountStrings);
        f = new double[amountStrings];
        x2 = new double[amountStrings];
        x1 = new double[amountStrings];
        B = new double[amountStrings][amountStrings];
        g = new double[amountStrings];
        e = 0.0001;
        numberOfIterations = 1;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    private void fillEq() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Matrix A: ");
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                A.matrix[i][j] = sc.nextDouble();
            }
        }
        System.out.println("F: ");
        for (int i = 0; i < amountStrings; i++) {
            f[i] = sc.nextDouble();
        }
    }
    private boolean convergenceCondition(){
        System.out.println("||B|| = " + normMatrix(B));
        return normMatrix(B) < 1;
    }
    private void fillMatrixB() {
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                if (i != j) {
                    B[i][j] = (-1) * A.matrix[i][j] / A.matrix[i][i];
                } else {
                    B[i][j] = 0;
                }
            }
        }
    }

    private void fillArrayG() {
        for (int i = 0; i < amountStrings; i++) {
            g[i] = f[i] / A.matrix[i][i];
        }
    }

    private void fillx2() {
        for (int i = 0; i < amountStrings; i++) {
            x2[i] = g[i];
        }
    }

    private double normArray(double[] arr) {
        double max = Math.abs(arr[0]);
        for (int i = 1; i < amountStrings; i++) {
            if (Math.abs(arr[i]) > max) {
                max = Math.abs(arr[i]);
            }
        }
        return max;

    }

    private double normMatrix(double[][] matrix) {
        double[] temp = new double[amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            temp[i] = 0;
        }
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                temp[i] += Math.abs(matrix[i][j]);
            }
        }
        double max = temp[0];
        for (int i = 1; i < amountStrings; i++) {
            if (max < temp[i]) {
                max = temp[i];
            }
        }
        return max;
    }

    private double[] subtractArrays(double[] a, double[] b) {
        double[] result = new double[a.length];
        for (int i = 0; i < amountStrings; i++) {
            result[i] = a[i] - b[i];
        }
        return result;
    }

    private void method() {
        fillx2();
        do {
            for (int i = 0; i < amountStrings; i++) {
                x1[i] = x2[i];
                x2[i] = 0;
            }

            for (int i = 0; i < amountStrings; i++) {
                for (int j = 0; j < amountStrings; j++) {
                    x2[i] += x1[j] * B[i][j];
                }
                x2[i] += g[i];
            }
            numberOfIterations++;
        } while (normArray(subtractArrays(x1, x2)) > e);
    }

    private void printArray(double[] arr) {
        for (double item : arr) {
            System.out.println(item);
        }
    }

    private void printSolution() {
        System.out.println("X: ");
        printArray(x2);
    }

    private void checkEqualization() {
        double[] r = new double[amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                r[i] += A.matrix[i][j] * x2[j];
            }
            r[i] -= f[i];
        }
        System.out.println("r = A * x - f: ");
        for (double item : r) {
            System.out.printf("%E", item);
            System.out.println();
        }
        System.out.println("||r|| = " + normArray(r));
    }

    private int aprior() {
        return (int) (Math.log(e * (1 - normMatrix(B)) / normArray(g)) / Math.log(normMatrix(B)) + 1);
    }

    public static void solution() {
        EIterations ei = new EIterations();
        ei.fillEq();
        ei.fillMatrixB();
        if(ei.convergenceCondition()) {
            System.out.println("Method converges!");
            ei.fillArrayG();
            ei.method();
            ei.printSolution();
            ei.checkEqualization();
            System.out.println("K - aprior: " + ei.aprior());
            System.out.println("Number of easy iterations method operations: " + ei.getNumberOfIterations());
        } else {
            System.out.println("Method doesn't converge!");
        }
    }


}
