package mutate;

import genome.Integer1D;
import main.Main;

public class MutateSwap extends Mutate{

	public void mutate(Integer1D ind, int point1, int point2) {
    	int aux = ind.genes[point1];
    	ind.genes[point1] = ind.genes[point2];
    	ind.genes[point2] = aux;
	}
	
	public void mutate(Integer1D ind) {
		int point1 = Main.random.nextInt(ind.genes.length);
		int point2 = Main.random.nextInt(ind.genes.length);
		int tmp;
		if (point1 > point2){
			tmp = point2;
			point2 = point1;
			point1 = tmp;
		}
		mutate(ind, point1, point2);
	}


	public static void main(String[] args) {
		MutateSwap m = new MutateSwap();
		Integer1D i = new Integer1D(new int[]{1,2,3,4,5});
		m.mutate(i, 1, 3);
		System.out.println("1,4,3,2,5".equals(""+i));
	}
}
