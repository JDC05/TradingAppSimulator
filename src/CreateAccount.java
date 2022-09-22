import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateAccount 
{
    Account account;

    JTextField name;
    JTextField balance;
    JFrame frame;
    boolean found;
    

    // Constructor
    public CreateAccount() 
    {    	
        ButtonListener listener = new ButtonListener();
        found = false;

        frame = new JFrame("Create Account");
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 3, 15, 10));

        JLabel nameLabel = new JLabel("ENTER NAME: ");
        name = new JTextField("");

        JLabel balanceLabel = new JLabel("ENTER BALANCE: ");
        balance = new JTextField("");

        Button button = new Button("Create");

        panel.add(nameLabel);
        panel.add(name);

        panel.add(balanceLabel);
        panel.add(balance);

        panel.add(new JLabel(""));

        button.addActionListener(listener);
        panel.add(button);

        frame.add(panel, BorderLayout.EAST);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

    }
    
    // Event handler for when the 'create' button is pressed
    public class ButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent a) 
        {
            try 
            {
                if (a.getActionCommand().equals("Create")) 
                {
                    String nameOfAccount = name.getText();
                    double balanceOfAccount = Double.parseDouble(balance.getText());
                    
                    int standardAccount = 0;
                    int premiumAmount = 1000;
                    int deluxeAmount = 2000;
                    
                    if(balanceOfAccount >= 20000)
                    {
                    	account = new DeluxeAccount(nameOfAccount, balanceOfAccount, deluxeAmount);
                    	JOptionPane.showMessageDialog(null,"DELUXE ACCOUNT: " + deluxeAmount + " deposited", "Success", JOptionPane.WARNING_MESSAGE);
                    }
                    
                    else if(balanceOfAccount >= 10000)
                    {
                    	account = new PremiumAccount(nameOfAccount, balanceOfAccount, premiumAmount);
                    	JOptionPane.showMessageDialog(null,"PREMIUM ACCOUNT: " + premiumAmount + " deposited", "Success", JOptionPane.WARNING_MESSAGE);
                    }
                    
                    else
                    {
                    	account = new StandardAccount(nameOfAccount, balanceOfAccount);
                    	JOptionPane.showMessageDialog(null,"STANDARD ACCOUNT: " + standardAccount + " deposited", "Success", JOptionPane.WARNING_MESSAGE);
                    }
                 
                    found = true;
                }
            } 
            catch (NumberFormatException e) 
            {
                JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    // Getter Methods are set
    public Account getAccount() 
    {
        return account;
    }

    public boolean getCreated() 
    {
        return found;
    }   
}
