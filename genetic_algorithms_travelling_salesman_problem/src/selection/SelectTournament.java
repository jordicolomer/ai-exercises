package selection;

import fitness.Fitness;
import genome.Integer1D;

import java.util.ArrayList;

import main.Main;

public class SelectTournament  extends Select{
	
	int k;
	
	public SelectTournament(int k) {
		super();
		this.k = k;
	}

	public int selecti(ArrayList<Integer1D> population, Fitness fitness) {
    	double max = -Double.MAX_VALUE;
    	//Double1D maxind = null;
    	int maxi=0;
    	for(int i=0;i<k;i++)
    	{
    		int j = Main.random.nextInt(population.size());
    		double f = fitness.fitness(population.get(j));
    		if (f>max)
    		{
    			max=f;
    			//maxind=population.get(i);
    			maxi=j;
    		}
    	}
    	return maxi;
	}

}
