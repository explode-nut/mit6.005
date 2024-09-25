/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     *
     * Testing strategy for writtenBy
     * Partition the input as follows:
     *      tweets.length(): 0 >0
     *      result: none or one or more
     *      not change original list
     *      same order
     *
     *  Testing strategy for inTimespan
     *  Partition the input as follows:
     *      tweets.length(): 0 >0
     *      result: none or more
     *      not change original list
     *      result have same order
     *
     *  Testing strategy for inTimespan
     *  Partition the input as follows:
     *      tweets.length(): 0 >0
     *      words.length(): 0 1 >1
     *      result: none or more
     *      not change original list
     *      result have same order
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-18T11:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype it", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "rivest talk in 30 minutes #hype", d3);

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    /*
     *  covers: tweets.length() > 0
     *          one result
    */
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }

    /*
     *  covers: tweets.length() > 0
     *          more result
     */
    @Test
    public void testWrittenByMultipleTweetsMultipleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3), "alyssa");

        assertEquals("expected singleton list", 2, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet3));
    }

    /*
     *  covers: tweets.length() > 0
     *          more result
     *          same order
     */
    @Test
    public void testWrittenByMultipleTweetsDifferentOrderMultipleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet3, tweet2, tweet1), "alyssa");

        assertEquals("expected singleton list", 2, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet3));
        assertEquals("expected same order", 0, writtenBy.indexOf(tweet3));
    }

    /*
     *  covers: tweets.length() > 0
     *          none result
     */
    @Test
    public void testWrittenBySingleTweetNoneResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Collections.singletonList(tweet2), "alyssa");

        assertEquals("expected singleton list", 0, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.isEmpty());
    }

    /*
     *  covers: tweets.length() = 0
     *          none result
     */
    @Test
    public void testWrittenByNoneTweetNoneResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Collections.emptyList(), "alyssa");

        assertEquals("expected singleton list", 0, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.isEmpty());
    }

    /*
     *  covers: not change original list
     */
    @Test
    public void testWrittenByNotChangeList() {
        List<Tweet> list = Arrays.asList(tweet1, tweet2);
        int before = list.hashCode();
        Filter.writtenBy(list, "alyssa");
        int after = list.hashCode();
        assertEquals("don't change the list!", before, after);
    }

    /*
    * covers:
    *       tweets.length() > 0
    *       result > 0
    *       same order
    *       not change list
    * */
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        List<Tweet> list = Arrays.asList(tweet1, tweet2);
        int before = list.hashCode();
        List<Tweet> inTimespan = Filter.inTimespan(list, new Timespan(testStart, testEnd));
        int after = list.hashCode();
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
        assertEquals("don't change list!", before, after);
    }

    /*
     * covers:
     *       tweets.length() > 0
     *       result = 0
     * */
    @Test
    public void testInTimespanMultipleTweetsNoneResults() {
        Instant testStart = Instant.parse("2016-02-17T13:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T14:00:00Z");

        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));

        assertTrue("expected non-empty list", inTimespan.isEmpty());
    }

    /*
     * covers:
     *       tweets.length() = 0
     *       result = 0
     * */
    @Test
    public void testInTimespanNoneTweetsNoneResults() {
        Instant testStart = Instant.parse("2016-02-17T13:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T14:00:00Z");

        List<Tweet> inTimespan = Filter.inTimespan(Collections.emptyList(), new Timespan(testStart, testEnd));

        assertTrue("expected non-empty list", inTimespan.isEmpty());
    }

    /*
     * covers:
     *       tweets.length() > 0
     *       words.length() = 1
     *       result > 0
     *       not change list
     * */
    @Test
    public void testContainingMultipleTweetsMultipleResults() {
        List<Tweet> list = Arrays.asList(tweet1, tweet2);
        int before = list.hashCode();
        List<Tweet> containing = Filter.containing(list, Arrays.asList("talk"));
        int after = list.hashCode();

        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
        assertEquals("don't change list!", before, after);
    }

    /*
     * covers:
     *       tweets.length() > 0
     *       words.length() > 1
     *       result = 0
     * */
    @Test
    public void testContainingMultipleTweetsNoneResults() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("hello"));

        assertTrue("expected non-empty list", containing.isEmpty());
    }

    /*
     * covers:
     *       tweets.length() > 0
     *       words.length() > 1
     *       result > 0
     *       same order
     * */
    @Test
    public void testContainingMultipleTweetsMultipleWordsMultipleResults() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet2, tweet1), Arrays.asList("talk", "it"));

        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet2));
    }

    /*
     * covers:
     *       tweets.length() = 0
     *       result = 0
     * */
    @Test
    public void testContainingNoneTweetsNoneResults() {
        List<Tweet> containing = Filter.containing(Collections.emptyList(), Arrays.asList("talk", "it"));

        assertTrue("expected non-empty list", containing.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
