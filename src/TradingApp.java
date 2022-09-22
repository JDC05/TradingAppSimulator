import java.util.Random;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TradingApp
{
    //File path to access stock DataStock folder in order to access the stock files	
    static Path currentRelativePath = Paths.get("");
    public static final String FILEPATH = currentRelativePath.toAbsolutePath().toString() + "\\src\\StockData\\";
    
    public static void main(String[] args) throws IOException
    {   	
    	Account account = createUserAccount();

    	Stock[][] stocks = createStocks();
    	
    	removeData(new File(FILEPATH));
    	createData(stocks);
    	   
    	Screen screen = new Screen(account, stocks);
    	
    	stockRates(stocks, screen);
    }
    
    // Rounding to 2 decimal place
    public static Double roundTo2DP(double num) 
    {
        DecimalFormat df = new DecimalFormat(".##");
        return (Double.parseDouble(df.format(num)));
    }

    // Rounding to 4 decimal place
    public static Double roundTo4DP(double num)
    {
        DecimalFormat df = new DecimalFormat(".####");
        return (Double.parseDouble(df.format(num)));
    }
    
    // sets the new price of 1 stock
    public static void setNewPrice(Stock stock, double change) 
    {
        stock.setPrice(roundTo4DP(change));
    }
    
    // Create the account for the user
    public static Account createUserAccount() 
    {
        CreateAccount userAccCreate = new CreateAccount();

        boolean successfull;
        Object simultaneous = new Object();
        
        // the do-while loop only breaks when the variables are synchronised
        do
        {
            synchronized (simultaneous)
            {
            	successfull = userAccCreate.getCreated();
            }
        } 
        while (!successfull);

        Account account = userAccCreate.getAccount();
        userAccCreate.frame.dispose();;
        return account;
    }
    
    // A timer to reset the main window every 5 seconds
    public static void stockRates(final Stock[][] stocks, final Screen screen) 
    {
        Timer t = new Timer();
        
        t.schedule(new TimerTask() 
        {
            @Override
            public void run() 
            {
            	stockRatePrices(stocks);
                screen.setValues(stocks);                                             
            }            
        }, 0, 5000);
    }    
    
    // sets new prices of all the stocks
    public static void stockRatePrices(Stock[][] stocks) 
    {
        Random rand = new Random();

        double priceChange;
        Stock changeStock;

        for (Stock[] sArray : stocks) 
        {
            for (Stock stock : sArray) 
            {
                priceChange = rand.nextDouble() + 0.1;
                int change = rand.nextInt(10) + 1;

                changeStock = stock;
                
                if (change > 6) 
                {
                	positivePriceChange(changeStock, priceChange);

                } 
                else 
                {
                	negativePriceChange(changeStock, priceChange);
                }

            }
        }

    }
    
    // Calculate a positive change in stock price
    public static void positivePriceChange(Stock stock, double priceChange) 
    {
        double change = stock.getPrice() + priceChange;
        setNewPrice(stock, change);
    }
    
    // Calculate a negative change in stock price
    public static void negativePriceChange(Stock stock, double priceChange) 
    {
    	double change = stock.getPrice() - priceChange;
    	setNewPrice(stock, change);
    }        
    
    // Creating all stocks
    public static Stock[][] createStocks() 
    {
        // BASIC INFORMATION FOR COMPANY STOCK
        String[] commonStockName = {"APPLE", "AMAZON", "TESLA", "MICROSOFT"};
        double[] commonPrice = {174.52, 3269.47, 1010.64, 309.92};

        // BASIC INFORMATION FOR ECONOMY STOCK
        String[] cryptoStockName = {"BITCOIN", "ETHEREUM", "XRP", "CARDANO"};
        String[] ABBstockName = {"BTC", "ETH", "XRP", "ADA"};
        double[] cryptoPrice = {35579.23, 3145.57, 91.69, 317.58};

        Stock[][] stocks = {createCommonStock(commonStockName, commonPrice), createCryptoStock(cryptoStockName, ABBstockName, cryptoPrice)};

        return stocks;
    }
    
    // create stocks via common class 
    public static Common[] createCommonStock(String[] commonStockName, double[] price)
    {
    	Common[] stocks = new Common[commonStockName.length];

        for (int i = 0; i < commonStockName.length; i++) 
        {
            stocks[i] = new Common(commonStockName[i], price[i]);
        }

        return stocks;
    }

    // create stocks via crypto class 
    public static Crypto[] createCryptoStock(String[] cryptoStockName, String[] ABBstockName, double[] price) 
    {
    	Crypto[] stocks = new Crypto[cryptoStockName.length];

        for (int i = 0; i < cryptoStockName.length; i++)
        {
            stocks[i] = new Crypto(cryptoStockName[i], ABBstockName[i], price[i]);
        }

        return stocks;
    }
    
    // Create the files for the stocks in csv format
    public static void createData(Stock[][] stocks) throws IOException 
    {
        String fileName;
        FileWriter fileWriter;

        for (Stock[] sArray : stocks) 
        {
            for (Stock stock : sArray)
            {
                fileName = stock.getStock() + ".csv";
                fileWriter = new FileWriter(FILEPATH + fileName);
                fileWriter.close();
            }
        } 

    }

    // Delete the csv files so they are empty at the beginning when the code is run
    public static void removeData(File directory) 
    {
        for (File file : directory.listFiles()) 
        {
            if (!file.isDirectory()) 
            {
                file.delete();
            }
        }

    }
}


