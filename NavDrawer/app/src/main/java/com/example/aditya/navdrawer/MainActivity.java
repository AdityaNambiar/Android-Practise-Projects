package com.example.aditya.navdrawer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ImageView avatarImg;
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    ListView lv;
    private Context context;
    private Toolbar toolbar;


    private CustomAdapter myAdapter;

    Intent nextAct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*avatarImg = (ImageView) findViewById(R.id.avatar);
        avatarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextAct = new Intent(context, DashBoard.class);
                startActivity(nextAct);
            }
        });*/
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        lv = (ListView) findViewById(R.id.drawerList);

        myAdapter = new CustomAdapter(this);
        lv.setAdapter(myAdapter);
        /*toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_dehaze_black_24dp);
        setSupportActionBar(toolbar);*/

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setting ActionBarDrawerToggle object to do stuff that involves both, i.e. the nav drawer and action bar
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };

        lv.setOnItemClickListener(this);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,getString(R.string.toast3)+position,Toast.LENGTH_LONG).show();
        drawerLayout.closeDrawer(GravityCompat.START);

        nextAct = new Intent(this, Main2Activity.class);
        startActivity(nextAct);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        /*if(drawerLayout.isDrawerOpen(lv)){
            drawerLayout.closeDrawer(lv);
        }else {
            drawerLayout.openDrawer(lv);
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

}

class CustomAdapter extends BaseAdapter {

    private Context context;
    LayoutInflater inflater;
    List<String> listItems;
    int[] imgs = {R.drawable.ic_timeline_black_24dp,R.drawable.ic_assignment_ind_black_24dp};

    public CustomAdapter(Context context) {
        this.context = context;
        listItems = new ArrayList<String>(
                Arrays.asList(context.getResources().getStringArray(R.array.navDrawer))
        );
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //convertView=null;
        //if(convertView == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mylistrow, null);
       //}
        /*else
        {*/
        //}

        ImageView listicon = (ImageView) convertView.findViewById(R.id.listicon);
        TextView listitemtxt = (TextView) convertView.findViewById(R.id.listitemtxt);

        listitemtxt.setText(listItems.get(position));
        listicon.setImageResource(imgs[position]);


        return convertView;

    }

}