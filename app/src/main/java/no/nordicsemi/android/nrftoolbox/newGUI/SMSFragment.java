package no.nordicsemi.android.nrftoolbox.newGUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import no.nordicsemi.android.nrftoolbox.R;

public class SMSFragment  extends Fragment {

        public SMSFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.sms_fragment, container, false);

            return rootView;
        }

}
