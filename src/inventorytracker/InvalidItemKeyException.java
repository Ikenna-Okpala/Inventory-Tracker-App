/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package inventorytracker;

/**
 *
 * @author 16045
 */
public class InvalidItemKeyException extends Exception{

    
    public InvalidItemKeyException(String errorMsg) {
        super(errorMsg);
    }
}
