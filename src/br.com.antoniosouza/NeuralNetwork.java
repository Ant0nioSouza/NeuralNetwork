import java.util.function.Function;

public class NeuralNetwork {
    int inputNodes;
    int hiddenNodes;
    int outputNodes;
   public NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes) {
        this.inputNodes = inputNodes;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;

        Matrix biasIH = new Matrix(hiddenNodes, 1); // Bias from input to hidden layer.
        biasIH.randomize();
        Matrix biasHO = new Matrix(outputNodes, 1); // Bias from hidden to output layer.
        biasHO.randomize();
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
        for (int i = 0; i < rows; i ++) {
            for (int j = 0; j < cols; j ++) {
                data[i][j] = Math.floor(Math.random() * 10);
            }
        }
    }

    public Matrix add(Matrix A, Matrix B) {
        matrix = new Matrix(A.rows, A.cols);
        for (int i = 0; i < A.cols; i++) {
            for (int j = 0; j < B.rows; j++) {
                matrix.data[i][j] = A.data[i][j] + B.data[i][j];
            }
        }
        return matrix;
    }

    public Matrix multiply(Matrix A, Matrix B) {

        if (A.data[0].length != B.data.length) {
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
        }
        return matrix;
    }

    public void randomize() {
        for (int i = 0; i < this.data.length; i ++) {
            for (int j = 0; j < this.data[0].length; j ++) {
                this.data[i][j] = Math.random();
            }
        }
    }

}
