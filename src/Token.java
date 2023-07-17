import java.io.Serializable;

public class Token implements Serializable {

  public enum TokenType {
    KNOWNWORD,
    OPERATOR,
    LITERAL,
    INDENTATION,
    PUNCTUATION,
    GENERAL
  }

  private TokenType tokenType;
  private String valueString;
  private int lineNumber;

  /**
   * Constructor
   */
  public Token(String valueString, TokenType tokenType, int lineNumber) {
    this.valueString = valueString;
    this.tokenType = tokenType;
    this.lineNumber = lineNumber;
  }

  public Token(Token orig) {
    this.valueString = orig.getValueString();
    this.tokenType = orig.getTokenType();
    this.lineNumber = orig.getTokenLineNum();
  }

  public int getTokenLineNum() {
    return this.lineNumber;
  }

  public TokenType getTokenType() {
    return this.tokenType;
  }

  public String getValueString() {
    return this.valueString;
  }

  @Override
  public String toString() {
    String retBase;
    retBase = this.getTokenType() + "(" + this.getValueString() + ")";
    return retBase + " -- Line " + this.getTokenLineNum();
  }

}
