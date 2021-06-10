import java.time.YearMonth;
import java.util.Random;
import java.util.function.Function;

public class NeuralNetwork {
    int inputNodes;
    int hiddenNodes;
    int outputNodes;
    double learningRate = 2;

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

    public Double[] train(Matrix input, Matrix expected) {
        if (input.data.length != inputNodes || expected.data.length != outputNodes) {
            System.out.println("Data error adjust nodes!");
            return null;
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


       // BACKPROPAGATION

       // OUTPUT -> HIDDEN
       Matrix outputError = expected.subtract(expected, output);
       Matrix dOutput = dSigmoid(output);
       Matrix gradient = dOutput.hadamard(dOutput, outputError);
       Matrix hiddenT = Matrix.transposeMatrix(hidden);

       gradient = gradient.escalarMultiply(gradient, learningRate);

       // ADJUST BIAS OUTPUT -> HIDDEN
       biasHO = biasHO.add(biasHO, gradient);

       Matrix weigthsHOD = gradient.multiply(gradient, hiddenT);
       weigthsHO = weigthsHOD.add(weigthsHO, weigthsHOD);

       // HIDDEN -> INPUT
       
       Matrix weigthsHOT = Matrix.transposeMatrix(weigthsHO);
       Matrix hiddenError = weigthsHOT.multiply(weigthsHOT, outputError);
       Matrix dHidden = dSigmoid(hidden);
       Matrix inputT = Matrix.transposeMatrix(input);

       Matrix hiddenGradient = dHidden.hadamard(hiddenError, dHidden);
       hiddenGradient = hiddenGradient.escalarMultiply(hiddenGradient, learningRate);

       // ADJUST BIAS HIDDEN -> INPUT
       biasIH = biasIH.add(biasIH, hiddenGradient);

       Matrix weigthsIHD = hiddenGradient.multiply(hiddenGradient, inputT);

       weigthsIH = weigthsIHD.add(weigthsIH, weigthsIHD);

       for (int i = 0; i < outputError.data.length; i ++) {
        for (int j = 0; j < outputError.data[0].length; j ++) {
         //System.out.println("Erro OUTPUT -> " + outputError.data[i][j] + " | \n" + "Output -> " + output.data[i][j] + " | ");
        }
    }
    return Matrix.matrixToArray(output);
}

    public Double[] predict(Matrix input) {

        this.input = input;


       // INPUT -> HIDDEN

       Matrix hidden = weigthsIH.multiply(weigthsIH, input);
       hidden = hidden.add(hidden, biasIH);
       sigmoid(hidden);

       // HIDDEN -> OUTPUT

       Matrix output = weigthsHO.multiply(weigthsHO, hidden);
       output = output.add(output, biasHO);
       sigmoid(output);
       System.out.println("OUTPUT -> " + output.data[0][0]);

       return Matrix.matrixToArray(output);
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
        Random r = new Random();
        for (int i = 0; i < this.data.length; i ++) {
            for (int j = 0; j < this.data[0].length; j ++) {
                this.data[i][j] = -1 + (1 - -1) * r.nextDouble();
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

    public static Double[] matrixToArray(Matrix A) {
        Double[] arr = new Double[A.data.length];
        for (int i = 0; i < A.data.length; i ++) {
            arr[i] = A.data[i][0];
        }
        return arr;
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
