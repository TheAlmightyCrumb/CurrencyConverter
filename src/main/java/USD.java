public class USD extends Coin {
    private static final double VALUE = 3.52;

    @Override
    public double getValue() {
        return VALUE;
    }

    @Override
    public double calculate(double num) {
        return num * this.getValue();
    }
}
