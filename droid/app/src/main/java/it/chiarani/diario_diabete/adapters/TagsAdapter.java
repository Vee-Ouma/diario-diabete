package it.chiarani.diario_diabete.adapters;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.db.persistence.entities.TagsEntity;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder> {

    private List<TagsEntity> mItems;
    private List<Integer> mPositionToColor = new ArrayList<>();
    private final ListItemClickListener mOnClickListener;

    public TagsAdapter(List<TagsEntity> tagsList, ListItemClickListener mOnClickListener) {
        this.mItems = tagsList;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public TagsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tags_item, parent, false);

        return new TagsAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtDescription;
        RelativeLayout rl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDescription = itemView.findViewById(R.id.tagsItemText);
            rl = itemView.findViewById(R.id.tagsItemRl);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(mOnClickListener == null) {
                return;
            }

            mOnClickListener.onItemClick(getAdapterPosition());

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
        holder.txtDescription.setText(mItems.get(position).getValue());

        if(mPositionToColor.contains(position)) {
            holder.rl.setBackgroundResource(R.drawable.background_azure_black_button);
            TypedValue typedValue = new TypedValue();
            holder.itemView.getContext().getTheme().resolveAttribute(R.attr.appColorWhite, typedValue, true);
            int color = typedValue.data;
            holder.txtDescription.setTextColor(color);
        }
        else {
            holder.rl.setBackgroundResource(R.drawable.background_white_rectangle_button);

            TypedValue typedValue = new TypedValue();
            holder.itemView.getContext().getTheme().resolveAttribute(R.attr.appColorGray, typedValue, true);
            int color = typedValue.data;
            holder.txtDescription.setTextColor(color);

        }
    }



    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

}
