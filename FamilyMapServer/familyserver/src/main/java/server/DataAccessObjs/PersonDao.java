package server.DataAccessObjs;
import shared.Model.Person;

import java.sql.*;
import java.util.*;
//DONE with code
/**
 * Created by Jonathan on 2/12/18.
 */


/**
 * Object that interacts with the Person DB Table
 */

public class PersonDao {
    private Connection conn;

    /**
     * Construct a new DAO based off of a connection received
     * @param conn Connection specific to the person
     */

    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     *Inserts the person along with a person object's data members into the person table
     * @param person person object to add
     * @return True if it was added to the DB false otherwise
     * @throws SQLException
     */

    public boolean createPerson(Person person) throws SQLException {
        PreparedStatement statement = null;
        boolean success = false;

        try {
            String mySQL = "Insert into person (personID, descendant, first_name, last_name, gender," +
                            " father, mother, spouse) values (?, ?, ?, ?, ?, ?, ?, ?)";
            statement = conn.prepareStatement(mySQL);
            statement.setString(1, person.getPersonID());
            statement.setString(2, person.getDescendant());
            statement.setString(3, person. getFirstName());
            statement.setString(4, person. getLastName());
            statement.setString(5, person. getGender());
            statement.setString(6, person.getFather());
            statement.setString(7, person.getMother());
            statement.setString(8, person.getSpouse());

            if (statement.executeUpdate() == 1) {
                success = true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (statement != null) {
                statement.close();
            }
        }

        return success;
    }

    /**
     * Selects from the person table the row that belongs to the personId
     * @param personId Id of person looking for
     * @return person that belongs to the Id
     */

    public Person getPersonByPersonId(String personId) {
        Person person = null;
        String personID = null;
        String descendant = null;
        String firstName = null;
        String lastName = null;
        String gender = null;
        String father = null;
        String mother = null;
        String spouse = null;

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            String mySQL = "Select * from person where person.personID = ?";
            statement = conn.prepareStatement(mySQL);
            statement.setString(1, personId);
            rs = statement.executeQuery();

            while (rs.next()) {
                personID = rs.getString(1);
                descendant = rs.getString(2);
                firstName = rs.getString(3);
                lastName = rs.getString(4);
                gender = rs.getString(5);
                if (rs.getString(6) != null) {
                    father = rs.getString(6);
                }
                if (rs.getString(7) != null) {
                    mother = rs.getString(7);
                }
                if (rs.getString(8) != null) {
                    spouse = rs.getString(8);
                }
            }
            person = new Person(firstName, lastName, gender);
            person.setPersonID(personID);
            person.setDescendant(descendant);
            person.setFather(father);
            person.setMother(mother);
            person.setSpouse(spouse);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (statement != null) {
                try {
                    statement.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return person;
    }

    /**
     * Select all of the ancestors that belong to the user
     * @param username user name to search for
     * @return a List of all ancestors
     */

    public List<Person> getAllPersonsByUser(String username) {
        Person person = null;
        String personID = null;
        String descendant = null;
        String firstName = null;
        String lastName = null;
        String gender = null;


        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Person> people = new ArrayList<>();

        try {
            String mySQL = "Select * from person where person.descendant = ?";
            statement = conn.prepareStatement(mySQL);
            statement.setString(1, username);
            rs = statement.executeQuery();

            while (rs.next()) {
                String father = null;
                String mother = null;
                String spouse = null;

                personID = rs.getString(1);
                descendant = rs.getString(2);
                firstName = rs.getString(3);
                lastName = rs.getString(4);
                gender = rs.getString(5);
                if (rs.getString(6) != null) {
                    father = rs.getString(6);
                }
                if (rs.getString(7) != null) {
                    mother = rs.getString(7);
                }
                if (rs.getString(8) != null) {
                    spouse = rs.getString(8);
                }

                person = new Person(firstName, lastName, gender);
                person.setPersonID(personID);
                person.setDescendant(descendant);
                person.setFather(father);
                person.setMother(mother);
                person.setSpouse(spouse);

                people.add(person);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (statement != null) {
                try {
                    statement.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return people;
    }

    /**
     * Checks to see if the person is actually in the DB by selecting based on the personId and
     * select the row of that person
     * @param personId what to search for
     * @return True if result set has things in it false if otherwise
     */

    public boolean checkForPersonByPersonId(String personId) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        boolean success = false;

        try {
            String mySQL = "Select * from person where personID = ?";
            statement = conn.prepareStatement(mySQL);
            statement.setString(1, personId);
            rs = statement.executeQuery();

            if (rs.next()) {
                success = true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (statement != null) {
                try {
                    statement.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return success;
    }

    /**
     * Delete from the person DB all ancestors that belong to the user
     * @param username user name to search for
     * @return True if everything was deleted false otherwise
     */

    public boolean deleteByUser(String username) {
        PreparedStatement statement = null;
        boolean success = false;

        try {
            String mySQL = "Delete from person where person.descendant = ?";
            statement = conn.prepareStatement(mySQL);
            statement.setString(1, username);

            if (statement.executeUpdate() > 0) { //updated from ==1
                success = true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (statement != null) {
                try {
                    statement.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return success;
    }

    /**
     * Deletes all info from the PersonTable DB
     */

    public void deleteAll() {
        PreparedStatement statement = null;
        boolean success = false;

        try {
            String mySQL = "Delete from person";
            statement = conn.prepareStatement(mySQL);

            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (statement != null) {
                try {
                    statement.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
