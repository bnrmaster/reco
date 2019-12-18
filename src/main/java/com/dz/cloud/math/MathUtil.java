package com.dz.cloud.math;

public class MathUtil {

    private static long fact(long n)
    {
        long sum=1;
        for(int i=2; i<=n; i++)
        {
            sum*=i;
        }
        return sum;
    }

    public static long combineNum(long a, long b) {
        long m = a;
        long k = b;
        long ans1 = (fact(k)*(fact(m-k)));
        long ans2 = fact(m);
        return ans2/ans1;
    }
}
