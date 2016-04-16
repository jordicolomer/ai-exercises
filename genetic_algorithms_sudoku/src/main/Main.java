package main;
import genome.Integer1D;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import mutate.Mutate;
import mutate.MutateInsert;
import mutate.MutateInvert;
import mutate.MutateShuffle;
import mutate.MutateSudoku2;
import mutate.MutateSwap;
import problem.Problem;
import problem.ProblemSudoku;
import problem.ProblemSudoku2;
import problem.ProblemTSP;
import selection.Select;
import selection.SelectBest;
import selection.SelectFitnessProportional;
import selection.SelectRandom;
import selection.SelectRanking;
import selection.SelectTournament;
import selection.SelectWorst;
import crossover.Crossover;
import crossover.Crossover9X;
import crossover.CrossoverCX;
import crossover.CrossoverGOX;
import crossover.CrossoverMOX;
import crossover.CrossoverOX;
import crossover.CrossoverPMX;

public class Main
{
    public static Random random = new Random();
    
    public static ArrayList<Integer1D> getInitialPopulation(int mu, int dim, Problem problem)
    {
    	MutateShuffle m = new MutateShuffle();
        ArrayList<Integer1D> population = new ArrayList();
        for (int i = 0; i < mu; i++) {
            Integer1D ind = new Integer1D();
            //ind.initseq(dim);
            ind.genes = problem.initial(dim);
            //m.mutate(ind, 0, dim-1);
            population.add(ind);
        }
        return population;
    }
    
    static int loglevel = 1;

    public static double main(String file, int seed, Benchmark bm) throws Exception
    {
    	Date date1 = new Date();
        random.setSeed(seed);
        
        // read the configuration file and initialize all variables
        Properties prop = new Properties();
    	prop.load(new FileInputStream(file));
        int mu = Integer.parseInt(prop.getProperty("mu"));
        int lambda = Integer.parseInt(prop.getProperty("lambda"));
        int maxGenerations = Integer.parseInt(prop.getProperty("maxGenerations"));
        int maxGenerationsWithoutImprovement = Integer.parseInt(prop.getProperty("maxGenerationsWithoutImprovement"));
        
        int dim = 0;
        String fitnessFunction = prop.getProperty("fitnessFunction");
        Problem problem = null;
        if (fitnessFunction.equals("tsp"))
        {
        	problem = new ProblemTSP(prop.getProperty("filename"));
        	dim = ((ProblemTSP)problem).getDimensions();
        }
        if (fitnessFunction.equals("sudoku"))
        {
        	if (prop.getProperty("insudokufile")!=null && prop.getProperty("insudokufile").length()>0)
        	{
        		String rep = "";
        		BufferedReader in = new BufferedReader(new FileReader(prop.getProperty("insudokufile")));
                String l = in.readLine();
                while(l != null)
                {
                    //System.out.println(l);
                	rep = rep+l.replaceAll(" ", "").replace('0', '.');
                    l = in.readLine();
                }
                in.close();

            	problem = new ProblemSudoku(rep);
        	}
        	else
        	{
        		problem = new ProblemSudoku(prop.getProperty("rep"));
        	}
           	//problem = new ProblemSudoku(prop.getProperty("rep"));
        	dim = ((ProblemSudoku)problem).getDimensions();
        }
        if (fitnessFunction.equals("sudoku2"))
        {
        	if (prop.getProperty("insudokufile")!=null && prop.getProperty("insudokufile").length()>0)
        	{
        		String rep = "";
        		BufferedReader in = new BufferedReader(new FileReader(prop.getProperty("insudokufile")));
                String l = in.readLine();
                while(l != null)
                {
                    //System.out.println(l);
                	rep = rep+l.replaceAll(" ", "").replace('0', '.');
                    l = in.readLine();
                }
                in.close();

            	problem = new ProblemSudoku2(rep);
        	}
        	else
        	{
        		problem = new ProblemSudoku2(prop.getProperty("rep"));
        	}
        	dim = ((ProblemSudoku2)problem).getDimensions();
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
        if (recombinationMethod.equals("GOX")) crossover = new CrossoverGOX();
        if (recombinationMethod.equals("9X")) crossover = new Crossover9X();
        
        double mutationRate = Double.parseDouble(prop.getProperty("mutationRate"));
        String mutationMethod = prop.getProperty("mutationMethod");
        Mutate mutate = null;
        if (mutationMethod.equals("insert")) mutate = new MutateInsert();
        if (mutationMethod.equals("invert")) mutate = new MutateInvert();
        if (mutationMethod.equals("shuffle")) mutate = new MutateShuffle();
        if (mutationMethod.equals("swap")) mutate = new MutateSwap();
        if (mutationMethod.equals("sudoku2")) mutate = new MutateSudoku2((ProblemSudoku2)problem);

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
        ArrayList<Integer1D> population = getInitialPopulation(mu, dim, problem);
        // update min and max individuals
        for(Integer1D i1: population)
        {
            if (problem.fitness(i1) > maxFitness){
            	maxFitness = problem.fitness(i1);
            	problem.setMaxFitness(problem.fitness(i1));
                maxInd = i1;
            }
            if (problem.fitness(i1) < minFitness){
            	minFitness = problem.fitness(i1);
            	problem.setMinFitness(problem.fitness(i1));
                minInd = i1;
            }
            bm.update(problem.distance(i1));
        }
        for(Integer1D i1: population)
        	if (loglevel > 1) System.out.println("init: "+i1+" "+problem.normalizedFitness(i1)+" "+problem.fitness(i1)+" "+problem.distance(i1));

        // generations loop
        int ite=0;
        double antd=0;
        int lastimprovement=0;
        for(ite = 0; ite < maxGenerations; ite++)
        {
            if (loglevel > 0) System.out.println("generation "+ite);
            if (loglevel > 1) System.out.println("generate new offspring");
            ArrayList<Integer1D> children = new ArrayList<Integer1D>();
            // generate lambda children
            for(int i = 0; i < lambda/2; i++)
            {
            	// select parents with corresponding select method
            	Integer1D i1 = parentSelect.select(population, problem);
            	Integer1D i2 = parentSelect.select(population, problem);
            	
                // Recombination
                Integer1D child[] = crossover.crossover(i1, i2); 
                if (loglevel > 1) System.out.println("parent1: "+i1+" "+problem.normalizedFitness(i1)+" "+problem.fitness(i1)+" "+problem.distance(i1));
                if (loglevel > 1) System.out.println("parent2: "+i2+" "+problem.normalizedFitness(i2)+" "+problem.fitness(i2)+" "+problem.distance(i2));
                if (loglevel > 1) System.out.println(" child1: "+child[0]+" "+problem.normalizedFitness(child[0])+" "+problem.fitness(child[0])+" "+problem.distance(child[0]));
                if (loglevel > 1) System.out.println(" child2: "+child[1]+" "+problem.normalizedFitness(child[1])+" "+problem.fitness(child[1])+" "+problem.distance(child[1]));
                //child[0].check();
                //child[1].check();
                // For each child
                for(int ch=0;ch<2;ch++)
                {
                	// mutation
                	if (mutationRate > random.nextDouble()) mutate.mutate(child[ch]);
                    
                    children.add(child[ch]);
                    //child[ch].check();
                    // update min and max individuals
                    if (problem.fitness(child[ch]) > maxFitness){
                    	maxFitness = problem.fitness(child[ch]);
                    	problem.setMaxFitness(problem.fitness(child[ch]));
                        maxInd = child[ch];
                    }
                    if (problem.fitness(child[ch]) < minFitness){
                    	minFitness = problem.fitness(child[ch]);
                    	problem.setMinFitness(problem.fitness(child[ch]));
                        minInd = child[ch];
                    }
                    // for benchmarks
                    bm.update(problem.distance(child[ch]));
                }
                if (loglevel > 1) System.out.println("mchild1: "+child[0]+" "+problem.normalizedFitness(child[0])+" "+problem.fitness(child[0])+" "+problem.distance(child[0]));
                if (loglevel > 1) System.out.println("mchild2: "+child[1]+" "+problem.normalizedFitness(child[1])+" "+problem.fitness(child[1])+" "+problem.distance(child[1]));
            }
            // select survivors
            Integer1D elite = null;
            // if elitism is set, select the best individual
            if (elitism)
            {
                SelectBest best = new SelectBest();
                int b = best.selecti(population, problem);
                elite = population.get(b);
                population.remove(b);
            }
            if (loglevel > 1) System.out.println("Survivor selection");
            // Steady State
            if (survivorSelectionMethod.equals("steadyStateRandom") || survivorSelectionMethod.equals("steadyStateWorst"))
            {
            	survivorSelect.removeN(population, problem, children.size());
                for(Integer1D ind: population) if (loglevel > 1) System.out.println("select: "+ind+" "+problem.normalizedFitness(ind)+" "+problem.fitness(ind)+" "+problem.distance(ind));
                population.addAll(children);
            }
            else
            {
                population.addAll(children);
                if (elitism) population = survivorSelect.selectN(population, problem, mu-1);
                else population = survivorSelect.selectN(population, problem, mu);
                for(Integer1D ind: population) if (loglevel > 1) System.out.println("select: "+ind+" "+problem.normalizedFitness(ind)+" "+problem.fitness(ind)+" "+problem.distance(ind));
            }
            if (elitism) population.add(elite);
            if (loglevel > 0) System.out.println("best: "+maxInd+" "+problem.fitness(maxInd)+" "+problem.distance(maxInd));
            if (problem.distance(maxInd) == 0)
            {
                //String filename = "file.txt";
                PrintStream out = new PrintStream(new FileOutputStream(prop.getProperty("outsudokufile")));
                for(int i=0;i<9;i++){
                	for(int j=0;j<9;j++){
                		out.print(maxInd.genes[i*9+j]);
                		if (j<8) out.print(" ");
                	}
                    out.println();
                }
                out.close();
            	break;//System.exit(0);
            }
            if (ite-lastimprovement>maxGenerationsWithoutImprovement) break;
            if (problem.distance(maxInd) != antd)
            {
            	lastimprovement = ite;
            }
            antd = problem.distance(maxInd);
        }
    	Date date2 = new Date();
    	long time = date2.getTime()-date1.getTime();
        if (loglevel >= 0) System.out.println("best: "+maxInd+" "+problem.fitness(maxInd)+" "+problem.distance(maxInd)+" "+ite+" "+time+" "+bm.count);
        return problem.distance(maxInd);
    }

    // we encourage the use of the entry point of the class Main100
    // that maintains the population diversity by running the algorithm
    // 100 times with different seeds
    // this main entry point runs the algorithm only one time
    public static void main(String args[]) throws Exception
    {
    	if (args.length>2 && args[2].equals("quiet")) loglevel=0;
    	main(args[0], Integer.parseInt(args[1]), new Benchmark());
    }
}
