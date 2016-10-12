
public class Square { // Squares should be polygons
	Node head;
	public Square(double[][] points){
		if(points!= null){
		if(points.length > 2)
		this.head = new Node(points[0]);
		Node h = head;
		for(int i = 1; i< points.length; i++){
			h.next = new Node(points[i]);
			h = h.next;
		}
		}
		
	}
	public void newHead(Node h){
		this.head = h;
	}
	public int length(){
		Node h = this.head;
		int s = 0;
		while(h != null){
			s += 1;
			h = h.next;
		}
		return s;
	}
	
	public static void main(String[] args){
		/*double[][] points = new double[10][3];
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				points[i][j] = 2;
			}
		}
		
		Square q = new Square(points);
		Node h = q.head;
		while(h != null){
			System.out.println(h);
			h = h.next;
		}*/
	}
	
}
