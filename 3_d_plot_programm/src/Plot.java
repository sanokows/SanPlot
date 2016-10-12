import java.util.ArrayList;

public class Plot {
	static int pixel = 20; // pixel which are supposed to fit into one step (x or y step) in the plot matrix;
	public int kind; // 0 == polar // 1 == kartesian
	public Funktion f;
	public ArrayList<Square> squares; 
	public int matrixCounter;
	
	public boolean polar;
	
	//karthesian
	public int sizeX;
	public int sizeY;
	public int dsizeX;
	public int dsizeY;
	
	//polar
	public double alpha;
	public double beta;
	//accuracy
	
	public int asquares = 20;
	public int bsquares = 60;
	
	public int maxY;
	public int maxZ;
	public int maxX;
	public int minZ;
	public int minY;
	public int minX;

	
	public Plot(double x, double y, boolean polar){ // if polar == true x is alpha y is beta

		this.polar = polar;
		if(!polar){
			this.sizeX = (int) x*pixel;
			this.sizeY = (int) y*pixel;
			this.dsizeX = 2*sizeX;
			this.dsizeY = 2*sizeY;
			this.minX = -sizeX;
			this.minY = -sizeY;
			this.maxX = sizeX;
			this.maxY = sizeY;
			this.squares = new ArrayList(); //consider negative values!

		}else{
			this.alpha = x;
			this.beta = y; //consider negative values!
			this.polar();
		}
	}

	public void polar(){
		long startTime = System.currentTimeMillis();
		double[] vector1 = new double[3];
		double[] vector2 = new double[3];
		double[] vector3 = new double[3];
		double[] vector4 = new double[3];
		double pi = 2* Math.PI;
		double istep = this.alpha/((double)asquares);
		double jstep = this.beta/((double)bsquares);
		this.maxZ = 1;
		this.minZ = -1;
		for(double i = 0; i < this.alpha ; i+=istep){
			for(double j = 0 ; j < this.beta; j+=jstep){
				vector1 = calc(i,j);
				vector1 = VektorCalculus.multiplicate(vector1, pixel);
				vector2 = calc(i+istep,j);
				vector2 = VektorCalculus.multiplicate(vector2, pixel);
				vector3 = calc(i+istep,j+jstep);
				vector3 = VektorCalculus.multiplicate(vector3, pixel);
				vector4 = calc(i,j+jstep);
				vector4 = VektorCalculus.multiplicate(vector4, pixel);
				if(i + istep > this.alpha){
					vector1 = calc(i,j);
					vector1 = VektorCalculus.multiplicate(vector1, pixel);
					vector2 = calc(0,j);
					vector2 = VektorCalculus.multiplicate(vector2, pixel);
					vector3 = calc(0,j+jstep);
					vector3 = VektorCalculus.multiplicate(vector3, pixel);
					vector4 = calc(i,j+jstep);
					vector4 = VektorCalculus.multiplicate(vector4, pixel);

				}
				double[][] vectors = {vector1, vector2, vector3, vector4};
				if(this.squares == null){
					this.squares = new ArrayList<Square>();
				}
				this.squares.add(new Square(vectors));
				checkIntersections();
			}
			
		}

		
		System.out.println("Loading time: " + (System.currentTimeMillis() - startTime) + " ms");
	}
	public void checkIntersections(){
		for(int i = 0; i < this.squares.size(); i++){
			for(int j = i+1; j < this.squares.size(); j++){
				
				hasIntersection(this.squares.get(i), this.squares.get(j));
				
			}
		}
	}
	public void hasIntersection(Square one, Square two){
		//schneiden sich die ebenen?
		Square[] s = new Square[2];
		s[0] = one;
		s[1] = two;
		if(!(one != null && two != null && s[1].head.next.next != null && s[0].head.next.next != null && s[1].head != null && s[0].head != null)){
		//erste ebene
		double[] a = s[0].head.vector; //Aufpunkt
		double[] u = VektorCalculus.subtraction(s[0].head.next.vector, s[0].head.vector);
		double[] v = VektorCalculus.subtraction(s[0].head.next.next.vector,s[0].head.vector);

		//zweite ebene
		double[] p = s[1].head.vector; ; //Aufpunkt
		double[] w = VektorCalculus.subtraction(s[1].head.next.vector, s[1].head.vector);
		double[] z = VektorCalculus.subtraction(s[1].head.next.next.vector,s[1].head.vector);
		
		double[] n = VektorCalculus.crossP(w, z, true);
		
		if(VektorCalculus.dotP(n,u) != 0 && VektorCalculus.dotP(n,v) != 0){
			for(int i = 0; i < 2; i++){
				if(VektorCalculus.dotP(n,u) != 0){
					addToSquares(calcMu(v,n,u,a,p,s[i],0));
				}else{
					addToSquares(calcMu(u,n,v,a,p,s[i],0));
				}
			}
		}
	}
	}
	public boolean areNeightbours(Square one, Square two){ // takes too long also unnecessary?
		Node head = one.head;
		while(head != null){
			Node head2 = two.head;
			while(head2 != null){
			if(VektorCalculus.compareVector(head.vector, head2.vector)){
				return true;
			}
			head2 = head2.next;
			}
			head = head.next;
		}
		
		return false;
	}
	
	public void addToSquares(ArrayList<Intersection> p){
		if(p.size() == 2){
			// creates new Square
			Node n1 = new Node(p.get(0).point);
			n1.next = p.get(0).p1.next;
			Square s = new Square(null);
			s.newHead(n1);
			this.squares.add(s);
			//
			Node n2 = new Node(p.get(0).point);
			p.get(0).p1.next = n2;
			n2.next = new Node(p.get(1).point);
			Node l = n2.next;
			l.next = p.get(1).p1.next;
			p.get(1).p1.next = null;
		}
		}
	
	
	public ArrayList<Intersection> calcMu(double[] v,double[] n, double[] u, double[] a, double[]p, Square one, int s){ // s has to be 0 or 2
		ArrayList<Intersection> intersections = new ArrayList<Intersection>();
		// schnittgerade
		double[] l = new double[3];
		for(int i = 0; i < 3; i++){
			l[i] = (v[i] - (VektorCalculus.dotP(n, v)/VektorCalculus.dotP(n, u))*u[i]);
		}
		double[] b = new double[3];
		for(int i = 0; i < 3; i++){
			b[i] = (a[i]+((VektorCalculus.dotP(p,n)-VektorCalculus.dotP(a,n))/VektorCalculus.dotP(u,n))*u[i]);
		}
		
		// schnittpunkt gerade ebene
		Node h = one.head;
		while(h != null){
			double[] au = h.vector; // aufpunkt
			double[] or;
			if(h.next == null){
				or = one.head.vector;
			}else{
				or = h.next.vector;// orientation
			}
			double[] c = VektorCalculus.subtraction(or, au);
	
			// in case wrong vektor
			double[] r = VektorCalculus.crossP(c, l, false);
			double mu = 1;
			if(VektorCalculus.norm(r) != 0){
				if(c[0]*l[1]-c[1]*l[0] != 0){
					mu = (b[0]*l[1]+l[0]*au[1]-b[1]*l[0]-l[1]*au[0])/(c[0]*l[1]-c[1]*l[0]); //nenner gleich null betrachten!
				}if(c[1]*l[2]-c[2]*l[1] != 0){
					mu = (b[1]*l[2]-b[2]*l[1]-a[1]*l[2]+a[2]*l[1])/(c[1]*l[2]-c[2]*l[1]);
				}if(c[0]*l[2]-c[2]*l[0]!= 0){
					mu = (b[0]*l[2]-b[2]*l[0]-a[0]*l[2]+a[2]*l[0])/(c[0]*l[2]-c[2]*l[0]);
				}
			}
			if(mu > 0 && mu < 1){
				double[] inter = VektorCalculus.add(au, VektorCalculus.multiplicate(c, mu));
				Intersection k = new Intersection(h, inter);
				intersections.add(k);
			}
			h = h.next;
		}
		return intersections;
	}
	
	public double[] calc(double i, double j){
		double pi = Math.PI * 2;
		double[] vector = new double[3];
		vector[0] = 5 *(Math.pow(Math.E, j/(pi)) * Math.cos(j) - (Math.pow(Math.E, j/(pi)) - Math.pow(Math.E, j/(pi) - 2* Math.PI))/2 * Math.cos(j) * Math.cos(i));
		vector[1] = 5* (Math.pow(Math.E, j/(pi)) * Math.sin(j) - (Math.pow(Math.E, j/(pi)) - Math.pow(Math.E, j/(pi) - 2* Math.PI))/2 * Math.sin(j) * Math.cos(i));
		vector[2] = 5*(Math.pow(Math.E, j/(pi)) - Math.pow(Math.E, j/(pi)- 2* Math.PI))/2 * Math.sin(i);
		return vector;
	}
	
	public void karthesian(double [] vector1){
		int x = (int) vector1[0];
		int y = (int) vector1[1];
		if((x + this.sizeX >= 0 && x + this.sizeX < this.dsizeX && y + this.sizeY < this.dsizeY && y + this.sizeY >= 0)){

			int z = (int)(pixel * vector1[2]);

		}
	}
	
	
	
	public void createMinandMaxZ(int z){
		if (z > this.maxZ){
			this.maxZ = z;
		}
		if(z<this.minZ){
			this.minZ = z;
		}
	}
	
	public void createKs(){
	//create line
	for(int i = this.minX-pixel; i < this.maxX+pixel; i++){
		
	}
		
	//create tip
	}
	
	
}
