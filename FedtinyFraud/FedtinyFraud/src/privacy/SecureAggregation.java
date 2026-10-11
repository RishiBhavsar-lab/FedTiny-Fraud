
package privacy;

import federation.Bank;
import model.TinyModel;

import java.util.List;

public class SecureAggregation {

    public static TinyModel aggregate(
            List<Bank> banks) {

        TinyModel result =
                new TinyModel();

        double[] weights =
                new double[
                        TinyModel.FEATURES
                ];

        double bias = 0;

        int totalSamples = 0;

        for (Bank bank : banks) {

            totalSamples +=
                    bank.getData().size();
        }

        for (Bank bank : banks) {

            double contribution =
                    (double) bank.getData().size()
                    / totalSamples;

            double[] localWeights =
                    bank.getModel()
                    .getWeights();

            for (int i = 0;
                 i < weights.length;
                 i++) {

                weights[i] +=
                        contribution
                        * localWeights[i];
            }

            bias +=
                    contribution
                    * bank.getModel()
                    .getBias();
        }

        result.setWeights(weights);
        result.setBias(bias);

        return result;
    }
}