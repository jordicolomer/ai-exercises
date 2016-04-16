package main;

public class Benchmark {

	int count = 0;
	public int evaluations = -1; //evaluations until successs
	
	public boolean isSuccess(double d)
	{
		return d==0;
	}
	
	public void update(double d)
	{
		count++;
		if (evaluations == -1 && isSuccess(d))
		{
			evaluations = count;
		}
	}

}
