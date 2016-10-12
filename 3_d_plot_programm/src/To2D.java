
public class To2D {
	
	public static int[] returnPlotVec(double[] observer, int[] koordinates, double frameDistance){
		if(VektorCalculus.dotP(observer,VektorCalculus.subtraction(observer,koordinates)) >= 0){
			double[] origin = {0,0,0};
			double[] toKs = VektorCalculus.subtraction(origin, observer);
			double KsDistance = VektorCalculus.norm(toKs);
			double[] toPoint = VektorCalculus.subtraction(koordinates, observer);
			double pointDistance = VektorCalculus.norm(toPoint);
			if(KsDistance != 0 && pointDistance != 0){
				toKs = VektorCalculus.multiplicate(toKs, 1/KsDistance);
				double projectionLength = VektorCalculus.dotP(toKs, toPoint);
				double[] orthVector =  VektorCalculus.multiplicate(toKs, projectionLength);

				double[] projection = VektorCalculus.subtraction(toPoint, orthVector);
				double[] YVector = createYVector(orthVector);
				double[] XVector = VektorCalculus.crossP(orthVector, YVector, true);

				// build orthogonal system
				/*double x = VektorCalculus.dotP(XVector, projection)*frameDistance/pointDistance; // for the frame
				double y = VektorCalculus.dotP(YVector, projection)*frameDistance/pointDistance;*/
				double x = VektorCalculus.dotP(XVector, projection)*frameDistance/(pointDistance*(1-(VektorCalculus.dotP(toKs, toPoint)/(pointDistance*KsDistance)))); // for the frame
				double y = VektorCalculus.dotP(YVector, projection)*frameDistance/(pointDistance*(1-(VektorCalculus.dotP(toKs, toPoint)/(pointDistance*KsDistance))));
				int[] frameKoord = {(int) x,(int) y, koordinates[0], koordinates[1], koordinates[2]};
				
				
				return frameKoord;
			}
		}
		return null;
	}
	
	public static double[] createYVector(double[] vector){
		if(vector[2] != 0){
		double i = VektorCalculus.norm(vector);
		double x = -i*i/vector[2];
		double[] vector1 = {vector[0], vector[1], x + vector[2]};
		vector1 = VektorCalculus.normVector(vector1);
		return vector1;
		}
		double[] vector1 = {0,0,1};
		return vector1;
	}
	
	
	public static void main(String[] args){
	}

}
