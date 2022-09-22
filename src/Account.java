import java.util.*;

public abstract class Account
{
	private String nameOfAccount;
    private double accountBalance;
    private ArrayList<Stock> stocksObtained;

    // Constructor
    public Account(String nameOfAccount, double accountBalance)
    {
    	setAccountName(nameOfAccount);
    	setBalance(accountBalance);
        this.stocksObtained = new ArrayList<Stock>();
    }    
    
    // Getter methods are set
    public String getAccountName()
    {
        return nameOfAccount;
    }
    
    public double getBalance()
    {
        return accountBalance;
    }
    
    public ArrayList<Stock> getStocks()
    {
        return stocksObtained;
    }
    
    // Setter methods are set
    public void setAccountName(String nameOfAccount)
    {
    	this.nameOfAccount += nameOfAccount;
    }
    
    public void setBalance(double amount)
    {
    	this.accountBalance += amount;
    }
    
    
    public void deposit(double amount)
    {
    	this.accountBalance += amount;
    } 
    
    public void withdraw(double amount)
    {
    	this.accountBalance -= amount;
    }

    public void addStock(Stock s)
    {
    	this.stocksObtained.add(s);
    }
    
    public void removeStock(Stock s)
    {
    	this.stocksObtained.remove(s);
    }
    
    public abstract double cutFee();
}
