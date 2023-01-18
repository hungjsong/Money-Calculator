package com.example.assignment1_mobileapp_songhj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    public BigDecimal enteredRM = new BigDecimal(0.00);
    public DecimalFormat formattedRM = new DecimalFormat("#,##0.00");
    public String formattedEnteredRM = formattedRM.format(enteredRM);
    BigDecimal hundred = new BigDecimal("100.00");
    BigDecimal fifty = new BigDecimal("50.00");
    BigDecimal twenty = new BigDecimal("20.00");
    BigDecimal ten = new BigDecimal("10.00");
    BigDecimal five = new BigDecimal("5.00");
    BigDecimal one = new BigDecimal("1.00");
    BigDecimal point5 = new BigDecimal("0.50");
    BigDecimal point2 = new BigDecimal("0.20");
    BigDecimal point1 = new BigDecimal("0.10");
    BigDecimal point05 = new BigDecimal("0.05");

    private TextView displayedNumber;
    private TextView bill100;
    private TextView bill50;
    private TextView bill20;
    private TextView bill10;
    private TextView bill5;
    private TextView bill1;
    private TextView coin50;
    private TextView coin20;
    private TextView coin10;
    private TextView coin5;

    BigDecimal[] denominator = {hundred, fifty, twenty, ten, five, one, point5, point2, point1, point05};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Money Calculator");
        displayedNumber = findViewById(R.id.enteredNumber);
        bill100 = findViewById(R.id.bill100);
        bill50 = findViewById(R.id.bill50);
        bill20 = findViewById(R.id.bill20);
        bill10 = findViewById(R.id.bill10);
        bill5 = findViewById(R.id.bill5);
        bill1 = findViewById(R.id.bill1);
        coin50 = findViewById(R.id.coin50);
        coin20 = findViewById(R.id.coin20);
        coin10 = findViewById(R.id.coin10);
        coin5 = findViewById(R.id.coin5);

        if(savedInstanceState != null) {
            enteredRM = BigDecimal.valueOf(savedInstanceState.getDouble("enteredRM"));
            setDisplayNumber();
            calculateChange();
        }
    }

    public void onePressed(View view){buttonPressed(0.01);}
    public void twoPressed(View view){buttonPressed(0.02);}
    public void threePressed(View view){buttonPressed(0.03);}
    public void fourPressed(View view){buttonPressed(0.04);}
    public void fivePressed(View view){buttonPressed(0.05);}
    public void sixPressed(View view){buttonPressed(0.06);}
    public void sevenPressed(View view){buttonPressed(0.07);}
    public void eightPressed(View view){buttonPressed(0.08);}
    public void ninePressed(View view){buttonPressed(0.09);}
    public void zeroPressed(View view){buttonPressed(0);}

    //Subtracts the last number and shifts everything over to the right.
    public void backspacePressed(View view){
        enteredRM = (enteredRM.subtract(enteredRM.remainder(point1))).divide(ten);
        setDisplayNumber();
        calculateChange();
    }

    public void clearPressed(View view){
        enteredRM = enteredRM.multiply(BigDecimal.valueOf(0.00));
        setDisplayNumber();
        calculateChange();
    }

    public void buttonPressed(double buttonValue){
        enteredRM = (enteredRM.multiply(ten));
        enteredRM = (enteredRM.add(BigDecimal.valueOf(buttonValue)));
        setDisplayNumber();
        calculateChange();
    }

    public void calculateChange(){
        BigDecimal valueToCalculate = enteredRM;
        BigDecimal require100Bill = BigDecimal.valueOf(0);
        BigDecimal require50Bill = BigDecimal.valueOf(0);
        BigDecimal require20Bill = BigDecimal.valueOf(0);
        BigDecimal require10Bill = BigDecimal.valueOf(0);
        BigDecimal require5Bill = BigDecimal.valueOf(0);
        BigDecimal require1Bill = BigDecimal.valueOf(0);
        BigDecimal require50Cent = BigDecimal.valueOf(0);
        BigDecimal require20Cent = BigDecimal.valueOf(0);
        BigDecimal require10Cent = BigDecimal.valueOf(0);
        BigDecimal require5Cent = BigDecimal.valueOf(0);

        int i = 0;
        TextView[] bills = {bill100, bill50, bill20, bill10, bill5, bill1, coin50, coin20, coin10, coin5};
        BigDecimal[] requiredBills = {require100Bill, require50Bill, require20Bill, require10Bill, require5Bill, require1Bill,
                                      require50Cent, require20Cent, require10Cent, require5Cent};

        //Calculates how many of a particular bill or coin is needed. .compareTo() equaling 1 means the value is greater than the other whereas 0 would be equal to.
        while(valueToCalculate.compareTo(point05) == 1 || valueToCalculate.compareTo(point05) == 0) {
            requiredBills[i] = calculateRequired(valueToCalculate, denominator[i]);
            valueToCalculate = valueToCalculate.remainder(denominator[i]);
            i++;
        }

        //Sets the textView to the value of required bills or coins.
        for(i = 0; i < bills.length; i++){
            bills[i].setText(String.format(""+requiredBills[i]));
        }
    }

    /*The function calculateRequired calculates how many of a particular bill or coin is needed.
    Variable value1 is the amount entered or its remainder, and variable value2 is the denominator that value1 will be divided by.
    The quotient of value1/value2 will be rounded down to represent how many of that particular bill or coin is needed.*/
    public BigDecimal calculateRequired(BigDecimal value1, BigDecimal value2){
        BigDecimal calculateBills = value1.divide(value2);
        BigDecimal billsRequired = calculateBills.setScale(0, RoundingMode.DOWN);
        return billsRequired;
    }

    //Set the TextView with the ID of enteredNumber to the value of the variable enteredRM after formatting.
    public void setDisplayNumber(){
        formattedEnteredRM = formattedRM.format(enteredRM);
        displayedNumber.setText(String.format("RM%s", formattedEnteredRM));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("enteredRM", enteredRM.doubleValue());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}