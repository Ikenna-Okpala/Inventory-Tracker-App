/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventorytracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 *  the InventoryManager class is responsible for executing queries received from the InventoryTracker
 *  acts as an intermediary class between InventoryTracker and Item classes
 */

public class InventoryManager 
{
    
    public static InventoryManager instance = new InventoryManager();
    private HashMap<Integer, Item> inventoryStorage = new HashMap<>();
    
    private InventoryManager()
    {
        
    }
    
    /**
    * Creates an item in storage with values from parameterS and returns the itemID
    * @param name           the name of the item
    * @param category       the category of the item as defined in enum Category
    * @param description    a brief description of the item
    * @param initQuantity   the initial quantity of the item
    * @param price          the price of the item
    * @return itemID        the key that identifies the item for quicker lookup, edits, and deletes
    */
    
    public int createItem(String name, String category, String description, int initQuantity, double price) throws ItemCreationException, InvalidCategoryException, EmptyNameException
    {
        int itemID = ItemUtils.isItemPresent(inventoryStorage, name, category);
        
        ItemUtils.validateName(name);
        
        Item newItem = new Item(name, category, description, initQuantity, price);
        inventoryStorage.put(itemID, newItem);
        newItem.setItemID(itemID);
        
        return itemID;
    }
    
    /**
    * Helper function for editItem to update itemID by rehashing
    * @param newName       the new name of the item
    * @param newCategory   the new category the item belongs to
    * @param itemID        the current itemID
    * @return newItemID    the new ID that is mapped to this item 
    */
    
    private int updateItemID(String newName, String newCategory, int itemID) throws ItemCreationException, InvalidCategoryException
    {
        int newItemID = ItemUtils.isItemPresent(inventoryStorage, newName, newCategory);
         
        Item targetItem = inventoryStorage.get(itemID);
        targetItem.setName(newName);
        targetItem.setCategory(newCategory);
         
        inventoryStorage.remove(itemID);
        inventoryStorage.put(newItemID, targetItem);
         
        targetItem.setItemID(newItemID);
         
        return newItemID;
    }
    
    /**
    * edits item's data as specified by the fields and changes
    * @param itemID        the key mapped to the specific item
    * @param fields        an array that contains fields the user wants to update
    * @param changes       an array containing new values such that the field will be set to the new value for every index of the array
    * @return newItemID    the new ID that is mapped to this item 
    */
    
    public int editItem(int itemID, String [] fields, String [] changes) throws InvalidCategoryException, NumberFormatException, InvalidFieldException, ItemCreationException, FieldMismatchException, InvalidItemKeyException
    {
        if(fields.length != changes.length)
        {
            throw new FieldMismatchException("***Item Updation failed, fields do not match with values***");
        }
        
        Item targetItem = inventoryStorage.get(itemID);
        
        if(targetItem == null)
        {
            throw new InvalidItemKeyException("***Item Updation failed, try copying item key from previous references***");
        }
        
        int indexForName = -1, indexForCategory = -1;
        
        //iterate through fields then if query matches with field, set field to its new value
        
        for(int i = 0; i < fields.length; i++)
        {
            if(fields[i].equalsIgnoreCase(Fields.Name.toString()))
            {
                indexForName = i;
            
            }
            else if(fields[i].equalsIgnoreCase(Fields.Category.toString()))
            {
                indexForCategory = i;
            }
            
            else if(fields[i].equalsIgnoreCase(Fields.Description.toString()))
            {
                targetItem.setDescription(changes[i]);
            }
            
            else if(fields[i].equalsIgnoreCase(Fields.Quantity.toString()))
            {
                targetItem.setInitQuantity(Integer.parseInt(changes[i]));
                targetItem.setCurrQuantity(Integer.parseInt(changes[i]));
            }
            
            else if(fields[i].equalsIgnoreCase(Fields.Price.toString()))
            {
                targetItem.setPrice(Double.parseDouble(changes[i]));
            }
            
        }
        
        // rehash itemID based on new name and category then set name field and set category field
        
        if(indexForName != -1 && indexForCategory != -1)
        {
            itemID = updateItemID(changes[indexForName], changes[indexForCategory], itemID);
        }
        
        else if(indexForName != -1)
        {
            itemID = updateItemID(changes[indexForName], targetItem.getCategory().toString(), itemID);
        }
        
        else if(indexForCategory != -1)
        {
            itemID = updateItemID(targetItem.getName(), changes[indexForCategory], itemID);
        }
        
        return itemID;
    }
    
     /**
    * deletes item in storage
    * @param itemID         the key mapped to the specific item
    * 
    */
    
    public void deleteItem(int itemID) throws InvalidItemKeyException
    {
        if(inventoryStorage.containsKey(itemID))
        {
            inventoryStorage.remove(itemID);
        }
        
        else
        {
            throw new InvalidItemKeyException("***Item Updation failed, try copying item key from previous references***");
        }
       
    }
    
      /**
    * sort items based on item.position
    * @param itemCollection        a collection of item objects to be sorted
    * @return itemList             the sorted ArrayList of items
    */
    
    private ArrayList<Item> sortItems(Collection<Item> itemCollection)
    {
        ArrayList<Item> itemList = new ArrayList(itemCollection);
        Collections.sort(itemList, (item1,item2)-> item1.getPosition() - item2.getPosition());
        return itemList;
    }
    
     /**
    * determine if an item should be filtered out from list
    * @param targetItem    the item currently being screened
    * @param fields        an array that contains fields to be filtered
    * @param filters       an array that contains value of filters which would filter the list of items
    */
    
    private boolean shoudBeFilteredOut(Item targetItem, String fields[], String filters[]) throws InvalidFieldException
    {
        for(int j = 0; j < fields.length; j++)
        {
            String field = fields[j];
            String filter = filters[j];
            
            if(field.equalsIgnoreCase(Fields.Name.toString()) && !filter.equalsIgnoreCase(targetItem.getName()))
            {
                return true;
            }
                
            else if(field.equalsIgnoreCase(Fields.Category.toString()) && !filter.equalsIgnoreCase(targetItem.getCategory().toString()))
            {
               return true;
            }
                
            else if(field.equalsIgnoreCase(Fields.Quantity.toString()) && Integer.parseInt(filter) > targetItem.getInitQuantity())
            {
                return true;
            }
                
            else if(field.equalsIgnoreCase(Fields.Price.toString()) && Double.parseDouble(filter) > targetItem.getPrice())
            {
                return true;
            }
            
            if(field.equalsIgnoreCase(Fields.Description.toString()))
            {
                throw new InvalidFieldException("***Item Filteration failed, cannot filter based on description***");
            }
                
        }
        
        return false;
    }
    
     /**
    * print item's data to console
    * @param targetItem    the item about to be printed
    */
    
    private void printItem(Item targetItem)
    {
        final String DATA_FORMAT = "%-30s%-30s%-30s%-30s%-30s%-30d%n";
        
        // get currQuantity and initQuantity and represent the values as currQuantity/initQuantity
        
        StringBuilder quantityBuilder = new StringBuilder();
        String quantityString = quantityBuilder
        .append(targetItem.getCurrQuantity())
        .append("/")
        .append(targetItem.getInitQuantity())
        .toString();
                
        String priceString = String.format("%.2f", targetItem.getPrice());
        
        String categoryString = ItemUtils.capitalize(targetItem.getCategory().toString());
                
        System.out.printf(DATA_FORMAT,targetItem.getName(), categoryString, 
        targetItem.getDescription(), quantityString, priceString, targetItem.getItemID());
    }
    
     /**
    * filter list of items based on filters
    * @param fields        an array that contains fields to be filtered
    * @param filters       an array that contains value of filters which would filter the list of items
    */
    
    private void filterView(String fields[], String filters[]) throws InventoryEmptyException, FieldMismatchException, NumberFormatException, InvalidFieldException
    {
        
        if(fields.length != filters.length)
        {
            throw new FieldMismatchException("***Item Filtration failed, fields do not match with values***");
        }
        
        Collection<Item> itemCollection = inventoryStorage.values();
        if(itemCollection.isEmpty())
        {
            throw new InventoryEmptyException("No item in inventory.");
        }
        
        ArrayList<Item> itemList = sortItems(itemCollection);
           
        final String HEADER_FORMAT = "%-30s%-30s%-30s%-30s%-30s%-30s%n";
        
        int countItem = 0;
        boolean isHeaderPrinted = false;
        
        // filter items 
        
        for(int i = 0; i < itemList.size(); i++)
        {
            Item currItem = itemList.get(i);
            
            if(shoudBeFilteredOut(currItem, fields, filters))
            {
                continue;
            }
            
            if(!isHeaderPrinted)
            {
                System.out.printf(HEADER_FORMAT, Fields.Name.toString(), Fields.Category.toString(), Fields.Description.toString(), 
                Fields.Quantity.toString(), Fields.Price.toString(), Fields.ItemID.toString());
                
                isHeaderPrinted = true;
            }
            
            printItem(currItem);
           
            countItem++;     
        }
        
        if(countItem == 0)
        {
            throw new InventoryEmptyException("No result matches these filters. Try reducing the filters.");
        }
        
        //Future task: Make filtering by name less restrictive so that other items similar by name will be displayed to user
        //For example, filter by name:"Cap" only will not filter out items with names such as "hat", "beanie", etc
        //The most relevant results will be displayed first
    }
    
     /**
    * displays all the items in the inventory as a list
    */
    
    public void view() throws InventoryEmptyException, FieldMismatchException, NumberFormatException, InvalidFieldException
    {
        String fields [] = {};
        String filters [] = {};
        filterView(fields, filters);
    }
    
     /**
    * filter list of items based on filters
    * @param fields        an array that contains fields to be filtered
    * @param filters       an array that contains value of filters which would filter the list of items
    */
    
    public void view(String fields [], String filters[]) throws InventoryEmptyException, FieldMismatchException, NumberFormatException, InvalidFieldException
    {
        filterView(fields, filters);
    }
    
     /**
    * ensures only one instance of inventoryManager is created
    * @return   the only instance of InventoryManager
    */
    
    public static InventoryManager getInstance()
    {
        return instance;
    }
    
    //Future task: An authentication system for user so that any other user will not be allowed to alter the items in inventory
}
