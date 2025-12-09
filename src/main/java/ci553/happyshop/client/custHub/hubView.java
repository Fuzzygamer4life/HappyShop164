package ci553.happyshop.client.custHub;

import ci553.happyshop.utility.UIStyle;
import ci553.happyshop.utility.WinPosManager;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class hubView {

    int HEIGHT = 200;
    int WIDTH = 350;

    public Stage thisWindow;
    private HBox hbRoot;

    public void start(Stage window) {
        thisWindow = window;
        makeHubWindow();
        window.show();
    }


    void makeHubWindow()
    {

        Label settTitle = new Label("- Settings -");
        settTitle.setStyle(UIStyle.labelTitleStyle);

        Button settButton = new Button("⚙\uFE0F");
        settButton.setPrefSize(100,100);
        settButton.setStyle(UIStyle.buttonStyle);
        settButton.setOnAction(this::buttonClicked);

        VBox settBox = new VBox(10,settTitle,settButton);

        Label shopTitle = new Label("- Shopping -");
        shopTitle.setStyle(UIStyle.labelTitleStyle);

        Button shopButon = new Button("\uD83D\uDCB5");
        shopButon.setPrefSize(100,100);
        shopButon.setStyle(UIStyle.buttonStyle);
        shopButon.setOnAction(this::buttonClicked);

        VBox shopBox = new VBox(10,shopTitle,shopButon);


        HBox result = new HBox(15,settBox,shopBox);


        result.setPrefWidth(WIDTH);
        result.setPrefHeight(HEIGHT);

        result.setAlignment(Pos.TOP_CENTER);
        result.setStyle("-fx-padding: 15px;");

        hbRoot = new HBox(10, result); //initialize to show trolleyPage
        result.setAlignment(Pos.TOP_CENTER);
        hbRoot.setStyle(UIStyle.rootStyle);

        Scene scene = new Scene(hbRoot, WIDTH, HEIGHT);
        thisWindow.setScene(scene);
        WinPosManager.registerWindow(thisWindow,WIDTH,HEIGHT,true);
        thisWindow.setTitle("🛒 HappyShop Sign in Client");
    }

    hubController hubControlls;

    void buttonClicked(ActionEvent event){
        Button btn = (Button)event.getSource();
        String action = btn.getText();
        System.out.println("Button clicked: " + action);
        hubControlls.doAction(action);
    }
}
