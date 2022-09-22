import java.util.ArrayList;

public class StandardAccount extends Account
{
	private ArrayList<Stock> stocksObtained;

	// Constructor
    public StandardAccount(String nameOfAccount, double accountBalance)
    {
    	super(nameOfAccount, accountBalance);
        this.stocksObtained = new ArrayList<Stock>();
    }
    
    @Override
    public double cutFee()
    {
        return 0.005;
    }
}
