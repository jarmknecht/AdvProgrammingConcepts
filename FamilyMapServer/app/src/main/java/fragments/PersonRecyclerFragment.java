package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import model.FamilyTree;
import model.Model;
import recyclerview.AbstractParentObject;
import recyclerview.ExpandableAdapter;
import com.example.jonathan.familymapserver.R;

public class PersonRecyclerFragment extends Fragment {
    private RecyclerView personRecyclerView;
    private ExpandableAdapter expandableAdapter;

    private class PersonParentObject extends AbstractParentObject {
        public PersonParentObject() {
            super("Family");
        }
    }

    private class EventParentObject extends AbstractParentObject {
        public EventParentObject() {
            super("Life Events");
        }
    }

    public static PersonRecyclerFragment newInstance() {
        return new PersonRecyclerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        PersonParentObject personParentObject = new PersonParentObject();
        EventParentObject eventParentObject = new EventParentObject();

        FamilyTree focusedTree = new FamilyTree(Model.getCurrentAncestor());

        personParentObject.setChildObjectList(focusedTree.getFamily());
        eventParentObject.setChildObjectList(focusedTree.getEvents());

        List<ParentObject> parentObjectList = new ArrayList<>();
        parentObjectList.add(personParentObject);
        parentObjectList.add(eventParentObject);

        expandableAdapter = new ExpandableAdapter(getContext(), parentObjectList);
        expandableAdapter.setCustomParentAnimationViewId(R.id.expandArrow);
        expandableAdapter.setParentClickableViewAnimationDefaultDuration();
        expandableAdapter.setParentAndIconExpandOnClick(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.person_recycler_fragment, container, false);
        personRecyclerView = (RecyclerView) v.findViewById(R.id.personRecyclerView);
        if(personRecyclerView == null) {
            personRecyclerView = new RecyclerView(getContext());
        }
        personRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        personRecyclerView.setAdapter(expandableAdapter);
        return v;
    }
}
