package it.chiarani.diario_diabete.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.db.persistence.entities.DiabeteReadingEntity;

public class ReadingsDetailsAdapter extends RecyclerView.Adapter<ReadingsDetailsAdapter.ViewHolder> {

    private List<DiabeteReadingEntity> mItems;
    private final ReadingItemClickListener mOnClickListener;
    private Context mContext;

    public ReadingsDetailsAdapter(List<DiabeteReadingEntity> tagsList, ReadingItemClickListener mOnClickListener) {
        this.mItems = tagsList;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public ReadingsDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reading_detail_item, parent, false);
        this.mContext = parent.getContext();
        return new ReadingsDetailsAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtValue, txtDescription, txtFasting, txtDate, txtTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtValue = itemView.findViewById(R.id.readingDetailItemValue);
            txtDescription = itemView.findViewById(R.id.readingDetailItemDescription);
            txtDate = itemView.findViewById(R.id.readingDetailItemHour);
            txtTime = itemView.findViewById(R.id.readingDetailItemTime);
            txtFasting = itemView.findViewById(R.id.readingDetailItemFasting);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(mOnClickListener == null) {
                return;
            }

            mOnClickListener.onItemClick(getAdapterPosition());
        }
    }


    @Override
    public void onBindViewHolder(@NonNull ReadingsDetailsAdapter.ViewHolder holder, int position) {
        holder.txtValue.setText(String.format("%s", mItems.get(position).getValue()));
        holder.txtDate.setText(String.format("%s", mItems.get(position).getReadingDate().substring(0,5)));
        holder.txtDescription.setText("Normale");

        holder.txtFasting.setText("Digiuno");
        if(mItems.get(position).isEaten2h()) {
            holder.txtFasting.setText("Mangiato da 2h");
        }


        if(mItems.get(position).getValue() > 100 && mItems.get(position).getValue() < 125) {
           holder.txtDescription.setBackground(mContext.getResources().getDrawable(R.drawable.background_orange_button));
           holder.txtDescription.setText("Pre-Diabete");

        }
        else if (mItems.get(position).getValue() >= 125) {
            holder.txtDescription.setBackground(mContext.getResources().getDrawable(R.drawable.background_orange_button));
            holder.txtDescription.setText("Diabete");
        }

    }


    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

}
