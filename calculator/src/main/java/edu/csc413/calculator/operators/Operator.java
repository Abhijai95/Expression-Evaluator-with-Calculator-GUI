package edu.csc413.calculator.operators;

import edu.csc413.calculator.evaluator.Operand;

import java.util.HashMap;

public abstract class Operator {

    private static final HashMap<String, Operator> operators = new HashMap<>();    //declaring as static because it belongs to the actual class. Declaring as final because we want it to be mutable, but only to point to a single hash map.

    static {   //We need a static block to initialize the static Hash Map. This block of code is only run once.

        operators.put("+", new AddOperator());
        operators.put("/", new DivideOperator());
        operators.put("*", new MultiplyOperator());
        operators.put("^", new PowerOperator());
        operators.put("-", new SubtractOperator());
        operators.put("(", new LeftParenthesisOperator());
        operators.put(")", new RightParenthesisOperator());
    }


    public abstract int priority();

    public abstract Operand execute(Operand op1, Operand op2);


    public static boolean check(String token) {     //determines if a token corresponds to a valid operator
        return operators.containsKey(token);   //returns true if the key is in the hash map, false otherwise
    }

    public static Operator getOperator(String s) {
        return (operators.getOrDefault(s, null));    //returns an instance of the operator if it exists, otherwise it returns a null value by default
    }
}