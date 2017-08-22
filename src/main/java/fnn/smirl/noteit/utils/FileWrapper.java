package fnn.smirl.noteit.utils;
import java.io.File;
import android.content.*;
import fnn.smirl.noteit.*;

public class FileWrapper implements Comparable<FileWrapper> {

	private long lastModified, fileSize = 0L;
	private int iconId;
	private File file;
	private boolean isDir = false;
	private String name;

	public FileWrapper(File file) {
		this.file = file;
		this.name = file.getName();
		isDir = file.isDirectory();
		if (isDir)iconId = R.mipmap.folder28px;
		else iconId = R.mipmap.file28px;
		lastModified =	file.lastModified();
		try {
			fileSize =	file.length();
		} catch (Exception e) {}
	}

	public File getFile() {
		return file;
	}

	public String getName() {
		return name;
	}
	
	public boolean isDirectory(){
		return isDir;
	}

	public long getLastModified() {
		return lastModified;
	}

	public long getFileSize() {
		return fileSize;
	}

	public int getIconId() {
		return iconId;
	}

	@Override
	public int compareTo(FileWrapper p1) {
		return name.compareToIgnoreCase(p1.name);
	}

	@Override
	public boolean equals(Object o) {
		return name.compareToIgnoreCase(((FileWrapper)o).getName()) == 0;
	}



	@Override
	public String toString() {
		return name;
	}



}
