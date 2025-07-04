package Service;

import Product.*;
import java.util.List;

public class ShippingService {
    public void shipItems(List<Shippable> items) {
        System.out.println("** Shipment notice **");
        double totalWeight = 0;

        for (Shippable item : items) {
            System.out.printf("%dx %s\t%.0fg%n",
                    items.stream().filter(i -> i.getName().equals(item.getName())).count(),
                    item.getName(),
                    item.getWeight() * 1000);
            totalWeight += item.getWeight();
        }

        System.out.printf("Total package weight %.1fkg%n%n", totalWeight);
    }
}
