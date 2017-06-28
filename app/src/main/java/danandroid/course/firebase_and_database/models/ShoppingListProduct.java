package danandroid.course.firebase_and_database.models;

/**
 * List product POJO
 */

public class ShoppingListProduct {
    private String ProductName;
    private String ownerUID;
    private String ownerName;
    private boolean purchased;

    //Empty constructor:
    public ShoppingListProduct() {
    }

    public ShoppingListProduct(String productName, String ownerUID, String ownerName, boolean purchased) {
        ProductName = productName;
        this.ownerUID = ownerUID;
        this.ownerName = ownerName;
        this.purchased = purchased;
    }

    //getters and setters
    public String getProductName() {
        return ProductName;
    }
    public void setProductName(String productName) {
        ProductName = productName;
    }
    public String getOwnerUID() {
        return ownerUID;
    }
    public void setOwnerUID(String ownerUID) {
        this.ownerUID = ownerUID;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public boolean isPurchased() {
        return purchased;
    }
    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    @Override
    public String toString() {
        return "ShoppingListProduct{" +
                "ProductName='" + ProductName + '\'' +
                ", ownerUID='" + ownerUID + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", purchased=" + purchased +
                '}';
    }
}
