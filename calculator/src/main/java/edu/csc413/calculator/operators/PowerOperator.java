package edu.csc413.calculator.operators;

import edu.csc413.calculator.evaluator.Operand;

public class PowerOperator extends Operator{
    private int priority_val = 3;

    public int priority(){

        return priority_val;
    }

    public Operand execute(Operand op1, Operand op2){
        int value1 = op1.getValue();
        int value2 = op2.getValue();
        int result_val = 1;
            for(int i = 0; i < value2; i++){
            result_val *= value1;
        }

        return(new Operand(result_val));
    }
}
