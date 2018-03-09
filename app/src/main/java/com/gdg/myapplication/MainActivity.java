package com.gdg.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity
    extends AppCompatActivity
{
    TextView textView;
    String currentOperation;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        currentOperation = "0";
        setContentView( R.layout.activity_main );
        chargeTextView();
    }

    protected void chargeTextView() {
        textView = (TextView) findViewById( R.id.textView );
    }

    protected void onClick( View v )
    {
        Button currentButton = (Button) v;
        String oppressed = currentButton.getText().toString();
        String last = currentOperation.length()>0?currentOperation.substring(currentOperation.length()-1):"empty";

        if ((isNum(oppressed) && sureDiv(oppressed,last)) || // is num and is sure div (avoid zero div)
            (isSig(oppressed) && !last.equals("empty") && isNum(last)) || // is a sign and there are a num before and the currentoper is not empty
            (isTrigoOrSq(oppressed) && (last.equals("empty") || isSig(last)))) { // is trigo or sqrt and it can stay at start or after a sign
            changeText(currentOperation+=oppressed);
        }
        else if (oppressed.equals(".") && !last.equals("empty") && isNum(last)){ // the point going after a num and it can't stay if it´s empty
            changeText(currentOperation+=oppressed);
        }
        else if (oppressed.equals("+/-") && !last.equals("empty") && isNum(last)){ //change the initial sign in the current value (if its - put + and vcvs)
            changeSign();
            changeText(currentOperation);
        }
        else if (oppressed.equals("AC")){ // clean the current string of operations
            currentOperation ="";
            changeText(currentOperation);
        }
        else if (oppressed.equals("=")){ // get the result of the operations
            currentOperation = String.valueOf(Calculator.evalResult(currentOperation.replace("√", "sqrt")));
            changeText(currentOperation);
        }
    }

    private boolean sureDiv(String text,String oper) { // sure div between zero
        return !(text.equals("0") && oper.equals("÷"));
    }

    private void changeSign() { // to change the sign of +/- operator
        StringBuilder temp =  new StringBuilder(currentOperation);
        if (temp.charAt(0)=='+'){
            temp.replace(0, 1, "-");
        }
        else if (temp.charAt(0)=='-'){
            temp.replace(0, 1, "+");
        }
        else if (currentOperation.length()>0){
            temp.insert(0,"+");
        }
        currentOperation = temp.toString();
    }

    // Identify the tipe of the element inserted

    private boolean isTrigoOrSq(String text) { return text.equals("sin") || text.equals("cos") || text.equals("tan") ||text.equals("√") ;}

    private boolean isNum(String text) {
        return text.matches("[0-9]");
    }

    private boolean isSig(String text) {
        return text.matches("[×+÷-]");
    }

    public void changeText( String text ) {
        textView.setText(text);
    }
}
