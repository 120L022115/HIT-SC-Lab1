package test;

import java.util.ArrayList;
import java.util.List;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		var strs = new ArrayList<String>();
		strs.add("HIT1");
		strs.add("HIT2");
		strs.add("HIT3");
		for(var s : strs) {
			if(s.startsWith("HIT")) {
				//strs.remove(s);
			}
			System.out.println(strs);
		}
		String ss = "1";
		ss= ss+false+1+0.1;
		System.out.println(ss);
		Number a =10;
		System.out.println(a instanceof Integer);
		System.out.println(a instanceof Number);
		var sb1= new StringBuilder("Aaa");
		var sb2= new StringBuilder("Aaa");
		System.out.println(sb1.equals(sb2));
		
		c va = new c();
		va.bs();
				
	}

}




interface a {
	Object n(Number a);
	List<Object> aa();
}
abstract class b implements a{
	abstract void bs();
	
	@Override
	public String n(Number a) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

public interface ccc{
	
}

class c extends b{

	public void bs(int i) {
		
	}
	
	@Override
	void bs() {
		// TODO Auto-generated method stub
		
	}
	
}