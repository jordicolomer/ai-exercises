package main;

public class Main100 {

	public static void main(String[] args) throws Exception
	{
		Main.loglevel = 0;
		double total = 0;
		int successrate = 0;
		double evaluations = 0;
		int optimalfound = 0;
		for(int i=0;i<100;i++)
		{
			Benchmark bm = new Benchmark();
			double best = Main.main(args[0], i, bm);
			total += best;
			System.out.println(bm.evaluations+" "+bm.optimalfound+" "+best);
			if (bm.evaluations!=-1)
			{
				successrate++;
				evaluations += bm.evaluations;
			}
			if (bm.optimalfound) optimalfound++;
		}
		System.out.println(""+total/100+" "+evaluations/100+" "+successrate+" "+optimalfound);
	}

}
