package com.thoughtworks.pos.domains;

/**
 * Created by 5Wenbin on 2016/6/29.
 */
public class UserShoppingList {
    private String user = null;
    private String[] items = null;

    public UserShoppingList(){
    }

    public UserShoppingList(String user, String[] items){
        this.setUser(user);
        this.setItems(items);
    }

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
