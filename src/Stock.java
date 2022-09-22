import java.util.Random;

public class Stock
{
    private final String stock;
    private double price;
    private int quantity;

    // Constructor for bought stocks
    public Stock(String stock, double price, int quantity) 
    {
    	this.stock = stock;
        this.price = price;
        this.quantity = quantity;
    }

    // Constructor for stocks when program is launched
    public Stock(String stock, double price) 
    {
    	this.stock = stock;
        this.price = price;
    }

    // Getter methods are set
    public int getQuantity() 
    {
        return this.quantity;
    }
    
    public String getStock() 
    {
        return this.stock;
    }

    public double getPrice() 
    {
        return this.price;
    }

    // Setter methods are set
    public void setQuantity(int quantity) 
    {
        this.quantity = quantity;
    }
    
    public void setPrice(double buyPrice) 
    {
        this.price = buyPrice;
    }

	public double fee()
	{
		return 0.05;
	}

	public double discount()
	{
		return 0.01;
	}
	
	public int freeStock()
	{
		Random rand = new Random();
		int change = rand.nextInt(1) + 1;
		
		return change;
	}
    
}

