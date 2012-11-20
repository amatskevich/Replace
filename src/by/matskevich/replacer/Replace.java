package by.matskevich.replacer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Replace {
	
	private static final String TEMP_PREFIX = ".rpl.tmp"; 

	private static void replace(String oldstring, String newstring, File sourceFile,
			File tempOut) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
		PrintWriter writer = new PrintWriter(new FileWriter(tempOut));
		String line = null;
		while ((line = reader.readLine()) != null) {
			writer.println(line.replaceAll(oldstring, newstring));
		}

		reader.close();
		writer.close();
		
		if (!sourceFile.delete()) {
			System.err.println("Can't remove old source file " + sourceFile.getAbsolutePath());	
			return;
		}
		
		if (!tempOut.renameTo(sourceFile)) {
			System.err.println("Can't rename temp file " + tempOut.getAbsolutePath());
			return;
		}		
	}
	
	public static void replace(String oldstring, String newstring, File[] files, boolean root) throws IOException {
		for (final File file : files) {
			final String path = file.getAbsolutePath();
			
			if (path.contains(TEMP_PREFIX)) {
				System.out.println("Skip temp files " + path);
				return;
			}
			
			if (!file.exists()) {
				System.err.println("File " + path + " not found");
				continue;
			}
			
			if (file.isFile()) {
				final File tempFile = new File(path + TEMP_PREFIX);
				
				if (tempFile.exists()) {
					if (!tempFile.delete()) {
						System.err.println("Can't remove old temp file " + path);
						continue;
					}
				}
				
				if (!tempFile.createNewFile()) {
					System.err.println("Can't create temp file " + path);
					continue;
				}
				
				replace(oldstring, newstring, file, tempFile);
			} else if (root) {
				replace(oldstring, newstring, file.listFiles(), false);
			}			
		}
	}
}
