package mutate;

import genome.Integer1D;
import main.Main;

public class MutateInsert extends Mutate{

	public void mutate(Integer1D ind, int point1, int point2) {
		if (point1 == point2) return;
    	int aux = ind.genes[point2];
    	int p = point2-1;
    	while(p!=point1)
    	{
    		ind.genes[p+1] = ind.genes[p];
    		p--;
    	}
    	ind.genes[point1+1] = aux;
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
		MutateInsert m = new MutateInsert();
		Integer1D i = new Integer1D(new int[]{1,2,3,4,5});
		m.mutate(i, 0, 3);
		System.out.println("1,4,2,3,5".equals(""+i));
	}

}
