package fitness;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


class Coordinate{
	double x,y;

	public Coordinate(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	public double distance(Coordinate c)
	{
		return Math.sqrt(Math.pow(this.x-c.x,2)+Math.pow(this.y-c.y,2));
	}

}

public class FitnessTSP extends Fitness  {

	ArrayList<Coordinate> nodes = new ArrayList<Coordinate>();
	
	public int getDimensions()
	{
		return nodes.size();
	}

	public FitnessTSP(String filename) {
		super();
		try{
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			boolean startparsing = false;
			String l;
			while ((l = br.readLine()) != null)   {
				if (startparsing == false)
				{
					if (l.equals("NODE_COORD_SECTION")) startparsing = true;
				}
				else
				{
					if (l.equals("EOF")) continue;
					String f[] = l.split(" ");
					nodes.add(new Coordinate(Float.parseFloat(f[1]),Float.parseFloat(f[2])));
				}
				//	System.out.println(l);
			}
			in.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		/*nodes.add(new Coordinate(-1,-1));
		nodes.add(new Coordinate(-1,0));
		nodes.add(new Coordinate(-1,1));

		nodes.add(new Coordinate(0,-1));
		nodes.add(new Coordinate(0,0));
		nodes.add(new Coordinate(0,1));

		nodes.add(new Coordinate(1,-1));
		nodes.add(new Coordinate(1,0));
		nodes.add(new Coordinate(1,1));*/
	}
	
	public double fitnessdouble(int[] X, int n) {
		double total = 0;
		for(int x=0;x<n;x++)
		{
			total += nodes.get((int)X[x]).distance(nodes.get((int)X[(x+1)%n]));
		}
		//return 1/total;
		return -total;
	}

	public double fitness(int[] X, int n) {
		double total = 0;
		for(int x=0;x<n;x++)
		{
			double d = nodes.get((int)X[x]).distance(nodes.get((int)X[(x+1)%n]));
			total += Math.round(d);
		}
		//return 1/total;
		return -total;
	}

	public static void main(String[] args) {
		FitnessTSP problem = new FitnessTSP("wi29.tsp");
		System.out.println(problem.fitness(new int[]{0,1,2,3},4));
	}


}
