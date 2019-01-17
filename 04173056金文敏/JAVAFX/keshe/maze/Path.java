package maze;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import maze.Create.ArcNode;
import maze.Create.VertexNode;

public class Path {
	// 寻找最短 路径
	static class EntryPQ implements Comparable<EntryPQ> {
		public int node; // 顶点序号
		public int weight;//
		EntryPQ pre; // 前驱节点

		public EntryPQ(int node, int weight, EntryPQ pre) {
			this.node = node;
			this.weight = weight;
			this.pre = pre;
		}

		@Override
		public int compareTo(EntryPQ arg0) {
			// TODO Auto-generated method stub
			return this.weight - arg0.weight;
		}

	}

	public int calWeight(int o1, int o2) {
		ArcNode node = Create.vexs.get(o1).firstNode;
		while (node != null) {
			if (node.adjvex == o2)
				return node.weight;
			else
				node = node.next;
		}
		return 0;
	}

	/**
	 * 查找最短路径 djskl算法   广度优先
	 * @param x
	 * @param y
	 * @param path
	 * @return
	 */
	public int queryPath(int x, int y, List<Integer> path) {
//		int x = Create.getPosition(start);
//		int y = Create.getPosition(end);
		Set<Integer> vex = Create.vexs.keySet();
		for (Integer o : vex) {
			Create.vexs.get(o).isVisited = false;
		}
		int pathCost = 0;

		Queue<EntryPQ> queue = new PriorityQueue<>();

		boolean done = false;
		EntryPQ en = new EntryPQ(x, 0, null);
		queue.add(en);
		while (!done && !queue.isEmpty()) {
			EntryPQ frontEntry = queue.poll();
			int frontVertex = frontEntry.node;

			if (!Create.vexs.get(frontVertex).isVisited) {
				Create.vexs.get(frontVertex).isVisited = true;
				if (frontVertex == y) {
					done = true;
					pathCost = frontEntry.weight;

					while (frontEntry != null) {
						path.add(frontEntry.node); // 加入当前顶点的下标
						frontEntry = frontEntry.pre;
					}
				} else {

					// 查找邻接表
					ArcNode node = Create.vexs.get(frontVertex).firstNode;
					while (node != null) {
						EntryPQ er = new EntryPQ(node.adjvex, frontEntry.weight + calWeight(frontVertex, node.adjvex),
								frontEntry);

						queue.add(er);
						node = node.next;
					}
				}

			}
		}

		return pathCost;
	}

	/**
	 * 增加景点
	 * @param name
	 */
	public boolean addView(String name) {
		if(exist("travel.sc",name)) {
			System.out.println("你想要增加的景点已经存在");
			return false;
		}
		try {
			try (BufferedWriter out = new BufferedWriter(new FileWriter("travel.sc", true));) {
				int max = 0;
				out.write("\n" + name);
				Set<Integer> findMin = Create.vexs.keySet();
				for (Integer o : findMin) {
					if (o > max)
						max = o;
				}
				VertexNode node = new VertexNode();
				node.firstNode = null;
				node.name = name;
				Create.vexs.put(max + 1, node);
			}
		} catch (Exception e) {

		}
		Create.createGraph("travel.sc", "path.sc");
		return exist("travel.sc",name);
	}
/**
 * 删除路径
 * @param name
 */
	public boolean removePath(String name) {
		if(!exist("path.sc",name)) {
			System.out.println("你要删除的路径不存在");
			return false;
		}
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("tempview.sc"));
			BufferedReader in = new BufferedReader(new FileReader("path.sc"));
			String s = "";
			s = in.readLine();
			if (!(s.equals(name)))
				out.write(s);
			else {
				s = in.readLine();
				if (s != null)
					out.write(s);
			}
			while ((s = in.readLine()) != null) {

				if (!(s.equals(name)))
					out.write("\n" + s);
			}
			in.close();
			out.close();
			BufferedWriter reout = new BufferedWriter(new FileWriter("path.sc"));
			BufferedReader tempin = new BufferedReader(new FileReader("tempview.sc"));
			String n = tempin.readLine();
			reout.write(n);
			while ((s = tempin.readLine()) != null) {
				reout.write("\n" + s);
			}
//
			tempin.close();
			reout.close();
			FileWriter h = new FileWriter("tempview.sc");
			h.close();
		} catch (IOException e) {
		}
		Create.createGraph("travel.sc", "path.sc");
		return !exist("path.sc",name);
	}
/**
 * 删除path.sc中包含name字符串的路径
 * @param name
 */
	private void deletePath(String name) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("tempview.sc"));
			BufferedReader in = new BufferedReader(new FileReader("path.sc"));
			String s = "";
			s = in.readLine();
			String[] list = s.split("  ");
			if (!(list[0].equals(name) || list[1].equals(name)))
				out.write(s);
			else {
				while (list[0].equals(name) || list[1].equals(name)) {
					if ((s = in.readLine()) != null)
						list = s.split("  ");
				}
				if (s != null)
					out.write(s);
			}
			while ((s = in.readLine()) != null) {

				list = s.split("  ");
				if (!(list[0].equals(name) || list[1].equals(name)))
					out.write("\n" + s);
			}
			in.close();
			out.close();
			BufferedWriter reout = new BufferedWriter(new FileWriter("path.sc"));
			BufferedReader tempin = new BufferedReader(new FileReader("tempview.sc"));
			String n = tempin.readLine();
			reout.write(n);
			while ((s = tempin.readLine()) != null) {
				reout.write("\n" + s);
			}
//
			tempin.close();
			reout.close();
			FileWriter h = new FileWriter("tempview.sc");
			h.close();
		} catch (IOException e) {
		}

	}

	/**
	 * 删除景点
	 * @param name
	 */
	public boolean removeView(String name) {
		if(!exist("travel.sc",name)) {
			System.out.println("你要删除的景点不存在");
			return false;
		}
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("tempview.sc"));
			BufferedReader in = new BufferedReader(new FileReader("travel.sc"));
			String s = "";
			s = in.readLine();
			if (!s.equals(name))
				out.write(s);
			else {
				if ((s = in.readLine()) != null)

					out.write(s);
			}

			while ((s = in.readLine()) != null) {
				if (s.equals(name)) {
					if ((s = in.readLine()) != null)
						out.write("\n" + s);

				} else {
					out.write("\n" + s);
				}
			}
			in.close();
			out.close();
			BufferedWriter reout = new BufferedWriter(new FileWriter("travel.sc"));
			BufferedReader tempin = new BufferedReader(new FileReader("tempview.sc"));
			String n = tempin.readLine();
			reout.write(n);
			while ((n = tempin.readLine()) != null) {
				reout.write("\n" + n);
			}
			tempin.close();
			reout.close();
			FileWriter h = new FileWriter("tempview.sc");
			h.close();
			// tempfile.delete();
			deletePath(name);
		} catch (IOException e) {
		}
		Create.createGraph("travel.sc", "path.sc");
		return !exist("travel.sc",name);
	}

	/**
	 * 将path文件中包含aim的字符串 中 aim 替换为s
	 * @param aim
	 * @param s
	 */
	private void modifypathFile(String aim, String s) {
		try (BufferedWriter temp = new BufferedWriter(new FileWriter("tempview.sc", true));
				BufferedReader in = new BufferedReader(new FileReader("path.sc"));) {
			String str = "";
			str = in.readLine();
			String[] list = str.split("  ");
			if (list[0].equals(aim))
				temp.write(s + "  " + list[1] + "  " + list[2]);
			else if (list[1].equals(aim))
				temp.write(list[0] + "  " + s + "  " + list[2]);
			else
				temp.write(str);
			while ((str = in.readLine()) != null) {
				list = str.split("  ");
				if (list[0].equals(aim) || list[1].equals(aim)) {
					if (list[0].equals(aim))
						temp.write("\n" + s + "  " + list[1] + "  " + list[2]);
					else
						temp.write("\n" + list[0] + "  " + s + "  " + list[2]);
				} else
					temp.write("\n" + str);
			}

		} catch (IOException e) {
		}
	}

	/**
	 * 将文件中的aim字符串换为s
	 * @param file
	 * @param aim
	 * @param s
	 */
	private void modifyFile(String file, String aim, String s) {
		try (BufferedWriter temp = new BufferedWriter(new FileWriter("tempview.sc", true));
				BufferedReader in = new BufferedReader(new FileReader(file));) {
			String str = "";
			str = in.readLine();
			if (str.equals(aim))
				temp.write(s);
			else
				temp.write(str);
			while ((str = in.readLine()) != null) {
				if (str.equals(aim)) {
					temp.write("\n" + s);
				} else
					temp.write("\n" + str);
			}
		} catch (IOException e) {
		}
	}

	/**
	 * 修改景点
	 * @param aim
	 * @param s
	 * @return
	 */
	public boolean modifyView(String aim, String s) {
		if(!exist("travel.sc",aim)) {
			System.out.println("你要修改的景点"+aim+"不存在");
			return false;
		}else if(exist("travel.sc",s)) {
			System.out.println("你想要重新设置的景点"+s+"已经存在");
			return false;
		}
		modifyFile("travel.sc", aim, s);
		try (BufferedWriter out = new BufferedWriter(new FileWriter("travel.sc"));
				BufferedReader in = new BufferedReader(new FileReader("tempview.sc"));) {
			// 将temp中的内容写进travel
			String str = "";
			str = in.readLine();
			out.write(str);
			while ((str = in.readLine()) != null)
				out.write("\n" + str);
			// 清空temp
			FileWriter re = new FileWriter("tempview.sc");
			re.close();
			// 将Path文件内容写进temp
			modifypathFile(aim, s);

			BufferedWriter reout = new BufferedWriter(new FileWriter("path.sc"));
			BufferedReader temp = new BufferedReader(new FileReader("tempview.sc"));
			String st = "";
			st = temp.readLine();
			reout.write(st);
			while ((st = temp.readLine()) != null)
				reout.write("\n" + st);
			reout.close();
			temp.close();
			// 清空文件
			FileWriter ret = new FileWriter("tempview.sc");
			ret.close();
		} catch (IOException e) {
		}
		Create.createGraph("travel.sc", "path.sc");
		return exist("travel.sc",s);
	}

	/**
	 * 添加路径
	 * @param start
	 * @param end
	 * @param weight
	 */
	public boolean addPath(String start, String end, int weight) {
		if(exist("path.sc",start+"  "+end+"  "+weight) || 
				exist("path.sc",end+"  "+start+"  "+weight)) {
			System.out.println("你要添加的路径["+start+" "+end+" "+weight+"]已经存在");
			return false;
		}
		ArcNode node = Create.vexs.get(Create.getPosition(start)).firstNode;
		ArcNode aim = new ArcNode();
		aim.adjvex = Create.getPosition(end);
		aim.weight = weight;
		if (node == null)
			node = aim;
		else
			Create.findLast(node, aim);
		ArcNode node1 = Create.vexs.get(Create.getPosition(end)).firstNode;
		ArcNode aim1 = new ArcNode();
		aim1.adjvex = Create.getPosition(start);
		aim1.weight = weight;
		if (node1 == null)
			node1 = aim1;
		else
			Create.findLast(node1, aim1);
		try (BufferedWriter out = new BufferedWriter(new FileWriter("path.sc", true));) {
			out.write("\n" + start + "  " + end + "  " + weight);
		} catch (IOException e) {
		}
		Create.createGraph("travel.sc", "path.sc");
		return exist("path.sc",start+"  "+end+"  "+weight);
	}
	/**
	 * 判断字符串s是否存在于file文件中
	 * @param file
	 * @param s
	 * @return
	 */
	private boolean exist(String file,String s) {
		try(BufferedReader check = new BufferedReader(new FileReader(file));){
			String checks = "";
			while((checks = check.readLine())!=null) {
				if(checks.equals(s)) return true;
			}
		}  catch (IOException e) {
		}
		return false;
	}
	/**
	 * 修改路径
	 * @param aim
	 * @param s
	 */
	public boolean modifyPath(String aim, String s) {
		if(!exist("path.sc",aim))  {
			System.out.println("你要修改的路径["+aim+"]不存在");
			return false;
		}else if(exist("path.sc",s)) {
			System.out.println("你想重新设置的路径["+s+"]已经存在");
			return false;
		}
		modifyFile("path.sc", aim, s);
		try {
			BufferedWriter reout = new BufferedWriter(new FileWriter("path.sc"));
			BufferedReader temp = new BufferedReader(new FileReader("tempview.sc"));
			String st = "";
			st = temp.readLine();
			reout.write(st);
			while ((st = temp.readLine()) != null)
				reout.write("\n" + st);
			reout.close();
			temp.close();
			FileWriter ret;

			ret = new FileWriter("tempview.sc");
			ret.close();
		} catch (IOException e) {
		}
		Create.createGraph("travel.sc", "path.sc");
		return exist("path.sc",s);
	}
/**
 * 查询景点信息  输出是否存在该景点 并且输出该景点可以到达的地方
 * @param name
 */
	public void queryView(String name) {
		Collection<VertexNode> list = Create.vexs.values();
		VertexNode node = null;
		for (VertexNode o : list) {
			if (o.name.equals(name)) {
				node = o;
				System.out.println(name + "在这里");
			}
		}
		int count = 0;
		if (node == null)
			System.out.println(name + "不在这里");
		else {
			ArcNode it = node.firstNode;
			if (it == null)
				System.out.println(name + "还不能通向远处");
			else {
				System.out.println("你可以从" + name + "到达这些地方 :");
				while (it != null) {
					
					System.out.print("**( " + Create.vexs.get(it.adjvex).name + " )**");
					count++;
					it = it.next;
					if(count % 5 == 0) System.out.println("\n");
				}
				System.out.println("\n");
			}
		}
	}
	public void print() {
		System.out.println("               \t [0]西区大门---------------\t------------------------  400");
		System.out.println("[1]情人坡                                            /      \\                          |");
		System.out.println("    \\             160       \\                         |                [18]东区操场");
		System.out.println("     30           /          65                      |                  |");	
		System.out.println("       \\         /             \\                     |                  50");
		System.out.println("         [2]西区教学楼 -----110------ [3]办公楼                                                          |                [17]东区教学楼");
		System.out.println("           \\                                         |                  |");
		System.out.println("            \\                                        |                  |");
		System.out.println("             \\                                    [10]东区正门                                                70");
		System.out.println("              \\         [4]鸳鸯湖                                                           \t    |                    |");
		System.out.println("               370      /                           |                  [16]安美公寓");
		System.out.println("                 \\     8                            |                    |");
		System.out.println("          \t[5]图书馆 ---/                                                                                       240                  100");
		System.out.println("                /                                    |                  [15]安悦公寓");
		System.out.println("              170                                    |                    |");
		System.out.println("              /                                      |                   100");
		System.out.println("             /                                       |--150------[13]东升苑-50--[14]澡堂");
		System.out.println("         [6]体育馆-------200-----[11]西区后门----50--[12]东区后门");
		System.out.println("          /   \\");
		System.out.println("        100    \\");
		System.out.println("       /         5");
		System.out.println("   [7]旭日苑                                   \\ ");
		System.out.println("                  \\          [8]快递点");  		
		System.out.println("                  [9]西区操场-----60-------------|");
		System.out.println("\n");
	}
}
