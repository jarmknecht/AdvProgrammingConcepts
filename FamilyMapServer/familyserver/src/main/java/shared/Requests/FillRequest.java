package shared.Requests;

/**
 * Created by Jonathan on 2/12/18.
 */
//Done with code

/**
 * Handles the requests from the server for fill urls
 */

public class FillRequest {
    private String username;
    private int generations = 4; //the default num of gens

    /**
     * Constructor if the number of generations is specified
     * @param username user's name
     * @param generations num of generations to add to user
     */

    public FillRequest(String username, int generations) {
        this.username = username;
        this.generations = generations;
    }

    /**
     * Default constructor to have the default of 4 generations for the user
     * @param username user to fill
     */

    public FillRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getGenerations() {
        return generations;
    }
}
