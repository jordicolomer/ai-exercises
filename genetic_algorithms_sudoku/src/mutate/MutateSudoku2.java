package mutate;

import genome.Integer1D;
import main.Main;
import problem.ProblemSudoku2;

public class MutateSudoku2 extends Mutate{

	ProblemSudoku2 problem;

	public MutateSudoku2(ProblemSudoku2 problem) {
		super();
		this.problem = problem;
	}

	// select a square and flip two valid (not known)
	// elements in the square
	public void mutate2(Integer1D ind) {
		int square = Main.random.nextInt(9);
		int point1 = Main.random.nextInt(9);
		int point2 = Main.random.nextInt(9);
		
		while (point1 == point2
				|| 
				problem.config.charAt(square*9+point1) != '.'
				|| 
				problem.config.charAt(square*9+point2) != '.')
		{
			square = Main.random.nextInt(9);
			point1 = Main.random.nextInt(9);
			point2 = Main.random.nextInt(9);
		}
		
		int aux = ind.genes[square*9+point1];
		ind.genes[square*9+point1] = ind.genes[square*9+point2];
		ind.genes[square*9+point2] = aux;
	}

	// select a square and rotate three valid (not known)
	// elements in the square
	public void mutate3(Integer1D ind) {
		int square = Main.random.nextInt(9);
		int point1 = Main.random.nextInt(9);
		int point2 = Main.random.nextInt(9);
		int point3 = Main.random.nextInt(9);
		
		while (
				point1 == point2
				|| 
				point1 == point3
				|| 
				point2 == point3
				|| 
				problem.config.charAt(square*9+point1) != '.'
				|| 
				problem.config.charAt(square*9+point2) != '.'
				|| 
				problem.config.charAt(square*9+point3) != '.'
				)
		{
			square = Main.random.nextInt(9);
			point1 = Main.random.nextInt(9);
			point2 = Main.random.nextInt(9);
			point3 = Main.random.nextInt(9);
		}
		
		int aux = ind.genes[square*9+point1];
		ind.genes[square*9+point1] = ind.genes[square*9+point2];
		ind.genes[square*9+point2] = ind.genes[square*9+point3];
		ind.genes[square*9+point3] = aux;
	}
	
	// toss a coin and forward to mutate2 or mutate3
	public void mutate(Integer1D ind) {
		if (Main.random.nextInt(2)==0) mutate2(ind);
		else mutate3(ind);
	}


	// for testing
	public static void main(String[] args) {
		ProblemSudoku2 problem = new ProblemSudoku2("817..2..3.954..67.3...8.1......7195....3.4....4962......1.3...7.26..783.4..9..216");
		MutateSudoku2 m = new MutateSudoku2(problem);
		Integer1D i = new Integer1D(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81});
		m.mutate(i);
		System.out.println((""+i));
	}
}
