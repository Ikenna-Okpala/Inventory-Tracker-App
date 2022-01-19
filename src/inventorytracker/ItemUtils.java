/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventorytracker;

import java.util.HashMap;

/**
 *  the ItemUtils class provides helper functionality to both InventoryManager and Item classes
 *  It performs function such as validating inputs and representing data in a more acceptable format
 */

public class ItemUtils 
{
     /**
    * removes all spaces in name to ensure the correct itemID is generated
    * @param name                   the name of the item
    * @return the name without white spaces to guarantee all string entry with the same characters regardless of white spaces have the same hash code 
    */
    
    private static String removeSpaces(String itemName)
    {
        StringBuilder nameBuilder = new StringBuilder();
        for(int i = 0; i < itemName.length(); i++)
        {
            if(itemName.charAt(i) != ' ')
            {
                nameBuilder.append(itemName.charAt(i));
            }
        }
        
        return nameBuilder.toString();
   }
  
     /**
    * determines if item is present in inventory. If it is not, the function returns the itemID for the item 
    * @param inventoryStorage         the inventory storage for the items
    * @param itemName                 the name of the item being considered
    * @param itemCategory             the category of the item
    * @return itemID                  the key mapped to the item
    */
    
    public static int isItemPresent(HashMap<Integer, Item> inventoryStorage, String itemName, String itemCategory) throws ItemCreationException, InvalidCategoryException
    {
        itemName = removeSpaces(itemName.toLowerCase());
        String category = convertToCategory(itemCategory).toString();
        
        // get name+category hash code
        
        String stringID = itemName + category;
        int itemID = Math.abs(stringID.hashCode());
        
        if(inventoryStorage.containsKey(itemID))
        {
            throw new ItemCreationException("***Item creation failed, try creating an item that is not in inventory***");
        }
        
        return itemID;
   }
    
     /**
    * convert category from user input(string) to enum Category
    * @param category        the category of the item
    * @return                the Category enum corresponding to the string category
    */
    
    public static Category convertToCategory(String category) throws InvalidCategoryException, IllegalArgumentException
    {
        return Category.valueOf(category.toUpperCase());
    }
  
     /**
    * truncates string input to ensure the format of displaying items in console is not altered
    * @param entry         a string input from user which is either name or description
    * @return              a truncated entry if the number of characters is greater than MAX_LENGTH    
    */
    
    public static String truncateStringInput(String entry)
    {
        final int MAX_LENGTH = 25;
      
        if(entry.length() > MAX_LENGTH)
        {
            StringBuilder shorterStringBuilder = new StringBuilder();
          
            for(int i = 0; i < MAX_LENGTH; i++)
            {
                shorterStringBuilder.append(entry.charAt(i));
            }
          
            return shorterStringBuilder.toString();
        }
        else
        {
            return entry;
        }
   }
    
    /**
     * capitalize the first letter of entry
     * @param entry     the string to be capitalized
     * @return          the capitalized string
     */
    
    public static String capitalize(String entry)
   {
        StringBuilder entryBuilder = new StringBuilder(entry.substring(0, 1).toUpperCase());
        entryBuilder.append(entry.substring(1, entry.length()).toLowerCase());
        
        return entryBuilder.toString();
   }
   
    /**
     * validates name by throwing an error if name is empty
     * @param name  the name of the item.
     */
   
    public static void validateName(String name) throws EmptyNameException
   {
       if(name.isEmpty())
       {
           throw new EmptyNameException("***Item operation failed, the name field cannot be empty***");
       }
   }
    
    /**
     * validates field by throwing an error if field cannot be found in enum Fields
     * @param field
     * @throws IllegalArgumentException
     */
    
    public static void validateField(String field) throws IllegalArgumentException
   {
       Fields.valueOf(field);
   }
    
   
}
