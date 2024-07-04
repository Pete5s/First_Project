import java.io.Serializable;

public class Dish implements Serializable {
    private String title;
    private double price;
    private int preparationTime;
    private String image;

    public Dish(String title, double price, int preparationTime, String image) {
        if (preparationTime <= 0) {
            throw new IllegalArgumentException("Doba přípravy musí být kladné číslo.");
        }
        this.title = title;
        this.price = price;
        this.preparationTime = preparationTime;
        this.image = (image != null && !image.isEmpty()) ? image : "blank";
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", preparationTime=" + preparationTime +
                ", image='" + image + '\'' +
                '}';
    }
}
