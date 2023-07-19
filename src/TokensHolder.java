import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class TokensHolder {

  private final OptionalExpandingArrayQueue<Token> tokens;

  private TokensHolder() {
    this.tokens = new OptionalExpandingArrayQueue<>();
  }

  public OptionalExpandingArrayQueue<Token> getTokens() {
    return this.tokens;
  }

  private static class SingletonHelper {

    private static final TokensHolder INSTANCE = new TokensHolder();
  }

  public static TokensHolder getInstance() {
    return SingletonHelper.INSTANCE;
  }

  public static void offer(Token token) {
    getInstance().getTokens().offer(token);
  }

  public static Optional<Token> poll() {
    return getInstance().getTokens().poll();
  }

  public static Token peek() {
    return getInstance().getTokens().peek().orElseGet(Token::new);
  }

  public static boolean peekIs(TokenType tt) {
    return peek().getTokenType() == tt;
  }

  public static boolean peekIs(List<TokenType> tts) {
    return tts.contains(peek().getTokenType());
  }

  public static Optional<Token> matchAndRemove(TokenType tt) {
    return (peekIs(tt)) ? poll() : Optional.empty();
  }

  public static Optional<Token> matchAndRemove(List<TokenType> tts) {
    return (peekIs(tts)) ? poll() : Optional.empty();
  }

  public static Token seek(int idx) {
    return getInstance().getTokens().seek(idx).orElseGet(Token::new);
  }

  public static boolean seekIs(TokenType tt, int idx) {
    return seek(idx).getTokenType() == tt;
  }

  public static boolean seekIs(List<TokenType> tts, int idx) {
    return tts.contains(seek(idx).getTokenType());
  }

  public static boolean findBeforeNextEOL(TokenType tt) {
    TokenType nextTokenType = peek().getTokenType();
    int i = 0;
    while (nextTokenType != TokenType.ENDOFFILE && nextTokenType != TokenType.ENDOFLINE) {
      if (nextTokenType == tt) {
        return true;
      }
      nextTokenType = seek(++i).getTokenType();
    }
    return false;
  }

  public static boolean findBeforeNextEOL2(TokenType tt) {
    return IntStream.iterate(0, i -> i + 1)
        .mapToObj(i -> seek(i).getTokenType())
        .takeWhile(tokenType -> tokenType != TokenType.ENDOFFILE && tokenType != TokenType.ENDOFLINE)
        .anyMatch(tokenType -> tokenType == tt);
  }

  public static int peekLine() {
    return peek().getTokenLineNum();
  }
}

