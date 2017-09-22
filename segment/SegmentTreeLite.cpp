#include <bits/stdc++.h>
using namespace std;
typedef long long ll;


ll init = 0;
ll merge(ll l, ll r) {
    return l + r;
}

struct SegmentTreeLite {
    int N;
    vector<ll> vs;

    SegmentTreeLite(int n) {
        N = 1;
        while (N < n) N *= 2;
        vs = vector<ll>(N * 2, init);
    }

    void update(int id, ll val) {
        id += N;
        vs[id] = val;
        for (id /= 2; id > 0; id /= 2) {
            vs[id] = merge(vs[id * 2], vs[id * 2 + 1]);
        }
    }

    ll query(int l, int r) {
        ll resL = init;
        ll resR = init;
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
};

int main() {

    struct BruteForce {
        vector<ll> vs;
        BruteForce(int n) {
            vs = vector<ll>(n, init);
        }
        void update(int id, ll val) {
            vs[id] = val;
        }
        ll query(int l, int r) {
            ll res = init;
            for (int i = l; i < r; i++) {
                res = merge(res, vs[i]);
            }
            return res;
        }
    };

    srand(0);
    int n = 1000;
    int q = 10000;

    SegmentTreeLite smart(n);
    BruteForce force(n);
    while (q-- > 0) {
        if (rand() % 2) {
            int id = rand() % n;
            ll val = rand();
            smart.update(id, val);
            force.update(id, val);
        } else {
            int l = rand() % n;
            int r = l + rand() % (n - l) + 1;
            ll resSmart = smart.query(l, r);
            ll resForce = force.query(l, r);
            if (resSmart != resForce) {
                throw runtime_error("Test failed");
            }
        }
    }
    printf("Passed\n");
    return 0;
}
