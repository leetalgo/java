import java.util.*;

class SegmentTreeLite {
    long init = 0;
    long merge(long l, long r) {
        return l + r;
    }

    int N;
    long[] vs;

    SegmentTreeLite(int n) {
        N = Integer.highestOneBit(n) * 2;
        vs = new long[N * 2];
        Arrays.fill(vs, init);
    }

    void update(int id, long val) {
        id += N;
        vs[id] = val;
        for (id /= 2; id > 0; id /= 2) {
            vs[id] = merge(vs[id * 2], vs[id * 2 + 1]);
        }
    }

    long query(int l, int r) {
        long resL = init;
        long resR = init;
        while (0 < l && l + (l & -l) <= r) {
            int id = (N + l) / (l & -l);
            resL = merge(resL, vs[id]);
            l += l & -l;
        }
        while (l < r) {
            int id = (N + r) / (r & -r) - 1;
            resR = merge(vs[id], resR);
            r -= r & -r;
        }
        return merge(resL, resR);
    }
}

class SegmentTreeLiteTest {

    static class BruteForce {
        long init = 0;
        long merge(long l, long r) {
            return l + r;
        }

        long[] vs;
        BruteForce(int n) {
            vs = new long[n];
        }
        void update(int id, long val) {
            vs[id] = val;
        }
        long query(int l, int r) {
            long res = init;
            for (int i = l; i < r; i++) {
                res = merge(res, vs[i]);
            }
            return res;
        }
    }

    public static void main(String[] args) {
        Random rnd = new Random(0);
        int n = 1000;
        int q = 10000;

        SegmentTreeLite smart = new SegmentTreeLite(n);
        BruteForce force = new BruteForce(n);
        while (q-- > 0) {
            if (rnd.nextBoolean()) {
                int id = rnd.nextInt(n);
                long val = rnd.nextInt();
                smart.update(id, val);
                force.update(id, val);
            } else {
                int l = rnd.nextInt(n);
                int r = l + rnd.nextInt(n - l) + 1;
                long resSmart = smart.query(l, r);
                long resForce = force.query(l, r);
                if (resSmart != resForce) {
                    throw new RuntimeException("Test failed");
                }
            }
        }
        System.out.println("Passed");
    }
}
