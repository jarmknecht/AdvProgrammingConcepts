package shared.Results;
import com.google.gson.JsonObject;
/**
 * Created by Jonathan on 2/12/18.
 */
//Done with code
/**
 * Creates a Json message based on the result of the request
 */

public class PersonResult {
    private String descendant = null;
    private String personID = null;
    private String firstName = null;
    private String lastName = null;
    private String gender = null;
    private String mother = null;
    private String father = null;
    private String spouse = null;

    private String message = null;

    /**
     * Creates a new person result
     * @param descendant user
     * @param personID id of person
     * @param firstName first name of person
     * @param lastName last name of person
     * @param gender m or f
     * @param mother name
     * @param father name
     * @param spouse name can be null
     */

    public PersonResult(String descendant, String personID, String firstName, String lastName, String gender,
                        String mother, String father, String spouse) {
        this.descendant = descendant;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.mother = mother;
        this.father = father;
        this.spouse = spouse;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Make message for the result
     * @param message what the result is
     */

    public PersonResult(String message) {
        this.message = message;
    }

    /**
     * Makes the Json message that is returned to server
     * @return Json string
     */

    @Override
    public String toString() {
        if (personID != null) {
            return "{" +
                    "descendant = '" + descendant + '\'' +
                    ", personID = '" + personID + '\'' +
                    ", firstName = '" + firstName + '\'' +
                    ", lastName = '" + lastName + '\'' +
                    ", gender = '" + gender + '\'' +
                    ", mother = '" + mother + '\'' +
                    ", father = '" + father + '\'' +
                    ", spouse = '" + spouse + '\'' +
                    '}';
        }
        else {
            JsonObject object = new JsonObject();
            object.addProperty("message", message);
            return object.toString();
        }
    }
}
