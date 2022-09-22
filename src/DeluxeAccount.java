import java.util.ArrayList;

public class DeluxeAccount extends Account
{
	private ArrayList<Stock> stocksObtained;

	// Constructor
    public DeluxeAccount(String nameOfAccount, double accountBalance, double amount)
    {
    	super(nameOfAccount, accountBalance);
    	this.deposit(amount);
        this.stocksObtained = new ArrayList<Stock>();
    }
    
    @Override
    public double cutFee()
    {
        return 0.5;
    }
}
