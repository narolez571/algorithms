package alex.algorithms.math;

import java.math.BigInteger;

public class GCD {
	public static BigInteger bitwiseGcd(BigInteger u, BigInteger v) {
		if (u.equals(BigInteger.ZERO))
			return v;
		if (v.equals(BigInteger.ZERO))
			return u;

		int uBit = u.getLowestSetBit();
		int vBit = v.getLowestSetBit();
		int k = (uBit <= vBit ? uBit : vBit);

		while (u.signum() > 0) {
			u = u.shiftRight(u.getLowestSetBit());
			v = v.shiftRight(v.getLowestSetBit());
			if (u.subtract(v).signum() >= 0) {
				u = (u.subtract(v)).shiftRight(1);
			} else {
				v = (v.subtract(u)).shiftRight(1);
			}
		}

		return v.shiftLeft(k);
	}

	public static int gcd(final int u, final int v) {
		if (u == v)
			return u;
		if (u == 0)
			return v;
		if (v == 0)
			return u;
		if ((~u & 1) > 0) {// u é par
			if ((v & 1) > 0) {// v impar
				return gcd(u >> 1, v);// v impar e u par
			}
			return gcd(u >> 1, v >> 1) << 1;// ambos são pares
		}
		if ((~v & 1) > 0)
			return gcd(u, v >> 1);// v par e u impar
		if (u > v) {
			return gcd((u - v) >> 1, v);
		} else {
			return gcd(u, (v - u) >> 1);
		}
	}

	public static int gcdAlex(final int u, final int v) {
		if (u == v)
			return u;
		if (u == 0 || v == 0)
			return java.lang.Math.max(u, v);
		if (u % 2 == 0) {
			if (v % 2 == 0) {
				return gcdAlex(u >> 1, v >> 1) << 1;
			}
			return gcdAlex(u >> 1, v);
		}
		if (v % 2 == 0) {
			return gcdAlex(u, v >> 1);
		}
		if (u > v)
			return gcdAlex((u - v) >> 1, v);
		return gcdAlex(u, (v - u) >> 1);
	}

	public static int lcm(final int a, final int b) {
		return a * b / gcd(a, b);
	}

	/**
	 * TODO: fazer iterativo
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		System.out.println(gcd(784777743, 928392839));
		System.out.println(gcd(943094, 343434));
		System.out.println(gcdAlex(1024, 7*1024));
		// System.out.println(lcm(3, 6));
		// System.out.println(bitwiseGcd(new BigInteger("94839048390483944"),
		// new BigInteger("893748937489374")));
	}

}
