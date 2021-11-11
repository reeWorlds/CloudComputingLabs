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
		mainTask.addJarFile("Factorozer.jar");
		
		System.out.print("class Solver method main added jars\n");
	     
		(new Solver()).run(new AMInfo(mainTask, (channel)null));
		
		System.out.print("class Solver method main finish work\n");
		
	    mainTask.end();
	}
	
	public void run(AMInfo info)
	{		
		long lt, lv, ln;
		
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(info.curtask.findFile("input.txt")));
			
			lt = new Long(in.readLine()).longValue();
			lv = new Long(in.readLine()).longValue();
			ln = new Long(in.readLine()).longValue();
		}
		catch (IOException e)
		{
			System.out.print("Error while reading input\n");
			
			e.printStackTrace();
			
			return;
		}
		
		System.out.print("class Solver method run read data from file\nt = " + lt + "\nv = " +
				lv + "\nn = " + ln + "\n");
		
		long tStart = System.nanoTime();
		
		solve(info, lt, lv, ln);
		
		long tEnd = System.nanoTime();
		
		System.out.println("time = " + ((tEnd - tStart) / 1000000) + "ms");
	}
	
	static public void solve(AMInfo info, long lnThread, long lv, long ln)
	{
		BigInteger nThreads = BigInteger.valueOf(lnThread);
		BigInteger v = BigInteger.valueOf(lv);
		BigInteger n = BigInteger.valueOf(ln);
		
		BigInteger root = n.sqrt();
		
		List <point> points = new ArrayList<point>();
		List <channel> channels = new ArrayList<channel>();
		
		for(BigInteger i = BigInteger.valueOf(0); i.compareTo(nThreads) == -1; i = i.add(BigInteger.valueOf(1)))
		{
			BigInteger tl = root.multiply(i).divide(nThreads);
			BigInteger tr = root.multiply(i.add(BigInteger.ONE)).divide(nThreads).subtract(BigInteger.ONE);
			
			int ii = i.intValue();
			
			points.add(info.createPoint());
			channels.add(points.get(ii).createChannel());
			
			points.get(ii).execute("Factorozer");
		    
			channels.get(ii).write(ln);
			channels.get(ii).write(tl.longValue());
			channels.get(ii).write(tr.longValue());
		}
		
		BigInteger p = null, q;
		
		long recRes = -1;
		for(int i = 0; i < lnThread; i++)
		{
			recRes = channels.get(i).readLong();
			
			if(recRes != -1)
			{
				p = BigInteger.valueOf(recRes);
			}
		}
		q = n.divide(p);
		
		System.out.println("p = " + p + "\nq = " + q + "\n");
		
		BigInteger x1, x2;
		
		x1 = solveModPrime(v, p);
		x2 = solveModPrime(v, q);
		
		if(x1 != null && x2 != null)
		{
			BigInteger l1, l2;
		
			if(true)
			{
				List<BigInteger> tr = gcd_ext(p, q);
				
				l1 = tr.get(1);
				l2 = tr.get(2);
			}
			
			System.out.println("x1 = " + x1 + "\nx2 = " + x2 + "\n");
			
			BigInteger y1, y2, y3, y4;
			
			y1 = l1.multiply(p).multiply(x2).add(l2.multiply(q).multiply(x1)).mod(n);
			y2 = l1.multiply(p).multiply(x2).subtract(l2.multiply(q).multiply(x1)).mod(n);
			y3 = n.subtract(y1);
			y4 = n.subtract(y2);
			
			System.out.println("y1 = " + y1);
			System.out.println("y2 = " + y2);
			System.out.println("y3 = " + y3);
			System.out.println("y4 = " + y4);
		}
		else
		{
			System.out.println("There is no solution");
		}
	}
	
	public static BigInteger solveModPrime(BigInteger a, BigInteger p)
	{
		a = a.mod(p);
		
		if(a.modPow(p.subtract(BigInteger.ONE).divide(BigInteger.TWO), p).compareTo(BigInteger.ONE) == 0)
		{
			return a.modPow(p.add(BigInteger.ONE).divide(BigInteger.valueOf(4)), p);
		}
		else
		{
			return null;
		}
	}
	
	public static List<BigInteger> gcd_ext(BigInteger a, BigInteger b)
    {
        if (a.compareTo(BigInteger.ZERO) == 0)
        {
        	List<BigInteger> res = new ArrayList<BigInteger>();
        	
        	res.add(b);
        	res.add(BigInteger.ZERO);
        	res.add(BigInteger.ONE);
        	
            return res;
        }
 
        BigInteger _x, _y, gcd;
        List<BigInteger> tr = gcd_ext(b.mod(a), a);
        gcd = tr.get(0);
        _x = tr.get(1);
        _y = tr.get(2);
 
        BigInteger x = _y.subtract(b.divide(a).multiply(_x));
        BigInteger y = _x;
        
        List<BigInteger> res = new ArrayList<BigInteger>();
    	
    	res.add(gcd);
    	res.add(x);
    	res.add(y);
    	
        return res;
    }
}
