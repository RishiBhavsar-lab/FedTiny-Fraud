
package federation;

import model.TinyModel;

import java.util.List;

public class FederatedServer {

    private TinyModel globalModel;

    public FederatedServer() {
        globalModel = new TinyModel();
    }

    public void aggregate(List<Bank> banks) {

        double[] newWeights =
                new double[TinyModel.FEATURES];

        double newBias = 0.0;

        int totalSamples = 0;

        for (Bank bank : banks) {

            totalSamples +=
                    bank.getData().size();
        }

        for (Bank bank : banks) {

            double weight =
                    (double) bank.getData().size()
                    / totalSamples;

            double[] localWeights =
                    bank.getModel().getWeights();

            for (int i = 0;
                 i < newWeights.length;
                 i++) {

                newWeights[i] +=
                        weight * localWeights[i];
            }

            newBias +=
                    weight * bank.getModel().getBias();
        }

        globalModel.setWeights(newWeights);
        globalModel.setBias(newBias);
    }

    public TinyModel getGlobalModel() {
        return globalModel;
    }

    public void distribute(List<Bank> banks) {

        for (Bank bank : banks) {

            bank.setModel(globalModel);
        }
    }
}