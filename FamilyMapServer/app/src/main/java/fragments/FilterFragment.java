package fragments;

import com.example.jonathan.familymapserver.R;
import model.Filter;
import model.Model;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.*;

public class FilterFragment extends Fragment {
    private RecyclerView recyclerView;
    private FilterAdapter filterAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
                             savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.filter_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

    private void updateUI() {
        filterAdapter = new FilterAdapter();
        recyclerView.setAdapter(filterAdapter);
    }

    private class FilterHolder extends RecyclerView.ViewHolder {
        private Filter filter;
        private TextView eventTitle;
        private TextView subTitle;
        private Switch switchButton;

        public FilterHolder(View itemView) {
           super(itemView);
            eventTitle = (TextView) itemView
                    .findViewById(R.id.eventTitle);
            subTitle = (TextView) itemView
                    .findViewById(R.id.eventSubtitle);
            switchButton = (Switch) itemView
                    .findViewById(R.id.eventSwitch);
        }

        public void bindFilter(final Filter filter) {
            this.filter = filter;
            eventTitle.setText(this.filter.getFilterTitle());
            subTitle.setText(this.filter.getFilterSubTitle());
            switchButton.setChecked(this.filter.isFilterOn());
            switchButton.setShowText(true);

            switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton button, boolean checked) {
                    Model.getFilters().get(getAdapterPosition()).setFilterOn(!filter.isFilterOn());
                }
            });
        }
    }

    private class FilterAdapter extends RecyclerView.Adapter<FilterHolder> {
        @Override
        public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_filter, parent, false);
            return new FilterHolder(view);
        }

        @Override
        public void onBindViewHolder(FilterHolder holder, int position) {
            Filter filter = Model.getFilters().get(position);
            holder.bindFilter(filter);
        }

        @Override
        public int getItemCount() {
            return Model.getFilters().size();
        }
    }
}
