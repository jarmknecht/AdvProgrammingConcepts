package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.example.jonathan.familymapserver.R;
import shared.Model.Event;
import model.FamilyTree;
import model.Model;
import shared.Model.Person;
import activities.MapsActivity;
import activities.PersonActivity;
import recyclerview.AbstractChildListItem;
import recyclerview.ChildViewHolder;
import recyclerview.EventChildListItem;
import recyclerview.OnChildListItemClickListener;
import recyclerview.PersonChildListItem;

public class SearchFragment extends Fragment {
    private RecyclerView searchRecyclerView;
    private EditText searchEditText;
    private List<AbstractChildListItem> searchResults;

    public static SearchFragment newInstance() {
        SearchFragment searchFragment = new SearchFragment();
        searchFragment.searchResults = new ArrayList<>();
        return searchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        searchRecyclerView = (RecyclerView) view.findViewById(R.id.searchRecyclerView);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchRecyclerView.setAdapter(new SearchAdapter());

        searchEditText = (EditText) view.findViewById(R.id.searchEditText);

        Button searchButton = (Button) view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchResults.clear();
                generateSearchResults(searchEditText.getText());
                Log.i("Search Button", "Searching for: " + searchEditText.getText());
                searchRecyclerView.setAdapter(new SearchAdapter());
            }
        });
        return view;
    }

    private void generateSearchResults(CharSequence query) {
        searchPeople(query);
        searchEvents(query);
    }

    private void searchPeople(CharSequence query) {
        for(Map.Entry<String, Person> entry : Model.getCurrentPeopleMap().entrySet()) {
            if(entry.getValue().getFullName().toLowerCase().contains(query.toString().toLowerCase())) {
                PersonChildListItem personChildListItem = new PersonChildListItem(
                        entry.getValue().getFullName(),
                        null,
                        entry.getValue().isMale(),
                        entry.getKey()
                );
                searchResults.add(personChildListItem);
            }
        }
    }

    private void searchEvents(CharSequence query) {
        for(Map.Entry<String, Event> entry : Model.getCurrentEventMap().entrySet()) {
            if(entry.getValue().getEventType().toLowerCase().contains(query.toString().toLowerCase()) ||
                    entry.getValue().getCity().toLowerCase().contains(query.toString().toLowerCase()) ||
                    entry.getValue().getCountry().toLowerCase().contains(query.toString().toLowerCase()) ||
                    Integer.toString(entry.getValue().getYear()).contains(query.toString())) {
                EventChildListItem eventChildListItem = new EventChildListItem(
                        entry.getValue().getEventType(),
                        entry.getValue().getCity() + ", " + entry.getValue().getCountry(),
                        Integer.toString(entry.getValue().getYear()),
                        null,
                        entry.getKey()
                );
                searchResults.add(eventChildListItem);
            }
        }
    }

    private class SearchAdapter extends RecyclerView.Adapter<ChildViewHolder> {
        @Override
        public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_child, parent, false);
            return new ChildViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ChildViewHolder holder, int position) {
            final AbstractChildListItem searchResult = searchResults.get(position);
            holder.title.setText(searchResult.getTitle());
            holder.subTitle.setText(searchResult.getSubTitle());
            if(searchResult.getClass().equals(EventChildListItem.class)) {
                holder.iconTextView.setText("{fa-map-marker}");
                holder.iconTextView.setTextColor(
                        getContext().getResources().getColor(R.color.defaultEvent,
                                getContext().getTheme())
                );
                holder.setOnClickListener(searchResult,
                        new OnChildListItemClickListener() {
                            @Override
                            public void onChildListItemClick(AbstractChildListItem childListItem) {
                                Intent mapActivityIntent = new Intent(getContext(), MapsActivity.class);
                                mapActivityIntent.putExtra("SELECTED_EVENT", searchResult.getId());
                                getContext().startActivity(mapActivityIntent);
                                Log.i("EventClickListener", "It's me!!");
                            }
                        }
                );
            }
            else if(searchResult.getClass().equals(PersonChildListItem.class)) {
                final PersonChildListItem personSearchResult = (PersonChildListItem) searchResult;
                if(personSearchResult.gender) {
                    holder.iconTextView.setText("{fa-male}");
                    holder.iconTextView.setTextColor(
                            getContext().getResources().getColor(R.color.male,
                                    getContext().getTheme())
                    );
                }
                else {
                    holder.iconTextView.setText("{fa-female}");
                    holder.iconTextView.setTextColor(
                            getContext().getResources().getColor(R.color.female,
                                    getContext().getTheme())
                    );
                }
                holder.setOnClickListener(personSearchResult,
                        new OnChildListItemClickListener() {
                            @Override
                            public void onChildListItemClick(AbstractChildListItem childListItem) {
                                Model.setCurrentAncestor(
                                        Model.getCurrentPeopleMap().get(personSearchResult.getId())
                                );
                                FamilyTree focusTree = new FamilyTree(Model.getCurrentAncestor());
                                getContext().startActivity(new Intent(getContext(), PersonActivity.class));
                                Log.i("PersonClickListener", "Consider me clicked");
                            }
                        });
            }
        }

        @Override
        public int getItemCount() {
            return searchResults.size();
        }
    }
}
