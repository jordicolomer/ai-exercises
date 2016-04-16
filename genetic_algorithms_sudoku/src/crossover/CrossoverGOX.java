package crossover;

import genome.Integer1D;

import java.util.Arrays;

import main.Main;

public class CrossoverGOX extends Crossover{
	
	public int[] getIndex(int x[]){
		int ret[] = new int[x.length];
		for(int i=1;i<x.length;i++){
			int j = i-1;
			while (j >= 0 && x[j] != x[i]) j--;
			if (j>=0) ret[i] = ret[j]+1;
		}
		return ret;
	}

	
	// implements the GOX crossover proposed in the paper:
	// Bierwirth, Christian. ``A generalized permutation approach to job shop scheduling with genetic algorithms.'' Operations-Research-Spektrum 17.2-3 (1995): 87-92.
	public Integer1D crossover1(Integer1D ind1, Integer1D ind2, int point1, int len) {
		int index1[] = getIndex(ind1.genes);
		int index2[] = getIndex(ind2.genes);
		
		int insert = -1;
		int del[] = new int[ind1.genes.length];
		for(int i=point1;i<point1+len;i++){
			for(int j=0;j<ind1.genes.length;j++){
				if (ind2.genes[i%ind1.genes.length] == ind1.genes[j] && index2[i%ind1.genes.length] == index1[j]){
					del[j] = 1;
					if (i==point1) insert = j;
					break;
				}
			}
		}
		int ret[] = new int[ind1.genes.length];
		int p=0;
		if (point1+len>ind1.genes.length){ // overlap
			for(int i=point1;i<point1+len;i++){
				if (i>=ind1.genes.length) ret[p++] = ind2.genes[i%ind1.genes.length];
			}
			for(int i=0;i<ind1.genes.length;i++){
				if (del[i]==0) ret[p++] = ind1.genes[i];
			}
			for(int i=point1;i<point1+len;i++){
				if (i<ind1.genes.length) ret[p++] = ind2.genes[i%ind1.genes.length];
			}
		}
		else{ // no overlap
			for(int i=0;i<=insert;i++){
				if (del[i]==0) ret[p++] = ind1.genes[i];
			}
			for(int i=point1;i<point1+len;i++){
				ret[p++] = ind2.genes[i];
			}
			for(int i=insert+1;i<ind1.genes.length;i++){
				if (del[i]==0) ret[p++] = ind1.genes[i];
			}
		}

		return new Integer1D(ret);
	}
	
	// generates a random it between a and b
	public int nextInt(int a, int b)
	{
		return Main.random.nextInt(b-a)+a;
	}
	
	// generates a random crossover point and length, and calls the main crossover 
	// function twice (with the parents swapped) to generate the two children
	public Integer1D[] crossover(Integer1D ind1, Integer1D ind2) {
		int point1 = Main.random.nextInt(ind1.genes.length+1);
		int len = nextInt(ind1.genes.length/3, ind1.genes.length/2);
		Integer1D child[] = new Integer1D[2];
		child[0] = crossover1(ind1, ind2, point1, len);
		child[1] = crossover1(ind2, ind1, point1, len);
		return child;
	}

	
	// for testing purposes
	public static void main(String[] args) {
		CrossoverGOX c = new CrossoverGOX();
		{
			Integer1D ret1 = c.crossover1(new Integer1D(new int[]{2,1,2,2,3,1,3,3,2,1}), new Integer1D(new int[]{1,2,2,1,3,1,2,3,2,3}), 3, 4);
			System.out.println(Arrays.toString(ret1.genes));
			System.out.println(Arrays.toString(new int[]{2,1,2,1,3,1,2,3,3,2}));
		}
		{
			Integer1D ret1 = c.crossover1(new Integer1D(new int[]{2,1,2,2,3,1,3,3,2,1}), new Integer1D(new int[]{1,2,2,1,3,1,2,3,2,3}), 8, 4);
			System.out.println(Arrays.toString(ret1.genes));
			System.out.println(Arrays.toString(new int[]{1,2,2,2,3,1,3,1,2,3}));
		}
	}

}
