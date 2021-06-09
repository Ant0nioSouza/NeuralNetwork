import java.time.YearMonth;
import java.util.function.Function;

public class NeuralNetwork {
    int inputNodes;
    int hiddenNodes;
    int outputNodes;
    double learningRate = 0.1;

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

    public void train(Matrix input, Matrix expected) {
        /*if (input.data.length != inputNodes || expected.data.length != outputNodes) {
            System.out.println("Data error adjust nodes!");
            return;
       }*/
       
       this.input = input;


       // INPUT -> HIDDEN

       Matrix hidden = weigthsIH.multiply(weigthsIH, input);
       hidden = hidden.add(hidden, biasIH);
       sigmoid(hidden);

       // HIDDEN -> OUTPUT

       Matrix output = weigthsHO.multiply(weigthsHO, hidden);
       output = output.add(output, biasHO);
       sigmoid(output);


       // BACKPROPAGATION

       // OUTPUT -> HIDDEN
       Matrix outputError = expected.subtract(expected, input);
       Matrix dOutput = dSigmoid(output);
       Matrix gradient = dOutput.hadamard(outputError, dOutput);
       Matrix hiddenT = Matrix.transposeMatrix(hidden);

       gradient = gradient.escalarMultiply(gradient, learningRate);

       Matrix weigthsHOD = gradient.multiply(gradient, hiddenT);
       weigthsHO = weigthsHOD.add(weigthsHO, weigthsHOD);

       // HIDDEN -> INPUT

       
       
    }


    private void sigmoid(Matrix x) {
        for (int i = 0; i < x.data.length; i ++) {
            for (int j = 0; j < x.data[0].length; j ++) {
                x.data[i][j] =  1 / (1 + Math.exp(-x.data[i][j]));
                
            }
        }
    }

    private Matrix dSigmoid(Matrix x) {
        Matrix ds = new Matrix(x.data.length, x.data[0].length);
        for (int i = 0; i < x.data.length; i ++) {
            for (int j = 0; j < x.data[0].length; j ++) {
                ds.data[i][j] = x.data[i][j] * (1 - x.data[i][j]);
                System.out.println(ds.data[i][j]);
            }
        }
        return ds;
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
                data[i][j] = 0.0;
            }
        }
    }

    public Matrix hadamard(Matrix A, Matrix B) {
        matrix = new Matrix(A.rows, A.cols);

        for (int i = 0; i < A.rows; i++) {
            for (int j = 0; j < B.cols; j++) {
                matrix.data[i][j] = A.data[i][j] * B.data[i][j];
            }
        }
        return matrix;
    }

    public Matrix escalarMultiply(Matrix A, double B) {
        matrix = new Matrix(A.rows, A.cols);

        for (int i = 0; i < A.rows; i++) {
            for (int j = 0; j < A.cols; j++) {
                matrix.data[i][j] = A.data[i][j] * B;
            }
        }
        return matrix;
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

    public Matrix subtract(Matrix A, Matrix B) {
        matrix = new Matrix(A.rows, A.cols);
        System.out.println("B length: " + B.data.length + " -> " + B.data[0].length);
        System.out.println("A length: " + A.data.length + " -> " + A.data[0].length);
        for (int i = 0; i < A.data.length; i++) {
            for (int j = 0; j < A.data[0].length; j++) {
                matrix.data[i][j] = A.data[i][j] - B.data[i][j];
                //System.out.println(matrix.data[i][j] + " | -> | " + A.data[i][j] + " | " + B.data[i][j]);
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

    // STATICS:

    public static Matrix arrayToMatrix(Double[] A) {
        Matrix amatrix = new Matrix(A.length, 1);
        for (int i = 0; i < amatrix.data.length; i ++) {
            amatrix.data[i][0] = A[i];
        }
        return amatrix;
    }

    public static Matrix transposeMatrix(Matrix A) {
        Matrix tm = new Matrix(A.cols, A.rows);
        for (int i = 0; i < tm.data.length; i ++) {
            for (int j = 0; j < tm.data[0].length; j ++) {
                tm.data[i][j] = A.data[j][i];
            }
        }
        return tm;
    }

}
