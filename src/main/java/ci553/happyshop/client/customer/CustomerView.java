package ci553.happyshop.client.customer;

import ci553.happyshop.catalogue.Product;
import ci553.happyshop.client.userLogin.userData;
import ci553.happyshop.utility.StorageLocation;
import ci553.happyshop.utility.UIStyle;
import ci553.happyshop.utility.WinPosManager;
import ci553.happyshop.utility.WindowBounds;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The CustomerView is separated into two sections by a line :
 *
 * 1. Search Page – Always visible, allowing customers to browse and search for products.
 * 2. the second page – display either the Trolley Page or the Receipt Page
 *    depending on the current context. Only one of these is shown at a time.
 */

public class CustomerView  {
    public CustomerController cusController;

     static String addText = "Add";
     static String remText = "Remove";

    private final int WIDTH = UIStyle.customerWinWidth;
    private final int HEIGHT = UIStyle.customerWinHeight;
    private final int COLUMN_WIDTH = WIDTH / 2 - 10;

    private HBox hbRoot; // Top-level layout manager
    private VBox vbTrolleyPage;  //vbTrolleyPage and vbReceiptPage will swap with each other when need
    private VBox vbReceiptPage;

    Label errorText;

    //four controllers needs updating when program going on
    private ImageView ivProduct; //image area in searchPage
    private Label lbProductInfo;//product text info in searchPage
    private TextArea taTrolley; //in trolley Page
    private TextArea taReceipt;//in receipt page

    // Holds a reference to this CustomerView window for future access and management
    // (e.g., positioning the removeProductNotifier when needed).
    public Stage viewWindow;

    public void start(Stage window,userData data) {
        VBox vbSearchPage = createSearchPage(data.getUserName(),data.getAccMoney());
        vbTrolleyPage = CreateTrolleyPage();
        vbReceiptPage = createReceiptPage();

        // Create a divider line
        Line line = new Line(0, 0, 0, HEIGHT);
        line.setStrokeWidth(4);
        line.setStroke(Color.PINK);
        VBox lineContainer = new VBox(line);
        lineContainer.setPrefWidth(4); // Give it some space
        lineContainer.setAlignment(Pos.CENTER);

        hbRoot = new HBox(10, vbSearchPage, lineContainer, vbTrolleyPage); //initialize to show trolleyPage
        hbRoot.setAlignment(Pos.CENTER);
        hbRoot.setStyle(UIStyle.rootStyle);

        Scene scene = new Scene(hbRoot, WIDTH, HEIGHT);
        window.setScene(scene);
        window.setTitle("🛒 HappyShop Customer Client");

        System.out.println("Redgister custView");
        WinPosManager.registerWindow(window,WIDTH,HEIGHT,false); //calculate position x and y for this window

        window.show();
        viewWindow=window;// Sets viewWindow to this window for future reference and management.
    }

    TextField prodSearchField;
    Label searchResult;

    ObservableList<Product> obeProductList; //observable product list
    ListView<Product> obrLvProducts; //A ListView observes the product list

    String getFundValue(int funds)
    {
        String fundString = funds + "";

        switch (fundString.length())
        {
            case 1:
            {
                fundString = "0.0" + fundString;
                break;
            }
            case 2:
            {
                fundString = "0." + fundString;
                break;
            }
            default://1099#
            {
                fundString = fundString.substring(0,fundString.length() - 2) + "." + fundString.substring(fundString.length() - 2);
                break;
            }
        }
        return fundString;
    }
    Label userFunds;
    private VBox createSearchPage(String userName,double funds) {

        Label userTitle = new Label("User: " + userName);
        userFunds = new Label("Funds: £" + funds);
        userTitle.setStyle(UIStyle.labelStyle);
        userFunds.setStyle(UIStyle.labelStyle);
        HBox userInfo = new HBox(10, userTitle, userFunds);
        errorText = new Label("");
        //todo make error text red errorText.setStyle();
        errorText.setVisible(false);
        HBox errorBox = new HBox(10, errorText);


        //page being changed
        Label laPageTitle = new Label("Search by Product ID/Name");
        laPageTitle.setStyle(UIStyle.labelTitleStyle);

        prodSearchField = new TextField();
        prodSearchField.setPromptText("eg. 0001");
        prodSearchField.setStyle(UIStyle.textFiledStyle);
        prodSearchField.setOnAction(this::trolleySearch);

        Button btnSearch = new Button("🔍");
        btnSearch.setOnAction(this::trolleySearch);

        btnSearch.setStyle(UIStyle.buttonStyle);
        HBox hbId = new HBox(10, prodSearchField, btnSearch);

        searchResult = new Label("Search Summary");
        searchResult.setStyle(UIStyle.labelStyle);

        Button btnEdit = new Button(addText);
        btnEdit.setStyle(UIStyle.greenFillBtnStyle);
        btnEdit.setOnAction(this::buttonClicked);

        Button btnDelete = new Button(remText);
        btnDelete.setStyle(UIStyle.grayFillBtnStyle);
        btnDelete.setOnAction(this::buttonClicked);

        HBox hbLaBtns = new HBox(10, searchResult, btnEdit,btnDelete);
        hbLaBtns.setAlignment(Pos.CENTER);
        hbLaBtns.setPadding(new Insets(5));

        obeProductList = FXCollections.observableArrayList();
        obrLvProducts = new ListView<>(obeProductList);//ListView proListView observes proList
        obrLvProducts.setPrefHeight(HEIGHT - 100);
        obrLvProducts.setFixedCellSize(50);
        obrLvProducts.setStyle(UIStyle.listViewStyle);
        obrLvProducts.setCellFactory(param -> new ListCell<Product>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);

                if (empty || product == null) {
                    setGraphic(null);
                    System.out.println("setCellFactory - empty item");
                } else {
                    String imageName = product.getProductImageName(); // Get image name (e.g. "0001.jpg")
                    String relativeImageUrl = StorageLocation.imageFolder + imageName;
                    // Get the full absolute path to the image
                    Path imageFullPath = Paths.get(relativeImageUrl).toAbsolutePath();
                    String imageFullUri = imageFullPath.toUri().toString();// Build the full image Uri

                    ImageView ivPro;
                    try {
                        ivPro = new ImageView(new Image(imageFullUri, 50,45, true,true)); // Attempt to load the product image
                    } catch (Exception e) {
                        // If loading fails, use a default image directly from the resources folder
                        ivPro = new ImageView(new Image("imageHolder.jpg",50,45,true,true)); // Directly load from resources
                    }

                    Label laProToString = new Label(product.toString()); // Create a label for product details
                    HBox hbox = new HBox(10, ivPro, laProToString); // Put ImageView and label in a horizontal layout
                    setGraphic(hbox);  // Set the whole row content
                }
            }
        });

        VBox vbSearchResult = new VBox(5,hbLaBtns, obrLvProducts);

        VBox vbSearchPage = new VBox(15,userInfo, errorBox, laPageTitle, hbId, vbSearchResult);
        vbSearchPage.setPrefWidth(COLUMN_WIDTH);

        vbSearchPage.setStyle("-fx-padding: 15px;");

        return vbSearchPage;
    }

    private VBox CreateTrolleyPage() {
        Label laPageTitle = new Label("🛒🛒  Trolley 🛒🛒");
        laPageTitle.setStyle(UIStyle.labelTitleStyle);

        taTrolley = new TextArea();
        taTrolley.setEditable(false);
        taTrolley.setPrefSize(WIDTH/2, HEIGHT-50);

        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(this::buttonClicked);
        btnCancel.setStyle(UIStyle.buttonStyle);

        Button btnCheckout = new Button("Check Out");
        btnCheckout.setOnAction(this::buttonClicked);
        btnCheckout.setStyle(UIStyle.buttonStyle);

        HBox hbBtns = new HBox(10, btnCancel,btnCheckout);
        hbBtns.setStyle("-fx-padding: 15px;");
        hbBtns.setAlignment(Pos.CENTER);

        vbTrolleyPage = new VBox(15, laPageTitle, taTrolley, hbBtns);
        vbTrolleyPage.setPrefWidth(COLUMN_WIDTH);
        vbTrolleyPage.setAlignment(Pos.TOP_CENTER);
        vbTrolleyPage.setStyle("-fx-padding: 15px;");
        return vbTrolleyPage;
    }

    private VBox createReceiptPage() {
        Label laPageTitle = new Label("Receipt");
        laPageTitle.setStyle(UIStyle.labelTitleStyle);

        taReceipt = new TextArea();
        taReceipt.setEditable(false);
        taReceipt.setPrefSize(WIDTH/2, HEIGHT-50);

        Button btnCloseReceipt = new Button("OK & Close"); //btn for closing receipt and showing trolley page
        btnCloseReceipt.setStyle(UIStyle.buttonStyle);

        btnCloseReceipt.setOnAction(this::buttonClicked);

        vbReceiptPage = new VBox(15, laPageTitle, taReceipt, btnCloseReceipt);
        vbReceiptPage.setPrefWidth(COLUMN_WIDTH);
        vbReceiptPage.setAlignment(Pos.TOP_CENTER);
        vbReceiptPage.setStyle(UIStyle.rootStyleYellow);
        return vbReceiptPage;
    }

    private void trolleySearch(ActionEvent event)
    {
        String searchRes = prodSearchField.getText();
        System.out.println("Grabbed item from label : " + searchRes);
        cusController.searchItems(searchRes);
    }

    private void buttonClicked(ActionEvent event) {
        Button btn = (Button)event.getSource();
        String action = btn.getText();
        if (action.equals(addText) || action.equals(remText))
        {
            cusController.doAction(action.equals(addText));
        }
        else{
            cusController.doAction(action);
        }
    }

    public void update(String trolley, String receipt,double funds) {
        userFunds.setText("Funds: £" + funds);
        taTrolley.setText(trolley);
        if (!receipt.equals("")) {
            showTrolleyOrReceiptPage(vbReceiptPage);
            taReceipt.setText(receipt);
        }
    }
    public void update(String trolley, String receipt, ArrayList<Product> prodList,double funds) {

        taTrolley.setText(trolley);
        if (!receipt.equals("")) {
            showTrolleyOrReceiptPage(vbReceiptPage);
            taReceipt.setText(receipt);
        }
        if (!Objects.equals(errorText.getText(), ""))
        {
            errorText.setVisible(true);
        }
        userFunds.setText("Funds: £" + funds);
        int proCounter = prodList.size();
        System.out.println("products found from search : " + proCounter);
        searchResult.setText(proCounter + " products found");
        searchResult.setVisible(true);
        obeProductList.clear();
        obeProductList.addAll(prodList);
    }

    // Replaces the last child of hbRoot with the specified page.
    // the last child is either vbTrolleyPage or vbReceiptPage.
    private void showTrolleyOrReceiptPage(Node pageToShow) {
        int lastIndex = hbRoot.getChildren().size() - 1;
        if (lastIndex >= 0) {
            hbRoot.getChildren().set(lastIndex, pageToShow);
        }
    }

    WindowBounds getWindowBounds() {
        return new WindowBounds(viewWindow.getX(), viewWindow.getY(),
                viewWindow.getWidth(), viewWindow.getHeight());
    }
}
