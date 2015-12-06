package sample;

import java.sql.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    int id = 0;
    ResultSet resultSet;
    DataBase dataBase;
    @Override
    public void start(Stage primaryStage) throws Exception{
        dataBase = new DataBase();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("JDBC");

        Scene scene = new Scene(root, 500, 550);
        VBox vBox = new VBox();
        vBox.setPrefSize(500,500);
        Button button = new Button("Select");
        final javafx.scene.control.TextField textField = new javafx.scene.control.TextField();
        textField.setPrefSize(50,20);
        textField.setText("Enter ID...");
        textField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                textField.setText("");
            }
        });
        final TextArea textArea = new TextArea();
        textArea.setPrefHeight(450);
        vBox.getChildren().add(textField);
        vBox.getChildren().add(textArea);
        primaryStage.setResizable(false);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                id = Integer.parseInt(textField.getText());

                try {
                    resultSet = dataBase.select(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    while (resultSet.next()) {
                        textArea.setText(resultSet.getInt("id") + " " + resultSet.getString("name") + " " + resultSet.getString("deoartment") + "\r\n");
                    }
                } catch (SQLException e) {

                }
                try {
                    dataBase.getConnection().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                textField.setText("Enter ID...");
            }
        });

        Button delete = new Button("Delete");
        final Button add = new Button("Add");

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Stage addStage = new Stage();
                addStage.setTitle("Add");
                VBox addBox = new VBox();
                addBox.setPrefSize(300,300);
                final javafx.scene.control.TextField name = new javafx.scene.control.TextField();
                final javafx.scene.control.TextField department = new javafx.scene.control.TextField();
                Button ok = new Button("OK");
                addBox.getChildren().addAll(name, department, ok);
                Scene addScene = new Scene(addBox, 300, 100);
                addScene.setRoot(addBox);
                addStage.setScene(addScene);
                addStage.setResizable(false);
                addStage.show();

                ok.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            dataBase.addEmployee(name.getText(), department.getText());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        addStage.close();
                    }
                });
            }
        });

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage deleteStage = new Stage();
                VBox deleteBox = new VBox();
                final javafx.scene.control.TextField deleteField = new javafx.scene.control.TextField();
                Button deleteButton = new Button("Delete");
                deleteBox.getChildren().addAll(deleteField, deleteButton);
                Scene deleteScene = new Scene(deleteBox, 300, 150);
                deleteScene.setRoot(deleteBox);
                deleteStage.setScene(deleteScene);
                deleteStage.setResizable(false);
                deleteStage.show();

                deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        try {
                            dataBase.deleteEmployee(deleteField.getText());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        vBox.getChildren().add(button);
        vBox.getChildren().add(add);
        vBox.getChildren().add(delete);
        scene.setRoot(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();







    }


    public static void main(String[] args) {
        launch(args);
    }
}
