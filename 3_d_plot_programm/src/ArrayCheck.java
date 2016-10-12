

public class ArrayCheck{
	public static void filter(int[][] matrix,int[] vector,int k){
		boolean occurs = false;
		for(int i = 0; i < k; i++){
			if (matrix[i] != null){
				boolean simi = checkSimilarity(matrix[i], vector);
				if (simi){
					occurs = true;
					if(matrix[i][3] >= vector[3]){
						matrix[i][2] = vector[2];
						matrix[i][3] = vector[3];
					}
					i = k;
				}
			}
		}
		if (!occurs){
			matrix[k] = vector;
		}
	}
	public static boolean checkSimilarity(int[] point, int[] vector){
		boolean similar = false;
		if (point[0] == vector[0] && point[2] == vector[2]){
			similar = true;
		}
		
		return similar;
	}
	public static void printVector(double[] vector){
		for(int i = 0; i < vector.length; i++){
			System.out.println(i +"  " + vector[i]);
		}
	}
	public static void printVector(int[] vector){
		for(int i = 0; i < vector.length; i++){
			System.out.println(i +"  " + vector[i]);
		}
	}
	
}
