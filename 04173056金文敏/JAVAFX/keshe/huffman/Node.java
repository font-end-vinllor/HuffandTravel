package huffman;
/*
 *   节点类，实现节点的基本操作
 * */
public class Node {
	Node left,right;
	String data,code;
	int weight;
	public Node() {}
	public Node(String data) {
		this.data = data;
		this.code = "";
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	public Node getLeft() {
		return left;
	}
	public void setRight(Node right) {
		this.right = right;
	}
	public Node getRight() {
		return right;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getWeight() {
		return weight;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
