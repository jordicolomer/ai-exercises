package selection;

import fitness.Fitness;
import genome.Integer1D;

import java.util.ArrayList;

public abstract class Select {
	public abstract int selecti(ArrayList<Integer1D> population, Fitness fitness);
	
	public Integer1D select(ArrayList<Integer1D> population, Fitness fitness)
	{
		int i = selecti(population, fitness);
		return population.get(i);
	}
	
	public ArrayList<Integer1D> selectN(ArrayList<Integer1D> population, Fitness fitness, int n)
	{
		ArrayList<Integer1D> newpopulation = new ArrayList<Integer1D>();
		for(int i=0;i<n;i++) newpopulation.add(select(population, fitness).copy());
		return newpopulation;
	}

	public void removeN(ArrayList<Integer1D> population, Fitness fitness, int n)
	{
		for(int i=0;i<n;i++)
		{
			population.remove(selecti(population,fitness));
		}
	}
}
