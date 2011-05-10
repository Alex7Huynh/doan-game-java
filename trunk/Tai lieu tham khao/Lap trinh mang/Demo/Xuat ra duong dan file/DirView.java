package basic;

import java.io.*;
import java.util.*;
public class DirView {
	class Filter implements FilenameFilter{

		public boolean accept(File dir, String filename) {
			
			return filename.endsWith(".txt");
//			return filename.indexOf(".txt")>-1;
		}
		
	}
	public void dirView(String path){
		File dir = new File(path);
		
		String[] names = dir.list(new Filter());
		for(String s:names) System.out.println(s);
	}
	
	public static void main(String[] args) {
		String path = "e:\\";
		
		new DirView().dirView(path);
		

	}

}
