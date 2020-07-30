package com.example.bottomnavmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class StepsCounter extends AppCompatActivity implements SensorEventListener
{
    private TextView permanentCount;
    private TextView tempStepCount;
    private TextView burntCalories;
    private TextView targetToBurn;
    private CircularProgressBar circularProgressBar;
    private Button resetButton;
    boolean activityRunning;
    private SensorManager sensorManager;

    private boolean firstSensorReading = true;

    private Context context;
    private float targetCalories;
    private int stepCountAtLastReset;

    DecimalFormat df = new DecimalFormat("0.00");

    private final String TARGET_CAL_FILE = "targetCalories.txt";
    private final String LAST_STEP_CNT_FILE = "lastStepCount.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_counter);

        context = this;

        permanentCount =  findViewById(R.id.count);
        tempStepCount =  findViewById(R.id.tempStepCount);
        burntCalories = findViewById(R.id.burntCalories);
        targetToBurn = findViewById(R.id.targetToBurn);

        circularProgressBar = findViewById(R.id.circularProgressBar);
        resetButton = findViewById(R.id.resetButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetSetPopup();
            }
        });


        try {
            initActivity();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);




    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        activityRunning = true;
        Sensor counter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(counter != null)
        {
            sensorManager.registerListener(this ,counter , SensorManager.SENSOR_DELAY_UI);
        }

        else
            Toast.makeText(this ,  "The device doesn't have support to count steps" , Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(activityRunning)
        {
            permanentCount.setText(String.valueOf((int)event.values[0]));
            int steps = (int)event.values[0] - stepCountAtLastReset;
            tempStepCount.setText(String.valueOf(steps));
            burntCalories.setText( df.format( (float)steps/20));

            if(firstSensorReading) {
                firstSensorReading = false;
                animateProgressBar();
            }

            circularProgressBar.setProgress(Math.min((float)steps/20 ,  targetCalories));

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    void animateProgressBar()
    {
      /*  System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("permCount = "+permanentCount.getText().toString());
        System.out.println("last step count = "+ stepCountAtLastReset);
*/
        int steps = Integer.parseInt(permanentCount.getText().toString()) - stepCountAtLastReset;
        circularProgressBar.setProgressWithAnimation((float)steps/20 , 1000L);
    }

    void initProgressBar()
    {
        circularProgressBar.setProgressMax(targetCalories);
        circularProgressBar.setProgress(0);
   }

   void initActivity() throws IOException {
       importStepCountAtLastReset();
       importTargetCalories();

       targetToBurn.setText(String.valueOf(targetCalories));
       int steps = Integer.parseInt(permanentCount.getText().toString()) - stepCountAtLastReset;
       tempStepCount.setText(String.valueOf(steps));
       burntCalories.setText( df.format( (float)steps/20));

       initProgressBar();
   }


    private void targetSetPopup()
    {
        final EditText displayText;
        final Button setButton;

        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.set_target_to_burn);

        displayText = dialog.findViewById(R.id.displayText);

        setButton =  dialog.findViewById(R.id.setButton);




        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String calStr = displayText.getText().toString();

                    if(! calStr.equals("")) {
                        exportStepCountAtLastReset(permanentCount.getText().toString());
                        exportTargetCalories(calStr);

                        tempStepCount.setText("0");

                        initActivity();

                        dialog.dismiss();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        dialog.show();

    }


    void importTargetCalories() throws IOException {
        FileInputStream fis = null;

        try {
            fis = openFileInput(TARGET_CAL_FILE);
            BufferedReader reader = new BufferedReader(new BufferedReader(new InputStreamReader(fis)));
            targetCalories = Float.parseFloat(reader.readLine());

        } catch (Exception e) {


            targetCalories = 0;
            e.printStackTrace();
        }
        finally {
            if(fis != null)
            {
                fis.close();
            }
        }
    }

    void importStepCountAtLastReset() throws IOException {
        FileInputStream fis = null;

        try {
            fis = openFileInput(LAST_STEP_CNT_FILE);
            BufferedReader reader = new BufferedReader(new BufferedReader(new InputStreamReader(fis)));
            stepCountAtLastReset = Integer.parseInt(reader.readLine());

        } catch (Exception e) {

            stepCountAtLastReset = 0;
            e.printStackTrace();
        }
        finally {
            if(fis != null)
            {
                fis.close();
            }
        }
    }

    void exportTargetCalories(String str) throws IOException {
        FileOutputStream fos = null;
        str = str+"\n";

        try {
            fos = openFileOutput(TARGET_CAL_FILE , MODE_PRIVATE);
            fos.write(str.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fos != null)
            {
                fos.close();
            }
        }
    }

    void exportStepCountAtLastReset(String str) throws IOException {
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(LAST_STEP_CNT_FILE , MODE_PRIVATE);
            fos.write(str.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fos != null)
            {
                fos.close();
            }
        }
    }


}
