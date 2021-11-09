import java.math.*;
import java.util.*;

import parcs.*;

public class BigStepBabyStep implements AM{
	public void run(AMInfo info)
	{
		long la, lb, lp, ll, lr;
		
		la = info.parent.readLong();
		lb = info.parent.readLong();
		lp = info.parent.readLong();
		ll = info.parent.readLong();
		lr = info.parent.readLong();
		
		System.out.print("class BigStepBabyStep method run read data from parent server\na = " + la + "\nb = " +
				lb + "\np = " + lp + "\nll = " + ll + "\nlr = " + lr + "\n\n");
		
		BigInteger a = BigInteger.valueOf(la);
		BigInteger b = BigInteger.valueOf(lb);
		BigInteger p = BigInteger.valueOf(lp);
		BigInteger l = BigInteger.valueOf(ll);
		BigInteger r = BigInteger.valueOf(lr);
		
		HashMap<BigInteger, BigInteger> mp = new HashMap<BigInteger, BigInteger>();
		
		BigInteger maxRoot = BigInteger.valueOf(2500000);
		BigInteger root = p.sqrt();
		root = root.min(maxRoot);
		
		// make map
		BigInteger x;
		BigInteger power = BigInteger.valueOf(1);
		for(BigInteger i = BigInteger.valueOf(0); i.compareTo(root) == -1;)
		{
			mp.put(power, i);
			
			power = power.multiply(a).mod(p);
			i = i.add(BigInteger.valueOf(1));
		}
		
		// main algorithm
		BigInteger solutionX = null;
		
		BigInteger step = a.modInverse(p).modPow(root, p);

		for(x = l, power = a.modInverse(p).modPow(l, p); x.compareTo(r) <= 0;)
		{			
			if(mp.containsKey(power.multiply(b).mod(p)))
			{				
				solutionX = x.add(mp.get(power.multiply(b).mod(p)));
			}
			
			x = x.add(root);
			power = power.multiply(step).mod(p);
		}
		
		if(solutionX != null)
		{
			System.out.print("class BigStepBabyStep method run result x = " + solutionX + "\n");
			
			info.parent.write(solutionX.longValue());
		}
		else
		{
			System.out.print("class BigStepBabyStep method run find no solution\n");
			
			info.parent.write(-1l);
		}
	}
}
