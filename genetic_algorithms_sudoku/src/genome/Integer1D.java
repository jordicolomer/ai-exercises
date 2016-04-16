package genome;
import java.util.HashSet;

import main.Main;

public class Integer1D implements Comparable
{
    public int genes[] = null;
    public double probability = 0;
    public double fitness = -1;
    

	public int compareTo(Object o) {
		Integer1D i = (Integer1D) o;
		if (i.probability<this.probability) return -1;
		if (i.probability>this.probability) return +1;
		return 0;
	}
    
    
    public Integer1D() {
		super();
	}


	public Integer1D(int[] genes) {
		super();
		this.genes = genes;
	}


	public Integer1D copy()
    {
    	Integer1D c = new Integer1D();
    	c.genes = genes.clone();
    	c.probability = probability;
    	return c;
    }


    public void init(int dim, int max)
    {
        //Random random = new Random();
        genes = new int[dim];
        for(int i=0;i<dim;i++) genes[i] = (int)Main.random.nextDouble()*max;
    }

    public void initseq(int dim)
    {
        genes = new int[dim];
        for(int i=0;i<dim;i++) genes[i] = i;
    }

    public int[] getGenotype()
    {
        return genes;
    }

    
    public void print()
    {
    	for(int d: genes) System.out.print(d+", ");
    	System.out.println();
    }
    
    public String toString()
    {
    	StringBuffer sb = new StringBuffer();
    	for(int d: genes) sb.append(d+",");
    	String s = sb.toString();
    	return s.substring(0, s.length()-1);
    }
    
    public void check()
    {
    	HashSet set = new HashSet();
    	for (int i: genes) set.add(i);
    	if (set.size()!=genes.length) System.out.println("error");
    }


}
