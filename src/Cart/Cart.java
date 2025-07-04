package Cart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Product.*;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void addItem(Product product, int quantity) {
        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock for " + product.getName());
        }
        items.add(new CartItem(product, quantity));
    }

    public void removeItem(Product product) {
        items.removeIf(item -> item.getProduct().equals(product));
    }

    public double getSubtotal() {
        return items.stream().mapToDouble(CartItem::getItemTotal).sum();
    }

    public List<Shippable> getShippableItems() {
        return items.stream()
                .filter(item -> item.getProduct() instanceof Shippable)
                .map(item -> (Shippable) item.getProduct())
                .collect(Collectors.toList());
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public List<CartItem> getItems() { return items; }
}