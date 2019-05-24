package no.nordicsemi.android.nrftoolbox.newGUI;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import no.nordicsemi.android.nrftoolbox.R;



public class ProductCardViewHolder extends RecyclerView.ViewHolder {

    public ImageView sensorImage;
    public TextView sensorName;
    public TextView sensorReading;

    public ProductCardViewHolder(@NonNull View itemView) {
        super(itemView);
        //TODO: Find and store views from itemView
        sensorImage = itemView.findViewById(R.id.product_image);
        sensorName = itemView.findViewById(R.id.product_title);
        sensorReading = itemView.findViewById(R.id.product_price);
    }
}
