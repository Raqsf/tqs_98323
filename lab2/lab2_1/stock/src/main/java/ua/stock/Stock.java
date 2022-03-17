package ua.stock;

/**
 * Stock
 */
public class Stock {

    private String label;
    private int quantity;

    public Stock(String label, int quantity) {
        this.setLabel(label);
        this.setQuantity(quantity);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    
}