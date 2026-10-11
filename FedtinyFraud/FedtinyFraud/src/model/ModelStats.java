
package model;

public class ModelStats {

    public static int parameterCount(
            TinyModel model) {

        return model.getWeights().length + 1;
    }

    public static int memoryBytesFP32(
            TinyModel model) {

        return parameterCount(model) * 4;
    }

    public static void print(
            TinyModel model) {

        int parameters =
                parameterCount(model);

        int memory =
                memoryBytesFP32(model);

        System.out.println(
                "Parameters: "
                + parameters
        );

        System.out.println(
                "FP32 memory: "
                + memory
                + " bytes"
        );
    }
}
