public class CollHelper {

  private final Coll<Token> tokens;

  private CollHelper() {
    this.tokens = new Coll<>();
  }

  private static class SingletonHelper {

    private static final CollHelper INSTANCE = new CollHelper();
  }

  public static CollHelper getInstance() {
    return SingletonHelper.INSTANCE;
  }

  public static void offer(Token token) throws Exception {
    getInstance().tokens.offer(token);
  }

}
