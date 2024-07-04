import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class RestaurantManager {
    private List<Dish> dishes;
    private List<Order> orders;

    public RestaurantManager() {
        this.dishes = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public void addDish(Dish dish) {
        dishes.add(dish);
    }

    public void removeDish(String title) {
        dishes.removeIf(dish -> dish.getTitle().equals(title));
    }

    public void updateDish(String title, Dish newDish) {
        for (int i = 0; i < dishes.size(); i++) {
            if (dishes.get(i).getTitle().equals(title)) {
                dishes.set(i, newDish);
                return;
            }
        }
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Order> getUnfulfilledOrders() {
        return orders.stream()
                .filter(order -> order.getFulfilmentTime() == null)
                .collect(Collectors.toList());
    }

    public List<Order> sortOrdersByTime() {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getOrderedTime))
                .collect(Collectors.toList());
    }

    public Duration averageFulfilmentTime() {
        List<Order> fulfilledOrders = orders.stream()
                .filter(order -> order.getFulfilmentTime() != null)
                .collect(Collectors.toList());
        if (fulfilledOrders.isEmpty()) {
            return Duration.ZERO;
        }
        Duration totalTime = fulfilledOrders.stream()
                .map(order -> Duration.between(order.getOrderedTime(), order.getFulfilmentTime()))
                .reduce(Duration.ZERO, Duration::plus);
        return totalTime.dividedBy(fulfilledOrders.size());
    }

    public Set<String> getDishesOrderedToday() {
        LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay();
        return orders.stream()
                .filter(order -> order.getOrderedTime().isAfter(today))
                .map(order -> order.getDish().getTitle())
                .collect(Collectors.toSet());
    }

    public String exportOrdersForTable(int tableNumber) {
        List<Order> tableOrders = orders.stream()
                .filter(order -> order.getTableNumber() == tableNumber)
                .collect(Collectors.toList());
        if (tableOrders.isEmpty()) {
            return String.format("** Objednávky pro stůl č. %2d **\n******\n", tableNumber);
        }

        StringBuilder export = new StringBuilder(String.format("** Objednávky pro stůl č. %2d **\n", tableNumber));
        for (int i = 0; i < tableOrders.size(); i++) {
            Order order = tableOrders.get(i);
            String orderedTimeStr = order.getOrderedTime().toLocalTime().toString();
            String fulfilmentTimeStr = order.getFulfilmentTime() != null ? order.getFulfilmentTime().toLocalTime().toString() : "";
            String paidStr = order.isPaid() ? " zaplaceno" : "";
            double totalPrice = order.getDish().getPrice() * order.getQuantity();
            export.append(String.format("%d. %s %dx (%.2f Kč):\t%s-%s\t%s\n",
                    i + 1, order.getDish().getTitle(), order.getQuantity(), totalPrice, orderedTimeStr, fulfilmentTimeStr, paidStr));
        }
        export.append("******\n");
        return export.toString();
    }

    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(dishes);
            oos.writeObject(orders);
        }
    }

    public void loadFromFile(String filename) {
        if (!new File(filename).exists()) {
            System.err.println("Soubor " + filename + " neexistuje.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            dishes = (List<Dish>) ois.readObject();
            orders = (List<Order>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            handleError(String.format("Chyba při načítání souboru %s: %s", filename, e.getMessage()));
        }
    }

    private void handleError(String message) {
        System.err.println("Chyba: " + message);
    }
}
