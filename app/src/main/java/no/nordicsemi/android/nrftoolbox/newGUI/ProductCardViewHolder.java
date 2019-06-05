package no.nordicsemi.android.nrftoolbox.newGUI;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.jjoe64.graphview.GraphView;

import no.nordicsemi.android.nrftoolbox.R;



public class ProductCardViewHolder extends RecyclerView.ViewHolder {


    Context context;

    public ImageView sensorImage;
    public TextView sensor_Name;
    public TextView sensor_Reading;
    public TextView sensor_units_view;
    public LinearLayout expandedArea;
    public TextView status_indicator;

    public GraphView graph;

    public ProductCardViewHolder(@NonNull View itemView) {
        super(itemView);
        //TODO: Find and store views from itemView
        sensorImage = itemView.findViewById(R.id.sensorImage_field);
        sensor_Name = itemView.findViewById(R.id.sensorName_field);
        sensor_Reading = itemView.findViewById(R.id.sensorValue_field);
        sensor_units_view = itemView.findViewById(R.id.units_field);

        expandedArea = itemView.findViewById(R.id.expanded_area);

        graph = itemView.findViewById(R.id.graph); //is red because the expanded section is #included

        status_indicator = itemView.findViewById(R.id.health_status_indicator);

        context = itemView.getContext();
    }

    public void setIndicatorColor(String sensorName, String sensorValue){

        status_indicator.setVisibility(View.VISIBLE);

        if (context!=null) {
            int value = Integer.parseInt(sensorValue);
            switch (sensorName) {
                case "Heart Rate":
                    if (value > 150 || value < 50)
                        status_indicator.setTextColor(ContextCompat.getColor(context, R.color.status_unhealthy_color));
                    else
                        status_indicator.setTextColor(ContextCompat.getColor(context, R.color.status_healthy_color));
                    break;
                case "Temperature":
                    if (value > 36 || value < 20)
                        status_indicator.setTextColor(ContextCompat.getColor(context, R.color.status_unhealthy_color));
                    else
                        status_indicator.setTextColor(ContextCompat.getColor(context, R.color.status_healthy_color));
                    break;
                case "Blood Oxygen":
                    if (value > 100 || value < 80)
                        status_indicator.setTextColor(ContextCompat.getColor(context, R.color.status_unhealthy_color));
                    else
                        status_indicator.setTextColor(ContextCompat.getColor(context, R.color.status_healthy_color));
                    break;
                case "Step Count":
                    if (value < 100)
                        status_indicator.setTextColor(ContextCompat.getColor(context, R.color.status_unhealthy_color));
                    else
                        status_indicator.setTextColor(ContextCompat.getColor(context, R.color.status_healthy_color));
                    break;
                case "Battery Level":
                    if (value < 30)
                        status_indicator.setTextColor(ContextCompat.getColor(context, R.color.status_unhealthy_color));
                    else
                        status_indicator.setTextColor(ContextCompat.getColor(context, R.color.status_healthy_color));
                    break;
                default:
                    break;
            }
        }
       // else
            //status_indicator.setVisibility(View.INVISIBLE);
    }
}
