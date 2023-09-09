import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class Login extends JFrame implements ActionListener {

    JButton signInButton, registerButton, clearButton;
    JTextField cardnoTextField;
    JPasswordField pinTextField;
    SignUpF signUpPage;
    ATMPage atmPage;

    Color primaryColor = new Color(32, 136, 203); 
    Color secondaryColor = new Color(44, 62, 80);
    public Person currentPerson;  

    Login() {
        setTitle("Automated Teller Machine");
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("logoatm.jpeg"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel(i3);
        label.setBounds(70, 10, 100, 100);
        add(label);

        JLabel text = new JLabel("Welcome to Aarya's ATM");
        text.setFont(new Font("Osward", Font.BOLD, 32));
        text.setBounds(200, 40, 600, 40);
        text.setForeground(primaryColor);
        add(text);

        JLabel cardno = new JLabel("Card Number:");
        cardno.setFont(new Font("Arial", Font.BOLD, 18));
        cardno.setBounds(200, 150, 150, 40);
        add(cardno);

        cardnoTextField = new JTextField();
        cardnoTextField.setBounds(350, 155, 210, 30);
        cardnoTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(cardnoTextField);

        JLabel pin = new JLabel("Pin:");
        pin.setFont(new Font("Arial", Font.BOLD, 18));
        pin.setBounds(200, 200, 600, 40);
        add(pin);

        pinTextField = new JPasswordField();
        pinTextField.setBounds(350, 205, 210, 30);
        pinTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(pinTextField);

        ((AbstractDocument) cardnoTextField.getDocument()).setDocumentFilter(new CompoundDocumentFilter(new LimitDocumentFilter(15), new NumericDocumentFilter()));
        ((AbstractDocument) pinTextField.getDocument()).setDocumentFilter(new CompoundDocumentFilter(new LimitDocumentFilter(4), new NumericDocumentFilter()));

        signInButton = createButton("SIGN IN", 300, 260);
        add(signInButton);

        clearButton = createButton("CLEAR", 430, 260);
        add(clearButton);

        registerButton = createButton("REGISTER", 300, 300, 240, 30);
        add(registerButton);

        getContentPane().setBackground(Color.WHITE);

        setSize(800, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JButton createButton(String text, int x, int y) {
        return createButton(text, x, y, 110, 30);
    }

    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBackground(primaryColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(secondaryColor);
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(primaryColor);
            }
        });

        button.addActionListener(this);
        return button;
    }

    public static void main(String[] args) {
        new Login();
    }
@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signInButton) {
            this.setVisible(false);
            String cardNumber = cardnoTextField.getText();
            String pin = new String(pinTextField.getPassword());
    
            PersonDAO dao = new PersonDAO();
            try {
                Person person = dao.getPerson(cardNumber);
                if (person != null && pin.equals(person.getPin())) {
                    this.currentPerson = person; 
                    
                    if (atmPage == null) {
                        atmPage = new ATMPage(this.currentPerson);
                    }
                    atmPage.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid card number or pin!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "An error occurred. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource() == clearButton) {
            cardnoTextField.setText("");
            pinTextField.setText("");
        } else if (e.getSource() == registerButton) {
            this.setVisible(false);
            if (signUpPage == null) {
                signUpPage = new SignUpF();
            }
            signUpPage.setVisible(true);
        }
    }
}




    


class LimitDocumentFilter extends DocumentFilter {
    private int limit;

    public LimitDocumentFilter(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit can not be <= 0");
        }
        this.limit = limit;
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        int currentLength = fb.getDocument().getLength();
        int overLimit = (currentLength + text.length()) - limit - length;
        if (overLimit > 0) {
            text = text.substring(0, text.length() - overLimit);
        }
        if (text.length() > 0) {
            super.replace(fb, offset, length, text, attrs); 
        }
    }
}

class NumericDocumentFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string != null && string.chars().allMatch(Character::isDigit)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text != null && text.chars().allMatch(Character::isDigit)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}


class CompoundDocumentFilter extends DocumentFilter {
    private DocumentFilter[] filters;

    public CompoundDocumentFilter(DocumentFilter... filters) {
        this.filters = filters;
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        boolean replaced = false;
        for (DocumentFilter filter : filters) {
            if (!replaced) {
                filter.replace(fb, offset, length, text, attrs);
                int newLength = fb.getDocument().getLength();
                if (newLength != (offset + length)) { 
                    replaced = true;
                }
            }
        }
    }
}
