package com.prateek.swiggy.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.prateek.swiggy.R;
import com.prateek.swiggy.event.GroupItemClickEvent;
import com.prateek.swiggy.event.VariantItemClickEvent;
import com.prateek.swiggy.rest.Request.GroupChoice;
import com.prateek.swiggy.rest.Request.PizzaResponse;
import com.prateek.swiggy.rest.Request.Variation;
import com.prateek.swiggy.rest.RestClient;
import com.prateek.swiggy.ui.MainFragment;
import com.prateek.swiggy.ui.adapter.GroupViewAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements MainFragment.GetDataInterface {

    RecyclerView mGroupList;
    FrameLayout mMainLayout;
    private DrawerLayout mDrawerLayout;
    private ProgressDialog mProgressDialog;
    private PizzaResponse mPizzaResponseList;
    private GroupViewAdapter mGroupViewAdapter;

    private ArrayList<GroupChoice> selectedValues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.request_progress));
        mProgressDialog.setCancelable(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        mGroupList = (RecyclerView) findViewById(R.id.main_recycler_group);
        mGroupList.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mGroupList.setLayoutManager(mLayoutManager);

        mMainLayout = (FrameLayout) findViewById(R.id.main_layout_content);

        if (mPizzaResponseList == null) {
            this.eventsCall();
        } else {
            updateView();
        }
    }

    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        // First uncheck all menu items
                        Menu menu = navigationView.getMenu();
                        menu.size();

                        for (int count = 0; count < menu.size(); count++) {
                            MenuItem item = menu.getItem(count);
                            if (item != null) {
                                item.setChecked(false);
                            }
                        }

                        menuItem.setChecked(true);

                        mDrawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                // mRestarantsList = PreferenceManager.fetchEvents();
                                updateView();
                                Toast.makeText(MainActivity.this, "HOME", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_favourites:
                                // MainActivity.this.startActivity(new Intent(MainActivity.this, DetailsActivity.class));
                                // displayFavourite();
                                Toast.makeText(MainActivity.this, "FAV", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_statistics:
                                Toast.makeText(MainActivity.this, "Stats", Toast.LENGTH_SHORT).show();
                                break;
                        }

                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_list, menu);
        return true;
    }

    private void updateView() {

        if (mGroupViewAdapter == null) {
            mGroupViewAdapter = new GroupViewAdapter();
        }

        mGroupViewAdapter.refreshWithData(mPizzaResponseList.getVariants().getVariantGroups());
        mGroupList.setAdapter(mGroupViewAdapter);
    }

    private void eventsCall() {

        mProgressDialog.show();

        // Initialize the task to fetch events
        RestClient.Implementation.getClient().getCustomizeOptions().enqueue(new Callback<PizzaResponse>() {
            @Override
            public void onResponse(Call<PizzaResponse> call, Response<PizzaResponse> response) {

                if (response.body() != null) {
                    mPizzaResponseList = response.body();
                }

                MainActivity.this.updateView();

                Snackbar.make(mMainLayout, R.string.request_success, Snackbar.LENGTH_LONG).show();
                mProgressDialog.hide();

                switchFragment(0);
            }

            @Override
            public void onFailure(Call<PizzaResponse> call, Throwable t) {
                Snackbar.make(mGroupList, R.string.request_error, Snackbar.LENGTH_LONG).show();
                mProgressDialog.hide();
            }
        });
    }


    public void switchFragment(int index) {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MainFragment mainFragment = MainFragment.newInstance(mPizzaResponseList.getVariants().getVariantGroups().get(index));
        fragmentTransaction.replace(R.id.main_layout_content, mainFragment);
        fragmentTransaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_refresh:
                this.eventsCall();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGroupViewClick(GroupItemClickEvent clickEvent) {
        switchFragment(clickEvent.getIndex());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVariantViewClick(VariantItemClickEvent clickEvent) {

        // Multiple ways to handle this, but for limited time, this could work
        for (Variation variation : mPizzaResponseList.getVariants().getGroup(clickEvent.getGroupChoice().getGroupId()).getVariations()) {
            if (variation.id.equalsIgnoreCase(clickEvent.getGroupChoice().getVariationId())) {
                variation.setSelected(true);
            } else {
                variation.setSelected(false);
            }
        }

        for (GroupChoice choice : selectedValues) {
            if (clickEvent.getGroupChoice().getGroupId().equalsIgnoreCase(choice.getGroupId())) {
                selectedValues.remove(choice);
                break;
            }
        }
        selectedValues.add(clickEvent.getGroupChoice());
    }

    @Override
    public ArrayList<ArrayList<GroupChoice>> getDataList() {
        return mPizzaResponseList.getVariants().excludeList;
    }

    @Override
    public ArrayList<GroupChoice> getSelectedValues() {
        return selectedValues;
    }
}
