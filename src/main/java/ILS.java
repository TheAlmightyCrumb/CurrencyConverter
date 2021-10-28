public class ILS extends Coin {
    private static final double VALUE = 0.28;

    @Override
    public double getValue() {
        return VALUE;
    }

    @Override
    public double calculate(double num) {
        return num * this.getValue();
    }
}
