/*
 * Copyright 2018 Evgeny Karsskiy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.eipugf;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongRBTreeSet;

/**
 * This implementation allows to store a bounded number of sorts in the
 * natural order of integers.
 * <p>
 * A {@link BoundedMaxLongTreeSet} implementation based on a
 * {@link LongRBTreeSet}. The elements are ordered using their
 * {@linkplain Comparable natural ordering}.
 * <p>
 * The size of this collection is limited. When the maximum number of
 * elements in the collection is reached, each subsequent element will
 * be added only if the value of this element is greater than the smallest.
 *
 * @author Evgeny Karsskiy
 * @since 0.0.1
 */
public class BoundedMaxLongTreeSet extends LongRBTreeSet {
    /**
     * Serial Version UID constant.
     */
    private static final long serialVersionUID = -249289815235290089L;

    /**
     * Represent the maximum size of a collection.
     */
    private final int maxSize;

    /**
     * Constructs a new, empty bounded max long tree set,
     * sorted according to the natural ordering of its elements.
     *
     * @param maximumSize Specifies the maximum size of a collection.
     */
    public BoundedMaxLongTreeSet(final int maximumSize) {
        super();
        if (maximumSize <= 0) {
            throw new IllegalArgumentException(
                    "maximumSize must be greater than zero");
        }
        maxSize = maximumSize;
    }

    /**
     * Adds the specified element to this set, if there is free space, it is
     * not already present or the new element is greater than the minimum.
     * <p>
     * If the number of elements has reached the maximum
     * and the new element is greater than the minimum, then the new element
     * will be added to the collection, the smallest will be deleted.
     *
     * @param value element to be added to this set.
     * @return {@code true} if an element was added to the collection, all
     * conditions were met to add a new element to the collection.
     * @throws NullPointerException if the specified element is null.
     */
    @Override
    public final boolean add(final long value) {
        if (size() < maxSize) {
            return super.add(value);
        } else if (firstLong() < value) {
            remove(firstLong());
            return super.add(value);
        }
        return false;
    }

    /**
     * Adds all the elements in the specified collection to this set one at a
     * time, using the algorithm of the
     * {@link BoundedMaxLongTreeSet#add(long value)} function.
     *
     * @param other collection containing elements to be added to this set.
     * @return {@code true} if all items have been added.
     */
    @Override
    public final boolean addAll(final LongCollection other) {
        LongIterator iter = other.iterator();
        boolean addResult = true;
        while (iter.hasNext()) {
            addResult = add(iter.nextLong());
        }
        return addResult;
    }
}
