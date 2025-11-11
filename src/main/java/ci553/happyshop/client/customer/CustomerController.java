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
    public void searchItems(String itemID)
    {
        try {
            cusModel.searchprod(itemID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
