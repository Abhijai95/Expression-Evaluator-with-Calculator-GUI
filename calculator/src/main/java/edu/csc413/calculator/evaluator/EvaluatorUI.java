package edu.csc413.calculator.evaluator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.csc413.calculator.operators.Operator;

public class EvaluatorUI extends JFrame implements ActionListener {

    private TextField txField = new TextField();
    private Panel buttonPanel = new Panel();

    // total of 20 buttons on the calculator,
    // numbered from left to right, top to bottom
    // bText[] array contains the text for corresponding buttons
    private static final String[] bText = {
            "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3",
            "*", "0", "^", "=", "/", "(", ")", "C", "CE"
    };

    /**
     * C  is for clear, clears entire expression
     * CE is for clear expression, clears last entry up until the last operator.
     */
    private Button[] buttons = new Button[bText.length];    //array of buttons

    public static void main(String argv[]) {

        EvaluatorUI calc = new EvaluatorUI();
    }

    public EvaluatorUI() {
        setLayout(new BorderLayout());
        this.txField.setPreferredSize(new Dimension(600, 50));
        this.txField.setFont(new Font("Courier", Font.BOLD, 28));

        add(txField, BorderLayout.NORTH);
        txField.setEditable(false);

        add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.setLayout(new GridLayout(5, 4));

        //create 20 buttons with corresponding text in bText[] array
        Button bt;
        for (int i = 0; i < EvaluatorUI.bText.length; i++) {
            bt = new Button(bText[i]);
            bt.setFont(new Font("Courier", Font.BOLD, 28));
            buttons[i] = bt;
        }

        //add buttons to button panel
        for (int i = 0; i < EvaluatorUI.bText.length; i++) {
            buttonPanel.add(buttons[i]);
        }

        //set up buttons to listen for mouse input
        for (int i = 0; i < EvaluatorUI.bText.length; i++) {
            buttons[i].addActionListener(this);
        }

        setTitle("Calculator");
        setSize(400, 400);
        setLocationByPlatform(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent arg0) {
        String temp;

        if ((!arg0.getActionCommand().equals("=")) && (!arg0.getActionCommand().equals("C")) && (!arg0.getActionCommand().equals("CE"))) { //Only numbers and mathematical operators
            this.txField.setText(this.txField.getText() + arg0.getActionCommand()); //We display the user's input on the calculator screen by appending to the previous screen value
        } else if (arg0.getActionCommand().equals("=")) {  //if user puts in an equal sign
            Evaluator ev = new Evaluator();
            temp = this.txField.getText();  //temporarily storing the previous field value for evaluation
            try {    //We use a try catch to account for the possibility that the user may enter an invalid expression which would prompt the Evaluator to throw an exception
                int result = ev.eval(temp);
                this.txField.setText(this.txField.getText() + "=" + result); //Appending an equal sign and the result of the evaluation(for valid expressions)
            } catch (Exception x) {     //Catching exception
                this.txField.setText("Invalid!!!(Press C to clear)");
            }

        }
        if (arg0.getActionCommand().equals("C")) {
            this.txField.setText("");       //clearing the screen
        }
        if (arg0.getActionCommand().equals("CE")) {
            String previous_txt = this.txField.getText();   //storing previous text temporarily
            int last_operator_index = 0;                //this variable will hold the index of the last operator
            for (int i = previous_txt.length() - 1; i >= 0; i--) { //We start at the end because that is much more efficient to find the last(right-most) operator
                String str = String.valueOf(previous_txt.charAt(i));    //str holds a String of the value of the character at a certain position
                if (Operator.check(str)) {    //if operator, we update index and break, because we want the last index and we are going left to right
                    last_operator_index = i;
                    break;
                }

            }
            //Now we copy from the previous_txt string to the new string up to(not including) last operator or last operand
            char old_arr[] = previous_txt.toCharArray();
            char[] arr = new char[old_arr.length];
            for (int i = 0; i < last_operator_index; i++) {
                arr[i] = old_arr[i];
            }
            String new_txt = new String(arr);
            txField.setText(new_txt);
        }


    }
}
