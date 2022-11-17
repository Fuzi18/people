package hu.petrik.peoplerestclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class CreatePeopleController {
    @FXML
    private Button submitButton;
    @FXML
    private Spinner<Integer> ageField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;

    @FXML
    public void submitClick(ActionEvent actionEvent) {
    }
}
