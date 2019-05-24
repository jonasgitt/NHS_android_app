package no.nordicsemi.android.nrftoolbox.newGUI;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;



public class LiveDataViewModel extends ViewModel {

        // Tracks the score for Team A
        public int scoreTeamA = 0;

        // Tracks the score for Team B
        public int scoreTeamB = 0;

        private  MutableLiveData<List<sensorData>> currentValue = new MutableLiveData<List<sensorData>>();

        public MutableLiveData<List<sensorData>> getCurrentValue() {
                if (currentValue == null) {
                        currentValue = new MutableLiveData<List<sensorData>>();
                }
                return currentValue;
        }
}
