package com.afcrowther.algorithms.week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double ended queue implementation, in the style of a doubly linked list
 */
public class Deque<Item> implements Iterable<Item> {

  private int size;
  private Node<Item> first;
  private Node<Item> last;

  public Deque() {
    this.size = 0;
    this.first = null;
    this.last = null;
  }

  /**
   * Is the Deque empty?
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * How many elements in the deque
   */
  public int size() {
    return size;
  }

  /**
   * Add an item to the start of the deque
   */
  public void addFirst(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }

    Node<Item> newItem = new Node<Item>(item);

    if (size == 0) {
      last = newItem;
    } else {
      newItem.next = first;
      first.previous = newItem;
    }
    first = newItem;
    size++;
  }

  /**
   * Add an element to the end of the deque
   */
  public void addLast(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }

    Node<Item> newItem = new Node<Item>(item);

    if (size == 0) {
      first = newItem;
    } else {
      newItem.previous = last;
      last.next = newItem;
    }
    last = newItem;
    size++;
  }

  /**
   * Remove an element from the start of the deque and return it
   */
  public Item removeFirst() {
    if (size == 0) {
      throw new NoSuchElementException();
    }

    Node<Item> currentFirst = first;
    first = currentFirst.next;
    size--;
    if (size != 0) {
      first.previous = null;
    } else {
      last = null;
    }

    return currentFirst.item;
  }

  /**
   * Remove an element from the end of the deque and return it
   */
  public Item removeLast() {
    if (size == 0) {
      throw new NoSuchElementException();
    }

    Node<Item> currentLast = last;
    last = currentLast.previous;
    size--;
    if (size != 0) {
      last.next = null;
    } else {
      first = null;
    }

    return currentLast.item;
  }

  /**
   * Iterator implementation for the deque
   */
  @Override
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }

  private class Node<Item> {
    private Item item;
    private Node<Item> next;
    private Node<Item> previous;

    Node(Item item) {
      this.item = item;
      this.next = null;
      this.previous = null;
    }
  }

  private class DequeIterator implements Iterator<Item> {

    private Node<Item> current;

    public DequeIterator() {
      current = first;
    }

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      else {
        Item next = current.item;
        current = current.next;
        return next;
      }
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

  }

  public static void main(String[] args) {
    // for testing
  }

}
