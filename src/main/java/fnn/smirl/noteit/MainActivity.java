package fnn.smirl.noteit;

import android.content.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import fnn.smirl.noteit.activities.*;
import fnn.smirl.noteit.utils.*;
import java.io.*;
import java.util.*;
import fnn.smirl.noteit.adapters.*;
import android.graphics.*;

public class MainActivity extends AppCompatActivity implements Constants {
	ListView main_list;
	private static File currFile, currDir;
	private ImageButton back_list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
	}

	private void init() {
		initIds();
		initValues();
		setListeners();
	}

	private void initIds() {
		back_list = (ImageButton) findViewById(R.id.main_ib_back);
		main_list = (ListView) findViewById(R.id.main_list);
	}


	private void initValues() {
		if (!APP_HOME_DIR.exists())APP_HOME_DIR.mkdirs();
		currDir = APP_HOME_DIR;
	}

	private void setListeners() {
		main_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

				@Override
				public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4) {
					currFile = ((FileWrapper) main_list.getItemAtPosition(p3)).getFile();
					blowListPop(p2);
					return true;
				}
			});

		main_list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
					currFile = ((FileWrapper) main_list.getItemAtPosition(p3)).getFile();
					if (currFile.isFile()) {
						loadActivity();
					}else{
						currDir = currFile;
						refreshList();
						setSubTitle();
					}
				}
			});

		getOptionActions();
	}

	private void getOptionActions() {
		back_list.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					if (!currDir.getAbsolutePath().equalsIgnoreCase(APP_HOME_DIR.getAbsolutePath())) {
						currDir = currDir.getParentFile();
						refreshList();
						setSubTitle();
					}
				}
			});

	}

	public static File getCurrentDir() {
		return currDir;
	}

	public static File getCurrentFile() {
		return currFile;
	}

	public static void setCurrentFile(File file) {
		currFile = file;
	}

	private void createNewFolder() {
		final EditText nf_ed = new EditText(this);
		AlertDialog dia = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle)
			.setTitle("New Folder")
			.setView(nf_ed)
			.setPositiveButton("Create", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2) {
					File f = new File(currDir, nf_ed.getText().toString());
					if (!f.exists())f.mkdirs();
					else if (f.exists() && !f.isDirectory())f.mkdir();
					else {
						confirmFolderCreationReplacement(f);
					}
					refreshList();
				}
			})
			.create();
		dia.show();
	}

	private void confirmFolderCreationReplacement(final File folder) {
		AlertDialog dia = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle)
			.setMessage("Directory " + folder.getName() + " exists.\n Do you want to replace it?")
			.setPositiveButton("Replace", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2) {
					folder.mkdir();
				}
			})
			.create();
		dia.show();
	}

	private void confirmDeletion(final File file) {
		AlertDialog dia = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle)
			.setMessage("You are about to delete " + currFile.getName() + ".\nAre you sure you want to do it?\nThis action cannot be reversed!!")
			.setPositiveButton("Delete", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2) {
					file.delete();
					currFile = null;
					refreshList();
				}
			})
			.create();
		dia.show();
	}

	private void refreshList() {
		File[] files = currDir.listFiles();
		if (files != null) {
			ArrayList<FileWrapper> list = new ArrayList<FileWrapper>();
			ArrayList<FileWrapper> listf = new ArrayList<FileWrapper>();
			for (File f : files) {
				if (f.isDirectory())list.add(new FileWrapper(f));
				else {listf.add(new FileWrapper(f));}
			}
			Collections.sort(list);
			Collections.sort(listf);
			list.addAll(listf);
			MyAdapter adapter = new MyAdapter(getBaseContext(), list);
			main_list.setAdapter(adapter);
		}
		setSubTitle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO: Implement this method
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO: Implement this method
		switch (item.getItemId()) {
			case R.id.mm_new_file:
				//snackIt(main_list, APP_HOME_DIR.getAbsolutePath(), 2000);
				currFile = null;
				loadActivity();
				break;
			case R.id.mm_new_folder:
				createNewFolder();
				refreshList();
				setSubTitle();
				break;
		}
		return true;
	}

	@Override
	protected void onResume() {
		// TODO: Implement this method
		super.onResume();
		refreshList();
	}

	private void loadActivity() {
		startActivity(new Intent(getBaseContext(), NoteActivity.class));
	}

	private void setSubTitle() {
		getSupportActionBar().setSubtitle(currDir.getAbsolutePath().replace(ROOT_HOME.getAbsolutePath(), ""));
	}

	private void blowListPop(View v) {
		PopupMenu pop = new PopupMenu(this, v);
		pop.getMenuInflater().inflate(R.menu.blow_list_pop, pop.getMenu());

		pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

				@Override
				public boolean onMenuItemClick(MenuItem p1) {
					// TODO: Implement this method
					switch (p1.getItemId()) {
						case R.id.blp_delete:
							confirmDeletion(currFile);
							break;
						case R.id.blp_open:
							if (currFile.isFile()) {
								loadActivity();
							} else {
								currDir = currFile;
								refreshList();
								setSubTitle();
							}
							break;
					}
					return false;
				}
			});
		pop.show();
	}

	private void snackIt(View v, String msg, int duration) {
		Snackbar.make(v, msg, duration).show();
	}
}
