package no.nordicsemi.android.nrftoolbox.newGUI;

import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.jjoe64.graphview.GridLabelRenderer;
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
    private int counter = 0;

    private sensorData[] test_data;
    LineGraphSeries<DataPoint> series0 = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> series5 = new LineGraphSeries<>();

    ProductCardRecyclerViewAdapter(sensorData[] data) {
        this.test_data = data;
    }

    @NonNull
    @Override
    public ProductCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shr_product_card, parent, false);

        ProductCardViewHolder holder = new ProductCardViewHolder(layoutView);

        holder.graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        holder.graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        holder.graph.getGridLabelRenderer().setPadding(40);

        holder.graph.getViewport().setXAxisBoundsManual(true);
        holder.graph.getViewport().setMinX(0);
        holder.graph.getViewport().setMaxX(40);

        int lineColor = ContextCompat.getColor(parent.getContext(),R.color.graphColor);

        if (counter ==0)
            initializeSensorData();

        series0.setColor(lineColor);
        series2.setColor(lineColor);
        series5.setColor(lineColor);
        series0.setThickness(12);
        series2.setThickness(12);
        series5.setThickness(12);

        holder.graph.addSeries(series0);

        return holder;
    }





    //The below code tells our RecyclerView's adapter what to do with each card, using a ViewHolder.
    @Override
    public void onBindViewHolder(@NonNull ProductCardViewHolder holder, int position) {


        if(test_data !=null && position < test_data.length){
            sensorData reading = test_data[position];
            holder.sensor_Name.setText(reading.sensorName);
            holder.sensorImage.setImageResource(reading.imageId);
            holder.sensor_units_view.setText(reading.units);


            if (!reading.sensorReading.equals("-")){
                reading.logObject();
                holder.sensor_Reading.setText(reading.sensorReading);

                holder.setIndicatorColor(reading.sensorName, reading.sensorReading);

                if (position == 5) {
                    counter++;
                }

                switch (position) {
                    case 0:
                        series0.appendData(makeDataPoint(reading.sensorReading), true, 40, true);
                        holder.graph.removeAllSeries();
                        holder.graph.addSeries(series0);
                        holder.graph.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        holder.graph.removeAllSeries();
                        holder.graph.setVisibility(View.GONE);
                        break;
                    case 2:
                        // series2 = dataSeriesList.get(2);
                        series2.appendData(makeDataPoint(reading.sensorReading), true, 40, true);
                        holder.graph.removeAllSeries();
                        holder.graph.addSeries(series2);
                        break;
                    case 3:
                        holder.graph.removeAllSeries();
                        break;
                    case 4:
                        holder.graph.removeAllSeries();
                        break;
                    case 5:
                        series5.appendData(makeDataPoint(reading.sensorReading), true, 40, true);
                        holder.graph.removeAllSeries();
                        holder.graph.addSeries(series5);
                        break;
                    default:
                        break;
                }

                holder.graph.getViewport().scrollToEnd();
                Log.w("graph", "position: " + position + "  # of Series on Graph: " + holder.graph.getSeries().size() + "   newReading: " + reading.sensorName);
            }

        }

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
        return test_data.length;
    }


    private DataPoint makeDataPoint(String newReading) {
        Date curDate = new Date();
        Double val = Double.parseDouble(newReading);
        DataPoint newDataPoint = new DataPoint(counter, val);
        return newDataPoint;
    }

    Random rand = new Random();
    double start = 50;
    private void initializeSensorData(){
        for (int i = 0; i < 10;i++){
            start = start +  0.8*rand.nextInt(10);
            Log.w("init","start: " + start);
            series0.appendData(intDataPoint(start),true, 40, false);
        }
        for (int i = 0; i < 10;i++){
            start = start - 0.5*rand.nextInt(10);
            series0.appendData(intDataPoint(start),true, 40, false);
        }
        for (int i = 0; i < 10;i++){
            start = start + 1*rand.nextInt(10);
            series0.appendData(intDataPoint(start),true, 40, false);
        }
        for (int i = 0; i < 10;i++){
            start = start -  0.4*rand.nextInt(10);
            series0.appendData(intDataPoint(start),true, 40, false);
        }

    }

    private DataPoint intDataPoint(double newReading) {
        Double val = (double) newReading;
        counter++;
        DataPoint newDataPoint = new DataPoint(counter, val);
        return newDataPoint;
    }
}
