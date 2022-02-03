import java.io.EOFException;

public class Compiler {
    public static void main(String[] args)
    {
        Interpreter interpreter = new Interpreter("10 % 6");

        try{
            var result = interpreter.expression();

            System.out.println(result);
        }catch (EOFException eofException)
        {
            System.out.println(eofException.getMessage());
        }
    }
}