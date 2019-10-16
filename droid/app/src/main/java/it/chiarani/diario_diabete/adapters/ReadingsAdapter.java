package it.chiarani.diario_diabete.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.db.persistence.entities.DiabeteReadingEntity;

public class ReadingsAdapter extends RecyclerView.Adapter<ReadingsAdapter.ViewHolder> {

    private List<DiabeteReadingEntity> mItems;
    private final ListItemClickListener mOnClickListener;
    private Context mContext;

    public ReadingsAdapter(List<DiabeteReadingEntity> tagsList, ListItemClickListener mOnClickListener) {
        this.mItems = tagsList;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public ReadingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reading_item, parent, false);
        this.mContext = parent.getContext();
        return new ReadingsAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtValue, txtDescription, txtDateTime;
        RecyclerView rvTags;
        ImageView imgTextDraw;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtValue = itemView.findViewById(R.id.readingItemTxt);
            rvTags = itemView.findViewById(R.id.rvTest);
            imgTextDraw = itemView.findViewById(R.id.readingItemImgGlucometer);
            txtDescription = itemView.findViewById(R.id.readingItemDescr);
            txtDateTime = itemView.findViewById(R.id.readingItemTxtDateTime);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

        }
    }


    @Override
    public void onBindViewHolder(@NonNull ReadingsAdapter.ViewHolder holder, int position) {
        holder.txtValue.setText(String.format("%s", mItems.get(position).getValue()));

        TextDrawable drawable;
        if(mItems.get(position).getValue() > 100 && mItems.get(position).getValue() < 125) {
            drawable = TextDrawable.builder()
                    .buildRound("PRE", Color.rgb(248, 206, 94));
        }
        else if (mItems.get(position).getValue() >= 125) {
            drawable = TextDrawable.builder()
                    .buildRound("DIA", Color.rgb(207, 92, 78));
        }
        else {
            drawable = TextDrawable.builder()
                    .buildRound("OK", Color.rgb(72, 157, 99));
        }

        if(!mItems.get(position).isEaten2h()) {
            holder.txtDescription.setText(mContext.getString(R.string.readings_adapter_eatenby2h));
        }
        else {
            holder.txtDescription.setText(mContext.getString(R.string.readings_adapter_fasting));
        }

        holder.txtDateTime.setText(String.format("%s\n%s", mItems.get(position).getReadingHour(), mItems.get(position).getReadingDate()));


        holder.imgTextDraw.setImageDrawable(drawable);

        LinearLayoutManager linearLayoutManagerTags = new LinearLayoutManager(mContext);
        linearLayoutManagerTags.setOrientation(RecyclerView.HORIZONTAL);


        TagsAdapter adapterTags = new TagsAdapter(mItems.get(position).getTags(), null);

        holder.rvTags.setAdapter(adapterTags);
        holder.rvTags.setLayoutManager(linearLayoutManagerTags);
    }


    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

}
