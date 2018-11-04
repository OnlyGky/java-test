package helloworld;

import java.util.Arrays;
import java.util.Scanner;

public class CreateTree {
	Scanner input=new Scanner(System.in);
	static int flag;//判断是不是第一个节点
	public class Node {
	    public char data;
	    public Node lchild;
	    public Node rchild;
	    Node(){};
	     Node(char data) {
	    	data=this.data;	
	    }
	}
	void createTree(Node parent) {
		char c = new java.util.Scanner(System.in).next().charAt(0);
		if(c=='#') {
			parent=null;
		}
		else {
			parent.data=c;
			parent.lchild=new Node();
			createTree(parent.lchild);
			parent.rchild=new Node();
			createTree(parent.rchild);
		}
		
	}
	public   Node createNode(Node root1,char []zhonxu,char []houxu) {
		if(houxu==null||zhonxu==null||houxu.length!=zhonxu.length) {
			return null;
		}
	     root1=buildTree(zhonxu, 0,  zhonxu.length-1, houxu,
	                0, houxu.length-1);
	      return root1;
		 				 
		
	}
	public  Node buildTree(char[] inorder, int is, int ie, char[] postorder,
            int ps, int pe) {//通过中序，后序创建二叉树
        if (is > ie || ps > pe)
            return null;
        char rootVal = postorder[pe];
        Node root = new Node();
        root.data=rootVal;
        
        for (int i = is; i <= ie; i++) {
            if (inorder[i] == rootVal) {
                Node left =new Node(); 
                		left=buildTree(inorder, is, i - 1, postorder, ps, ps+ i - is - 1);
                Node right=new Node(); 
                right= buildTree(inorder, i + 1, ie, postorder, pe- ie + i, pe - 1);
                root.lchild = left;
                root.rchild = right;
            }
        }
        return root;
    }	
}
