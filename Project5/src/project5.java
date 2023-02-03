import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class project5 {

	public static void main(String[] args) {
		StringBuilder str = new StringBuilder();
		if(args.length!=2) {
			System.out.println("number of arguments is not 2");
			System.exit(1);
		}
		try {
			ArrayList<String> vertices = new ArrayList<>();
			vertices.add("virtual");
			ArrayList<String> lines = new ArrayList<>();
			ArrayList<Edge> edges = new ArrayList<>();
			File myObj = new File(args[0]);
			Scanner sc = new Scanner(myObj);
			int city = sc.nextInt();
			int index = 1;
			int verticesSize = 0;
			HashMap<String,Integer> map = new HashMap<>();
			for(int i=1; i<7; i++) {
				int distance = sc.nextInt();
				edges.add(new Edge(0, i, distance));
				
			}
			sc.nextLine();
			verticesSize++;
			while(sc.hasNextLine()) {
				String st = sc.nextLine();
				lines.add(st);
				String[] line = st.split(" ");
				vertices.add(line[0]);
				map.put(line[0], index);
				index++;
				verticesSize++;
			}
			vertices.add("KL");
			verticesSize++;
			map.put("KL", index);
			for(int i=0; i<lines.size(); i++) {
				String[] arr = lines.get(i).split(" ");
				int from = map.get(arr[0]);
				for(int j=1; j<arr.length; j=j+2) {
					int to = map.get(arr[j]);
					int weight = Integer.valueOf(arr[j+1]);
					edges.add(new Edge(from,to,weight));
				}
			}

			Graph graph = new Graph(edges, verticesSize, vertices);
			str.append(graph.fulkerson() + System.lineSeparator());
			str.append(graph.dfsAll());
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
