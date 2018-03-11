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

/**
 * Implementation of {@link LongCollector} that implement
 * algorithm for searching N maximal long.
 * <p>
 * <p>The following are examples of using the predefined collector.
 * <p>
 * <pre>{@code
 *     //Accumulate n maximums long elements.
 *     int n = 10;
 *    BoundedMaxLongTreeSet maximums = LongStreamEx
 *                               .of(longList)
 *                               .collect(CollectorsEx.collectNMax(n));
 * }</pre>
 *
 * @author Evgeny Karsskiy
 * @since 0.0.1
 */
public final class CollectorsEx {
    /**
     * Constructor is hidden, the utility class.
     */
    private CollectorsEx() {
    }

    /**
     * <p>Returns a {@link one.util.streamex.LongCollector} to search for n
     * maximal elements in {@link one.util.streamex.LongStreamEx}.</p>
     * <pre>
     *     int n = 3;
     *     BoundedMaxLongTreeSet result = LongStreamEx
     *                              .of(9,10,7,5,8)
     *                              .collect(CollectorsEx.collectNMax(n));
     *     //result contains 10, 9, 8.
     * </pre>
     *
     * @param numberOfMaximums desirable number of maximums.
     * @return LongCollector
     * @since 0.0.1
     */
    public static LongCollector<BoundedMaxLongTreeSet, BoundedMaxLongTreeSet>
    collectNMax(final int numberOfMaximums) {
        return LongCollector.of(
                () -> new BoundedMaxLongTreeSet(numberOfMaximums),
                (longs, value) -> longs.add(value),
                BoundedMaxLongTreeSet::addAll);
    }
}
