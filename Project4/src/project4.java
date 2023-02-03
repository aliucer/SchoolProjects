import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class project4 {
	public static void main(String[] args) {
		HashMap<String,Integer> map = new HashMap<>();
		HashMap<String,Integer> flagMap = new HashMap<>();
		StringBuilder str = new StringBuilder();
		if(args.length!=2) {
			System.out.println("number of arguments is not 2");
			System.exit(1);
		}
		ArrayList<Edge> edgeList = new ArrayList<>();
		ArrayList<Integer> verticesList = new ArrayList<>();
		Integer pointCount;
		Integer flagCount;
		String start = "";
		String end = "";
		ArrayList<String> listem = new ArrayList<>();
		String[] flags = new String[5];
		try {
			File myObj = new File(args[0]);
			Scanner sc = new Scanner(myObj);
			pointCount = Integer.valueOf(sc.nextLine());
			flagCount = Integer.valueOf(sc.nextLine());
			String line = sc.nextLine();
			start = line.split(" ")[0];
			end = line.split(" ")[1];
			line = sc.nextLine();
			flags = line.split(" ");
			for(int i=0; i<flags.length; i++) {
				flagMap.put(flags[i], i);
			}
			
			int index = 0;
			while(sc.hasNextLine()) {
				line = sc.nextLine();
				listem.add(line);
				String[] arr = line.split(" ");
				map.put(arr[0],index);
				verticesList.add(index);
				index++;
				
			}

			sc.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		int startIndex = map.get(start);
		int endIndex = map.get(end);
		ArrayList<Integer> flagsIndex = new ArrayList<>();
		for(int i=0; i<flags.length; i++) {
			flagsIndex.add(map.get(flags[i]));
		}
		for(int i=0; i<listem.size(); i++) {
			String line = listem.get(i);
			String[] arr = line.split(" ");
			for(int j=1; j<arr.length; j=j+2) {
				Edge edge = new Edge(map.get(arr[0]),map.get(arr[j]),Integer.valueOf(arr[j+1]));
				edgeList.add(edge);
				edge = new Edge(map.get(arr[j]),map.get(arr[0]),Integer.valueOf(arr[j+1]));
				edgeList.add(edge);
			}
		}
		Graph graph = new Graph(verticesList, edgeList);
		int[] arr = graph.shortestPath(0);
		str.append(arr[endIndex]);	
		ArrayList<Edge> edgeList2 = new ArrayList<>();
		ArrayList<Integer> verticesList2 = new ArrayList<>();
		for(int i=0; i<flagsIndex.size(); i++) {
			verticesList2.add(i);
		}
		for(int i=0; i<flagsIndex.size(); i++) {
			int[] path = graph.shortestPath(flagsIndex.get(i));
			for(int j=0; j<flagsIndex.size()&& i!=j; j++) {
				Edge edge = new Edge(i,j,path[flagsIndex.get(j)]);
				Edge edge2 = new Edge(j,i,path[flagsIndex.get(j)]);
				edgeList2.add(edge);
				edgeList2.add(edge2);
			}
		}
		Graph graph2 = new Graph(verticesList2, edgeList2);
		long mst = graph2.MST();
		str.append(System.lineSeparator() + mst);
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
