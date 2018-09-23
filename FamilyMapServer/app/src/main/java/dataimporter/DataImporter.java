package dataimporter;


import java.util.*;

import shared.Model.Event;
import shared.Model.Person;
import model.Filter;
import model.Model;


/**
 * Created by Jonathan on 3/22/18.
 */

public class DataImporter {
    public void generateData() {
        generateEventTypes();
        generateFilters();
        generateAncestors();
    }

    public Set<String> generateEventTypes() {
        for (Map.Entry<String, Event> eventEntry : Model.getCurrentEventMap().entrySet()) {
            Model.getEventTypes().add(eventEntry.getValue().getEventType());
        }

        return Model.getEventTypes();
    }

    private void generateFilters() {
        boolean checkFilter = false;

        for (String eventType : Model.getEventTypes()) {
            String filterTitle = eventType.substring(0, 1).toUpperCase() +
                    eventType.substring(1) +
                    " Events";
            String filterSubTitle = ("Filter by " + filterTitle).toUpperCase();
            Filter filter = new Filter(eventType.toLowerCase(), filterTitle, filterSubTitle);
            for (int i=0;i<Model.getFilters().size();i++){
                if (filter.getFilterTitle().equals(Model.getFilters().get(i).getFilterTitle())){
                    checkFilter = true;
                }
            }
            if (!checkFilter) {
                Model.getFilters().add(filter);
                checkFilter = false;
            }
        }

        Filter fathersSideFilter = new Filter(
                null,
                "Father's Side",
                ("Filter by Father's side of family").toUpperCase()
        );

        for (int i = 0; i < Model.getFilters().size(); i++){
            if (fathersSideFilter.getFilterTitle().equals(Model.getFilters().get(i).getFilterTitle())){
                checkFilter = true;
            }
        }
        if (!checkFilter) {
            Model.getFilters().add(fathersSideFilter);
            checkFilter = false;
        }

        Filter mothersSideFilter = new Filter(
                null,
                "Mother's Side",
                ("Filter by Mother's side of family").toUpperCase()
        );

        for (int i = 0; i < Model.getFilters().size(); i++){
            if (mothersSideFilter.getFilterTitle().equals(Model.getFilters().get(i).getFilterTitle())){
                checkFilter = true;
            }
        }
        if (!checkFilter) {
            Model.getFilters().add(mothersSideFilter);
            checkFilter = false;
        }

        Filter maleEventsFilter = new Filter(
                null,
                "Male Events",
                ("Filter events based on gender").toUpperCase()
        );

        for (int i = 0; i < Model.getFilters().size(); i++){
            if (maleEventsFilter.getFilterTitle().equals(Model.getFilters().get(i).getFilterTitle())){
                checkFilter = true;
            }
        }
        if (!checkFilter) {
            Model.getFilters().add(maleEventsFilter);
            checkFilter = false;
        }

        Filter femaleEventsFilter = new Filter(
                null,
                "Female Events",
                ("Filter events based on gender").toUpperCase()
        );
        for (int i = 0; i < Model.getFilters().size(); i++){
            if (femaleEventsFilter.getFilterTitle().equals(Model.getFilters().get(i).getFilterTitle())){
                checkFilter = true;
            }
        }
        if (!checkFilter) {
            Model.getFilters().add(femaleEventsFilter);
            checkFilter = false;
        }
    }

    private void generateAncestors() {
        if(Model.getCurrentPerson().getFather() != null) {
            Set<String> paternalAncestors = new HashSet<>();
            generateAncestorsHelper(Model.getCurrentPerson().getFather(),
                    paternalAncestors);

            Model.setPaternalAncestors(paternalAncestors);
        }

        if(Model.getCurrentPerson().getMother() != null) {
            Set<String> maternalAncestors = new HashSet<>();
            generateAncestorsHelper(Model.getCurrentPerson().getMother(),
                    maternalAncestors);

            Model.setMaternalAncestors(maternalAncestors);
        }

    }

    private void generateAncestorsHelper(String parentId, Set<String> ancestors) {
        ancestors.add(parentId);

        Person parent = Model.getCurrentPeopleMap().get(parentId);

        if(parent != null) {
            if (parent.getFather() != null) {
                generateAncestorsHelper(parent.getFather(), ancestors);
            }

            if (parent.getMother() != null) {
                generateAncestorsHelper(parent.getMother(), ancestors);
            }
        }
    }
}
