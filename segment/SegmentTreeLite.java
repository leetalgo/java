package segment;

import java.util.Arrays;

public class SegmentTreeLite {
    long init = 0;
    long merge(long l, long r) {
        return l + r;
    }

    int N;
    long[] is;

    SegmentTreeLite(int n) {
        N = Integer.highestOneBit(n) * 2;
        is = new long[N * 2];
        Arrays.fill(is, init);
    }

    void update(int id, long val) {
        id += N;
        is[id] = val;
        for (id /= 2; id > 0; id /= 2) {
            is[id] = merge(is[id * 2], is[id * 2 + 1]);
        }
    }

    long query(int l, int r) {
        long resL = init;
        long resR = init;
        while (0 < l && l + (l & -l) <= r) {
            int id = (N + l) / (l & -l);
            resL = merge(resL, is[id]);
            l += l & -l;
        }
        while (l < r) {
            int id = (N + r) / (r & -r) - 1;
            resR = merge(is[id], resR);
            r -= r & -r;
        }
        return merge(resL, resR);
    }
}
