package com.arr.rentacar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<Car> mCars = new ArrayList<>(6);
    ArrayList<String> mMakes = new ArrayList<>();
    ArrayList<String> mModels = new ArrayList<>();

    Car mCar;
    double mDailyRate,mInsurance,mExtras;;

    Spinner mSpinnerMake,mSpinnerModel;
    ImageView mImageCar;
    TextView mTextViewDailyRate, mTextViewCurrentMillage,mTextViewRentDays, mTextViewTotalAmount;
    SeekBar mSeekBarDays;
    RadioGroup mRadioGroup;
    RadioButton mRadioButton1,mRadioButton2,mRadioButton3;
    CheckBox mCheckBox1,mCheckBox2,mCheckBox3;
    Button mButton1, mButton2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSpinnerMake = findViewById(R.id.spinner1);
        mSpinnerModel = findViewById(R.id.spinner2);
        mImageCar = findViewById(R.id.image1);
        mTextViewDailyRate = findViewById(R.id.txtDailyRate);
        mTextViewCurrentMillage = findViewById(R.id.txtCurrentMillage);
        mTextViewRentDays = findViewById(R.id.txtRentDays);
        mSeekBarDays = findViewById(R.id.seekBarRentDays);
        mRadioGroup = findViewById(R.id.radioGroup);
        mRadioButton1 = findViewById(R.id.radio21);
        mRadioButton2 = findViewById(R.id.radio64);
        mRadioButton3 = findViewById(R.id.radio65);
        mCheckBox1 = findViewById(R.id.checkNavigator);
        mCheckBox2 = findViewById(R.id.checkChildSeat);
        mCheckBox3 = findViewById(R.id.checkBikeRake);
        mTextViewTotalAmount = findViewById(R.id.txtTotalAmount);
        mButton1 = findViewById(R.id.btnGetTotal);
        mButton2 = findViewById(R.id.btnRentCar);


        fillDate();

        mTextViewRentDays.setText(String.valueOf(mSeekBarDays.getProgress()));

        for(Car car:mCars)
            mMakes.add(car.getMake());

        mMakes = new ArrayList<String>(new LinkedHashSet<String>(mMakes));

        ArrayAdapter makeAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,mMakes);
        mSpinnerMake.setAdapter(makeAdapter);

        mSpinnerMake.setOnItemSelectedListener(this);
        mSpinnerModel.setOnItemSelectedListener(this);

        mSeekBarDays.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mTextViewRentDays.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mRadioButton1.setOnClickListener(new RadioButtonEvents());
        mRadioButton2.setOnClickListener(new RadioButtonEvents());
        mRadioButton3.setOnClickListener(new RadioButtonEvents());

        mCheckBox1.setOnCheckedChangeListener(new CheckBoxEvents());
        mCheckBox2.setOnCheckedChangeListener(new CheckBoxEvents());
        mCheckBox3.setOnCheckedChangeListener(new CheckBoxEvents());

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int daysSelected = Integer.parseInt(mTextViewRentDays.getText().toString());
                double totalAmount = (mDailyRate+mInsurance+mExtras)*daysSelected;
                totalAmount += totalAmount*1.13;
                mTextViewTotalAmount.setText(String.format("%.2f",totalAmount));
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Rent Application Generated",Toast.LENGTH_LONG).show();
                clear();
            }
        });


    }

    public void clear(){
        mSpinnerMake.setSelection(0);
        mSpinnerModel.setSelection(0);
        mRadioGroup.clearCheck();
        mCheckBox1.setChecked(false);
        mCheckBox2.setChecked(false);
        mCheckBox3.setChecked(false);
        mSeekBarDays.setProgress(1);
        mTextViewTotalAmount.setText("");
    }


    public void fillDate(){
        mCars.add(new Car("Maruthi Suziki","Swift",100.0,123.5));
        mCars.add(new Car("Maruthi Suziki","Vitara Breeza",200.0,123.5));
        mCars.add(new Car("Maruthi Suziki","Ertiga",150.0,123.5));
        mCars.add(new Car("Hyundai","i10",100.0,123.5));
        mCars.add(new Car("Hyundai","i20",150.0,123.5));
        mCars.add(new Car("Hyundai","Verna",200.0,123.5));
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.spinner1:
                String makeSelected = mMakes.get(i);
                mModels.clear();
                for(Car car:mCars)
                    if(car.getMake().equalsIgnoreCase(makeSelected))
                        mModels.add(car.getModel());
                ArrayAdapter modelAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,mModels);
                mSpinnerModel.setAdapter(modelAdapter);
             break;
            case R.id.spinner2:
                String modelSelected = mModels.get(i).toLowerCase();
                for(Car car:mCars)
                    if(car.getModel().equalsIgnoreCase(modelSelected)){
                        mCar = car;
                        break;
                    }
                mDailyRate = mCar.getDailyRate();
                mTextViewDailyRate.setText(String.format("%.2f",mCar.getDailyRate()));
                mTextViewCurrentMillage.setText(String.format("%.2f",mCar.getCurrentMillage()));
                int res = getResources().getIdentifier(modelSelected, "drawable", this.getPackageName());
                mImageCar.setImageResource(res);
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private class RadioButtonEvents implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            double amount = 0;
            switch (view.getId()){
                case R.id.radio21:
                    amount += 30.0;
                break;
                case R.id.radio64:
                    amount += 17.0;
                break;
                case R.id.radio65:
                    amount += 22;
                break;
                default:
                    throw new IllegalStateException("Unexpected value: " + view.getId());
            }
            mInsurance = amount;
        }
    }


    private class CheckBoxEvents implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            double amount = mExtras;
            switch (compoundButton.getId()){
                case R.id.checkNavigator:
                    if(compoundButton.isChecked())
                        amount += 5;
                    else
                        amount -= 5;
                break;
                case R.id.checkChildSeat:
                    if(compoundButton.isChecked())
                        amount += 7;
                    else
                        amount -= 7;
                break;
                case R.id.checkBikeRake:
                    if(compoundButton.isChecked())
                        amount += 10;
                    else
                        amount -= 10;
                break;
                default:
                    throw new IllegalStateException("Unexpected value: " + compoundButton.getId());
            }
            mExtras = amount;
        }
    }
}