package segment;

import java.util.Arrays;

public class SegmentTreeLite {
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
