package com.example.imazjav0017.instaclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parse.ParseUser;

public class TopPosts extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
      if(item.getItemId()==R.id.shareImage)
      {
          return true;
      }
        else if(item.getItemId()==R.id.logout)
      {
          ParseUser.logOut();
          Intent i=new Intent(getApplicationContext(),MainActivity.class);
          startActivity(i);
          return true;
      }
        else if(item.getItemId()==R.id.browse)
      {
          Intent i=new Intent(getApplicationContext(),BrowseUsers.class);
          startActivity(i);

          return true;
      }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_posts);
        setTitle("All Posts");
    }
}
