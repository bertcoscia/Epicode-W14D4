package bertcoscia.entities;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.List;

public class Order {
    protected long id;
    protected String status;
    protected LocalDate orderDate;
    protected LocalDate deliveryDate;
    protected List<Product> products;
    protected Customer customer;

    public Order(Customer customer, List<Product> products, LocalDate orderDate) {
        Faker faker = new Faker();
        this.id = Long.parseLong(faker.idNumber().validSvSeSsn().substring(0, 5));
        this.status = "Accepted";
        this.orderDate = orderDate;
        this.deliveryDate = orderDate.plusDays(2);
        this.products = products;
        this.customer = customer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    @Override
    public String toString() {
        return "Order {" +
                "products=" + products +
                ", customer=" + customer +
                '}';
    }
}
