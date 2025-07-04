import Customer.Customer;
import Product.*;
import Cart.*;
import Service.*;

import java.util.Date;

public class ECommerceApp {
    public static void main(String[] args) {

        Product cheese = new ExpirableShippableProduct("Cheese", 100, 10,
                new Date(System.currentTimeMillis() + 86400000), 0.4);
        Product tv = new ShippableProduct("TV", 1000, 5, 5.0);
        Product scratchCard = new Product("Scratch Card", 50, 20);

        Customer customer = new Customer("Mahmoud Talaat", 2000);

        Cart cart = new Cart();
        cart.addItem(cheese, 2);
        cart.addItem(tv, 1);
        cart.addItem(scratchCard, 3);

        ShippingService shippingService = new ShippingService();
        CheckoutService checkoutService = new CheckoutService(shippingService);

        checkoutService.checkout(customer, cart);
    }
}