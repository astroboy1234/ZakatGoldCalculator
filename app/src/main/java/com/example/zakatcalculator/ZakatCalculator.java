package com.example.zakatcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class ZakatCalculator extends AppCompatActivity  implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    EditText gweight, gvalue;
    Button btncalc, btnreset;
    TextView tvOutput1, tvOutput2, tvOutput3;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    float goweight;
    float govalue;


    SharedPreferences sharedPref1;
    SharedPreferences sharedPref2;
    private Menu menu;


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakat_calculator);

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        gweight = (EditText) findViewById(R.id.goldWeight);
        gvalue = (EditText) findViewById(R.id.goldValue);
        btncalc = (Button) findViewById(R.id.CalcBTN);
        tvOutput1 = (TextView) findViewById(R.id.totalGold);
        tvOutput2 = (TextView) findViewById(R.id.zakatPaymnt);
        tvOutput3 = (TextView) findViewById(R.id.totalZakat);
        btnreset = (Button) findViewById(R.id.ResetBTN);

        btncalc.setOnClickListener(this);
        btnreset.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

        sharedPref1 = this.getSharedPreferences("weight", Context.MODE_PRIVATE);
        goweight = sharedPref1.getFloat("weight", 0.0F);

        sharedPref2 = this.getSharedPreferences("value", Context.MODE_PRIVATE);
        govalue = sharedPref2.getFloat("value", 0.0F);

        gweight.setText(""+goweight);
        gvalue.setText(""+govalue);

    }



    @Override
    public void onClick(View v) {

        try {
            switch (v.getId()) {

                case R.id.CalcBTN:
                    calc();
                    break;

                case R.id.ResetBTN:
                    gweight.setText("");
                    gvalue.setText("");
                    tvOutput1.setText("");
                    tvOutput2.setText("");
                    tvOutput3.setText("");

                    break;

            }
        } catch (java.lang.NumberFormatException nfe) {
            Toast.makeText(this, "Please enter value to proceed the calculation", Toast.LENGTH_SHORT).show();

        } catch (Exception exp) {
            Toast.makeText(this,"Unknown Exception" + exp.getMessage(),Toast.LENGTH_SHORT).show();

            Log.d("Exception",exp.getMessage());

        }
    }

    public void calc(){
        DecimalFormat df = new DecimalFormat("##.00");
        float gw = Float.parseFloat(gweight.getText().toString());
        float gv = Float.parseFloat(gvalue.getText().toString());
        String stat = spinner.getSelectedItem().toString();
        double tgv;
        double uruf;
        double zp;
        double tz;
        SharedPreferences.Editor editor = sharedPref1.edit();
        editor.putFloat("weight", gw);
        editor.apply();
        SharedPreferences.Editor editor2 = sharedPref2.edit();
        editor2.putFloat("value", gv);
        editor2.apply();


        if (stat.equals("Keep")){
            tgv= gw * gv;
            uruf= gw - 85;

            if(uruf>=0.0) {
                zp = uruf * gv;
                tz =  zp * 0.025;
            }

            else{
                zp = 0.0;
                tz =  zp * 0.025;

            }

            tvOutput1.setText("Total value of the gold:\n RM"+ df.format(tgv));
            tvOutput2.setText("Zakat payable:\n RM"+ df.format(zp));
            tvOutput3.setText("Total Zakat:\n RM"+ df.format(tz));
        }

        else{
            tgv= gw * gv;
            uruf= gw - 200;

            if(uruf>=0.0) {
                zp = uruf * gv;
                tz =  zp * 0.025;
            }

            else{
                zp = 0.0;
                tz =  zp * 0.025;

            }

            tvOutput1.setText("Total value of the gold:\n RM"+ df.format(tgv));
            tvOutput2.setText("Zakat payable:\n RM"+ df.format(zp));
            tvOutput3.setText("Total Zakat:\n RM"+ df.format(tz));

        }

    }

}