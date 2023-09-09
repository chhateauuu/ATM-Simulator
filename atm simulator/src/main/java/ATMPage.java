import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ATMPage extends JFrame implements ActionListener {
    JButton checkBalanceButton, depositButton, withdrawButton, logoutButton;
    JTextField amountField;
    JLabel messageLabel, amountLabel;
    private Person currentPerson;
    
        public ATMPage(Person currentPerson) {
            this.currentPerson = currentPerson;
    
            setLayout(null);
    
            messageLabel = new JLabel();
        messageLabel.setFont(new Font("Raleway", Font.BOLD, 20));
        messageLabel.setBounds(140, 100, 400, 30);
        add(messageLabel);

        amountLabel = new JLabel("Enter Amount:");
        amountLabel.setFont(new Font("Raleway", Font.BOLD, 18));
        amountLabel.setBounds(140, 160, 160, 30);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(300, 160, 180, 30);
        add(amountField);

        checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.setBounds(140, 220, 180, 30);
        add(checkBalanceButton);
        checkBalanceButton.addActionListener(this);

        depositButton = new JButton("Deposit");
        depositButton.setBounds(330, 220, 150, 30);
        add(depositButton);
        depositButton.addActionListener(this);

        withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(140, 260, 180, 30);
        add(withdrawButton);
        withdrawButton.addActionListener(this);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(330, 260, 150, 30);
        add(logoutButton);
        logoutButton.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        setTitle("ATM Interface");
        setSize(600, 500);
        setLocation(350, 10);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

   

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkBalanceButton) {
            double balance = checkBalance();
            messageLabel.setText("Your balance is: $" + balance);
        } else if (e.getSource() == depositButton) {
            double amount = Double.parseDouble(amountField.getText());
            deposit(amount);
            messageLabel.setText("Deposited: $" + amount);
        } else if (e.getSource() == withdrawButton) {
            double amount = Double.parseDouble(amountField.getText());
            double balance = checkBalance();

            if (balance < amount) {
                messageLabel.setText("Insufficient funds!");
            } else {
                withdraw(amount);
                messageLabel.setText("Withdrew: $" + amount);
            }
        } else if (e.getSource() == logoutButton) {
            this.dispose();
            new Login();
        }
    }

  

    private double checkBalance() {
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement("SELECT balance FROM persons WHERE cardNumber = ?")) {
             
            pst.setString(1, currentPerson.getCardNumber());
            ResultSet rs = pst.executeQuery();
    
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  
    }
    
    private void deposit(double amount) {
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement("UPDATE persons SET balance = balance + ? WHERE cardNumber = ?")) {
            
            pst.setDouble(1, amount);
            pst.setString(2, currentPerson.getCardNumber());
            pst.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void withdraw(double amount) {
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement("UPDATE persons SET balance = balance - ? WHERE cardNumber = ?")) {
             
            pst.setDouble(1, amount);
            pst.setString(2, currentPerson.getCardNumber());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
