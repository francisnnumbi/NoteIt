package fnn.smirl.noteit.utils;
import java.io.File;
import android.os.Environment;

public interface Constants
{
	File ROOT_HOME = Environment.getExternalStorageDirectory();
	File APP_HOME_DIR = new File(ROOT_HOME, "noteit");

	}
