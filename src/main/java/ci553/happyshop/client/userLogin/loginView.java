package ci553.happyshop.client.userLogin;

import ci553.happyshop.utility.UIStyle;
import ci553.happyshop.utility.WinPosManager;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class loginView {

    static String signIntext = "Sign in";
    private HBox hbRoot;
    public Stage thisWindow;
    public loginModel logMod;
    static int WIDTH = 300;//was 600 in other thing
    static int HEIGHT = 300;

    public void start(Stage window) {
        VBox loginPage = makeLoginPage();

        hbRoot = new HBox(10, loginPage); //initialize to show trolleyPage
        hbRoot.setAlignment(Pos.CENTER);
        hbRoot.setStyle(UIStyle.rootStyle);

        Scene scene = new Scene(hbRoot, WIDTH, HEIGHT);
        window.setScene(scene);
        window.setTitle("🛒 HappyShop Customer Client");
        WinPosManager.registerWindow(window, WIDTH, HEIGHT); //calculate position x and y for this window
        window.show();

        thisWindow = window;
    }

    TextField nameInput;
    TextField passInput;

    VBox makeLoginPage() {


        Label userLabel = new Label("Enter Username");
        userLabel.setStyle(UIStyle.labelTitleStyle);
        HBox topTitle = new HBox(10, userLabel);


        nameInput = new TextField();
        nameInput.setPromptText("User143");
        nameInput.setStyle(UIStyle.textFiledStyle);

        HBox topPrompt = new HBox(10, nameInput);

        Label passLabel = new Label("Enter Password");
        passLabel.setStyle(UIStyle.labelTitleStyle);

        HBox passTitle = new HBox(10, passLabel);

        passInput = new TextField();
        passInput.setPromptText("P5S2or?D");
        passInput.setStyle(UIStyle.textFiledStyle);

        HBox passText = new HBox(10, passInput);

        Label laPlaceHolder = new Label(" ".repeat(15)); //create left-side spacing so that this HBox aligns with others in the layout.
        Button btnSearch = new Button(signIntext);
        btnSearch.setStyle(UIStyle.buttonStyle);
        btnSearch.setOnAction(this::buttonClicked);

        HBox hbBtns = new HBox(10, laPlaceHolder, btnSearch);


        VBox result = new VBox(15, topTitle, topPrompt, passTitle, passText, hbBtns);
        result.setPrefWidth(WIDTH);
        result.setAlignment(Pos.TOP_CENTER);
        result.setStyle("-fx-padding: 15px;");

        return result;
    }

    void buttonClicked(ActionEvent event)
    {
        Button btn = (Button)event.getSource();
        String action = btn.getText();
        if (Objects.equals(action, signIntext))
        {
            logMod.takeInput();
        }
    }
}
