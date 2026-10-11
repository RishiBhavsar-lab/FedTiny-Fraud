
package experiment;

public class ExperimentConfig {

    public int numberOfBanks = 5;

    public int transactionsPerBank = 1000;

    public int rounds = 5;

    public int localEpochs = 5;

    public boolean nonIID = true;

    public boolean pruning = false;

    public boolean quantization = false;

    public boolean differentialPrivacy = false;

    public double pruningThreshold = 0.05;

    public double privacyNoise = 0.01;

    public ExperimentConfig() {

        System.out.println(
                "Experiment configuration created."
        );
    }
}
