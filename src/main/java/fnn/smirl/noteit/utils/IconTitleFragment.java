package fnn.smirl.noteit.utils;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.BundleUtil;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;

public class IconTitleFragment extends Fragment
{
	protected AppCompatActivity activity;
	public IconTitleFragment(){}
	public IconTitleFragment(AppCompatActivity activity, int iconId, String title){
		Bundle b = new Bundle();
		b.putString("title", title);
		b.putInt("icon", iconId);
		setArguments(b);
		this.activity = activity;
	}
	
	public ActionBar getSupportActionBar(){
		return activity.getSupportActionBar();
	}
	
	public void setTitle(String title){
		getArguments().putString("title", title);
	}
	
	public String getTitle(){
		return getArguments().getString("title");
	}
	
	public void setIcon(int iconId){
		getArguments().putInt("icon", iconId);
	}
	
	public int getIcon(){
		return getArguments().getInt("icon");
	}
}
