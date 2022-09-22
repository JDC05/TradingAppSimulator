import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Screen
{
    //File path to access stock DataStock folder in order to access the stock files	
    static Path currentRelativePath = Paths.get("");
    public static final String FILEPATH = currentRelativePath.toAbsolutePath().toString() + "\\src\\StockData\\";
	
    // text fields for common stocks
    JTextField APPLE;
    JTextField AMAZON;
    JTextField TESLA;
    JTextField MICROSOFT;
    
    // text fields for crypto stocks
    JTextField BITCOIN;
    JTextField ETHEREUM;
    JTextField XRP;
    JTextField CARDANO;

    JTextField[][] textFields;

    JTextArea stocksBoughtInfo;
    Account account;
    Stock[][] stocks;
    
    
    // Constructor
    public Screen(Account account, final Stock[][] stocks) 
    {
    	this.account = account;
        this.stocks = stocks;       

        ButtonListener listener = new ButtonListener();

        JFrame frame = new JFrame("Trading App");
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        
        textFields = new JTextField[2][4];
        
        JPanel stockInfo = new JPanel();
        stockInfo.setLayout(new GridLayout(2, 0, 10, 0));
        
        // Panel for common stocks
        JLabel APPLELabel = new JLabel("APPLE", SwingConstants.CENTER);
        JLabel AMAZONLabel = new JLabel("AMAZON", SwingConstants.CENTER);
        JLabel TESLALabel = new JLabel("TESLA", SwingConstants.CENTER);
        JLabel MICROSOFTLabel = new JLabel("MICROSOFT", SwingConstants.CENTER);
        
        APPLE = new JTextField("");
        AMAZON = new JTextField("");
        TESLA = new JTextField("");
        MICROSOFT = new JTextField("");

        textFields[0][0] = APPLE;
        textFields[0][1] = AMAZON;
        textFields[0][2] = TESLA;
        textFields[0][3] = MICROSOFT;       
        
        JPanel commonPanel = new JPanel();
        commonPanel.setLayout(new GridLayout(5, 3, 5, 5));

        commonPanel.add(new JLabel(""));
        commonPanel.add(new JLabel("PRICE", SwingConstants.CENTER));
        commonPanel.add(new JLabel(""));

        commonPanel.add(APPLELabel);
        commonPanel.add(textFields[0][0]);
        commonPanel.add(new JLabel(""));

        commonPanel.add(AMAZONLabel);
        commonPanel.add(textFields[0][1]);
        commonPanel.add(new JLabel(""));

        commonPanel.add(TESLALabel);
        commonPanel.add(textFields[0][2]);
        commonPanel.add(new JLabel(""));

        commonPanel.add(MICROSOFTLabel);
        commonPanel.add(textFields[0][3]);
        commonPanel.add(new JLabel(""));
             
        JPanel commonHolder = new JPanel();
        commonHolder.setLayout(new GridLayout(1, 2, 10, 0));

        commonHolder.add(commonPanel);
        
        // Panel for crypto stocks
        JLabel BITCOINLabel = new JLabel("BITCOIN", SwingConstants.CENTER);
        JLabel ETHEREUMLabel = new JLabel("ETHEREUM", SwingConstants.CENTER);
        JLabel XRPLabel = new JLabel("XRP", SwingConstants.CENTER);
        JLabel CARDANOLabel = new JLabel("CARDANO", SwingConstants.CENTER);

        BITCOIN = new JTextField("");
        ETHEREUM = new JTextField("");
        XRP = new JTextField("");
        CARDANO = new JTextField("");

        textFields[1][0] = BITCOIN;
        textFields[1][1] = ETHEREUM;
        textFields[1][2] = XRP;
        textFields[1][3] = CARDANO;
       
        JPanel cryptoPanel = new JPanel();
        cryptoPanel.setLayout(new GridLayout(5, 3, 5, 5));

        cryptoPanel.add(new JLabel(""));
        cryptoPanel.add(new JLabel("PRICE", SwingConstants.CENTER));
        cryptoPanel.add(new JLabel(""));

        cryptoPanel.add(BITCOINLabel);
        cryptoPanel.add(textFields[1][0]);
        cryptoPanel.add(new JLabel(""));

        cryptoPanel.add(ETHEREUMLabel);
        cryptoPanel.add(textFields[1][1]);
        cryptoPanel.add(new JLabel(""));

        cryptoPanel.add(XRPLabel);
        cryptoPanel.add(textFields[1][2]);
        cryptoPanel.add(new JLabel(""));

        cryptoPanel.add(CARDANOLabel);
        cryptoPanel.add(textFields[1][3]);
        cryptoPanel.add(new JLabel(""));
        
        
        for (JTextField[] textFieldArray : textFields) 
        {
            for (JTextField textField : textFieldArray) 
            {
                textField.setSize(new Dimension(150, 20));
            }
        }
        
        JPanel cryptoHolder = new JPanel();
        cryptoHolder.setLayout(new FlowLayout(FlowLayout.CENTER));
        cryptoHolder.add(cryptoPanel);
        
        JPanel stocksBought = new JPanel();

        stocksBoughtInfo = new JTextArea(15, 30);
        stocksBought.add(stocksBoughtInfo);

        stockInfo.add(commonHolder, BorderLayout.CENTER);
        stockInfo.add(cryptoHolder, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));

        mainPanel.add(stockInfo);
        mainPanel.add(stocksBought);

        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));

        JPanel buttonPanel = new JPanel();
        
        Button btnDeposit = new Button("Deposit");       
        btnDeposit.addActionListener(listener);
        
        Button btnWithdraw = new Button("Withdraw");       
        btnWithdraw.addActionListener(listener);
        
        Button btnBuySell = new Button("Trade");
        btnBuySell.addActionListener(listener);

        buttonPanel.setLayout(new GridLayout(1, 3, 5, 5));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.add(btnBuySell);
        buttonPanel.add(btnDeposit);
        buttonPanel.add(btnWithdraw);
     
        frame.add(mainPanel);
        frame.add(buttonPanel);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // Event handler for when the main window is exited, another method is called
        frame.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
            	try
            	{
					printData(stocks);
				} 
            	catch (IOException e) 
            	{
					e.printStackTrace();
				}
                System.exit(0);
            }
        });
        
        frame.setSize(1200, 650);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
    
    //Round to 2dp
    public static Double roundTo2dp(double num) 
    {
        DecimalFormat df = new DecimalFormat(".##");
        return (Double.parseDouble(df.format(num)));
    }
    
    // Event handler for when the 'Trade', 'Deposit' and 'Withdraw' buttons are pressed
    public class ButtonListener implements ActionListener 
    {
    	public void actionPerformed(ActionEvent a) 
        {
            JFrame frame;
            JPanel panel = new JPanel();
            
            try 
            {
            	if(a.getActionCommand().equals("Trade")) 
                {
                	new Shares(account, stocks);
                }
            	else
            	{
            		if(a.getActionCommand().equals("Deposit")) 
                    {
            			frame = new JFrame();
                        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
                        
                        panel.setLayout(new GridLayout(3, 2, 5, 5));
                        
                        JLabel quantityLabel = new JLabel("Quantity");
                        final JTextField depositQuantity = new JTextField("");
                        
                        panel.add(quantityLabel);
                        panel.add(depositQuantity);
                        
                        Button depositButton = new Button("Deposit");
                                            
                        depositButton.addActionListener(new ActionListener()
                		{
                			public void actionPerformed(ActionEvent evt)
                			{		           		    	
                				int amount = 0;
                						
                				try 
                				{
                					amount = Integer.parseInt(depositQuantity.getText());
                				}
                						
                				catch (NumberFormatException e)
                				{
                					JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.WARNING_MESSAGE);
                				}
                						
                				account.deposit(amount);
                				JOptionPane.showMessageDialog(null, "Balance: " + roundTo2dp(account.getBalance()), "Successful Deposit", JOptionPane.WARNING_MESSAGE);
                			}
                		});

                        panel.add(depositButton);

                        frame.add(panel);

                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    }
            		else
            		{
            			frame = new JFrame();
                        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
                        
                        panel.setLayout(new GridLayout(3, 2, 5, 5));
                        
                        JLabel quantityLabel = new JLabel("Quantity");
                        final JTextField withdrawQuantity = new JTextField("");
                        
                        panel.add(quantityLabel);
                        panel.add(withdrawQuantity);
                        
                        Button withdrawButton = new Button("Withdraw");
                                            
                        withdrawButton.addActionListener(new ActionListener()
                		{
                			public void actionPerformed(ActionEvent evt)
                			{		           		    	
                				int amount = 0;
                						
                				try 
                				{
                					amount = Integer.parseInt(withdrawQuantity.getText());
                				}
                						
                				catch (NumberFormatException e)
                				{
                					JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.WARNING_MESSAGE);		
                				}
                						
                				account.withdraw(amount);
                				JOptionPane.showMessageDialog(null, "Balance: " + roundTo2dp(account.getBalance()), "Successful Withdraw", JOptionPane.WARNING_MESSAGE);
                			}
                		});

                        panel.add(withdrawButton);

                        frame.add(panel);

                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
            		}
            	}
            }            
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    // Prints the time and stock price of all the stocks between when code runs and ends
    public static void printData(Stock[][] stocks) throws IOException 
    {
    	String fileName;
    	String line = "";
    	
    	for (Stock[] sArray : stocks) 
        {
    		for (Stock stock : sArray)
            {
    			fileName = stock.getStock() + ".csv";
    			BufferedReader br = new BufferedReader(new FileReader(FILEPATH + fileName));    		
    			
    			System.out.print(stock.getStock() + "\n");
    			
    			while((line = br.readLine()) != null)
    			{
    				
    				String[] values = line.split(",");
    				System.out.print("Time: " + values[0] + " Stock Price: "  + values[1] + "\n");
    			}    			
    			System.out.print("\n");
    			
    			br.close();
            }
        }
    	
    	
    }
    
    // Add the time and rate of the stock
    public void AddData(String fileName, double rate)
    {
        try
        {
            String path = FILEPATH + fileName + ".csv";

            Calendar time = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String timeStamp = sdf.format(time.getTime());

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
                String line = timeStamp + "," + rate;
                bw.write(line);
                bw.newLine();
                bw.close();
            }            
        }         
        catch (IOException e)
        {
            System.out.println("Error: " + e);
        }
    }
    
    // set the current stock prices in the text fields
    public void setValues(Stock[][] stocks) 
    {
        for (int i = 0; i < stocks.length; i++) 
        {
            for (int j = 0; j < stocks[i].length; j++) 
            {
            	Stock stock = stocks[i][j];
                setStockPrice(textFields[i][j], stock);
                AddData(stock.getStock(), stock.getPrice());
            }
        }
        setOwnedStocks();       
    }
    
    // set the trade prices for each stock
    public void setStockPrice(JTextField field, Stock stock) 
    {
        field.setText(String.valueOf(stock.getPrice()));
    }
    
    // Display the bought stocks by the user in the portfolio window
    public void setOwnedStocks()
    {
        ArrayList<Stock> ownedStocks = account.getStocks();
        int quantity = ownedStocks.size();
        String info = "STOCK NAME \t PRICE \t QUANTITY\n";

        for (int i = 0; i < quantity; i++)
        {
        	info = info + ownedStocks.get(i).getStock() + " \t " + ownedStocks.get(i).getPrice() + " \t " + ownedStocks.get(i).getQuantity() + "\n";
        }
        
        info = info + "\nBALANCE: " + roundTo2dp(account.getBalance());
        stocksBoughtInfo.setText(info);
    }   
}
