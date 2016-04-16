package selection;

import genome.Integer1D;

import java.util.ArrayList;

import problem.Problem;

import main.Main;

public class SelectRandom extends Select {

	public int selecti(ArrayList<Integer1D> population, Problem fitness) {
		return Main.random.nextInt(population.size());
	}

}
