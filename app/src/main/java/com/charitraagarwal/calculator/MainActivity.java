package com.charitraagarwal.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.udojava.evalex.Expression;   // EvalEx
//import org.mariuszgromada.math.mxparser.*; // mXparser


public class MainActivity extends AppCompatActivity {
    TextView expressionDisplay, resultDisplay;

    Button clearBtn, bracketBtn, divisionBtn, backspaceBtn;
    Button sevenBtn, eightBtn, nineBtn, multiplyBtn;
    Button fourBtn, fiveBtn, sixBtn, subtractBtn;
    Button oneBtn, twoBtn, threeBtn, addBtn;
    Button plusMinusBtn, zeroBtn, periodBtn, equalBtn;

    int numberOfBracketsOpen = 0;


    void appendStringToExpressionDisplay(String s) {
        // appends a string to the end of the current string in equation text view
        String oldText = expressionDisplay.getText().toString();
        String newText = oldText + s;
        expressionDisplay.setText(newText);
    }

    boolean isOperatorAtEnd(String text) {
        // checks if last character of the equation text view is an operator
        if(text.isEmpty()) return false;

        // get the character at the end
        char c = text.charAt(text.length()-1);

        if(c=='+' || c=='-' || c=='÷' || c=='×') return true;
        return false;
    }


    ////////////////////////// button press calls ///////////////////////

    void clearBtnPressed() {
        // reset the calculator
        expressionDisplay.setText("");
        resultDisplay.setText("");
        numberOfBracketsOpen = 0;
    }

    void backspaceBtnPressed() {
        // delete last character from equation text view
        String oldText = expressionDisplay.getText().toString();
        if(oldText.isEmpty()) return;

        // manage bracket count on delete
        char lastChar = oldText.charAt(oldText.length()-1);
        if(lastChar == ')')
            numberOfBracketsOpen++;
        if(lastChar == '(')
            numberOfBracketsOpen--;

        String newText = oldText.substring(0, oldText.length()-1);
        expressionDisplay.setText(newText);
    }

    void operatorBtnPressed(String operator) {
        // called when an operator button is pressed
        String oldText = expressionDisplay.getText().toString();
        if(oldText.isEmpty()) return;

        // replace current operator if present
        if(isOperatorAtEnd(oldText)) backspaceBtnPressed();
        appendStringToExpressionDisplay(operator);
    }

    void equalBtnPressed() {
        // Evaluates value of the expression using mXparser library

        String text = expressionDisplay.getText().toString();
        if(text.isEmpty()) return;  // empty expression check
        String equation = text.replace("×", "*").replace("÷", "/");

        Double result;
        // Double result = new Expression(equation).calculate();   // evaluate expression using mXparser

        try{
            result = new Expression(equation).eval().doubleValue();   // evaluate expression using evalex
        } catch (Exception e){
            Toast.makeText(this, "Incorrect Expression", Toast.LENGTH_SHORT).show();
            return;
        }

        // NaN check at incorrect expression in case of mXparser
        if(result.isNaN()){
            Toast.makeText(this, "Incorrect Expression", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert result to string
        String resultText;
        if(result == result.intValue())
            resultText = "=" + Integer.toString(result.intValue()); // remove unnecessary .0
        else
            resultText = "=" + Double.toString(result);
        resultDisplay.setText(resultText);  // update result
    }

    void bracketBtnPressed(){
        String oldText = expressionDisplay.getText().toString();

        if(oldText.isEmpty()) {
            appendStringToExpressionDisplay("(");
            numberOfBracketsOpen++;
            return;
        }

        char lastChar = oldText.charAt(oldText.length()-1);

        if(Character.isDigit(lastChar)) {
            if(numberOfBracketsOpen!=0) {
                appendStringToExpressionDisplay(")");
                numberOfBracketsOpen--;
            } else {
                appendStringToExpressionDisplay("×(");
                numberOfBracketsOpen++;
            }
        }
        else if(lastChar == ')') {
            if(numberOfBracketsOpen == 0) {
                appendStringToExpressionDisplay("×(");
                numberOfBracketsOpen++;
            } else {
                appendStringToExpressionDisplay(")");
                numberOfBracketsOpen--;
            }

        }
        else{
            appendStringToExpressionDisplay("(");
            numberOfBracketsOpen++;
        }
    }

    void plusMinusBtnPressed(){
        Toast.makeText(getApplicationContext(), "Not yet implemented", Toast.LENGTH_LONG).show();
    }

    void periodBtnPressed(){
        Toast.makeText(getApplicationContext(), "Not yet implemented", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expressionDisplay = (TextView)findViewById(R.id.expressionDisplay);
        resultDisplay = (TextView)findViewById(R.id.resultDisplay);

        clearBtn =  (Button)findViewById(R.id.clearBtn);
        bracketBtn =  (Button)findViewById(R.id.bracketBtn);
        divisionBtn =  (Button)findViewById(R.id.divisionBtn);
        backspaceBtn =  (Button)findViewById(R.id.backspaceBtn);

        sevenBtn =  (Button)findViewById(R.id.sevenBtn);
        eightBtn =  (Button)findViewById(R.id.eightBtn);
        nineBtn =  (Button)findViewById(R.id.nineBtn);
        multiplyBtn =  (Button)findViewById(R.id.multiplyBtn);

        fourBtn =  (Button)findViewById(R.id.fourBtn);
        fiveBtn =  (Button)findViewById(R.id.fiveBtn);
        sixBtn =  (Button)findViewById(R.id.sixBtn);
        subtractBtn =  (Button)findViewById(R.id.subtractBtn);

        oneBtn =  (Button)findViewById(R.id.oneBtn);
        twoBtn =  (Button)findViewById(R.id.twoBtn);
        threeBtn =  (Button)findViewById(R.id.threeBtn);
        addBtn =  (Button)findViewById(R.id.addBtn);

        plusMinusBtn =  (Button)findViewById(R.id.plusMinusBtn);
        zeroBtn =  (Button)findViewById(R.id.zeroBtn);
        periodBtn =  (Button)findViewById(R.id.periodBtn);
        equalBtn =  (Button)findViewById(R.id.equalBtn);


        clearBtn.setOnClickListener(v -> clearBtnPressed());
        backspaceBtn.setOnClickListener(v -> backspaceBtnPressed());

        oneBtn.setOnClickListener(v -> appendStringToExpressionDisplay("1"));
        twoBtn.setOnClickListener(v -> appendStringToExpressionDisplay("2"));
        threeBtn.setOnClickListener(v -> appendStringToExpressionDisplay("3"));
        fourBtn.setOnClickListener(v -> appendStringToExpressionDisplay("4"));
        fiveBtn.setOnClickListener(v -> appendStringToExpressionDisplay("5"));
        sixBtn.setOnClickListener(v -> appendStringToExpressionDisplay("6"));
        sevenBtn.setOnClickListener(v -> appendStringToExpressionDisplay("7"));
        eightBtn.setOnClickListener(v -> appendStringToExpressionDisplay("8"));
        nineBtn.setOnClickListener(v -> appendStringToExpressionDisplay("9"));
        zeroBtn.setOnClickListener(v -> appendStringToExpressionDisplay("0"));
        addBtn.setOnClickListener(v -> appendStringToExpressionDisplay("+"));
        subtractBtn.setOnClickListener(v -> appendStringToExpressionDisplay("-"));
        multiplyBtn.setOnClickListener(v -> appendStringToExpressionDisplay("×"));
        divisionBtn.setOnClickListener(v -> appendStringToExpressionDisplay("÷"));

        addBtn.setOnClickListener(v -> operatorBtnPressed("+"));
        subtractBtn.setOnClickListener(v -> operatorBtnPressed("-"));
        multiplyBtn.setOnClickListener(v -> operatorBtnPressed("×"));
        divisionBtn.setOnClickListener(v -> operatorBtnPressed("÷"));

        equalBtn.setOnClickListener(v -> equalBtnPressed());

        bracketBtn.setOnClickListener(v -> bracketBtnPressed());
        plusMinusBtn.setOnClickListener(v -> plusMinusBtnPressed());
        periodBtn.setOnClickListener(v-> periodBtnPressed());
    }
}