import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Driver {
	private static Path invalidatePath(String fileName) {
		String output = "";
		if (fileName.contains(".csv")) {
			output = fileName.replaceAll(".csv", "") + "_sorted.csv";
		} else {
			output = fileName + "_sorted.csv";
		}
		return Paths.get(Paths.get("").toAbsolutePath().toString(), output);
	}

	public static void writeResult(ArrayBasedList<Product> plist, String fileName) throws IOException {
		Path outputPath = invalidatePath(fileName);
		if (outputPath.toFile().exists()) {
			outputPath.toFile().delete();
		}
		try (BufferedWriter bw = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
			ArrayBasedList<Product>.Node currentNode = plist.getallNodes();
			while (currentNode.getNextNode() != null) {
				bw.write(String.format("%s\n", currentNode.getCurrentNode().getAsin()));
				currentNode = currentNode.getNextNode();
			}
			bw.write(String.format("%s\n", currentNode.getCurrentNode().getAsin()));
		}
		System.out.printf("result writer to path:%s\n", outputPath.toString());
	}

	public static void main(String[] args) {
		/* Check if arguments are valid */
		/* program end with -1 if, arguments are invalid */
		if (args.length != 1) {
			System.out.println("Invalid Argument(s) Inputed!");
			System.exit(-1);
		}
		ArrayBasedList<Product> plist = null;
		/* parse content of file into an ArrayBasedList<Product> plist */
		try {
			plist = FileParser.parse(args[0]);
			InsertionSort.sort(plist);
		} catch (IOException e3) {
			e3.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * check if plist is still null, in order to avoid null pointer
		 * exception
		 */
		/* program end with -2 if, plist is null */
		if (plist == null) {
			System.out.println("Something wrong while parsing the file");
			System.exit(-2);
		} else {
			try {
				writeResult(plist, args[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// System.out.println("every thing is fine now!!!");
			// System.exit(7);
		}

		try {
			// This will test the certain edge case and accuracy of your sorts.
			// This is NOT comprehensive and your own tests need to done in
			// addition to this test.
			//
			// Test.java file. Large samples will take a long time to complete
			// though.
			Test.test(plist, args);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | IOException e) {
			System.out.println("Test could not run/finish.");
		}

		// TODO: Sort based on averageRating from highest to lowest.

		// TODO: Write to a new .csv file named whatever the file being read is
		// with
		// "_sorted"
		// added to it. So if the file being read is called ratings_Stuff.csv,
		// your file
		// will be called ratings_Stuff_sorted.csv. For this part, use any of
		// your
		// sorts.
		// The file will have one ASIN per line.

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
