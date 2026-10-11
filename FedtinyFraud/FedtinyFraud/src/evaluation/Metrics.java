
package evaluation;

import data.Transaction;
import model.TinyModel;

import java.util.List;

public class Metrics {

    public static double[] calculate(
            TinyModel model,
            List<Transaction> data) {

        int truePositive = 0;
        int trueNegative = 0;
        int falsePositive = 0;
        int falseNegative = 0;

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

            int prediction =
                    model.predict(features);

            int actual =
                    transaction.getLabel();

            if (prediction == 1 && actual == 1) {

                truePositive++;

            } else if (prediction == 0 && actual == 0) {

                trueNegative++;

            } else if (prediction == 1 && actual == 0) {

                falsePositive++;

            } else {

                falseNegative++;
            }
        }

        double accuracy =
                (double) (truePositive + trueNegative)
                / data.size();

        double precision =
                truePositive + falsePositive == 0
                ? 0
                : (double) truePositive
                / (truePositive + falsePositive);

        double recall =
                truePositive + falseNegative == 0
                ? 0
                : (double) truePositive
                / (truePositive + falseNegative);

        double f1 =
                precision + recall == 0
                ? 0
                : 2 * precision * recall
                / (precision + recall);

        return new double[] {
                accuracy,
                precision,
                recall,
                f1
        };
    }

    public static void evaluate(
            TinyModel model,
            List<Transaction> data) {

        double[] results =
                calculate(model, data);

        System.out.println(
                "Accuracy  : "
                + results[0]
        );

        System.out.println(
                "Precision : "
                + results[1]
        );

        System.out.println(
                "Recall    : "
                + results[2]
        );

        System.out.println(
                "F1 Score  : "
                + results[3]
        );
    }
}

