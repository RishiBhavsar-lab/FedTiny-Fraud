
import experiment.CSVWriter;
import experiment.RoundCSVWriter;
import experiment.ExperimentConfig;
import experiment.ExperimentResult;
import experiment.ExperimentRunner;

public class ExperimentMain {

    public static void main(String[] args) {

        System.out.println(
                "================================="
        );

        System.out.println(
                "     FEDTINY-FRAUD EXPERIMENTS"
        );

        System.out.println(
                "================================="
        );

        /*
         * INITIALIZE CSV FILES
         */

        CSVWriter.initialize();

        RoundCSVWriter.initialize();

        /*
         * EXPERIMENT 1
         * IID + FP32
         */

        ExperimentConfig exp1 =
                new ExperimentConfig();

        exp1.nonIID = false;
        exp1.pruning = false;
        exp1.quantization = false;
        exp1.differentialPrivacy = false;

        ExperimentResult result1 =
                ExperimentRunner.run(
                        "IID_FP32",
                        exp1
                );

        CSVWriter.writeResult(
                result1
        );

        /*
         * EXPERIMENT 2
         * NON-IID + FP32
         */

        ExperimentConfig exp2 =
                new ExperimentConfig();

        exp2.nonIID = true;
        exp2.pruning = false;
        exp2.quantization = false;
        exp2.differentialPrivacy = false;

        ExperimentResult result2 =
                ExperimentRunner.run(
                        "NONIID_FP32",
                        exp2
                );

        CSVWriter.writeResult(
                result2
        );

        /*
         * EXPERIMENT 3
         * NON-IID + PRUNING
         */

        ExperimentConfig exp3 =
                new ExperimentConfig();

        exp3.nonIID = true;
        exp3.pruning = true;
        exp3.quantization = false;
        exp3.differentialPrivacy = false;

        ExperimentResult result3 =
                ExperimentRunner.run(
                        "NONIID_PRUNING",
                        exp3
                );

        CSVWriter.writeResult(
                result3
        );

        /*
         * EXPERIMENT 4
         * NON-IID + INT8
         */

        ExperimentConfig exp4 =
                new ExperimentConfig();

        exp4.nonIID = true;
        exp4.pruning = false;
        exp4.quantization = true;
        exp4.differentialPrivacy = false;

        ExperimentResult result4 =
                ExperimentRunner.run(
                        "NONIID_INT8",
                        exp4
                );

        CSVWriter.writeResult(
                result4
        );

        /*
         * EXPERIMENT 5
         * NON-IID + PRUNING + INT8
         */

        ExperimentConfig exp5 =
                new ExperimentConfig();

        exp5.nonIID = true;
        exp5.pruning = true;
        exp5.quantization = true;
        exp5.differentialPrivacy = false;

        ExperimentResult result5 =
                ExperimentRunner.run(
                        "NONIID_PRUNING_INT8",
                        exp5
                );

        CSVWriter.writeResult(
                result5
        );

        /*
         * EXPERIMENT 6
         * NON-IID + PRUNING + INT8 + DP
         */

        ExperimentConfig exp6 =
                new ExperimentConfig();

        exp6.nonIID = true;
        exp6.pruning = true;
        exp6.quantization = true;
        exp6.differentialPrivacy = true;

        ExperimentResult result6 =
                ExperimentRunner.run(
                        "NONIID_PRUNING_INT8_DP",
                        exp6
                );

        CSVWriter.writeResult(
                result6
        );

        /*
         * EXPERIMENTS COMPLETE
         */

        System.out.println();

        System.out.println(
                "================================="
        );

        System.out.println(
                "ALL EXPERIMENTS COMPLETE"
        );

        System.out.println(
                "Results saved to results.csv"
        );

        System.out.println(
                "Round results saved to round_results.csv"
        );

        System.out.println(
                "================================="
        );
    }
}
