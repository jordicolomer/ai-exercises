package selection;

import fitness.Fitness;
import genome.Integer1D;

import java.util.ArrayList;

import main.Main;

public class SelectRandom extends Select {

	public int selecti(ArrayList<Integer1D> population, Fitness fitness) {
		return Main.random.nextInt(population.size());
	}

}
