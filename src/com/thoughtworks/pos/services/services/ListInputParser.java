package com.thoughtworks.pos.services.services;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.pos.common.BarCodeNotExistException;
import com.thoughtworks.pos.common.EmptyShoppingChartException;
import com.thoughtworks.pos.domains.Item;
import com.thoughtworks.pos.domains.ShoppingChart;
import com.thoughtworks.pos.domains.User;
import com.thoughtworks.pos.domains.UserShoppingList;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by 5Wenbin on 2016/6/29.
 */
public class ListInputParser{
    private File indexFile;
    private File itemsFile;
    private File usersFile;
    private final ObjectMapper objectMapper;

    public File getIndexFile(){
        return indexFile;
    }

    public File getItemsFile(){
        return itemsFile;
    }

    public File getUsersFile(){
        return usersFile;
    }

    public ListInputParser(File indexFile, File itemsFile, File usersFile) {
        this.indexFile = indexFile;
        this.itemsFile = itemsFile;
        this.usersFile = usersFile;
        objectMapper = new ObjectMapper(new JsonFactory());
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    public ShoppingChart parser() throws IOException,BarCodeNotExistException,EmptyShoppingChartException {
        return BuildShoppingChart(getUserShoppingList(), getItemIndexes(), getUserIndexes());
    }

    private ShoppingChart BuildShoppingChart(UserShoppingList userShoppingList, HashMap<String, Item> itemIndexes, HashMap<String, User> userIndexes) throws BarCodeNotExistException, EmptyShoppingChartException {
        ShoppingChart shoppingChart = new ShoppingChart();
        User user = new User();
        if(!userShoppingList.getUser().isEmpty()){
            if (userIndexes.containsKey(userShoppingList.getUser())) {
                User mappedUser = userIndexes.get(userShoppingList.getUser());
                user = new User(userShoppingList.getUser(), mappedUser.getName(), mappedUser.getIsVIP(), mappedUser.getScore());
                shoppingChart.setUser(user);
            }
            else {
                user = new User(userShoppingList.getUser(), "新会员", true, 0);
                userIndexes.put(userShoppingList.getUser(), user);
                shoppingChart.setUser(user);
                /*Scanner sc = new Scanner(System.in);
                System.out.println("是否要注册会员？（Y/N）");
                String answer =  sc.nextLine();
                if(answer.equals("Y")||answer.equals("y")) {
                    System.out.println("请输入姓名");
                    String name = sc.nextLine();
                    user = new User(userShoppingList.getUser(), name, true, 0);
                    userIndexes.put(userShoppingList.getUser(), user);
                    shoppingChart.setUser(user);*/
                }
            }
        /*else{
            Scanner sc = new Scanner(System.in);
            System.out.println("是否要注册会员？（Y/N）");
            String answer =  sc.nextLine();
            if(answer.equals("Y")||answer.equals("y")) {
                System.out.println("请输入会员编号");
                String userCode = sc.nextLine();
                System.out.println("请输入姓名");
                String name = sc.nextLine();
                user = new User(userCode, name, true, 0);
                userIndexes.put(userShoppingList.getUser(), user);
                shoppingChart.setUser(user);
            }
        }*/
        if(userShoppingList.getItems().length!=0){
            for (String barcode : userShoppingList.getItems()) {
                if(itemIndexes.containsKey(barcode)) {
                    Item mappedItem = itemIndexes.get(barcode);
                    Item item = new Item(mappedItem.getVipDiscount(), barcode, mappedItem.getName(), mappedItem.getUnit(), mappedItem.getPrice(), mappedItem.getDiscount(), mappedItem.getPromotion());
                    shoppingChart.add(item);
                }
                else {
                    throw new BarCodeNotExistException();
                }
            }
        }
        else {
            throw new EmptyShoppingChartException();
        }

        return shoppingChart;
    }

    private UserShoppingList getUserShoppingList() throws IOException {
        String userShoppingListStr = FileUtils.readFileToString(indexFile);
        return objectMapper.readValue(userShoppingListStr, UserShoppingList.class);
    }

    private HashMap<String, Item> getItemIndexes() throws IOException {
        String itemsIndexStr = FileUtils.readFileToString(itemsFile);
        TypeReference<HashMap<String,Item>> typeRef = new TypeReference<HashMap<String,Item>>() {};
        return objectMapper.readValue(itemsIndexStr, typeRef);
    }

    private HashMap<String, User> getUserIndexes() throws IOException {
        String userIndexStr = FileUtils.readFileToString(usersFile);
        TypeReference<HashMap<String,User>> typeRef = new TypeReference<HashMap<String,User>>() {};
        return objectMapper.readValue(userIndexStr, typeRef);
    }
}
