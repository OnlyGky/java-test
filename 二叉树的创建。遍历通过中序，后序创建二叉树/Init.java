package helloworld;

import java.util.Scanner;

import helloworld.CreateTree.Node;
public class Init {
	
public static void main(String[] args) {//主函数
	Scanner input=new Scanner(System.in);
	@SuppressWarnings("resource")
	CreateTree tree=new CreateTree();
	Node root=tree.new Node();
//	tree.createTree(root);//采用先序序列创建一个二叉树，空字符用#表示
//	RunLinkTree.FirstRun(root);
//	RunLinkTree.SecondRun(root);
//	RunLinkTree.LastRun(root);
	System.out.println("通过中序和后序创建二叉树");
	String s=new String();
	s=input.nextLine();
	char []zhonxu=s.toCharArray();
	
	s=input.nextLine();
	char []houxu=s.toCharArray();
	
	Node root1=tree.new Node();
	root1=tree.createNode(root1,zhonxu,houxu);//通过中序，后序创建二叉树
	RunLinkTree.FirstRun(root1);
//	tree.preTraverseBinTree(root1);
	RunLinkTree.SecondRun(root1);
	RunLinkTree.LastRun(root1);
}
}
