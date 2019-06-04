package no.nordicsemi.android.nrftoolbox.newGUI;

import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import no.nordicsemi.android.nrftoolbox.R;

/**
 * Adapter used to show a simple grid of products.
 */
public class ProductCardRecyclerViewAdapter extends RecyclerView.Adapter<ProductCardViewHolder> {

    private int mExpandedPosition = -1;

    private List<sensorData> sensorList;
    private sensorData[] test_data;
    //private List<PointsGraphSeries<DataPoint>> dataSeriesList = new ArrayList<>();

//    private DataPoint[] initPt = {makeDataPoint("1")};
//    private PointsGraphSeries<DataPoint> singleSeries = new PointsGraphSeries<>(initPt);

    private List<PointsGraphSeries<DataPoint>>  dataSeriesList; //= Arrays.asList(singleSeries, singleSeries, singleSeries, singleSeries, singleSeries, singleSeries);


    ProductCardRecyclerViewAdapter(List<sensorData> sensorList, sensorData[] data, List<PointsGraphSeries<DataPoint>> seriesList) {
        this.sensorList = sensorList;
        this.test_data = data;

        this.dataSeriesList = seriesList;
    }

    @NonNull
    @Override
    public ProductCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shr_product_card, parent, false);

        //dataSeriesList.add(singleSeries);

        return new ProductCardViewHolder(layoutView);
    }


    private DataPoint[] dPtArray = generateData();
    private List<DataPoint[]> dPtArrayList  = Arrays.asList(dPtArray,dPtArray,dPtArray,dPtArray,dPtArray,dPtArray);

    //The below code tells our RecyclerView's adapter what to do with each card, using a ViewHolder.
    @Override
    public void onBindViewHolder(@NonNull ProductCardViewHolder holder, int position) {



        if (sensorList != null && position < sensorList.size()  ) {

            sensorData data = sensorList.get(position);
            holder.sensor_Name.setText(data.sensorName);
            holder.sensorImage.setImageResource(data.imageId);
            holder.sensor_units_view.setText(data.units);

            if(test_data !=null){
                sensorData reading = test_data[position];
                if (reading != null){
                    reading.logObject();
                    holder.sensor_Reading.setText(reading.sensorReading);

                    if (position == 5) {
                        counter++;
//                        if (counter == 10) {
//                            counter = 0;
//                        }
                    }


                    PointsGraphSeries<DataPoint> singleSeries = dataSeriesList.get(position);

                    if (!singleSeries.isEmpty() && holder.graph.getSeries().size() == 0){
                        holder.graph.addSeries(singleSeries);
                    }
                    //dataSeriesList.set(position,singleSeries);

                    holder.graph.getViewport().setXAxisBoundsManual(true);
                    holder.graph.getViewport().setMinX(0);
                    holder.graph.getViewport().setMaxX(100);

                    Log.w("graph", "position: " + position+ "  # of Series on Graph: " + holder.graph.getSeries().size() +"   newReading: " + reading.sensorName);
                    //Log.w("jonas", "Size of Series: " + s1.getValues(0, counter));

                }
            }
        }

        //Handles Expansion
        final boolean isExpanded = position==mExpandedPosition;
        holder.expandedArea.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);

        if (isExpanded)
            mExpandedPosition = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                notifyItemChanged(mExpandedPosition);
                notifyItemChanged(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return sensorList.size();
    }

    private int counter = 0;
    private DataPoint makeDataPoint(String newReading){
        Date curDate = new Date();
        Double val = Double.parseDouble(newReading);
        DataPoint newDataPoint = new DataPoint(counter, val) ;
        return newDataPoint;
    }



    private DataPoint[] generateData() {
        int count = 10;
        DataPoint[] values = new DataPoint[count];
        for (int i=0; i<count; i++) {
            double x = i;
            double y = 0;
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }
}


