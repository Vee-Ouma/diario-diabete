package it.chiarani.diario_diabete.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.db.persistence.entities.DiabeteReadingEntity;
import it.chiarani.diario_diabete.db.persistence.entities.TagsEntity;

public class ReadingsAdapter extends RecyclerView.Adapter<ReadingsAdapter.ViewHolder> {

    private List<DiabeteReadingEntity> mItems;
    private final ListItemClickListener mOnClickListener;
    private Context mContext;

    public ReadingsAdapter(List<DiabeteReadingEntity> tagsList, ListItemClickListener mOnClickListener, Context ctx) {
        this.mContext = ctx;
        this.mItems = tagsList;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public ReadingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reading_item, parent, false);
        //mContext = parent.getContext();
        return new ReadingsAdapter.ViewHolder(view);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt, descr;
        ConstraintLayout rl;
        RecyclerView rv;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.readingItemTxt);
          //  rl = itemView.findViewById(R.id.readingItemRl);
            rv = itemView.findViewById(R.id.rvTest);
            img = itemView.findViewById(R.id.readingItemImgGlucometer);
            descr = itemView.findViewById(R.id.readingItemDescr);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

        }
    }


    @Override
    public void onBindViewHolder(@NonNull ReadingsAdapter.ViewHolder holder, int position) {
        holder.txt.setText(mItems.get(position).getValue() + "");

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

        if(mItems.get(position).isEaten2h()) {
            holder.descr.setText("Mangiato da 2h");
        }
        else {
            holder.descr.setText("Digiuno");
        }


        holder.img.setImageDrawable(drawable);

        LinearLayoutManager linearLayoutManagerTags = new LinearLayoutManager(mContext);
        linearLayoutManagerTags.setOrientation(RecyclerView.HORIZONTAL);


        TagsAdapter adapterTags = new TagsAdapter(mItems.get(position).getTags(), null);

        holder.rv.setAdapter(adapterTags);
        holder.rv.setLayoutManager(linearLayoutManagerTags);
    }


    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

}
