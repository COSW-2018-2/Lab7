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

        if ((isNum(oppressed)) ||
            (isSig(oppressed) && !last.equals("empty") && isNum(last)) ||
            (isTrigoOrSq(oppressed) && (last.equals("empty") || isSig(last)))) {
            changeText(currentOperation+=oppressed);
        }
        else if (oppressed.equals(".") && !last.equals("empty") && isNum(last)){
            changeText(currentOperation+=oppressed);
        }
        else if (oppressed.equals("+/-") && !last.equals("empty") && isNum(last)){
            changeSign();
            changeText(currentOperation);
        }
        else if (oppressed.equals("AC")){
            currentOperation ="";
            changeText(currentOperation);
        }
        else if (oppressed.equals("=")){
            currentOperation = String.valueOf(Calculator.evalResult(currentOperation.replace("√", "sqrt")));
            changeText(currentOperation);
        }
    }

    private void changeSign() {
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

    private boolean isTrigoOrSq(String text) {
        return text.equals("sin") || text.equals("cos") || text.equals("tan") ||text.equals("√") ;
    }

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
