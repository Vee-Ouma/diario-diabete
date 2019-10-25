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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.db.persistence.entities.DiabeteReadingEntity;
import it.chiarani.diario_diabete.fragments.ReadingDetailFragment;

public class ReadingsAdapter extends RecyclerView.Adapter<ReadingsAdapter.ViewHolder> {

    private List<DiabeteReadingEntity> mItems;
    private final ReadingItemClickListener mOnClickListener;
    private Context mContext;

    public ReadingsAdapter(List<DiabeteReadingEntity> tagsList, ReadingItemClickListener mOnClickListener) {
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtValue = itemView.findViewById(R.id.readingItemReadingValue);
            txtDescription = itemView.findViewById(R.id.readingItemDescr);
            txtDateTime = itemView.findViewById(R.id.readingItemDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onItemClick(getAdapterPosition());
        }
    }


    @Override
    public void onBindViewHolder(@NonNull ReadingsAdapter.ViewHolder holder, int position) {
        holder.txtValue.setText(String.format("%s", mItems.get(position).getValue()));
        holder.txtDateTime.setText(String.format("%s", mItems.get(position).getReadingDate().substring(0,5)));
        holder.txtDescription.setText("N");


        if(mItems.get(position).getValue() > 100 && mItems.get(position).getValue() < 125) {
           holder.txtDescription.setBackground(mContext.getResources().getDrawable(R.drawable.background_orange_button, mContext.getTheme()));
           holder.txtDescription.setText("P");

        }
        else if (mItems.get(position).getValue() >= 125) {
            holder.txtDescription.setBackground(mContext.getResources().getDrawable(R.drawable.background_orange_button, mContext.getTheme()));
            holder.txtDescription.setText("D");
        }
    }


    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

}
