# 🍽️ Restaurant Ordering System

A Java console-based restaurant ordering system with full menu, cart, billing, discount and order history features.

---

## 📌 About This Project

This project was built as part of my BSc IT 2nd year Java programming coursework at GLS University.
It simulates a real restaurant ordering experience in a console application using Core Java concepts.

---

## ✨ Features

- 📋 **View Full Menu** — displays all items with prices
- 🛒 **Add to Cart** — add any menu item by number
- 👀 **View Cart** — see all selected items and total
- ❌ **Remove Item** — remove any item from cart
- 💰 **Auto Discount** — 10% discount on orders above Rs.1000
- 🎟️ **Coupon System** — apply coupon codes for extra discount
- 🧾 **Checkout & Bill** — generates full bill with GST
- 📁 **Order History** — saves every order to a text file

---

## 🛠️ Technologies Used

- **Language:** Java (Core Java)
- **Concepts:** OOP, ArrayList, StringBuilder, Scanner, Exception Handling
- **File I/O:** FileWriter — saves order history to file
- **Type:** Console Application (no GUI)

---

## 📁 Project Files

| File | Purpose |
|---|---|
| `RestaurantOrderingSystem.java` | Main file — runs the program |
| `MenuItem.java` | Menu item class (name, price, category) |
| `OrderItem.java` | Order item class (item + quantity) |
| `FileUtils.java` | Handles saving order history to file |

---

## ▶️ How to Run

1. Make sure Java JDK is installed
2. Open terminal/command prompt
3. Navigate to project folder
4. Compile all files:
```bash
javac *.java
```
5. Run the program:
```bash
java RestaurantOrderingSystem
```

---

## 📸 Output Example

```
===================================
    WELCOME TO HARSH RESTAURANT
===================================
1. View Menu
2. Add Item to Cart
3. View Cart
4. Remove Item
5. Checkout
6. Exit

Enter choice: 1

----- MENU -----
1. Burger      Rs.120
2. Pizza       Rs.250
3. Pasta       Rs.180
4. Cold Drink  Rs.60
----------------

Enter choice: 2
Enter item number: 2
Pizza added to cart!

Enter choice: 5
----- YOUR BILL -----
Pizza x1        Rs.250
Subtotal:       Rs.250
GST (5%):       Rs.12.5
Total:          Rs.262.5
Order saved to history!
```

---

## 📚 What I Learned

- Java OOP concepts — Classes, Objects, Methods
- ArrayList for dynamic cart management
- File I/O for saving order history
- Exception handling for invalid inputs
- Menu-driven console application design

---

## 👨‍💻 Author

**Harsh Patel**
BSc IT Student — GLS University, Ahmedabad
GitHub: github.com/harshpatel-15# restaurant-ordering-system
Java console based restaurant ordering system with cart,discount and billing system
