package fnn.smirl.noteit.fragments;
import android.graphics.*;
import android.os.*;
import android.support.v7.app.*;
import android.text.*;
import android.util.*;
import android.view.*;
import android.view.ScaleGestureDetector.*;
import fnn.smirl.noteit.*;
import fnn.smirl.noteit.activities.*;
import fnn.smirl.noteit.utils.*;
import fnn.smirl.lnedit.view.*;

public class EditorFragment extends IconTitleFragment {

	LineNumberedEditText note_et;

	private ScaleGestureDetector scaleGestureDetector;

	public EditorFragment(AppCompatActivity activity, int iconId, String title) {
		super(activity, iconId, title);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO: Implement this method
		return inflater.inflate(R.layout.editor_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onViewCreated(view, savedInstanceState);
		init(view);
	}
	private void init(View view) {
		initIds(view);
		initValues();
		setListeners();
		//runInBackground();
	}

	private void initIds(View view) {
		note_et = (LineNumberedEditText) view.findViewById(R.id.note_et);
		note_et.setLineNumberVisible(true);
		note_et.setLineNumberColor(Color.RED);

		scaleGestureDetector = new ScaleGestureDetector(getContext(), scaleGesterListener);
		setHasOptionsMenu(true);

	}

	private void initValues() {
		if (MainActivity.getCurrentFile() != null) {
			String txt = FileUtils.load(MainActivity.getCurrentFile());
			((NoteActivity)activity).content = txt;
			note_et.setText(txt);
			getSupportActionBar().setSubtitle(MainActivity.getCurrentFile().getName());
		}
	}
	
	public void setText(String text){
		note_et.setText(text);
	}

	private void setListeners() {
		note_et.addTextChangedListener(new TextWatcher(){

				@Override
				public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
					// TODO: Implement this method
				}

				@Override
				public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
					// TODO: Implement this method
					((NoteActivity)activity).inEditMode = true;
					((NoteActivity)activity).content = p1.toString();

				}

				@Override
				public void afterTextChanged(Editable p1) {
					// TODO: Implement this method

				}
			});

		note_et.setOnTouchListener(new View.OnTouchListener(){

				@Override
				public boolean onTouch(View p1, MotionEvent p2) {
					// TODO: Implement this method
					scaleGestureDetector.onTouchEvent(p2);
					return p1.performClick();
				}
			});
	}
	
	SimpleOnScaleGestureListener scaleGesterListener = new SimpleOnScaleGestureListener(){
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			float size = note_et.getTextSize();
			float factor = detector.getScaleFactor();
			float product = size * factor;
			note_et.setTextSize(TypedValue.COMPLEX_UNIT_PX, product);
			return true;
		}
	};
}
