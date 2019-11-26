package com.example.myinstagramapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myinstagramapp.Post;
import com.example.myinstagramapp.PostsAdapter;
import com.example.myinstagramapp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private final static String TAG = "HomeFragment";
    RecyclerView rvPosts;
    protected PostsAdapter postsAdapter;
    protected List<Post> allPosts;
    private SwipeRefreshLayout swipeContainer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync();
                postsAdapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }
        });

        rvPosts = view.findViewById(R.id.rvPosts);

        allPosts = new ArrayList<Post>();
        postsAdapter = new PostsAdapter(getContext(),allPosts);
        rvPosts.setAdapter(postsAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();



    }

    public void fetchTimelineAsync() {

        queryPosts();

        // Remember to CLEAR OUT old items before appending in the new ones
        postsAdapter.clear();
        // ...the data has come back, add new items to your adapter...
        postsAdapter.addAll(allPosts);
        // Now we call setRefreshing(false) to signal refresh has finished

    }





    protected void queryPosts(){
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
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
