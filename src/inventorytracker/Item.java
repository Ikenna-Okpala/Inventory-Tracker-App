/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventorytracker;

import java.util.HashMap;

/**
 *  the Item class serves as a data center where an item's information is stored
 */

public class Item 
{
    private String name;
    private Category category;
    private String description;
    private int initQuantity;
    private int currQuantity;
    private double price;
    private static int time = 0;
    private int position;
    private int itemID;

    public Item(String name, String category, String description, int initQuantity, double price) throws InvalidCategoryException, IllegalArgumentException
    {
        this.name = ItemUtils.truncateStringInput(name);
        this.category = ItemUtils.convertToCategory(category);
        this.description = ItemUtils.truncateStringInput(description);
        this.initQuantity = initQuantity;
        this.currQuantity = initQuantity;
        this.price = price;
        this.position = time;
        time++;
    }
    
     /**
    * gets the name of the item
    * @return the name of the item
    */
    
    public String getName() 
    {
        return name;
    }

     /**
    * gets the category of the item
    * @return the category of the item
    */
    
    public Category getCategory() 
    {
        return category;
    }

     /**
    * gets description of the item
    * @return the description of the item
    */
    
    public String getDescription() 
    {
        return description;
    }
    
     /**
    * sets the description of the item
    * @param description    contains the new value the description field will be set to
    */
    
    public void setDescription(String description) 
    {
        this.description = description;
    }

     /**
    * get the initial quantity of the item
    * @return   the initial quantity of the item when it was first created
    */
    
    public int getInitQuantity() 
    {
        return initQuantity;
    }
    
     /**
    * sets the initial quantity of the item
    * @param initQuantity     contains the new value the initQuantity field will be set to
    */
    
    public void setInitQuantity(int initQuantity) 
    {
        this.initQuantity = initQuantity;
    }

     /**
    * get the current quantity of the item
    * @return   the current quantity of the item
    */
    
    public int getCurrQuantity() 
    {
        return currQuantity;
    }

     /**
    * sets the current quantity of the item
    * @param currQuantity      contains the new value the currQuantity field will be set to
    */
    
    public void setCurrQuantity(int currQuantity) 
    {
        this.currQuantity = currQuantity;
    }

     /**
    * get the price of the item
    * @return   the price of the item
    */
    
    public double getPrice() 
    {
        
        return price;
    }

     /**
    * sets the price of the item
    * @param price      contains the new value the price field will be set to
    */
    
    public void setPrice(double price) 
    {
        this.price = price;
    }

     /**
    * sets the name of the item
    * @param name      contains the new value the name field will be set to
    */
    
    public void setName(String name) 
    {
        this.name = name;
    }

     /**
    * sets the category of the item
    * @param category      contains the new value the category field will be set to
    */
    
    public void setCategory(String category) throws InvalidCategoryException 
    {
        this.category = ItemUtils.convertToCategory(category);
    }

     /**
    * get the position of the item based on the time the item was created
    * @return   the position of the item
    */
    
    public int getPosition() 
    {
        return position;
    }

     /**
    * get the itemID of the item
    * @return   the key that is mapped to the item
    */
    
    public int getItemID() 
    {
        return itemID;
    }

     /**
    * sets the key of the item
    * @param itemID      contains the new value the itemID field will be set to
    */
    
    public void setItemID(int itemID) 
    {
        this.itemID = itemID;
    }
    
}
