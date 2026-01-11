 E-Commerce Checkout Engine (Java)

A modular Java-based e-commerce checkout system designed using MVC architecture and the Bridge design pattern to support multiple payment methods and payment processors.

This project simulates the core checkout workflow used in real-world e-commerce systems, including cart management, tax calculation, payment processing, and order generation.

Features:

- Product catalog with unique product IDs
- Shopping cart with add, remove, and update quantity functionality
- Automatic subtotal, tax, and total calculation
- Multiple payment methods:
  - Credit Card
  - Debit
  - PayPal
- Multiple payment processors:
  - Stripe (simulated)
  - PayPal (simulated)
  - Mock Bank Processor
- Clean separation of concerns using MVC
- Bridge pattern to decouple payment abstractions from processor implementations
- Console-based UI for interactive checkout
- Order confirmation and receipt generation


Architecture Overview:

This project follows a layered architecture:
ecommerce
 model // Core domain objects (Product, Cart, Order)
 controller // Business logic and workflow orchestration
 payment // Payment abstractions (Bridge pattern)
 processor // Payment processor implementations (Bridge pattern)
 view // Console-based user interface
 Main.java // Application entry point

Design Patterns Used
- MVC (Model–View–Controller)** for clean separation of responsibilities
-  Bridge Pattern to allow payment methods and processors to vary independently

Bridge Pattern Example:

The Bridge pattern allows switching payment processors without modifying payment logic.

java
Payment payment = new CreditCardPayment(new StripeProcessor(), "1234");
payment.pay(totalAmount)

The same CreditCardPayment can be processed via Stripe, PayPal,Bank Processor without changing business logic.
