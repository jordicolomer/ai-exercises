package problem;
import genome.Integer1D;


public abstract class Problem {

	double range1, range2;
	double maxfitness = 0;
	double minfitness = 0;

	public void setMaxFitness(double maxfitness)
	{
		this.maxfitness = maxfitness;
	}
	public void setMinFitness(double minfitness)
	{
		this.minfitness = minfitness;
	}
	public double normalizedFitness(Integer1D ind)
	{
		//System.out.println("normalizedFitness "+minfitness+" "+maxfitness);
		return (fitness(ind)-minfitness)/(maxfitness-minfitness);
	}
	public double fitness(Integer1D ind)
	{
		return -distance(ind);
	}
	public double distance(Integer1D ind)
	{
		return -fitness(ind.genes,ind.genes.length);
		/*if (ind.fitness==-1) 
			ind.fitness = -fitness(ind.genes,ind.genes.length);
		return ind.fitness;*/
	}
	public abstract double fitness(int X[], int n);
	
    public int[] initial(int dim)
    {
        int genes[] = new int[dim];
        for(int i=0;i<dim;i++) genes[i] = i;
        return genes;
    }

}
