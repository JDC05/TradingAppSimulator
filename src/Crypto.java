import java.util.Random;

public class Crypto extends Stock implements Payment
{
    private final String cryptoStock;
    
    // Constructor
    public Crypto(String cryptoStock, String stockName, double price)
    {
        super(stockName, price);
        this.cryptoStock = cryptoStock;
    }

	@Override
	public double fee()
	{
		return 0.015;
	}

	@Override
	public double discount()
	{
		return 0.04;
	}

	@Override
	public int freeStock()
	{
		Random rand = new Random();
		int change = rand.nextInt(2) + 1;
		
		return change;
	}

}
