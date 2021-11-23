package poc.graphql.models;

import lombok.Data;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
public class Cart {

    private String id;

    private List<Item> items;

    private String name;

    private Purpose purpose;

    private int shareCode;

    private User user;

    private Date createTime;

    private Date updateTime;

    public Cart(String name, Purpose purpose) {
        this.name = name;
        this.purpose = purpose;
        this.createTime = new Date();
        this.updateTime = new Date();
        this.id = name + "-" + Calendar.getInstance().hashCode();

        this.shareCode = Objects.hash(name, createTime);
    }

    public Cart(String name, Purpose purpose, List<Item> items) {
        this(name, purpose);
        this.items = items;
    }


    public Cart(String id, String name, List<Item> items, Purpose purpose, Date creationDate, Date updateDate,
//                User owner,
                int shareCode) {
        this.id = id;
        this.name = name;
        this.items = items;
        this.purpose = purpose;
        this.createTime = creationDate;
        this.updateTime = updateDate;
//        this.user = owner;
        this.shareCode = shareCode;
    }
}

