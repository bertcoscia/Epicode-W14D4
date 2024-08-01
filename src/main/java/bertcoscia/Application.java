package bertcoscia;

import bertcoscia.entities.Customer;
import bertcoscia.entities.Order;
import bertcoscia.entities.Product;
import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) throws IOException {
        Faker faker = new Faker();

        Supplier<Customer> randomCustomer = () -> {
            String name = faker.lordOfTheRings().character();
            int tier = faker.number().numberBetween(1, 3);
            return new Customer(name, tier);
        };

        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            customers.add(randomCustomer.get());
        }

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String name = faker.book().title();
            String category = "Books";
            double price = faker.number().randomDouble(2, 10, 250);
            Product product = new Product(name, category, price);
            products.add(product);
        }

        for (int i = 0; i < 10; i++) {
            String name = faker.pokemon().name();
            String category = "Baby";
            double price = faker.number().randomDouble(2, 10, 250);
            Product product = new Product(name, category, price);
            products.add(product);
        }

        for (int i = 0; i < 10; i++) {
            String name = faker.beer().name();
            String category = "Boys";
            double price = faker.number().randomDouble(2, 10, 250);
            Product product = new Product(name, category, price);
            products.add(product);
        }

        List<Order> orders = new ArrayList<>();
        for (Customer customer : customers) {
            int product1 = faker.number().numberBetween(0, 29);
            int product2 = faker.number().numberBetween(0, 29);
            int product3 = faker.number().numberBetween(0, 29);
            LocalDate dateOrder = LocalDate.now();
            Order order = customer.createOrder(dateOrder, products.get(product1), products.get(product2), products.get(product3));
            orders.add(order);
        }

        //--------------------------------------------------------------------EX1--------------------------------------------------------------------//
        System.out.println("//--------------------------------------------------------------------EX1--------------------------------------------------------------------//");
        Map<Customer, List<Order>> ordersByClient = orders.stream().collect(Collectors.groupingBy(Order::getCustomer));
        ordersByClient.forEach((customer, orderList) -> System.out.println(customer + ": " + orderList));

        //--------------------------------------------------------------------EX2--------------------------------------------------------------------//
        System.out.println("//--------------------------------------------------------------------EX2--------------------------------------------------------------------//");
        Map<Customer, Double> totalByCustomer = orders.stream()
                .collect(Collectors.groupingBy(
                                Order::getCustomer,
                                Collectors.summingDouble(order -> order.getProducts().stream().mapToDouble(Product::getPrice).sum())
                        )
                );
        totalByCustomer.forEach((customer, total) -> System.out.println("Customer " + customer.getName() + ", total: " + total));

        //--------------------------------------------------------------------EX3--------------------------------------------------------------------//
        System.out.println("//--------------------------------------------------------------------EX3--------------------------------------------------------------------//");
        List<Product> mostExpensiveProducts = products.stream().sorted(Comparator.comparingDouble(Product::getPrice).reversed()).limit(5).toList();
        mostExpensiveProducts.forEach(System.out::println);

        //--------------------------------------------------------------------EX4--------------------------------------------------------------------//
        System.out.println("//--------------------------------------------------------------------EX4--------------------------------------------------------------------//");
        Map<Order, Double> avgOrderPrice = orders.stream()
                .collect(Collectors.groupingBy(
                                order -> order,
                                Collectors.averagingDouble(order -> order.getProducts().stream().collect(Collectors.averagingDouble(Product::getPrice))
                                )
                        )
                );
        avgOrderPrice.forEach((order, avgPrice) -> System.out.println("Order: " + order.getId() + ", average price: " + avgPrice));

        //--------------------------------------------------------------------EX5--------------------------------------------------------------------//
        System.out.println("//--------------------------------------------------------------------EX5--------------------------------------------------------------------//");
        Map<String, Double> totalByCategory = products.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.summingDouble(Product::getPrice)));
        totalByCategory.forEach((category, price) -> System.out.println(category + " " + price));

        //--------------------------------------------------------------------EX6--------------------------------------------------------------------//
        System.out.println("//--------------------------------------------------------------------EX6--------------------------------------------------------------------//");
        File productsFile = new File("src/products.txt");
        salvaProdottiSuDisco(products, productsFile);

        //--------------------------------------------------------------------EX7--------------------------------------------------------------------//
        System.out.println("//--------------------------------------------------------------------EX7--------------------------------------------------------------------//");
        List<Product> productsFromFile = new ArrayList<>();
        leggiProdottiDaDisco(productsFile, productsFromFile);
        for (Product product : productsFromFile) {
            System.out.println(product);
        }
    }

    public static void salvaProdottiSuDisco(List<Product> productList, File file) throws IOException {
        StringBuilder stringa = new StringBuilder();

        for (Product product : productList) {
            /*FileUtils.writeStringToFile(file, product.getName() + "@" + product.getCategory() + "@" + product.getPrice() + "#" + System.lineSeparator(), StandardCharsets.UTF_8, true);*/
            stringa.append(product.getName()).append("@").append(product.getCategory()).append("@").append(product.getPrice()).append("#").append(System.lineSeparator());
        }
        FileUtils.writeStringToFile(file, stringa.toString(), StandardCharsets.UTF_8);
        System.out.println("Products added to products.txt 😄");
    }

    public static void leggiProdottiDaDisco(File file, List<Product> productsFromFile) throws IOException {
        String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        String[] contentAsArray = content.split(System.lineSeparator());
        for (String string : contentAsArray) {
            String[] productString = string.split("[@#]");
            Product product = new Product(productString[0], productString[1], Double.parseDouble(productString[2]));
            productsFromFile.add(product);
        }
    }
}
