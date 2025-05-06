package com.crack.snap.make.medium.priorityqueue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
 * getNewsFeed should be a bit tricky, we need to get the tweets from the user and the tweets from the followers and then sort them by timestamp and return the top 10
 *
 * * @author Mohan Sharma
 */
public class DesignTwitter {

    private final Map<Integer, List<Tweet>> userTweets;
    private final Map<Integer, Set<Integer>> userFollowers;

    public DesignTwitter() {
        userTweets = new HashMap<>();
        userFollowers = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        userTweets.computeIfAbsent(userId, key -> new ArrayList<>()).add(new Tweet(tweetId, LocalDateTime.now()));
    }

    public List<Integer> getNewsFeed(int userId) {
        var ownTweets = userTweets.getOrDefault(userId, new ArrayList<>());
        var allTweets = new ArrayList<>(ownTweets);

        var followers = userFollowers.getOrDefault(userId, new HashSet<>());
        for (var follower : followers) {
            var followerTweets = userTweets.getOrDefault(follower, new ArrayList<>());
            allTweets.addAll(followerTweets);
        }
        var result = new ArrayList<Integer>();
        if (allTweets.isEmpty()) {
            return result;
        }
        allTweets.sort((a, b) -> b.timestamp.compareTo(a.timestamp));
        for (int i = 0; i < Math.min(10, allTweets.size()); i++) {
            result.add(allTweets.get(i).tweetId);
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

    record Tweet(int tweetId, LocalDateTime timestamp) {

    }

    public static void main(String[] args) {
        var twitter = new DesignTwitter();
        twitter.postTweet(1, 5);
        System.out.println(twitter.getNewsFeed(1)); // Output: [5]
        twitter.follow(1, 2);
        twitter.postTweet(2, 6);
        System.out.println(twitter.getNewsFeed(1)); // Output: [6, 5]
        twitter.unfollow(1, 2);
        System.out.println(twitter.getNewsFeed(1)); // Output: [5]
        twitter.postTweet(3, 5);
        twitter.postTweet(3, 3);
        System.out.println(twitter.getNewsFeed(3));
    }
}
