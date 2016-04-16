package selection;

import genome.Integer1D;

import java.util.ArrayList;

import problem.Problem;

public class SelectWorst extends Select{

	public int selecti(ArrayList<Integer1D> population, Problem fitness) {
    	double min = Double.MAX_VALUE;
    	int mini=0;
    	for(int i=0;i<population.size();i++)
    	{
    		double f = fitness.fitness(population.get(i));
    		if (f<min)
    		{
    			min=f;
    			mini=i;
    		}
    	}
    	return mini;
	}

}
