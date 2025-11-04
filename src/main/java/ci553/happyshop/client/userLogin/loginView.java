package ci553.happyshop.client.userLogin;

import ci553.happyshop.utility.UIStyle;
import ci553.happyshop.utility.WinPosManager;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class loginView {

     static String loginBut1 = "Sign in";
     static String loginBut2 = "Register";
     boolean signInMode = true;
    private HBox hbRoot;
    public Stage thisWindow;
    public loginModel logMod;
    static int WIDTH = 300;//was 600 in other thing
    static int HEIGHT = 300;

    public void start(Stage window) {
        thisWindow = window;
        makeLoginPage("");
//        hbRoot = new HBox(10, loginPage); //initialize to show trolleyPage
//        hbRoot.setAlignment(Pos.CENTER);
//        hbRoot.setStyle(UIStyle.rootStyle);
//
//        Scene scene = new Scene(hbRoot, WIDTH, HEIGHT);
//        window.setScene(scene);
//        window.setTitle("🛒 HappyShop Sign In Client");

        WinPosManager.registerWindow(thisWindow, WIDTH, HEIGHT); //calculate position x and y for this window
        thisWindow.show();
    }

    TextField nameInput;
    TextField passInput;

    VBox makeLoginPage(String redText) {


        loginBut1 = "Sign in";
        loginBut2 = "Register";

        signInMode = true;
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
        Button btnSignIn = new Button(loginBut1);
        btnSignIn.setStyle(UIStyle.buttonStyle);
        btnSignIn.setOnAction(this::buttonClicked);

        Button btnReg = new Button(loginBut2);
        btnReg.setStyle(UIStyle.buttonStyle);
        btnReg.setOnAction(this::buttonClicked);

        HBox hbBtns = new HBox(10, laPlaceHolder, btnSignIn,btnReg);


        //red Text
        Label redLabel = new Label(redText);
        redLabel.setStyle(UIStyle.redTextStyle);
        HBox textTest = new HBox(10, redLabel);
        VBox result = new VBox(15, topTitle, topPrompt, passTitle, passText, hbBtns,textTest);
        //red Text

        //VBox result = new VBox(15, topTitle, topPrompt, passTitle, passText, hbBtns);

        result.setPrefWidth(WIDTH);
        result.setAlignment(Pos.TOP_CENTER);
        result.setStyle("-fx-padding: 15px;");

        hbRoot = new HBox(10, result); //initialize to show trolleyPage
        hbRoot.setAlignment(Pos.CENTER);
        hbRoot.setStyle(UIStyle.rootStyle);

        Scene scene = new Scene(hbRoot, WIDTH, HEIGHT);
        thisWindow.setScene(scene);
        thisWindow.setTitle("🛒 HappyShop Sign in Client");

        return result;
    }

    int invalidCounter = 0;

    void buttonClicked(ActionEvent event)
    {
        Button btn = (Button)event.getSource();
        String action = btn.getText();
        if (Objects.equals(action, loginBut1))
        {
            if (signInMode)
            {
                if (!logMod.takeInput(true))
                {
                    invalidCounter++;
                    makeLoginPage("Invalid login Attempt " + (invalidCounter > 1 ? "("+invalidCounter+")" : ""));
                }
                //returns false if login failed
            }
            else{
                System.out.println("Make new account");

                if (logMod.takeInput(false))
                {
                    invalidCounter = 0;
                    makeLoginPage("");
                }
                else
                {
                    invalidCounter++;
                    makeRegPage("Invalid Username Attempt " + (invalidCounter > 1 ? "("+invalidCounter+")" : ""));
                }
                //returns true when redgister has been made
            }
        }
        else if (Objects.equals(action, loginBut2))
        {
            if (signInMode)
            {
                System.out.println("Go to reg page");
                invalidCounter = 0;
                makeRegPage("");
            }
            else{
                System.out.println("Make go Back");
                invalidCounter = 0;
                makeLoginPage("");
            }
        }
    }

    void makeRegPage(String redText)
    {
        signInMode = false;
        loginBut1 = "Register";
        loginBut2 = "Back";
        Label userLabel = new Label("Enter New Username");
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
        passInput.setPromptText("P5S2or?D :D");
        passInput.setStyle(UIStyle.textFiledStyle);

        HBox passText = new HBox(10, passInput);

        Label laPlaceHolder = new Label(" ".repeat(15)); //create left-side spacing so that this HBox aligns with others in the layout.
        Button btnSignIn = new Button(loginBut1);
        btnSignIn.setStyle(UIStyle.buttonStyle);
        btnSignIn.setOnAction(this::buttonClicked);

        Button btnReg = new Button(loginBut2);
        btnReg.setStyle(UIStyle.buttonStyle);
        btnReg.setOnAction(this::buttonClicked);

        HBox hbBtns = new HBox(10, laPlaceHolder, btnSignIn,btnReg);

        //red Text
        Label redLabel = new Label(redText);
        redLabel.setStyle(UIStyle.redTextStyle);
        HBox textTest = new HBox(10, redLabel);
        VBox result = new VBox(15, topTitle, topPrompt, passTitle, passText, hbBtns,textTest);
        //red Text

        //VBox result = new VBox(15, topTitle, topPrompt, passTitle, passText, hbBtns);
        result.setPrefWidth(WIDTH);
        result.setAlignment(Pos.TOP_CENTER);
        result.setStyle("-fx-padding: 15px;");

        hbRoot = new HBox(10, result); //initialize to show trolleyPage
        hbRoot.setAlignment(Pos.CENTER);
        hbRoot.setStyle(UIStyle.rootStyle);

        Scene scene = new Scene(hbRoot, WIDTH, HEIGHT);
        thisWindow.setScene(scene);
        thisWindow.setTitle("🛒 HappyShop Log In Client");
    }
}
