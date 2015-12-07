package lk.ac.mrt.ent.dialog.imoni.main;

/**
 * @author Anuruddha Hettiarachchi
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lk.ac.mrt.ent.dialog.imoni.web.ConnectWS;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements TabListener {
	  public static ArrayList<String> list = new ArrayList<String>();
	  private static Context mContext;
	  public static ExpandableListView mDrawerListView;
	  public static HashMap<String, List<String>> mGroupChildMap;
	  public static List<String> mGroups;
	  public static ProgressDialog mProgressDialog;
	  public static ViewPager mViewPager;
	  public static int mSelectedTab;
	  DrawerLayout mDrawerLayout;
	  SectionPagerAdapter mSectionPagerAdapter;
	  
	  
	  int count = 0;
  
	  public static void SetExpandableDrawerAdapter() {
		  DrawerListAdapter drawerListAdapter = new DrawerListAdapter(mContext, mGroups, mGroupChildMap);
		  mDrawerListView.setAdapter(drawerListAdapter);
	  }
  
	  public static void ShowToast(String message) {
		  Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
	  }
  
	  protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.activity_main);
		  mContext = getApplicationContext();
		  mProgressDialog = new ProgressDialog(this);
		  mProgressDialog.setTitle("Please wait.mProcess");
		  mDrawerLayout = ((DrawerLayout)findViewById(R.id.drawer_layout));
		  mDrawerListView = (ExpandableListView)mDrawerLayout.findViewById(R.id.drawer_list);
		  mDrawerLayout.setDrawerListener(new DrawerListener() {
		      public void onDrawerClosed(View view) {}      
		      public void onDrawerOpened(View view) {
		    	  ConnectWS.getRACUMap(Variables.mComPrefix);
		      }      
		      public void onDrawerSlide(View view, float slideOffset) {}      
		      public void onDrawerStateChanged(int newState) {}
		  });
		  mDrawerListView.setOnGroupClickListener(new OnGroupClickListener(){
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				if(groupPosition == 0){
					Variables.mSelectedRACU = "";
					Variables.mSelectedRACUGroup = "";
					mSectionPagerAdapter.notifyDataSetChanged(); //TODO: Optimize
					mDrawerLayout.closeDrawers();
				} else{
					
				}
				return false;
			}
		  });
		  mDrawerListView.setOnChildClickListener(new OnChildClickListener() {
			  public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				  Variables.mSelectedRACU = mGroupChildMap.get(mGroups.get(groupPosition)).get(childPosition);
				  Variables.mSelectedRACUGroup = mGroups.get(groupPosition);
				  mSectionPagerAdapter.notifyDataSetChanged(); //TODO: Optimize
				  mDrawerLayout.closeDrawers();
				  return true;
	      }
		  });
		  final ActionBar actionBar = getActionBar();
		  actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		  mViewPager = ((ViewPager)findViewById(R.id.pager));
		  mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
		  mViewPager.setAdapter(this.mSectionPagerAdapter);
		  mViewPager.setOnPageChangeListener(new SimpleOnPageChangeListener() {
			  public void onPageSelected(int position) {
				  actionBar.setSelectedNavigationItem(position);
			  }
		  });
		  for (int i = 0;; i++) {
			  if (i >= this.mSectionPagerAdapter.getCount()) {
				  return;
			  }
			  actionBar.addTab(actionBar.newTab().setText(mSectionPagerAdapter.getPageTitle(i)).setTabListener(this));
		  }
	  }
  
	  public boolean onCreateOptionsMenu(Menu menu) {
		  getMenuInflater().inflate(R.menu.main, menu);
		  return true;
	  }
  
	  public void onTabReselected(Tab tab, FragmentTransaction ft) {}
  
	  public void onTabSelected(Tab tab, FragmentTransaction ft) {
		  mSelectedTab = tab.getPosition();
		  android.support.v4.app.FragmentTransaction ftv4 = getSupportFragmentManager().beginTransaction();
		  if(mSectionPagerAdapter.getItem(tab.getPosition()).isDetached()){
			  ftv4.attach(mSectionPagerAdapter.getItem(tab.getPosition()));
		  }
		  ftv4.commit();
		  mViewPager.setCurrentItem(tab.getPosition());
	  }
  
	  public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		  android.support.v4.app.FragmentTransaction ftv4 = getSupportFragmentManager().beginTransaction();
		  ftv4.detach(mSectionPagerAdapter.getItem(tab.getPosition()));
		  ftv4.commit();
	  }
  
}