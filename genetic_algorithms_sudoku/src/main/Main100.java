package main;


public class Main100 {

	public static void main(String[] args) throws Exception
	{
		Main.loglevel = 0;
		String params = "default.properties";
		if (args.length>0) params = args[0];
		for(int i=0;i<100;i++)
		{
			Benchmark bm = new Benchmark();
			double best = Main.main(params, i, bm);
		}
	}

}
