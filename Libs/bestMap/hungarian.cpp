//
// File: hungarian.cpp
//
// MATLAB Coder version            : 2.6
// C/C++ source code generated on  : 20-Sep-2015 17:55:26
//

// Include files
#include "rt_nonfinite.h"
#include "bestMap.h"
#include "hungarian.h"
#include "bestMap_emxutil.h"
#include <stdio.h>
#include <iostream>

using namespace std;
// Function Declarations
static void eml_li_find(const emxArray_boolean_T *x, emxArray_int32_T *y);
static void hminired(emxArray_real_T *A);
static void hmreduce(emxArray_real_T *A, emxArray_real_T *CH, emxArray_real_T
                     *RH, const emxArray_real_T *LC, const emxArray_real_T *LR,
                     const emxArray_real_T *SLR);

// Function Definitions

//
// Arguments    : const emxArray_boolean_T *x
//                emxArray_int32_T *y
// Return Type  : void
//
static void eml_li_find(const emxArray_boolean_T *x, emxArray_int32_T *y)
{
  int k;
  int i;
  int j;
  k = 0;
  for (i = 1; i <= x->size[1]; i++) {
    if (x->data[i - 1]) {
      k++;
    }
  }

  j = y->size[0] * y->size[1];
  y->size[0] = 1;
  y->size[1] = k;
  emxEnsureCapacity((emxArray__common *)y, j, (int)sizeof(int));
  j = 0;
  for (i = 1; i <= x->size[1]; i++) {
    if (x->data[i - 1]) {
      y->data[j] = i;
      j++;
    }
  }
}

//
// HMINIRED Initial reduction of cost matrix for the Hungarian method.
//
// B=assredin(A)
// A - the unreduced cost matris.
// B - the reduced cost matrix with linked zeros in each row.
// Arguments    : emxArray_real_T *A
// Return Type  : void
//
static void hminired(emxArray_real_T *A)
{
  int n;
  int outsz[2];
  int ix;
  emxArray_real_T *colMin;
  int b_ix;
  int iy;
  int i;
  int nx;
  int idx;
  double mtmp;
  boolean_T exitg3;
  emxArray_real_T *varargin_1;
  boolean_T exitg2;
  emxArray_real_T *rowMin;
  emxArray_boolean_T *x;
  emxArray_int32_T *ii;
  emxArray_int32_T *jj;
  boolean_T exitg1;
  boolean_T guard1 = false;
  emxArray_int32_T *b_ii;
  emxArray_int32_T *b_jj;
  emxArray_real_T *j;
  emxArray_real_T *r1;
  emxArray_real_T *b_A;
  emxArray_boolean_T *v;
  emxArray_int32_T *b_n;

  //  Calculate the total cost.
  // T=sum(orig(logical(sparse(C,1:size(orig,2),1))));
  //  v1.0  96-06-13. Niclas Borlin, niclas@cs.umu.se.
  n = A->size[1];

  //  Subtract column-minimum values from each column.
  for (ix = 0; ix < 2; ix++) {
    outsz[ix] = A->size[ix];
  }

  b_emxInit_real_T(&colMin, 2);
  ix = colMin->size[0] * colMin->size[1];
  colMin->size[0] = 1;
  colMin->size[1] = outsz[1];
  emxEnsureCapacity((emxArray__common *)colMin, ix, (int)sizeof(double));
  b_ix = 0;
  iy = -1;
  for (i = 1; i <= A->size[1]; i++) {
    nx = b_ix;
    idx = b_ix + A->size[0];
    mtmp = A->data[b_ix];
    if (A->size[0] > 1) {
      if (rtIsNaN(A->data[b_ix])) {
        ix = b_ix + 1;
        exitg3 = false;
        while ((!exitg3) && (ix + 1 <= idx)) {
          nx = ix;
          if (!rtIsNaN(A->data[ix])) {
            mtmp = A->data[ix];
            exitg3 = true;
          } else {
            ix++;
          }
        }
      }

      if (nx + 1 < idx) {
        for (ix = nx + 1; ix + 1 <= idx; ix++) {
          if (A->data[ix] < mtmp) {
            mtmp = A->data[ix];
          }
        }
      }
    }

    iy++;
    colMin->data[iy] = mtmp;
    b_ix += A->size[0];
  }

  ix = A->size[0] * A->size[1];
  emxEnsureCapacity((emxArray__common *)A, ix, (int)sizeof(double));
  nx = A->size[1];
  for (ix = 0; ix < nx; ix++) {
    b_ix = A->size[0];
    for (iy = 0; iy < b_ix; iy++) {
      A->data[iy + A->size[0] * ix] -= colMin->data[colMin->size[0] * ix];
    }
  }

  b_emxInit_real_T(&varargin_1, 2);

  //  Subtract row-minimum values from each row.
  ix = varargin_1->size[0] * varargin_1->size[1];
  varargin_1->size[0] = A->size[1];
  varargin_1->size[1] = A->size[0];
  emxEnsureCapacity((emxArray__common *)varargin_1, ix, (int)sizeof(double));
  nx = A->size[0];
  for (ix = 0; ix < nx; ix++) {
    b_ix = A->size[1];
    for (iy = 0; iy < b_ix; iy++) {
      varargin_1->data[iy + varargin_1->size[0] * ix] = A->data[ix + A->size[0] *
        iy];
    }
  }

  for (ix = 0; ix < 2; ix++) {
    outsz[ix] = varargin_1->size[ix];
  }

  ix = colMin->size[0] * colMin->size[1];
  colMin->size[0] = 1;
  colMin->size[1] = outsz[1];
  emxEnsureCapacity((emxArray__common *)colMin, ix, (int)sizeof(double));
  b_ix = 0;
  iy = -1;
  for (i = 1; i <= varargin_1->size[1]; i++) {
    nx = b_ix;
    idx = b_ix + varargin_1->size[0];
    mtmp = varargin_1->data[b_ix];
    if (varargin_1->size[0] > 1) {
      if (rtIsNaN(varargin_1->data[b_ix])) {
        ix = b_ix + 1;
        exitg2 = false;
        while ((!exitg2) && (ix + 1 <= idx)) {
          nx = ix;
          if (!rtIsNaN(varargin_1->data[ix])) {
            mtmp = varargin_1->data[ix];
            exitg2 = true;
          } else {
            ix++;
          }
        }
      }

      if (nx + 1 < idx) {
        for (ix = nx + 1; ix + 1 <= idx; ix++) {
          if (varargin_1->data[ix] < mtmp) {
            mtmp = varargin_1->data[ix];
          }
        }
      }
    }

    iy++;
    colMin->data[iy] = mtmp;
    b_ix += varargin_1->size[0];
  }

  emxFree_real_T(&varargin_1);
  emxInit_real_T(&rowMin, 1);
  ix = rowMin->size[0];
  rowMin->size[0] = colMin->size[1];
  emxEnsureCapacity((emxArray__common *)rowMin, ix, (int)sizeof(double));
  nx = colMin->size[1];
  for (ix = 0; ix < nx; ix++) {
    rowMin->data[ix] = colMin->data[colMin->size[0] * ix];
  }

  ix = A->size[0] * A->size[1];
  emxEnsureCapacity((emxArray__common *)A, ix, (int)sizeof(double));
  nx = A->size[1];
  for (ix = 0; ix < nx; ix++) {
    b_ix = A->size[0];
    for (iy = 0; iy < b_ix; iy++) {
      A->data[iy + A->size[0] * ix] -= rowMin->data[iy];
    }
  }

  b_emxInit_boolean_T(&x, 2);

  //  Get positions of all zeros.
  ix = x->size[0] * x->size[1];
  x->size[0] = A->size[0];
  x->size[1] = A->size[1];
  emxEnsureCapacity((emxArray__common *)x, ix, (int)sizeof(boolean_T));
  nx = A->size[1];
  for (ix = 0; ix < nx; ix++) {
    b_ix = A->size[0];
    for (iy = 0; iy < b_ix; iy++) {
      x->data[iy + x->size[0] * ix] = (A->data[iy + A->size[0] * ix] == 0.0);
    }
  }

  emxInit_int32_T(&ii, 1);
  emxInit_int32_T(&jj, 1);
  nx = x->size[0] * x->size[1];
  idx = 0;
  ix = ii->size[0];
  ii->size[0] = nx;
  emxEnsureCapacity((emxArray__common *)ii, ix, (int)sizeof(int));
  ix = jj->size[0];
  jj->size[0] = nx;
  emxEnsureCapacity((emxArray__common *)jj, ix, (int)sizeof(int));
  if (nx == 0) {
    ix = ii->size[0];
    ii->size[0] = 0;
    emxEnsureCapacity((emxArray__common *)ii, ix, (int)sizeof(int));
    ix = jj->size[0];
    jj->size[0] = 0;
    emxEnsureCapacity((emxArray__common *)jj, ix, (int)sizeof(int));
  } else {
    b_ix = 1;
    iy = 1;
    exitg1 = false;
    while ((!exitg1) && (iy <= x->size[1])) {
      guard1 = false;
      if (x->data[(b_ix + x->size[0] * (iy - 1)) - 1]) {
        idx++;
        ii->data[idx - 1] = b_ix;
        jj->data[idx - 1] = iy;
        if (idx >= nx) {
          exitg1 = true;
        } else {
          guard1 = true;
        }
      } else {
        guard1 = true;
      }

      if (guard1) {
        b_ix++;
        if (b_ix > x->size[0]) {
          b_ix = 1;
          iy++;
        }
      }
    }

    if (nx == 1) {
      if (idx == 0) {
        ix = ii->size[0];
        ii->size[0] = 0;
        emxEnsureCapacity((emxArray__common *)ii, ix, (int)sizeof(int));
        ix = jj->size[0];
        jj->size[0] = 0;
        emxEnsureCapacity((emxArray__common *)jj, ix, (int)sizeof(int));
      }
    } else {
      if (1 > idx) {
        nx = 0;
      } else {
        nx = idx;
      }

      emxInit_int32_T(&b_ii, 1);
      ix = b_ii->size[0];
      b_ii->size[0] = nx;
      emxEnsureCapacity((emxArray__common *)b_ii, ix, (int)sizeof(int));
      for (ix = 0; ix < nx; ix++) {
        b_ii->data[ix] = ii->data[ix];
      }

      ix = ii->size[0];
      ii->size[0] = b_ii->size[0];
      emxEnsureCapacity((emxArray__common *)ii, ix, (int)sizeof(int));
      nx = b_ii->size[0];
      for (ix = 0; ix < nx; ix++) {
        ii->data[ix] = b_ii->data[ix];
      }

      emxFree_int32_T(&b_ii);
      if (1 > idx) {
        nx = 0;
      } else {
        nx = idx;
      }

      emxInit_int32_T(&b_jj, 1);
      ix = b_jj->size[0];
      b_jj->size[0] = nx;
      emxEnsureCapacity((emxArray__common *)b_jj, ix, (int)sizeof(int));
      for (ix = 0; ix < nx; ix++) {
        b_jj->data[ix] = jj->data[ix];
      }

      ix = jj->size[0];
      jj->size[0] = b_jj->size[0];
      emxEnsureCapacity((emxArray__common *)jj, ix, (int)sizeof(int));
      nx = b_jj->size[0];
      for (ix = 0; ix < nx; ix++) {
        jj->data[ix] = b_jj->data[ix];
      }

      emxFree_int32_T(&b_jj);
    }
  }

  emxFree_boolean_T(&x);
  ix = rowMin->size[0];
  rowMin->size[0] = ii->size[0];
  emxEnsureCapacity((emxArray__common *)rowMin, ix, (int)sizeof(double));
  nx = ii->size[0];
  for (ix = 0; ix < nx; ix++) {
    rowMin->data[ix] = ii->data[ix];
  }

  emxInit_real_T(&j, 1);
  ix = j->size[0];
  j->size[0] = jj->size[0];
  emxEnsureCapacity((emxArray__common *)j, ix, (int)sizeof(double));
  nx = jj->size[0];
  for (ix = 0; ix < nx; ix++) {
    j->data[ix] = jj->data[ix];
  }

  emxFree_int32_T(&jj);
  emxInit_real_T(&r1, 1);
  
      printf("\n");
    for (int i=0; i< A->size[1] ; i++)
      {
          for (int j=0; j< A->size[0] ; j++)
          {  
             // b_G->data[i*b_G->size[0]+j] = b_G->data[i*b_G->size[0]+j] + 0.0;
              printf("%f ", A->data[i*A->size[0]+j]);
          }
          printf("\n");
      }
  
  //  Extend A to give room for row zero list header column.
  ix = r1->size[0];
  r1->size[0] = n;
  emxEnsureCapacity((emxArray__common *)r1, ix, (int)sizeof(double));
  for (ix = 0; ix < n; ix++) {
    r1->data[ix] = 0.0;
  }

  b_emxInit_real_T(&b_A, 2);
  ix = b_A->size[0] * b_A->size[1];
  b_A->size[0] = A->size[0];
  b_A->size[1] = A->size[1] + 1;
  emxEnsureCapacity((emxArray__common *)b_A, ix, (int)sizeof(double));
  nx = A->size[1];
  for (ix = 0; ix < nx; ix++) {
    b_ix = A->size[0];
    for (iy = 0; iy < b_ix; iy++) {
      b_A->data[iy + b_A->size[0] * ix] = A->data[iy + A->size[0] * ix];
    }
  }

  for (ix = 0; ix < n; ix++) {
    b_A->data[ix + b_A->size[0] * A->size[1]] = r1->data[ix];
  }

  emxFree_real_T(&r1);
  ix = A->size[0] * A->size[1];
  A->size[0] = b_A->size[0];
  A->size[1] = b_A->size[1];
  emxEnsureCapacity((emxArray__common *)A, ix, (int)sizeof(double));
  nx = b_A->size[1];
  for (ix = 0; ix < nx; ix++) {
    b_ix = b_A->size[0];
    for (iy = 0; iy < b_ix; iy++) {
      A->data[iy + A->size[0] * ix] = b_A->data[iy + b_A->size[0] * ix];
    }
  }

  emxFree_real_T(&b_A);
  
    printf("\n");
    for (int i=0; i< A->size[1] ; i++)
      {
          for (int j=0; j< A->size[0] ; j++)
          {  
             // b_G->data[i*b_G->size[0]+j] = b_G->data[i*b_G->size[0]+j] + 0.0;
              printf("%f ", A->data[i*A->size[0]+j]);
          }
          printf("\n");
      }
  
  // A(1,n+1)=0;
  iy = 0;
  emxInit_boolean_T(&v, 1);
  emxInit_int32_T(&b_n, 1);
  while (iy <= n - 1) {
    //  Get all column in this row.
    ix = v->size[0];
    v->size[0] = rowMin->size[0];
    emxEnsureCapacity((emxArray__common *)v, ix, (int)sizeof(boolean_T));
    nx = rowMin->size[0];
    for (ix = 0; ix < nx; ix++) {
      v->data[ix] = (1.0 + (double)iy == rowMin->data[ix]);
    }

    b_ix = 0;
    for (i = 1; i <= v->size[0]; i++) {
      if (v->data[i - 1]) {
        b_ix++;
      }
    }

    ix = ii->size[0];
    ii->size[0] = b_ix;
    emxEnsureCapacity((emxArray__common *)ii, ix, (int)sizeof(int));
    b_ix = 0;
    for (i = 1; i <= v->size[0]; i++) {
      if (v->data[i - 1]) {
        ii->data[b_ix] = i;
        b_ix++;
      }
    }

    ix = colMin->size[0] * colMin->size[1];
    colMin->size[0] = 1;
    colMin->size[1] = ii->size[0];
    emxEnsureCapacity((emxArray__common *)colMin, ix, (int)sizeof(double));
    nx = ii->size[0];
    for (ix = 0; ix < nx; ix++) {
      colMin->data[colMin->size[0] * ix] = j->data[ii->data[ix] - 1];
    }

    //  Insert pointers in matrix.
    ix = b_n->size[0];
    b_n->size[0] = 1 + colMin->size[1];
    emxEnsureCapacity((emxArray__common *)b_n, ix, (int)sizeof(int));
    b_n->data[0] = n + 1;
    nx = colMin->size[1];
    for (ix = 0; ix < nx; ix++) {
      b_n->data[ix + 1] = (int)colMin->data[colMin->size[0] * ix];
    }

    ix = ii->size[0];
    ii->size[0] = b_n->size[0];
    emxEnsureCapacity((emxArray__common *)ii, ix, (int)sizeof(int));
    nx = b_n->size[0];
    for (ix = 0; ix < nx; ix++) {
      ii->data[ix] = b_n->data[ix] - 1;
    }

    nx = colMin->size[1];
    for (ix = 0; ix < nx; ix++) {
      A->data[iy + A->size[0] * ii->data[ix]] = -colMin->data[colMin->size[0] *
        ix];
    }

    A->data[iy + A->size[0] * ii->data[colMin->size[1]]] = 0.0;
    iy++;
  }

  emxFree_int32_T(&b_n);
  emxFree_boolean_T(&v);
  emxFree_int32_T(&ii);
  emxFree_real_T(&j);
  emxFree_real_T(&rowMin);
  emxFree_real_T(&colMin);
}

//
// HMREDUCE Reduce parts of cost matrix in the Hungerian method.
//
// [A,CH,RH]=hmreduce(A,CH,RH,LC,LR,SLC,SLR)
// Input:
// A   - Cost matrix.
// CH  - vector of column of 'next zeros' in each row.
// RH  - vector with list of unexplored rows.
// LC  - column labels.
// RC  - row labels.
// SLC - set of column labels.
// SLR - set of row labels.
//
// Output:
// A   - Reduced cost matrix.
// CH  - Updated vector of 'next zeros' in each row.
// RH  - Updated vector of unexplored rows.
// Arguments    : emxArray_real_T *A
//                emxArray_real_T *CH
//                emxArray_real_T *RH
//                const emxArray_real_T *LC
//                const emxArray_real_T *LR
//                const emxArray_real_T *SLR
// Return Type  : void
//
static void hmreduce(emxArray_real_T *A, emxArray_real_T *CH, emxArray_real_T
                     *RH, const emxArray_real_T *LC, const emxArray_real_T *LR,
                     const emxArray_real_T *SLR)
{
  emxArray_boolean_T *coveredRows;
  int n;
  int i1;
  int loop_ub;
  emxArray_boolean_T *coveredCols;
  emxArray_boolean_T *x;
  emxArray_int32_T *ii;
  int idx;
  int iy;
  boolean_T exitg9;
  boolean_T guard6 = false;
  emxArray_int32_T *b_ii;
  emxArray_real_T *r;
  boolean_T exitg8;
  boolean_T guard5 = false;
  emxArray_int32_T *c_ii;
  emxArray_real_T *c;
  emxArray_real_T *b_A;
  int b_n;
  int i2;
  unsigned int outsz[2];
  emxArray_real_T *colsInList;
  emxArray_real_T *b_r;
  int ix;
  int i;
  emxArray_int32_T *d_ii;
  emxArray_int32_T *jj;
  emxArray_real_T *b_c;
  emxArray_real_T *c_r;
  int32_T exitg6;
  int ixstop;
  double mtmp;
  int b_ix;
  boolean_T exitg7;
  boolean_T exitg5;
  emxArray_real_T *c_A;
  emxArray_boolean_T *d_r;
  emxArray_boolean_T *e_r;
  boolean_T exitg4;
  boolean_T guard4 = false;
  emxArray_int32_T *e_ii;
  boolean_T exitg3;
  boolean_T guard3 = false;
  emxArray_int32_T *f_ii;
  emxArray_boolean_T *b_x;
  boolean_T exitg2;
  boolean_T guard2 = false;
  emxArray_int32_T *g_ii;
  emxArray_int32_T *b_jj;
  emxArray_real_T *b_i;
  emxArray_real_T *j;
  emxArray_int32_T *h_ii;
  double c_c;
  boolean_T exitg1;
  boolean_T guard1 = false;
  emxArray_real_T *d_A;
  b_emxInit_boolean_T(&coveredRows, 2);

  //  v1.0  96-06-14. Niclas Borlin, niclas@cs.umu.se.
  n = A->size[0];

  //  Find which rows are covered, i.e. unlabelled.
  i1 = coveredRows->size[0] * coveredRows->size[1];
  coveredRows->size[0] = 1;
  coveredRows->size[1] = LR->size[1];
  emxEnsureCapacity((emxArray__common *)coveredRows, i1, (int)sizeof(boolean_T));
  loop_ub = LR->size[0] * LR->size[1];
  for (i1 = 0; i1 < loop_ub; i1++) {
    coveredRows->data[i1] = (LR->data[i1] == 0.0);
  }

  b_emxInit_boolean_T(&coveredCols, 2);

  //  Find which columns are covered, i.e. labelled.
  i1 = coveredCols->size[0] * coveredCols->size[1];
  coveredCols->size[0] = 1;
  coveredCols->size[1] = LC->size[1];
  emxEnsureCapacity((emxArray__common *)coveredCols, i1, (int)sizeof(boolean_T));
  loop_ub = LC->size[0] * LC->size[1];
  for (i1 = 0; i1 < loop_ub; i1++) {
    coveredCols->data[i1] = (LC->data[i1] != 0.0);
  }

  b_emxInit_boolean_T(&x, 2);
  i1 = x->size[0] * x->size[1];
  x->size[0] = 1;
  x->size[1] = coveredRows->size[1];
  emxEnsureCapacity((emxArray__common *)x, i1, (int)sizeof(boolean_T));
  loop_ub = coveredRows->size[0] * coveredRows->size[1];
  for (i1 = 0; i1 < loop_ub; i1++) {
    x->data[i1] = !coveredRows->data[i1];
  }

  b_emxInit_int32_T(&ii, 2);
  idx = 0;
  i1 = ii->size[0] * ii->size[1];
  ii->size[0] = 1;
  ii->size[1] = x->size[1];
  emxEnsureCapacity((emxArray__common *)ii, i1, (int)sizeof(int));
  iy = 1;
  exitg9 = false;
  while ((!exitg9) && (iy <= x->size[1])) {
    guard6 = false;
    if (x->data[iy - 1]) {
      idx++;
      ii->data[idx - 1] = iy;
      if (idx >= x->size[1]) {
        exitg9 = true;
      } else {
        guard6 = true;
      }
    } else {
      guard6 = true;
    }

    if (guard6) {
      iy++;
    }
  }

  if (x->size[1] == 1) {
    if (idx == 0) {
      i1 = ii->size[0] * ii->size[1];
      ii->size[0] = 1;
      ii->size[1] = 0;
      emxEnsureCapacity((emxArray__common *)ii, i1, (int)sizeof(int));
    }
  } else {
    if (1 > idx) {
      loop_ub = 0;
    } else {
      loop_ub = idx;
    }

    b_emxInit_int32_T(&b_ii, 2);
    i1 = b_ii->size[0] * b_ii->size[1];
    b_ii->size[0] = 1;
    b_ii->size[1] = loop_ub;
    emxEnsureCapacity((emxArray__common *)b_ii, i1, (int)sizeof(int));
    for (i1 = 0; i1 < loop_ub; i1++) {
      b_ii->data[b_ii->size[0] * i1] = ii->data[i1];
    }

    i1 = ii->size[0] * ii->size[1];
    ii->size[0] = 1;
    ii->size[1] = b_ii->size[1];
    emxEnsureCapacity((emxArray__common *)ii, i1, (int)sizeof(int));
    loop_ub = b_ii->size[1];
    for (i1 = 0; i1 < loop_ub; i1++) {
      ii->data[ii->size[0] * i1] = b_ii->data[b_ii->size[0] * i1];
    }

    emxFree_int32_T(&b_ii);
  }

  b_emxInit_real_T(&r, 2);
  i1 = r->size[0] * r->size[1];
  r->size[0] = 1;
  r->size[1] = ii->size[1];
  emxEnsureCapacity((emxArray__common *)r, i1, (int)sizeof(double));
  loop_ub = ii->size[0] * ii->size[1];
  for (i1 = 0; i1 < loop_ub; i1++) {
    r->data[i1] = ii->data[i1];
  }

  i1 = x->size[0] * x->size[1];
  x->size[0] = 1;
  x->size[1] = coveredCols->size[1];
  emxEnsureCapacity((emxArray__common *)x, i1, (int)sizeof(boolean_T));
  loop_ub = coveredCols->size[0] * coveredCols->size[1];
  for (i1 = 0; i1 < loop_ub; i1++) {
    x->data[i1] = !coveredCols->data[i1];
  }

  idx = 0;
  i1 = ii->size[0] * ii->size[1];
  ii->size[0] = 1;
  ii->size[1] = x->size[1];
  emxEnsureCapacity((emxArray__common *)ii, i1, (int)sizeof(int));
  iy = 1;
  exitg8 = false;
  while ((!exitg8) && (iy <= x->size[1])) {
    guard5 = false;
    if (x->data[iy - 1]) {
      idx++;
      ii->data[idx - 1] = iy;
      if (idx >= x->size[1]) {
        exitg8 = true;
      } else {
        guard5 = true;
      }
    } else {
      guard5 = true;
    }

    if (guard5) {
      iy++;
    }
  }

  if (x->size[1] == 1) {
    if (idx == 0) {
      i1 = ii->size[0] * ii->size[1];
      ii->size[0] = 1;
      ii->size[1] = 0;
      emxEnsureCapacity((emxArray__common *)ii, i1, (int)sizeof(int));
    }
  } else {
    if (1 > idx) {
      loop_ub = 0;
    } else {
      loop_ub = idx;
    }

    b_emxInit_int32_T(&c_ii, 2);
    i1 = c_ii->size[0] * c_ii->size[1];
    c_ii->size[0] = 1;
    c_ii->size[1] = loop_ub;
    emxEnsureCapacity((emxArray__common *)c_ii, i1, (int)sizeof(int));
    for (i1 = 0; i1 < loop_ub; i1++) {
      c_ii->data[c_ii->size[0] * i1] = ii->data[i1];
    }

    i1 = ii->size[0] * ii->size[1];
    ii->size[0] = 1;
    ii->size[1] = c_ii->size[1];
    emxEnsureCapacity((emxArray__common *)ii, i1, (int)sizeof(int));
    loop_ub = c_ii->size[1];
    for (i1 = 0; i1 < loop_ub; i1++) {
      ii->data[ii->size[0] * i1] = c_ii->data[c_ii->size[0] * i1];
    }

    emxFree_int32_T(&c_ii);
  }

  b_emxInit_real_T(&c, 2);
  i1 = c->size[0] * c->size[1];
  c->size[0] = 1;
  c->size[1] = ii->size[1];
  emxEnsureCapacity((emxArray__common *)c, i1, (int)sizeof(double));
  loop_ub = ii->size[0] * ii->size[1];
  for (i1 = 0; i1 < loop_ub; i1++) {
    c->data[i1] = ii->data[i1];
  }

  b_emxInit_real_T(&b_A, 2);

  //  Get minimum of uncovered elements.
  i1 = b_A->size[0] * b_A->size[1];
  b_A->size[0] = r->size[1];
  b_A->size[1] = c->size[1];
  emxEnsureCapacity((emxArray__common *)b_A, i1, (int)sizeof(double));
  loop_ub = c->size[1];
  for (i1 = 0; i1 < loop_ub; i1++) {
    b_n = r->size[1];
    for (i2 = 0; i2 < b_n; i2++) {
      b_A->data[i2 + b_A->size[0] * i1] = A->data[((int)r->data[r->size[0] * i2]
        + A->size[0] * ((int)c->data[c->size[0] * i1] - 1)) - 1];
    }
  }

  for (i1 = 0; i1 < 2; i1++) {
    outsz[i1] = (unsigned int)b_A->size[i1];
  }

  emxFree_real_T(&b_A);
  b_emxInit_real_T(&colsInList, 2);
  emxInit_real_T(&b_r, 1);
  i1 = colsInList->size[0] * colsInList->size[1];
  colsInList->size[0] = 1;
  colsInList->size[1] = (int)outsz[1];
  emxEnsureCapacity((emxArray__common *)colsInList, i1, (int)sizeof(double));
  i1 = b_r->size[0];
  b_r->size[0] = r->size[1];
  emxEnsureCapacity((emxArray__common *)b_r, i1, (int)sizeof(double));
  loop_ub = r->size[1];
  for (i1 = 0; i1 < loop_ub; i1++) {
    b_r->data[i1] = r->data[r->size[0] * i1];
  }

  b_n = b_r->size[0];
  ix = 1;
  iy = -1;
  i = 1;
  emxFree_real_T(&b_r);
  emxInit_int32_T(&d_ii, 1);
  emxInit_int32_T(&jj, 1);
  emxInit_real_T(&b_c, 1);
  emxInit_real_T(&c_r, 1);
  do {
    exitg6 = 0;
    i1 = b_c->size[0];
    b_c->size[0] = c->size[1];
    emxEnsureCapacity((emxArray__common *)b_c, i1, (int)sizeof(double));
    loop_ub = c->size[1];
    for (i1 = 0; i1 < loop_ub; i1++) {
      b_c->data[i1] = c->data[c->size[0] * i1];
    }

    if (i <= b_c->size[0]) {
      idx = ix;
      ixstop = (ix + b_n) - 1;
      i1 = d_ii->size[0];
      d_ii->size[0] = r->size[1];
      emxEnsureCapacity((emxArray__common *)d_ii, i1, (int)sizeof(int));
      loop_ub = r->size[1];
      for (i1 = 0; i1 < loop_ub; i1++) {
        d_ii->data[i1] = (int)r->data[r->size[0] * i1];
      }

      i1 = jj->size[0];
      jj->size[0] = c->size[1];
      emxEnsureCapacity((emxArray__common *)jj, i1, (int)sizeof(int));
      loop_ub = c->size[1];
      for (i1 = 0; i1 < loop_ub; i1++) {
        jj->data[i1] = (int)c->data[c->size[0] * i1];
      }

      i1 = d_ii->size[0];
      i2 = (ix - 1) % d_ii->size[0];
      mtmp = A->data[(d_ii->data[i2] + A->size[0] * (jj->data[(ix - 1) / i1] - 1))
        - 1];
      i1 = c_r->size[0];
      c_r->size[0] = r->size[1];
      emxEnsureCapacity((emxArray__common *)c_r, i1, (int)sizeof(double));
      loop_ub = r->size[1];
      for (i1 = 0; i1 < loop_ub; i1++) {
        c_r->data[i1] = r->data[r->size[0] * i1];
      }

      if (c_r->size[0] > 1) {
        if (rtIsNaN(mtmp)) {
          b_ix = ix;
          exitg7 = false;
          while ((!exitg7) && (b_ix + 1 <= ixstop)) {
            idx = b_ix + 1;
            i1 = d_ii->size[0];
            d_ii->size[0] = r->size[1];
            emxEnsureCapacity((emxArray__common *)d_ii, i1, (int)sizeof(int));
            loop_ub = r->size[1];
            for (i1 = 0; i1 < loop_ub; i1++) {
              d_ii->data[i1] = (int)r->data[r->size[0] * i1];
            }

            i1 = jj->size[0];
            jj->size[0] = c->size[1];
            emxEnsureCapacity((emxArray__common *)jj, i1, (int)sizeof(int));
            loop_ub = c->size[1];
            for (i1 = 0; i1 < loop_ub; i1++) {
              jj->data[i1] = (int)c->data[c->size[0] * i1];
            }

            i1 = d_ii->size[0];
            i2 = b_ix % d_ii->size[0];
            if (!rtIsNaN(A->data[(d_ii->data[i2] + A->size[0] * (jj->data[b_ix /
                   i1] - 1)) - 1])) {
              i1 = d_ii->size[0];
              d_ii->size[0] = r->size[1];
              emxEnsureCapacity((emxArray__common *)d_ii, i1, (int)sizeof(int));
              loop_ub = r->size[1];
              for (i1 = 0; i1 < loop_ub; i1++) {
                d_ii->data[i1] = (int)r->data[r->size[0] * i1];
              }

              i1 = jj->size[0];
              jj->size[0] = c->size[1];
              emxEnsureCapacity((emxArray__common *)jj, i1, (int)sizeof(int));
              loop_ub = c->size[1];
              for (i1 = 0; i1 < loop_ub; i1++) {
                jj->data[i1] = (int)c->data[c->size[0] * i1];
              }

              i1 = d_ii->size[0];
              i2 = b_ix % d_ii->size[0];
              mtmp = A->data[(d_ii->data[i2] + A->size[0] * (jj->data[b_ix / i1]
                - 1)) - 1];
              exitg7 = true;
            } else {
              b_ix++;
            }
          }
        }

        if (idx < ixstop) {
          while (idx + 1 <= ixstop) {
            i1 = d_ii->size[0];
            d_ii->size[0] = r->size[1];
            emxEnsureCapacity((emxArray__common *)d_ii, i1, (int)sizeof(int));
            loop_ub = r->size[1];
            for (i1 = 0; i1 < loop_ub; i1++) {
              d_ii->data[i1] = (int)r->data[r->size[0] * i1];
            }

            i1 = jj->size[0];
            jj->size[0] = c->size[1];
            emxEnsureCapacity((emxArray__common *)jj, i1, (int)sizeof(int));
            loop_ub = c->size[1];
            for (i1 = 0; i1 < loop_ub; i1++) {
              jj->data[i1] = (int)c->data[c->size[0] * i1];
            }

            i1 = d_ii->size[0];
            i2 = idx % d_ii->size[0];
            if (A->data[(d_ii->data[i2] + A->size[0] * (jj->data[idx / i1] - 1))
                - 1] < mtmp) {
              i1 = d_ii->size[0];
              d_ii->size[0] = r->size[1];
              emxEnsureCapacity((emxArray__common *)d_ii, i1, (int)sizeof(int));
              loop_ub = r->size[1];
              for (i1 = 0; i1 < loop_ub; i1++) {
                d_ii->data[i1] = (int)r->data[r->size[0] * i1];
              }

              i1 = jj->size[0];
              jj->size[0] = c->size[1];
              emxEnsureCapacity((emxArray__common *)jj, i1, (int)sizeof(int));
              loop_ub = c->size[1];
              for (i1 = 0; i1 < loop_ub; i1++) {
                jj->data[i1] = (int)c->data[c->size[0] * i1];
              }

              i1 = d_ii->size[0];
              i2 = idx % d_ii->size[0];
              mtmp = A->data[(d_ii->data[i2] + A->size[0] * (jj->data[idx / i1]
                - 1)) - 1];
            }

            idx++;
          }
        }
      }

      iy++;
      colsInList->data[iy] = mtmp;
      ix += b_n;
      i++;
    } else {
      exitg6 = 1;
    }
  } while (exitg6 == 0);

  emxFree_real_T(&c_r);
  emxFree_real_T(&b_c);
  idx = 1;
  mtmp = colsInList->data[0];
  if (colsInList->size[1] > 1) {
    if (rtIsNaN(colsInList->data[0])) {
      ix = 2;
      exitg5 = false;
      while ((!exitg5) && (ix <= colsInList->size[1])) {
        idx = ix;
        if (!rtIsNaN(colsInList->data[ix - 1])) {
          mtmp = colsInList->data[ix - 1];
          exitg5 = true;
        } else {
          ix++;
        }
      }
    }

    if (idx < colsInList->size[1]) {
      while (idx + 1 <= colsInList->size[1]) {
        if (colsInList->data[idx] < mtmp) {
          mtmp = colsInList->data[idx];
        }

        idx++;
      }
    }
  }

  b_emxInit_real_T(&c_A, 2);

  //  Subtract minimum from all uncovered elements.
  i1 = c_A->size[0] * c_A->size[1];
  c_A->size[0] = r->size[1];
  c_A->size[1] = c->size[1];
  emxEnsureCapacity((emxArray__common *)c_A, i1, (int)sizeof(double));
  loop_ub = c->size[1];
  for (i1 = 0; i1 < loop_ub; i1++) {
    b_n = r->size[1];
    for (i2 = 0; i2 < b_n; i2++) {
      c_A->data[i2 + c_A->size[0] * i1] = A->data[((int)r->data[r->size[0] * i2]
        + A->size[0] * ((int)c->data[c->size[0] * i1] - 1)) - 1] - mtmp;
    }
  }

  loop_ub = c_A->size[1];
  for (i1 = 0; i1 < loop_ub; i1++) {
    b_n = c_A->size[0];
    for (i2 = 0; i2 < b_n; i2++) {
      A->data[((int)r->data[r->size[0] * i2] + A->size[0] * ((int)c->data
                [c->size[0] * i1] - 1)) - 1] = c_A->data[i2 + c_A->size[0] * i1];
    }
  }

  emxFree_real_T(&c_A);

  //  Check all uncovered columns..
  b_n = 0;
  b_emxInit_boolean_T(&d_r, 2);
  b_emxInit_boolean_T(&e_r, 2);
  while (b_n <= c->size[1] - 1) {
    //  ...and uncovered rows in path order..
    for (ix = 0; ix < SLR->size[1]; ix++) {
      //  If this is a (new) zero..
      if (A->data[((int)SLR->data[ix] + A->size[0] * ((int)c->data[b_n] - 1)) -
          1] == 0.0) {
        //  If the row is not in unexplored list..
        if (RH->data[(int)SLR->data[ix] - 1] == 0.0) {
          //  ...insert it first in unexplored list.
          RH->data[(int)SLR->data[ix] - 1] = RH->data[n];
          RH->data[n] = SLR->data[ix];

          //  Mark this zero as "next free" in this row.
          CH->data[(int)SLR->data[ix] - 1] = c->data[b_n];
        }

        //  Find last unassigned zero on row I.
        loop_ub = A->size[1];
        iy = (int)SLR->data[ix];
        i1 = r->size[0] * r->size[1];
        r->size[0] = 1;
        r->size[1] = loop_ub;
        emxEnsureCapacity((emxArray__common *)r, i1, (int)sizeof(double));
        for (i1 = 0; i1 < loop_ub; i1++) {
          r->data[r->size[0] * i1] = A->data[(iy + A->size[0] * i1) - 1];
        }

        i1 = e_r->size[0] * e_r->size[1];
        e_r->size[0] = 1;
        e_r->size[1] = r->size[1];
        emxEnsureCapacity((emxArray__common *)e_r, i1, (int)sizeof(boolean_T));
        loop_ub = r->size[0] * r->size[1];
        for (i1 = 0; i1 < loop_ub; i1++) {
          e_r->data[i1] = (r->data[i1] < 0.0);
        }

        eml_li_find(e_r, ii);
        i1 = colsInList->size[0] * colsInList->size[1];
        colsInList->size[0] = 1;
        colsInList->size[1] = ii->size[1];
        emxEnsureCapacity((emxArray__common *)colsInList, i1, (int)sizeof(double));
        loop_ub = ii->size[0] * ii->size[1];
        for (i1 = 0; i1 < loop_ub; i1++) {
          colsInList->data[i1] = -r->data[ii->data[i1] - 1];
        }

        if (colsInList->size[1] == 0) {
          //  No zeros in the list.
          i1 = r->size[0] * r->size[1];
          r->size[0] = 1;
          r->size[1] = 1;
          emxEnsureCapacity((emxArray__common *)r, i1, (int)sizeof(double));
          r->data[0] = (double)n + 1.0;
        } else {
          i1 = d_r->size[0] * d_r->size[1];
          d_r->size[0] = 1;
          d_r->size[1] = colsInList->size[1];
          emxEnsureCapacity((emxArray__common *)d_r, i1, (int)sizeof(boolean_T));
          loop_ub = colsInList->size[0] * colsInList->size[1];
          for (i1 = 0; i1 < loop_ub; i1++) {
            d_r->data[i1] = (r->data[(int)colsInList->data[i1] - 1] == 0.0);
          }

          eml_li_find(d_r, ii);
          i1 = r->size[0] * r->size[1];
          r->size[0] = 1;
          r->size[1] = ii->size[1];
          emxEnsureCapacity((emxArray__common *)r, i1, (int)sizeof(double));
          loop_ub = ii->size[0] * ii->size[1];
          for (i1 = 0; i1 < loop_ub; i1++) {
            r->data[i1] = colsInList->data[ii->data[i1] - 1];
          }
        }

        //  Append this zero to end of list.
        i1 = d_ii->size[0];
        d_ii->size[0] = r->size[1];
        emxEnsureCapacity((emxArray__common *)d_ii, i1, (int)sizeof(int));
        loop_ub = r->size[1];
        for (i1 = 0; i1 < loop_ub; i1++) {
          d_ii->data[i1] = (int)r->data[r->size[0] * i1];
        }

        iy = (int)SLR->data[ix];
        ixstop = d_ii->size[0];
        for (i1 = 0; i1 < ixstop; i1++) {
          A->data[(iy + A->size[0] * (d_ii->data[i1] - 1)) - 1] = -c->data[b_n];
        }
      }
    }

    b_n++;
  }

  emxFree_boolean_T(&e_r);
  emxFree_boolean_T(&d_r);
  emxFree_real_T(&colsInList);

  //  Add minimum to all doubly covered elements.
  idx = 0;
  i1 = ii->size[0] * ii->size[1];
  ii->size[0] = 1;
  ii->size[1] = coveredRows->size[1];
  emxEnsureCapacity((emxArray__common *)ii, i1, (int)sizeof(int));
  iy = 1;
  exitg4 = false;
  while ((!exitg4) && (iy <= coveredRows->size[1])) {
    guard4 = false;
    if (coveredRows->data[iy - 1]) {
      idx++;
      ii->data[idx - 1] = iy;
      if (idx >= coveredRows->size[1]) {
        exitg4 = true;
      } else {
        guard4 = true;
      }
    } else {
      guard4 = true;
    }

    if (guard4) {
      iy++;
    }
  }

  if (coveredRows->size[1] == 1) {
    if (idx == 0) {
      i1 = ii->size[0] * ii->size[1];
      ii->size[0] = 1;
      ii->size[1] = 0;
      emxEnsureCapacity((emxArray__common *)ii, i1, (int)sizeof(int));
    }
  } else {
    if (1 > idx) {
      loop_ub = 0;
    } else {
      loop_ub = idx;
    }

    b_emxInit_int32_T(&e_ii, 2);
    i1 = e_ii->size[0] * e_ii->size[1];
    e_ii->size[0] = 1;
    e_ii->size[1] = loop_ub;
    emxEnsureCapacity((emxArray__common *)e_ii, i1, (int)sizeof(int));
    for (i1 = 0; i1 < loop_ub; i1++) {
      e_ii->data[e_ii->size[0] * i1] = ii->data[i1];
    }

    i1 = ii->size[0] * ii->size[1];
    ii->size[0] = 1;
    ii->size[1] = e_ii->size[1];
    emxEnsureCapacity((emxArray__common *)ii, i1, (int)sizeof(int));
    loop_ub = e_ii->size[1];
    for (i1 = 0; i1 < loop_ub; i1++) {
      ii->data[ii->size[0] * i1] = e_ii->data[e_ii->size[0] * i1];
    }

    emxFree_int32_T(&e_ii);
  }

  emxFree_boolean_T(&coveredRows);
  i1 = r->size[0] * r->size[1];
  r->size[0] = 1;
  r->size[1] = ii->size[1];
  emxEnsureCapacity((emxArray__common *)r, i1, (int)sizeof(double));
  loop_ub = ii->size[0] * ii->size[1];
  for (i1 = 0; i1 < loop_ub; i1++) {
    r->data[i1] = ii->data[i1];
  }

  idx = 0;
  i1 = ii->size[0] * ii->size[1];
  ii->size[0] = 1;
  ii->size[1] = coveredCols->size[1];
  emxEnsureCapacity((emxArray__common *)ii, i1, (int)sizeof(int));
  iy = 1;
  exitg3 = false;
  while ((!exitg3) && (iy <= coveredCols->size[1])) {
    guard3 = false;
    if (coveredCols->data[iy - 1]) {
      idx++;
      ii->data[idx - 1] = iy;
      if (idx >= coveredCols->size[1]) {
        exitg3 = true;
      } else {
        guard3 = true;
      }
    } else {
      guard3 = true;
    }

    if (guard3) {
      iy++;
    }
  }

  if (coveredCols->size[1] == 1) {
    if (idx == 0) {
      i1 = ii->size[0] * ii->size[1];
      ii->size[0] = 1;
      ii->size[1] = 0;
      emxEnsureCapacity((emxArray__common *)ii, i1, (int)sizeof(int));
    }
  } else {
    if (1 > idx) {
      loop_ub = 0;
    } else {
      loop_ub = idx;
    }

    b_emxInit_int32_T(&f_ii, 2);
    i1 = f_ii->size[0] * f_ii->size[1];
    f_ii->size[0] = 1;
    f_ii->size[1] = loop_ub;
    emxEnsureCapacity((emxArray__common *)f_ii, i1, (int)sizeof(int));
    for (i1 = 0; i1 < loop_ub; i1++) {
      f_ii->data[f_ii->size[0] * i1] = ii->data[i1];
    }

    i1 = ii->size[0] * ii->size[1];
    ii->size[0] = 1;
    ii->size[1] = f_ii->size[1];
    emxEnsureCapacity((emxArray__common *)ii, i1, (int)sizeof(int));
    loop_ub = f_ii->size[1];
    for (i1 = 0; i1 < loop_ub; i1++) {
      ii->data[ii->size[0] * i1] = f_ii->data[f_ii->size[0] * i1];
    }

    emxFree_int32_T(&f_ii);
  }

  emxFree_boolean_T(&coveredCols);
  i1 = c->size[0] * c->size[1];
  c->size[0] = 1;
  c->size[1] = ii->size[1];
  emxEnsureCapacity((emxArray__common *)c, i1, (int)sizeof(double));
  loop_ub = ii->size[0] * ii->size[1];
  for (i1 = 0; i1 < loop_ub; i1++) {
    c->data[i1] = ii->data[i1];
  }

  b_emxInit_boolean_T(&b_x, 2);

  //  Take care of the zeros we will remove.
  i1 = b_x->size[0] * b_x->size[1];
  b_x->size[0] = r->size[1];
  b_x->size[1] = c->size[1];
  emxEnsureCapacity((emxArray__common *)b_x, i1, (int)sizeof(boolean_T));
  loop_ub = c->size[1];
  for (i1 = 0; i1 < loop_ub; i1++) {
    b_n = r->size[1];
    for (i2 = 0; i2 < b_n; i2++) {
      b_x->data[i2 + b_x->size[0] * i1] = (A->data[((int)r->data[r->size[0] * i2]
        + A->size[0] * ((int)c->data[c->size[0] * i1] - 1)) - 1] <= 0.0);
    }
  }

  ix = b_x->size[0] * b_x->size[1];
  idx = 0;
  i1 = d_ii->size[0];
  d_ii->size[0] = ix;
  emxEnsureCapacity((emxArray__common *)d_ii, i1, (int)sizeof(int));
  i1 = jj->size[0];
  jj->size[0] = ix;
  emxEnsureCapacity((emxArray__common *)jj, i1, (int)sizeof(int));
  if (ix == 0) {
    i1 = d_ii->size[0];
    d_ii->size[0] = 0;
    emxEnsureCapacity((emxArray__common *)d_ii, i1, (int)sizeof(int));
    i1 = jj->size[0];
    jj->size[0] = 0;
    emxEnsureCapacity((emxArray__common *)jj, i1, (int)sizeof(int));
  } else {
    iy = 1;
    b_n = 1;
    exitg2 = false;
    while ((!exitg2) && (b_n <= b_x->size[1])) {
      guard2 = false;
      if (b_x->data[(iy + b_x->size[0] * (b_n - 1)) - 1]) {
        idx++;
        d_ii->data[idx - 1] = iy;
        jj->data[idx - 1] = b_n;
        if (idx >= ix) {
          exitg2 = true;
        } else {
          guard2 = true;
        }
      } else {
        guard2 = true;
      }

      if (guard2) {
        iy++;
        if (iy > b_x->size[0]) {
          iy = 1;
          b_n++;
        }
      }
    }

    if (ix == 1) {
      if (idx == 0) {
        i1 = d_ii->size[0];
        d_ii->size[0] = 0;
        emxEnsureCapacity((emxArray__common *)d_ii, i1, (int)sizeof(int));
        i1 = jj->size[0];
        jj->size[0] = 0;
        emxEnsureCapacity((emxArray__common *)jj, i1, (int)sizeof(int));
      }
    } else {
      if (1 > idx) {
        loop_ub = 0;
      } else {
        loop_ub = idx;
      }

      emxInit_int32_T(&g_ii, 1);
      i1 = g_ii->size[0];
      g_ii->size[0] = loop_ub;
      emxEnsureCapacity((emxArray__common *)g_ii, i1, (int)sizeof(int));
      for (i1 = 0; i1 < loop_ub; i1++) {
        g_ii->data[i1] = d_ii->data[i1];
      }

      i1 = d_ii->size[0];
      d_ii->size[0] = g_ii->size[0];
      emxEnsureCapacity((emxArray__common *)d_ii, i1, (int)sizeof(int));
      loop_ub = g_ii->size[0];
      for (i1 = 0; i1 < loop_ub; i1++) {
        d_ii->data[i1] = g_ii->data[i1];
      }

      emxFree_int32_T(&g_ii);
      if (1 > idx) {
        loop_ub = 0;
      } else {
        loop_ub = idx;
      }

      emxInit_int32_T(&b_jj, 1);
      i1 = b_jj->size[0];
      b_jj->size[0] = loop_ub;
      emxEnsureCapacity((emxArray__common *)b_jj, i1, (int)sizeof(int));
      for (i1 = 0; i1 < loop_ub; i1++) {
        b_jj->data[i1] = jj->data[i1];
      }

      i1 = jj->size[0];
      jj->size[0] = b_jj->size[0];
      emxEnsureCapacity((emxArray__common *)jj, i1, (int)sizeof(int));
      loop_ub = b_jj->size[0];
      for (i1 = 0; i1 < loop_ub; i1++) {
        jj->data[i1] = b_jj->data[i1];
      }

      emxFree_int32_T(&b_jj);
    }
  }

  emxFree_boolean_T(&b_x);
  emxInit_real_T(&b_i, 1);
  i1 = b_i->size[0];
  b_i->size[0] = d_ii->size[0];
  emxEnsureCapacity((emxArray__common *)b_i, i1, (int)sizeof(double));
  loop_ub = d_ii->size[0];
  for (i1 = 0; i1 < loop_ub; i1++) {
    b_i->data[i1] = d_ii->data[i1];
  }

  emxInit_real_T(&j, 1);
  i1 = j->size[0];
  j->size[0] = jj->size[0];
  emxEnsureCapacity((emxArray__common *)j, i1, (int)sizeof(double));
  loop_ub = jj->size[0];
  for (i1 = 0; i1 < loop_ub; i1++) {
    j->data[i1] = jj->data[i1];
  }

  emxFree_int32_T(&jj);
  ixstop = b_i->size[0];
  i = 1;
  b_emxInit_int32_T(&h_ii, 2);
  while (i - 1 <= ixstop - 1) {
    //  Find zero before this in this row.
    loop_ub = A->size[1];
    ix = (int)r->data[(int)b_i->data[i - 1] - 1];
    c_c = -c->data[(int)j->data[i - 1] - 1];
    i1 = x->size[0] * x->size[1];
    x->size[0] = 1;
    x->size[1] = loop_ub;
    emxEnsureCapacity((emxArray__common *)x, i1, (int)sizeof(boolean_T));
    for (i1 = 0; i1 < loop_ub; i1++) {
      x->data[x->size[0] * i1] = (A->data[(ix + A->size[0] * i1) - 1] == c_c);
    }

    idx = 0;
    i1 = ii->size[0] * ii->size[1];
    ii->size[0] = 1;
    ii->size[1] = x->size[1];
    emxEnsureCapacity((emxArray__common *)ii, i1, (int)sizeof(int));
    iy = 1;
    exitg1 = false;
    while ((!exitg1) && (iy <= x->size[1])) {
      guard1 = false;
      if (x->data[iy - 1]) {
        idx++;
        ii->data[idx - 1] = iy;
        if (idx >= x->size[1]) {
          exitg1 = true;
        } else {
          guard1 = true;
        }
      } else {
        guard1 = true;
      }

      if (guard1) {
        iy++;
      }
    }

    if (x->size[1] == 1) {
      if (idx == 0) {
        i1 = ii->size[0] * ii->size[1];
        ii->size[0] = 1;
        ii->size[1] = 0;
        emxEnsureCapacity((emxArray__common *)ii, i1, (int)sizeof(int));
      }
    } else {
      if (1 > idx) {
        loop_ub = 0;
      } else {
        loop_ub = idx;
      }

      i1 = h_ii->size[0] * h_ii->size[1];
      h_ii->size[0] = 1;
      h_ii->size[1] = loop_ub;
      emxEnsureCapacity((emxArray__common *)h_ii, i1, (int)sizeof(int));
      for (i1 = 0; i1 < loop_ub; i1++) {
        h_ii->data[h_ii->size[0] * i1] = ii->data[i1];
      }

      i1 = ii->size[0] * ii->size[1];
      ii->size[0] = 1;
      ii->size[1] = h_ii->size[1];
      emxEnsureCapacity((emxArray__common *)ii, i1, (int)sizeof(int));
      loop_ub = h_ii->size[1];
      for (i1 = 0; i1 < loop_ub; i1++) {
        ii->data[ii->size[0] * i1] = h_ii->data[h_ii->size[0] * i1];
      }
    }

    //  Link past it.
    i1 = d_ii->size[0];
    d_ii->size[0] = ii->size[1];
    emxEnsureCapacity((emxArray__common *)d_ii, i1, (int)sizeof(int));
    loop_ub = ii->size[1];
    for (i1 = 0; i1 < loop_ub; i1++) {
      d_ii->data[i1] = ii->data[ii->size[0] * i1];
    }

    ix = (int)r->data[(int)b_i->data[i - 1] - 1];
    c_c = A->data[((int)r->data[(int)b_i->data[i - 1] - 1] + A->size[0] * ((int)
      c->data[(int)j->data[i - 1] - 1] - 1)) - 1];
    b_n = d_ii->size[0];
    for (i1 = 0; i1 < b_n; i1++) {
      A->data[(ix + A->size[0] * (d_ii->data[i1] - 1)) - 1] = c_c;
    }

    //  Mark it as assigned.
    A->data[((int)r->data[(int)b_i->data[i - 1] - 1] + A->size[0] * ((int)
              c->data[(int)j->data[i - 1] - 1] - 1)) - 1] = 0.0;
    i++;
  }

  emxFree_int32_T(&h_ii);
  emxFree_int32_T(&d_ii);
  emxFree_int32_T(&ii);
  emxFree_boolean_T(&x);
  emxFree_real_T(&j);
  emxFree_real_T(&b_i);
  b_emxInit_real_T(&d_A, 2);
  i1 = d_A->size[0] * d_A->size[1];
  d_A->size[0] = r->size[1];
  d_A->size[1] = c->size[1];
  emxEnsureCapacity((emxArray__common *)d_A, i1, (int)sizeof(double));
  loop_ub = c->size[1];
  for (i1 = 0; i1 < loop_ub; i1++) {
    b_n = r->size[1];
    for (i2 = 0; i2 < b_n; i2++) {
      d_A->data[i2 + d_A->size[0] * i1] = A->data[((int)r->data[r->size[0] * i2]
        + A->size[0] * ((int)c->data[c->size[0] * i1] - 1)) - 1] + mtmp;
    }
  }

  loop_ub = d_A->size[1];
  for (i1 = 0; i1 < loop_ub; i1++) {
    b_n = d_A->size[0];
    for (i2 = 0; i2 < b_n; i2++) {
      A->data[((int)r->data[r->size[0] * i2] + A->size[0] * ((int)c->data
                [c->size[0] * i1] - 1)) - 1] = d_A->data[i2 + d_A->size[0] * i1];
    }
  }

  emxFree_real_T(&d_A);
  emxFree_real_T(&c);
  emxFree_real_T(&r);
}

//
// HUNGARIAN Solve the Assignment problem using the Hungarian method.
//
// [C,T]=hungarian(A)
// A - a square cost matrix.
// C - the optimal assignment.
// T - the cost of the optimal assignment.
// s.t. T = trace(A(C,:)) is minimized over all possible assignments.
// Arguments    : emxArray_real_T *A
//                emxArray_real_T *C
// Return Type  : void
//
void hungarian(emxArray_real_T *A, emxArray_real_T *C)
{
  emxArray_real_T *b_A;
  int n;
  int i0;
  int idx;
  int lm;
  emxArray_real_T *LZ;
  emxArray_real_T *NZ;
  double l;
  double r;
  boolean_T exitg6;
  boolean_T exitg5;
  double m;
  emxArray_boolean_T *b_C;
  emxArray_int32_T *ii;
  emxArray_boolean_T *x;
  boolean_T exitg4;
  boolean_T guard2 = false;
  emxArray_int32_T *b_ii;
  emxArray_real_T *U;
  emxArray_real_T *CH;
  emxArray_real_T *RH;
  emxArray_real_T *SLR;
  emxArray_int32_T *r0;
  emxArray_int32_T *c_ii;
  int32_T exitg3;
  int b_l;
  int32_T exitg1;
  boolean_T exitg2;
  boolean_T guard1 = false;
  b_emxInit_real_T(&b_A, 2);

  //  Adapted from the FORTRAN IV code in Carpaneto and Toth, "Algorithm 548:
  //  Solution of the assignment problem [H]", ACM Transactions on
  //  Mathematical Software, 6(1):104-111, 1980.
  //  v1.0  96-06-14. Niclas Borlin, niclas@cs.umu.se.
  //                  Department of Computing Science, Ume? University,
  //                  Sweden.
  //                  All standard disclaimers apply.
  //  A substantial effort was put into this code. If you use it for a
  //  publication or otherwise, please include an acknowledgement or at least
  //  notify me by email. /Niclas
  n = A->size[1];

  //  Save original cost matrix.
  //  Reduce matrix.
  hminired(A);

  //  Do an initial assignment.
  // HMINIASS Initial assignment of the Hungarian method.
  //
  // [B,C,U]=hminiass(A)
  // A - the reduced cost matrix.
  // B - the reduced cost matrix, with assigned zeros removed from lists.
  // C - a vector. C(J)=I means row I is assigned to column J,
  //               i.e. there is an assigned zero in position I,J.
  // U - a vector with a linked list of unassigned rows.
  i0 = b_A->size[0] * b_A->size[1];
  b_A->size[0] = A->size[0];
  b_A->size[1] = A->size[1];
  emxEnsureCapacity((emxArray__common *)b_A, i0, (int)sizeof(double));
  idx = A->size[0] * A->size[1];
  for (i0 = 0; i0 < idx; i0++) {
    b_A->data[i0] = A->data[i0];
  }

  //  v1.0  96-06-14. Niclas Borlin, niclas@cs.umu.se.
  //  Initalize return vectors.
  i0 = C->size[0] * C->size[1];
  C->size[0] = 1;
  emxEnsureCapacity((emxArray__common *)C, i0, (int)sizeof(double));
  lm = A->size[0];
  i0 = C->size[0] * C->size[1];
  C->size[1] = lm;
  emxEnsureCapacity((emxArray__common *)C, i0, (int)sizeof(double));
  idx = A->size[0];
  for (i0 = 0; i0 < idx; i0++) {
    C->data[i0] = 0.0;
  }

  b_emxInit_real_T(&LZ, 2);

  //  Initialize last/next zero "pointers".
  i0 = LZ->size[0] * LZ->size[1];
  LZ->size[0] = 1;
  emxEnsureCapacity((emxArray__common *)LZ, i0, (int)sizeof(double));
  lm = A->size[0];
  i0 = LZ->size[0] * LZ->size[1];
  LZ->size[1] = lm;
  emxEnsureCapacity((emxArray__common *)LZ, i0, (int)sizeof(double));
  idx = A->size[0];
  for (i0 = 0; i0 < idx; i0++) {
    LZ->data[i0] = 0.0;
  }

  b_emxInit_real_T(&NZ, 2);
  i0 = NZ->size[0] * NZ->size[1];
  NZ->size[0] = 1;
  emxEnsureCapacity((emxArray__common *)NZ, i0, (int)sizeof(double));
  lm = A->size[0];
  i0 = NZ->size[0] * NZ->size[1];
  NZ->size[1] = lm;
  emxEnsureCapacity((emxArray__common *)NZ, i0, (int)sizeof(double));
  idx = A->size[0];
  for (i0 = 0; i0 < idx; i0++) {
    NZ->data[i0] = 0.0;
  }

  for (idx = 0; idx < A->size[0]; idx++) {
    //  Set j to first unassigned zero in row i.
    l = (double)A->size[0] + 1.0;
    r = -b_A->data[idx + b_A->size[0] * A->size[0]];

    //  Repeat until we have no more zeros (j==0) or we find a zero
    //  in an unassigned column (c(j)==0).
    exitg6 = false;
    while ((!exitg6) && (C->data[(int)r - 1] != 0.0)) {
      //  Advance lj and j in zero list.
      l = r;
      r = -b_A->data[idx + b_A->size[0] * ((int)r - 1)];

      //  Stop if we hit end of list.
      if (r == 0.0) {
        exitg6 = true;
      }
    }

    if (r != 0.0) {
      //  We found a zero in an unassigned column.
      //  Assign row i to column j.
      C->data[(int)r - 1] = 1.0 + (double)idx;

      //  Remove A(i,j) from unassigned zero list.
      b_A->data[idx + b_A->size[0] * ((int)l - 1)] = b_A->data[idx + b_A->size[0]
        * ((int)r - 1)];

      //  Update next/last unassigned zero pointers.
      NZ->data[idx] = -b_A->data[idx + b_A->size[0] * ((int)r - 1)];
      LZ->data[idx] = l;

      //  Indicate A(i,j) is an assigned zero.
      b_A->data[idx + b_A->size[0] * ((int)r - 1)] = 0.0;
    } else {
      //  We found no zero in an unassigned column.
      //  Check all zeros in this row.
      l = (double)A->size[0] + 1.0;
      r = -b_A->data[idx + b_A->size[0] * A->size[0]];

      //  Check all zeros in this row for a suitable zero in another row.
      exitg5 = false;
      while ((!exitg5) && (r != 0.0)) {
        //  Check the in the row assigned to this column.
        //  Pick up last/next pointers.
        lm = (int)LZ->data[(int)C->data[(int)r - 1] - 1];
        m = NZ->data[(int)C->data[(int)r - 1] - 1];

        //  Check all unchecked zeros in free list of this row.
        while ((m != 0.0) && (!(C->data[(int)m - 1] == 0.0))) {
          //  Stop if we find an unassigned column.
          //  Advance one step in list.
          lm = (int)m;
          m = -b_A->data[((int)C->data[(int)r - 1] + b_A->size[0] * ((int)m - 1))
            - 1];
        }

        if (m == 0.0) {
          //  We failed on row r. Continue with next zero on row i.
          l = r;
          r = -b_A->data[idx + b_A->size[0] * ((int)r - 1)];
        } else {
          //  We found a zero in an unassigned column.
          //  Replace zero at (r,m) in unassigned list with zero at (r,j)
          b_A->data[((int)C->data[(int)r - 1] + b_A->size[0] * (lm - 1)) - 1] =
            -r;
          b_A->data[((int)C->data[(int)r - 1] + b_A->size[0] * ((int)r - 1)) - 1]
            = b_A->data[((int)C->data[(int)r - 1] + b_A->size[0] * ((int)m - 1))
            - 1];

          //  Update last/next pointers in row r.
          NZ->data[(int)C->data[(int)r - 1] - 1] = -b_A->data[((int)C->data[(int)
            r - 1] + b_A->size[0] * ((int)m - 1)) - 1];
          LZ->data[(int)C->data[(int)r - 1] - 1] = r;

          //  Mark A(r,m) as an assigned zero in the matrix . . .
          b_A->data[((int)C->data[(int)r - 1] + b_A->size[0] * ((int)m - 1)) - 1]
            = 0.0;

          //  ...and in the assignment vector.
          C->data[(int)m - 1] = C->data[(int)r - 1];

          //  Remove A(i,j) from unassigned list.
          b_A->data[idx + b_A->size[0] * ((int)l - 1)] = b_A->data[idx +
            b_A->size[0] * ((int)r - 1)];

          //  Update last/next pointers in row r.
          NZ->data[idx] = -b_A->data[idx + b_A->size[0] * ((int)r - 1)];
          LZ->data[idx] = l;

          //  Mark A(r,m) as an assigned zero in the matrix . . .
          b_A->data[idx + b_A->size[0] * ((int)r - 1)] = 0.0;

          //  ...and in the assignment vector.
          C->data[(int)r - 1] = 1.0 + (double)idx;

          //  Stop search.
          exitg5 = true;
        }
      }
    }
  }

  //  Create vector with list of unassigned rows.
  //  Mark all rows have assignment.
  i0 = LZ->size[0] * LZ->size[1];
  LZ->size[0] = 1;
  emxEnsureCapacity((emxArray__common *)LZ, i0, (int)sizeof(double));
  lm = A->size[0];
  i0 = LZ->size[0] * LZ->size[1];
  LZ->size[1] = lm;
  emxEnsureCapacity((emxArray__common *)LZ, i0, (int)sizeof(double));
  idx = A->size[0];
  for (i0 = 0; i0 < idx; i0++) {
    LZ->data[i0] = 0.0;
  }

  b_emxInit_boolean_T(&b_C, 2);
  i0 = b_C->size[0] * b_C->size[1];
  b_C->size[0] = 1;
  b_C->size[1] = C->size[1];
  emxEnsureCapacity((emxArray__common *)b_C, i0, (int)sizeof(boolean_T));
  idx = C->size[0] * C->size[1];
  for (i0 = 0; i0 < idx; i0++) {
    b_C->data[i0] = (C->data[i0] != 0.0);
  }

  b_emxInit_int32_T(&ii, 2);
  eml_li_find(b_C, ii);
  i0 = NZ->size[0] * NZ->size[1];
  NZ->size[0] = 1;
  NZ->size[1] = ii->size[1];
  emxEnsureCapacity((emxArray__common *)NZ, i0, (int)sizeof(double));
  idx = ii->size[0] * ii->size[1];
  emxFree_boolean_T(&b_C);
  for (i0 = 0; i0 < idx; i0++) {
    NZ->data[i0] = C->data[ii->data[i0] - 1];
  }

  idx = NZ->size[0] * NZ->size[1];
  for (i0 = 0; i0 < idx; i0++) {
    LZ->data[(int)NZ->data[i0] - 1] = NZ->data[i0];
  }

  b_emxInit_boolean_T(&x, 2);
  i0 = x->size[0] * x->size[1];
  x->size[0] = 1;
  x->size[1] = LZ->size[1];
  emxEnsureCapacity((emxArray__common *)x, i0, (int)sizeof(boolean_T));
  idx = LZ->size[0] * LZ->size[1];
  for (i0 = 0; i0 < idx; i0++) {
    x->data[i0] = (LZ->data[i0] == 0.0);
  }

  idx = 0;
  i0 = ii->size[0] * ii->size[1];
  ii->size[0] = 1;
  ii->size[1] = x->size[1];
  emxEnsureCapacity((emxArray__common *)ii, i0, (int)sizeof(int));
  lm = 1;
  exitg4 = false;
  while ((!exitg4) && (lm <= x->size[1])) {
    guard2 = false;
    if (x->data[lm - 1]) {
      idx++;
      ii->data[idx - 1] = lm;
      if (idx >= x->size[1]) {
        exitg4 = true;
      } else {
        guard2 = true;
      }
    } else {
      guard2 = true;
    }

    if (guard2) {
      lm++;
    }
  }

  if (x->size[1] == 1) {
    if (idx == 0) {
      i0 = ii->size[0] * ii->size[1];
      ii->size[0] = 1;
      ii->size[1] = 0;
      emxEnsureCapacity((emxArray__common *)ii, i0, (int)sizeof(int));
    }
  } else {
    if (1 > idx) {
      idx = 0;
    }

    b_emxInit_int32_T(&b_ii, 2);
    i0 = b_ii->size[0] * b_ii->size[1];
    b_ii->size[0] = 1;
    b_ii->size[1] = idx;
    emxEnsureCapacity((emxArray__common *)b_ii, i0, (int)sizeof(int));
    for (i0 = 0; i0 < idx; i0++) {
      b_ii->data[b_ii->size[0] * i0] = ii->data[i0];
    }

    i0 = ii->size[0] * ii->size[1];
    ii->size[0] = 1;
    ii->size[1] = b_ii->size[1];
    emxEnsureCapacity((emxArray__common *)ii, i0, (int)sizeof(int));
    idx = b_ii->size[1];
    for (i0 = 0; i0 < idx; i0++) {
      ii->data[ii->size[0] * i0] = b_ii->data[b_ii->size[0] * i0];
    }

    emxFree_int32_T(&b_ii);
  }

  i0 = LZ->size[0] * LZ->size[1];
  LZ->size[0] = 1;
  LZ->size[1] = ii->size[1];
  emxEnsureCapacity((emxArray__common *)LZ, i0, (int)sizeof(double));
  idx = ii->size[0] * ii->size[1];
  for (i0 = 0; i0 < idx; i0++) {
    LZ->data[i0] = ii->data[i0];
  }

  b_emxInit_real_T(&U, 2);

  //  Create vector with linked list of unassigned rows.
  i0 = U->size[0] * U->size[1];
  U->size[0] = 1;
  emxEnsureCapacity((emxArray__common *)U, i0, (int)sizeof(double));
  lm = A->size[0] + 1;
  i0 = U->size[0] * U->size[1];
  U->size[1] = lm;
  emxEnsureCapacity((emxArray__common *)U, i0, (int)sizeof(double));
  idx = A->size[0] + 1;
  for (i0 = 0; i0 < idx; i0++) {
    U->data[i0] = 0.0;
  }

  i0 = ii->size[0] * ii->size[1];
  ii->size[0] = 1;
  ii->size[1] = 1 + LZ->size[1];
  emxEnsureCapacity((emxArray__common *)ii, i0, (int)sizeof(int));
  ii->data[0] = A->size[0] + 1;
  idx = LZ->size[1];
  for (i0 = 0; i0 < idx; i0++) {
    ii->data[ii->size[0] * (i0 + 1)] = (int)LZ->data[LZ->size[0] * i0];
  }

  idx = LZ->size[1];
  for (i0 = 0; i0 < idx; i0++) {
    U->data[ii->data[ii->size[0] * i0] - 1] = LZ->data[LZ->size[0] * i0];
  }

  U->data[ii->data[ii->size[0] * LZ->size[1]] - 1] = 0.0;
  i0 = A->size[0] * A->size[1];
  A->size[0] = b_A->size[0];
  A->size[1] = b_A->size[1];
  emxEnsureCapacity((emxArray__common *)A, i0, (int)sizeof(double));
  idx = b_A->size[0] * b_A->size[1];
  for (i0 = 0; i0 < idx; i0++) {
    A->data[i0] = b_A->data[i0];
  }

  //  Repeat while we have unassigned rows.
  b_emxInit_real_T(&CH, 2);
  b_emxInit_real_T(&RH, 2);
  b_emxInit_real_T(&SLR, 2);
  emxInit_int32_T(&r0, 1);
  b_emxInit_int32_T(&c_ii, 2);
  while (U->data[n] != 0.0) {
    //  Start with no path, no unchecked zeros, and no unexplored rows.
    i0 = LZ->size[0] * LZ->size[1];
    LZ->size[0] = 1;
    LZ->size[1] = n;
    emxEnsureCapacity((emxArray__common *)LZ, i0, (int)sizeof(double));
    for (i0 = 0; i0 < n; i0++) {
      LZ->data[i0] = 0.0;
    }

    i0 = NZ->size[0] * NZ->size[1];
    NZ->size[0] = 1;
    NZ->size[1] = n;
    emxEnsureCapacity((emxArray__common *)NZ, i0, (int)sizeof(double));
    for (i0 = 0; i0 < n; i0++) {
      NZ->data[i0] = 0.0;
    }

    i0 = CH->size[0] * CH->size[1];
    CH->size[0] = 1;
    CH->size[1] = n;
    emxEnsureCapacity((emxArray__common *)CH, i0, (int)sizeof(double));
    for (i0 = 0; i0 < n; i0++) {
      CH->data[i0] = 0.0;
    }

    i0 = RH->size[0] * RH->size[1];
    RH->size[0] = 1;
    RH->size[1] = n + 1;
    emxEnsureCapacity((emxArray__common *)RH, i0, (int)sizeof(double));
    for (i0 = 0; i0 < n; i0++) {
      RH->data[RH->size[0] * i0] = 0.0;
    }

    RH->data[RH->size[0] * n] = -1.0;

    //  No labelled columns.
    //  Start path in first unassigned row.
    r = U->data[n];

    //  Mark row with end-of-path label.
    LZ->data[(int)U->data[n] - 1] = -1.0;

    //  Insert row first in labelled row set.
    i0 = SLR->size[0] * SLR->size[1];
    SLR->size[0] = 1;
    SLR->size[1] = 1;
    emxEnsureCapacity((emxArray__common *)SLR, i0, (int)sizeof(double));
    SLR->data[0] = U->data[n];

    //  Repeat until we manage to find an assignable zero.
    do {
      exitg3 = 0;

      //  If there are free zeros in row r
      if (A->data[((int)r + A->size[0] * n) - 1] != 0.0) {
        //  ...get column of first free zero.
        l = -A->data[((int)r + A->size[0] * n) - 1];

        //  If there are more free zeros in row r and row r in not
        //  yet marked as unexplored..
        if ((A->data[((int)r + A->size[0] * ((int)-A->data[((int)r + A->size[0] *
                n) - 1] - 1)) - 1] != 0.0) && (RH->data[(int)r - 1] == 0.0)) {
          //  Insert row r first in unexplored list.
          RH->data[(int)r - 1] = RH->data[n];
          RH->data[n] = r;

          //  Mark in which column the next unexplored zero in this row
          //  is.
          CH->data[(int)r - 1] = -A->data[((int)r + A->size[0] * ((int)-A->data
            [((int)r + A->size[0] * n) - 1] - 1)) - 1];
        }
      } else {
        //  If all rows are explored..
        if (RH->data[n] <= 0.0) {
          //  Reduce matrix.
          hmreduce(A, CH, RH, NZ, LZ, SLR);
        }

        //  Re-start with first unexplored row.
        r = RH->data[n];

        //  Get column of next free zero in row r.
        l = CH->data[(int)RH->data[n] - 1];

        //  Advance "column of next free zero".
        CH->data[(int)RH->data[n] - 1] = -A->data[((int)RH->data[n] + A->size[0]
          * ((int)CH->data[(int)RH->data[n] - 1] - 1)) - 1];

        //  If this zero is last in the list..
        if (A->data[((int)RH->data[n] + A->size[0] * ((int)l - 1)) - 1] == 0.0)
        {
          //  ...remove row r from unexplored list.
          RH->data[n] = RH->data[(int)RH->data[n] - 1];
          RH->data[(int)r - 1] = 0.0;
        }
      }

      //  While the column l is labelled, i.e. in path.
      while (NZ->data[(int)l - 1] != 0.0) {
        //  If row r is explored..
        if (RH->data[(int)r - 1] == 0.0) {
          //  If all rows are explored..
          if (RH->data[n] <= 0.0) {
            //  Reduce cost matrix.
            hmreduce(A, CH, RH, NZ, LZ, SLR);
          }

          //  Re-start with first unexplored row.
          r = RH->data[n];
        }

        //  Get column of next free zero in row r.
        l = CH->data[(int)r - 1];

        //  Advance "column of next free zero".
        CH->data[(int)r - 1] = -A->data[((int)r + A->size[0] * ((int)CH->data
          [(int)r - 1] - 1)) - 1];

        //  If this zero is last in list..
        if (A->data[((int)r + A->size[0] * ((int)l - 1)) - 1] == 0.0) {
          //  ...remove row r from unexplored list.
          RH->data[n] = RH->data[(int)r - 1];
          RH->data[(int)r - 1] = 0.0;
        }
      }

      //  If the column found is unassigned..
      if (C->data[(int)l - 1] == 0.0) {
        exitg3 = 1;
      } else {
        //  ...else add zero to path.
        //  Label column l with row r.
        NZ->data[(int)l - 1] = r;

        //  Add l to the set of labelled columns.
        //  Continue with the row assigned to column l.
        r = C->data[(int)l - 1];

        //  Label row r with column l.
        LZ->data[(int)C->data[(int)l - 1] - 1] = l;

        //  Add r to the set of labelled rows.
        idx = SLR->size[1];
        i0 = SLR->size[0] * SLR->size[1];
        SLR->size[1] = idx + 1;
        emxEnsureCapacity((emxArray__common *)SLR, i0, (int)sizeof(double));
        SLR->data[idx] = C->data[(int)l - 1];
      }
    } while (exitg3 == 0);

    //  Flip all zeros along the path in LR,LC.
    b_l = (int)l - 1;

    // HMFLIP Flip assignment state of all zeros along a path.
    //
    // [A,C,U]=hmflip(A,C,LC,LR,U,l,r)
    // Input:
    // A   - the cost matrix.
    // C   - the assignment vector.
    // LC  - the column label vector.
    // LR  - the row label vector.
    // U   - the
    // r,l - position of last zero in path.
    // Output:
    // A   - updated cost matrix.
    // C   - updated assignment vector.
    // U   - updated unassigned row list vector.
    i0 = b_A->size[0] * b_A->size[1];
    b_A->size[0] = A->size[0];
    b_A->size[1] = A->size[1];
    emxEnsureCapacity((emxArray__common *)b_A, i0, (int)sizeof(double));
    idx = A->size[0] * A->size[1];
    for (i0 = 0; i0 < idx; i0++) {
      b_A->data[i0] = A->data[i0];
    }

    //  v1.0  96-06-14. Niclas Borlin, niclas@cs.umu.se.
    do {
      exitg1 = 0;

      //  Move assignment in column l to row r.
      C->data[b_l] = r;

      //  Find zero to be removed from zero list..
      //  Find zero before this.
      idx = b_A->size[1];
      i0 = x->size[0] * x->size[1];
      x->size[0] = 1;
      x->size[1] = idx;
      emxEnsureCapacity((emxArray__common *)x, i0, (int)sizeof(boolean_T));
      for (i0 = 0; i0 < idx; i0++) {
        x->data[x->size[0] * i0] = (b_A->data[((int)r + b_A->size[0] * i0) - 1] ==
          -(double)(b_l + 1));
      }

      idx = 0;
      i0 = ii->size[0] * ii->size[1];
      ii->size[0] = 1;
      ii->size[1] = x->size[1];
      emxEnsureCapacity((emxArray__common *)ii, i0, (int)sizeof(int));
      lm = 1;
      exitg2 = false;
      while ((!exitg2) && (lm <= x->size[1])) {
        guard1 = false;
        if (x->data[lm - 1]) {
          idx++;
          ii->data[idx - 1] = lm;
          if (idx >= x->size[1]) {
            exitg2 = true;
          } else {
            guard1 = true;
          }
        } else {
          guard1 = true;
        }

        if (guard1) {
          lm++;
        }
      }

      if (x->size[1] == 1) {
        if (idx == 0) {
          i0 = ii->size[0] * ii->size[1];
          ii->size[0] = 1;
          ii->size[1] = 0;
          emxEnsureCapacity((emxArray__common *)ii, i0, (int)sizeof(int));
        }
      } else {
        if (1 > idx) {
          idx = 0;
        }

        i0 = c_ii->size[0] * c_ii->size[1];
        c_ii->size[0] = 1;
        c_ii->size[1] = idx;
        emxEnsureCapacity((emxArray__common *)c_ii, i0, (int)sizeof(int));
        for (i0 = 0; i0 < idx; i0++) {
          c_ii->data[c_ii->size[0] * i0] = ii->data[i0];
        }

        i0 = ii->size[0] * ii->size[1];
        ii->size[0] = 1;
        ii->size[1] = c_ii->size[1];
        emxEnsureCapacity((emxArray__common *)ii, i0, (int)sizeof(int));
        idx = c_ii->size[1];
        for (i0 = 0; i0 < idx; i0++) {
          ii->data[ii->size[0] * i0] = c_ii->data[c_ii->size[0] * i0];
        }
      }

      //  Link past this zero.
      i0 = r0->size[0];
      r0->size[0] = ii->size[1];
      emxEnsureCapacity((emxArray__common *)r0, i0, (int)sizeof(int));
      idx = ii->size[1];
      for (i0 = 0; i0 < idx; i0++) {
        r0->data[i0] = ii->data[ii->size[0] * i0];
      }

      l = b_A->data[((int)r + b_A->size[0] * b_l) - 1];
      lm = r0->size[0];
      for (i0 = 0; i0 < lm; i0++) {
        b_A->data[((int)r + b_A->size[0] * (r0->data[i0] - 1)) - 1] = l;
      }

      b_A->data[((int)r + b_A->size[0] * b_l) - 1] = 0.0;

      //  If this was the first zero of the path..
      if (LZ->data[(int)r - 1] < 0.0) {
        exitg1 = 1;
      } else {
        //  Move back in this row along the path and get column of next zero.
        b_l = (int)LZ->data[(int)r - 1] - 1;

        //  Insert zero at (r,l) first in zero list.
        b_A->data[((int)r + b_A->size[0] * ((int)LZ->data[(int)r - 1] - 1)) - 1]
          = b_A->data[((int)r + b_A->size[0] * A->size[0]) - 1];
        b_A->data[((int)r + b_A->size[0] * A->size[0]) - 1] = -LZ->data[(int)r -
          1];

        //  Continue back along the column to get row of next zero in path.
        r = NZ->data[(int)LZ->data[(int)r - 1] - 1];
      }
    } while (exitg1 == 0);

    // remove row from unassigned row list and return.
    U->data[A->size[0]] = U->data[(int)r - 1];
    U->data[(int)r - 1] = 0.0;
    i0 = A->size[0] * A->size[1];
    A->size[0] = b_A->size[0];
    A->size[1] = b_A->size[1];
    emxEnsureCapacity((emxArray__common *)A, i0, (int)sizeof(double));
    idx = b_A->size[0] * b_A->size[1];
    for (i0 = 0; i0 < idx; i0++) {
      A->data[i0] = b_A->data[i0];
    }

    //  ...and exit to continue with next unassigned row.
  }

  emxFree_int32_T(&c_ii);
  emxFree_int32_T(&r0);
  emxFree_int32_T(&ii);
  emxFree_boolean_T(&x);
  emxFree_real_T(&NZ);
  emxFree_real_T(&LZ);
  emxFree_real_T(&b_A);
  emxFree_real_T(&SLR);
  emxFree_real_T(&RH);
  emxFree_real_T(&CH);
  emxFree_real_T(&U);
}

//
// File trailer for hungarian.cpp
//
// [EOF]
//
