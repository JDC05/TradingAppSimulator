import java.util.Random;

public class Common extends Stock implements Payment
{
    // Constructor
    public Common(String stockName, double price)
    {
        super(stockName, price);
    }

	@Override
	public double fee()
	{
		return 0.025;
	}

	@Override
	public double discount()
	{
		return 0.02;
	}

	@Override
	public int freeStock()
	{
		Random rand = new Random();
		int change = rand.nextInt(3) + 1;
		
		return change;
	}

}
