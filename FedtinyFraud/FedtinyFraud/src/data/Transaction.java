package data;

public class Transaction {

    private double amount;
    private double frequency;
    private double international;
    private double merchantRisk;
    private double locationRisk;
    private double deviceRisk;
    private double accountAge;
    private double previousFraud;
    private int label;

    public Transaction(
            double amount,
            double frequency,
            double international,
            double merchantRisk,
            double locationRisk,
            double deviceRisk,
            double accountAge,
            double previousFraud,
            int label) {

        this.amount = amount;
        this.frequency = frequency;
        this.international = international;
        this.merchantRisk = merchantRisk;
        this.locationRisk = locationRisk;
        this.deviceRisk = deviceRisk;
        this.accountAge = accountAge;
        this.previousFraud = previousFraud;
        this.label = label;
    }

    public double getAmount() {
        return amount;
    }

    public double getFrequency() {
        return frequency;
    }

    public double getInternational() {
        return international;
    }

    public double getMerchantRisk() {
        return merchantRisk;
    }

    public double getLocationRisk() {
        return locationRisk;
    }

    public double getDeviceRisk() {
        return deviceRisk;
    }

    public double getAccountAge() {
        return accountAge;
    }

    public double getPreviousFraud() {
        return previousFraud;
    }

    public int getLabel() {
        return label;
    }
}
