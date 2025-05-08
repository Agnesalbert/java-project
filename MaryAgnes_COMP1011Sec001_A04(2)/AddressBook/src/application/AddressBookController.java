package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AddressBookController {

    @FXML
    private ListView<String> listView;

    @FXML
    private TextField findByLastNameTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private ComboBox<String> departmentComboBox;

    @FXML
    private TextField addressTextField;  // New field for address

    //  hold the entries to display in the ListView
    private ObservableList<String> listItems;

    @FXML
    private void initialize() {
        
        listItems = FXCollections.observableArrayList();
        listView.setItems(listItems);  

      
        ObservableList<String> departments = FXCollections.observableArrayList(
                "Sales", "Marketing", "IT", "HR", "Finance"
        );
        departmentComboBox.setItems(departments);
    }

    @FXML
    private void findButtonPressed() {
        String lastNameToFind = findByLastNameTextField.getText().trim();
        listItems.clear();

        if (lastNameToFind.isEmpty()) {
            System.out.println("Please enter a last name to search.");
            return;
        }

        PersonQueries queries = new PersonQueries();
        var people = queries.getPeopleByLastName(lastNameToFind);
        queries.close();

        if (people == null || people.isEmpty()) {
            System.out.println("No entries found for last name: " + lastNameToFind);
        } else {
            for (Person p : people) {
                listItems.add(p.getFirstName() + " " + p.getLastName() + " - " + p.getDepartment() + " - " + p.getAddress());
            }
        }
    }

    @FXML
    private void browseAllButtonPressed() {
        listItems.clear();

        PersonQueries queries = new PersonQueries();
        var people = queries.getAllPeople();
        queries.close();

        if (people == null || people.isEmpty()) {
            System.out.println("No entries found.");
        } else {
            for (Person p : people) {
                listItems.add(p.getFirstName() + " " + p.getLastName() + " - " + p.getDepartment() + " - " + p.getAddress());
            }
        }
    }


    @FXML
    private void addEntryButtonPressed() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String phone = phoneTextField.getText();
        String department = departmentComboBox.getSelectionModel().getSelectedItem();
        String address = addressTextField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || department == null || address.isEmpty()) {
            System.out.println("Please fill in all fields.");
            return;
        }

        PersonQueries queries = new PersonQueries();
        int rows = queries.addPerson(firstName, lastName, email, phone, address, department);
        queries.close();

        if (rows > 0) {
            String newEntry = firstName + " " + lastName + " - " + department + " - " + address;
            listItems.add(newEntry);
            System.out.println("Entry successfully added.");
        } else {
            System.out.println("Failed to add entry.");
        }

        firstNameTextField.clear();
        lastNameTextField.clear();
        emailTextField.clear();
        phoneTextField.clear();
        departmentComboBox.getSelectionModel().clearSelection();
        addressTextField.clear();
    }
}