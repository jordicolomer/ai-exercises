package mutate;

import genome.Integer1D;

import java.util.ArrayList;
import java.util.Collections;

import main.Main;

public class MutateInvert extends Mutate{

	public void mutate(Integer1D ind, int point1, int point2) {
		if (point1 == point2) return;
		ArrayList<Integer> ls = new ArrayList();
		int p = point1;
		while(p != point2+1)
		{
			ls.add(ind.genes[p]);
			p++;
		}
    	Collections.reverse(ls);
		p = point1;
		for(int g: ls)
		{
			ind.genes[p] = g;
			p++;
		}

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
		MutateInvert m = new MutateInvert();
		Integer1D i = new Integer1D(new int[]{1,2,3,4,5,6,7});
		m.mutate(i, 1, 5);
		System.out.println("1,6,5,4,3,2,7".equals(""+i));
	}
}
