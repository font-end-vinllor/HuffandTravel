package huffman;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* 实现Huffman的创建 编码 译码    */
public class Tree {
	public Node root;
	String s = "";
	// 存放节点
	List<Node> list = new ArrayList<>();
	public Map<String,String> result = new HashMap<>();
	public List<Character>  chars = new LinkedList<>();
	static RandomAccessFile o;
	public int[] count = new int[128];
	public Tree() {
	}

	 private String enFile(String file) {
		try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));) {
			int ch = in.read();
			String str = "";
			while (ch > 0) {
				str = str + (char) ch;
				ch = in.read();
			}
			return str;
		} catch (FileNotFoundException ex) {	
		} catch (IOException e) {		
		}
		return null;
	}

	private void create(String str) {
		// 统计字符出现的次数
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			count[ch]++;
			chars.add(ch);
		}
		for (int i = 0; i < count.length; i++) {
			if (count[i] != 0) {
				char ch = (char) i;
				int number = count[i];
				Node node = new Node(ch + "");
				node.setWeight(number);
				list.add(node);
			}
		}
		while (list.size() > 1) {
			this.sort();
			Node left = list.remove(0);
			Node right = list.remove(0);
			// String s = left.getData() + right.getData();
			Node father = new Node(s);
			father.setWeight(left.getWeight() + right.getWeight());
			father.setLeft(left);
			father.setRight(right);
			list.add(0, father);
		}
		root = list.get(0);
	}
	public void Huffman() {
		String s = enFile("encode.dec");
		create(s);
	}
	public void encode() {
		encode(root);
		printfCode();
	}
	public String decode() {
		String s = enFile("code.dec");
		decode(s,root);
		try(FileWriter out = new FileWriter("code.dec");){
			out.close();
		} catch (IOException e) {
		}
		return "\n-------译码结束------";
	}
	private void sort() {
		Node temp;
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (list.get(i).getWeight() > list.get(j).getWeight()) {
					temp = list.get(i);
					list.set(i, list.get(j));
					list.set(j, temp);
				}
			}
		}
	}

	private void encode(Node root) {
		if (root.left == null && root.right == null) {
			System.out.println(root.data + "  " + root.code+"  "+this.count[root.data.charAt(0)]);
			result.put(root.data, root.code);
			return;
		} else if (root.left != null && root.right == null)
			root.left.code = root.code + "0";
		else if (root.right != null && root.left == null)
			root.right.code = root.code + "1";
		else {
			root.left.code = root.code + "0";
			root.right.code = root.code + "1";
		}
		encode(root.left);
		encode(root.right);
	}
	private void printfCode() {
		for(Character i : chars) {
			System.out.print(result.get(i.toString()));
			try(BufferedWriter out = new BufferedWriter(new FileWriter("code.dec",true));
					){
				out.write(result.get(i.toString()));
			} catch (IOException e) {
			}
		}
		System.out.println("\n");
		
	}
	private void decode(String code, Node e) {
		char[] s = code.toCharArray();
		String str = "";
		Node n = e;
		for (int i = 0; i < code.length(); i++) {
			if (s[i] == '0') {
				n = n.left;
				if (n.left == null && n.right == null) {
					System.out.print(n.data);
					str = str + n.data;
					n = e;
				}
			} else {
				n = n.right;
				if (n.left == null && n.right == null) {
					System.out.print(n.data);
					str = str + n.data;
					n = e;
				}
			}
		}
		try(
				BufferedWriter out = new BufferedWriter(new FileWriter("decode.dec"));){
			 out.write(str);
		} catch (IOException e1) {
		      
		}
	}
}
