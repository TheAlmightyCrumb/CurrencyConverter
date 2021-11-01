public class Result {
    private double value;
    private Coins type;

    public Result(double value, Coins type) {
        this.value = value;
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public Coins getType() {
        return type;
    }
}
