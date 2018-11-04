package helloworld;

import java.util.Stack;

import helloworld.CreateTree.Node;



public class RunLinkTree {//先序遍历
	public static void FirstRun(Node root) {
		System.out.println("先序遍历");
		Stack<Node> NodeStack=new Stack<Node>();
		Node node=root;
		while(node!=null||!NodeStack.isEmpty()) {
			while(node!=null) {
				if(node.data!=' ')
				System.out.print(node.data);
				
				NodeStack.push(node);
				node=node.lchild;
			}
			if(!NodeStack.isEmpty()) {
				node=NodeStack.pop();
				node=node.rchild;
			}
		}
	System.out.println();	
	}
	public static void SecondRun(Node root) {//中序遍历
		System.out.println("中序遍历");
		Stack<Node> NodeStack=new Stack<Node>();
		while(root!=null||!NodeStack.isEmpty()) {
			while(root!=null) {
				NodeStack.push(root);
				root=root.lchild;
			}
			if(!NodeStack.isEmpty()) {
				root=NodeStack.pop();
				System.out.print(root.data+" ");
				root=root.rchild;//如果该节点只有右子树，用于输出右子树
			}
		}
		System.out.println();
	}
	public static void LastRun(Node root) {//后序遍历
		System.out.println("后序遍历");
		Node lastVisit=root;
		Stack<Node> NodeStack=new Stack<Node>();
		while(root!=null||!NodeStack.isEmpty()) {
		while(root!=null) {
			NodeStack.push(root);
			root=root.lchild;
		}
		root = NodeStack.peek();//查看当前栈顶是否有为空或者已经被确认过
        //如果其右子树也为空，或者右子树已经访问
        //则可以直接输出当前节点的值
        if (root.rchild == null || root.rchild == lastVisit) {
            System.out.print(root.data + " ");
            NodeStack.pop();
            lastVisit = root;
            root = null;
        } else {
            //否则，继续遍历右子树
            root = root.rchild;
        }
		}
		System.out.println();
	}	
}
