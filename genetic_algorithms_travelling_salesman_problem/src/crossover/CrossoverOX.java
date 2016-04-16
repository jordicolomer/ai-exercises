package crossover;

import genome.Integer1D;

import java.util.HashSet;

import main.Main;

public class CrossoverOX extends Crossover{

	public Integer1D[] crossover(Integer1D ind1, Integer1D ind2, int point1, int point2) {
		Integer1D child[] = new Integer1D[2];
		child[0] = new Integer1D();
		child[1] = new Integer1D();
		child[0].genes = new int[ind1.genes.length];
		child[1].genes = new int[ind2.genes.length];
		
		HashSet s1 = new HashSet();
		HashSet s2 = new HashSet();
		int p = point1;
		while(p != point2)
		{
			s1.add(ind2.genes[p]);
			child[0].genes[p] = ind2.genes[p];
			s2.add(ind1.genes[p]);
			child[1].genes[p] = ind1.genes[p];
			p++;
		}
		
		if (point2 == ind2.genes.length) point2 = 0;
		p = point2;
		int p1 = point2;
		int p2 = point2;
		do
		{
			//System.out.println(p);
			if (!s1.contains(ind1.genes[p]))
			{
				child[0].genes[p1] = ind1.genes[p];
				p1 = (p1+1)%ind1.genes.length;
				//System.out.println(ind1.genes[p]);
			}
			if (!s2.contains(ind2.genes[p]))
			{
				child[1].genes[p2] = ind2.genes[p];
				p2 = (p2+1)%ind2.genes.length;
				//System.out.println(ind1.genes[p]);
			}
			p = (p+1)%ind2.genes.length;
		}while(p != point2);

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

	public static void main(String[] args) {
		CrossoverOX c = new CrossoverOX();
		Integer1D[] r = c.crossover(new Integer1D(new int[]{1,3,5,7,6,2,4,8}), new Integer1D(new int[]{5,6,3,8,2,1,4,7}), 3,6);
		System.out.println("5,7,6,8,2,1,4,3".equals(""+r[0]));
		System.out.println("3,8,1,7,6,2,4,5".equals(""+r[1]));
	}

}
