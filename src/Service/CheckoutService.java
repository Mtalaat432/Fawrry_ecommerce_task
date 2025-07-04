package Service;

import Cart.*;
import Customer.*;
import Product.*;

import java.util.List;

public class CheckoutService {
    private ShippingService shippingService;
    private static final double SHIPPING_RATE_PER_KG = 30.0;

    public CheckoutService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    public void checkout(Customer customer, Cart cart) {
        // Validate cart
        if (cart.isEmpty()) {
            throw new RuntimeException("Cannot checkout with empty cart");
        }

        // Check stock and expiry
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();

            if (product instanceof Expirable && ((Expirable) product).isExpired()) {
                throw new RuntimeException("Product expired: " + product.getName());
            }

            if (item.getQuantity() > product.getQuantity()) {
                throw new RuntimeException("Out of stock: " + product.getName());
            }
        }

        double subtotal = cart.getSubtotal();
        double shippingFee = calculateShippingFee(cart.getShippableItems());
        double total = subtotal + shippingFee;

        if (customer.getBalance() < total) {
            throw new RuntimeException("Insufficient balance");
        }

        customer.deductBalance(total);

        // Update stock
        cart.getItems().forEach(item ->
                item.getProduct().reduceQuantity(item.getQuantity()));

        if (!cart.getShippableItems().isEmpty()) {
            shippingService.shipItems(cart.getShippableItems());
        }

        printReceipt(cart, subtotal, shippingFee, total, customer.getBalance());
    }

    private double calculateShippingFee(List<Shippable> shippableItems) {
        double totalWeight = shippableItems.stream()
                .mapToDouble(Shippable::getWeight)
                .sum();
        return totalWeight * SHIPPING_RATE_PER_KG;
    }

    private void printReceipt(Cart cart, double subtotal, double shipping, double total, double balance) {
        System.out.println("** Checkout receipt **");
        cart.getItems().forEach(item ->
                System.out.printf("%dx %s\t%.2f%n",
                        item.getQuantity(),
                        item.getProduct().getName(),
                        item.getItemTotal()));

        System.out.println("---");
        System.out.printf("Subtotal\t%.2f%n", subtotal);
        System.out.printf("Shipping\t%.2f%n", shipping);
        System.out.printf("Amount\t\t%.2f%n", total);
        System.out.printf("Remaining balance\t%.2f%n%n", balance);
    }
}
