import java.math.*;
import java.util.*;

import parcs.*;

public class Factorozer implements AM{
	public void run(AMInfo info)
	{
		long ln, ll, lr;
		
		ln = info.parent.readLong();
		ll = info.parent.readLong();
		lr = info.parent.readLong();
		
		System.out.print("class Factorozer method run read data from parent server\nn = " + ln + "\nl = " +
				ll + "\nr = " + lr + "\n\n");
		
		BigInteger n = BigInteger.valueOf(ln);
		BigInteger l = BigInteger.valueOf(ll);
		BigInteger r = BigInteger.valueOf(lr);
		
		BigInteger res = null;
		
		BigInteger n120 = BigInteger.valueOf(120);
		
		l = l.divide(n120).multiply(n120);
		r = r.divide(n120).multiply(n120).add(n120);
		
		ArrayList<BigInteger> primes = getPrimes(n120);
		
		for(BigInteger i = l; i.compareTo(r) <= 0; i = i.add(n120)) 
		{
			for(BigInteger ext : primes)
			{
				BigInteger a = i.add(ext);
				
				if(n.mod(a).compareTo(BigInteger.ZERO) == 0)
				{
					res = a;
				}
			}
		}
		
		if(res != null)
		{
			System.out.print("class Factorozer method run result p = " + res + "\n");
			
			info.parent.write(res.longValue());
		}
		else
		{
			System.out.print("class Factorozer method run find no solution\n");
			
			info.parent.write(-1l);
		}
	}
	
	public static ArrayList<BigInteger> getPrimes(BigInteger n)
	{
		ArrayList<BigInteger> primes = new ArrayList<BigInteger>();
		
		for(BigInteger i = BigInteger.TWO; i.compareTo(n) == -1; i = i.add(BigInteger.ONE))
		{
			boolean isPrime = true;
			
			for(BigInteger j = BigInteger.TWO; j.multiply(j).compareTo(i) <= 0; j = j.add(BigInteger.ONE))
			{
				if(i.mod(j).compareTo(BigInteger.ZERO) == 0)
				{
					isPrime = false;
					
					break;
				}
			}
			
			if(isPrime == true)
			{
				primes.add(i);
			}
		}
		
		return primes;
	}
}
