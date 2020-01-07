package wrestlingtournamentcli;

/**
 * @author Jared Murphy
 * https://github.com/murphman29
 */
public class BadCommandException extends Exception {
    public BadCommandException(){
        super("Error: Command Not Found");
}
}
