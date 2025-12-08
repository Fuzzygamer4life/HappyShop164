package ci553.happyshop.client.hub;

import java.io.IOException;
import java.sql.SQLException;

public class settingsController {
    public settingsModel cusModel;

    public void doAction(String action){
        switch (action) {

            case "Add":
                cusModel.changeBalance(true);
                break;
            case "Remove":
                cusModel.changeBalance(false);
                break;
            default:
                System.out.println("Invalid action : " + action);
                break;
        }
    }
    public void changeSort()
    {
        cusModel.changeSoringType();
    }
}
