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

import java.util.Date;
import java.util.List;

import no.nordicsemi.android.nrftoolbox.R;

/**
 * Adapter used to show a simple grid of products.
 */
public class ProductCardRecyclerViewAdapter extends RecyclerView.Adapter<ProductCardViewHolder> {

    private int mExpandedPosition = -1;

    private List<sensorData> sensorList;
    private sensorData[] test_data;

    private DataPoint[] initPt = {new DataPoint(new Date(), 50)};
    private PointsGraphSeries<DataPoint> mSeries2;

//    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
//        new DataPoint(0, 1),
//        new DataPoint(1, 5),
//        new DataPoint(2, 3),
//        new DataPoint(3, 2),
//        new DataPoint(4, 6)});


    ProductCardRecyclerViewAdapter(List<sensorData> sensorList, sensorData[] data) {
        this.sensorList = sensorList;
        this.test_data = data;
    }

    @NonNull
    @Override
    public ProductCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shr_product_card, parent, false);

        mSeries2 = new PointsGraphSeries<>(initPt);

        return new ProductCardViewHolder(layoutView);
    }

    //The below code tells our RecyclerView's adapter what to do with each card, using a ViewHolder.
    @Override
    public void onBindViewHolder(@NonNull ProductCardViewHolder holder, int position) {


        if (sensorList != null && position < sensorList.size()) {

            sensorData data = sensorList.get(position);
            holder.sensor_Name.setText(data.sensorName);
            holder.sensorImage.setImageResource(data.imageId);
            holder.sensor_units_view.setText(data.units);
            //holder.sensor_Reading.setText(data.sensorReading);

            if(test_data !=null){
                sensorData reading = test_data[position];
                if (reading != null){
                    reading.logObject();
                    holder.sensor_Reading.setText(reading.sensorReading);
                    holder.sensorImage.setImageResource(reading.imageId);
                    holder.sensor_units_view.setText(reading.units);


                    Date curDate = new Date();
                    Double val = Double.parseDouble(reading.sensorReading);
                    mSeries2.appendData(new DataPoint(curDate, val), true, 40);
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

        //Handle Graphing
        holder.graph.addSeries(mSeries2);
        //holder.graph.getViewport().setXAxisBoundsManual(true);
      //  holder.graph.getViewport().setMinX(0);
       // holder.graph.getViewport().setMaxX(40);



    }


    @Override
    public int getItemCount() {
        return sensorList.size();
    }
}
