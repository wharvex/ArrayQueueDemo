import java.io.*;
import java.util.*;
import java.awt.*;

public class ObjectCloner {

  // so that nobody can accidentally create an ObjectCloner object
  private ObjectCloner() {
  }

  // returns a deep copy of an object
  static public Object deepCopy(Object oldObj) throws Exception {
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;
    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      oos = new ObjectOutputStream(bos);
      // serialize and pass the object
      oos.writeObject(oldObj);
      oos.flush();
      ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
      ois = new ObjectInputStream(bin);
      // return the new object
      return ois.readObject();
    } catch (Exception e) {
      System.out.println("Exception in ObjectCloner = " + e);
      throw (e);
    } finally {
      oos.close();
      ois.close();
    }
  }
}