package com.example.calcon;

import androidx.appcompat.app.AppCompatActivity;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView workingTv, resultTv;
    MaterialButton btn_clear, btn_backspace, btn_allClear;
    MaterialButton btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_dot;
    MaterialButton btn_divide, btn_multiply, btn_add, btn_subtract, btn_equal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        workingTv = findViewById(R.id.workingTv);
        resultTv = findViewById(R.id.resultTv);

        assignId(btn_clear, R.id.btn_clear);
        assignId(btn_backspace, R.id.btn_backspace);
        assignId(btn_allClear, R.id.btn_allclear);
        assignId(btn_0, R.id.btn_0);
        assignId(btn_1, R.id.btn_1);
        assignId(btn_2, R.id.btn_2);
        assignId(btn_3, R.id.btn_3);
        assignId(btn_4, R.id.btn_4);
        assignId(btn_5, R.id.btn_5);
        assignId(btn_6, R.id.btn_6);
        assignId(btn_7, R.id.btn_7);
        assignId(btn_8, R.id.btn_8);
        assignId(btn_9, R.id.btn_9);
        assignId(btn_dot, R.id.btn_dot);
        assignId(btn_divide, R.id.btn_divide);
        assignId(btn_multiply, R.id.btn_multiply);
        assignId(btn_add, R.id.btn_add);
        assignId(btn_subtract, R.id.btn_subtract);
        assignId(btn_equal, R.id.btn_equal);
    }

    public void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();

//        if(!buttonText.equals("back_space")) {
//            workingTv.setText(buttonText);
//        }
        String workingText = workingTv.getText().toString();
//        Log.e("is clicked: ", buttonText + " is clicked");

        if(buttonText.equals("AC") || buttonText.equals("C")) {
            workingTv.setText("");
            resultTv.setText("0");
            return;
        }

        if(buttonText.equals("=")) {
            workingTv.setText(resultTv.getText().toString());
            return;
        }
        if(buttonText.equals("back_space")) {
            if(workingText.length() > 1) {
                workingText = workingText.substring(0, workingText.length() - 1);
            } else {
                return;
            }
        } else {
            workingText = workingText + buttonText;
        }

        workingTv.setText(workingText);

        String finalResult = getFinalResult(workingText);

        if(!finalResult.equals("Err")) {
            resultTv.setText(finalResult);
        }
    }
    String getFinalResult(String expression) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult =
                    context.evaluateString(scriptable, expression, "Javascript", 1, null).toString();
            if(finalResult.endsWith(".0")) {
                //finalResult.replace(".0", "");
                finalResult = finalResult.substring(0, finalResult.length() - 2);
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }
    }
}