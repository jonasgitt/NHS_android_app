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

import static no.nordicsemi.android.nrftoolbox.newGUI.sensorData.initSensorDataList;

public class ProductGridFragment extends Fragment {

    public List<sensorData> sensorDataList = initSensorDataList();
    private sensorData[] BLEdata = new sensorData[6]; //TODO make this device agnostic (by changing to arraylist?)

    /***/
    private DataPoint[] initPt = {makeDataPoint("1")};
    private PointsGraphSeries<DataPoint> singleSeries = new PointsGraphSeries<>(initPt);
    private List<PointsGraphSeries<DataPoint>>  dataSeriesList = Arrays.asList(singleSeries, singleSeries, singleSeries, singleSeries, singleSeries, singleSeries);

    private int counter = 0;
    private DataPoint makeDataPoint(String newReading){
        Date curDate = new Date();
        counter++;
        //if (counter == 40) counter =0;
        Double val = Double.parseDouble(newReading);
        DataPoint newDataPoint = new DataPoint(counter, val) ;
        return newDataPoint;
    }
     /***/

    private ProductCardRecyclerViewAdapter mAdapter;

    protected LiveDataViewModel mViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        /** View MODEL stuff
         *
         */
        mViewModel = ViewModelProviders.of(getActivity()).get(LiveDataViewModel.class);


        // Create the observer which updates the UI.
        final Observer<List<sensorData>> nameObserver = new Observer<List<sensorData>>() {
            @Override
            public void onChanged(@Nullable final List<sensorData> newValue) {
                sensorDataList = newValue;
               // Log.w("jonas", "hast the value changed? ____________________________________: " + sensorDataList.get(1).sensorReading);
                //with sensorData array
                //TODO update using DiffUtil
                for (int i  = 0;  i < newValue.size(); i++){
                    BLEdata[i] = newValue.get(i);

                    //dataSeriesList.get(i).appendData(makeDataPoint(newValue.get(i).sensorReading),true, 40 );
                }
                singleSeries.appendData(makeDataPoint(newValue.get(0).sensorReading),true,40);
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

        // Set up the toolbar
        setUpToolbar(view);

        // Set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //set up adapter
        mAdapter = new ProductCardRecyclerViewAdapter(sensorDataList, BLEdata, singleSeries);
        recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));

        return view;
    }

    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.settings_and_about, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

}