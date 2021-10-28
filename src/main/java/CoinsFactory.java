public class CoinsFactory {
    public static Coin getCoinInstance(Coins coin) throws Exception {
        switch (coin) {
            case ILS:
                return new ILS();
            case USD:
                return new USD();
        }
        throw new Exception("Oops, seems like something went wrong!");
    }
}
