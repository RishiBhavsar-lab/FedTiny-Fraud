
package privacy;

import model.TinyModel;

import java.util.Random;

public class DifferentialPrivacy {

    private static final Random random =
            new Random();

    public static void addNoise(
            TinyModel model,
            double noiseLevel) {

        double[] weights =
                model.getWeights();

        for (int i = 0;
             i < weights.length;
             i++) {

            double noise =
                    random.nextGaussian()
                    * noiseLevel;

            weights[i] += noise;
        }

        double bias =
                model.getBias()
                + random.nextGaussian()
                * noiseLevel;

        model.setWeights(weights);

        model.setBias(bias);
    }
}
