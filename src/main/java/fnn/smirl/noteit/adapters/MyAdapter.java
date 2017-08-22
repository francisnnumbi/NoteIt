package fnn.smirl.noteit.adapters;
import android.widget.ArrayAdapter;
import android.content.Context;
import java.util.ArrayList;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import fnn.smirl.noteit.utils.*;
import fnn.smirl.noteit.*;
import android.widget.*;

public class MyAdapter extends ArrayAdapter<FileWrapper> {
	public MyAdapter(Context context, ArrayList<FileWrapper> list) {
		super(context, 0, list);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO: Implement this method
		FileWrapper myFile = getItem(position);
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_model, parent, false);
			holder.imgView = (ImageView) convertView.findViewById(R.id.list_modelImageView);
			holder.tv1 = (TextView) convertView.findViewById(R.id.listmodelTextView1);
			holder.tv2 = (TextView) convertView.findViewById(R.id.listmodelTextView2);
			holder.tv3 = (TextView) convertView.findViewById(R.id.listmodelTextView3);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.imgView.setImageResource(myFile.getIconId());
		holder.tv1.setText(myFile.getName());
		holder.tv2.setText(AppUtils.getDate(myFile.getLastModified()));
		holder.tv3.setText(AppUtils.getReadableSize(myFile.getFileSize()));

		return convertView;
	}

	private class Holder {
		TextView tv1, tv2, tv3;
		ImageView imgView;
	}

}
