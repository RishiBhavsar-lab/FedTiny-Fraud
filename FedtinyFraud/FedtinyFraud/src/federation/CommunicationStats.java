
package federation;

import model.TinyModel;

public class CommunicationStats {

    public static int fp32Bytes(
            TinyModel model) {

        int parameters =
                model.getWeights().length + 1;

        return parameters * 4;
    }

    public static int int8Bytes(
            TinyModel model) {

        int parameters =
                model.getWeights().length + 1;

        return parameters;
    }

    public static void print(
            TinyModel model) {

        int fp32 =
                fp32Bytes(model);

        int int8 =
                int8Bytes(model);

        System.out.println(
                "FP32 communication: "
                + fp32
                + " bytes"
        );

        System.out.println(
                "INT8 communication: "
                + int8
                + " bytes"
        );
    }
}
