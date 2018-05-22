package com.example.hiraokatatsuru.dentaku2java;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static TextView formulaLabel;
    //static boolean isOperation;
    boolean isOperation = false;
    boolean isInput = false;
    boolean isEqual = false;
    List<Double> inputNums = new ArrayList<>();
    List<String> operations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        formulaLabel = (TextView) findViewById(R.id.textView1);
    }

    public void acButtonPush(View view) {
        formulaLabel.setText("0");
        isOperation = false;
        isInput = false;
        isEqual = false;
        inputNums.clear();
        operations.clear();
    }

    public void numbersButtonInput(View view) {
        Button btn = (Button) findViewById(view.getId());
        //textView1.setText(btn.getText());
        //System.out.println(btn.getText());
        String text1 = (String) formulaLabel.getText().toString();
        String text2 = (String) btn.getText().toString();
        if (text1.startsWith("0.") || text1.startsWith("-0.")) {
            String sconcat = text1.concat(text2);
            formulaLabel.setText(sconcat);
        } else if (text1.startsWith("0")) {
            formulaLabel.setText(text2);
        } else if (text1.startsWith("-0")) {
            String str = "-";
            String sconcat = str.concat(text2);
            formulaLabel.setText(sconcat);
        } else {
            if (isOperation || isEqual){//演算子を選んだ直後
                text1 = "";
                isOperation = false;
                isEqual = false;
            }
            String sconcat = text1.concat(text2);
            formulaLabel.setText(sconcat);
        }
        isInput = true;
    }

    public void dotButtonInput(View view) {
        String text1 = formulaLabel.getText().toString();
        String dot = ".";
        if (!text1.contains(".")) {
            String sconcat = text1.concat(dot);
            formulaLabel.setText(sconcat);
        }
    }

    public void plusOrMinusButtonChange(View view) {
        String text1 = formulaLabel.getText().toString();
        if (text1.startsWith("-")) {
            formulaLabel.setText(text1.substring(1));
        } else {
            String str = "-";
            String sconcat = str.concat(text1);
            formulaLabel.setText(sconcat);
        }
    }

    public void operationButtonSelect(View view) {
        double result = Double.parseDouble((String) formulaLabel.getText());
        Button btn = (Button) findViewById(view.getId());
        String text1 = (String) formulaLabel.getText().toString();
        String text2 = (String) btn.getText().toString();
        if (isEqual || isInput == false) {
            //operationsの最後の要素をsender.titleLabel?.text!に入れ替える
            operations.set(operations.size() - 1, text2);
            isEqual = false;
            isOperation = true;
            System.out.println("演算子訂正");
        } else if (isInput) {//数字が入力された直後
            isInput = false;
            isOperation = true;
            //選択した演算子のボタンのタイトルラベル文字列が入る
            operations.add(text2);
            //現在、表示されている数字をDoubleにして１番目として保存
            inputNums.add(result);
            System.out.println("数字が入力された直後");
        }
        result = caluculate(operations, inputNums);
        //String str = String.valueOf(result);
        String str = (new DecimalFormat().format(result));//String.format("%g",result);
        //System.out.println(new DecimalFormat().format(1.2));
        formulaLabel.setText(str);
    }

    private static double caluculate(List<String> operation, List<Double> inputNum) {
        //static TextView formulaLabel;にしないとダメだった。
        double returnVal = Double.parseDouble((String) formulaLabel.getText());

        if (operation.size() == 2) {
            if ((operation.get(0) == "+" || operation.get(0) == "-") && (operation.get(1) == "×" || operation.get(1) == "÷")) {
                System.out.println("guard");
                //return returnVal;
            }else {
                returnVal = calc4Ways(inputNum.get(0), inputNum.get(1), operation.get(0));
                System.out.println("guard2");
            }
            //print("operation.count == 2")
        } else if (operation.size() == 3) {
            returnVal = calc4Ways2(inputNum.get(0), inputNum.get(1), inputNum.get(2), operation.get(0), operation.get(1));
            //print("operation.count == 3")
        }
        return returnVal;
    }

    private static double calc4Ways(double num1, double num2, String opr) {
        System.out.println("calc4Ways");
        double returnVal = 0;
        //String operation = opr;
        System.out.println(opr);
        switch (opr) {
            case "+":
                returnVal = num1 + num2;
                break;
            case "-":
                returnVal = num1 - num2;
                break;
            case "×":
                returnVal = num1 * num2;
                break;
            case "÷":
                returnVal = num1 / num2;
                break;
            default:
                break;
        }
        return returnVal;
    }

    private  static double calc4Ways2(double num1, double num2, double num3, String opr1, String opr2){
        System.out.println("calc4Ways2");
        double returnVal = 0;
        String operation = opr1 + opr2;
        switch (operation) {
            case "+×":
                returnVal = num1 + num2 * num3;
                break;
            case "-×":
                returnVal = num1 - num2 * num3;
                break;
            case "+÷":
                returnVal = num1 + num2 / num3;
                break;
            case "-÷":
                returnVal = num1 - num2 / num3;
                break;
            default:
                break;
        }
        return returnVal;
    }
}
