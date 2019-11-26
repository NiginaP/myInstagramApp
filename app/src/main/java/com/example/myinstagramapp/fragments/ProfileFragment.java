package com.example.myinstagramapp.fragments;


import android.util.Log;

import com.example.myinstagramapp.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends HomeFragment {
    private final static String TAG = "ProfileFragment";


    @Override
    protected void queryPosts(){
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);


        postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());

        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e!=null){
                    Log.e(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                allPosts.addAll(posts);
                postsAdapter.notifyDataSetChanged();
                for (Post post: posts)
                    Log.d(TAG, "Post: " + post.getDescription() + " username: " + post.getUser().getUsername());

            }
        });
    }


}
