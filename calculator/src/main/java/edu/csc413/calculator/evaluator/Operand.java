package edu.csc413.calculator.evaluator;
/**
 * Operand class used to represent an operand
 * in a valid mathematical expression.
 */
public class Operand {
      private int val;  //holds integer value of Operand. This variable is private, with an accessor(getValue())

    /**
     * construct operand from string token.
     */
  public Operand( String token ) {
      this.val = Integer.parseInt(token);
  }
  /**
   * construct operand from integer
   */
  public Operand( int value ) {
        this.val = value;
  }
  /**
   * return value of operand
   */
  public int getValue() {
      return this.val;
  }
  /**
   * Check to see if given token is a valid
   * operand.
   */
  public static boolean check( String token ) {

      try {
          Integer.parseInt(token);
      }catch(RuntimeException x){
            return false;
      }
        return true;
  }
}
