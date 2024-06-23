package com.example.les26;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button openSecondActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textView = findViewById(R.id.text_view);
        openSecondActivityButton = findViewById(R.id.open_second_activity);

        openSecondActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    String input = "";
    String operator = "";
    double result = 0;
    boolean isNewOperation = true;

    public void onNumberClick(View view) {
        String textButton = ((MaterialButton) view).getText().toString();

        switch (textButton) {
            case "AC":
                input = "";
                operator = "";
                result = 0;
                isNewOperation = true;
                textView.setText("0");
                openSecondActivityButton.setVisibility(View.GONE);
                break;
            case "+/-":
                if (!input.isEmpty() && !input.equals("0")) {
                    if (input.startsWith("-")) {
                        input = input.substring(1);
                    } else {
                        input = "-" + input;
                    }
                    textView.setText(input);
                }
                break;
            case "%":
                if (!input.isEmpty()) {
                    double value = Double.parseDouble(input) / 100;
                    input = String.valueOf(value);
                    textView.setText(input);
                }
                break;
            case ".":
                if (!input.contains(".")) {
                    input += ".";
                    textView.setText(input);
                }
                break;
            case "+":
            case "-":
            case "×":
            case "÷":
                if (!input.isEmpty()) {
                    if (!operator.isEmpty()) {
                        result = calculate(result, Double.parseDouble(input), operator);
                    } else {
                        result = Double.parseDouble(input);
                    }
                    operator = textButton;
                    input = "";
                    textView.setText(String.valueOf(result));
                    isNewOperation = true;
                }
                break;
            case "=":
                if (!input.isEmpty() && !operator.isEmpty()) {
                    result = calculate(result, Double.parseDouble(input), operator);
                    operator = "";
                    input = String.valueOf(result);
                    textView.setText(input);
                    isNewOperation = true;
                    openSecondActivityButton.setVisibility(View.VISIBLE);
                }
                break;
            default:
                if (isNewOperation) {
                    input = textButton;
                    isNewOperation = false;
                } else {
                    input += textButton;
                }
                textView.setText(input);
                break;
        }
    }

    private double calculate(double firstNumber, double secondNumber, String operator) {
        switch (operator) {
            case "+":
                return firstNumber + secondNumber;
            case "-":
                return firstNumber - secondNumber;
            case "×":
                return firstNumber * secondNumber;
            case "÷":
                if (secondNumber != 0) {
                    return firstNumber / secondNumber;
                } else {
                    return 0;
                }
            default:
                return 0;
        }
    }
}
