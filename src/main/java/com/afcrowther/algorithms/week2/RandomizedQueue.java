package com.afcrowther.algorithms.week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Random Queue implementation, utilizes a resizing array data structure
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

  private static int MIN_QUEUE_SIZE = 1;

  private int size;
  private Item[] queue;

  public RandomizedQueue() {
    this.queue = (Item[]) new Object[MIN_QUEUE_SIZE];
    this.size = 0;
  }

  /**
   * Is the queue empty?
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * How many items are in the queue
   */
  public int size() {
    return size;
  }

  /**
   * Add a new item to the queue
   */
  public void enqueue(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
    if (size == queue.length) {
      // grow queue
      resizeQueue(queue.length * 2);
    }
    queue[size++] = item;
  }

  /**
   * Remove an item at random from the queue and return it
   */
  public Item dequeue() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    int dequeueIndex = StdRandom.uniform(size);
    Item dequeued = queue[dequeueIndex];
    queue[dequeueIndex] = queue[size - 1];
    queue[size - 1] = null;
    size--;
    if (queue.length != MIN_QUEUE_SIZE && size <= queue.length / 4) {
      // shrink queue
      resizeQueue(queue.length / 2);
    }
    return dequeued;
  }

  /**
   * Get a random element from the queue, do not dequeue
   */
  public Item sample() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    return queue[StdRandom.uniform(size)];
  }

  private void resizeQueue(int newSize) {
    Item[] temp = (Item[]) new Object[newSize];
    for (int i = 0; i < size; i++) {
      temp[i] = queue[i];
    }
    queue = temp;
  }

  /**
   * Get a randomly ordered iterator from the queue
   */
  @Override
  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }

  /**
   * Random iterator implementsa
   */
  private class RandomizedQueueIterator implements Iterator<Item> {

    private Item[] internalQueue;
    private int internalSize;

    public RandomizedQueueIterator() {
      // have to snapshot the queue at this point
      internalSize = size;
      internalQueue = (Item[]) new Object[internalSize];
      for (int i = 0; i < internalSize; i++) {
        internalQueue[i] = queue[i];
      }
    }

    @Override
    public boolean hasNext() {
      return internalSize > 0;
    }

    @Override
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      int random = StdRandom.uniform(internalSize);
      Item next = internalQueue[random];
      internalQueue[random] = internalQueue[--internalSize];
      internalQueue[internalSize] = null;
      return next;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

  }

  public static void main(String[] args) {
    // for tests
  }
}
