package no.nordicsemi.android.nrftoolbox.newGUI.p24callbacks;

public interface p24CharacteristicCallback {
    void onStepCountReceived(final int stepCount);
    void onBloodOxReceived(final int bloodOxValue);
}
