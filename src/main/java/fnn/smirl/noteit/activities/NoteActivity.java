package fnn.smirl.noteit.activities;
import android.os.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.view.*;
import fnn.smirl.noteit.*;
import fnn.smirl.noteit.fragments.*;
import fnn.smirl.noteit.utils.*;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import fnn.smirl.noteit.adapters.TabsAdapter;
import java.io.File;
import java.util.*;
import android.widget.*;
import android.widget.LinearLayout.*;

public class NoteActivity extends AppCompatActivity implements Constants {
	public String content = "";
	public boolean inEditMode = false;
	private Toolbar toolbar;
	private TabLayout tabs;
	private ViewPager pager;
	private TabsAdapter adapter;

	private EditorFragment editorFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_activity);
		init();
	}

	private void init() {
		initIds();
		initValues();
		setListeners();
		runInBackground();
	}

	private void initIds() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		tabs = (TabLayout) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
	}

	private void initValues() {
		adapter = new TabsAdapter(getSupportFragmentManager());
		editorFragment = new EditorFragment(this, R.mipmap.editor28px, null);
		adapter.add(editorFragment);
		DisplayerFragment df = new DisplayerFragment(this, R.mipmap.web28px, null);
		adapter.add(df);
		pager.setAdapter(adapter);
		tabs.setupWithViewPager(pager);
		adapter.setupWithTabLayout(tabs);
	}

	private void setListeners() {
		pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

				@Override
				public void onPageScrolled(int p1, float p2, int p3) {
					// TODO: Implement this method
				}

				@Override
				public void onPageSelected(int p1) {
					// TODO: Implement this method
					Fragment frag =	adapter.getItem(p1);
					if (frag instanceof DisplayerFragment) {
						DisplayerFragment df = (DisplayerFragment) frag;
						try {
							//df.loadUrl(content);
							//	snackIt(pager, MainActivity.getCurrentFile().toURL().toString(), 1000);
							df.loadUrl(MainActivity.getCurrentFile().toURL().toString());
							hideKeyboard(df.getWebView());
						} catch (Exception e) {}
					}
				}

				@Override
				public void onPageScrollStateChanged(int p1) {
					// TODO: Implement this method
				}
			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO: Implement this method
		getMenuInflater().inflate(R.menu.note_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO: Implement this method
		switch (item.getItemId()) {
			case R.id.na_save:

				if (MainActivity.getCurrentFile() != null) {
					saveNow(MainActivity.getCurrentFile(), content);
				} else {
					enterFileName(content);
				}
				break;
			case R.id.na_saveas:
				enterFileName(content);
				break;
			case R.id.na_delete:
				confirmDelete();
				break;
			case R.id.na_html_basic:
				loadTamplate(R.raw.html0);
				break;
			case R.id.na_html:
				loadTamplate(R.raw.html1);
				break;
			case R.id.na_html_advance:
				loadTamplate(R.raw.html2);
				break;
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO: Implement this method
		if (tabs.getSelectedTabPosition() != 0) {
			pager.setCurrentItem(0);
		} else super.onBackPressed();

	}



	private void enterFileName(final String content) {
		LinearLayout ll = new LinearLayout(this);
		ll.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		ll.setOrientation(LinearLayout.VERTICAL);
		TextInputLayout til = 
			new TextInputLayout(this);
		final EditText et1 = new EditText(this);
		et1.setHint("File Name");
		til.addView(et1);
		ll.addView(til);

		final Spinner formatSpinner = new Spinner(this);
		ll.addView(formatSpinner);
		ArrayAdapter<CharSequence> adaa = ArrayAdapter.createFromResource(this, R.array.file_format, android.R.layout.simple_spinner_item);
		adaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		formatSpinner.setAdapter(adaa);
		formatSpinner.setPrompt("Select format");

		AlertDialog dia = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle)
			.setTitle("Save as...")
			.setView(ll)
			.setPositiveButton("Save", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface p1, int p2) {
					String fname = et1.getText().toString();
					String ext = (String) formatSpinner.getSelectedItem();
					if (!ext.endsWith(".*")) {
						if (fname.contains("."))fname = fname.substring(0, fname.lastIndexOf("."));
						fname += ext;
					} else {
						if (!fname.contains("."))fname += ".txt";
					}
					File filee = new File(MainActivity.getCurrentDir(), fname);
					saveNow(filee, content);
				}
			})
			.create();
		if (MainActivity.getCurrentFile() != null) {
			et1.setText(MainActivity.getCurrentFile().getName());
		}
		dia.show();
	}

	private void saveNow(File file, String content) {
		boolean b = FileUtils.store(file, content);
		String vv = " saved successfully";
		if (b) {
			inEditMode = false;
			MainActivity.setCurrentFile(file);
		} else vv = " saving failed !";
		snackIt(toolbar, file.getName() + vv, 1000);
		getSupportActionBar().setSubtitle(MainActivity.getCurrentFile().getName());

	}

	private void confirmDelete() {
		if (MainActivity.getCurrentFile() != null && MainActivity.getCurrentFile().exists()) {
			AlertDialog dia = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle)
				.setMessage("Are you sure you want to delete " + MainActivity.getCurrentFile().getName() + "?")
				.setPositiveButton("Delete", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface p1, int p2) {
						boolean b =	MainActivity.getCurrentFile().delete();
						if (b)	snackIt(toolbar, "Deleted successfully", 1000);
						else snackIt(toolbar, "Deletion failed!", 1000);

						MainActivity.setCurrentFile(null);
						finish();
					}
				})
				.create();
			dia.show();
		}
	}

	private void snackIt(View v, String msg, int duration) {
		Snackbar.make(v, msg, duration).show();
	}

	private void hideKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		//imm.showSoftInput(v, 0);
	}

	private void adjustSubTitle(boolean b) {
		try {
			String _sub = getSupportActionBar().getSubtitle().toString();
			if (b) {
				if (!_sub.endsWith("*")) {
					getSupportActionBar().setSubtitle(_sub + "*");
				}
			} else {
				if (_sub.endsWith("*")) {
					getSupportActionBar().setSubtitle(_sub.replace("*", ""));
				}
			}
		} catch (Exception e) {}
	} 

	private void loadTamplate(int tamplateId) {
		//int resId = tamplateId == 0 ? R.raw.html0 : R.raw.html1;
		Scanner reader = new Scanner(getResources().openRawResource(tamplateId));
		StringBuilder builder = new StringBuilder();
		while (reader.hasNext()) {
			builder.append(reader.nextLine() + "\n");
		}
		editorFragment.setText(builder.toString().trim());
	}

	private void runInBackground() {
		final Handler handler = new Handler();
		runOnUiThread(new Runnable(){

				@Override
				public void run() {
					adjustSubTitle(inEditMode);
					handler.postDelayed(this, 1000);
				}
			});
	}
}
