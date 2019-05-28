package no.nordicsemi.android.nrftoolbox.newGUI;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import no.nordicsemi.android.nrftoolbox.R;



public class ProductCardViewHolder extends RecyclerView.ViewHolder {

    public ImageView sensorImage;
    public TextView sensor_Name;
    public TextView sensor_Reading;
    public TextView sensor_units_view;

    public ProductCardViewHolder(@NonNull View itemView) {
        super(itemView);
        //TODO: Find and store views from itemView
        sensorImage = itemView.findViewById(R.id.product_image);
        sensor_Name = itemView.findViewById(R.id.product_title);
        sensor_Reading = itemView.findViewById(R.id.product_price);
        sensor_units_view = itemView.findViewById(R.id.units_field);
    }
}
