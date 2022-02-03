import java.io.EOFException;

public class Interpreter
{
    private String text;
    private int position;
    private Token currentToken;
    private char currentChar = '\0';

    private final static char PLUS_OPERATOR_SYMBOL = '+';
    private final static char MINUS_OPERATOR_SYMBOL = '-';
    private final static char MULTIPLICATION_OPERATOR_SYMBOL = '*';
    private final static char DIVISION_OPERATOR_SYMBOL = '/';
    private final static String INTEGER_TYPE = "INTEGER";
    private final static String PLUS_OPERATOR = "PLUS_OPERATOR";
    private final static String MINUS_OPERATOR = "MINUS_OPERATOR";
    private final static String MULTIPLICATION_OPERATOR = "MULTIPLICATION_OPERATOR";
    private final static String DIVISION_OPERATOR = "DIVISION_OPERATOR";

    public Interpreter ()
    { }

    public Interpreter (String text)
    {
        this.text = text;
        this.currentChar = text.charAt(position);
    }

    public Interpreter (String text, int position, Token current_token)
    {
        this.text = text;
        this.position = position;
        this.currentToken = current_token;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public Token getCurrentToken()
    {
        return currentToken;
    }

    public void setCurrentToken(Token currentToken)
    {
        this.currentToken = currentToken;
    }

    @Override
    public String toString()
    {
        return "Interpreter{" +
                "text='" + text + '\'' +
                ", position=" + position +
                ", current_token=" + currentToken +
                '}';
    }

    /***************************************************************/
    //                        LEXER CODE                           //
    /***************************************************************/
    public void error() throws EOFException
    {
        throw new EOFException("Error parsing input");
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
            else
            {
                error();
            }
        }
        return new Token("EOFException", '\0');
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

    /***************************************************************/
    //                   PARSER/INTERPRETER CODE                   //
    /***************************************************************/

    public void eat(String tokenType) throws EOFException
    {
        if(currentToken.getType().equalsIgnoreCase(tokenType))
        {
            currentToken = getNextToken();
        }
        else error();
    }

    public int expression() throws EOFException
    {
        currentToken = getNextToken();
        var result = term();
        while(currentToken.getType().equalsIgnoreCase(PLUS_OPERATOR) ||
              currentToken.getType().equalsIgnoreCase(MINUS_OPERATOR)  )
        {
            var token = currentToken;
            if(token.getType().equalsIgnoreCase((PLUS_OPERATOR)))
            {
                eat(PLUS_OPERATOR);
                result = result + term();
            }
            else if(token.getType().equalsIgnoreCase((MINUS_OPERATOR)))
            {
                eat(MINUS_OPERATOR);
                result = result - term();
            }else if(token.getType().equalsIgnoreCase((MULTIPLICATION_OPERATOR)))
            {
                eat(MULTIPLICATION_OPERATOR);
                result = result * term();
            }else if(token.getType().equalsIgnoreCase((DIVISION_OPERATOR)))
            {
                eat(DIVISION_OPERATOR);
                result = result / term();
            }
        }
        return result;
    }

    private int term() throws EOFException
    {
        var token = currentToken;
        eat(INTEGER_TYPE);
        return (int) token.getValue();
    }

}
