package ci553.happyshop.client.customer;

import java.io.IOException;
import java.sql.SQLException;

public class CustomerController {
    public CustomerModel cusModel;

    public void doAction(boolean addingItem){
        if (addingItem)
        {
            System.out.println("Attempting Adding Item");
            cusModel.addItem();
        }
        else{
            System.out.println("Attempting Removing Item");
            cusModel.removeItem();
        }
    }


    public void doAction(String action){
        switch (action) {
            case "Clear":
                System.out.println("Performing action : Clear");
                break;
            case "Check Out":
                System.out.println("Performing action : Check Out");
                try {
                    cusModel.checkOut();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "OK & Close":
                cusModel.closeReceipt();
                break;
            case "Cancel":
                cusModel.cancel();
            default:
                System.out.println("Invalid action : " + action);
                break;
        }
    }

    public void searchItems(String itemID)
    {
        try {
            cusModel.searchprod(itemID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
