import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class RestaurantOrderingSystem {
    private final List<MenuItem> menu = new ArrayList<>();
    private final List<OrderItem> cart = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);
    private final DecimalFormat money = new DecimalFormat("0.00");
    private final String ORDER_HISTORY_FILE = "orders.txt";

    public RestaurantOrderingSystem() {
        seedMenu();
    }

   
    private void seedMenu() {
        menu.add(new MenuItem(1, "Margherita Pizza", 299.00));
        menu.add(new MenuItem(2, "Farmhouse Pizza", 349.00));
        menu.add(new MenuItem(3, "Veg Burger", 129.00));
        menu.add(new MenuItem(4, "Grilled Sandwich", 99.00));
        menu.add(new MenuItem(5, "Pasta Alfredo", 199.00));
        menu.add(new MenuItem(6, "French Fries", 79.00));
        menu.add(new MenuItem(7, "Cold Coffee", 89.00));
        menu.add(new MenuItem(8, "Chocolate Shake", 119.00));
    }

   
    private void showMenu() {
        System.out.println("\n--- MENU ---");
        for (MenuItem m : menu) System.out.println(m);
    }

    private MenuItem findMenuItemById(int id) {
        for (MenuItem m : menu) if (m.getId() == id) return m;
        return null;
    }

   
    private void addToCart() {
        while (true) {
            try {
                showMenu();
                System.out.print("Enter item ID to add (0 to stop): ");
                int id = Integer.parseInt(sc.nextLine().trim());
                if (id == 0) return;

                MenuItem mi = findMenuItemById(id);
                if (mi == null) {
                    System.out.println("Invalid ID. Please choose a valid menu item.");
                    continue;
                }

                System.out.print("Quantity: ");
                int qty = Integer.parseInt(sc.nextLine().trim());
                if (qty <= 0) {
                    System.out.println("Quantity must be positive.");
                    continue;
                }

                boolean found = false;
                for (OrderItem oi : cart) {
                    if (oi.getItem().getId() == mi.getId()) {
                        oi.addQuantity(qty);
                        found = true;
                        break;
                    }
                }
                if (!found) cart.add(new OrderItem(mi, qty));
                System.out.println(qty + " x " + mi.getName() + " added to cart.");
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter numeric values only.");
            }
        }
    }

    
    private void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        System.out.println("\n--- YOUR CART ---");
        int i = 1;
        for (OrderItem oi : cart) {
            System.out.printf("%d) %s x %d = %s\n", i++, oi.getItem().getName(), oi.getQuantity(), money.format(oi.lineTotal()));
        }
        System.out.println("Subtotal: " + money.format(calculateSubtotal()));
    }

    private double calculateSubtotal() {
        double s = 0.0;
        for (OrderItem oi : cart) s += oi.lineTotal();
        return s;
    }

   
    private double applyDiscounts(double subtotal) {
        double finalTotal = subtotal;
        if (subtotal >= 1000) {
            finalTotal = subtotal * 0.90; 
            System.out.println("Automatic discount applied: 10% off for orders >= 1000");
        }

        System.out.print("Have a coupon code? (y/n): ");
        String ans = sc.nextLine().trim().toLowerCase();
        if (ans.equals("y")) {
            System.out.print("Enter coupon code: ");
            String code = sc.nextLine().trim();
            if (code.equalsIgnoreCase("SAVE50") && subtotal >= 500) {
                finalTotal -= 50;
                System.out.println("Coupon SAVE50 applied: -50.00");
            } else {
                System.out.println("Invalid or inapplicable coupon.");
            }
        }
        if (finalTotal < 0) finalTotal = 0;
        return finalTotal;
    }

   
    private String generateBill(double subtotal, double discountAmount, double finalTotal) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===== BILL =====\n");
        sb.append("Date: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n\n");
        for (OrderItem oi : cart) {
            sb.append(String.format("%s x %d = %s\n", oi.getItem().getName(), oi.getQuantity(), money.format(oi.lineTotal())));
        }
        sb.append("\nSubtotal: ").append(money.format(subtotal)).append("\n");
        sb.append("Discounts: -").append(money.format(discountAmount)).append("\n");
        sb.append("TOTAL: ").append(money.format(finalTotal)).append("\n");
        sb.append("================\n");
        return sb.toString();
    }

    
    private void saveOrderHistory(String billText) {
        try {
            FileUtils.appendToFile(ORDER_HISTORY_FILE, billText);
            System.out.println("Order saved to " + ORDER_HISTORY_FILE);
        } catch (IOException e) {
            System.out.println("Failed to save order history: " + e.getMessage());
        }
    }

  
    private void checkout() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Add items before checkout.");
            return;
        }
        double subtotal = calculateSubtotal();
        double finalTotal = applyDiscounts(subtotal);
        double discountAmount = subtotal - finalTotal;
        String bill = generateBill(subtotal, discountAmount, finalTotal);
        System.out.println(bill);
        saveOrderHistory(bill);
        cart.clear();
    }

  
    private void removeFromCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        viewCart();
        try {
            System.out.print("Enter the item number to remove (0 to cancel): ");
            int idx = Integer.parseInt(sc.nextLine().trim());
            if (idx == 0) return;
            if (idx < 1 || idx > cart.size()) {
                System.out.println("Invalid selection.");
                return;
            }
            cart.remove(idx - 1);
            System.out.println("Item removed.");
        } catch (NumberFormatException nfe) {
            System.out.println("Please enter numeric values only.");
        }
    }

  
    public void start() {
        System.out.println("Welcome to Restaurant Ordering System");
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1) Show Menu\n2) Add to Cart\n3) View Cart\n4) Remove from Cart\n5) Checkout\n0) Exit");
            System.out.print("Your choice: ");
            String input = sc.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1: showMenu(); break;
                    case 2: addToCart(); break;
                    case 3: viewCart(); break;
                    case 4: removeFromCart(); break;
                    case 5: checkout(); break;
                    case 0:
                        System.out.println("Thank you! Goodbye.");
                        return;
                    default: System.out.println("Invalid choice."); break;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a number from the menu.");
            }
        }
    }

    public static void main(String[] args) {
        new RestaurantOrderingSystem().start();
    }
}


class MenuItem {
    private final int id;
    private final String name;
    private final double price;

    public MenuItem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return id + ") " + name + " - " + price;
    }
}


class OrderItem {
    private final MenuItem item;
    private int quantity;

    public OrderItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public MenuItem getItem() { return item; }
    public int getQuantity() { return quantity; }

    public void addQuantity(int q) { this.quantity += q; }

    public double lineTotal() {
        return item.getPrice() * quantity;
    }
}


class FileUtils {
    public static void appendToFile(String filename, String text) throws IOException {
        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(text + System.lineSeparator());
        }
    }
}
