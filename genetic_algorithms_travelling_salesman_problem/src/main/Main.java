package main;
import fitness.Fitness;
import fitness.FitnessTSP;
import genome.Integer1D;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import mutate.Mutate;
import mutate.MutateInsert;
import mutate.MutateInvert;
import mutate.MutateShuffle;
import mutate.MutateSwap;
import selection.Select;
import selection.SelectBest;
import selection.SelectFitnessProportional;
import selection.SelectRandom;
import selection.SelectRanking;
import selection.SelectTournament;
import selection.SelectWorst;
import crossover.Crossover;
import crossover.CrossoverCX;
import crossover.CrossoverMOX;
import crossover.CrossoverOX;
import crossover.CrossoverPMX;

public class Main
{
    public static Random random = new Random();
    
    public static ArrayList<Integer1D> getInitialPopulation(int mu, int dim)
    {
    	MutateShuffle m = new MutateShuffle();
        ArrayList<Integer1D> population = new ArrayList();
        for (int i = 0; i < mu; i++) {
            Integer1D ind = new Integer1D();
            ind.initseq(dim);
            m.mutate(ind, 0, dim-1);
            population.add(ind);
        }
        return population;
    }
    
    static int loglevel = 2;

    public static double main(String file, int seed, Benchmark bm) throws Exception
    {
        random.setSeed(seed);
        
        // read the configuration file and initialize all variables
        Properties prop = new Properties();
    	prop.load(new FileInputStream(file));
        int mu = Integer.parseInt(prop.getProperty("mu"));
        int lambda = Integer.parseInt(prop.getProperty("lambda"));
        int maxGenerations = Integer.parseInt(prop.getProperty("maxGenerations"));
        
        int dim = 0;
        String fitnessFunction = prop.getProperty("fitnessFunction");
        Fitness fitness = null;
        if (fitnessFunction.equals("tsp"))
        {
        	fitness = new FitnessTSP(prop.getProperty("filename"));
        	dim = ((FitnessTSP)fitness).getDimensions();
        }
        
        String parentSelectionMethod = prop.getProperty("parentSelectionMethod");
        Select parentSelect = null;
    	if (parentSelectionMethod.equals("fitnessProportional")) parentSelect = new SelectFitnessProportional();
    	if (parentSelectionMethod.equals("ranking")) parentSelect = new SelectRanking();
    	if (parentSelectionMethod.equals("random")) parentSelect = new SelectRandom();
    	if (parentSelectionMethod.equals("tournament")) 
    		parentSelect = new SelectTournament(
    				Integer.parseInt(prop.getProperty("parentSelectionMethodTournamentk", "5")));
        
        String recombinationMethod = prop.getProperty("recombinationMethod");
        Crossover crossover = null;
        if (recombinationMethod.equals("CX")) crossover = new CrossoverCX();
        if (recombinationMethod.equals("MOX")) crossover = new CrossoverMOX();
        if (recombinationMethod.equals("OX")) crossover = new CrossoverOX();
        if (recombinationMethod.equals("PMX")) crossover = new CrossoverPMX();
        
        double mutationRate = Double.parseDouble(prop.getProperty("mutationRate"));
        String mutationMethod = prop.getProperty("mutationMethod");
        Mutate mutate = null;
        if (mutationMethod.equals("insert")) mutate = new MutateInsert();
        if (mutationMethod.equals("invert")) mutate = new MutateInvert();
        if (mutationMethod.equals("shuffle")) mutate = new MutateShuffle();
        if (mutationMethod.equals("swap")) mutate = new MutateSwap();

        String survivorSelectionMethod = prop.getProperty("survivorSelectionMethod");
        Select survivorSelect = null;
    	if (survivorSelectionMethod.equals("steadyStateRandom")) survivorSelect = new SelectRandom();
    	if (survivorSelectionMethod.equals("steadyStateWorst")) survivorSelect = new SelectWorst();
    	if (survivorSelectionMethod.equals("fitnessProportional")) survivorSelect = new SelectFitnessProportional();
    	if (survivorSelectionMethod.equals("ranking")) survivorSelect = new SelectRanking();
    	if (survivorSelectionMethod.equals("tournament")) 
    		survivorSelect = new SelectTournament(
            		Integer.parseInt(prop.getProperty("survivorSelectionMethodTournamentk", "5")));
    	boolean elitism = Boolean.parseBoolean(prop.getProperty("elitism", "false"));
        
    	// we'll keep track of the min and max individuals
        double maxFitness = -Double.MAX_VALUE;
        Integer1D maxInd = null;
        double minFitness = Double.MAX_VALUE;
        Integer1D minInd = null;

        // generate the initial population
        if (loglevel > 1) System.out.println("generating initial population");
        ArrayList<Integer1D> population = getInitialPopulation(mu, dim);
        // update min and max individuals
        for(Integer1D i1: population)
        {
            if (fitness.fitness(i1) > maxFitness){
            	maxFitness = fitness.fitness(i1);
            	fitness.setMaxFitness(fitness.fitness(i1));
                maxInd = i1;
            }
            if (fitness.fitness(i1) < minFitness){
            	minFitness = fitness.fitness(i1);
            	fitness.setMinFitness(fitness.fitness(i1));
                minInd = i1;
            }
            bm.update(fitness.distance(i1));
        }
        for(Integer1D i1: population)
        	if (loglevel > 1) System.out.println("init: "+i1+" "+fitness.normalizedFitness(i1)+" "+fitness.fitness(i1)+" "+fitness.distance(i1));

        // generations loop
        for(int ite = 0; ite < maxGenerations; ite++)
        {
            if (loglevel > 1) System.out.println("generation "+ite);
            if (loglevel > 1) System.out.println("generate new offspring");
            ArrayList<Integer1D> children = new ArrayList<Integer1D>();
            // generate lambda children
            for(int i = 0; i < lambda/2; i++)
            {
            	// select parents with corresponding select method
            	Integer1D i1 = parentSelect.select(population, fitness);
            	Integer1D i2 = parentSelect.select(population, fitness);
            	
                // Recombination
                Integer1D child[] = crossover.crossover(i1, i2); 
                if (loglevel > 1) System.out.println("parent1: "+i1+" "+fitness.normalizedFitness(i1)+" "+fitness.fitness(i1)+" "+fitness.distance(i1));
                if (loglevel > 1) System.out.println("parent2: "+i2+" "+fitness.normalizedFitness(i2)+" "+fitness.fitness(i2)+" "+fitness.distance(i2));
                if (loglevel > 1) System.out.println(" child1: "+child[0]+" "+fitness.normalizedFitness(child[0])+" "+fitness.fitness(child[0])+" "+fitness.distance(child[0]));
                if (loglevel > 1) System.out.println(" child2: "+child[1]+" "+fitness.normalizedFitness(child[1])+" "+fitness.fitness(child[1])+" "+fitness.distance(child[1]));
                child[0].check();
                child[1].check();
                // For each child
                for(int ch=0;ch<2;ch++)
                {
                	// mutation
                	if (mutationRate > random.nextDouble()) mutate.mutate(child[ch]);
                    
                    children.add(child[ch]);
                    //child[ch].check();
                    // update min and max individuals
                    if (fitness.fitness(child[ch]) > maxFitness){
                    	maxFitness = fitness.fitness(child[ch]);
                    	fitness.setMaxFitness(fitness.fitness(child[ch]));
                        maxInd = child[ch];
                    }
                    if (fitness.fitness(child[ch]) < minFitness){
                    	minFitness = fitness.fitness(child[ch]);
                    	fitness.setMinFitness(fitness.fitness(child[ch]));
                        minInd = child[ch];
                    }
                    // for benchmarks
                    bm.update(fitness.distance(child[ch]));
                }
                if (loglevel > 1) System.out.println("mchild1: "+child[0]+" "+fitness.normalizedFitness(child[0])+" "+fitness.fitness(child[0])+" "+fitness.distance(child[0]));
                if (loglevel > 1) System.out.println("mchild2: "+child[1]+" "+fitness.normalizedFitness(child[1])+" "+fitness.fitness(child[1])+" "+fitness.distance(child[1]));
            }
            // select survivors
            Integer1D elite = null;
            // if elitism is set, select the best individual
            if (elitism)
            {
                SelectBest best = new SelectBest();
                int b = best.selecti(population, fitness);
                elite = population.get(b);
                population.remove(b);
            }
            if (loglevel > 1) System.out.println("Survivor selection");
            // Steady State
            if (survivorSelectionMethod.equals("steadyStateRandom") || survivorSelectionMethod.equals("steadyStateWorst"))
            {
            	survivorSelect.removeN(population, fitness, children.size());
                for(Integer1D ind: population) if (loglevel > 1) System.out.println("select: "+ind+" "+fitness.normalizedFitness(ind)+" "+fitness.fitness(ind)+" "+fitness.distance(ind));
                population.addAll(children);
            }
            else
            {
                population.addAll(children);
                if (elitism) population = survivorSelect.selectN(population, fitness, mu-1);
                else population = survivorSelect.selectN(population, fitness, mu);
                for(Integer1D ind: population) if (loglevel > 1) System.out.println("select: "+ind+" "+fitness.normalizedFitness(ind)+" "+fitness.fitness(ind)+" "+fitness.distance(ind));
            }
            if (elitism) population.add(elite);
        }
        if (loglevel > 1) System.out.println("best: "+maxInd+" "+fitness.fitness(maxInd)+" "+fitness.distance(maxInd));
        return fitness.distance(maxInd);
    }

    public static void main(String args[]) throws Exception
    {
    	main(args[0], Integer.parseInt(args[1]), new Benchmark());
    }
}
