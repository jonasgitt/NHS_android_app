package no.nordicsemi.android.nrftoolbox.newGUI;

import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import no.nordicsemi.android.nrftoolbox.R;

/**
 * Adapter used to show a simple grid of products.
 */
public class ProductCardRecyclerViewAdapter extends RecyclerView.Adapter<ProductCardViewHolder> {

    private int mExpandedPosition = -1;

    private List<sensorData> sensorList;
    private sensorData[] test_data;

    ProductCardRecyclerViewAdapter(List<sensorData> sensorList, sensorData[] data) {
        this.sensorList = sensorList;
        this.test_data = data;
    }

    @NonNull
    @Override
    public ProductCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shr_product_card, parent, false);

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
                }
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
        return sensorList.size();
    }
}
