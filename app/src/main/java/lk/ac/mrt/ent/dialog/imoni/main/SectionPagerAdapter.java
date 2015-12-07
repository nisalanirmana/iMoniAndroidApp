package lk.ac.mrt.ent.dialog.imoni.main;

/**
 * @author Anuruddha Hettiarachchi
 *
 */

import lk.ac.mrt.ent.dialog.imoni.events.EventsSectionFragment;
import lk.ac.mrt.ent.dialog.imoni.racu.RACUSectionFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SectionPagerAdapter extends FragmentStatePagerAdapter {
	
	public static EventsSectionFragment mEventsFragment;
	public static RACUSectionFragment mRACUFragment;
	
	public SectionPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
		mEventsFragment = new EventsSectionFragment();
		mRACUFragment = new RACUSectionFragment();
	}
  
	public int getCount() {
		return 2;
	}
  
	public Fragment getItem(int position) {
		switch (position) {
			case 0: 
				return mEventsFragment;
			case 1:
				return mRACUFragment;
			default:
				return mEventsFragment;
		}
	}
  
	public int getItemPosition(Object object) {
		return -2;
	}
  
	public CharSequence getPageTitle(int paramInt) {
		switch (paramInt) {
			case 0: 
				return "Events";
			case 1:
				return "RACU";
			default: 
				return null;
		}
	}
}