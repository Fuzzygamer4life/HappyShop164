package ci553.happyshop.client.hub;

import ci553.happyshop.utility.UIStyle;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class hubView {

    int HEIGHT = 500;
    int WIDTH = 750;

    public Stage thisWindow;
    private HBox hbRoot;

    public void start(Stage window) {
        thisWindow = window;
        makeMainHub();
        thisWindow.show();
    }



    void makeMainHub()
    {

        Label topTitle = new Label("Welcome _Name_ [SizeTest]");

        Button shopButton = new Button("Shop");
        shopButton.setPrefSize(100,75);
        shopButton.setStyle(UIStyle.buttonStyle);
        shopButton.setOnAction(this::buttonClicked);

        Button settingButton = new Button("Settings");
        settingButton.setPrefSize(100,75);
        settingButton.setStyle(UIStyle.buttonStyle);
        settingButton.setOnAction(this::buttonClicked);

        HBox topButtons = new HBox(15,shopButton,settingButton);


        Button walletButton = new Button("Wallet");
        walletButton.setPrefSize(100,75);
        walletButton.setStyle(UIStyle.buttonStyle);
        walletButton.setOnAction(this::buttonClicked);

        Button orderButton = new Button("Orders");
        orderButton.setPrefSize(100,75);
        orderButton.setStyle(UIStyle.buttonStyle);
        orderButton.setOnAction(this::buttonClicked);

        HBox bottomButtons = new HBox(15,walletButton,orderButton);

        VBox result = new VBox(15,topTitle , topButtons,bottomButtons);


        result.setPrefWidth(WIDTH);
        result.setPrefHeight(HEIGHT);

        result.setAlignment(Pos.TOP_CENTER);
        result.setStyle("-fx-padding: 15px;");

        hbRoot = new HBox(10, result); //initialize to show trolleyPage
        hbRoot.setAlignment(Pos.CENTER);
        hbRoot.setStyle(UIStyle.rootStyle);

        Scene scene = new Scene(hbRoot, WIDTH, HEIGHT);
        thisWindow.setScene(scene);
        thisWindow.setTitle("🛒 HappyShop Sign in Client");
    }

    void buttonClicked(ActionEvent event){
        Button btn = (Button)event.getSource();
        String action = btn.getText();
        System.out.println("Button clicked: " + action);
    }
}
