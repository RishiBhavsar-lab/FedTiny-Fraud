
package model;

import data.Transaction;

import java.util.Collections;
import java.util.List;

public class Trainer {

    private static final double LEARNING_RATE = 0.05;

    public static void train(
            TinyModel model,
            List<Transaction> data,
            int epochs) {

        for (int epoch = 0;
             epoch < epochs;
             epoch++) {

            Collections.shuffle(data);

            for (Transaction transaction : data) {

                double[] features = {
                        transaction.getAmount(),
                        transaction.getFrequency(),
                        transaction.getInternational(),
                        transaction.getMerchantRisk(),
                        transaction.getLocationRisk(),
                        transaction.getDeviceRisk(),
                        transaction.getAccountAge(),
                        transaction.getPreviousFraud()
                };

                double[] normalized =
                        TinyModel.normalizeFeatures(features);

                double prediction =
                        model.predictProbability(features);

                double actual =
                        transaction.getLabel();

                double error =
                        prediction - actual;

                /*
                 * Give fraud transactions more importance
                 * because they are less common.
                 */
                double classWeight =
                        actual == 1.0
                        ? 3.0
                        : 1.0;

                double weightedError =
                        error * classWeight;

                double[] weights =
                        model.getWeights();

                for (int i = 0;
                     i < weights.length;
                     i++) {

                    weights[i] -=
                            LEARNING_RATE
                            * weightedError
                            * normalized[i];
                }

                model.setBias(
                        model.getBias()
                        - LEARNING_RATE
                        * weightedError
                );
            }
        }
    }
}
