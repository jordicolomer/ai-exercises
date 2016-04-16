package crossover;

import genome.Integer1D;

public class CrossoverCX extends Crossover{

	public int indexOf(int v[], int x)
	{
		for(int i=0;i<v.length;i++) if (v[i] == x) return i;
		return -1;
	}

	public Integer1D[] crossover(Integer1D ind1, Integer1D ind2)
	{
		Integer1D child[] = new Integer1D[2];
		child[0] = new Integer1D();
		child[1] = new Integer1D();
		child[0].genes = new int[ind1.genes.length];
		child[1].genes = new int[ind2.genes.length];

		for(int i=0;i<child[0].genes.length;i++) child[0].genes[i] = Integer.MAX_VALUE;
		for(int i=0;i<child[1].genes.length;i++) child[1].genes[i] = Integer.MAX_VALUE;
		
		int p = 0;
		do{
			child[0].genes[p] = ind1.genes[p];
			child[1].genes[p] = ind2.genes[p];
			p = indexOf(ind1.genes,ind2.genes[p]);
		}while(p!=0);

		for(int i=0;i<child[0].genes.length;i++) if (child[0].genes[i] == Integer.MAX_VALUE)
		{
			child[0].genes[i] = ind2.genes[i];
			child[1].genes[i] = ind1.genes[i];
		}

		return child;
	}

	public static void main(String[] args) {
		Crossover c = new CrossoverCX();
		Integer1D[] r = c.crossover(new Integer1D(new int[]{1,2,3,4,5,6,7,8}), new Integer1D(new int[]{8,5,2,1,3,6,4,7}));
		System.out.println("1,5,2,4,3,6,7,8".equals(""+r[0]));
		System.out.println("8,2,3,1,5,6,4,7".equals(""+r[1]));
	}

}
