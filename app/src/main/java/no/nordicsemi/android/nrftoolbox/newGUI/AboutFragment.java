package no.nordicsemi.android.nrftoolbox.newGUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import mehdi.sakout.aboutpage.AboutPage;
import no.nordicsemi.android.nrftoolbox.R;

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_about, container, false);
        // Inflate the layout for this fragment
        View aboutPage = new AboutPage(getActivity())
                .isRTL(false)
//                .addItem(versionElement)
//                .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("jf1116@imperial.ac.uk")
                .addGitHub("ledangaravi/remote-patient-monitoring")
                .create();

        viewGroup.addView(aboutPage);

        return viewGroup;
    }

}
