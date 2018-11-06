package com.broooapps.legacyioscalculator.activities;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.broooapps.legacyioscalculator.R;
import com.broooapps.legacyioscalculator.exception.CalculationException;

import logic.Calc;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText screen;
    TextView oldExp;

    boolean isInfinite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = findViewById(R.id.field);
        oldExp = findViewById(R.id.oldExp);


//        calInfo();
    }

    private void calInfo() {
        AlertDialog.Builder info = new AlertDialog.Builder(this);
        info.setIcon(android.R.drawable.ic_dialog_alert);
        info.setTitle("Calcii: infixExp");
        info.setMessage("This infix calculator will only accept non negative, single digit numbers");
        info.show();

    }

    private void setMessage(String title, String body) {
        oldExp.setText(body);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                screen.append("+");
                break;

            case R.id.divide:
                screen.append("/");
                break;

            case R.id.multiply:
                screen.append("*");
                break;

            case R.id.subtract:
                screen.append("-");
                break;

            case R.id.btnClear:
                oldExp.setText("");
                screen.setText("");
                break;

            case R.id.openPa:
                screen.append("(");
                break;

            case R.id.closePa:
                screen.append(")");
                break;

            case R.id.one:
                screen.append("1");
                break;

            case R.id.two:
                screen.append("2");
                break;

            case R.id.three:
                screen.append("3");
                break;

            case R.id.four:
                screen.append("4");
                break;

            case R.id.five:
                screen.append("5");
                break;

            case R.id.six:
                screen.append("6");
                break;

            case R.id.seven:
                screen.append("7");
                break;

            case R.id.eight:
                screen.append("8");
                break;

            case R.id.nine:
                screen.append("9");
                break;

            case R.id.zero:
                screen.append("0");
                break;

            case R.id.decimal:
                screen.append(".");
                break;

            case R.id.del:
                String sb;
                try {
                    sb = screen.getText().toString();
                    sb = sb.substring(0, sb.length() - 1);
                    screen.setText(sb);
                } catch (Exception e) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.calculate:
                Calc evaluator = new Calc();
                try {
                    String expr = screen.getText().toString();
                    double result = evaluator.eval(expr);
                    oldExp.setText(expr);
                    switch (evaluator.checkError()) {
                        case 0:
                            screen.setText(Double.toString(result));
                            if (result == Math.round(result)) {
                                screen.setText(Math.round(result) + "");
                            }
                            if (Double.isInfinite(result)) {
                                isInfinite = true;
                            }
                            break;
                        case 1:
                            throw new CalculationException("Infix Error", "You did not input a proper infix expression.");
                        case 2:
                            throw new CalculationException("Stack Error", "For some reason, the stack did not fully empty itself.");
                        case 4:
                            throw new CalculationException("Parenthesis Error", "The braces?");
                        case 404:
                            throw new CalculationException("Input Error", "");
                        case 403:
                            throw new CalculationException("Division by 0", "Dividing by zero is a crime");
                    }
                } catch (CalculationException e) {
                    setMessage(e.getErr(), e.getDesc());
                } catch (Calc.SyntaxErrorException e) {
                    e.printStackTrace();
                }
                break;


        }
    }
}
