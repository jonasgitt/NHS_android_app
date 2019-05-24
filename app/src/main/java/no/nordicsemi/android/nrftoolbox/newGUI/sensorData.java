package no.nordicsemi.android.nrftoolbox.newGUI;

import java.util.ArrayList;
import java.util.List;

class sensorData {

    String sensorReading;
    String sensorName;

    sensorData(String name, String value){
        this.sensorReading = value;
        this.sensorName = name;
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
}
