package com.example.tablemode;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class motiondetect extends Service implements SensorEventListener
{
    Float xaccel,yaccel,zaccel;
    Float xprevaccel,yprevaccel,zprevaccel;
    Boolean firstupdate=true;
    Float shakethreshold = 12.5f;
    Boolean ShakeInitiated;
    Sensor accleometer;
    SensorManager sm;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sm=(SensorManager) getSystemService(SENSOR_SERVICE);
        accleometer= sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this,accleometer,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
updateaccelparamter(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
if((!ShakeInitiated) && isaccelchanged())
{
    ShakeInitiated=true;


}
else if ((ShakeInitiated) && isaccelchanged())
{
    executeaction();

}
else if((ShakeInitiated) && !isaccelchanged())
{
ShakeInitiated=false;
}


    }
    private Boolean isaccelchanged()
    {
        Float deltaX=Math.abs(xprevaccel-xaccel);
        Float deltaY=Math.abs(yprevaccel-yaccel);
        Float deltaZ=Math.abs(zprevaccel-zaccel);

        return (deltaX>shakethreshold && deltaY>shakethreshold)

                ||(deltaX>shakethreshold && deltaZ>shakethreshold)||
                (deltaZ>shakethreshold && deltaY>shakethreshold);
    }
    private void executeaction()
    {
        Intent ii =new Intent(this,Secondactivity.class);
        ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ii);
    }
    private void updateaccelparamter(Float xnewaceel , Float ynewaccel, Float znewaccel)
    {
       if (firstupdate)
       {
           xprevaccel=xnewaceel;
           yprevaccel=ynewaccel;
           zprevaccel=znewaccel;
           firstupdate=false;

       }
       else
       {
           xprevaccel=xaccel;
           yprevaccel=yaccel;
           zprevaccel=zaccel;

       }
       xaccel=xnewaceel;
       yaccel=ynewaccel;
       zaccel=znewaccel;

    }
}
