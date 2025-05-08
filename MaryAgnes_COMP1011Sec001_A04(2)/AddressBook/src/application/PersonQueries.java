// Fig. 24.31: PersonQueries.java
// PreparedStatements used by the Address Book application.
package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class PersonQueries {

    private static final String URL = "jdbc:mysql://localhost:3306/AddressBook?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Angela@2005";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
        }
    }static {
        try {
          
            Class.forName("com.mysql.cj.jdbc.Driver");

           
            System.out.println("JDBC Driver Version: " + com.mysql.cj.jdbc.Driver.class.getPackage().getImplementationVersion());

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
        }
    }

    private Connection connection; 
    private PreparedStatement selectAllPeople;
    private PreparedStatement selectPeopleByLastName;
    private PreparedStatement insertNewPerson;

    // constructor
    public PersonQueries() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Prepare statements
            selectAllPeople = connection.prepareStatement(
                "SELECT * FROM Addresses ORDER BY LastName, FirstName");

            selectPeopleByLastName = connection.prepareStatement(        
                "SELECT * FROM Addresses WHERE LastName LIKE ? ORDER BY LastName, FirstName");

            insertNewPerson = connection.prepareStatement(         
                "INSERT INTO Addresses " +                           
                "(FirstName, LastName, Email, PhoneNumber, Address, Department) " +     
                "VALUES (?, ?, ?, ?, ?, ?)");
        } 
        catch (SQLException sqlException) {
            System.err.println("Error connecting or preparing statements:");
            sqlException.printStackTrace();
            System.exit(1);
        } 
    }

    
    public List<Person> getAllPeople() {
        try (ResultSet resultSet = selectAllPeople.executeQuery()) {
            List<Person> results = new ArrayList<>();

            while (resultSet.next()) {
                results.add(new Person(
                    resultSet.getInt("AddressID"),
                    resultSet.getString("FirstName"),
                    resultSet.getString("LastName"),
                    resultSet.getString("Email"),
                    resultSet.getString("PhoneNumber"),
                    resultSet.getString("Address"),
                    resultSet.getString("Department")));
            }
            return results;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    
    public List<Person> getPeopleByLastName(String lastName) {
        try {
            selectPeopleByLastName.setString(1, lastName + "%");

            try (ResultSet resultSet = selectPeopleByLastName.executeQuery()) {
                List<Person> results = new ArrayList<>();

                while (resultSet.next()) {
                    results.add(new Person(
                        resultSet.getInt("AddressID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Email"),
                        resultSet.getString("PhoneNumber"),
                        resultSet.getString("Address"),
                        resultSet.getString("Department")
                    ));
                }

                return results;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    // Add a new person
    public int addPerson(String firstName, String lastName, 
                         String email, String phoneNumber, String address, String department) {
        try {
            insertNewPerson.setString(1, firstName);
            insertNewPerson.setString(2, lastName);
            insertNewPerson.setString(3, email);
            insertNewPerson.setString(4, phoneNumber);
            insertNewPerson.setString(5, address);
            insertNewPerson.setString(6, department);

            return insertNewPerson.executeUpdate();         
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return 0;
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } 
    }
}