package selection;

import genome.Integer1D;

import java.util.ArrayList;

import problem.Problem;

public abstract class Select {
	public abstract int selecti(ArrayList<Integer1D> population, Problem fitness);
	
	public Integer1D select(ArrayList<Integer1D> population, Problem fitness)
	{
		int i = selecti(population, fitness);
		return population.get(i);
	}
	
	public ArrayList<Integer1D> selectN(ArrayList<Integer1D> population, Problem fitness, int n)
	{
		ArrayList<Integer1D> newpopulation = new ArrayList<Integer1D>();
		for(int i=0;i<n;i++) newpopulation.add(select(population, fitness).copy());
		return newpopulation;
	}

	public void removeN(ArrayList<Integer1D> population, Problem fitness, int n)
	{
		for(int i=0;i<n;i++)
		{
			population.remove(selecti(population,fitness));
		}
	}
}
