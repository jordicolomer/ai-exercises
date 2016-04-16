package fitness;
import genome.Integer1D;


public abstract class Fitness {

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
	}
	public abstract double fitness(int X[], int n);
}
