import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralNetwork {
    
    private Random random = new Random();
    private int nInputs, nHidden, nOutputs;
    private double hiddenBias, outputBias;
    private List<Double> newInputs = new ArrayList<Double>();
    private Double[][] hiddenLayer;
    private Double[][] outputLayer;
    private List<Double> inputLayer;
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


        for (int i = 0; i < nHidden + 1; i++) {
            hiddenLayer[i][0] = (-1 + (1 + 1) * random.nextDouble()); // generate a random number between -1 and 1.
        }
        for (int i = 0; i < nOutputs + 1; i++) {
            outputLayer[i][0] = (-1 + (1 + 1) * random.nextDouble()); // generate a random number between -1 and 1.
        }

        hiddenBias = hiddenLayer[nHidden][3];
        outputBias = outputLayer[nOutputs][3];
        network[0] = hiddenLayer;
        network[1] = outputLayer;
    }

    private Double neuronActivation (double weigth, double input, double bias) {
        return (weigth * input) + bias;
    }

    private Double neuronTransfer (double activation) {
        return 1.0 / (1.0 + Math.exp(-activation));
    }

    private void forwardPropagation() {
        
        for (Double[][] layer : network) {
            for (int i = 0; i < layer.length - 1; i++) {
                if (layer == network[1]) {
                    for (int j = 0; j < inputLayer.size(); j++) {
                        newInputs.add(neuronTransfer(neuronActivation(layer[i][0], inputLayer.get(j), hiddenBias)));
                    }
                } else if (layer == network[0]) {
                    for (int j = 0; j < inputLayer.size(); j++) {
                        newInputs.add(neuronTransfer(neuronActivation(layer[i][0], inputLayer.get(j), outputBias)));
                    }
                }
            }
            inputLayer = newInputs;
        }
    }
}
