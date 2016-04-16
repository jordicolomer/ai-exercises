package crossover;

import genome.Integer1D;

import java.util.HashSet;

import main.Main;

public class CrossoverMOX extends Crossover{

	public Integer1D[] crossover(Integer1D ind0, Integer1D ind1, int point1) {
		Integer1D child[] = new Integer1D[2];
		child[0] = new Integer1D();
		child[1] = new Integer1D();
		child[0].genes = ind0.genes.clone();
		child[1].genes = ind1.genes.clone();

		//child[0].print();
		//child[1].print();

		{
			HashSet set = new HashSet();
			for(int i=point1;i<ind0.genes.length;i++)
			{
				set.add(ind0.genes[i]);
			}

			int p = point1;
			for(int i=0;i<ind1.genes.length;i++)
			{
				if (set.contains(ind1.genes[i])) child[0].genes[p++] = ind1.genes[i];
			}
		}
		{
			HashSet set = new HashSet();
			for(int i=point1;i<ind0.genes.length;i++)
			{
				set.add(ind1.genes[i]);
			}

			int p = point1;
			for(int i=0;i<ind0.genes.length;i++)
			{
				if (set.contains(ind0.genes[i])) child[1].genes[p++] = ind0.genes[i];
			}
		}

		//child[0].print();
		//child[1].print();

		return child;
	}

	public Integer1D[] crossover(Integer1D ind1, Integer1D ind2) {
		int point1 = Main.random.nextInt(ind1.genes.length+1);
		return crossover(ind1, ind2, point1);
	}

	public static void main(String[] args) {
		//Main.random.setSeed(0);
		CrossoverMOX c = new CrossoverMOX();
		//Double1D[] r = c.crossover(new Double1D(new double[]{1,2,3,4,5,6}), new Double1D(new double[]{3,6,4,2,1,5}), 3,6);
		Integer1D[] r = c.crossover(new Integer1D(new int[]{1,2,3,4,5,6}), new Integer1D(new int[]{3,6,4,2,1,5}),3);
		System.out.println("1,2,3,6,4,5".equals(""+r[0]));
		System.out.println("3,6,4,1,2,5".equals(""+r[1]));
	}

}
