/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package inventorytracker;

/**
 *
 * @author 16045
 */
public class EmptyNameException extends Exception{

    /**
     * Creates a new instance of <code>EmptyNameException</code> without detail
     * message.
     */
  
    public EmptyNameException(String errorMsg) {
        super(errorMsg);
    }
}
