package selection;

import fitness.Fitness;
import genome.Integer1D;

import java.util.ArrayList;

public class SelectBest extends Select{

	public int selecti(ArrayList<Integer1D> population, Fitness fitness) {
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
