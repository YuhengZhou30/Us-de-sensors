package com.example.usdesensors;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private SensorEventListener accelerometerListener;

    private long lastClickTime = 0;
    private static final long DOUBLE_CLICK_TIME_DELTA = 300;
    private TextView xAxisTextView;
    private TextView yAxisTextView;
    private TextView zAxisTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xAxisTextView = findViewById(R.id.xPoint);
        yAxisTextView = findViewById(R.id.yPoint);
        zAxisTextView = findViewById(R.id.zPoint);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        accelerometerListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float xAxis = event.values[0];
                float yAxis = event.values[1];
                float zAxis = event.values[2];

                xAxisTextView.setText(xAxis+"");
                yAxisTextView.setText(yAxis+"");
                zAxisTextView.setText(zAxis+"");
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Not used in this example
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(accelerometerListener);
    }



    public void onScreenClicked(View view) {
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {

            showToast("Doble Click!");
        }
        lastClickTime = clickTime;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
