import data.DataGenerator;
import data.NonIIDGenerator;
import data.Transaction;

import federation.Bank;
import federation.FederatedServer;

import evaluation.Metrics;

import java.util.ArrayList;
import java.util.List;
import model.ModelStats;
import model.InferenceBenchmark;
import compression.Pruner;
import federation.CommunicationStats;


public class Main {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("       FEDTINY-FRAUD");
        System.out.println("=================================");

        // Create banks
        List<Bank> banks =
                new ArrayList<>();
                List<Transaction> testData =
        DataGenerator.generate(1000);

        // Create data for each bank
       List<Transaction> bank1Data =
        NonIIDGenerator.generate(1000, 1);

List<Transaction> bank2Data =
        NonIIDGenerator.generate(1000, 2);

List<Transaction> bank3Data =
        NonIIDGenerator.generate(1000, 3);

List<Transaction> bank4Data =
        NonIIDGenerator.generate(1000, 4);

List<Transaction> bank5Data =
        NonIIDGenerator.generate(1000, 5);

        // Create Bank objects
        Bank bank1 =
                new Bank("Bank A", bank1Data);

        Bank bank2 =
                new Bank("Bank B", bank2Data);

        Bank bank3 =
                new Bank("Bank C", bank3Data);

        Bank bank4 =
                new Bank("Bank D", bank4Data);

        Bank bank5 =
                new Bank("Bank E", bank5Data);

        // Add banks to list
        banks.add(bank1);
        banks.add(bank2);
        banks.add(bank3);
        banks.add(bank4);
        banks.add(bank5);

        System.out.println();
        System.out.println("Banks created: "
                + banks.size());

        // Create federated server
        FederatedServer server =
                new FederatedServer();

        System.out.println(
                "Federated server created."
        );

        // Federated learning rounds
        int rounds = 5;

        for (int round = 1;
             round <= rounds;
             round++) {

            System.out.println();
            System.out.println(
                    "========== ROUND "
                    + round
                    + " =========="
            );

            // Send global model to banks
            server.distribute(banks);

            // Local training
            for (Bank bank : banks) {

                System.out.println(
                        bank.getName()
                        + " training..."
                );

                bank.train(5);
            }

            // Aggregate local models
            server.aggregate(banks);
            System.out.println(
        "Global model performance after round "
        + round
        + ":"
);

Metrics.evaluate(
        server.getGlobalModel(),
        testData
);

            System.out.println(
        "Server aggregated models."
);

System.out.println(
        "Global model updated."
);
            
        }

        System.out.println();
        System.out.println(
                "Federated learning complete."
        );

        // Evaluate global model
        System.out.println();
        System.out.println(
                "Global Model Performance:"
        );

        Metrics.evaluate(
        server.getGlobalModel(),
        testData
);
System.out.println();
System.out.println("Model Statistics:");

ModelStats.print(
        server.getGlobalModel()
);
System.out.println();
System.out.println("Inference Benchmark:");

InferenceBenchmark.measure(
        server.getGlobalModel(),
        testData
);
System.out.println();
System.out.println("Applying pruning...");

Pruner.prune(
        server.getGlobalModel(),
        0.05
);
System.out.println(
        "Performance after pruning:"
);

Metrics.evaluate(
        server.getGlobalModel(),
        testData
);
System.out.println();
System.out.println(
        "Communication Statistics:"
);

CommunicationStats.print(
        server.getGlobalModel()
);
        
    }

}
