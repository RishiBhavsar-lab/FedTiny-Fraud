
package federation;

import data.Transaction;
import model.TinyModel;
import model.Trainer;

import java.util.List;

public class Bank {

    private final String name;
    private final List<Transaction> data;
    private TinyModel model;

    public Bank(
            String name,
            List<Transaction> data) {

        this.name = name;
        this.data = data;
        this.model = new TinyModel();
    }
    public int fraudCount() {

    int count = 0;

    for (Transaction transaction : data) {

        if (transaction.getLabel() == 1) {
            count++;
        }
    }

    return count;
}

    public void train(int epochs) {

        Trainer.train(
                model,
                data,
                epochs
        );
    }

    public String getName() {
        return name;
    }

    public List<Transaction> getData() {
        return data;
    }

    public TinyModel getModel() {
        return model;
    }

    public void setModel(TinyModel model) {

        TinyModel copy =
                new TinyModel();

        copy.setWeights(
                model.getWeights()
        );

        copy.setBias(
                model.getBias()
        );

        this.model = copy;
    }
}
