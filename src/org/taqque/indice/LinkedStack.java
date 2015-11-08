package org.taqque.indice;

/**
 * <p>Title: </p>
 * <p>Description: Linked list implementation of Stack </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class LinkedStack implements Stack {

  private SingleLinkedListNode top;

  public LinkedStack() {
    top = null;
  }

  public boolean isEmpty(){
    return top == null;
  }

  public Object getLast(){
    if(top == null)
      throw new java.util.NoSuchElementException();
    return top.item;
  }

  public void clear(){
    top = null;
  }

  public void addLast(Object item){
    top = new SingleLinkedListNode(item, top);
  }

  public Object removeLast(){
    if(top == null)
      throw new java.util.NoSuchElementException();
    Object topItem = top.item;
    top = top.succ;
    return topItem;
  }
}