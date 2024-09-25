/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     *
     * Testing strategy for getTimespan
     * Partition the input as follows:
     * tweets.length(): 1, >1
     * tweets similarity: none of tweet are similar, all tweets are similar, part of tweet are similar.
     *
     * Testing strategy for getMentionedUsers
     * Partition the input as follows:
     * valid mention: 0, >0
     * capital and small letter : same mentions but presented in full capital and small letter,
     *                            same mentions but presented in partial capital and small letter,
     *                              same mentions in full capital or small letter.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype @explode-nut.", d2);
    private static final Tweet tweet3 = new Tweet(3, "bbitdiddle", "rivest talk in 30 minutes @EXPLODE-NUT #hype @explode-nut", d2);
    private static final Tweet tweet4 = new Tweet(4, "alyssa", "is it reasonable to talk about rivest so much? czn@outlook.com", Instant.parse("2024-02-20T11:00:00Z"));
    private static final Tweet tweet5 = new Tweet(5, "alyssa", "is it reasonable to talk about rivest so much? @czn hello,world!", Instant.parse("2016-02-11T10:00:00Z"));
    private static final Tweet tweet6 = new Tweet(6, "alyssa", "is it reasonable to talk about @EXPLODE-NUT rivest so much? @explode-NUT hello,world!", Instant.parse("2016-02-11T10:00:00Z"));

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // covers tweets.length = 1
    @Test
    public void testGetTimespanOneTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }

    // covers tweets.length > 1
    //        part of tweets are similar
    @Test
    public void testGetTimespanMoreTweets() {

        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet1, tweet3, tweet4, tweet5));
        
        assertEquals("expected start", Instant.parse("2016-02-11T10:00:00Z"), timespan.getStart());
        assertEquals("expected end", Instant.parse("2024-02-20T11:00:00Z"), timespan.getEnd());
    }

    // covers tweets.length > 1
    //        all tweets are similar
    @Test
    public void testGetTimespanAllEqualTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }

    // covers Number of mentioned user = 0
    // valid mention: 0
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    // covers Number of mentioned user > 0
    //        tweets.length() > 1
    //        valid mention > 0
    @Test
    public void testGetMentionedUsersManyValidMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2));

        assertEquals(1,mentionedUsers.size());

    }

    // covers
    //        tweets.length() > 1
    //        valid mention = 0
    @Test
    public void testGetMentionedUsersNoValidMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1,tweet4));

        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    // covers
    //        tweets.length() > 1
    //        valid mention = 1
    //        same mentions but presented in full capital and small letter
    @Test
    public void testGetMentionedUsersDifferentCaseMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet3));

        assertEquals(1, mentionedUsers.size());

    }

    // covers
    //        tweets.length() > 1
    //        valid mention > 0
    //        same mentions but presented in partial capital and small letter,
    @Test
    public void testGetMentionedUsersDifferentPartialCaseMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet6));
        assertEquals(1, mentionedUsers.size());

    }


    // covers
    //        tweets.length() > 1
    //        valid mention > 0
    //        same mentions but presented in partial capital and small letter,
    @Test
    public void testGetMentionedUsersManyMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3,tweet4,tweet5,tweet6));
        assertEquals(2, mentionedUsers.size());

    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
