package ru.eipugf

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Unit Tests for BoundedMaxLongTreeSet
 *
 * @author Evgeny Karsskiy
 * @see {@link ru.eipugf.CollectorsEx}
 * @since 0.0.1
 */
class BoundedMaxLongTreeSetSpec extends Specification {

    /**
     * <p>Test {@link BoundedMaxLongTreeSet#add(Long value)}.</p>
     */
    @Unroll
    def "test add"() {
        when:
        def bmlSet = new BoundedMaxLongTreeSet(nmax)
        params.forEach() { bmlSet.add(it) }
        then:
        bmlSet == result
        where:
        params                                    | nmax || result
        [10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L, 1L] | 1    || (Set<Long>) [10]
        [10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L, 1L] | 2    || (Set<Long>) [10, 9]
        [8L, 9L, 5L, 6L, 7L, 4L, 1L, 2L, 3L]      | 2    || (Set<Long>) [9, 8]
        [8L, 9L, 5L, 6L, 10L, 7L, 4L, 2L, 3L]     | 3    || (Set<Long>) [10, 9, 8]
        [9L, 6L, 10L, 4L, 1L, 2L, 3L]             | 3    || (Set<Long>) [10, 9, 6]
    }

    /**
     * <p>Test {@link BoundedMaxLongTreeSet#addAll(Collection < ? extends Long > other)}.</p>
     */
    def "test addAll"() {
        setup:
        def bmlSetOne = new BoundedMaxLongTreeSet(nmax)
        def bmlSetTwo = new BoundedMaxLongTreeSet(nmax)
        itemsOne.forEach { bmlSetOne.add(it) }
        itemsTwo.forEach { bmlSetTwo.add(it) }
        when:
        bmlSetOne.addAll(bmlSetTwo)
        then:
        result == bmlSetOne
        where:
        itemsOne  | itemsTwo  | nmax || result
        [1, 2, 3] | [4, 5, 6] | 6    || (Set<Long>) [1, 2, 3, 4, 5, 6]
        [1, 2, 3] | [4, 5, 6] | 5    || (Set<Long>) [2, 3, 4, 5, 6]
        [4, 3, 6] | [1, 5, 2] | 6    || (Set<Long>) [1, 2, 3, 4, 5, 6]
        [4, 3, 6] | [1, 5, 2] | 5    || (Set<Long>) [2, 3, 4, 5, 6]
    }

    /**
     * <p>Test thrown exceptions for {@link BoundedMaxLongTreeSet ( int )}
     * constructor.</p>
     */
    def "exception maxNBuffer"() {
        when:
        new BoundedMaxLongTreeSet(-10)
        then:
        thrown IllegalArgumentException
    }

}
