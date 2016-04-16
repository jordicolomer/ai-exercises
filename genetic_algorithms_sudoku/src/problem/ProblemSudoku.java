package problem;

import java.util.Arrays;
import java.util.HashSet;


public class ProblemSudoku extends Problem  {

	String config; // string of the sudoku problem
	int idx[]; // this vector maps each element of a solution vector to a position of the grid
	int dimensions = 0; // number of unknowns
	
	// initializes the varialbes config, idx and dimensions
	public ProblemSudoku(String config) {
		super();
		this.config = config;
		idx = new int[config.length()];
		int i = 0;
		for(int j=0;j<config.length();j++)
		{
			if (config.charAt(j) == '.')
			{
				idx[j] = i++;
				dimensions++;
			}
			else idx[j] = -1;
		}
	}

	// encapsulates the access to the elements of a proposed solution X
	public int get(int[] X, int x, int y)
	{
		int p = x+y*9;
		if (idx[p] == -1) return config.charAt(p)-'0';
		return X[idx[p]];
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
		double total = 0;
		
		// horizontal lines
		for(int y=0;y<9;y++){
			HashSet set = new HashSet();
			for(int x=0;x<9;x++){
				set.add(this.get(X, x, y));
			}
			//System.out.println(9-set.size());
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
		return dimensions;
	}
	
	// The initial state is a random sequence of the missing numbers in the sudoku.
	// for each number we count the repetitions and we generate 9-count new instances of it
	public int[] initial(int dim)
	{
		int ret[] = new int[dim];
		int j=0;
		for (int i=1;i<=9;i++){
			int count = 0;
			for(char c: config.toCharArray()){
				if (c-'0' == i) count++;
			}
			while(count<9){
				ret[j] = i;
				j++;
				count++;
			}
		}
		return ret;
	}

	// for testing purposes
	public static void main(String[] args) {
		ProblemSudoku problem = new ProblemSudoku("817..2..3.954..67.3...8.1......7195....3.4....4962......1.3...7.26..783.4..9..216");
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
		problem.fitness(X,X.length);
		System.out.println(Arrays.toString(problem.initial(problem.getDimensions())));
	}


}
