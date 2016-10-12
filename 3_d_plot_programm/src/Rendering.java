import java.util.ArrayList;

public class Rendering {
	public static ArrayList<int[][]> sortSquares(ArrayList<int[][]> list){
		int sort = 0;
		for(int i = 0; i < list.size()-1; i++){
			for(int j = i + 1; j < list.size(); j++){
					int s = checkForIntersection(list.get(i),list.get(j));
					if(s==-1){
						sort += 1;
					}
					if(s == 1){
						int[][] obj = list.get(i);
						int[][] obj2 = list.get(j);
						list.set(j, obj);
						list.set(i, obj2);
					}
					if(s == 0){
						int[][] obj3 = list.get(j);
						list.remove(obj3);
						if(list.size() > i+1){
							list.add(i+1, obj3);
						}else{
							list.add(obj3);
						}
						
					}
			}
				
		}
		System.out.println(sort + " " + list.size());
		
		return list;
	}
	
	public static int checkForIntersection(int[][] one, int[][] two){ // returns 1 if two is in front of one
		// 1.step Check relevance
		int[][][] list = {one, two};
		int s = 0; // represents whether v1 is inside one or two if s = 0 it is inside of one else in two
		double[] v1 = findOverleap(one,two);
		if(v1 == null){
			s = 1;
			v1 = findOverleap(two,one);
		}
		return returnSortNumber(list, s, v1);
	
	}
	
	public static int returnSortNumber(int[][][] list, int s, double[] v1){
		int returnNumber = 0;
		if(v1 != null){
			double[] p = Game.observer;
			double[] a = new double[3];
			a[0] = list[s][0][2];
			a[1] = list[s][0][3];
			a[2] = list[s][0][4];
			double[] v = VektorCalculus.subtraction(v1, p);
			double[] u1 = {list[s][1][2]-list[s][0][2], list[s][1][3]-list[s][0][3], list[s][1][4]-list[s][0][4]};
			double[] u2 = {list[s][2][2]-list[s][0][2], list[s][2][3]-list[s][0][3], list[s][2][4]-list[s][0][4]};
			double[] n = VektorCalculus.crossP(u1, u2, false);
			double d = VektorCalculus.dotP(n, v);
			double t = -1;
			if(d != 0){
				t = (VektorCalculus.dotP(n, a) - VektorCalculus.dotP(n, p))/VektorCalculus.dotP(n, v);
			}
			if(d == 0){
				return 0;
			}
			if(t >= 0 && t < 1){
				if(s == 1){
					returnNumber = -1;
				}else{
					returnNumber = 1;
				}
			 }
			if(t > 1 || t < 0){
				if(s == 1){
					returnNumber = 1;
				}else{
					returnNumber = -1;
				}
			}
		 }
		 return returnNumber;
	}
	
	public static int compareSquares(int[][] one, int[][] two){
		double[] s = findOverleap(one, two);
		
		return 0;
	}
	
	public static double[] findOverleap(int[][] one, int[][] two){
		double[] v1 = new double[3];
		
		int[] a = one[0];
		int[] ab = VektorCalculus.subtract(one[1], one[0]);
		int[] ac = VektorCalculus.subtract(one[2], one[0]);
		
		for(int i = 0; i < two.length; i++){
			int[] p =  {two[i][0], two[i][1]};
			double beta = 0;
			double alpha = 0;
			if(ac[0]*ab[1]-ac[1]*ab[0] != 0){
				beta = (a[0]*ab[1]-a[1]*ab[0]-p[0]*ab[1]+p[1]*ab[0])/(ac[0]*ab[1]-ac[1]*ab[0]);
			}
			if(ab[0]*ac[1]-ab[1]*ac[0]!= 0){
				alpha = (a[0]*ac[1]-a[1]*ac[0]-p[0]*ac[1]+p[1]*ac[0])/(ab[0]*ac[1]-ab[1]*ac[0]);
			}
			if(beta >= 0 && beta <= 1 && alpha >= 0 && alpha <= 1){
				if(v1 == null){
					v1 = new double[3];
				}
				v1[0] = two[i][2];
				v1[1] = two[i][3];
				v1[2] = two[i][4];
				i = two.length;
			}else{
				v1 = null; // important
			}
		}
		
		return v1;
	}
	
	
}


