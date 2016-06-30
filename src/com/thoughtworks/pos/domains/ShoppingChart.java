<<<<<<< HEAD
package com.thoughtworks.pos.domains;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/12/28.
 */
public class ShoppingChart {
    private ArrayList<Item> items = new ArrayList<Item>();
    private User user = new User();

    public void add(Item item) {
        this.items.add(item);
    }

    public User getUser() { return user; }

    public void setUser(User user){
        this.user = user;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
=======
package com.thoughtworks.pos.domains;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/12/28.
 */
public class ShoppingChart {
    private ArrayList<Item> items = new ArrayList<Item>();
    private User user = new User();

    public void add(Item item) {
        this.items.add(item);
    }

    public User getUser() { return user; }

    public void setUser(User user){
        this.user = user;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
>>>>>>> 2b28a0ac09b1f19b7c4030b9295b86c537e8d9f6
