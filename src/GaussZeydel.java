import java.util.Scanner;

public class GaussZeydel {
    private Matrix A;
    private int amountStrings;
    private double[] f;
    private double[] x2;
    private double e;
    private double[] x1;
    private int numberOfOperations;

    public GaussZeydel() {
        amountStrings = 5;
        A = new Matrix(amountStrings, amountStrings);
        f = new double[amountStrings];
        x2 = new double[amountStrings];
        x1 = new double[amountStrings];
        e = 0.0001;
        numberOfOperations = 1;
    }

    public int getNumberOfOperations() {
        return numberOfOperations;
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

    private boolean convergenceCondition() {
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                if(i != j){
                    if(Math.abs(A.matrix[i][j] / A.matrix[i][i]) > 1){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void fillx2() {
        for (int i = 0; i < amountStrings; i++) {
            x2[i] = f[i] / A.matrix[i][i];
        }
    }

    private double norm(double[] arr) {
        double max = Math.abs(arr[0]);
        for (int i = 1; i < amountStrings; i++) {
            if (Math.abs(arr[i]) > max) {
                max = Math.abs(arr[i]);
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
                    if (j < i) {
                        x2[i] += -A.matrix[i][j] / A.matrix[i][i] * x2[j];
                    } else if (j > i) {
                        x2[i] += -A.matrix[i][j] / A.matrix[i][i] * x1[j];
                    }
                }
                x2[i] += f[i] / A.matrix[i][i];
            }
            numberOfOperations++;
        } while (norm(subtractArrays(x1, x2)) > e);
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
        System.out.println("||r|| = " + norm(r));
    }

    public static void solution() {
        GaussZeydel gz = new GaussZeydel();
        gz.fillEq();
        if(gz.convergenceCondition()) {
            System.out.println("Method converges!");
            gz.method();
            gz.printSolution();
            gz.checkEqualization();
            System.out.println("Number of Gauss-Zeydel method operations: " + gz.getNumberOfOperations());
        } else {
            System.out.println("Method doesn't converge!");
        }
    }

}
