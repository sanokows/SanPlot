
public class TestFunktion extends Funktion {

	public TestFunktion(double base, double a, double b, double d) {
		super(base, a, b, d);
	}
	
	@Override
	public double calc(float x, float y){
		double a = 0.2;
		double f = 0;
		try{
			f = (Math.sin(x)) * 100;
			//f = (Math.sin(x * a) + Math.cos(y * a)) * 100;
		}catch(Exception e){
			f = 3000;
		}
		return f;
	}
}
