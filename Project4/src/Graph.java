import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Graph {
	ArrayList<Integer> vertices = new ArrayList<>();
	ArrayList<ArrayList<Edge>> neighbors = new ArrayList<>();
	public Graph(ArrayList<Integer> vertices,ArrayList<Edge> edge) {
		for(int i=0; i<vertices.size(); i++) {
			addVertex(vertices.get(i));
		}
		for(int i=0; i<edge.size(); i++) {
			addEdge(edge.get(i));
		}
	}
	public void addVertex(int vertex) {
		vertices.add(vertex);
		neighbors.add(new ArrayList<Edge>());
	}
	public void addEdge(Edge edge) {
		neighbors.get(edge.from).add(edge);
	}

	int index = 0;
	public int[] shortestPath(int from) {
		int[] cost = new int[vertices.size()];
		for(int i=0; i<cost.length; i++) {
			cost[i] = Integer.MAX_VALUE;
		}
		cost[from] = 0;
		PriorityQueue<Integer> queue = new PriorityQueue<>(vertices.size(), ((k,v) -> (cost[k]-cost[v])));
		queue.offer(from);
		HashSet<Integer> found = new HashSet<>();
		while(found.size() < vertices.size()) {
			int index = 0;
			if(!queue.isEmpty()) {
				index = queue.poll();
			}
			else {
				break;
			}
			if(found.contains(index)) {
				continue;
			}
			int minCost = cost[index];
			/*
			for(int i=0; i<vertices.size(); i++) {
				if(!found.contains(i) && cost[i]<minCost) {
					minCost = cost[i];
					index = i;
				}
			}*/
			if(minCost == Integer.MAX_VALUE) break;
			else {
				found.add(index);
				for(int i=0; i<neighbors.get(index).size(); i++) {
					if(!found.contains(neighbors.get(index).get(i).to)) {
						queue.offer(neighbors.get(index).get(i).to);
						
					}
				}
			}
			for(int i=0; i< neighbors.get(index).size(); i++) {
				Edge edge = neighbors.get(index).get(i);
				if(!found.contains(edge.to) && cost[edge.to]>cost[index] + edge.weight) {
					cost[edge.to] = cost[index] + edge.weight;
				}
			}
		}
		return cost;
	}
	public long MST2() {
		return index;
		
	}
	public long MST() {
		int[] cost = new int[vertices.size()];
		for(int i=0; i<cost.length; i++) {
			cost[i] = Integer.MAX_VALUE;
		}
		cost[0] = 0;
		long weight = 0;
		HashSet<Integer> past = new HashSet<>();
		while(past.size()<vertices.size()) {
			int x = -1;
			int curCost = Integer.MAX_VALUE;
			for(int i=0; i<vertices.size(); i++) {
				if(!past.contains(i) && cost[i] < curCost) {
					curCost = cost[i];
					x = i;
				}
			}
			if(x == -1) {
				break;
			}
			else {
				past.add(x);
			}
			weight += cost[x];
			for(int i=0; i<neighbors.get(x).size(); i++){
				Edge edge3 = neighbors.get(x).get(i);
				if(!past.contains(edge3.to) && cost[edge3.to] > edge3.weight) {
					 cost[edge3.to] = edge3.weight;
				}
			}
		}
		if(past.size()<vertices.size()) return -1;
		return weight;
	}
}
