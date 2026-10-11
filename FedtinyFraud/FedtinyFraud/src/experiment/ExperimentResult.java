
package experiment;

public class ExperimentResult {

    private String experimentName;

    private double accuracy;
    private double precision;
    private double recall;
    private double f1;

    private int modelSizeBytes;
    private int communicationBytes;

    private double inferenceTimeMs;

    public ExperimentResult(
            String experimentName) {

        this.experimentName =
                experimentName;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public void setRecall(double recall) {
        this.recall = recall;
    }

    public void setF1(double f1) {
        this.f1 = f1;
    }

    public void setModelSizeBytes(
            int modelSizeBytes) {

        this.modelSizeBytes =
                modelSizeBytes;
    }

    public void setCommunicationBytes(
            int communicationBytes) {

        this.communicationBytes =
                communicationBytes;
    }

    public void setInferenceTimeMs(
            double inferenceTimeMs) {

        this.inferenceTimeMs =
                inferenceTimeMs;
    }

    public String getExperimentName() {
        return experimentName;
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

    public int getModelSizeBytes() {
        return modelSizeBytes;
    }

    public int getCommunicationBytes() {
        return communicationBytes;
    }

    public double getInferenceTimeMs() {
        return inferenceTimeMs;
    }
}
