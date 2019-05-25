package no.nordicsemi.android.nrftoolbox.newGUI;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import no.nordicsemi.android.nrftoolbox.R;

class sensorData {
    public static final int TEMPERATURE = 0;
    public static final int BATTERY = 1;
    public static final int HEARTRATE = 2;



    String sensorReading;
    String sensorName;
    int imageId;

    sensorData(String name, String value){
        this.sensorReading = value;
        this.sensorName = name;
        this.imageId = getDrawableId();
    }

    public static List<sensorData> initSensorDataList(){
        List<sensorData> sensorDataList = new ArrayList<>();
        sensorData data1 = new sensorData("Heart Rate", "00");
        sensorData data2 = new sensorData("Battery Level", "00");//TODO fix this
        sensorData data3 = new sensorData("Temperature", "00");
        sensorDataList.add(data1);
        sensorDataList.add(data2);
        sensorDataList.add(data3);
        return sensorDataList;
    }

    private int getDrawableId() {
        String resourceName = "icon_notfound";
        try {
            switch (this.sensorName){
                case "Temperature": resourceName = "icon_temperature";
                case "Battery Level": resourceName = "icon_notfound";
                case "Heart Rate": resourceName = "icon_heart_rate";
                default: resourceName = "icon_notfound";
            }
            Field idField = R.drawable.class.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / ", e);
        }
    }
}
