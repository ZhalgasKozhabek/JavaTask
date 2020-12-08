package kz.springboot.springbootuser.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    public Item(Long id, String name, String description, String type, int price, int amount, int stars, String smallPicURL, String largePicURL,  boolean inTopPage, Brand brand) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.amount = amount;
        this.stars = stars;
        this.smallPicURL = smallPicURL;
        this.largePicURL = largePicURL;
        this.inTopPage = inTopPage;
        this.brand = brand;
    }

    public Item(String name, String description, String type, int price, int amount, int stars, String smallPicURL, String largePicURL, boolean inTopPage, Brand brand) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.amount = amount;
        this.stars = stars;
        this.smallPicURL = smallPicURL;
        this.largePicURL = largePicURL;
        this.addedDate = new Timestamp(new Date().getTime());
        this.inTopPage = inTopPage;
        this.brand = brand;
    }

    public Item(Long id, String name, String description, String type, int price, int amount, int stars, String smallPicURL, String largePicURL, boolean inTopPage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.amount = amount;
        this.stars = stars;
        this.smallPicURL = smallPicURL;
        this.largePicURL = largePicURL;
        this.addedDate = new Timestamp(new Date().getTime());
        this.inTopPage = inTopPage;
//        this.brand = brand;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="name", length=500)
    private String name;

    @Column(name="description", length=500)
    private String description;

    @Column(name="type")
    private String type;

    @Column(name="price")
    private int price;

    @Column(name="amount")
    private int amount;

    @Column(name="stars")
    private int stars;

    @Column(name="smallPicURL", length = 500)
    private String smallPicURL;

    @Column(name="largePicURL", length = 500)
    private String largePicURL;

    @Column(name="addedDate")
    private Date addedDate;

    @Column(name="inTopPage")
    private boolean inTopPage;

    @ManyToOne(fetch = FetchType.EAGER)
    private Brand brand;


    @ManyToMany(fetch = FetchType.EAGER)
    private List<Category> categories;




}
