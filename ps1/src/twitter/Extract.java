/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Collectors;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        Instant min = tweets.get(0).getTimestamp();
        Instant max = tweets.get(0).getTimestamp();
        for (Tweet i : tweets) {
            long currentTimeStamp = Math.abs(Duration.between(i.getTimestamp(), Instant.EPOCH).getSeconds());
            long minTimeStamp = Math.abs(Duration.between(min, Instant.EPOCH).getSeconds());
            long maxTimeStamp = Math.abs(Duration.between(max, Instant.EPOCH).getSeconds());
           
            min = currentTimeStamp < minTimeStamp ? i.getTimestamp() : min;
            max = currentTimeStamp > maxTimeStamp ? i.getTimestamp() : max;
        }
        return new Timespan(min, max);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     *
     *
     *      * @return Twitter username who wrote this tweet.
     *      *         A Twitter username is a nonempty sequence of letters (A-Z or
     *      *         a-z), digits, underscore ("_"), or hyphen ("-").
     *      *         Twitter usernames are case-insensitive, so "jbieber" and "JBieBer"
     *      *         are equivalent.
     *      public String getAuthor()
     *
     */

    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        if (tweets == null) {
            throw new IllegalArgumentException("List of tweets cannot be null.");
        }
        Set<String> result = new HashSet<>();
        for (Tweet tweet : tweets) {
            if (tweet == null) {
                continue;
            }
            String text = tweet.getText();
            List<String> extractedText = extractText(text);
            if (extractedText.isEmpty()) {
                continue;
            }
            // 获取符合条件的用户名
            for (String extract : extractedText) {
                if (extract.equals("@")) {
                    continue;
                }
                String s = extractAuthor(extract);
                if (s != null) {
                    result.add(s);
                }
            }
        }
        return result;
    }

    // 提取以@开头的字符串
    private static List<String> extractText(String text) {
        String[] split = text.split("@");
        if (split.length > 1) {
            return new ArrayList<>(Arrays.asList(split).subList(1, split.length));
        } else {
            return Collections.emptyList();
        }
    }

    private static String extractAuthor(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '.' && i != text.length() - 1) {
                return null;
            } else if (text.charAt(i) == ' ' || text.charAt(i) == '\n' || text.charAt(i) == '\t' || text.charAt(i) == '\r' || text.charAt(i) == '.') {
                break;
            }
            stringBuilder.append(text.charAt(i));
        }
        String substring = stringBuilder.substring(0);
        return substring.toLowerCase();
    }
}
