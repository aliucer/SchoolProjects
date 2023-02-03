import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Project1 {
	public static void main(String[] args) {
		FactoryImpl factory = new FactoryImpl(null, null, 0);
		StringBuilder str = new StringBuilder();
		if(args.length!=2) {
			System.out.println("number of arguments is not 2");
			System.exit(1);
		}
		try {
			File myObj = new File(args[0]);
			Scanner sc = new Scanner(myObj);
			
			while(sc.hasNext()) {
				String a = sc.next();
				switch (a) {
				case "AF":
					int id = sc.nextInt();
					int value = sc.nextInt();
					factory.addFirst(new Product(id, value));
					break;
				case "AL":
					id = sc.nextInt();
					value = sc.nextInt();
					factory.addLast(new Product(id, value));
					break;
				case "A":
					int index = sc.nextInt();
					id = sc.nextInt();
					value = sc.nextInt();				
					try {
						factory.add(index, new Product(id, value));	
					} catch (Exception e) {
						str.append("Index out of bounds." + System.lineSeparator());
					}
					break;
				case "RF":
					try {
						str.append(factory.removeFirst() + System.lineSeparator());
					}
					catch (NoSuchElementException ex) {
						str.append("Factory is empty." + System.lineSeparator());
					}
					break;
				case "RL":
					try {
						str.append(factory.removeLast() + System.lineSeparator());
					}
					catch (NoSuchElementException ex) {
						str.append("Factory is empty." + System.lineSeparator());
					}
					break;
				case "RI":
					index = sc.nextInt();
					try {
						str.append(factory.removeIndex(index) + System.lineSeparator());
					}
					catch (IndexOutOfBoundsException ex) {
						str.append("Index out of bounds." + System.lineSeparator());
					}
					break;
				case "RP":
					value = sc.nextInt();
					try {
						str.append(factory.removeProduct(value) + System.lineSeparator());
					}
					catch (NoSuchElementException ex) {
						str.append("Product not found." + System.lineSeparator());
					}
					break;
				case "F":
					id = sc.nextInt();
					try {
						str.append(factory.find(id) + System.lineSeparator());
					} catch (NoSuchElementException ex) {
						str.append("Product not found." + System.lineSeparator());
					}
					break;
					
				case "G":
					index = sc.nextInt();
					try {
						str.append(factory.get(index) + System.lineSeparator());
					} catch (Exception e) {
						str.append("Index out of bounds." + System.lineSeparator());
					}
					break;
					
				case "U":
					id = sc.nextInt();
					value = sc.nextInt();
					
					try {
						str.append(factory.update(id, value) + System.lineSeparator() );
					}
					catch (NoSuchElementException ex) {
						str.append("Product not found." + System.lineSeparator());
					}
					
					break;
					
				case "FD":
					str.append(factory.filterDuplicates() + System.lineSeparator());
					break;
				case "R":
					factory.reverse();
					str.append(factory.toString() + System.lineSeparator());
					break;
				case "P":
					str.append(factory.toString() + System.lineSeparator());
					break;
				default:
					break;
				}
			}
			
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		try {
			File myObj = new File(args[1]);
			FileWriter myWriter = new FileWriter(myObj);
			myWriter.write(String.valueOf(str));
			myWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}