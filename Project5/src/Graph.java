import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Graph {
	ArrayList<String> vertices;
	public ArrayList<Edge> edges;
	public ArrayList<Edge> edgesCopy;
	int size;
	public ArrayList<ArrayList<Edge>> neighbors = new ArrayList<>();
	public Graph(ArrayList<Edge> edges, int size, ArrayList<String> vertices) {
		this.vertices = vertices;
		this.edges = edges;
		edgesCopy = edges;
		this.size = size;
		for(int i=0; i<size; i++) {
			neighbors.add(new ArrayList<Edge>());
		}
		for(int i=0; i<edges.size(); i++) {
			neighbors.get(edges.get(i).from).add(edges.get(i));
		}
	}
	public String vulnerable() {
		StringBuilder str = new StringBuilder();
		
		return String.valueOf(str);
	}
	public int fulkerson() {
		int dis = 0;
		while(true) {
			ArrayList<Integer> list = dfs(0,size-1);
			Collections.reverse(list);
			if(list.size()==1) {
				break;
			}
			int min = Integer.MAX_VALUE;
			for(int i=0; i<list.size()-1; i++) {
				min = Integer.min(min, getDistance(list.get(i),list.get(i+1)));
			}
			dis += min;
			for(int i=0; i<list.size()-1; i++) {
				increaseDistance(list.get(i), list.get(i+1), -min);
				increaseDistance(list.get(i+1), list.get(i), min);
			}
		}
		return dis;
	}
	
	
	
	
	private int getDistance(Integer integer, Integer integer2) {
		for(int i=0; i<neighbors.get(integer).size(); i++) {
			if(neighbors.get(integer).get(i).to==integer2) {
				return neighbors.get(integer).get(i).weight;
			}
		}
		return integer2;
	}

	private void increaseDistance(Integer integer, Integer integer2, int increase) {
		boolean bool = false;
		for(int i=0; i<neighbors.get(integer).size(); i++) {
			if(neighbors.get(integer).get(i).to==integer2) {
				neighbors.get(integer).get(i).weight += increase;
				bool = true;
			}
		}
		if(bool==false) {
			Edge edge = new Edge(integer, integer2, increase);
			neighbors.get(integer).add(edge);
			edges.add(edge);
		}
	}
	public String dfsAll(){
		StringBuilder str = new StringBuilder();
		boolean[] isVisited = new boolean[size];
		int[] parent = new int[size];
		for(int i=0; i<isVisited.length; i++) {
			parent[i] = -1;
		}
		dfs(0,parent,isVisited);
		HashSet<Integer> visitedNodes = new HashSet<>();
		HashSet<Integer> unvisitedNodes = new HashSet<>();
		ArrayList<Edge> minCut = new ArrayList<>();
		for(int i=0; i<vertices.size(); i++) {
			if(isVisited[i]) {
				visitedNodes.add(i);
			}
			else {
				unvisitedNodes.add(i);
			}
		}
		for(int i=0; i<edgesCopy.size(); i++) {
			if(visitedNodes.contains(edgesCopy.get(i).from) && unvisitedNodes.contains(edgesCopy.get(i).to)) {
				minCut.add(edgesCopy.get(i));
			}
		}
		for(int i=0; i<minCut.size(); i++) {
			Edge edge = minCut.get(i);
			if(edge.from == 0) {
				str.append(vertices.get(edge.to) + System.lineSeparator());
			}
			else {
				str.append(vertices.get(edge.from) + " " + vertices.get(edge.to) + System.lineSeparator());
			}
		}
		return String.valueOf(str);
	}
	public ArrayList<Integer> dfs(int from, int to){
		
		ArrayList<Integer> order = new ArrayList<>();
		boolean[] isVisited = new boolean[size];
		int[] parent = new int[size];
		for(int i=0; i<isVisited.length; i++) {
			parent[i] = -1;
		}
		dfs(from,parent,isVisited);
		
		order.add(to);
		while(parent[to]!=-1) {
			order.add(parent[to]);
			to = parent[to];
		}
		return order;
		
	}
	
	private void dfs(int from, int[] parent, boolean[] isVisited) {
		isVisited[from] = true;
		for(Edge edge : neighbors.get(from)) {
			if(!isVisited[edge.to] && edge.weight!=0) {
				parent[edge.to] = from;
				dfs(edge.to, parent, isVisited);
			}
		}
	}
}
