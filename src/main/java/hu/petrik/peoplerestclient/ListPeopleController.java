package hu.petrik.peoplerestclient;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ListPeopleController {

    @FXML
    private TableView<Person> peopleTable;
    @FXML
    private TableColumn<Person, Integer> ageCol;
    @FXML
    private Button deleteButton;
    @FXML
    private Button insertButton;
    @FXML
    private TableColumn<Person, String> nameCol;
    @FXML
    private TableColumn<Person, String> emailCol;
    @FXML
    private Button updateButton;

    @FXML
    private void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        Platform.runLater(() -> {
            try {
                loadPeopleFromServer();
            } catch (IOException e) {
                error("Couldn't get data from server", e.getMessage());
                Platform.exit();
            }
        });
    }

    private void loadPeopleFromServer() throws IOException {
        Response response = RequestHandler.get(App.BASE_URL);
        String content = response.getContent();
        System.out.println(content);
        Gson converter = new Gson();
        Person[] people = converter.fromJson(content, Person[].class);
        peopleTable.getItems().clear();
        for (Person person : people) {
            peopleTable.getItems().add(person);

        }
    }


    @FXML
    public void insertClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("list-people-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            Stage stage= new Stage();
            stage.setTitle("Create People");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            error("Couldn't load form", e.getMessage());
        }
    }

    @FXML
    public void deleteClick(ActionEvent actionEvent) {
        int selectIndex = peopleTable.getSelectionModel().getSelectedIndex();
        if (selectIndex == -1){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Please select a person");
            alert.show();
            return;
        }
        Person selected = peopleTable.getSelectionModel().getSelectedItem();
        Alert confirmation= new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText(String.format("Are u sure want to delete %s", selected.getName()));
        Optional<ButtonType> optionalButtonType=confirmation.showAndWait();
        if (optionalButtonType.isEmpty()){
            System.err.println("Unkown error occured");
            return;
        }
        ButtonType clickedButton= optionalButtonType.get();
        if (clickedButton.equals(ButtonType.OK)){
            String url = App.BASE_URL + "/" + selected.getId();
            try {
                RequestHandler.delete(url);
                loadPeopleFromServer();
            }catch (IOException e){
                error("An error occured while cummunicating with the server");
            }
        }
    }

    @FXML
    public void updateClick(ActionEvent actionEvent) {
    }

    private void error(String headerText){
       error(headerText, "");
    }

    private void error(String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}