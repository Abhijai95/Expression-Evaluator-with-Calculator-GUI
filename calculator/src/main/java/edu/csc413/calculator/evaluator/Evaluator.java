package edu.csc413.calculator.evaluator;


import edu.csc413.calculator.operators.Operator;

import java.util.Stack;
import java.util.StringTokenizer;

public class Evaluator {
    private Stack<Operand> operandStack;
    private Stack<Operator> operatorStack;
    private StringTokenizer tokenizer;
    private static final String DELIMITERS = "+-*^/()"; //DELIMITERS was modified to include parenthesis

    public Evaluator() {
        operandStack = new Stack<>();
        operatorStack = new Stack<>();
    }

    public int eval(String expression) {
        String token;

        // The 3rd argument is true to indicate that the delimiters should be used as tokens too.
        this.tokenizer = new StringTokenizer(expression, DELIMITERS, true);


        while (this.tokenizer.hasMoreTokens()) {

            if (!(token = this.tokenizer.nextToken()).equals(" ")) {   // filter out spaces

                if (Operand.check(token)) {      //is it an operand?
                    operandStack.push(new Operand(token));   //if operand, we push to stack
                } else {                          //if not space or operand
                    if ((!Operator.check(token))) {   //see if not operator
                        System.out.println("*****invalid token******");
                        throw new RuntimeException("*****invalid token******"); //causes us to STOP here if not operator!!!
                    }
                    //At this point, we can deduce that the token represents an operator

                    Operator newOperator = Operator.getOperator(token); //getting the actual operator object

                    if ((operatorStack.isEmpty()) || (newOperator.priority() == 0)) { //checking if operator stack is empty, or if we have a left parenthesis
                        operatorStack.push(newOperator);
                    } else {   //stack is not empty, and the new operator isn't a left parenthesis

                        if (newOperator.priority() == -1) {    //if right parenthesis
                            while (operatorStack.peek().priority() != 0) {  //We keep processing until we get to the corresponding left parenthesis
                                process();
                            }
                            if (operatorStack.peek().priority() == 0) { //if left parenthesis
                                operatorStack.pop();    //ignoring the left parenthesis after the previous processing is done
                            }
                        } else {  //The new operator must be a mathematical operator as we've already checked for parenthesis
                            while ((operatorStack.peek().priority() >= newOperator.priority())) {  //If top of stack has higher(or equal) priority and stack is not empty, keep processing until that is no longer the case
                                process();
                                if (operatorStack.isEmpty()) //this is important to make sure that we handle the case where there is only 1 operator in the stack. (prevents from peeking at empty stack in loop condition)
                                    break;
                            }

                            operatorStack.push(newOperator);  //Here, we push because we have ensured that the new operator has a higher priority than the current one on the stack(if not empty)

                        }
                    }

                }
            }
        }


        while (!operatorStack.isEmpty()) {    //runs as long as the operator stack is not empty
            process();  //keeps processing until the operator stack is empty, meaning that the expression has been fully processed
        }
        return operandStack.peek().getValue();
    }

    private void process() { //This helper function pops the operator stack once, the operand stack twice, evaluates the result, and pushes it to the operand stack.
        Operator oldOpr = operatorStack.pop();
        Operand op2 = operandStack.pop();
        Operand op1 = operandStack.pop();
        operandStack.push(oldOpr.execute(op1, op2));
    }


}


