区间
======

精简线段树（SegmentTreeLite）
------

简单的“单点更新、区间查询”线段树。线段树背后维护了一个数组 `a`，长度为 `n`。
支持
- `O(log n)` 时间内修改 `a[id] = val`：`update(id, val)`；
- `O(log n)` 时间内查询区间 `[l, r)` 的和 `sum(a[i] for i in [l, r))`：
`query(l, r)`。

示例代码支持查询区间和。如果想查询最大值，只需要修改头部为：
```java
long init = Long.MIN_VALUE;
long merge(long l, long r) {
    return Math.max(l, r);
}
```

**内部原理**

字段：
- `int N`：大于 n 的第一个整数（2 的幂）。
- `long[] vs`：存放统计信息，比如区间和、区间最大值。

方法：
- `update(id, val)` 会把信息从低往上更新：从树叶（编号为 `id + N`）到树根
（`编号为 1`）。
- `query(l, r)` 会 `merge` 树中的小区间。两个 `while` 循环选取了一些小区间，
拼接起来后跟 `[l, r)` 完美重合。可以证明：线段树每层被选取的小区间不超过 2 个。
