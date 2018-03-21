import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Driver {

	public static void main(String[] args) {
		ArrayBasedList<Product> plist = new ArrayBasedList<Product>();
		//TODO: Read .csv file from command line and parse into ArrayBasedList<Product>
		// You should not need to declare another ArrayBasedList<Product> in main
		
		try {
			// This will test the certain edge case and accuracy of your sorts.
			// This is NOT comprehensive and your own tests need to done in 
			// addition to this test.
			//
			// TODO: To look the timing of your sorts, uncomment printTiming in the 
			// Test.java file. Large samples will take a long time to complete though.
			Test.test(plist, args);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | 
				IllegalArgumentException | InvocationTargetException | IOException e) {
			System.out.println("Test could not run/finish.");
		}
		
		//TODO: Sort based on averageRating from highest to lowest.
		
		//TODO: Write to a new .csv file named whatever the file being read is with "_sorted"
		//      added to it. So if the file being read is called ratings_Stuff.csv, your file
		//      will be called ratings_Stuff_sorted.csv. For this part, use any of your sorts.
		//      The file will have one ASIN per line.
		
		try {
			// Requires both result and sorted files to be in current directory
			Test.testFile(args);
		} catch (IOException e1) {
			System.out.println("----------------------------------------");
			System.out.println("!! File Test: Could not read file. Check file naming.");
		} catch (IndexOutOfBoundsException e2) {
			System.out.println("----------------------------------------");
			System.out.println("!! File Test: Missing command line argument");
		}
	}

}
