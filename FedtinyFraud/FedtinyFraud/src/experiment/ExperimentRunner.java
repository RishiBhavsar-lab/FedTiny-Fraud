
package experiment;

import compression.Pruner;
import compression.Quantizer;
import data.DataGenerator;
import data.NonIIDGenerator;
import data.Transaction;
import evaluation.Metrics;
import federation.Bank;
import federation.CommunicationStats;
import federation.FederatedServer;
import model.TinyModel;
import privacy.DifferentialPrivacy;

import java.util.ArrayList;
import java.util.List;

public class ExperimentRunner {

    public static ExperimentResult run(
            String experimentName,
            ExperimentConfig config) {

        System.out.println();
        System.out.println("=================================");
        System.out.println(
                "EXPERIMENT: " + experimentName
        );
        System.out.println("=================================");

        List<Bank> banks =
                new ArrayList<>();

        /*
         * CREATE BANK DATA
         */

        for (int i = 0;
             i < config.numberOfBanks;
             i++) {

            List<Transaction> data;

            if (config.nonIID) {

                data =
                        NonIIDGenerator.generate(
                                config.transactionsPerBank,
                                i + 1
                        );

            } else {

                data =
                        DataGenerator.generate(
                                config.transactionsPerBank
                        );
            }

            Bank bank =
                    new Bank(
                            "Bank " + (char) ('A' + i),
                            data
                    );

            banks.add(bank);

            System.out.println(
        bank.getName()
        + " fraud transactions: "
        + bank.fraudCount()
        + " / "
        + bank.getData().size()
);
           
        }
        
        /*
         * CREATE TEST DATA
         */

        List<Transaction> testData;

        if (config.nonIID) {

            testData =
                    NonIIDGenerator.generate(
                            1000,
                            1
                    );

        } else {

            testData =
                    DataGenerator.generate(
                            1000
                    );
        }

        /*
         * CREATE FEDERATED SERVER
         */

        FederatedServer server =
                new FederatedServer();

        /*
         * FEDERATED TRAINING ROUNDS
         */

        for (int round = 1;
             round <= config.rounds;
             round++) {

            System.out.println();

            System.out.println(
                    "Round "
                    + round
                    + " / "
                    + config.rounds
            );

            /*
             * SEND GLOBAL MODEL TO BANKS
             */

            server.distribute(banks);

            /*
             * LOCAL TRAINING
             */

            for (Bank bank : banks) {

                bank.train(
                        config.localEpochs
                );
            }

            /*
             * SERVER AGGREGATION
             */

            server.aggregate(banks);

            /*
             * GET GLOBAL MODEL
             */

            TinyModel roundModel =
                    server.getGlobalModel();

            /*
             * EVALUATE AFTER EVERY ROUND
             */

            double[] roundMetrics =
                    Metrics.calculate(
                            roundModel,
                            testData
                    );

            System.out.println(
                    "Round Accuracy  : "
                    + roundMetrics[0]
            );

            System.out.println(
                    "Round Precision : "
                    + roundMetrics[1]
            );

            System.out.println(
                    "Round Recall    : "
                    + roundMetrics[2]
            );

            System.out.println(
                    "Round F1        : "
                    + roundMetrics[3]
            );

            /*
             * SAVE ROUND RESULT
             */

            RoundResult roundResult =
                    new RoundResult(
                            experimentName,
                            round,
                            roundMetrics[0],
                            roundMetrics[1],
                            roundMetrics[2],
                            roundMetrics[3]
                    );

            RoundCSVWriter.writeResult(
                    roundResult
            );
        }

        /*
         * FINAL GLOBAL MODEL
         */

        TinyModel globalModel =
                server.getGlobalModel();

        /*
         * PRUNING
         */

        if (config.pruning) {

            System.out.println(
                    "Applying pruning..."
            );

            Pruner.prune(
                    globalModel,
                    config.pruningThreshold
            );
        }

        /*
         * DIFFERENTIAL PRIVACY
         */

        if (config.differentialPrivacy) {

            System.out.println(
                    "Applying differential privacy..."
            );

            DifferentialPrivacy.addNoise(
                    globalModel,
                    config.privacyNoise
            );
        }

        /*
         * FINAL EVALUATION
         */

        double[] metrics =
                Metrics.calculate(
                        globalModel,
                        testData
                );

        System.out.println();
        System.out.println("Results:");

        System.out.println(
                "Accuracy  : "
                + metrics[0]
        );

        System.out.println(
                "Precision : "
                + metrics[1]
        );

        System.out.println(
                "Recall    : "
                + metrics[2]
        );

        System.out.println(
                "F1 Score  : "
                + metrics[3]
        );

        /*
         * MODEL SIZE
         */

        int modelSize;

        if (config.quantization) {

            modelSize =
                    Quantizer.memoryBytesINT8(
                            globalModel
                    );

        } else {

            modelSize =
                    CommunicationStats.fp32Bytes(
                            globalModel
                    );
        }

        /*
         * COMMUNICATION SIZE
         */

        int communication;

        if (config.quantization) {

            communication =
                    CommunicationStats.int8Bytes(
                            globalModel
                    );

        } else {

            communication =
                    CommunicationStats.fp32Bytes(
                            globalModel
                    );
        }

        /*
         * INFERENCE TIME
         */

        long start =
                System.nanoTime();

        for (Transaction transaction :
                testData) {

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

            globalModel.predict(
                    features
            );
        }

        long end =
                System.nanoTime();

        double inferenceTime =
                (end - start)
                / 1_000_000.0;

        System.out.println(
                "Model size: "
                + modelSize
                + " bytes"
        );

        System.out.println(
                "Communication: "
                + communication
                + " bytes"
        );

        System.out.println(
                "Inference time: "
                + inferenceTime
                + " ms"
        );

        /*
         * CREATE FINAL RESULT
         */

        ExperimentResult result =
                new ExperimentResult(
                        experimentName
                );

        result.setAccuracy(
                metrics[0]
        );

        result.setPrecision(
                metrics[1]
        );

        result.setRecall(
                metrics[2]
        );

        result.setF1(
                metrics[3]
        );

        result.setModelSizeBytes(
                modelSize
        );

        result.setCommunicationBytes(
                communication
        );

        result.setInferenceTimeMs(
                inferenceTime
        );

        return result;
    }
}
