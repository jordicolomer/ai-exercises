package selection;

import genome.Integer1D;

import java.util.ArrayList;
import java.util.Collections;

import problem.Problem;

public class SelectRanking extends Select{

	public int selecti(ArrayList<Integer1D> population, Problem fitness) {
        for(Integer1D ind: population) ind.probability = fitness.fitness(ind);
		Collections.sort(population);
		Collections.reverse(population);
		double s = 1.5;
		int mu = population.size();
		for(int i=0;i<population.size();i++)
		{
			double p = 1.*(2-s)/mu+1.*2*i*(s-1)/(mu*(mu-1));
			population.get(i).probability = p;
			//System.out.println(""+i+" "+p+" "+population.get(i).probability);
		}
		return SelectFitnessProportional.rouletteWheel(population);
	}

}
