import java.util.Optional;

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

  public static void offer(Token token) throws Exception {
    getInstance().getTokens().offer(token);
  }

  public static Optional<Token> poll() throws Exception {
    return getInstance().getTokens().poll();
  }

  public static Optional<Token> matchAndRemove(Token.TokenType tt) throws Exception {
    // Replace GENERAL with EOF when copying to hespr project
    Token peekRet = getInstance().getTokens().peek()
        .orElseGet(() -> new Token("", Token.TokenType.GENERAL, -1));
    if (peekRet.getTokenType() == tt) {
      poll();
      return Optional.of(peekRet);
    }
    return Optional.empty();
  }
}

