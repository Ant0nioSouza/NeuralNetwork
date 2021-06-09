import java.util.function.Function;

public class NeuralNetwork {
    int inputNodes;
    int hiddenNodes;
    int outputNodes;

    Matrix biasIH, biasHO, weigthsIH, weigthsHO, input;

   public NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes) {
        this.inputNodes = inputNodes;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;

        biasIH = new Matrix(hiddenNodes, 1); // Bias from input to hidden layer.
        biasIH.randomize();
        biasHO = new Matrix(outputNodes, 1); // Bias from hidden to output layer.
        biasHO.randomize();

        weigthsIH = new Matrix(hiddenNodes, inputNodes);
        weigthsIH.randomize();
        weigthsHO = new Matrix (outputNodes, hiddenNodes);
        weigthsHO.randomize();
   }

    public void FeedForward(Matrix input) {
       if (input.data.length != inputNodes) {
            System.out.println("Data error adjust input nodes!");
            return;
       }
       
       this.input = input;


       // INPUT -> HIDDEN

       Matrix hidden = weigthsIH.multiply(weigthsIH, input);
       hidden = hidden.add(hidden, biasIH);
       sigmoid(hidden);

       // HIDDEN -> OUTPUT

       Matrix output = weigthsHO.multiply(weigthsHO, hidden);
       output = output.add(output, biasHO);
       sigmoid(output);
    }

    private void sigmoid(Matrix x) {
        for (int i = 0; i < x.data.length; i ++) {
            for (int j = 0; j < x.data[0].length; j ++) {
                System.out.println("BEFORE Sigmoid -> " + x.data[i][j]);
                x.data[i][j] =  1 / (1 + Math.exp(-x.data[i][j]));
                System.out.println("AFTER Sigmoid -> " + x.data[i][j]);
                
            }
        }
    }

}

class Matrix {
    private int rows, cols;
    protected  Double[][] data;
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

        for (int i = 0; i < A.rows; i++) {
            for (int j = 0; j < B.cols; j++) {
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

    public static Matrix arrayToMatrix(Double[] A) {
        Matrix amatrix = new Matrix(A.length, 1);
        for (int i = 0; i < amatrix.data.length; i ++) {
            amatrix.data[i][0] = A[i];
        }
        return amatrix;
    }
}
