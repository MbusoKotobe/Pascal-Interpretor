public class Token<T> {
    private String type;
    private T value;

    public Token()
    {}

    public Token(String type, T value)
    {
        this.type = type;
        this.value = value;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public T getValue()
    {
        return value;
    }

    public void setValue(T value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "Token{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}
