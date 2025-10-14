package ci553.happyshop.client.customer;

import java.io.IOException;
import java.sql.SQLException;

public class CustomerController {
    public CustomerModel cusModel;

    public void doAction(String action) throws SQLException, IOException {
        switch (action) {
            case "Search":
                System.out.println("inactive Search called");
                //cusModel.search();
                break;
            case "Add to Trolley":
                cusModel.addToTrolley();
                break;
            case "Cancel":
                cusModel.cancel();
                break;
            case "Check Out":
                cusModel.checkOut();
                break;
            case "OK & Close":
                cusModel.closeReceipt();
                break;
            case "\uD83D\uDD0D":
                cusModel.searchProduct();
                break;
            case "Add":
                System.out.println("Action :Act");
                cusModel.addProduct();
                break;
            case "Remove":
                System.out.println("Action :Remove");
                break;
            default:
                System.out.println("Unknown Action : " + action);
                break;
        }
    }

}
