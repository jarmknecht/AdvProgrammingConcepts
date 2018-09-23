package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.joanzapata.iconify.IconDrawable;
import android.content.Intent;

import com.example.jonathan.familymapserver.R;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import model.Model;
import shared.Model.Person;
import activities.MainActivity;


public class PersonFragment extends Fragment {
    private Person person;

    public static PersonFragment newInstance() {
        PersonFragment personFragment = new PersonFragment();
        personFragment.setPerson(Model.getCurrentAncestor());
        return personFragment;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        person = Model.getCurrentAncestor();
        setHasOptionsMenu(true);
        assert person != null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.person_fragment, container, false);

        TextView firstName = (TextView) v.findViewById(R.id.firstName);
        TextView lastName = (TextView) v.findViewById(R.id.lastName);
        TextView gender = (TextView) v.findViewById(R.id.gender);

        firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        if(person.isMale()) {
            gender.setText("Male");
        }
        else {
            gender.setText("Female");
        }
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_go_to_top, menu);

        MenuItem goToTopMenuItem = menu.findItem(R.id.goToTopButton);
        goToTopMenuItem.setIcon(
                new IconDrawable(getActivity(), FontAwesomeIcons.fa_angle_double_up)
                        .actionBarSize()
                        .colorRes(R.color.menuItemColor)
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goToTopButton:
                startActivity(new Intent(getActivity(), MainActivity.class));
        }
        return true;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
