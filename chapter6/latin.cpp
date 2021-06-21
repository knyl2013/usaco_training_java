#define watch(x) cout << (#x) << " is " << (x) << endl
#include <bits/stdc++.h>
using namespace std;
using ll = long long;
int mask, n;
vector<bool> rowappear, colappear;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cin >> n;
    mask = (1 << n) - 1;
    rowappear = vector<bool>(n*n);
    colappear = vector<bool>(n*n);

}
