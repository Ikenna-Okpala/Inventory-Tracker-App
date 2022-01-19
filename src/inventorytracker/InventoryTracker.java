/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package inventorytracker;


import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 16045
 */
public class InventoryTracker {

    /**
     * the InventoryTracker class is responsible for handling the UI logic for the app
     * @param args the command line arguments
     */
    
    private static Scanner input = new Scanner(System.in);
    private static InventoryManager inventoryManager = InventoryManager.getInstance();
    
    /**
    * displays UI to show all options
    */
    
    private static void showOptions()
    {
        System.out.println("\nSelect an option by choosing one of these numbers.");
        
        System.out.println("1 Create item");
        System.out.println("2 Edit item");
        System.out.println("3 Delete item");
        System.out.println("4 View all items");
        System.out.println("5 Filter items");
        System.out.println("6 View options");
        System.out.println("7 Tips");
        System.out.print("8 Exit\n");
    }
    
    /**
    * displays UI for tips
    */
    
    private static void showTips()
    {
        System.out.println("\n-------------------Tips-------------------");
        
        System.out.print("\n1 These are the fields: ");
        Fields fields [] = Fields.values();
        int i = 0;
        for(; i < fields.length-2; i++)
        {
            System.out.printf("%s, ", fields[i].toString());
        }
        System.out.printf("and %s.\n", fields[i].toString());
        
        System.out.print("\n2 These are the categories: ");
        int j = 0;
        Category categories[] = Category.values();
        for(; j < categories.length-1; j++)
        {
            System.out.printf("%s, ", ItemUtils.capitalize(categories[j].toString()));
        }
        System.out.printf("and %s.\n", ItemUtils.capitalize(categories[j].toString()));
        
        System.out.println("\n3 When entering a list of fields or values, separate each field or value with a \"/\".");
        System.out.println("  For example: Meat/Groceries/Soft/89/72.7.");
        
        System.out.println("\n4 Filtering items based on description is not allowed.");
    }
    
    /**
    * gets user's choice that determines how the user will like to manage inventory
    * @return   the choice the user selected
    */
    
    private static String getSelectedOption()
    {
        System.out.print("\nSelect your choice: ");
        String option = input.nextLine();
        
        return option;
    }
    
    /**
    * displays UI for the create functionality
    */
    
    private static void createOption() throws ItemCreationException, InvalidCategoryException, FieldMismatchException, NumberFormatException, EmptyNameException
    {
        final int NUM_OF_FIELDS = 5;
        
        System.out.println("\nFill in the fields in the exact order of fields shown above.");
        
        System.out.print("Enter item details: ");
        String values [] = input.nextLine().split("/",0);
        
        if(values.length < NUM_OF_FIELDS)
        {
            throw new FieldMismatchException("***Item Creation failed, fields do not match with values***");
        }
        
        int itemID = inventoryManager.createItem(values[0], values[1], values[2],
        Integer.parseInt(values[3]), Double.parseDouble(values[4]));
        
        System.out.printf("Item creation successful, use this key %d for reference to this item.\n", itemID);
    }
    
    /**
    * formats selected fields for display
    * @param fields the fields selected by the user
    * @return       a string that consist of all capitalized fields
    */
    
    private static String formatSelectedFields(String fields []) throws IllegalArgumentException
    {
        
        StringBuilder fieldsBuilder = new StringBuilder();
        int j = 0;
        
        for(; j < fields.length-1; j++)
        {
            String field = ItemUtils.capitalize(fields[j]);
            
            ItemUtils.validateField(field);
            
            fieldsBuilder.append(field).append(", ");
        }
        
        String field = ItemUtils.capitalize(fields[j]);
        
        ItemUtils.validateField(field);
        
        return fieldsBuilder.append(field).toString(); 
    }
    
    /**
    * displays UI for the edit functionality
    */
    
    private static void editOption() throws InvalidCategoryException, NumberFormatException, InvalidFieldException, ItemCreationException, FieldMismatchException, InvalidItemKeyException
    {
        System.out.print("\nEnter item key: ");
        int itemID = Integer.parseInt(input.nextLine());
        
        System.out.println("In no order, include fields you will like to edit.");
        
        System.out.print("Enter fields: ");
        String fields [] = input.nextLine().split("/",0);
        
        String formattedFields = formatSelectedFields(fields);
        
        System.out.printf("Include new values for %s following the same order.\n", formattedFields);
        
        System.out.print("Enter values: ");
        String updates[] = input.nextLine().split("/",0);
        
        itemID = inventoryManager.editItem(itemID, fields, updates);
        
        System.out.printf("Item updation successful, use this key %d for reference to this item.\n", itemID);
    }
    
    /**
    * displays UI for the delete functionality
    */
    
    private static void deleteOption() throws InvalidItemKeyException, NumberFormatException
    {
        System.out.print("Enter item key: ");
        int itemID  = Integer.parseInt(input.nextLine());
        
        inventoryManager.deleteItem(itemID);
        
        System.out.println("Item deletion successful.");
    }
    
     /**
    * displays UI for the view inventory functionality
    */
    
    private static void viewOption() throws InventoryEmptyException, FieldMismatchException, NumberFormatException, InvalidFieldException
    {
        System.out.println("");
        inventoryManager.view();
    }
    
     /**
    * displays UI for the filter functionality
    */
    
    private static void filterViewOption() throws InventoryEmptyException, FieldMismatchException, NumberFormatException, InvalidFieldException
    {
        System.out.println("\nIn no order, include fields you will like to filter.");
         
        System.out.print("Enter fields: ");
        String fields [] = input.nextLine().split("/",0);
        
        String formattedFields = formatSelectedFields(fields);
        
        System.out.printf("Add filters for the fields: %s in the same order.\n", formattedFields);
        
        System.out.print("Enter filters: ");
        String filters[] = input.nextLine().split("/",0);
        System.out.println("");
        
        inventoryManager.view(fields, filters);
    }
    
    /**
    * starts to display UI for the app
    */
    
    private static void start()
    {
        final String END = "8";

        System.out.println("<<<Welcome to your Inventory Tracker>>>");
        System.out.println("Before you begin...");

        System.out.print("Enter your user name: ");
        String username = input.nextLine();
            
        showTips();
            
        System.out.print("\nEnter any character when you are ready: ");
        input.nextLine();
            
        System.out.printf("\n%s, how will you like to track your inventory?", username);
        showOptions();

        while(true)
        {
            try
            {
                String choice = getSelectedOption();

                if(choice.equals("1"))
                {
                    createOption();
                }

                else if(choice.equals("2"))
                {
                    editOption();
                }
                
                else if(choice.equals("3"))
                {
                    deleteOption();
                }
                
                else if(choice.equals("4"))
                {
                    viewOption();
                }
                
                else if(choice.equals("5"))
                {
                    filterViewOption();
                }
        
                else if(choice.equals("6"))
                {
                    showOptions();
                }
                
                else if(choice.equals("7"))
                {
                    showTips();
                }
                
                else if(choice.equals("8"))
                {
                    break;
                }
                
                else
                {
                    System.out.println("Please, enter a valid number(1-8).");
                }
                
            }
            
            catch(ItemCreationException | InvalidCategoryException |FieldMismatchException | InvalidFieldException | InvalidItemKeyException | InventoryEmptyException | EmptyNameException ex)
            {
                System.out.println(ex.getMessage());
            }
                
            catch(NumberFormatException ex)
            {
                System.out.println("***Item Operation failed, the input is invalid");
            }
                
            catch(IllegalArgumentException ex)
            {
                System.out.println("***Item operation failed, select view options to see acceptable fields and categories***");
            }
            
            catch(Exception ex)
            {
                System.out.println("***An error occurred, try again***");
            }
                   
        }
    }
    
    public static void main(String[] args)
    {
        start();
        
        System.out.println("Exited.....");
    }
    
    //Future task: Add GUI feature to the app to allow for better inventory tracking experience
}
