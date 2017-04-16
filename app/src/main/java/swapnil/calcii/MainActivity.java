package swapnil.calcii;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText screen;
    TextView oldExp;

    boolean    isInfinite = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = (EditText) findViewById(R.id.field);

//        calInfo();
    }

    private void calInfo() {
        AlertDialog.Builder info = new AlertDialog.Builder(this);
        info.setIcon(android.R.drawable.ic_dialog_alert);
        info.setTitle("Calcii: infixExp");
        info.setMessage("This infix calculator will only accept non negative, single digit numbers");
        info.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void checkSolved() {
//        String old = oldExp.getText().toString();
//        if(old.matches("")){
//            return;
//        }else{
//            screen.setText("");
//            return;
//        }
    }

    public void setOne(View view) {
        screen.append("1");
        checkSolved();
    }

    public void setZero(View view) {
        screen.append("0");
        checkSolved();
    }

    public void setFour(View view) {
        checkSolved();
        screen.append("4");
    }

    public void setSeven(View view) {
        screen.append("7");
        checkSolved();
    }

    public void setTwo(View view) {
        screen.append("2");
        checkSolved();
    }

    public void setFive(View view) {
        screen.append("5");
        checkSolved();
    }

    public void setEight(View view) {
        screen.append("8");
        checkSolved();
    }

    public void setThree(View view) {
        checkSolved();
        screen.append("3");
    }

    public void setSix(View view) {
        screen.append("6");
        checkSolved();
    }

    public void setNine(View view) {
        screen.append("9");
        checkSolved();
    }

    public void setOpenPa(View view) {
        screen.append("(");
        checkSolved();
    }

    public void setPlus(View view) {
        screen.append("+");
        checkSolved();
    }

    public void setMinus(View view) {
        screen.append("-");
        checkSolved();
    }

    public void setClosePa(View view) {
        screen.append(")");
        checkSolved();
    }

    public void setStar(View view) {
        screen.append("*");
        checkSolved();
    }

    public void setSlash(View view) {
        screen.append("/");
        checkSolved();
    }


    public void clear(View view) {
        oldExp = (TextView) findViewById(R.id.oldExp);
        oldExp.setText("");
        screen.setText("");

    }

    public void setCalculate(View view) {
        Calc evaluator = new Calc();
        oldExp = (TextView) findViewById(R.id.oldExp);
        try {
            TextView screen = (TextView) findViewById(R.id.field);
            String expr = screen.getText().toString();
            double result = evaluator.eval(expr);
            oldExp.setText(expr);
            switch (evaluator.checkError()) {
                case 0:
                    screen.setText(Double.toString(result));
                    if(result == Math.round(result)) {
                        screen.setText(Math.round(result)+"");
                    }
                    if (Double.isInfinite(result)) {
                        isInfinite = true;
                    }
                    break;
                case 1:
                    messageBox("Infix Error", "You did not input a proper infix expression.");
                    break;
                case 2:
                    messageBox("Stack Error", "For some reason, the stack did not fully empty itself.");
                    break;
                case 3:
                    messageBox("Input Error", "You should only be using operands and operators.");
                    break;
                case 4:
                    messageBox("Parenthesis Error", "You did not properly open and close your parenthesis.");
                    break;
                case 404:
                    messageBox("Input Error", "Invalid Input dude.");
                    break;
                case 403:
                    messageBox("Division by 0", "I'm afraid I can't let you do that.");
                    break;
            }
        } catch (Exception e) {

        }

    }

    private void messageBox(String title, String body) {
        oldExp.setText(body);
//        AlertDialog.Builder msg = new AlertDialog.Builder(this);
//        msg.setTitle(title);
//        msg.setMessage(body);
//        msg.setIcon(android.R.drawable.ic_dialog_alert);
//        msg.show();

    }

    public void delText(View view) {
        String sb = new String();
        try {

            sb = screen.getText().toString();
            sb = sb.substring(0, sb.length() - 1);
            screen.setText(sb);
        } catch (Exception e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

    }

    public void setZeroZero(View view) {
        screen.append("00");
    }

    public void setDecimal(View view) {
        screen.append(".");

    }
}
