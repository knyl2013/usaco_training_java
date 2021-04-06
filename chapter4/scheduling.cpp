#define watch(x) cout << (#x) << " is " << (x) << endl
#include <bits/stdc++.h>
using namespace std;
using ll = long long;
template <typename flow_t>
struct MaxFlowEdgeDemands {
  MaxFlowDinic<flow_t> mf;
  vector<flow_t> ind, outd;
  flow_t D;
  int n;
  void init(int _n) {
    n = _n; D = 0; mf.init(n + 2);
    ind.clear(); ind.resize(n);
    outd.clear(); outd.resize(n);
  }
  void add_edge(int s, int e, flow_t cap, flow_t demands = 0) {
    mf.add_edge(s, e, cap - demands);
    D += demands;
    ind[e] += demands; outd[s] += demands;
  }
  // returns { false, 0 } if infeasible
  // { true, maxflow } if feasible
  pair<bool, flow_t> solve(int source, int sink) {
    mf.add_edge(sink, source, numeric_limits<flow_t>::max());
    for (int i = 0; i < n; ++i) {
      if (ind[i]) mf.add_edge(n, i, ind[i]);
      if (outd[i]) mf.add_edge(i, n + 1, outd[i]);
    }
    if (mf.solve(n, n + 1) != D) return { false, 0 };
    for (int i = 0; i < n; ++i) {
      if (outd[i]) mf.graph[i].pop_back();
      if (ind[i]) mf.graph[i].pop_back();
    }
    return { true, mf.solve(source, sink) };
  }
};
int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    //int n;
    //cin >> n;
    // cout << "Yes" << endl;
    MaxFlowEdgeDemands flow = MaxFlowEdgeDemands();
    
}