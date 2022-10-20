package P3;

import org.junit.Test; 

import static org.junit.Assert.assertEquals;

public class FriendshipGraphTest {
	@Test
	public void TestGetDistance() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		
		assertEquals(1,graph.getDistance(rachel, ross)); 
		//should print 1
		assertEquals(2,graph.getDistance(rachel, ben)); 
		//should print 2
		assertEquals(0,graph.getDistance(rachel, rachel)); 
		//should print 0
		assertEquals(-1,graph.getDistance(rachel, kramer)); 
		//should print -1
	}
	@Test
	public void TestGetDistance2() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel2");
		Person ross = new Person("Ross2");
		Person ben = new Person("Ben2");
		Person kramer = new Person("Kramer2");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		//graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		//graph.addEdge(ben, ross);
		graph.addEdge(ben,kramer);
		
		
		assertEquals(1,graph.getDistance(rachel, ross)); 
		//should print 1
		assertEquals(2,graph.getDistance(rachel, ben)); 
		//should print 2
		assertEquals(0,graph.getDistance(rachel, rachel)); 
		//should print 0
		assertEquals(3,graph.getDistance(rachel, kramer)); 
		//should print -1
	}
	@Test
	public void TestAddVertex() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel3");
		Person ross = new Person("Ross3");
		Person ben = new Person("Ben3");
		Person kramer = new Person("Kramer3");
		
		assertEquals(true,graph.addVertex(rachel)); 
		assertEquals(true,graph.addVertex(ross)); 
		assertEquals(true,graph.addVertex(ben)); 
		assertEquals(true,graph.addVertex(kramer)); 
	}
	@Test
	public void TestAddEdge() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel4");
		Person ross = new Person("Ross4");
		Person ben = new Person("Ben4");
		Person kramer = new Person("Kramer4");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
	
		assertEquals(true,graph.addEdge(rachel, ross)); 
		assertEquals(true,graph.addEdge(rachel, ben)); 
		assertEquals(true,graph.addEdge(rachel, kramer)); 
		assertEquals(true,graph.addEdge(ben, ross)); 
		assertEquals(true,graph.addEdge(kramer, ross)); 
	}
}