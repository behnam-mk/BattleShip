public class Product {
    
    private int qty;
    private String type;
    private int order_antiAircraft;
    private boolean used;

    public Product(){
        this.used = false;
        this.order_antiAircraft = 0;
    }

    public int getQty() {
        return this.qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOrder_antiAircraft() {
        return this.order_antiAircraft;
    }

    public void setOrder_antiAircraft(int order_antiAircraft) {
        this.order_antiAircraft = order_antiAircraft;
    }

    public boolean isUsed() {
        return this.used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

}
