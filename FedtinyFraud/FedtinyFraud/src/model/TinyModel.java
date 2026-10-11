
package model;

public class TinyModel {

    public static final int FEATURES = 8;

    private double[] weights;
    private double bias;

    public TinyModel() {

        weights = new double[FEATURES];

        for (int i = 0; i < FEATURES; i++) {
            weights[i] = 0.0;
        }

        bias = 0.0;
    }

    public static double[] normalizeFeatures(
            double[] features) {

        double[] normalized =
                new double[FEATURES];

        normalized[0] =
                features[0] / 10000.0;

        normalized[1] =
                features[1] / 20.0;

        normalized[2] =
                features[2];

        normalized[3] =
                features[3];

        normalized[4] =
                features[4];

        normalized[5] =
                features[5];

        normalized[6] =
                features[6] / 120.0;

        normalized[7] =
                features[7];

        return normalized;
    }

    public double predictProbability(
            double[] features) {

        double[] normalized =
                normalizeFeatures(features);

        double z = bias;

        for (int i = 0;
             i < FEATURES;
             i++) {

            z +=
                    weights[i]
                    * normalized[i];
        }

        return sigmoid(z);
    }

    private double sigmoid(double value) {

        return 1.0 /
                (1.0 + Math.exp(-value));
    }

    public int predict(double[] features) {

        /*
         * Lower threshold from 0.50 to 0.35
         * so the model detects more potential
         * fraud transactions.
         */
        if (predictProbability(features) >= 0.35) {
            return 1;
        }

        return 0;
    }

    public double[] getWeights() {
        return weights;
    }

    public double getBias() {
        return bias;
    }

    public void setWeights(double[] weights) {
        this.weights = weights.clone();
    }

    public void setBias(double bias) {
        this.bias = bias;
    }
}
