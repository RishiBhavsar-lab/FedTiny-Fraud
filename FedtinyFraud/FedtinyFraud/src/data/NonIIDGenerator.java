
package data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class NonIIDGenerator {

    private static final Random random =
            new Random();

    private static final double FRAUD_RATE = 0.15;

    /*
     * Temporary internal class.
     * It stores transaction features together
     * with their calculated risk score.
     */
    private static class ScoredTransaction {

        double amount;
        double frequency;
        double international;
        double merchantRisk;
        double locationRisk;
        double deviceRisk;
        double accountAge;
        double previousFraud;
        double riskScore;

        ScoredTransaction(
                double amount,
                double frequency,
                double international,
                double merchantRisk,
                double locationRisk,
                double deviceRisk,
                double accountAge,
                double previousFraud,
                double riskScore) {

            this.amount = amount;
            this.frequency = frequency;
            this.international = international;
            this.merchantRisk = merchantRisk;
            this.locationRisk = locationRisk;
            this.deviceRisk = deviceRisk;
            this.accountAge = accountAge;
            this.previousFraud = previousFraud;
            this.riskScore = riskScore;
        }
    }

    public static List<Transaction> generate(
            int count,
            int bankType) {

        List<ScoredTransaction> scoredTransactions =
                new ArrayList<>();

        /*
         * STEP 1:
         * Generate bank-specific feature distributions.
         */

        for (int i = 0; i < count; i++) {

            double amount;
            double frequency;
            double international;
            double merchantRisk;
            double locationRisk;
            double deviceRisk;
            double accountAge;
            double previousFraud;

            if (bankType == 1) {

                /*
                 * Bank 1:
                 * Lower transaction activity.
                 */

                amount =
                        100 + random.nextDouble() * 4000;

                frequency =
                        1 + random.nextDouble() * 8;

                international =
                        random.nextDouble() < 0.20
                        ? 1.0 : 0.0;

                merchantRisk =
                        random.nextDouble() * 0.5;

                locationRisk =
                        random.nextDouble() * 0.5;

                deviceRisk =
                        random.nextDouble() * 0.5;

                accountAge =
                        40 + random.nextDouble() * 80;

                previousFraud =
                        random.nextDouble() < 0.05
                        ? 1.0 : 0.0;

            } else if (bankType == 2) {

                /*
                 * Bank 2:
                 * Higher amount and international activity.
                 */

                amount =
                        3000 + random.nextDouble() * 7000;

                frequency =
                        8 + random.nextDouble() * 12;

                international =
                        random.nextDouble() < 0.70
                        ? 1.0 : 0.0;

                merchantRisk =
                        0.5 + random.nextDouble() * 0.5;

                locationRisk =
                        random.nextDouble() * 0.5;

                deviceRisk =
                        random.nextDouble() * 0.5;

                accountAge =
                        40 + random.nextDouble() * 80;

                previousFraud =
                        random.nextDouble() < 0.05
                        ? 1.0 : 0.0;

            } else if (bankType == 3) {

                /*
                 * Bank 3:
                 * Higher device risk.
                 */

                amount =
                        1000 + random.nextDouble() * 5000;

                frequency =
                        3 + random.nextDouble() * 12;

                international =
                        random.nextDouble() < 0.40
                        ? 1.0 : 0.0;

                merchantRisk =
                        random.nextDouble() * 0.5;

                locationRisk =
                        random.nextDouble() * 0.5;

                deviceRisk =
                        0.5 + random.nextDouble() * 0.5;

                accountAge =
                        40 + random.nextDouble() * 80;

                previousFraud =
                        random.nextDouble() < 0.05
                        ? 1.0 : 0.0;

            } else if (bankType == 4) {

                /*
                 * Bank 4:
                 * Higher merchant risk.
                 */

                amount =
                        1000 + random.nextDouble() * 6000;

                frequency =
                        3 + random.nextDouble() * 12;

                international =
                        random.nextDouble() < 0.40
                        ? 1.0 : 0.0;

                merchantRisk =
                        0.5 + random.nextDouble() * 0.5;

                locationRisk =
                        random.nextDouble() * 0.5;

                deviceRisk =
                        random.nextDouble() * 0.5;

                accountAge =
                        40 + random.nextDouble() * 80;

                previousFraud =
                        random.nextDouble() < 0.05
                        ? 1.0 : 0.0;

            } else {

                /*
                 * Bank 5:
                 * Higher location risk.
                 */

                amount =
                        1000 + random.nextDouble() * 6000;

                frequency =
                        3 + random.nextDouble() * 12;

                international =
                        random.nextDouble() < 0.40
                        ? 1.0 : 0.0;

                merchantRisk =
                        random.nextDouble() * 0.5;

                locationRisk =
                        0.5 + random.nextDouble() * 0.5;

                deviceRisk =
                        random.nextDouble() * 0.5;

                accountAge =
                        40 + random.nextDouble() * 80;

                previousFraud =
                        random.nextDouble() < 0.05
                        ? 1.0 : 0.0;
            }

            /*
             * STEP 2:
             * Use the SAME fraud-risk rule for every bank.
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

            scoredTransactions.add(
                    new ScoredTransaction(
                            amount,
                            frequency,
                            international,
                            merchantRisk,
                            locationRisk,
                            deviceRisk,
                            accountAge,
                            previousFraud,
                            riskScore
                    )
            );
        }

        /*
         * STEP 3:
         * Sort transactions from highest risk
         * to lowest risk.
         */

        scoredTransactions.sort(
                Comparator.comparingDouble(
                        (ScoredTransaction transaction)
                                -> transaction.riskScore
                ).reversed()
        );

        /*
         * STEP 4:
         * Exactly 15% of transactions become fraud.
         */

        int fraudCount =
                (int) Math.round(
                        count * FRAUD_RATE
                );

        /*
         * STEP 5:
         * Create the final transaction list.
         */

        List<Transaction> transactions =
                new ArrayList<>();

        for (int i = 0;
             i < scoredTransactions.size();
             i++) {

            ScoredTransaction transaction =
                    scoredTransactions.get(i);

            int label =
                    i < fraudCount
                    ? 1
                    : 0;

            transactions.add(
                    new Transaction(
                            transaction.amount,
                            transaction.frequency,
                            transaction.international,
                            transaction.merchantRisk,
                            transaction.locationRisk,
                            transaction.deviceRisk,
                            transaction.accountAge,
                            transaction.previousFraud,
                            label
                    )
            );
        }

        return transactions;
    }
}
