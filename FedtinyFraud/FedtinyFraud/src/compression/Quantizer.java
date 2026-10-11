
package compression;

import model.TinyModel;

public class Quantizer {

    public static int[] quantizeWeights(
            TinyModel model) {

        double[] weights =
                model.getWeights();

        int[] quantized =
                new int[weights.length];

        double max = 0.0;

        for (double weight : weights) {

            max = Math.max(
                    max,
                    Math.abs(weight)
            );
        }

        if (max == 0) {
            return quantized;
        }

        for (int i = 0;
             i < weights.length;
             i++) {

            double normalized =
                    weights[i] / max;

            quantized[i] =
                    (int) Math.round(
                            normalized * 127
                    );

            if (quantized[i] > 127) {
                quantized[i] = 127;
            }

            if (quantized[i] < -128) {
                quantized[i] = -128;
            }
        }

        return quantized;
    }

    public static int memoryBytesINT8(
            TinyModel model) {

        int parameters =
                model.getWeights().length + 1;

        return parameters;
    }

    public static int memoryBytesFP32(
            TinyModel model) {

        int parameters =
                model.getWeights().length + 1;

        return parameters * 4;
    }
}
