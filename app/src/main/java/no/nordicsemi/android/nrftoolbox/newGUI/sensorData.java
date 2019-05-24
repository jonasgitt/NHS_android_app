package no.nordicsemi.android.nrftoolbox.newGUI;

class sensorData {

    String sensorReading;
    String sensorName;

    sensorData(String name, String value){
        this.sensorReading = value;
        this.sensorName = name;
    }
}
