package crossover;
import genome.Integer1D;
import main.Main;


public class CrossoverPMX extends Crossover{

	public Integer1D[] crossover(Integer1D ind1, Integer1D ind2, int point1, int point2)
	{
		Integer1D child[] = new Integer1D[2];
		child[0] = new Integer1D();
		child[0].genes = ind1.genes.clone();
		child[1] = new Integer1D();
		child[1].genes = ind2.genes.clone();

		for(int i = point1; i < point2; i++)
		{
			int p1 = 0;
			int p2 = 0;
			for(int j = 0; j < ind1.genes.length; j++)
			{
				if (ind1.genes[i] == child[0].genes[j]) p1 = j;
				if (ind2.genes[i] == child[0].genes[j]) p2 = j;
			}
			int aux = child[0].genes[p1];
			child[0].genes[p1] = child[0].genes[p2];
			child[0].genes[p2] = aux;

			for(int j = 0; j < ind1.genes.length; j++)
			{
				if (ind1.genes[i] == child[1].genes[j]) p1 = j;
				if (ind2.genes[i] == child[1].genes[j]) p2 = j;
			}
			aux = child[1].genes[p1];
			child[1].genes[p1] = child[1].genes[p2];
			child[1].genes[p2] = aux;
		}
		return child;
	}

	public Integer1D[] crossover(Integer1D ind1, Integer1D ind2) {
		int point1 = Main.random.nextInt(ind1.genes.length+1);
		int point2 = Main.random.nextInt(ind1.genes.length+1);
		int tmp;
		if (point1 > point2){
			tmp = point2;
			point2 = point1;
			point1 = tmp;
		}
		return crossover(ind1, ind2, point1, point2);
	}

	public static void main(String args[])
	{
		CrossoverPMX c = new CrossoverPMX();
		Integer1D[] r = c.crossover(new Integer1D(new int[]{1,2,3,4,5,6,7,8}), new Integer1D(new int[]{8,5,2,1,3,6,4,7}),4,7);
		System.out.println("1,2,5,7,3,6,4,8".equals(""+r[0]));
		System.out.println("8,3,2,1,5,6,7,4".equals(""+r[1]));
	}
}
