package wrestlingtournamentcli;

/**
 * @author Jared Murphy
 * https://github.com/murphman29
 */
public class NotFoundException extends Exception {
    public NotFoundException(Object o){
        super("The operation(s) could not be completed.\nIf creating a Wrestler object, we were unable to find the team from the alias you provided us.\n" + "The following object could not be found: " + o +"\nPlease try again.");
    }
}
