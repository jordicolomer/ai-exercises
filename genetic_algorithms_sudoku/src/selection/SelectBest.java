package selection;

import genome.Integer1D;

import java.util.ArrayList;

import problem.Problem;

public class SelectBest extends Select{

	public int selecti(ArrayList<Integer1D> population, Problem fitness) {
    	double max = -Double.MAX_VALUE;
    	int maxi=0;
    	for(int i=0;i<population.size();i++)
    	{
    		double f = fitness.fitness(population.get(i));
    		if (f>max)
    		{
    			max=f;
    			maxi=i;
    		}
    	}
    	return maxi;
	}

}
