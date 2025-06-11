package com.reciclk;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class reciclkApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("A arte de reciclar");

        TabPane tabPane = new TabPane();

        Tab tabIdentificar = new Tab("Onde descartar?", criarIdentificarPanel());
        tabIdentificar.setClosable(false);

        Tab tabPontos = new Tab("Pontos de coleta", criarPontosPanel());
        tabPontos.setClosable(false);

        Tab tabDicas = new Tab("Dicas", criarDicasPanel());
        tabDicas.setClosable(false);

        tabPane.getTabs().addAll(tabIdentificar, tabPontos, tabDicas);

        Scene scene = new Scene(tabPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox criarIdentificarPanel() {
        VBox panel = new Vbox(10);
        panel.setPadding(new Insets(15));

        Label lblTitulo = new label("Onde descartar esse material?");
        TextField txtMaterial = new TextField();
        txtMaterial.setPromptText("Ex.: Garrafa PET, Pilha, Eletrônico, etc.");

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setOnAction(e -> {
            String material = txtMaterial.getText();
        });

        ImageView imgLixeira = new ImageView(new Image("images/")); //Colocar a imagem feita pelo Cauã depois.
        imgLixeira.setFitWidth(100);
        imgLixeira.setPreserveRatio(true);

        panel.getChildren().addAll(lblTitulo, txtMaterial, btnBuscar, imgLixeira);
        return panel;
    }

    private ScrollPane criarPontosPanel() {
        return new ScrollPane();
    }

    private TextArea criarDicasPanel() {
        TextArea dicasArea = new TextArea();
        dicasArea.setText(
                "Dicas de reciclagem: /n/n" +
                        "1. Lave embalagens antes de descartar. /n" +
                        "2. Papel molhado não se pode ser reciclado. /n" +
                        "3. Pilhas e baterias devem ir a pontos especificos. /n" +
                        "4. Vidros quebrados devem ser embrulhados em papel."

        );
        dicasArea.setEditable(false);
        return dicasArea;
    }

    public static void main(String[] args) {
        launch(args);
    }
}