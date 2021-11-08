import java.io.*;
import java.math.*;
import java.util.*;

import parcs.*;

public class Solver implements AM
{
	
	public static void main(String[] args)
	{
		System.out.print("class Solver start method main\n");
		
		task mainTask = new task();
		 
		mainTask.addJarFile("Solver.jar");
		mainTask.addJarFile("BigStepBabyStep.jar");
		
		System.out.print("class Solver method main adder jars\n");
	     
		(new Solver()).run(new AMInfo(mainTask, (channel)null));
		
		System.out.print("class Solver method main finish work\n");
		
	    mainTask.end();
	}
	
	public void run(AMInfo info)
	{		
		long ln, la, lb, lp;
		
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(info.curtask.findFile("input.txt")));
			
			ln = new Long(in.readLine()).longValue();
			la = new Long(in.readLine()).longValue();
			lb = new Long(in.readLine()).longValue();
			lp = new Long(in.readLine()).longValue();
		}
		catch (IOException e)
		{
			System.out.print("Error while reading input\n");
			
			e.printStackTrace();
			
			return;
		}
		
		System.out.print("class Solver method run read data from file\na = " + la + "\nb = " +
				lb + "\np = " + lp + "\n");
		
		long tStart = System.nanoTime();
		
		long res = solve(info, ln, la, lb, lp);
		
		long tEnd = System.nanoTime();
		
		if(res == -1)
		{
			System.out.println("No solution for: " + la + " ^ x = " + lb + " (mod " + lp + ")");
		}
		else
		{
			System.out.println("x = " + res);
			System.out.println("" + la + " ^ " + res + " = " + lb + " (mod " + lp + ")");
		}
		
		System.out.println("time = " + ((tEnd - tStart) / 1000000) + "ms");
	}
	
	static public long solve(AMInfo info, long lnThread, long la, long lb, long lp)
	{		
		List<BigInteger> l = new ArrayList<BigInteger>();
		List<BigInteger> r = new ArrayList<BigInteger>();
		List<Long> sol = new ArrayList<Long>();
		
		BigInteger nThreads = BigInteger.valueOf(lnThread);
		BigInteger p = BigInteger.valueOf(lp);
		
		for(BigInteger i = BigInteger.valueOf(0); i.compareTo(nThreads) == -1; i = i.add(BigInteger.valueOf(1)))
		{
			BigInteger tl = p.multiply(i).divide(nThreads);
			BigInteger tr = p.multiply(i.add(BigInteger.valueOf(1))).divide(nThreads).subtract(BigInteger.valueOf(1));
			
			l.add(tl);
			r.add(tr);
		}
		
		List <point> points = new ArrayList<point>();
		List <channel> channels = new ArrayList<channel>();
		
		for(BigInteger i = BigInteger.valueOf(0); i.compareTo(nThreads) == -1; i = i.add(BigInteger.valueOf(1)))
		{
			BigInteger tl = p.multiply(i).divide(nThreads);
			BigInteger tr = p.multiply(i.add(BigInteger.valueOf(1))).divide(nThreads).subtract(BigInteger.valueOf(1));
			
			int ii = i.intValue();
			
			points.add(info.createPoint());
			channels.add(points.get(ii).createChannel());
			
			points.get(ii).execute("BigStepBabyStep");
		    
			channels.get(ii).write(la);
			channels.get(ii).write(lb);
			channels.get(ii).write(lp);
			
			channels.get(ii).write(tl.longValue());
			channels.get(ii).write(tr.longValue());
		}
		
		for(int i = 0; i < lnThread; i++)
		{
			sol.add(channels.get(i).readLong());
		}
		
		long res = -1;
		for(int i = 0; i < sol.size(); i++)
		{
			if(sol.get(i) != -1)
			{
				res = sol.get(i).longValue();
			}
		}
		
		return res;
	}	
}
