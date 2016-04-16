package crossover;

import main.Main;
import genome.Integer1D;

public class Crossover9X extends Crossover{

	public static void main(String[] args) {
		Crossover9X c = new Crossover9X();
		int q[] = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81};
		int qq[] = new int[]{101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181};
		Integer1D[] r = c.crossover(new Integer1D(q), new Integer1D(qq));
		System.out.println((""+r[0]));
		System.out.println((""+r[1]));
	}

	public Integer1D[] crossover(Integer1D ind1, Integer1D ind2) {
		Integer1D ret[] = new Integer1D[2];
		ret[0] = ind1.copy();
		ret[1] = ind2.copy();
		int j = Main.random.nextInt(ind1.genes.length/9+1);
		for(int i=j*9;i<ind2.genes.length;i++){
			ret[0].genes[i] = ind2.genes[i];
			ret[1].genes[i] = ind1.genes[i];
		}
		return ret;
	}

}
