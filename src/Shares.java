import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;

public class Shares
{
    JComboBox<String> stockNames;
    JTextField stockAmount;

    Account account;
    
    Stock[][] stocks;

    JFrame frame;

    // Constructor
    public Shares(Account account, Stock[][] stockData)
    {
    	this.account = account;
        stocks = stockData;

        ButtonListener listener = new ButtonListener();

        frame = new JFrame();
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 30));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 12, 10));

        ArrayList<String> assetNames = new ArrayList<String>();

        for (Stock[] assetList : stockData)
        {
            for (Stock stock : assetList)
            {
            	assetNames.add(stock.getStock());
            }
        }

        String[] nameOfShares = new String[assetNames.size()];

        for (int i = 0; i < nameOfShares.length; i++)
        {
        	nameOfShares[i] = assetNames.get(i);
        }

        JLabel stockNameLabel = new JLabel("Stock Name");
        stockNames = new JComboBox<>(nameOfShares);

        JLabel quantityLabel = new JLabel("Quantity");
        stockAmount = new JTextField("");

        panel.add(stockNameLabel);
        panel.add(stockNames);

        panel.add(quantityLabel);
        panel.add(stockAmount);

        Button buyButton = new Button("Buy");
        Button sellButton = new Button("Sell");

        buyButton.addActionListener(listener);
        sellButton.addActionListener(listener);

        panel.add(buyButton);
        panel.add(sellButton);

        frame.add(panel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    // Rounding to 2 decimal place
    public static Double roundTo2dp(double number)
    {
        DecimalFormat roundFormat = new DecimalFormat(".##");
        return (Double.parseDouble(roundFormat.format(number)));
    }

    // Event handler for when the 'buy' and 'sell' buttons are pressed
    public class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent a)
        {
            try
            {
                String nameOfStock = (String) stockNames.getSelectedItem();

                int quantity = Integer.parseInt(stockAmount.getText());

                Stock availableAssets = searchFreeStock(nameOfStock);

                if (availableAssets != null)
                {

                    if (a.getActionCommand().equals("Buy"))
                    {
                        buyStock(availableAssets, quantity);
                    }

                    if (a.getActionCommand().equals("Sell"))
                    {
                    	Stock boughtAssets = searchObtainedStock(nameOfStock);
                    	
                        if (boughtAssets != null)
                        {
                            sellStock(boughtAssets, quantity, nameOfStock);
                        }                         
                        else
                        {
                            JOptionPane.showMessageDialog(null, "You Don't Own This Stock", "ERROR", JOptionPane.WARNING_MESSAGE);
                        }
                    }

                    frame.dispose();
                }
            }           
            catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }      
    
    // Allows user to sell stock
    public void sellStock(Stock stock, int quantity, String nameOfStock) 
    {
        if (quantity <= stock.getQuantity()) 
        {
        	double amount = (stock.getPrice() * quantity);
            account.deposit(amount);
            
            if (quantity == stock.getQuantity()) 
            {
            	account.removeStock(stock);
            } 
            
            else
            {
            	stock.setQuantity(stock.getQuantity() - quantity);
            }

            JOptionPane.showMessageDialog(null, "Balance: " + roundTo2dp(account.getBalance()), "Successful Trade", JOptionPane.WARNING_MESSAGE);
        } 
        
        else 
        {
            JOptionPane.showMessageDialog(null, "You Do Not Own This Many Stock", "ERROR", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // searches for the stock that the user has when selling
    public Stock searchObtainedStock(String stockName)
    {
        ArrayList<Stock> stocksObtained = account.getStocks();
        
        for (int i = 0; i < stocksObtained.size(); i++)
        {
            if (stocksObtained.get(i).getStock().equalsIgnoreCase(stockName))
            {
                return stocksObtained.get(i);
            }
        }
        
        return null;
    }

    // Allows user to buy stock
    public void buyStock(Stock stock, int quantity) 
    {
    	Random rand = new Random();
    	int discountChange = rand.nextInt(10) + 1;
    	int freeDiscountChange = rand.nextInt(10) + 1;
  
        double amount = (stock.getPrice() * quantity) * (1 + (stock.fee() * account.cutFee()));
        
        
        //There can be a chance that the user gets a discount or some free stock when they buy
        if(discountChange != freeDiscountChange)
        {
        	if (discountChange > 7) 
            {
            	amount -= amount*stock.discount();
            	JOptionPane.showMessageDialog(null, "Discount: " + roundTo2dp(stock.discount()*amount), "Successful Discount", JOptionPane.WARNING_MESSAGE);
            }
        	
        	if (freeDiscountChange > 8) 
            {
        		quantity += quantity + stock.freeStock();
        		JOptionPane.showMessageDialog(null, stock.freeStock() + " FREE STOCK ON US!", "Free Stock", JOptionPane.WARNING_MESSAGE);
            }
        }      
        
        if (amount < account.getBalance()) 
        {
        	Stock newStock = new Stock(stock.getStock(), stock.getPrice(), quantity);
        	account.withdraw(amount);
        	account.addStock(newStock);

        	JOptionPane.showMessageDialog(null, "Balance: " + roundTo2dp(account.getBalance()), "Successful Trade", JOptionPane.WARNING_MESSAGE);
        }         
        else
        {
            JOptionPane.showMessageDialog(null, "You Don't Have Enough Money", "ERROR", JOptionPane.WARNING_MESSAGE);
        }

    }

    // searches for the stock that the user has when buying
    public Stock searchFreeStock(String stockName)
    {
        for (Stock[] sArray : stocks)
        {
            for (Stock stock : sArray)
            {
                if (stock.getStock().equalsIgnoreCase(stockName))
                {
                    return stock;
                }
            }

        }
        
        return null;
    }
    
    

}
