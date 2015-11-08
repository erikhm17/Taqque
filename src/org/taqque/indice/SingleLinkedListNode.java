package org.taqque.indice;

/**
 * <p>Title: SingleLinkedListNode </p>
 * <p>Description: SingleLinkedListNode implementation </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class SingleLinkedListNode {
  protected Object item;
  protected SingleLinkedListNode succ;

  public SingleLinkedListNode(Object item, SingleLinkedListNode succ) {
    this.item = item;
    this.succ = succ;
  }
}