package it.chiarani.diario_diabete.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.db.persistence.entities.TagsEntity;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder> {

    private List<TagsEntity> mItems;
    private ArrayList<Integer> mPositionToColor = new ArrayList<>();

    public TagsAdapter(List<TagsEntity> hourlyList) {
        this.mItems = hourlyList;
    }

    @NonNull
    @Override
    public TagsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tags_item, parent, false);

        return new TagsAdapter.ViewHolder(view);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt;
        RelativeLayout rl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.tagsItemText);
            rl = itemView.findViewById(R.id.tagsItemRl);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(mPositionToColor.contains(getAdapterPosition())) {
                int index = mPositionToColor.indexOf(getAdapterPosition());
                mPositionToColor.remove(index);
            } else {
                mPositionToColor.add(getAdapterPosition());
            }
            notifyDataSetChanged();
        }
    }


    @Override
    public void onBindViewHolder(@NonNull TagsAdapter.ViewHolder holder, int position) {
        holder.txt.setText(mItems.get(position).getValue());

        if(mPositionToColor.contains(position)) {
            holder.rl.setBackgroundResource(R.drawable.background_azure_button);
            holder.txt.setTextColor(holder.itemView.getResources().getColor(R.color.colorWhite));
        }
        else {
            holder.rl.setBackgroundResource(R.drawable.background_white_rectangle_button);
            holder.txt.setTextColor(holder.itemView.getResources().getColor(R.color.colorLightGray));
        }
    }



    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

}
