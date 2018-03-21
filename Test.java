
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Test {
	
	/**
	 * Tests all sorts. This is NOT a comprehensive test and further tests should be
	 * done to ensure validity. 
	 * 
	 * @param args 
	 * @throws IOException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static void test(ProductList plist, String[] args) throws IOException, NoSuchMethodException, 
	SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// Test empty array based list
		if (testExceptions()) {
			//printTiming(plist);
			// Test accuracy
			testAccuracy();
		}
	}
	
	@SuppressWarnings("unused")
	private static void printTiming(ProductList orig) throws 
	NoSuchMethodException, SecurityException, IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException {
		System.out.println("----------------------------------------");
		System.out.println("Timing (Does NOT check for accuracy): ");
		Object[] sortNames = new Object[] {new BubbleSort(),
				new MergeSort(),new QuickSort()
				,new HeapSort()};
		for (Object sortName: sortNames) {
			Method method = sortName.getClass().getMethod("sort",ProductList.class);
			ProductList plist = new ProductList();
			for (Product p: orig) {
				plist.add(p);
			}
			long start = System.nanoTime();
			method.invoke(sortName,plist);
			System.out.println(sortName.getClass().getName()+": "+(System.nanoTime()-start)+" nano seconds");
		}
	}
	
	private static ProductList setUp1() {
		ProductList plist = new ProductList();
		plist.add(new Product("123",new int[] {3,4,5}));
		plist.add(new Product("345",new int[] {5,3,2,5,5}));
		plist.add(new Product("4",new int[] {4,5}));
		plist.add(new Product("2AC",new int[] {2,2}));
		return plist;
	}
	
	private static ProductList setUp2() {
		ProductList plist = new ProductList();
		plist.add(new Product("F",new int[] {4,5,3,4,4}));
		plist.add(new Product("AL",new int[] {2,2,3,3}));
		plist.add(new Product("PI",new int[] {2,3,1,4,3,2}));
		plist.add(new Product("S",new int[] {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5}));
		plist.add(new Product("A",new int[] {4,5,4,5,4,5}));
		plist.add(new Product("IS",new int[] {3,4,3,4,3,4}));
		plist.add(new Product("ID",new int[] {2,2,2}));
		plist.add(new Product("U",new int[] {5,5,5,5}));
		plist.add(new Product("IL",new int[] {4}));
		plist.add(new Product("T",new int[] {3,4,3,4}));
		plist.add(new Product("OUS",new int[] {1,1,1}));
		plist.add(new Product("I",new int[] {4,5,4}));
		plist.add(new Product("P",new int[] {5,5,5}));
		plist.add(new Product("CI",new int[] {1,1,1,1,1,1}));
		plist.add(new Product("EX",new int[] {3}));
		plist.add(new Product("C",new int[] {4,5,5,4,5}));
		plist.add(new Product("O",new int[] {1,2,1}));
		plist.add(new Product("E",new int[] {5,5}));
		plist.add(new Product("G",new int[] {4,4}));
		plist.add(new Product("R",new int[] {5}));
		plist.add(new Product("IC",new int[] {3,3,3}));
		plist.add(new Product("L",new int[] {4,4,5,5}));
		plist.add(new Product("RA",new int[] {4,4,4}));
		return plist;
	}
	
	private static void testAccuracy() throws NoSuchMethodException, SecurityException, 
	IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("----------------------------------------");
		System.out.println("Accuracy Test: ");
		testAccuracySmall();
		testAccuracyMedium();
	}
	
	private static void testAccuracySmall() throws NoSuchMethodException, SecurityException, 
	IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("-------------");
		System.out.println("Small Test");
		System.out.println("-------------");
		if (testAccuracyFor(setUp1(),new String[] {"4","345","123","2AC"})) {
			System.out.println("Passes small sorting!");
		}
	}
	
	private static void testAccuracyMedium() throws NoSuchMethodException, SecurityException, 
	IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("-------------");
		System.out.println("Medium Test");
		System.out.println("-------------");
		if (testAccuracyFor(setUp2(),new String[] {"S","U","P","E","R","C","A","L","I","F","RA","G",
				"IL","IS","T","IC","EX","PI","AL","ID","O","CI","OUS"})) {
			System.out.println("Passed medium sorting!");
		} 
	}
	
	private static boolean testAccuracyFor(ProductList orig, String[] answer) throws NoSuchMethodException, 
	SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		boolean success = true;
		Object[] sortNames = new Object[] {new BubbleSort(),new MergeSort(),new HeapSort(),new QuickSort()};
		for (Object sortName: sortNames) {
			Method method = sortName.getClass().getMethod("sort",ProductList.class);
			ProductList plist = new ProductList();
			for (Product p: orig) {
				plist.add(p);
			}
			long start = System.nanoTime();
			method.invoke(sortName,plist);
			System.out.println(sortName.getClass().getName()+": "+(System.nanoTime()-start)+" nano seconds");
			for (int i=0; i<answer.length; i++) {
				try {
					if (!plist.get(i).toString().equals(answer[i])) {
						System.out.println("!!! "+sortName.getClass().getName()+".sort does not work");
						success = false;
						break;
					}
				} catch (IndexOutOfBoundsException e) {
					System.out.println("!!! "+sortName.getClass().getName()+".sort crashed");
					success = false;
					break;
				}
			}
		}
		return success;
	}
	
	public static void testFile(String[] args) throws IOException {
		String f = args[0];
		System.out.println("----------------------------------------");
		System.out.println("File Test:");
		int index = f.lastIndexOf('.');
		Path result = Paths.get(f.substring(0, index)+"_result"+f.substring(index));
		Path answer = Paths.get(f.substring(0, index)+"_sorted"+f.substring(index));
		
		if (Arrays.equals(Files.readAllBytes(answer), Files.readAllBytes(result))) {
			System.out.println(answer.toString()+" matches "+result.toString());
		} else {
			System.out.println(answer.toString()+" does not match "+result.toString());
			System.out.println("Ignore if using unstable sort.");
		}
	}
	
	private static boolean testExceptions() throws NoSuchMethodException, 
	SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		boolean success = true;
		System.out.println("----------------------------------------");
		System.out.println("Exception Test:");
		success &= testEmpty();
		success &= testEmptyRating();
		success &= testOne();
		return success;
	}
	
	private static boolean testOne() throws NoSuchMethodException, SecurityException, 
	IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		boolean success = true;
		StringWriter fail = new StringWriter();
		System.out.println("-------------");
		System.out.println("Single Test: ");
		Object[] sortNames = new Object[] {new BubbleSort(),
				new MergeSort(),new QuickSort()
				,new HeapSort()};
		for (Object sortName: sortNames) {
			Method method = sortName.getClass().getMethod("sort",ProductList.class);
			ProductList plist = new ProductList();
			plist.add(new Product("abc",new int[] {1,3}));
			try {
				method.invoke(sortName,plist);
			} catch (Exception e) {
				fail.write(sortName.getClass().getName()+", ");
				success = false;
			}
		}
		if (success) {
			System.out.println("Passes one element test!");
		} else {
			System.out.println("Fail: "+fail.toString());
		}
		return success;
	}
	
	private static boolean testEmpty() throws NoSuchMethodException, SecurityException {
		boolean success = true;
		StringWriter fail = new StringWriter();
		System.out.println("-------------");
		System.out.println("Empty Product Test: ");
		Object[] sortNames = new Object[] {new BubbleSort(),
				new MergeSort(),new QuickSort()
				,new HeapSort()};
		for (Object sortName: sortNames) {
			Method method = sortName.getClass().getMethod("sort",ProductList.class);
			ProductList plist = new ProductList();
			try {
				method.invoke(sortName,plist);
			} catch (Exception e) {
				fail.write(sortName.getClass().getName()+", ");
				success = false;
			}
		}
		if (success) {
			System.out.println("Passes one element test!");
		} else {
			System.out.println("Fail: "+fail.toString());
		}
		return success;
	}
	
	private static boolean testEmptyRating() throws NoSuchMethodException, SecurityException {
		boolean success = true;
		StringWriter fail = new StringWriter();
		System.out.println("-------------");
		System.out.println("Empty Rating Test: ");
		Object[] sortNames = new Object[] {new BubbleSort(),
				new MergeSort(),new QuickSort()
				,new HeapSort()};
		for (Object sortName: sortNames) {
			Method method = sortName.getClass().getMethod("sort",ProductList.class);
			ProductList plist = new ProductList();
			plist.add(new Product("123",new int[] {}));
			plist.add(new Product("234",new int[] {}));
			plist.add(new Product("345",new int[] {}));
			plist.add(new Product("abc",new int[] {1,3}));
			try {
				method.invoke(sortName,plist);
			} catch (Exception e) {
				fail.write(sortName.getClass().getName()+", ");
				success = false;
			}
		}
		if (success) {
			System.out.println("Passes one element test!");
		} else {
			System.out.println("Fail: "+fail.toString());
		}
		return success;
	}
	
}
