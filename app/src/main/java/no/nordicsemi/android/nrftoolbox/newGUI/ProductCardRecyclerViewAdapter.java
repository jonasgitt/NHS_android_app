package no.nordicsemi.android.nrftoolbox.newGUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.google.codelabs.mdc.java.shrine.network.ProductEntry;

import java.util.List;

import no.nordicsemi.android.nrftoolbox.R;
import no.nordicsemi.android.nrftoolbox.network.ProductEntry;

/**
 * Adapter used to show a simple grid of products.
 */
public class ProductCardRecyclerViewAdapter extends RecyclerView.Adapter<ProductCardViewHolder> {

    private List<ProductEntry> productList;
    //private ImageRequester imageRequester;
    private String[] dataString;

    ProductCardRecyclerViewAdapter(List<ProductEntry> productList, String[] dataString) {
        this.productList = productList;
        //imageRequester = ImageRequester.getInstance();
        this.dataString = dataString;
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
        if (productList != null && position < productList.size()) {
//            ProductEntry product = productList.get(position);
//            holder.productTitle.setText("Blood Pressure");
//            holder.productPrice.setText(product.price);
//           // imageRequester.setImageFromUrl(holder.productImage, product.url);
//
//            holder.productImage.setImageResource(R.drawable.hr_heart);

            holder.productTitle.setText(dataString[position]);
            holder.productPrice.setText("60");
            // imageRequester.setImageFromUrl(holder.productImage, product.url);

            holder.productImage.setImageResource(R.drawable.hr_heart);
    }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
