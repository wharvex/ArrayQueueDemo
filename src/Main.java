public class Main {

  public static void main(String[] args) throws Exception {
    System.out.println("Hello world!");
    for (int i = 0; i < 50; i++) {
      TokensHolder.offer(new Token("yes", TokenType.GENERAL, i));
    }
    for (int i = 0; i < 15; i++) {
      TokensHolder.poll();
    }
    System.out.println(TokensHolder.getInstance().getTokens());
  }
}