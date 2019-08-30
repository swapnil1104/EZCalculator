package swapnil.calcii.legacyioscalculator.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import swapnil.calcii.legacyioscalculator.R;
import swapnil.calcii.legacyioscalculator.analytics.Analytics;
import swapnil.calcii.legacyioscalculator.exception.CalculationException;

import swapnil.calcii.legacyioscalculator.logic.Calc;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText screen;
    TextView oldExp;

    boolean isInfinite = false;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        initializeViews();
        setEventForViews();

//        calInfo();
    }

    private void setEventForViews() {
        findViewById(R.id.one).setOnClickListener(this);
        findViewById(R.id.two).setOnClickListener(this);
        findViewById(R.id.three).setOnClickListener(this);
        findViewById(R.id.four).setOnClickListener(this);
        findViewById(R.id.five).setOnClickListener(this);
        findViewById(R.id.six).setOnClickListener(this);
        findViewById(R.id.seven).setOnClickListener(this);
        findViewById(R.id.eight).setOnClickListener(this);
        findViewById(R.id.nine).setOnClickListener(this);
        findViewById(R.id.zero).setOnClickListener(this);
        findViewById(R.id.decimal).setOnClickListener(this);
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.divide).setOnClickListener(this);
        findViewById(R.id.subtract).setOnClickListener(this);
        findViewById(R.id.multiply).setOnClickListener(this);
        findViewById(R.id.openPa).setOnClickListener(this);
        findViewById(R.id.closePa).setOnClickListener(this);
        findViewById(R.id.btnClear).setOnClickListener(this);
        findViewById(R.id.calculate).setOnClickListener(this);
        findViewById(R.id.del).setOnClickListener(this);

    }

    private void initializeViews() {
        screen = findViewById(R.id.field);
        oldExp = findViewById(R.id.oldExp);
    }

    private void setMessage(String title, String body) {
        oldExp.setText(body);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                replaceSymbol("+");
                break;

            case R.id.divide:
                replaceSymbol("/");
                break;

            case R.id.multiply:
                replaceSymbol("*");
                break;

            case R.id.subtract:
                replaceSymbol("-");
                break;

            case R.id.btnClear:
                oldExp.setText("");
                screen.setText("");
                break;

            case R.id.openPa:
                replaceSymbol("(");
                break;

            case R.id.closePa:
                replaceSymbol(")");
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
                replaceSymbol(".");
                break;

            case R.id.del:
                String sb;
                try {
                    sb = screen.getText().toString();
                    sb = sb.substring(0, sb.length() - 1);
                    screen.setText(sb);
                } catch (Exception ignored) {
                }
                break;

            case R.id.calculate:
                Calc evaluator = new Calc();
                try {
                    String expr = screen.getText().toString();
                    double result = evaluator.eval(expr);
                    oldExp.setText(expr);

                    Analytics.logAnalytics(mFirebaseAnalytics, Analytics.Event.CALCULATE, new Bundle());

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
                            throw new CalculationException("Infix Error", "That ain't right");
                        case 2:
                            throw new CalculationException("Stack Error", "For some reason, the stack did not fully empty itself");
                        case 4:
                            throw new CalculationException("Parenthesis Error", "The braces?");
                        case 404:
                            throw new CalculationException("Input Error", "...");
                        case 403:
                            throw new CalculationException("Division by 0", "Dividing by zero is a crime");

                    }
                } catch (CalculationException e) {
                    Bundle b = new Bundle();
                    b.putString(e.getErr(), e.getDesc());
                    Analytics.logAnalytics(mFirebaseAnalytics, Analytics.Event.ERROR, b);
                    setMessage(e.getErr(), e.getDesc());
                } catch (Calc.SyntaxErrorException e) {
                    e.printStackTrace();
                }
                break;


        }
    }

    @SuppressLint("SetTextI18n")
    private void replaceSymbol(String symbol) {
        String curText = screen.getText().toString();
        Pattern pattern = Pattern.compile("[+-/*.]");
        if (curText.length() > 0) {
            if (symbol.equals("(")) {
                screen.setText(curText + symbol);
            } else if (pattern.matcher(curText.substring(curText.length() - 1)).matches()) {
                screen.setText(curText.substring(0, curText.length() - 1) + symbol);
            } else {
                screen.setText(curText + symbol);
            }
        } else if (symbol.equals("(")) screen.setText(symbol);
        else if (symbol.equals(".")) screen.setText("0" + symbol);

    }
}
