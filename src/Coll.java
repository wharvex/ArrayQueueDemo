import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

public class Coll<T> {

  private Object[] elements;
  private static final int MAX_SIZE = 144998983;
  private int tailIdx;

  public Coll() {
    this.elements = new Object[100];
  }

  public Object[] getElements() {
    return elements;
  }

  public void setElements(Object[] elements) {
    this.elements = elements;
  }

  public int getTailIdx() {
    return tailIdx;
  }

  public void setTailIdx(int tailIdx) {
    this.tailIdx = tailIdx;
  }

  public int size() {
    return this.getElements().length;
  }

  public boolean isEmpty() {
    return this.getElements()[0] == null;
  }

  private void incrTailIdx() throws Exception {
    this.setTailIdx(this.getTailIdx() + 1);
    if (this.getTailIdx() >= this.size()) {
      this.grow();
    }
  }

  public Optional<T> peek() throws Exception {
    return this.seek(0);
  }

  private void grow() throws Exception {
    int oldCapacity = this.size();
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    if (newCapacity > MAX_SIZE) {
      throw new Exception("Overflow");
    }
    this.setElements(Arrays.copyOf(this.getElements(), newCapacity));
  }

  private void setElement(T t) throws Exception {
    this.getElements()[this.getTailIdx()] = t;
    this.incrTailIdx();
  }

  public void offer(T t) throws Exception {
    if (t == null) {
      throw new Exception("Internal Error: Adding Null Entry To Coll Not Allowed");
    }
    this.setElement(t);
  }

  @SuppressWarnings("unchecked")
  public Optional<T> seek(int idx) throws Exception {
    if (idx > this.getTailIdx() || this.isEmpty()) {
      return Optional.empty();
    }
    Optional<T> ret = Optional.ofNullable((T) this.getElements()[idx]);
    if (ret.isEmpty()) {
      throw new Exception("Internal Error: Null Entry Before Tail Index");
    }
    return ret;
  }

//  public Optional<T> poll() throws Exception {
//    Optional<T> optT = this.peek();
//    if (t != null) {
//      iter.remove();
//      return Optional.of(t);
//    }
//    return Optional.empty();
//  }
}
