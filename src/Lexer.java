import java.io.EOFException;

public class Lexer
{
    private String text;
    private int position;
    private char currentChar;

    //const members.
    private final static char PLUS_OPERATOR_SYMBOL = '+';
    private final static char MINUS_OPERATOR_SYMBOL = '-';
    private final static char MULTIPLICATION_OPERATOR_SYMBOL = '*';
    private final static char DIVISION_OPERATOR_SYMBOL = '/';
    private final static char MODULUS_OPERATOR_SYMBOL = '%';
    private final static String INTEGER_TYPE = "INTEGER";
    private final static String PLUS_OPERATOR = "PLUS_OPERATOR";
    private final static String MINUS_OPERATOR = "MINUS_OPERATOR";
    private final static String MULTIPLICATION_OPERATOR = "MULTIPLICATION_OPERATOR";
    private final static String DIVISION_OPERATOR = "DIVISION_OPERATOR";
    private final static String MODULUS_OPERATOR = "MODULUS_OPERATOR";

    public Lexer ()
    {
        text = "";
        position = 0;
        currentChar = '\0';
    }

    public Lexer (String text, int position, char currentChar)
    {
        this.text = text;
        this.position = position;
        this.currentChar = currentChar;
    }

    public Lexer (String text)
    {
        this.text = text;
        this.position = 0;
        this.currentChar = '\0';
    }

    public void advance()
    {
        ++position;
        if(position > text.length() -1)
        {
            currentChar = '\0';
        }else
        {
            currentChar = text.charAt(position);
        }
    }

    public void skipWhitespace()
    {
        while(currentChar != '\0' && Character.isSpaceChar(currentChar))
        {
            advance();
        }
    }

    public Token getNextToken() throws EOFException
    {
        while(currentChar != '\0')
        {
            if (Character.isSpaceChar(currentChar))
            {
                skipWhitespace();
            }
            else if(Character.isDigit(currentChar))
            {
                int number = integer();
                return new Token<Integer>(INTEGER_TYPE, number);
            }
            else if(currentChar == PLUS_OPERATOR_SYMBOL)
            {
                advance();
                return new Token<Character>(PLUS_OPERATOR, PLUS_OPERATOR_SYMBOL);
            }
            else if(currentChar == MINUS_OPERATOR_SYMBOL)
            {
                advance();
                return new Token<Character>(MINUS_OPERATOR, MINUS_OPERATOR_SYMBOL);
            }
            else if(currentChar == MULTIPLICATION_OPERATOR_SYMBOL)
            {
                advance();
                return new Token<Character>(MULTIPLICATION_OPERATOR, MULTIPLICATION_OPERATOR_SYMBOL);
            }
            else if(currentChar == DIVISION_OPERATOR_SYMBOL)
            {
                advance();
                return new Token<Character>(DIVISION_OPERATOR, DIVISION_OPERATOR_SYMBOL);
            }
            else if(currentChar == MODULUS_OPERATOR_SYMBOL)
            {
                advance();
                return new Token<Character>(MODULUS_OPERATOR, MODULUS_OPERATOR_SYMBOL);
            }
            else
            {
                error();
            }
        }
        return new Token("EOFException", '\0');
    }

    public void error() throws EOFException
    {
        throw new EOFException("Error parsing input");
    }

    private int integer()
    {
        StringBuilder multiDigitNumber = new StringBuilder("");
        while(currentChar != '\0' && Character.isDigit(currentChar))
        {
            multiDigitNumber.append(currentChar);
            advance();
        }

        return Integer.parseInt(new String(multiDigitNumber));
    }
}
