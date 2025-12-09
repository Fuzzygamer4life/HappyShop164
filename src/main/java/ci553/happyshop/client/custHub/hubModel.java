package ci553.happyshop.client.custHub;

import ci553.happyshop.client.Main;
import ci553.happyshop.client.customer.CustomerController;
import ci553.happyshop.client.customer.CustomerModel;
import ci553.happyshop.client.customer.CustomerView;
import ci553.happyshop.client.orderTracker.OrderTracker;
import ci553.happyshop.client.settings.settingsModel;
import ci553.happyshop.client.settings.settingsView;
import ci553.happyshop.client.userLogin.userData;
import ci553.happyshop.orderManagement.OrderHub;
import ci553.happyshop.storageAccess.DatabaseRW;
import ci553.happyshop.storageAccess.DatabaseRWFactory;
import ci553.happyshop.utility.WinPosManager;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class hubModel {
    userData currentUser;
    Main main;

    OrderTracker mainTracker;
    hubView hubVisuals;
    CustomerView customerVisuals;
    settingsView settingsVisuals;


    public hubModel(Main mainScript, userData user, hubView hubVisuals, OrderTracker tracker) {
        main = mainScript;
        currentUser = user;
        this.hubVisuals = hubVisuals;
        this.mainTracker = tracker;
        hubVisuals.hubControlls = new hubController(this);
    }

    public void openSettings()
    {
        if (settingsVisuals != null)
        {
            return;
        }
        if (customerVisuals != null)
        {
            customerVisuals.viewWindow.close();
            customerVisuals = null;
        }
        settingsVisuals = new settingsView();
        new settingsModel(currentUser, settingsVisuals,this);
    }

    public void startShop()
    {
        if (customerVisuals != null)
        {
            return;
        }
        if (settingsVisuals != null)
        {
            settingsVisuals.thisWindow.close();
            settingsVisuals = null;
        }

        customerVisuals = new CustomerView();
        CustomerController cusController = new CustomerController();
        CustomerModel cusModel = new CustomerModel(currentUser);
        DatabaseRW databaseRW = DatabaseRWFactory.createDatabaseRW();

        customerVisuals.cusController = cusController;
        cusController.cusModel = cusModel;
        cusModel.cusView = customerVisuals;
        cusModel.databaseRW = databaseRW;
        customerVisuals.start(new Stage(),currentUser);

    }

    public void logOut()
    {

        System.out.println("Full Log");
        if (customerVisuals != null)
        {
            customerVisuals.viewWindow.close();
            customerVisuals = null;
        }
        if (settingsVisuals != null)
        {
            settingsVisuals.thisWindow.close();
            settingsVisuals = null;
        }
        mainTracker.thisWindow.close();
        mainTracker = null;

        OrderHub.getOrderHub().orderTrackerList.clear();

        hubVisuals.thisWindow.close();
        WinPosManager.resetData();
        main.startLogin(false);
    }
}
