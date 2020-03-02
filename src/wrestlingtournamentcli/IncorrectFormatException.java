package wrestlingtournamentcli;

/**
 * @author Jared Murphy
 * https://github.com/murphman29
 */
public class IncorrectFormatException extends Exception {
 public IncorrectFormatException(Object o){
        super("The operation(s) could not be completed because the line being \nconverted to an object was not in the correct format.\nPlease fix the error and try importing again.\nHere's where the problem was:" + o);
    }
}
