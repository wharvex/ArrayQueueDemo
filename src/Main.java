public class Main {

  public static void main(String[] args) throws Exception {
    System.out.println("Hello world!");
    OptionalExpandingArrayQueue<Token> coll = new OptionalExpandingArrayQueue<>();
    for (int i = 0; i < 100; i++) {
      coll.offer(new Token("hi", Token.TokenType.GENERAL, i));
    }
    for (int i = 0; i < 15; i++) {
      coll.poll();
    }
    for (int i = 0; i < 50; i++) {
      coll.offer(new Token("hi2", Token.TokenType.GENERAL, 100 + i));
    }
    System.out.println(coll);
    System.out.println(coll.peek());
    System.out.println(coll.seek(200));
  }
}