import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralNetwork {
    
    private Random random = new Random();
    private int nInputs, nHidden, nOutputs;
    private List<Double> hiddenLayer = new ArrayList<Double>();
    private List<Double> outputLayer = new ArrayList<Double>();
    private List<Double> inputLayer;

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
        for (int i = 0; i < nHidden + 1; i++) {
            hiddenLayer.add(-1 + (1 + 1) * random.nextDouble()); // generate a random number between -1 and 1.
        }
        for (int i = 0; i < nOutputs + 1; i++) {
            outputLayer.add(-1 + (1 + 1) * random.nextDouble()); // generate a random number between -1 and 1.
        }
    }

    private Double neuronActivation (double weigth, double input, double bias) {
        return (weigth * input) + bias;
    }

    private Double neuronTransfer (double activation) {
        return 1.0 / (1.0 + Math.exp(-activation));
    }
}
