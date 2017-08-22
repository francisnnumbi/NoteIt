package fnn.smirl.noteit.adapters;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import java.util.ArrayList;
import fnn.smirl.noteit.utils.IconTitleFragment;
import android.support.design.widget.TabLayout;

public class TabsAdapter extends FragmentPagerAdapter
{
	
	private ArrayList<Fragment> list;
	
	public TabsAdapter(FragmentManager fm){
		super(fm);
		list = new ArrayList<Fragment>();
	}

	@Override
	public Fragment getItem(int p1) {
		// TODO: Implement this method
		return list.get(p1);
	}

	@Override
	public int getCount() {
		// TODO: Implement this method
		return list.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO: Implement this method
		return ((IconTitleFragment)list.get(position)).getTitle();
	}
	
	public void add(IconTitleFragment newFragment){
		list.add(newFragment);
		notifyDataSetChanged();
	}

	public int getIcon(int position){
		return ((IconTitleFragment)list.get(position)).getIcon();
	}
	
	public void setupWithTabLayout(TabLayout tablayout){
		for(int i = 0; i < list.size(); i++){
			tablayout.getTabAt(i).setIcon(((IconTitleFragment)list.get(i)).getIcon());
		}
	}
}
