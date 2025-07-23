package com.crack.snap.make.medium.priorityqueue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Problem: Design a simplified version of Twitter where users can post tweets, follow/unfollow another user, and is able to see
 * the 10 most recent tweets in the user's news feed.
 * Implement the Twitter class:
 * Twitter() Initializes your twitter object.
 * void postTweet(int userId, int tweetId) Composes a new tweet with ID tweetId by the user userId. Each call to this function will be made with a unique tweetId.
 * List<Integer> getNewsFeed(int userId) Retrieves the 10 most recent tweet IDs in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user themself. Tweets must be ordered from most recent to least recent.
 * void follow(int followerId, int followeeId) The user with ID followerId started following the user with ID followeeId.
 * void unfollow(int followerId, int followeeId) The user with ID followerId started unfollowing the user with ID followeeId.
 *
 * Intuition: Each user can have a list of tweets, let's have a dictionary to map userId to a list of tweets.
 * Also, each user can have a list of followers, let's have a dictionary to map userId to a set of followers.
 * postTweet should be pretty simple, we just need to add the tweet to the list of tweets for the user.
 * follow and unfollow should also be simple, we just need to add or remove the user from the list of followers for the user.
 * getNewsFeed: Since we will use a list to store the tweets for a user, the latest tweet will always be at the end of the list
 * What if we get all the latest tweets of the user and its followers from the end of the list first and store them in a max heap
 *  * @author Mohan Sharma
 */
public class DesignTwitterOptimized {

    private final Map<Integer, List<Tweet>> userTweets;
    private final Map<Integer, Set<Integer>> userFollowers;

    public DesignTwitterOptimized() {
        userTweets = new HashMap<>();
        userFollowers = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        userTweets.computeIfAbsent(userId, key -> new ArrayList<>()).add(new Tweet(userId, tweetId, LocalDateTime.now()));
    }

    public List<Integer> getNewsFeed(int userId) {
        var allUsers = new HashSet<Integer>();
        allUsers.add(userId);
        allUsers.addAll(userFollowers.getOrDefault(userId, new HashSet<>()));
        var maxHeap = new PriorityQueue<>(10, Comparator.comparing(Tweet::timestamp).reversed());

        var indexTracker = new HashMap<Integer, Integer>();
        for (var user: allUsers) {
            var tweets = userTweets.getOrDefault(user, new ArrayList<>());
            if (!tweets.isEmpty()) {
                int lastIndex = tweets.size() - 1;
                maxHeap.add(tweets.get(lastIndex));
                indexTracker.put(user, lastIndex - 1);
            }
        }
        var result = new ArrayList<Integer>();
        while (!maxHeap.isEmpty() && result.size() < 10) {
            var tweet = maxHeap.poll();
            result.add(tweet.tweetId);
            int nextIndex = indexTracker.getOrDefault(tweet.userId, -1);

            if (nextIndex >= 0) {
                var tweets = userTweets.getOrDefault(tweet.userId, new ArrayList<>());
                maxHeap.add(tweets.get(nextIndex));
                indexTracker.put(tweet.userId, nextIndex - 1);
            }
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if (followerId == followeeId) {
            return;
        }
        userFollowers.computeIfAbsent(followerId, key -> new HashSet<>()).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (followerId == followeeId) {
            return;
        }
        var followers = userFollowers.get(followerId);
        if (followers != null) {
            followers.remove(followeeId);
        }
    }

    record Tweet(int userId, int tweetId, LocalDateTime timestamp) {}

    public static void main(String[] args) {
        var twitter = new DesignTwitterOptimized();
        twitter.postTweet(1, 5);
        System.out.println(twitter.getNewsFeed(1)); // Output: [5]
        twitter.follow(1, 2);
        twitter.postTweet(2, 6);
        System.out.println(twitter.getNewsFeed(1)); // Output: [6, 5]
        twitter.unfollow(1, 2);
        System.out.println(twitter.getNewsFeed(1)); // Output: [5]
        twitter.postTweet(3, 5);
        twitter.postTweet(3, 3);
        System.out.println(twitter.getNewsFeed(3)); // Output: [3, 5]
        //["Twitter","postTweet","follow","follow","getNewsFeed"]
        // [[],[2,5],[1,2],[1,2],[1]]
        twitter.postTweet(4, 5);
        twitter.follow(5, 4);
        twitter.follow(5, 4);
        System.out.println(twitter.getNewsFeed(5)); // Output: [5]
    }
}
