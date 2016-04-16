package selection;

import genome.Integer1D;

import java.util.ArrayList;

import problem.Problem;

import main.Main;

public class SelectFitnessProportional extends Select{

	public static int rouletteWheel(ArrayList<Integer1D> population)
	{
		double r = Main.random.nextDouble();
		//System.out.println("average "+r);
		double count = 0;
		int i = 0;
		for(Integer1D ind: population)
		{
			count += ind.probability;
			//System.out.println("average count "+count);
			if (r < count) return i;
			i++;
		}
		return -1;
	}

	public int selecti(ArrayList<Integer1D> population, Problem fitness) {
		double totalFitness = 0;
		for(Integer1D ind: population)
		{
			ind.probability = fitness.normalizedFitness(ind);
			//System.out.println("average fitness "+ind.probability);
			totalFitness += ind.probability;
		}
		//System.out.println("average totalFitness "+totalFitness);
		double total = 0;
		for(Integer1D ind: population)
		{
			//System.out.println("average fitness2 "+ind.probability+"/"+totalFitness+" = "+(ind.probability/totalFitness));
			ind.probability = ind.probability/totalFitness;
			total += ind.probability;
		}
		//System.out.println("average total "+total);
		return rouletteWheel(population);
	}

}
