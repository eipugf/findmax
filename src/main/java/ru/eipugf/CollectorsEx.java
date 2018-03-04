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

import one.util.streamex.LongCollector;

import java.util.Set;
import java.util.TreeSet;

/**
 * Extension of library stream ex.
 * <p>
 * <ul>
 * <li><b>collectNMax</b>
 * - Creates a collector to find the maximum elements</li>
 * </ul>
 *
 * @author Evgeny Karsskiy
 * @see one.util.streamex.LongStreamEx#collect(LongCollector)
 * @since 0.0.1
 */
public final class CollectorsEx {

    /**
     * Default constructor.
     */
    private CollectorsEx() {
    }

    /**
     * <p><Creates a {@link one.util.streamex.LongCollector} to search for n
     * maximal elements in {@link one.util.streamex.LongStreamEx}.</p>
     * <p>
     * Example.
     * {@code LongStreamEx.of(9,10,7,5,8).collect(CollectorsEx.collectNMax(3))}
     * Returning: 10, 9, 8
     * </p>
     *
     * @param nmax - desirable number of maximums
     * @return LongCollector
     * @since 0.0.1
     */
    public static LongCollector<MaxNBuffer, Set<Long>>
    collectNMax(final int nmax) {
        return LongCollector.of(
                () -> new MaxNBuffer(nmax),
                MaxNBuffer::add,
                MaxNBuffer::addAll,
                MaxNBuffer::getItems);
    }

    /**
     * <p>Represent algorithm for searching N maximal elements in a
     * {@link one.util.streamex.LongStreamEx}.</p>
     *
     * @see one.util.streamex.LongCollector
     * @see ru.eipugf.CollectorsEx#collectNMax(int nmax)
     * @since 0.0.1
     */
    static class MaxNBuffer {
        /**
         * Aggregated maximums.
         */
        private final TreeSet<Long> items = new TreeSet<>();

        /**
         * Desirable number of maximums.
         */
        private final int nmax;

        /**
         * construct MaxNBuffer for nmax elements.
         *
         * @param numberOfMaximums The number of maximums.
         */
        MaxNBuffer(final int numberOfMaximums) {
            if (numberOfMaximums <= 0) {
                throw new IllegalArgumentException(
                        "nmax must be greater than zero");
            }
            nmax = numberOfMaximums;
        }

        /**
         * <p>Add an element if there is a place or element
         * of the most smallest.</p>
         *
         * @param value Long value.
         */
        public void add(final long value) {
            if (items.size() < nmax) {
                items.add(value);
            } else if (items.first() < value) {
                items.pollFirst();
                items.add(value);
            }
        }

        /**
         * <p>Merge maximum buffers.</p>
         *
         * @param other Other maximums buffer.
         * @return this
         */
        public MaxNBuffer addAll(final MaxNBuffer other) {
            other.items.forEach(this::add);
            return this;
        }

        /**
         * Returning search result.
         *
         * @return items
         */
        public TreeSet<Long> getItems() {
            return items;
        }
    }
}
