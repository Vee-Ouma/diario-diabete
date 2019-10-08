package it.chiarani.diario_diabete.views;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import it.chiarani.diario_diabete.R;
import it.chiarani.diario_diabete.adapters.TagsAdapter;
import it.chiarani.diario_diabete.databinding.ActivityDataReaderBinding;
import it.chiarani.diario_diabete.db.persistence.entities.TagsEntity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class DataReaderActivity extends BaseActivity {

    ActivityDataReaderBinding binding;


    @Override
    protected int getLayoutID() {
        return R.layout.activity_data_reader;
    }

    @Override
    protected void setActivityBinding() {
       binding = DataBindingUtil.setContentView(this, getLayoutID());
    }

    @Override
    protected void setViewModel() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     /*   LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.scrollToPositionWithOffset(3, 0);
        binding.activityDataReaderRVEatingFrom.setLayoutManager(linearLayoutManager);

        List<HourlyList> items = new ArrayList<>();
        items.add(new HourlyList(1, "0h"));
        items.add(new HourlyList(2, "0h 30m"));
        items.add(new HourlyList(3, "1h"));
        items.add(new HourlyList(4, "1h 30m"));
        items.add(new HourlyList(5, "2h"));
        items.add(new HourlyList(6, "2h 30m"));
        items.add(new HourlyList(7, "3h"));
        items.add(new HourlyList(8, "3h 30m"));
        HourlyItemsAdapter adapter = new HourlyItemsAdapter(items);

        binding.activityDataReaderRVEatingFrom.setAdapter(adapter);

        binding.activityDataReaderInputRead.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(binding.activityDataReaderInputRead, InputMethodManager.SHOW_IMPLICIT);
*/
        LinearLayoutManager linearLayoutManagerTags = new LinearLayoutManager(this);
        linearLayoutManagerTags.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.activityDataReaderRVTags.setLayoutManager(linearLayoutManagerTags);

        List<TagsEntity> itemsTags = new ArrayList<>();
        itemsTags.add(new TagsEntity(1, "Pranzo", ""));
        itemsTags.add(new TagsEntity(2, "Cena", ""));
        itemsTags.add(new TagsEntity(3, "Cioccolata", ""));
        TagsAdapter adapterTags = new TagsAdapter(itemsTags);

        binding.activityDataReaderRVTags.setAdapter(adapterTags);
    }
}
