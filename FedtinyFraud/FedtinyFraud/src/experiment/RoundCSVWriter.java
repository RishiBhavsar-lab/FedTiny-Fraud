
package experiment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RoundCSVWriter {

    private static final String FILE_NAME =
            "round_results.csv";

    public static void initialize() {

        File file =
                new File(FILE_NAME);

        if (file.exists()) {
            return;
        }

        try {

            FileWriter writer =
                    new FileWriter(FILE_NAME);

            writer.write(
                    "Experiment,"
                    + "Round,"
                    + "Accuracy,"
                    + "Precision,"
                    + "Recall,"
                    + "F1\n"
            );

            writer.close();

        } catch (IOException e) {

            System.out.println(
                    "Error creating round CSV: "
                    + e.getMessage()
            );
        }
    }

    public static void writeResult(
            RoundResult result) {

        try {

            FileWriter writer =
                    new FileWriter(
                            FILE_NAME,
                            true
                    );

            writer.write(
                    result.getExperimentName()
                    + ","
                    + result.getRound()
                    + ","
                    + result.getAccuracy()
                    + ","
                    + result.getPrecision()
                    + ","
                    + result.getRecall()
                    + ","
                    + result.getF1()
                    + "\n"
            );

            writer.close();

        } catch (IOException e) {

            System.out.println(
                    "Error writing round result: "
                    + e.getMessage()
            );
        }
    }
}
