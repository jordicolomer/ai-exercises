package selection;

import fitness.Fitness;
import genome.Integer1D;

import java.util.ArrayList;

public class SelectWorst extends Select{

	public int selecti(ArrayList<Integer1D> population, Fitness fitness) {
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
