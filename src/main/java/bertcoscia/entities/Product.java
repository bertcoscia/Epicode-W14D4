package bertcoscia.entities;

import com.github.javafaker.Faker;

public class Product {
    protected long id;
    protected String name;
    protected String category;
    protected double price;

    public Product(String name, String category, double price) {
        Faker faker = new Faker();
        this.id = Long.parseLong(faker.idNumber().validSvSeSsn().substring(0, 5));
        this.name = name;
        this.category = category;
        this.price = price;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product {" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}
