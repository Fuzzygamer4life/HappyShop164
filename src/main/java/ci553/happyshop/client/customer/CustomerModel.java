package ci553.happyshop.client.customer;

import ci553.happyshop.catalogue.Order;
import ci553.happyshop.catalogue.Product;
import ci553.happyshop.storageAccess.DatabaseRW;
import ci553.happyshop.orderManagement.OrderHub;
import ci553.happyshop.utility.StorageLocation;
import ci553.happyshop.utility.ProductListFormatter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 * You can either directly modify the CustomerModel class to implement the required tasks,
 * or create a subclass of CustomerModel and override specific methods where appropriate.
 */
public class CustomerModel {
    public CustomerView cusView;
    public DatabaseRW databaseRW; //Interface type, not specific implementation
                                  //Benefits: Flexibility: Easily change the database implementation.

    private ArrayList<Product> trolley =  new ArrayList<>(); // a list of products in trolley

    // Four UI elements to be passed to CustomerView for display updates.
    private String imageName = "imageHolder.jpg";                // Image to show in product preview (Search Page)
    private String displayLaSearchResult = "No Product was searched yet"; // Label showing search result message (Search Page)
    private String displayTaTrolley = "";                                // Text area content showing current trolley items (Trolley Page)
    private String displayTaReceipt = "";                                // Text area content showing receipt after checkout (Receipt Page)

    public void removeProduct()
    {
        Product newProd = cusView.obrLvProducts.getSelectionModel().getSelectedItem();
        if (newProd != null)
        {
            if (trolley.contains(newProd))
            {
                System.out.println("Item : " + newProd.getProductId() + " reduced");
                newProd.setOrderedQuantity(newProd.getOrderedQuantity() - 1);
                if (newProd.getOrderedQuantity() == 0)
                {
                    trolley.remove(newProd);
                }
                displayTaTrolley = ProductListFormatter.buildString(trolley);
            }
            else{
                System.out.println("Item not in trolley");
            }
        }
        else{
            System.out.println("no Product selected");
        }
        displayTaReceipt=""; // Clear receipt to switch back to trolleyPage (receipt shows only when not empty)
        updateView();
    }

    public void addProduct() {

        //TO-DO has dash becase finished
        // 1. Merges items with the same product ID (combining their quantities). Done
        // 2. Sorts the products in the trolley by product ID. Why would you want that?

        Product newProd = cusView.obrLvProducts.getSelectionModel().getSelectedItem();
        if (newProd != null) {
            System.out.println("Item stock : " + newProd.getStockQuantity());
            System.out.println("Item ordered : " + newProd.getOrderedQuantity());
            if (newProd.getStockQuantity() > 0 && newProd.getStockQuantity() - newProd.getOrderedQuantity() > 0)
            {
                if (trolley.contains(newProd))
                {
                    newProd.setOrderedQuantity(newProd.getOrderedQuantity() + 1);
                }
                else{
                    newProd.setOrderedQuantity(1);
                    trolley.add(newProd);
                }
                displayTaTrolley = ProductListFormatter.buildString(trolley);
            }
            else{
                System.out.println("No more of Product in stock");
            }
        }
        else{
            System.out.println("No product was selected");
        }
        displayTaReceipt=""; // Clear receipt to switch back to trolleyPage (receipt shows only when not empty)
        updateView();
    }




    void addToTrolley(){
//        if(theProduct!= null){
//
//            // trolley.add(theProduct) — Product is appended to the end of the trolley.
//            // To keep the trolley organized, add code here or call a method that:
//            //TODO
//            // 1. Merges items with the same product ID (combining their quantities).
//            // 2. Sorts the products in the trolley by product ID.
//            trolley.add(theProduct);
//            displayTaTrolley = ProductListFormatter.buildString(trolley); //build a String for trolley so that we can show it
//        }
//        else{
//            displayLaSearchResult = "Please search for an available product before adding it to the trolley";
//            System.out.println("must search and get an available product before add to trolley");
//        }
//        displayTaReceipt=""; // Clear receipt to switch back to trolleyPage (receipt shows only when not empty)
//        updateView();
    }

    private ArrayList<Product> productList = new ArrayList<>();
    void searchProduct() throws SQLException {
        System.out.println("Product Searching");
        String searchText = cusView.tfId.getText();
        System.out.println("Search text = " + searchText);

        if (!searchText.equals(""))
        {
            productList = databaseRW.searchProduct(searchText);
            cusView.updateProductList(productList);
        }
        else{
            productList.clear();
            System.out.println("Text is empty");
        }
        updateView();
    }

    void checkOut() throws IOException, SQLException {
        if(!trolley.isEmpty()){
            // Group the products in the trolley by productId to optimize stock checking
            // Check the database for sufficient stock for all products in the trolley.
            // If any products are insufficient, the update will be rolled back.
            // If all products are sufficient, the database will be updated, and insufficientProducts will be empty.
            // Note: If the trolley is already organized (merged and sorted), grouping is unnecessary.
            //ArrayList<Product> groupedTrolley= groupProductsById(trolley);
            ArrayList<Product> insufficientProducts= databaseRW.purchaseStocks(trolley);

            if(insufficientProducts.isEmpty()){ // If stock is sufficient for all products
                //get OrderHub and tell it to make a new Order
                OrderHub orderHub =OrderHub.getOrderHub();
                Order theOrder = orderHub.newOrder(trolley);
                displayTaTrolley ="";
                displayTaReceipt = String.format(
                        "Order_ID: %s\nOrdered_Date_Time: %s\n%s",
                        theOrder.getOrderId(),
                        theOrder.getOrderedDateTime(),
                        ProductListFormatter.buildString(theOrder.getProductList())
                );
                System.out.println(displayTaReceipt);
                for (Product prod : productList)
                {
                    prod.setOrderedQuantity(0);
                }
                trolley.clear();
            }
            else{ // Some products have insufficient stock — build an error message to inform the customer
                StringBuilder errorMsg = new StringBuilder();
                for(Product p : insufficientProducts){
                    errorMsg.append("\u2022 "+ p.getProductId()).append(", ")
                            .append(p.getProductDescription()).append(" (Only ")
                            .append(p.getStockQuantity()).append(" available, ")
                            .append(p.getOrderedQuantity()).append(" requested)\n");
                }

                //TODO
                // Add the following logic here:
                // 1. Remove products with insufficient stock from the trolley.
                // 2. Trigger a message window to notify the customer about the insufficient stock, rather than directly changing displayLaSearchResult.
                //You can use the provided RemoveProductNotifier class and its showRemovalMsg method for this purpose.
                //remember close the message window where appropriate (using method closeNotifierWindow() of RemoveProductNotifier class)
                displayLaSearchResult = "Checkout failed due to insufficient stock for the following products:\n" + errorMsg.toString();
                System.out.println("stock is not enough");
            }
        }
        else{
            displayTaTrolley = "Your trolley is empty";
            System.out.println("Your trolley is empty");
        }
        updateView();
    }

    /**
     * Groups products by their productId to optimize database queries and updates.
     * By grouping products, we can check the stock for a given `productId` once, rather than repeatedly
     */
    private ArrayList<Product> groupProductsById(ArrayList<Product> proList) {
        Map<String, Product> grouped = new HashMap<>();
        for (Product p : proList) {
            String id = p.getProductId();
            if (grouped.containsKey(id)) {
                Product existing = grouped.get(id);
                existing.setOrderedQuantity(existing.getOrderedQuantity() + p.getOrderedQuantity());
            } else {
                // Make a shallow copy to avoid modifying the original
                grouped.put(id,new Product(p.getProductId(),p.getProductDescription(),
                        p.getProductImageName(),p.getUnitPrice(),p.getStockQuantity()));
            }
        }
        return new ArrayList<>(grouped.values());
    }

    void cancel(){
        trolley.clear();
        displayTaTrolley="";
        updateView();
    }
    void closeReceipt(){
        displayTaReceipt="";
    }

    void updateView() {
        imageName = "imageHolder.jpg";
        cusView.update(imageName, displayLaSearchResult, displayTaTrolley,displayTaReceipt);
    }
     // extra notes:
     //Path.toUri(): Converts a Path object (a file or a directory path) to a URI object.
     //File.toURI(): Converts a File object (a file on the filesystem) to a URI object

    //for test only
    public ArrayList<Product> getTrolley() {
        return trolley;
    }
}
