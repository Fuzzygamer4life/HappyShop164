package ci553.happyshop.client.hub;

import ci553.happyshop.utility.UIStyle;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class settingsView {

    int HEIGHT = 500;
    int WIDTH = 750;

    settingsController controller;
    public Stage thisWindow;
    private HBox hbRoot;

    Label balTitle;
    ComboBox sortType;
    TextField moneyView;

    public void start(Stage window) {
        thisWindow = window;
        controller = new settingsController();
        makeSettingView();
    }

    public void updateView(String bal,String type)
    {
        balTitle.setText("Balance: " + bal);
        sortType.setValue(type);
    }

    void makeSettingView()
    {

        balTitle = new Label("Balance: [ExampleMoney]");
        balTitle.setStyle(UIStyle.labelTitleStyle);

        moneyView = new TextField();
        moneyView.setPromptText("eg. 510 -> $5.10");
        moneyView.setStyle(UIStyle.textFiledStyle);

        Button btnEdit = new Button("Add");
        btnEdit.setStyle(UIStyle.greenFillBtnStyle);
        btnEdit.setOnAction(this::buttonClicked);

        Button btnDelete = new Button("Remove");
        btnDelete.setStyle(UIStyle.redFillBtnStyle);
        btnDelete.setOnAction(this::buttonClicked);

        HBox hbLaBtns = new HBox(10, moneyView, btnEdit,btnDelete);
        hbLaBtns.setAlignment(Pos.CENTER);
        hbLaBtns.setPadding(new Insets(5));

        Label sortingTitle = new Label("Item sorting:");
        sortingTitle.setStyle(UIStyle.labelTitleStyle);

        sortType = new ComboBox<>();
        sortType.setStyle(UIStyle.comboBoxStyle);

        sortType.getItems().addAll("Unknown", "ID_Sort", "Name_Sort");
        sortType.setValue("[Accounts sorting style]");

        sortType.setOnAction(actionEvent -> {
            controller.changeSort();
        });

        HBox sortBox = new HBox(10,sortingTitle, sortType);
        sortBox.setAlignment(Pos.CENTER);
        sortBox.setPadding(new Insets(5));


        Button btnSearch = new Button("Log Out");
        btnSearch.setStyle(UIStyle.redFillBtnStyle);

        VBox result = new VBox(15,balTitle,hbLaBtns,sortBox,btnSearch);


        result.setPrefWidth(WIDTH);
        result.setPrefHeight(HEIGHT);

        result.setAlignment(Pos.TOP_CENTER);
        result.setStyle("-fx-padding: 15px;");

        hbRoot = new HBox(10, result); //initialize to show trolleyPage
        result.setAlignment(Pos.TOP_CENTER);
        hbRoot.setStyle(UIStyle.rootStyle);

        Scene scene = new Scene(hbRoot, WIDTH, HEIGHT);
        thisWindow.setScene(scene);
        thisWindow.setTitle("🛒 HappyShop Sign in Client");
    }

    void buttonClicked(ActionEvent event){
        Button btn = (Button)event.getSource();
        String action = btn.getText();
        System.out.println("Button clicked: " + action);
        controller.doAction(action);
    }
}
