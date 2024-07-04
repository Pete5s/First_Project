import java.io.IOException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        RestaurantManager manager = new RestaurantManager();

        try {
            manager.loadFromFile("data.dat");
        } catch (Exception e) {
            System.err.println("Chyba při načítání souboru: " + e.getMessage());
        }

        manager.addDish(new Dish("Kuřecí řízek obalovaný 150 g", 150, 20, "kureci-rizek"));
        manager.addDish(new Dish("Hranolky 150 g", 50, 10, "hranolky"));
        manager.addDish(new Dish("Pstruh na víně 200 g", 200, 30, "pstruh-na-vine"));
        manager.addDish(new Dish("Kofola 0,5 l", 30, 1, "kofola"));

        manager.addOrder(new Order(15, new Dish("Kuřecí řízek obalovaný 150 g", 150, 20, "kureci-rizek"), 2));
        manager.addOrder(new Order(15, new Dish("Hranolky 150 g", 50, 10, "hranolky"), 2));

        Order kofolaOrder = new Order(15, new Dish("Kofola 0,5 l", 30, 1, "kofola"), 2);
        kofolaOrder.setFulfilmentTime(LocalDateTime.now());
        kofolaOrder.setPaid(true);
        manager.addOrder(kofolaOrder);

        manager.addOrder(new Order(2, new Dish("Pstruh na víně 200 g", 200, 30, "pstruh-na-vine"), 1));

        double totalPrice15 = manager.getOrders().stream()
                .filter(order -> order.getTableNumber() == 15)
                .mapToDouble(order -> order.getDish().getPrice() * order.getQuantity())
                .sum();
        System.out.printf("Celková cena konzumace pro stůl číslo 15: %.2f Kč\n", totalPrice15);

        System.out.println("Nerozpracované objednávky: " + manager.getUnfulfilledOrders());
        System.out.println("Seřazené objednávky podle času zadání: " + manager.sortOrdersByTime());
        System.out.println("Průměrná doba zpracování objednávek: " + manager.averageFulfilmentTime());
        System.out.println("Seznam jídel objednaných dnes: " + manager.getDishesOrderedToday());
        System.out.println(manager.exportOrdersForTable(15));

        try {
            manager.saveToFile("data.dat");
        } catch (IOException e) {
            System.err.println("Chyba při ukládání souboru: " + e.getMessage());
        }
    }
}
