package fnn.smirl.noteit.utils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Format;
import java.util.Formatter;

public class FileUtils
{
	
	public static boolean store2(File file, String content){
		FileWriter out = null;
		try{
			out = new FileWriter(file);
			out.write(content);
			out.close();
			return true;
			}catch(IOException ioe){
				return false;
			}

		}
		
	public static boolean store(File file, String content){
		Formatter out = null;
		try{
			out = new Formatter(file);
			out.format("%s", content);
			out.close();
			return true;
		}catch(IOException ioe){
			return false;
		}

	}
		
	
		public static String load2(File file){
			StringBuilder builder = new StringBuilder();
			try{
				BufferedReader in = new BufferedReader(new FileReader(file));
				String line = null;
				while((line = in.readLine()) != null){
					builder.append(line);
					builder.append("\n");
				}
				in.close();
			}catch(IOException ioe){}
			return builder.toString().trim();
		}
		
		public static String load(File file){
			StringBuilder builder = new StringBuilder();
			try {
				Scanner scanner = new Scanner(file);
				while(scanner.hasNext()){
					builder.append(scanner.nextLine());
					builder.append("\n");
				}
				scanner.close();
			} catch (FileNotFoundException e) {}
			return builder.toString().trim();
		}
}
