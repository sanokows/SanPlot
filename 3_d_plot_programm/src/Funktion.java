
public class Funktion {
	double base;
	double a;
	double b;
	double d;
	
	public Funktion(double base, double a, double b, double d){
		this.base = base;
		this.a = a;
		this.b = b;
		this.d = d;
	}
	
	public double calc(float x, float y){
		double f = this.a * Math.pow(this.base, y * x * this.b) + this.d;
		return f;
	}
}
