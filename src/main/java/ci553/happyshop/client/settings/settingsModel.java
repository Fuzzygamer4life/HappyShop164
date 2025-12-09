package ci553.happyshop.client.settings;

import ci553.happyshop.client.Main;
import ci553.happyshop.client.custHub.hubModel;
import ci553.happyshop.client.userLogin.userData;
import javafx.stage.Stage;


/**
 * TODO
 * You can either directly modify the CustomerModel class to implement the required tasks,
 * or create a subclass of CustomerModel and override specific methods where appropriate.
 */
public class settingsModel {
    userData currentUser;
    settingsView setView;
    hubModel hubScript;
    public settingsModel(userData user, settingsView settingsView, hubModel hubScript) {
        currentUser = user;
        this.hubScript = hubScript;
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
        System.out.println("Logging Out");
        hubScript.logOut();
    }

    void updateView()
    {
        setView.updateView(currentUser.getAccMoney() + "",currentUser.getSortType());
    }
}
