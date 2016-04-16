package main;

public class Benchmark {

	int count = 0;
	double optimal = 27603;
	public int evaluations = -1;
	boolean optimalfound = false;
	
	public boolean isSuccess(double d)
	{
		return d/optimal*100<200;
	}
	
	public void update(double d)
	{
		count++;
		if (evaluations == -1 && isSuccess(d))
		{
			evaluations = count;
		}
		if (d==optimal) optimalfound = true;
	}

}
