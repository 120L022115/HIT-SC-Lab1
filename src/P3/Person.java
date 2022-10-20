package P3;

import java.util.HashSet;
import java.util.Set;

public class Person {
	private static Set<String> persons = new HashSet<String>();

	private String name;	
	private Set<Person> fatherFriends = new HashSet<Person>();
	private Set<Person> childFriends = new HashSet<Person>();
	
	
	public Person(String name){
		if(persons.contains(name)) throw new IllegalArgumentException("已有名字为"+name+"的人，请更换名字后再试");
		this.name = name;
		persons.add(name);
	}
	public String getName() {
		return this.name;
	}
	public Set<Person> getFriendFather() {
		return this.fatherFriends;
	}
	public Set<Person> getFriendChild() {
		return this.childFriends;
	}
	public void addFriendFather(Person p) {
		this.fatherFriends.add(p);
	}
	public void addFriendChild(Person p) {
		this.childFriends.add(p);
	}
	public void addFriend(Person p) {
		this.fatherFriends.add(p);
		this.childFriends.add(p);
	}
	public String toString() {
		return name;
	}
	public String toStringAll() {
		Person[] arr;
		arr = this.getFriendChild().toArray(new Person[0]);
		String childs = "",fathers="";
		if(arr.length!=0)childs=arr[0].getName();
		for(int i=1;i<arr.length;i++) {
			childs+=","+arr[i].getName();
		}
		arr = this.getFriendFather().toArray(new Person[0]);
		if(arr.length!=0)childs=arr[0].getName();
		for(int i=1;i<arr.length;i++) {
			fathers+=","+arr[i].getName();
		}
		return "{\n\tname="+this.name+"\n\tchilds="+childs+"\n\tfathers="+fathers+"\n}";
	}
}
