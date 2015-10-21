/**
 * This code is written for demonstrative purposes. We do gross things in ways
 * that are easy to understand (and subsequently remember). It's how I'd write
 * it in a competition.
 */

#include <algorithm>
#include <cstdio>
#include <string>
#include <vector>

#define uint unsigned int
#define UNMATCHED 0xffffffff

// The algorithm runs in O(VE) which is about MAX_INSTANCE_SIZE^3. An instance
// size of about 500 will run in under a second on your average 2013 machine.

#define MAX_INSTANCE_SIZE 500

using namespace std;

uint size_A_, size_B_, num_edges;

// We store the bipartite graph as your standard adjacency list.
vector<uint> graph[MAX_INSTANCE_SIZE];

// matched[u] == v and matched[v] == u iff (u, v) is a matched edge.
uint matched_[MAX_INSTANCE_SIZE];

// We use this for our DFS
bool visited_[MAX_INSTANCE_SIZE];

typedef struct _vertex {
  char type;
  uint id;
} _vertex;

// We map all the vertex identifiers into a unique id. Vertices in partition A
// are mapped to [ 0, size A ) and the vertices in partition B are mapped
// to [ size A, number of vertices ). This makes it easy to store the graph
// as a single array and converting back is also very easy.

uint vertex_to_uid(_vertex v) {
  return ((v.type == 'A') ? 0 : size_A_) + v.id;
}

_vertex uid_to_vertex(uint uid) {
  _vertex ret;
  if (uid < size_A_) {
    ret = (_vertex) { 'A', uid };
  } else {
    ret = (_vertex) { 'B', uid - size_A_ };
  }
  ret.type = (uid < size_A_) ? 'A' : 'B';
  ret.id = (uid < size_A_) ? uid : uid - size_A_;
  return ret;
}

// This is the heart of the algorithm. It starts from a free node and performs
// a depth-first search to find an augmenting path. The recursion returns true
// if it finds another free node at the end of an alternating path and then
// as the recursion unwinds, it 'swaps' all the matched edges by updating
// the matched array.
bool augment_path(uint uid) {
  visited_[uid] = true;

  for (uint i = 0; i < graph[uid].size(); i++) {
    uint neighbour = graph[uid][i];
    if (visited_[neighbour]) {
      continue;
    }

    // Base-case. We've reached a node at the end of an alternating path that
    // ends in a freenode.
    if (matched_[neighbour] == UNMATCHED) {
      matched_[uid] = neighbour;
      matched_[neighbour] = uid;
      return true;
    } else if (matched_[neighbour] != uid) {
      // This is not your standard DFS. Because we're DFSing along an
      // alternating path, when we choose the next vertex to visit, we MUST
      // then go along its matching edge. So we say we've visited the neighbour
      // trivially and then recursing on matched[neighbour].

      visited_[neighbour] = true;
      if (augment_path(matched_[neighbour])) {
        matched_[uid] = neighbour;
        matched_[neighbour] = uid;
        return true;
      }
    }
  }

  return false;
}

