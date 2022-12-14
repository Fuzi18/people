package hu.petrik.peoplerestclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

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
    private void initialize(){
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory= new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200,30);
        ageField.setValueFactory(valueFactory);
    }

    @FXML
    public void submitClick(ActionEvent actionEvent) throws IOException {
        String name= nameField.getText().trim();
        String email = emailField.getText().trim();
        int age = ageField.getValue();
        if (name.isEmpty()){
            warning("Name is required");
            return;
        }
        if (email.isEmpty()){
            warning("Email is required");
            return;
        }
        // TODO: validate email format
        Person newPerson = new Person(0, name, email, age);
        Gson converter = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = converter.toJson(newPerson);
        try {
            Response response =RequestHandler.post(App.BASE_URL, json);
            if (response.getResponseCode() == 201){
                warning("Person added!");
                nameField.setText("");
                emailField.setText("");
                ageField.getValueFactory().setValue(30);
            }else {
                // TODO: error
            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void warning(String headerText) {
        Alert alert=new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(headerText);
        alert.showAndWait();

    }
}
