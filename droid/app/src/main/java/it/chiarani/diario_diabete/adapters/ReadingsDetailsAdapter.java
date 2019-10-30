package it.chiarani.diario_diabete.adapters;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
    long DURATION = 300;
    private boolean on_attach = true;

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
            txtDate = itemView.findViewById(R.id.readingDetailItemHour);
            txtTime = itemView.findViewById(R.id.readingDetailItemTime);
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
        holder.txtTime.setText(String.format("%s", mItems.get(position).getReadingHour()));
       // FromLeftToRight(holder.itemView, position);
        setAnimation(holder.itemView, position);

    }

    // https://medium.com/better-programming/android-recyclerview-with-beautiful-animations-5e9b34dbb0fa
    private void setAnimation(View itemView, int i) {
        if(!on_attach){
            i = -1;
        }
        boolean isNotFirstItem = i == -1;
        i++;
        itemView.setAlpha(0.f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animator = ObjectAnimator.ofFloat(itemView, "alpha", 0.f, 0.5f, 1.0f);
        ObjectAnimator.ofFloat(itemView, "alpha", 0.f).start();
        animator.setStartDelay(isNotFirstItem ? DURATION / 2 : (i * DURATION / 3));
        animator.setDuration(500);
        animatorSet.play(animator);
        animator.start();
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

}
