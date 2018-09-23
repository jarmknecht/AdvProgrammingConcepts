package recyclerview;

public class PersonChildListItem extends AbstractChildListItem {
    public String name;
    public String relation;
    public String personId;
    public boolean gender;

    public PersonChildListItem(String name, String relation, boolean gender, String personId) {
        this.name = name;
        this.relation = relation;
        this.gender = gender;
        this.personId = personId;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSubTitle() {
        return relation;
    }

    @Override
    public String getId() {
        return personId;
    }
}
