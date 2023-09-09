import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDAO {

    public void savePerson(Person person) throws SQLException {
        String query = "INSERT INTO persons (cardNumber, pin) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, person.getCardNumber());
            statement.setString(2, person.getPin());
            statement.execute();
        }
    }

    public Person getPerson(String cardNumber) throws SQLException {
        Person person = null;
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM persons WHERE cardNumber = ?")) {
             
            pst.setString(1, cardNumber);
            ResultSet rs = pst.executeQuery();
    
            if (rs.next()) {
                String pin = rs.getString("pin");
                person = new Person(cardNumber, pin);
            }
        }
        return person;
    }
}
