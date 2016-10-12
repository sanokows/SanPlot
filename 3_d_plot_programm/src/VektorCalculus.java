
public class VektorCalculus {
	public static int[] fromDoubletoInt(double[] vector1){
		int[] vector2 = new int[vector1.length];
		for(int i = 0; i < vector1.length; i++){
			vector2[i] = (int) vector1[i];
		}
		
		return vector2;
	}
	
	public static double[] fromIntToDouble(int[] vector1){
		double[] vector2 = new double[vector1.length];
		for(int i = 0; i < vector1.length; i++){
			vector2[i] = vector1[i];
		}
			
		return vector2;
	}
	
	public static int[] add(int[] vector1, int[] vector2){
		for(int i = 0; i < vector1.length; i++){
			vector1[i] = vector2[i] + vector1[i];
		}
		return vector1;
	}
	
	public static double[] add(double[] vector1, double[] vector2){
		for(int i = 0; i < vector1.length; i++){
			vector1[i] = vector2[i] + vector1[i];
		}
		return vector1;
	}
	
	public static double[] normVector(double[] x_proto){
		double normVector =  Math.sqrt(Math.pow(x_proto[0], 2) + Math.pow(x_proto[1], 2) + Math.pow(x_proto[2], 2));
		double[] vector1 = {x_proto[0] / normVector, x_proto[1] / normVector, x_proto[2] / normVector};
		return vector1;
	}
	public static double[] subtraction(double[] vector, int[] vector1){
		double[] vector2 = new double[vector.length];
		if (vector.length == vector1.length){
			for (int i = 0; i < vector.length; i++){
				vector2[i] = vector[i] - vector1[i];
			}
		}
		return vector2;
	}
	public static double[] subtraction(int[] vector, int[] vector1){
		double[] vector2 = new double[vector.length];
		if (vector.length == vector1.length){
			for (int i = 0; i < vector.length; i++){
				vector2[i] = vector[i] - vector1[i];
			}
		}
		return vector2;
	}
	public static int[] subtract(int[] vector, int[] vector1){
		int[] vector2 = new int[vector.length];
		if (vector.length == vector1.length){
			for (int i = 0; i < vector.length; i++){
				vector2[i] = vector[i] - vector1[i];
			}
		}
		return vector2;
	}
	public static double[] subtraction(int[] vector, double[] vector1){
		double[] vector2 = new double[vector.length];
		if (vector.length == vector1.length){
			for (int i = 0; i < vector.length; i++){
				vector2[i] = vector[i] - vector1[i];
			}
		}
		return vector2;
	}
	public static double[] subtraction(double[] vector, double[] vector1){
		double[] vector2 = new double[vector.length];
		if (vector.length == vector1.length){
			for (int i = 0; i < vector.length; i++){
				vector2[i] = vector[i] - vector1[i];
			}
		}
		return vector2;
	}
	
	

	public static double norm(double[] subtracted){
		double norm = 0;
		for(int i = 0; i<subtracted.length; i++){
			norm += Math.pow(subtracted[i], 2);
		}
		norm = Math.sqrt(norm);
		return norm;
	}
	public static double norm(int[] subtracted){
		double norm = 0;
		for(int i = 0; i < subtracted.length; i++){
			norm += Math.pow(subtracted[i], 2);
		}
		norm = Math.sqrt(norm);
		return norm;
	}
	public static void printVec(double[] vector){
		for(int i = 0; i < vector.length; i++){
			System.out.println(i + " " + vector[i]);
		}
	}
	
	public static double[] scalar(double[] x_vector){
		double len_vector = Math.sqrt(Math.pow(x_vector[0], 2) + Math.pow(x_vector[1], 2) + Math.pow(x_vector[2], 2));
		double[] vector1 = {(- x_vector[1] / len_vector), (x_vector[0] / len_vector), x_vector[2]};
		return vector1;
	}
	public static double[] scalar(int[] x_vector){
		double len_vector = Math.sqrt(Math.pow(x_vector[0], 2) + Math.pow(x_vector[1], 2) + Math.pow(x_vector[2], 2));
		double[] vector1 = {(- x_vector[1] / len_vector), (x_vector[0] / len_vector), x_vector[2]};
		return vector1;
	}

	public static double dotP(double[] vector, double[] vector1){
		double scalar = 0;
		for(int i = 0; i< vector.length; i++){
			scalar += vector[i]*vector1[i];
		}
		return scalar;
	}
	public static double dotP(double[] vector, int[] vector1){
		double scalar = 0;
		for(int i = 0; i< vector.length; i++){
			scalar += vector[i]*vector1[i];
		}
		return scalar;
	}
	public static double[] crossP(double[] x_vector, double[] y_vector, boolean normed){
		double[] vector3 = {x_vector[1] * y_vector[2] - x_vector[2] * y_vector[1], - (x_vector[0] * y_vector[2] - x_vector[2] * y_vector[0]), (x_vector[0] * y_vector[1] - x_vector[1] * y_vector[0])};
		double[] vector4 = new double[3];
		if (normed){
			double len_vector3 = Math.sqrt(Math.pow(vector3[0], 2) + Math.pow(vector3[1], 2) + Math.pow(vector3[2], 2));
			for (int i = 0; i < 3; i++){
				vector4[i] = vector3[i] / len_vector3;
			}
		} else {
			for (int i = 0; i < 3; i++){
				vector4[i] = vector3[i];
			}
		}
		return vector4;
	}

	public static double[] rotate(double[] vector, double rad){
		double[] vector2 = new double[3];
		vector2[0] = vector[0] * Math.cos(rad) - vector[1] * Math.sin(rad);
		vector2[1] = vector[0] * Math.sin(rad)  + vector[1] * Math.cos(rad);
		vector2[2] = vector[2];
		return vector2;
	}
	
	public static double[] rotate(double[] vector, double[] n,double rad){
		double[] vector2 = new double[3];
		//predifine for less calculation
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		double a = 1 - cos;
		
		vector2[0] = (Math.pow(n[0],2)*(a)+cos)*vector[0]+(n[0]*n[1]*(a)-n[2]*sin)*vector[1]+(n[0]*n[2]*(a)+n[1]*sin)*vector[2];
		vector2[1] = (n[0]*n[1]*(a)+n[2]*sin)*vector[0]+(n[1]*n[1]*(a)+Math.cos(rad))*vector[1]+(n[1]*n[2]*(a)-n[0]*sin)*vector[2];
		vector2[2] = (n[2]*n[0]*(a)-n[1]*sin)*vector[0]+(n[2]*n[1]*(a)+n[0]*sin)*vector[1]+(n[2]*n[2]*(a)+cos)*vector[2];
		return vector2;
	}
	
	public static double[] multiplicate(double[] vector, double k){
		double[] vector2 = new double[vector.length];
		for(int i = 0; i < vector.length; i++){
			vector2[i] = vector[i]*k;
		}
		return vector2;
		
	}
	
	public static int[] multiply(int[] vector, double k){
		int[] vector2 = new int[vector.length];
		for(int i = 0; i < vector.length; i++){
			vector2[i] = (int) (vector[i]*k);
		}
		return vector2;
		
	}
	public static boolean compareVector(double[] v1, double[] v2){
		int s = 0;
		for(int i = 0; i < v1.length; i++){
			if(v1[i] == v2[i]){
				s+=1;
			}
		}
		if(s == 3){
			return true;
		}
		return false;
	}
	public static void main(String[] args){

	}
}
