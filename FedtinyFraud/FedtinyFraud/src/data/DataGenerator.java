
package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    private static final Random random =
            new Random();

    public static List<Transaction> generate(
            int count) {

        List<Transaction> transactions =
                new ArrayList<>();

        for (int i = 0; i < count; i++) {

            double amount =
                    100 + random.nextDouble() * 9900;

            double frequency =
                    1 + random.nextDouble() * 19;

            double international =
                    random.nextDouble() < 0.30
                    ? 1.0 : 0.0;

            double merchantRisk =
                    random.nextDouble();

            double locationRisk =
                    random.nextDouble();

            double deviceRisk =
                    random.nextDouble();

            double accountAge =
                    1 + random.nextDouble() * 119;

            double previousFraud =
                    random.nextDouble() < 0.05
                    ? 1.0 : 0.0;

            /*
             * Create a clear and learnable fraud pattern.
             *
             * Higher amount, frequency, international
             * transactions and risk scores increase the
             * probability of fraud.
             */
            double riskScore =
                    1.2 * (amount / 10000.0)
                    + 0.8 * (frequency / 20.0)
                    + 1.5 * international
                    + 1.5 * merchantRisk
                    + 1.2 * locationRisk
                    + 1.5 * deviceRisk
                    - 0.8 * (accountAge / 120.0)
                    + 1.5 * previousFraud;

            /*
             * Convert risk score into a probability.
             * The 0.5 base keeps fraud relatively uncommon.
             */
            double probability =
                    0.02
                    +
                    0.30
                    *
                    (
                        1.0 /
                        (
                            1.0 +
                            Math.exp(
                                -riskScore + 4.0
                            )
                        )
                    );

            probability =
                    Math.max(
                            0.02,
                            Math.min(
                                    0.30,
                                    probability
                            )
                    );

            int label =
                    random.nextDouble()
                    < probability
                    ? 1
                    : 0;

            transactions.add(
                    new Transaction(
                            amount,
                            frequency,
                            international,
                            merchantRisk,
                            locationRisk,
                            deviceRisk,
                            accountAge,
                            previousFraud,
                            label
                    )
            );
        }

        return transactions;
    }
}
