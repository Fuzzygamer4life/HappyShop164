package ci553.happyshop.client.settings;

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
            case "Log Out":
                cusModel.logOut();
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
