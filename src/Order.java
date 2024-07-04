import java.io.Serializable;
import java.time.LocalDateTime;

public class Order implements Serializable {
    private int tableNumber;
    private Dish dish;
    private int quantity;
    private LocalDateTime orderedTime;
    private LocalDateTime fulfilmentTime;
    private boolean paid;

    public Order(int tableNumber, Dish dish, int quantity) {
        this.tableNumber = tableNumber;
        this.dish = dish;
        this.quantity = quantity;
        this.orderedTime = LocalDateTime.now();
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public Dish getDish() {
        return dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public LocalDateTime getFulfilmentTime() {
        return fulfilmentTime;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setFulfilmentTime(LocalDateTime fulfilmentTime) {
        this.fulfilmentTime = fulfilmentTime;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "Order{" +
                "tableNumber=" + tableNumber +
                ", dish=" + dish +
                ", quantity=" + quantity +
                ", orderedTime=" + orderedTime +
                ", fulfilmentTime=" + fulfilmentTime +
                ", paid=" + paid +
                '}';
    }
}
