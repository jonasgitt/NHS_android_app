package no.nordicsemi.android.nrftoolbox;


import android.content.ClipData;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LiveDataViewModel extends ViewModel {

        // Tracks the score for Team A
        public int scoreTeamA = 0;

        // Tracks the score for Team B
        public int scoreTeamB = 0;

        private  MutableLiveData<String> currentValue = new MutableLiveData<String>();

        public MutableLiveData<String> getCurrentValue() {
                if (currentValue == null) {
                        currentValue = new MutableLiveData<String>();
                }
                return currentValue;
        }
}
