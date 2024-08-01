package bertcoscia.entities;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Customer {
    protected long id;
    protected String name;
    protected int tier;

    public Customer(String name, int tier) {
        Faker faker = new Faker();
        this.id = Long.parseLong(faker.idNumber().validSvSeSsn().substring(0, 5));
        this.name = name;
        this.tier = tier;
    }

    public Order createOrder(LocalDate orderDate, Product... product) {
        List<Product> products = new ArrayList<>(Arrays.asList(product));
        return new Order(this, products, orderDate);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    @Override
    public String toString() {
        return "Customer {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tier=" + tier +
                '}';
    }
}
