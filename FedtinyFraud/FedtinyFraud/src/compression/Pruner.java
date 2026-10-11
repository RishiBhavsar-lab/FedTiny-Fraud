
package compression;

import model.TinyModel;

public class Pruner {

    public static void prune(
            TinyModel model,
            double threshold) {

        double[] weights =
                model.getWeights();

        for (int i = 0;
             i < weights.length;
             i++) {

            if (Math.abs(weights[i])
                    < threshold) {

                weights[i] = 0.0;
            }
        }

        model.setWeights(weights);
    }
}
