package com.salavatdautov.hexcalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static {
        System.loadLibrary("native-lib");
    }

    String[] numberSystemBases = {"BIN", "OCT", "DEC", "HEX"};

    TextView log;
    TextView numberField;
    Spinner numberSystemBaseSpinner;
    Button num0;
    Button num1;
    Button num2;
    Button num3;
    Button num4;
    Button num5;
    Button num6;
    Button num7;
    Button num8;
    Button num9;
    Button a;
    Button b;
    Button c;
    Button d;
    Button e;
    Button f;
    Button backspace;
    Button clear;
    Button plus;
    Button minus;
    Button multiply;
    Button divide;
    Button equal;

    String operand1 = "0";
    String operand2 = "0";
    char operation = '+';
    int numberSystemBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        log = findViewById(R.id.log);
        numberField = findViewById(R.id.number_field);
        num0 = findViewById(R.id.num_0);
        num1 = findViewById(R.id.num_1);
        num2 = findViewById(R.id.num_2);
        num3 = findViewById(R.id.num_3);
        num4 = findViewById(R.id.num_4);
        num5 = findViewById(R.id.num_5);
        num6 = findViewById(R.id.num_6);
        num7 = findViewById(R.id.num_7);
        num8 = findViewById(R.id.num_8);
        num9 = findViewById(R.id.num_9);
        a = findViewById(R.id.a);
        b = findViewById(R.id.b);
        c = findViewById(R.id.c);
        d = findViewById(R.id.d);
        e = findViewById(R.id.e);
        f = findViewById(R.id.f);
        backspace = findViewById(R.id.backspace);
        clear = findViewById(R.id.clear);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        multiply = findViewById(R.id.multiply);
        divide = findViewById(R.id.divide);
        equal = findViewById(R.id.equal);

        numberSystemBaseSpinner = findViewById(R.id.number_system_base);
        ArrayAdapter<String> numberSystemBaseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, numberSystemBases);
        numberSystemBaseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberSystemBaseSpinner.setAdapter(numberSystemBaseAdapter);
        numberSystemBaseSpinner.setSelection(2);
        numberSystemBaseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                log.setText("0");
                numberField.setText("0");
                switch (numberSystemBases[position]) {
                    case "BIN":
                        num2.setEnabled(false);
                        num3.setEnabled(false);
                        num4.setEnabled(false);
                        num5.setEnabled(false);
                        num6.setEnabled(false);
                        num7.setEnabled(false);
                        num8.setEnabled(false);
                        num9.setEnabled(false);
                        a.setEnabled(false);
                        b.setEnabled(false);
                        c.setEnabled(false);
                        d.setEnabled(false);
                        e.setEnabled(false);
                        f.setEnabled(false);
                        break;
                    case "OCT":
                        num2.setEnabled(true);
                        num3.setEnabled(true);
                        num4.setEnabled(true);
                        num5.setEnabled(true);
                        num6.setEnabled(true);
                        num7.setEnabled(true);
                        num8.setEnabled(false);
                        num9.setEnabled(false);
                        a.setEnabled(false);
                        b.setEnabled(false);
                        c.setEnabled(false);
                        d.setEnabled(false);
                        e.setEnabled(false);
                        f.setEnabled(false);
                        break;
                    case "DEC":
                        num2.setEnabled(true);
                        num3.setEnabled(true);
                        num4.setEnabled(true);
                        num5.setEnabled(true);
                        num6.setEnabled(true);
                        num7.setEnabled(true);
                        num8.setEnabled(true);
                        num9.setEnabled(true);
                        a.setEnabled(false);
                        b.setEnabled(false);
                        c.setEnabled(false);
                        d.setEnabled(false);
                        e.setEnabled(false);
                        f.setEnabled(false);
                        break;
                    case "HEX":
                        num2.setEnabled(true);
                        num3.setEnabled(true);
                        num4.setEnabled(true);
                        num5.setEnabled(true);
                        num6.setEnabled(true);
                        num7.setEnabled(true);
                        num8.setEnabled(true);
                        num9.setEnabled(true);
                        a.setEnabled(true);
                        b.setEnabled(true);
                        c.setEnabled(true);
                        d.setEnabled(true);
                        e.setEnabled(true);
                        f.setEnabled(true);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        d.setOnClickListener(this);
        e.setOnClickListener(this);
        f.setOnClickListener(this);
        backspace.setOnClickListener(this);
        clear.setOnClickListener(this);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        multiply.setOnClickListener(this);
        divide.setOnClickListener(this);
        equal.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_about) {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.num_0:
                if (numberField.getText().equals("0")) {
                    numberField.setText("");
                }
                numberField.setText(numberField.getText() + String.valueOf(0));
                break;
            case R.id.num_1:
                if (numberField.getText().equals("0")) {
                    numberField.setText("");
                }
                numberField.setText(numberField.getText() + String.valueOf(1));
                break;
            case R.id.num_2:
                if (numberField.getText().equals("0")) {
                    numberField.setText("");
                }
                numberField.setText(numberField.getText() + String.valueOf(2));
                break;
            case R.id.num_3:
                if (numberField.getText().equals("0")) {
                    numberField.setText("");
                }
                numberField.setText(numberField.getText() + String.valueOf(3));
                break;
            case R.id.num_4:
                if (numberField.getText().equals("0")) {
                    numberField.setText("");
                }
                numberField.setText(numberField.getText() + String.valueOf(4));
                break;
            case R.id.num_5:
                if (numberField.getText().equals("0")) {
                    numberField.setText("");
                }
                numberField.setText(numberField.getText() + String.valueOf(5));
                break;
            case R.id.num_6:
                if (numberField.getText().equals("0")) {
                    numberField.setText("");
                }
                numberField.setText(numberField.getText() + String.valueOf(6));
                break;
            case R.id.num_7:
                if (numberField.getText().equals("0")) {
                    numberField.setText("");
                }
                numberField.setText(numberField.getText() + String.valueOf(7));
                break;
            case R.id.num_8:
                if (numberField.getText().equals("0")) {
                    numberField.setText("");
                }
                numberField.setText(numberField.getText() + String.valueOf(8));
                break;
            case R.id.num_9:
                if (numberField.getText().equals("0")) {
                    numberField.setText("");
                }
                numberField.setText(numberField.getText() + String.valueOf(9));
                break;
            case R.id.a:
                if (numberField.getText().equals("0")) {
                    numberField.setText("");
                }
                numberField.setText(numberField.getText() + "A");
                break;
            case R.id.b:
                if (numberField.getText().equals("0")) {
                    numberField.setText("");
                }
                numberField.setText(numberField.getText() + "B");
                break;
            case R.id.c:
                if (numberField.getText().equals("0")) {
                    numberField.setText("");
                }
                numberField.setText(numberField.getText() + "C");
                break;
            case R.id.d:
                if (numberField.getText().equals("0")) {
                    numberField.setText("");
                }
                numberField.setText(numberField.getText() + "D");
                break;
            case R.id.e:
                if (numberField.getText().equals("0")) {
                    numberField.setText("");
                }
                numberField.setText(numberField.getText() + "E");
                break;
            case R.id.f:
                if (numberField.getText().equals("0")) {
                    numberField.setText("");
                }
                numberField.setText(numberField.getText() + "F");
                break;
            case R.id.backspace:
                if (!numberField.getText().equals("0") && numberField.length() > 1) {
                    numberField.setText(numberField.getText().subSequence(0, numberField.length() - 1));
                } else if (numberField.length() == 1) {
                    numberField.setText("0");
                }
                break;
            case R.id.clear:
                numberSystemBaseSpinner.setEnabled(true);
                log.setText("0");
                numberField.setText("0");
                operand1 = "0";
                operand2 = "0";
                break;
            case R.id.plus:
                if (!numberField.getText().equals("0")) {
                    log.setText(numberField.getText() + " +");
                    operand1 = numberField.getText().toString();
                    numberField.setText("0");
                    numberSystemBaseSpinner.setEnabled(false);
                    operation = '+';
                }
                break;
            case R.id.minus:
                if (!numberField.getText().equals("0")) {
                    log.setText(numberField.getText() + " -");
                    operand1 = numberField.getText().toString();
                    numberField.setText("0");
                    numberSystemBaseSpinner.setEnabled(false);
                    operation = '-';
                }
                break;
            case R.id.multiply:
                if (!numberField.getText().equals("0")) {
                    log.setText(numberField.getText() + " *");
                    operand1 = numberField.getText().toString();
                    numberField.setText("0");
                    numberSystemBaseSpinner.setEnabled(false);
                    operation = '*';
                }
                break;
            case R.id.divide:
                if (!numberField.getText().equals("0")) {
                    log.setText(numberField.getText() + " /");
                    operand1 = numberField.getText().toString();
                    numberField.setText("0");
                    numberSystemBaseSpinner.setEnabled(false);
                    operation = '/';
                }
                break;
            case R.id.equal:
                operand2 = numberField.getText().toString();
                log.setText("0");
                numberSystemBaseSpinner.setEnabled(true);
                switch (numberSystemBaseSpinner.getSelectedItem().toString()) {
                    case "BIN":
                        numberSystemBase = 2;
                        break;
                    case "OCT":
                        numberSystemBase = 8;
                        break;
                    case "DEC":
                        numberSystemBase = 10;
                        break;
                    case "HEX":
                        numberSystemBase = 16;
                }
                if (numberSystemBase == 10) {
                    int decNumber1;
                    int decNumber2;
                    try {
                        decNumber1 = Integer.parseInt(operand1);
                        decNumber2 = Integer.parseInt(operand2);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        decNumber1 = 0;
                        decNumber2 = 0;
                    }
                    numberField.setText(calculate(decNumber1, decNumber2, operation));
                } else {
                    numberField.setText(calculateNoDec(operand1, operand2, operation, numberSystemBase));
                }
                operand1 = "0";
                operand2 = "0";
        }
    }

    public native String calculate(int operand1, int operand2, char operation);

    public native String calculateNoDec(String operand1, String operand2, char operation, int numberSystemBase);
}
