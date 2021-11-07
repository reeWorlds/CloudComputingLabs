import java.math.*;
import java.util.*;

public class BigStepBabyStep {
	public void run(AMInfo info)
	{
		long lnThread, la, lb, lp, ll, lr;
		
		lnThread = info.parent.readLong();
		la = info.parent.readLong();
		lb = info.parent.readLong();
		lp = info.parent.readLong();
		ll = info.parent.readLong();
		lr = info.parent.readLong();
		
		BigInteger a = BigInteger.valueOf(la);
		BigInteger b = BigInteger.valueOf(lb);
		BigInteger p = BigInteger.valueOf(lp);
		BigInteger l = BigInteger.valueOf(ll);
		BigInteger r = BigInteger.valueOf(lr);
		
		HashMap<BigInteger, BigInteger> mp = new HashMap<BigInteger, BigInteger>();
		
		BigInteger root = p.sqrt();
		
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
			info.parent.write(solutionX.longValue());
		}
		else
		{
			info.parent.write(-1l);
		}
	}
}
