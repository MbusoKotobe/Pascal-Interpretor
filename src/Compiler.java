import java.io.EOFException;

public class Compiler {
    public static void main(String[] args)
    {
        try{
            Interpreter interpreter = new Interpreter("10 % 6");
            var result = interpreter.expression();
            System.out.println(result);
        }catch (EOFException eofException)
        {
            System.out.println(eofException.getMessage());
        }
    }
}