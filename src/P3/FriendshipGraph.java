package P3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

public class FriendshipGraph {
	Set<Person> set = new HashSet<Person>();

	public FriendshipGraph() {

	}

	/**
	 * 添加节点
	 * 
	 * @param p 人
	 * @return 是否成功
	 */
	public boolean addVertex(Person p) {
		if (set.contains(p))
			throw new IllegalArgumentException("已有名字为" + p.getName() + "的人，请更换名字后再试");
		set.add(p);
		return true;
	}

	/**
	 * 添加有向边
	 * 
	 * @param s 开始人
	 * @param e 结束人
	 * @return 是否成功
	 */
	public boolean addEdge(Person s, Person e) {
		s.addFriendChild(e);
		e.addFriendFather(s);
		return true;
	}

	/**
	 * 获取距离
	 * 
	 * @param s 人1
	 * @param e 人2
	 * @return 距离
	 */
	public int getDistance(Person s, Person e) {
		if (s == null || e == null)
			throw new IllegalArgumentException("不能为空");
		if (s.equals(e))
			return 0;
		int count = 1;

		LinkedList<Person> queue = new LinkedList<Person>();
		Set<Person> hasVisit = new HashSet<Person>();
		hasVisit.add(s);
		Person p = s;
		Person[] childs;
		int len;
		queue.addLast(p);
		queue.addLast(null);
		while (!queue.isEmpty()) {
			p = queue.pollFirst();
			if (p == null) {
				count++;
				continue;
			}
			hasVisit.add(p);
			childs = p.getFriendChild().toArray(new Person[0]);
			len = childs.length;
			for (int i = 0; i < len; i++) {
				if (childs[i].equals(e))
					return count;
				if (hasVisit.contains(childs[i]))
					continue;
				queue.addLast(childs[i]);
			}
			queue.addLast(null);
		}
		return -1;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, Person> map = new HashMap<String, Person>();
		FriendshipGraph graph = new FriendshipGraph();
		Scanner sn = new Scanner(System.in);
		System.out.println("欢迎来到友情图！");
		System.out.println("使用以下命令来操作：");
		System.out.println("new name //创建一个新人物");
		System.out.println("add name //把一个人物加入图");
		System.out.println("dis name1 name2 //计算两个人物的距离");
		System.out.println("edg name1 name2 //添加name1到name2的友情线");
		System.out.println("info name1 //查看name1人的信息");
		System.out.println("exit //退出");
		while (true) {
			System.out.print(">>");
			String input = sn.nextLine();
			if(input.equals("exit")) {
				sn.close();
				break;
			}
			String[] arr;
			try {
				arr = input.split(" ");
			} catch (PatternSyntaxException e) {
				System.out.println("命令错误！");
				continue;
			}
			//System.out.println(Arrays.toString(arr));
			if (arr.length == 0)
				continue;
			try {
				try {
					if (arr[0].equals("new")) {
						if (arr.length > 2) {
							System.out.println("命令错误！");
							continue;
						}
						map.put(arr[1], new Person(arr[1]));

						System.out.println(arr[1]+" 已创建");
					}else if(arr[0].equals("add")) {
						if (arr.length > 2) {
							System.out.println("命令错误！");
							continue;
						}
						Person p = map.get(arr[1]);
						if(p==null) {
							System.out.println("没有这个人！请重新输入");
							continue;
						}
						graph.addVertex(p);
						System.out.println(p.getName()+" 已添加");
						
					}else if(arr[0].equals("info")) {
						if (arr.length > 2) {
							System.out.println("命令错误！");
							continue;
						}
						Person p = map.get(arr[1]);
						if(p==null) {
							System.out.println("没有这个人！请重新输入");
							continue;
						}
						System.out.println(p.toStringAll());
						
					}else if(arr[0].equals("edg")) {
						if (arr.length > 3) {
							System.out.println("命令错误！");
							continue;
						}
						Person s = map.get(arr[1]), e = map.get(arr[2]);
						if(s==null||e==null) {
							System.out.println("没有这个人！请重新输入");
							continue;
						}
						graph.addEdge(s, e);
						System.out.println(s.getName()+"->"+e.getName()+" 已添加");
					}else if(arr[0].equals("dis")) {
						if (arr.length > 3) {
							System.out.println("命令错误！");
							continue;
						}
						Person s = map.get(arr[1]), e = map.get(arr[2]);
						if(s==null||e==null) {
							System.out.println("没有这个人！请重新输入");
							continue;
						}
						int i = graph.getDistance(s, e);
						if(i>=0) {
							System.out.println(s.getName()+"->"+e.getName()+"的距离是："+i);
						}else {
							System.out.println(s.getName()+"->"+e.getName()+"没有路径！");
						}
						
					}else {
						System.out.println("命令错误！");
						continue;
					}
				} catch (IndexOutOfBoundsException e) {
					System.out.println("命令错误！");
					continue;
				}
			} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
					continue;
			}

		}

	}

}
