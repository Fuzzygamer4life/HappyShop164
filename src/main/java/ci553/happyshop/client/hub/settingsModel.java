package ci553.happyshop.client.hub;

import ci553.happyshop.catalogue.Order;
import ci553.happyshop.catalogue.Product;
import ci553.happyshop.client.customer.CustomerView;
import ci553.happyshop.client.userLogin.userData;
import ci553.happyshop.orderManagement.OrderHub;
import ci553.happyshop.storageAccess.DatabaseRW;
import ci553.happyshop.utility.ProductListFormatter;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * TODO
 * You can either directly modify the CustomerModel class to implement the required tasks,
 * or create a subclass of CustomerModel and override specific methods where appropriate.
 */
public class settingsModel {
    userData currentUser;
    settingsView setView;
    public settingsModel(userData user,settingsView settingsView) {
        currentUser = user;
        this.setView = settingsView;
        setView.start(new Stage());
        updateView();
        this.setView.controller.cusModel = this;
        setView.thisWindow.show();
    }
    public String changeBalance(boolean isAdding)
    {
        String value = setView.moneyView.getText();
        int moneyValue = 0;
        try {
            moneyValue = Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            System.out.println("Error in parsing value");
            return "Invalid Number";
        }
        if (moneyValue <= 0)
        {
            System.out.println("Invalid number");
            return "Invalid Number";
        }
        if (isAdding)
        {
            double newValue = currentUser.getAccMoney() + (moneyValue * 0.01);
            if (newValue >= userData.maxMoney)
            {
                return "Cannot hold this much money";
            }
            else{
                currentUser.changeMoney(newValue);
                currentUser.saveData();
            }
        }
        else{
            double newValue = currentUser.getAccMoney() - (moneyValue * 0.01);
            if (newValue < 0)
            {
                return "Cannot remove amount";
            }
            else{
                currentUser.changeMoney(newValue);
                currentUser.saveData();
            }
        }
        updateView();
        return "";
    }
    public void changeSoringType()
    {
        String type = setView.sortType.getValue().toString();
        System.out.println("newStort Type: " + type);
        currentUser.changeSortType(type);
        currentUser.saveData();
        updateView();
    }
    public void logOut()
    {

    }

    void updateView()
    {
        setView.updateView(currentUser.getAccMoney() + "",currentUser.getSortType());
    }
}
