package it.chiarani.diario_diabete.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.models.HourlyList;

public class HourlyItemsAdapter extends RecyclerView.Adapter<HourlyItemsAdapter.ViewHolder> {

    private List<HourlyList> mItems;
    private int mPositionToColor = 4;

    public HourlyItemsAdapter(List<HourlyList> hourlyList) {
        this.mItems = hourlyList;
    }

    @NonNull
    @Override
    public HourlyItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_item, parent, false);

        return new ViewHolder(view);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt;
        RelativeLayout rl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.hourlyItemText);
            rl = itemView.findViewById(R.id.hourlyItemRl);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            mPositionToColor = getAdapterPosition();
            notifyDataSetChanged();
        }
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt.setText(mItems.get(position).getDisplay());

        if(position == mPositionToColor) {
            holder.rl.setBackgroundResource(R.drawable.background_azure_button);
        }
        else {
            holder.rl.setBackgroundResource(R.drawable.background_white_rectangle_button);
        }
    }



    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

}
