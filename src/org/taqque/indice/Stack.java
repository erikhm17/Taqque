package org.taqque.indice;

/**
 * <p>Title: Stack data type interface </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public interface Stack {
  public boolean isEmpty();
  public Object getLast(); // Throw NoSuchElementExceptions if stack is empty
  public void clear();
  public void addLast(Object item);
  public Object removeLast(); // Throw NoSuchElementExceptions if stack is empty
}