package ci553.happyshop.client.custHub;

import ci553.happyshop.utility.UIStyle;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class hubController {

    public hubModel mainHubMod;

    public hubController(hubModel mainHubMod) {
        this.mainHubMod = mainHubMod;
    }

    public void doAction(String action){
        switch (action) {
            case "⚙\uFE0F":
                mainHubMod.openSettings();
                break;
            case "\uD83D\uDCB5":
                mainHubMod.startShop();
                break;
            default:
                System.out.println("Invalid action : " + action);
                break;
        }
    }
}
