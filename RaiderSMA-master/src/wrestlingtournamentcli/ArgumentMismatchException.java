package wrestlingtournamentcli;

/**
 * @author Jared Murphy
 * https://github.com/murphman29
 */
public class ArgumentMismatchException extends Exception{
   public ArgumentMismatchException(int expectedArguments, int receivedArguments){
        super("Argument Mismatch Exception: \n\tExpected " + expectedArguments + "\n\tReceived: " + receivedArguments);
}
}
