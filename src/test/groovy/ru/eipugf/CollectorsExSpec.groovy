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

package ru.eipugf

import one.util.streamex.LongStreamEx
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Unit Tests for CollectorsEx
 *
 * @author Evgeny Karsskiy
 * @see {@link ru.eipugf.CollectorsEx}
 * @since 0.0.1
 */
class CollectorsExSpec extends Specification {

    /**
     * Tests for {@link ru.eipugf.CollectorsEx#collectNMax(int) )}.
     */
    @Unroll
    def "test CollectorEx"() {
        when:
        def res = LongStreamEx
                .of(params)
                .collect(CollectorsEx.collectNMax(nmax))
        then:
        res == result
        where:
        params                                    | nmax || result
        [10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L, 1L] | 1    || (Set<Long>) [10]
        [10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L, 1L] | 2    || (Set<Long>) [10, 9]
        [8L, 9L, 5L, 6L, 7L, 4L, 1L, 2L, 3L]      | 2    || (Set<Long>) [9, 8]
        [8L, 9L, 5L, 6L, 10L, 7L, 4L, 2L, 3L]     | 3    || (Set<Long>) [10, 9, 8]
        [9L, 6L, 10L, 4L, 1L, 2L, 3L]             | 3    || (Set<Long>) [10, 9, 6]
    }
}
