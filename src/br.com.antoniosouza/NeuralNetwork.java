import java.util.function.Function;

public class NeuralNetwork {
   public NeuralNetwork() {

   }
}

class Matrix {
    private int rows, cols;
    private  Double[][] data;
    private  Matrix matrix;


    public Matrix(int rows, int cols) {
    
        this.rows = rows;
        this.cols = cols;

        data = new Double[rows][cols];
        System.out.println("GENERATION VALUES +++++++++++++++++++++++");
        for (int i = 0; i < rows; i ++) {
            for (int j = 0; j < cols; j ++) {
                data[i][j] = Math.floor(Math.random() * 10);
                System.out.println("Data[" + i + "]" + "[" + j + "]: " + data[i][j] );
            }
        }
        System.out.println("VALUES GENERATED!");
    }

    public Matrix add(Matrix A, Matrix B) {
        matrix = new Matrix(A.rows, A.cols);
        for (int i = 0; i < A.cols; i++) {
            for (int j = 0; j < B.rows; j++) {
                matrix.data[i][j] = A.data[i][j] + B.data[i][j];
                System.out.println("AFTER SUM!");
                System.out.println(A.data[i][j] + " | " + B.data[i][j] + " | " + matrix.data[i][j]); 
            }
        }
        return matrix;
    }

    public Matrix multiply(Matrix A, Matrix B) {

        if (A.data[0].length != B.data.length) {
            System.out.println("Multiplication undefined!");
            return null;
        }

        matrix = new Matrix(A.data.length, B.data[0].length);
        for (int i = 0; i < A.data.length; i++)
            for (int j = 0; j < B.data[0].length; j++) {
                matrix.data[i][j] = 0.0;
            }
        for (int i = 0; i < A.data.length; i++)
            for (int j = 0; j < B.data[0].length; j++) {
                for (int k = 0; k < A.data[0].length; k++) {
                    matrix.data[i][j] += (A.data[i][k] * B.data[k][j]);
                }
                System.out.println("Matrix data["+i+"]["+j+"]: "+matrix.data[i][j]);
        }
        return matrix;
    }


}
