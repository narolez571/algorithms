package alex.algorithms.math.projecteuler;

public class Euler427a {
    static final int p = 1000000009;
    static final double pinv = 1.0 / p;

    static long multiMod(final long a, final long b) {
        return (a % p) * (b % p) % p;
    }

    static long modPow(long b, long e, final long m) {
        long r = 1;
        while (e > 0) {
            if ((e & 1) != 0)
                r = r * b % m;
            {
                b = b * b % m;
                e >>= 1;
            }
        }
        return r;
    }

    public static void main(final String[] args) {

        int n = 7500000;
        int n1 = n - 1;
        int p2 = p - 2;
        long invN = modPow(n, p2, p);

        // build the factorial and power cache for fast binomial coeff.
        // calculation
        long [] cacheFact = new long[n + 1];
        long [] cacheInvFact = new long[n + 1];
        long [] cachePowN1 = new long[n + 1];
        long [] cachePowN = new long[n + 1];
        long [] cacheInvPowN = new long[n + 1];

        cacheFact[0] = cacheInvFact[0] = cachePowN1[0] = cachePowN[0] = cacheInvPowN[0] = 1;

        for (int i = 1; i <= n; i++) {
            cacheFact[i] = multiMod(cacheFact[i - 1], i);
            cacheInvFact[i] = modPow(cacheFact[i], p2, p);
            cachePowN1[i] = multiMod(cachePowN1[i - 1], n1);
            cachePowN[i] = multiMod(cachePowN[i - 1], n);
            cacheInvPowN[i] = multiMod(cacheInvPowN[i - 1], invN);
        }
        System.out.println("built caches, starting...");

        long result = 0;

        for (int m = n1; m > 0; m--) {
            System.out.println("m= " + m);

            long bound = n / (m + 1);

            for (int j = 0; j <= bound; j++) {
                int nmj0 = n - (m + 1) * j;
                int nmj1 = nmj0 - 1; // n - (m+1)*j - 1
                int nmj2 = nmj1 + j; // n - m*j - 1;

                long first = 0, second = 0;

                if (nmj2 >= j) {
                    long npow1 = nmj0 >= 0 ? cachePowN[nmj0] : cacheInvPowN[-nmj0];
                    long comb1 = multiMod(cacheFact[nmj2], multiMod(cacheInvFact[j], cacheInvFact[nmj1]));

                    first = multiMod(multiMod(cachePowN1[j], npow1), comb1);
                }

                if (j > 0) {
                    int exp2 = nmj0 + 1;
                    long npow2 = exp2 >= 0 ? cachePowN[exp2] : cacheInvPowN[-exp2];

                    long comb2 = multiMod(cacheFact[nmj2], multiMod(cacheInvFact[j - 1], cacheInvFact[nmj0]));

                    second = multiMod(multiMod(cachePowN1[j - 1], npow2), comb2);
                }

                long total = addMod(first, second, p);

                // multiply by (-1) ^ j
                if (j % 2 == 1)
                    total = p - total;

                result = addMod(result, total, p);
            }
            System.out.println("Result=" + result);
        }

        // result = n^(n+1)-result
        long nn = multiMod(cachePowN[n], n);
        result = subMod(nn, result, p);

        System.out.println("Result=" + result);
    }

    private static long subMod(final long first, final long second, final int p2) {
        return ((first % p2) - (second % p2)) % p2;
    }

    private static long addMod(final long first, final long second, final int p2) {

        return ((first % p2) + (second % p2)) % p2;
    }
}
