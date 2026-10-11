
package model;

import data.Transaction;

import java.util.List;

public class InferenceBenchmark {

    public static void measure(
            TinyModel model,
            List<Transaction> data) {

        long start = System.nanoTime();

        int predictions = 0;

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

            model.predict(features);

            predictions++;
        }

        long end = System.nanoTime();

        long elapsed = end - start;

        double milliseconds =
                elapsed / 1_000_000.0;

        System.out.println(
                "Predictions: "
                + predictions
        );

        System.out.println(
                "Inference time: "
                + milliseconds
                + " ms"
        );
    }
}
