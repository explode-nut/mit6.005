/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.*;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     * Testing strategy for guessFollowsGraph
     * Partition the input as follows:
     *          tweets.length(): 0 >0
     *          valid user: 0 >0
     *          capital and small divergence: true false
     *          mention himself: true false
     *
     * Testing strategy for influencers
     * Partition the input as follows:
     *          tweets.length(): 0 >0
     *          valid user: 0 >0
     *          result order obey default order if they have same followers
     */

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk czn@outlook.com in 30 @bbitdiddle minutes #hype.", d2);
    private static final Tweet tweet3 = new Tweet(3, "bbitdiddle", "rivest talk in 30 minutes @EXPLODE-NUT #hype @explode-nut", d2);
    private static final Tweet tweet4 = new Tweet(4, "alyssa", "is it reasonable to talk @Bob about rivest so much? czn@outlook.com", Instant.parse("2024-02-20T11:00:00Z"));
    private static final Tweet tweet5 = new Tweet(5, "alyssa", "is it reasonable to talk about rivest so much? @czn hello,world!", Instant.parse("2016-02-11T10:00:00Z"));
    private static final Tweet tweet6 = new Tweet(6, "czn", "is it reasonable to talk about @Bob rivest so much? @alyssa hello,world!", Instant.parse("2016-02-11T10:00:00Z"));

    // alyssa follow bob,czn
    // bbitdiddle follow explode-nut
    // czn follow bob,alyssa

    // alyssa has 1
    // bob has 2
    // czn has 1
    // explode-nut has 1
    // bbitdiddle has 0

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    /*
    * covers:
    *       tweets.length(): 0
    *       valid user: 0
    * */
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    /*
     * covers:
     *       tweets.length(): >0
     *       valid user: 0
     *       mention himself: true
     * */
    @Test
    public void testGuessFollowsGraphMultipleTweetsEmptyResult() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2));

        assertFalse("not expected empty graph", followsGraph.isEmpty());
        assertEquals("alyssa should follow 0 user", 0, followsGraph.get("alyssa").size());
        assertEquals("bbitdiddle should follow 0 user", 0, followsGraph.get("bbitdiddle").size());
    }

    /*
     * covers:
     *       tweets.length(): >0
     *       valid user: >0
     *       capital and small divergence: true
     * */
    @Test
    public void testGuessFollowsGraphMultipleTweetsMultipleResult() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5, tweet6));

        assertFalse("not expected empty graph", followsGraph.isEmpty());
        assertEquals("alyssa should follow 1 user", 2, followsGraph.get("alyssa").size());
        assertEquals("bbitdiddle should follow 1 user", 1, followsGraph.get("bbitdiddle").size());
        assertEquals("czn should follow 2 user", 2, followsGraph.get("czn").size());
    }

    /*
     * covers:
     *       tweets.length(): 0
     *       valid user: 0
     * */
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }

    /*
     * covers:
     *       tweets.length(): 0
     *       valid user: 0
     * */
    @Test
    public void testInfluencersMultipleTweetsEmptyResult() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2));
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertFalse("not expected empty list", influencers.isEmpty());
        assertEquals("expected 2 element", 2, influencers.size());
        assertEquals("expected right order", "alyssa", influencers.get(0));
    }

    /*
     * covers:
     *       tweets.length(): >0
     *       valid user: >0
     * */
    @Test
    public void testInfluencersMultipleTweetsMultipleResult() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5, tweet6));
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertEquals("expected 5 element", 5, influencers.size());
        assertEquals("expected bob in correct index 0,because he has most followers", "bob", influencers.get(0));
        assertEquals("expected alyssa in correct index 1", "alyssa", influencers.get(1));
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
