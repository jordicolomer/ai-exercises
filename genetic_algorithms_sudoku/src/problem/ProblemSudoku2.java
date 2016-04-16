package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import main.Main;


public class ProblemSudoku2 extends Problem  {

	public String config;
	
	// initializes the problem class
	public ProblemSudoku2(String config) {
		super();
		this.config = config;
	}

	// encapsulates the access to the elements of a proposed solution X
	public int get(int[] X, int x, int y)
	{
		int p = x+y*9;
		return X[p];
	}

	// implementation of the fitness equation with 3 components:
	// The first component counts the number of repetitions of each column. 
	// Another way to express it is to take the size of the set composed by the elements
	// of each column and subtract that value from 9. In the ideal case where there are no 
	// repetitions in any of the columns this value will be 0.
	// The second component is the same value but this time for the rows,
	// and the third and last component would be the 
	// same value for the 3x3 squares.
	public double fitness(int[] X, int n) {
		//if (cache != -1) return cache;
		double total = 0;
		
		// horizontal lines
		for(int y=0;y<9;y++){
			HashSet set = new HashSet();
			for(int x=0;x<9;x++){
				set.add(this.get(X, x, y));
			}
			total += 9-set.size();
		}
		// vertical lines
		for(int x=0;x<9;x++){
			HashSet set = new HashSet();
			for(int y=0;y<9;y++){
				set.add(this.get(X, x, y));
			}
			//System.out.println(9-set.size());
			total += 9-set.size();
		}
		// squares
		for(int y=0;y<3;y++){
			for(int x=0;x<3;x++){
				HashSet set = new HashSet();
				//System.out.println("new hashset");
				for(int j=0;j<3;j++){
					for(int i=0;i<3;i++){
						set.add(this.get(X, x*3+i, y*3+j));
						//System.out.println(" "+(x*3+i)+" "+(y*3+j));
					}
				}
				//System.out.println(9-set.size());
				total += 9-set.size();
			}
		}
		
		return -total;
	}
	
	public int getDimensions()
	{
		return 81;
	}
	
	// We initialize the population randomly with two constraints:
	// the known values of the problem must appear in the solution,
	// and each 3x3 square must contain no repetitions
	// We can easily construct such individuals by filling in the
	// blanks with values randomly drawn from the set of numbers that
	// don't appear in the square.
	public int[] initial(int dim)
	{
		int ret[] = new int[dim];
		int n19[]=new int[]{0,1,2,3,4,5,6,7,8};
		ArrayList<Integer> ls = new ArrayList();
		for(int i:n19) ls.add(i);
		//ArrayList<Integer> ls = new ArrayList();
		int p=0;
		for (int i=0;i<9;i++){
			HashSet set = new HashSet();
			for (int j=0;j<9;j++){
				int k = config.charAt(i*9+j)-'0';
				if (k>0 && k<10){
					set.add(k);
					ret[i*9+j] = k;
				}
			}
			Collections.shuffle(ls, Main.random);
			//for (int j=0;j<9;j++){
			for(int j: ls){
				if (!set.contains(j+1))
				{
					//System.out.println("initial: "+i+" "+j+" "+Arrays.toString(ret));
					while(ret[p]!=0) p++;
					ret[p++] = j+1;
				}
			}
		}
		//System.out.println("initial2: "+Arrays.toString(ret));
		return ret;
	}

	// used for testing
	public static void main(String[] args) {
		ProblemSudoku2 problem = new ProblemSudoku2("817..2..3.954..67.3...8.1......7195....3.4....4962......1.3...7.26..783.4..9..216");
		int[] X = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		
		/*for(int y=0;y<9;y++)
		{
			String row = "";
			for(int x=0;x<9;x++)
			{
				row = row+" "+problem.get(X, x, y);
			}
			System.out.println(row);
		}*/
		//problem.fitness(X,X.length);
		System.out.println(Arrays.toString(problem.initial(problem.getDimensions())));
	}


}
