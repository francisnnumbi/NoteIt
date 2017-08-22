package fnn.smirl.noteit.utils;
import java.text.*;
import java.util.*;

public class AppUtils
{
	private static String dateFormat = "dd/MM/yyyy";
	public static String getDate(long date){
	SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
	return formatter.format(new Date(date));
	}
	
	public static String getReadableSize(long size){
		String[] units = {"B", "KB", "MB", "GB"};
		long l = size;
		int a = 0;
		
		do{
			l /= 1024;
		if(l > 0)	a++;
		}while(l > 0);
		
		double d;
		if(a == 0) d = size;
		else d = size / Math.pow(1024, a);
		return String.format("%.2f%3s", d, units[a]);
	}
}
