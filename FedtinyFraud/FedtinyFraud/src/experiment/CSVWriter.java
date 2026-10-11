
package experiment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {

    private static final String FILE_NAME =
            "results.csv";

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
                    + "Accuracy,"
                    + "Precision,"
                    + "Recall,"
                    + "F1,"
                    + "ModelSizeBytes,"
                    + "CommunicationBytes,"
                    + "InferenceTimeMs\n"
            );

            writer.close();

        } catch (IOException e) {

            System.out.println(
                    "Error creating CSV: "
                    + e.getMessage()
            );
        }
    }

    public static void writeResult(
            ExperimentResult result) {

        try {

            FileWriter writer =
                    new FileWriter(
                            FILE_NAME,
                            true
                    );

            writer.write(
                    result.getExperimentName()
                    + ","
                    + result.getAccuracy()
                    + ","
                    + result.getPrecision()
                    + ","
                    + result.getRecall()
                    + ","
                    + result.getF1()
                    + ","
                    + result.getModelSizeBytes()
                    + ","
                    + result.getCommunicationBytes()
                    + ","
                    + result.getInferenceTimeMs()
                    + "\n"
            );

            writer.close();

        } catch (IOException e) {

            System.out.println(
                    "Error writing result: "
                    + e.getMessage()
            );
        }
    }
}
