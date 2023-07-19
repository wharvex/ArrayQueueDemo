import java.util.*;

/**
 * The OptionalExpandingArrayQueue class uses an array to implement a queue.
 */

public class OptionalExpandingArrayQueue<T> {

  private Object[] elements; // Holds queue elements
  private int frontIdx; // Next item to be removed
  private int rearIdx; // Next slot to fill
  private int size; // Number of items in queue
  private static final int INITIALSIZE = 10;

  /**
   * Constructor.
   */

  public OptionalExpandingArrayQueue() {
    this.elements = new Object[INITIALSIZE];
  }

  public Object[] getElements() {
    return this.elements;
  }

  public void setElements(Object[] elements) {
    this.elements = elements;
  }

  public int getFrontIdx() {
    return this.frontIdx;
  }

  public void setFrontIdx(int frontIdx) {
    this.frontIdx = frontIdx;
  }

  public int getRearIdx() {
    return this.rearIdx;
  }

  public void setRearIdx(int rearIdx) {
    this.rearIdx = rearIdx;
  }

  public int getSize() {
    return this.size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getArrCap() {
    return this.getElements().length;
  }

  public boolean isWrapped() {
    return this.getRearIdx() <= this.getFrontIdx();
  }

  /**
   * Returns empty if the index is out of range because this will signal end of file in a peek
   *
   * @param idx
   * @return
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public Optional<T> getElementRawIdx(int idx) {
    if (this.isEmpty() || (this.isWrapped() && idx >= this.getRearIdx() && idx < this.getFrontIdx())
        || (!this.isWrapped() && (idx < this.getFrontIdx() || idx >= this.getRearIdx()))
        || idx >= this.getArrCap()) {
      return Optional.empty();
    }
    Optional<T> ret = Optional.ofNullable((T) this.getElements()[idx]);
    if (ret.isEmpty()) {
      System.out.println(idx + " " + this.getFrontIdx() + " " + this.getRearIdx());
      this.printQueue2();
      throw new RuntimeException("Internal Error: Found Null Entry In Queue");
    }
    return ret;
  }

  public Optional<T> peek() {
    return this.getElementRawIdx(this.getFrontIdx());
  }

  public void offer(T t) {
    if (t == null) {
      throw new RuntimeException("Null Additions To Queue Not Allowed");
    }
    this.setSize(this.getSize() + 1);
    this.getElements()[this.getRearIdx()] = t;
    this.setRearIdx(this.getRearIdx() + 1);
    if (this.getRearIdx() == this.getArrCap()) {
      this.setRearIdx(0);
    }
    if (this.getSize() == this.getArrCap()) {
      this.expand();
    }
  }

  private void expectsFullQueue() {
    if (!(this.getSize() == this.getArrCap() && this.getRearIdx() == this.getFrontIdx())) {
      throw new RuntimeException("Internal Error: Expected Full Queue For Current Operation");
    }
  }

  private void expand() {
    this.expectsFullQueue();
    int oldCapacity = this.getArrCap();
    int increase = oldCapacity >> 1;
    int newCapacity = oldCapacity + increase;
    Object[] newElements = new Object[newCapacity];
    System.arraycopy(this.getElements(), 0, newElements, 0, this.getRearIdx());
    System.arraycopy(this.getElements(), this.getFrontIdx(), newElements,
        this.getFrontIdx() + increase, this.getArrCap() - this.getFrontIdx());
    this.setFrontIdx(this.getFrontIdx() + increase);
    this.setElements(newElements);
  }

  public Optional<T> seek(int idx) {
    if (idx >= this.getSize()) {
      return Optional.empty();
    }
    int seekIdx = this.getFrontIdx() + idx;
    if (seekIdx >= this.getArrCap()) {
      seekIdx -= this.getArrCap();
    }
    return this.getElementRawIdx(seekIdx);
  }

  public Optional<T> poll() {
    Optional<T> optionalT = this.peek();
    // This should only be empty if the queue is empty
    if (optionalT.isEmpty()) {
      return optionalT;
    }

    // Decrease size; store front element; set front element to null
    T t = optionalT.get();
    this.setSize(this.getSize() - 1);
    this.getElements()[this.getFrontIdx()] = null;

    // Update front index
    this.setFrontIdx(this.getFrontIdx() + 1);
    if (this.getFrontIdx() == this.getArrCap()) {
      this.setFrontIdx(0);
    }

    return Optional.of(t);
  }

  public boolean isEmpty() {
    return this.getSize() == 0;
  }

  /**
   * The toString method returns a readable representation of the contents of the queue.
   *
   * @return The string representation of the contents of the queue.
   */

  public String toString() {
    StringBuilder ret = new StringBuilder();
    for (int k = 0; k < this.getArrCap(); k++) {
      ret.append(this.getElementRawIdx(k).map(T::toString).orElse("?"));
      if (k != this.getArrCap() - 1) {
        ret.append("\n");
      }
    }
    return ret.toString();
  }

  public void printQueue() {
    for (int k = 0; k < this.getArrCap(); k++) {
      System.out.println(this.getElementRawIdx(k).map(T::toString).orElse("?"));
    }
  }

  public void printQueue2() {
    for (int k = 0; k < this.getArrCap(); k++) {
      System.out.println(k + " " + this.getElements()[k]);
    }
  }
}
