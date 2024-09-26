/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        if (tweets == null || tweets.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, Set<String>> result = new HashMap<>();
        for (Tweet tweet : tweets) {
            Set<String> users = Extract.getMentionedUsers(Collections.singletonList(tweet));
            Set<String> resultSet = users.stream()
                    .filter(user -> !tweet.getAuthor().equals(user)) // filter是筛选出符合条件的元素
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());
            resultSet.remove("null");
            if (result.containsKey(tweet.getAuthor())) {
                result.get(tweet.getAuthor()).addAll(resultSet);
            } else {
                result.put(tweet.getAuthor(), resultSet);
            }

        }

        return result;
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        if (followsGraph == null || followsGraph.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        Map<String, Integer> followersMap = new HashMap<>();
        List<Node> tmp = new ArrayList<>();
        followsGraph.forEach((k, v) -> {
            v.forEach((i) -> {
                if (followersMap.containsKey(i)) {
                    followersMap.put(i, followersMap.get(i) + 1);
                } else {
                    followersMap.put(i, 1);
                }
            });
        });
        if (followersMap.size() != followsGraph.size()) {
            followsGraph.forEach((k, v) -> {
                if (!followersMap.containsKey(k)) {
                    followersMap.put(k, 0);
                }
            });
        }
        followersMap.forEach((k, v) -> {
            tmp.add(new Node(k, v));
        });
        tmp.sort(new NodeComparator());
        for (Node node : tmp) {
            result.add(node.getName());
        }

        return result;
    }

    private static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            if (o1.getFollowers() == o2.getFollowers()) {
                return o1.getName().charAt(0) - o2.getName().charAt(0);
            }
            return o2.getFollowers() - o1.getFollowers();
        }
    }

    private static class Node {
        private final String name;
        private final int followers;

        public Node(String name, int followers) {
            this.name = name;
            this.followers = followers;
        }

        public String getName() {
            return name;
        }

        public int getFollowers() {
            return followers;
        }
    }
}
