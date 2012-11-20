package by.matskevich.replacer;

import java.io.File;
import java.io.IOException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		boolean hasError = false;
		
		String oldValue = null;
		String newValue = null;
		File[] files = null;
		
		if (args.length < 1) {
			hasError = true;
			System.err.println("First param not found (old substring)");
		} else {
			oldValue = args[0];
		}
		
		if (args.length < 2) {
			hasError = true;
			System.err.println("Second param not found (new substring)");
		} else {
			newValue = args[1];
		}
		
		if (args.length < 3) {
			hasError = true;
			System.err.println("At least one file should be entered");
		} else {
			files = new File[args.length - 2];
			for (int i = 0; i < files.length; i++) {
				files[i] = new File(args[i + 2]);
			}
		}
		
		if (!hasError) {
			try {
				Replace.replace(oldValue, newValue, files, true);
			} catch (IOException e) {
				hasError = true;
				e.printStackTrace();
			}
		} 
		System.err.println("Terminate " + (hasError ? "with error" : "successfuly"));
	}
}
