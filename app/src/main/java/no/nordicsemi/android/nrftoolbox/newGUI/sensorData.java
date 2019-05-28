package no.nordicsemi.android.nrftoolbox.newGUI;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import no.nordicsemi.android.nrftoolbox.R;

class sensorData {

    String sensorReading;
    String sensorName;
    int imageId;

    sensorData(String name, String value){
        this.sensorReading = value;
        this.sensorName = name;
        this.imageId = getDrawableId();
    }

    public static sensorData[]  initSensorDataArray(){
//        List<sensorData> sensorDataList = new ArrayList<>();
        sensorData[] BLEdata = new sensorData[3];
        sensorData data0 = new sensorData("Heart Rate", "00");
        sensorData data1= new sensorData("Battery Level", "00");//TODO fix this
        sensorData data2 = new sensorData("Temperature", "00");
        BLEdata[0] = data0;
        BLEdata[1] = data1;
        BLEdata[2] = data2;

        return BLEdata;
    }

    public static List<sensorData> initSensorDataList(){
        List<sensorData> sensorDataList = new ArrayList<>();
        sensorData data1 = new sensorData("Heart Rate", "00");
        sensorData data2 = new sensorData("Battery Level", "00");
        sensorData data3 = new sensorData("Temperature", "00");
        sensorDataList.add(data1);
        sensorDataList.add(data2);
        sensorDataList.add(data3);
        return sensorDataList;
    }

    private int getDrawableId() {
        String resourceName = "0";
        try {
            switch (sensorName){
                case "Temperature": resourceName = "icon_temperature"; break;
                case "Battery Level": resourceName = "icon_notfound"; break;
                case "Heart Rate": resourceName = "icon_heart_rate"; break;
                default: resourceName = "icon_notfound";
            }
            Field idField = R.drawable.class.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            Log.w("jonas", "resource not found: " + resourceName);
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / ", e);
        }
    }

    public void logObject(){
        Log.w("jonas", "sensorName: " + sensorName + "     sensorReading: " + sensorReading + "     imageId: " + imageId);
    }
}
