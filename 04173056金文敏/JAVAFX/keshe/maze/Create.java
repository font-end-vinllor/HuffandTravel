package maze;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 *     实现无向有权图的创建(邻接表)
 */
public class Create {
	public static Map<Integer,VertexNode> vexs = new HashMap<>();
	static class ArcNode {
		int adjvex; // 与之连接的点
		int weight; // 边的权值
		ArcNode next;
	}

	static class VertexNode {
		String name; // 景点名称
		ArcNode firstNode;
		boolean isVisited;

		public boolean isVisited() {
			return this.isVisited;
		}

		public void setVisited(boolean isVisited) {
			this.isVisited = isVisited;
		}
	}

	static void findLast(ArcNode last, ArcNode node) {
		while (last.next != null)
			last = last.next;
		last.next = node;
	}

	static int getPosition(String name) {
		Set<Integer> list = vexs.keySet();
		for(Integer o: list) {
			if(vexs.get(o).name.equals(name)) return o;
		}
		return -1;
	}

	static public void createGraph(String file, String viewFile) {
      
		try (   FileReader in = new FileReader(file); 
				BufferedReader bin = new BufferedReader(in);
				FileReader view = new FileReader(viewFile); 
				BufferedReader inview = new BufferedReader(view);) {
			String s = "";
			int i = 0;
			while ((s = bin.readLine()) != null) {
				VertexNode node = new VertexNode();
				node.firstNode = null;
				node.name = s;
				vexs.put(i, node);
				i++;
			}
			String str = "";
			while ((str = inview.readLine()) != null) {
				String[] s1 = str.split("  ");	
				if(s1.length == 1)    break;	
					String a = s1[0];
					String b = s1[1];
					int value = Integer.parseInt(s1[2]); 
					int start = getPosition(a);
					int end = getPosition(b);
					ArcNode anode = new ArcNode();
					ArcNode bnode = new ArcNode();
					anode.adjvex = end;
					anode.weight = value;
					bnode.adjvex = start;
					bnode.weight = value;
					if(vexs.get(end).firstNode == null) vexs.get(end).firstNode = bnode;
					else findLast(vexs.get(end).firstNode,bnode);
					if(vexs.get(start).firstNode == null) vexs.get(start).firstNode = anode;
					else findLast(vexs.get(start).firstNode,anode);
			}
		} catch (IOException e) {}		
	}
	static void printGraph() {
		Set<Integer> list = vexs.keySet();
		for(int i : list) {
			System.out.printf("%s--",vexs.get(i).name);
			ArcNode node = vexs.get(i).firstNode;
			while(node != null) {
				System.out.printf("---%s--",vexs.get(node.adjvex).name);
				node = node.next;
			}
			System.out.println("\n");
		}
	}
}
