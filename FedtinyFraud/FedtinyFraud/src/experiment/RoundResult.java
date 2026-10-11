
package experiment;

public class RoundResult {

    private String experimentName;
    private int round;

    private double accuracy;
    private double precision;
    private double recall;
    private double f1;

    public RoundResult(
            String experimentName,
            int round,
            double accuracy,
            double precision,
            double recall,
            double f1) {

        this.experimentName =
                experimentName;

        this.round = round;

        this.accuracy = accuracy;

        this.precision = precision;

        this.recall = recall;

        this.f1 = f1;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public int getRound() {
        return round;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getPrecision() {
        return precision;
    }

    public double getRecall() {
        return recall;
    }

    public double getF1() {
        return f1;
    }
}
