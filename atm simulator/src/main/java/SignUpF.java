import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;




public class SignUpF extends JFrame implements ActionListener {


     Random ranNo;
    long randomNum;
    JLabel formNum, personalDetails, name, age, dob, gender, email, marital, street, city, state, zipcode;
    JTextField nameTextField, emailTextField, streetTextField, cityTextField, zipTextField;
    JSpinner ageSpinner;
    JComboBox<String> dayDropdown, monthDropdown, yearDropdown, stateComboBox;
    JRadioButton male, female, other, married, unmarried, mOther;
    ButtonGroup genderGroup, marriedGroup;
    JButton nextButton;
    JButton atmButton;
    private Person person; 



    SignUpF() {



         setTitle("Sign Up");
         setSize(850, 800);
         setLocation(350, 10);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         getContentPane().setBackground(Color.WHITE);
         setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

         

        setLayout(null);


        ranNo = new Random();
        randomNum = 100000L + (Math.abs(ranNo.nextLong()) % 900000L);


        formNum = new JLabel("APPLICATION NO. " + randomNum);
        formNum.setFont(new Font("Raleway", Font.BOLD, 38));
        formNum.setBounds(140, 20, 600, 40);
        add(formNum);

         personalDetails = new JLabel("Section 1: Personal");
        personalDetails.setFont(new Font("Raleway", Font.BOLD, 28));
        personalDetails.setBounds(270, 80, 600, 30);
        add(personalDetails);




         name = new JLabel("Full Name");
        name.setFont(new Font("Raleway", Font.BOLD, 18));
        name.setBounds(140, 150, 200, 30);
        add(name);
         nameTextField = new JTextField();
        nameTextField.setBounds(340, 150, 300, 30);
        nameTextField.setFont(new Font("Arial", Font.BOLD, 13));
        add(nameTextField);





         age = new JLabel("Age");
        age.setFont(new Font("Raleway", Font.BOLD, 18));
        age.setBounds(140, 200, 600, 30);
        add(age);
        SpinnerNumberModel ageModel = new SpinnerNumberModel(1, 
                                   1, 
                                   120, 
                                   1); 
         ageSpinner = new JSpinner(ageModel);
        ageSpinner.setBounds(340, 200, 75, 30);
        ageSpinner.setFont(new Font("Arial", Font.BOLD, 13));
        add(ageSpinner);





         dob = new JLabel("Date of Birth");
        dob.setFont(new Font("Raleway", Font.BOLD, 18));
        dob.setBounds(140, 250, 600, 30);
        add(dob);

        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) {
        days[i - 1] = Integer.toString(i);
        }

        String[] months = new String[]{
             "January", "February", "March", "April", "May", "June", "July", 
             "August", "September", "October", "November", "December"
            };

        String[] years = new String[124];
            for (int i = 1900; i <= 2023; i++) {
    years[i - 1900] = Integer.toString(i);
}


        dayDropdown = new JComboBox<>(days);
      monthDropdown = new JComboBox<>(months);
        yearDropdown = new JComboBox<>(years);

        dayDropdown.setBounds(340, 250, 80, 30);
        monthDropdown.setBounds(410, 250, 110, 30);
        yearDropdown.setBounds(520, 250, 100, 30);

        add(dayDropdown);
        add(monthDropdown);
        add(yearDropdown);






        gender = new JLabel("Gender");
        gender.setFont(new Font("Raleway", Font.BOLD, 18));
        gender.setBounds(140, 300, 600, 30);
        add(gender);
         male = new JRadioButton("Male");
         female = new JRadioButton("Female");
         other = new JRadioButton("Other");
        male.setBounds(340, 300, 100, 30);
        female.setBounds(460, 300, 100, 30);
        other.setBounds(580, 300, 100, 30);
        add(male);
        add(female);
        add(other);
         genderGroup = new ButtonGroup();
        genderGroup.add(other);
        genderGroup.add(male);
        genderGroup.add(female);









         email = new JLabel("Email Address");
        email.setFont(new Font("Raleway", Font.BOLD, 18));
        email.setBounds(140, 350, 600, 30);
        add(email);
         emailTextField = new JTextField();
        emailTextField.setBounds(340, 350, 300, 30);
        emailTextField.setFont(new Font("Arial", Font.BOLD, 13));
        add(emailTextField);



         marital = new JLabel("Marital Status");
        marital.setFont(new Font("Raleway", Font.BOLD, 18));
        marital.setBounds(140, 400, 600, 30);
        add(marital);
         married = new JRadioButton("Married");
         unmarried = new JRadioButton("Unmarried");
         mOther = new JRadioButton("Other");

        married.setBounds(340, 400, 100, 30);
        unmarried.setBounds(460, 400, 100, 30);
        mOther.setBounds(580, 400, 100, 30);

        add(married);
        add(unmarried);
        add(mOther);
         marriedGroup = new ButtonGroup();
        marriedGroup.add(married);
        marriedGroup.add(unmarried);
        marriedGroup.add(mOther);









         street = new JLabel("Street Line");
        street.setFont(new Font("Raleway", Font.BOLD, 18));
        street.setBounds(140, 450, 600, 30);
        add(street);
         streetTextField = new JTextField();
        streetTextField.setBounds(340, 450, 300, 30);
        streetTextField.setFont(new Font("Arial", Font.BOLD, 13));
        add(streetTextField);






         city = new JLabel("City");
        city.setFont(new Font("Raleway", Font.BOLD, 18));
        city.setBounds(140, 500, 600, 30);
        add(city);
         cityTextField = new JTextField();
        cityTextField.setBounds(340, 500, 300, 30);
        cityTextField.setFont(new Font("Arial", Font.BOLD, 13));
        add(cityTextField);






         state = new JLabel("State");
        state.setFont(new Font("Raleway", Font.BOLD, 18));
        state.setBounds(140, 550, 600, 30);
        add(state);
        String[] states = {
            "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", 
            "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", 
            "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", 
            "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", 
            "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", 
            "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", 
            "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
        };
        stateComboBox = new JComboBox<>(states);
        stateComboBox.setBounds(340, 550, 300, 30);
        stateComboBox.setFont(new Font("Arial", Font.BOLD, 13));
        add(stateComboBox);




         zipcode = new JLabel("Zip Code");
        zipcode.setFont(new Font("Raleway", Font.BOLD, 18));
        zipcode.setBounds(140, 600, 600, 30);
        add(zipcode);
         zipTextField = new JTextField();
        zipTextField.setBounds(340, 600, 300, 30);
        zipTextField.setFont(new Font("Arial", Font.BOLD, 13));
        add(zipTextField);

        nextButton = new JButton("SUBMIT");
        nextButton.setBounds(300, 650, 240, 30);
        nextButton.setOpaque(true);
        nextButton.setBorderPainted(false);
        nextButton.setBackground(Color.DARK_GRAY);
        nextButton.setForeground(Color.WHITE);
        add(nextButton);

        
        nextButton.addActionListener(this);





        atmButton = new JButton("GO TO ATM");
        atmButton.setBounds(300, 700, 240, 30);
        atmButton.setOpaque(true);
        atmButton.setBorderPainted(false);
        atmButton.setBackground(Color.DARK_GRAY);
        atmButton.setForeground(Color.WHITE);
        add(atmButton);
        
        atmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ATMPage(person);  
                setVisible(false); 
            }
        });
        







        getContentPane().setBackground(Color.WHITE);

        setTitle("Sign Up");
        setSize(850, 800);
        setLocation(350, 10); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SignUpF();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == nextButton || e.getSource() == atmButton) {
            String fullName = nameTextField.getText();
            String emailAddress = emailTextField.getText();
            String streetAddress = streetTextField.getText();
            String cityName = cityTextField.getText();
            String zipCode = zipTextField.getText();
    
            if (isEmpty(fullName) || isEmpty(emailAddress) || isEmpty(streetAddress) || isEmpty(cityName) || isEmpty(zipCode)) {
                showPopup("Please fill all the required fields.");
                return;
            }
    
            String ageValue = ageSpinner.getValue().toString();
    
            String dayOfBirth = dayDropdown.getSelectedItem().toString();
            String monthOfBirth = monthDropdown.getSelectedItem().toString();
            String yearOfBirth = yearDropdown.getSelectedItem().toString();
            String fullDob = dayOfBirth + " " + monthOfBirth + " " + yearOfBirth;
    
            String selectedGender;
            if (male.isSelected()) {
                selectedGender = "Male";
            } else if (female.isSelected()) {
                selectedGender = "Female";
            } else {
                selectedGender = "Other";
            }
    
            String maritalStatus;
            if (married.isSelected()) {
                maritalStatus = "Married";
            } else if (unmarried.isSelected()) {
                maritalStatus = "Unmarried";
            } else {
                maritalStatus = "Other";
            }
    
            String selectedState = stateComboBox.getSelectedItem().toString();
    
            try {

                String cardNumber = generateCardNumber();
                String randomCode = generateCode();
                showPopup("Your Card Number: " + cardNumber + "\nYour Code: " + randomCode);
                person = new Person(cardNumber, randomCode);

                Conn c = new Conn();
                String query = "INSERT INTO signup(formNum, name, age, dob, gender, email, marital, street, city, state, zip) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
                PreparedStatement pst = c.c.prepareStatement(query);
                pst.setString(1, String.valueOf(randomNum));
                pst.setString(2, fullName);
                pst.setString(3, ageValue);
                pst.setString(4, fullDob);
                pst.setString(5, selectedGender);
                pst.setString(6, emailAddress);
                pst.setString(7, maritalStatus);
                pst.setString(8, streetAddress);
                pst.setString(9, cityName);
                pst.setString(10, selectedState);
                pst.setString(11, zipCode);
    
                pst.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
                showPopup("Error while inserting into the database: " + ex.getMessage());
            }
            
        }
    }
    

    private String generateCardNumber() {
        Random rand = new Random();
        return String.format("%016d", rand.nextInt(1_0000_0000) + 1_0000_0000L);
    }
    
    private String generateCode() {
        Random rand = new Random();
        return String.format("%05d", rand.nextInt(100_000));
    }

    private boolean isEmpty(String field) {
        return field == null || field.trim().isEmpty();
    }
    
    private void showPopup(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}    