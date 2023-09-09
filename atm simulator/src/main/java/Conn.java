import java.sql.*;

public class Conn {
    public Connection c;
    public Statement s;

    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM_Simulator", "root", "NAIMABHANDINA0520");
            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
