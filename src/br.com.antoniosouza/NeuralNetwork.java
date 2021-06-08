public class NeuralNetwork {
   public NeuralNetwork() {

   }
}

class Matrix {
    private int rows, cols;
    private Double[][] data;

    public Matrix(int rows, int cols) {
    
        this.rows = rows;
        this.cols = cols;

        data = new Double[rows][cols];

        for (int i = 0; i < data.length; i ++) {
            for (int j = 0; j < data[0].length; j ++) {
                data[i][j] = Math.random();
                System.out.println("Data[" + i + "]" + "[" + j + "]: " + data[i][j]);
            }
        }
    }

    
}
