package com.datagraph.core;

import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Denny Joseph on 6/4/16.
 */
public class ReadWriteLinkedList<TJob> extends LinkedList {

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public boolean add(Object o) {
        lock.writeLock().lock();
        try {
            return super.add(o);
        } finally {
            lock.writeLock().unlock();
        }
    }

}
