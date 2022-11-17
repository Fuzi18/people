module hu.petrik.peoplerestclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens hu.petrik.peoplerestclient to javafx.fxml, com.google.gson;
    exports hu.petrik.peoplerestclient;
}