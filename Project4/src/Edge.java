
public class Edge {
	int from;
	int to;
	int weight;
	public Edge(int f, int t, int w) {
		from = f;
		to = t;
		weight = w;
	}
	@Override
	public String toString() {
		String str = "from: " + from + " to: " + to + " weight: " + weight + System.lineSeparator();
		
		return str;
	}
}
