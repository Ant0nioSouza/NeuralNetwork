import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralNetwork {
    
    private Random random = new Random();
    private int nInputs, nHidden, nOutputs;
    private double hiddenBias, outputBias;
    private List<Double> newInputs = new ArrayList<Double>();
    public Double[][] hiddenLayer;
    public Double[][] outputLayer;
    private List<Double> inputLayer;
    private List<Double> errors = new ArrayList<Double>();
    private double neuronDelta;
    private Double[][][] network = new Double[2][0][0];

    /*
    * @param nInputs - Number of input neurons
    * @param nHidden - Number of hidden neurons
    * @param nOutputs - Number of output neurons
    */
    public NeuralNetwork (int nInputs, int nHidden, int nOutputs, List<Double> input) {
        this.nInputs = nInputs;
        this.nHidden = nHidden;
        this.nOutputs = nOutputs;
        inputLayer = input;
        hiddenLayer = new Double[nHidden][4];
        outputLayer = new Double[nOutputs][4];


        for (int i = 0; i < nHidden; i++) {
            hiddenLayer[i][0] = (-1 + (1 + 1) * random.nextDouble()); // generate a random number between -1 and 1.
        }
        for (int i = 0; i < nOutputs; i++) {
            outputLayer[i][0] = (-1 + (1 + 1) * random.nextDouble()); // generate a random number between -1 and 1.
        }

        hiddenBias = hiddenLayer[nHidden - 1][0];
        outputBias = outputLayer[nOutputs - 1][0];
        network[0] = hiddenLayer;
        network[1] = outputLayer;
    }

    private Double neuronActivation (Double[][] layer, List<Double> input) {
        System.out.println(layer[1][0] + "SIZE " + layer.length);
        double activation = layer[layer.length-1][0];
        for (int i = 0; i < layer.length - 1; i++) {
            activation += layer[i][0] * input.get(i);
        }
        return activation;
    }

    private Double neuronTransfer (double activation) {
        return 1.0 / (1.0 + Math.exp(-activation));
    }

    public void forwardPropagation() {
        
        for (Double[][] layer : network) {
            
            for (int i = 0; i < layer.length; i++) {
                System.out.println(layer);
                System.out.println(inputLayer);
                double activation = neuronActivation(layer, inputLayer);
                layer[i][1] = neuronTransfer(activation);
                newInputs.add(layer[i][1]);
            }
            inputLayer = newInputs;
        }
    }
    


    public void backwardPropagateError(List<Double> expectedList) {
        for (int i = 1; i > 0; i--) {
            Double[][] layer = network[i];
            if (i != network.length - 1) {
                double error = 0.0;
                for (int j = 0; j < layer.length; j++) {
                    layer = network[i+1];
                    error += layer[j][0] * layer[j][2];
                }
                errors.add(error);
            } else {
                for (int j = 0; j < layer.length; j++) {
                    System.out.println("----------> " + layer[j][1] + " <--------");
                    errors.add(expectedList.get(j) - layer[j][1]);
                }
            }
            for (int j = 0; j < layer.length; j++) {
                if (layer == hiddenLayer) {
                    layer[j][2] = errors.get(j) * transferDerivative(layer[j][1]);
                }
            }
            
        }
    }

    private Double transferDerivative(double output) {
        return output * (1.0 - output);
    }
}
