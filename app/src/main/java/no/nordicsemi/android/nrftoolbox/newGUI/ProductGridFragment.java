package no.nordicsemi.android.nrftoolbox.newGUI;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import no.nordicsemi.android.nrftoolbox.R;
import no.nordicsemi.android.nrftoolbox.network.ProductEntry;

import static no.nordicsemi.android.nrftoolbox.newGUI.sensorData.initSensorDataArray;
import static no.nordicsemi.android.nrftoolbox.newGUI.sensorData.initSensorDataList;

public class ProductGridFragment extends Fragment {

    public List<sensorData> sensorDataList = initSensorDataList();
    private sensorData[] BLEdata = initSensorDataArray(); //TODO make this device agnostic (by changing to arraylist?)


    private ProductCardRecyclerViewAdapter mAdapter;

    protected LiveDataViewModel mViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);


        /** View MODEL stuff
         *
         */
        mViewModel = ViewModelProviders.of(getActivity()).get(LiveDataViewModel.class);


        // Create the observer which updates the UI.
        final Observer<List<sensorData>> nameObserver = new Observer<List<sensorData>>() {
            @Override
            public void onChanged(@Nullable final List<sensorData> newValue) {
                sensorDataList = newValue;
                //TODO update using DiffUtil
                Log.w("logObject","---------------in fragment----------------------");
                for (int i  = 0;  i < newValue.size(); i++){
                    BLEdata[i] = newValue.get(i);
                    newValue.get(i).logObject();
                }
                Log.w("logObject","---------------in adapter----------------------");
                if (mAdapter != null)
                    mAdapter.notifyDataSetChanged();
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
       mViewModel.getCurrentValue().observe(getActivity(), nameObserver);
    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment with the ProductGrid theme
        View view = inflater.inflate(R.layout.shr_product_grid_fragment, container, false);



        // Set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        //set up adapter
        mAdapter = new ProductCardRecyclerViewAdapter(BLEdata);
        recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));

        return view;
    }

//    private void setUpToolbar(View view) {
//        Toolbar toolbar = view.findViewById(R.id.app_bar);
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        if (activity != null) {
//            activity.setSupportActionBar(toolbar);
//        }
//    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
//        menuInflater.inflate(R.menu.settings_and_about, menu);
//        super.onCreateOptionsMenu(menu, menuInflater);
//    }
//
}