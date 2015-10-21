//
// File: bestMap.cpp
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
// Function Definitions

//
// bestmap: permute labels of L2 to match L1 as good as possible
//    [newL2] = bestMap(L1,L2);
//
//    version 2.0 --May/2007
//    version 1.0 --November/2003
//
//    Written by Deng Cai (dengcai AT gmail.com)
// Arguments    : const emxArray_real_T *L1
//                const emxArray_real_T *L2
//                emxArray_real_T *C
// Return Type  : void
//
void bestMap(const emxArray_real_T *L1, const emxArray_real_T *L2,
             emxArray_real_T *C)
{
  emxArray_int32_T *idx;
  int kEnd;
  int pEnd;
  emxArray_int32_T *idx0;
  int k;
  int32_T exitg17;
  int32_T exitg16;
  int32_T exitg15;
  boolean_T p;
  int i;
  int32_T exitg13;
  int i2;
  int j;
  int32_T exitg14;
  int b_p;
  int q;
  int qEnd;
  emxArray_real_T *Label1;
  int32_T exitg12;
  int32_T exitg11;
  double x;
  int32_T exitg10;
  double absxk;
  double Label2;
  int exponent;
  emxArray_real_T *b_Label1;
  int32_T exitg9;
  int32_T exitg8;
  int32_T exitg7;
  int32_T exitg5;
  int32_T exitg6;
  emxArray_real_T *b_Label2;
  int32_T exitg4;
  int32_T exitg3;
  int32_T exitg2;
  int b_exponent;
  emxArray_real_T *c_Label2;
  emxArray_real_T *G;
  emxArray_boolean_T *b_x;
  emxArray_int32_T *pos;
  emxArray_int32_T *b_pos;
  boolean_T exitg1;
  boolean_T guard1 = false;
  emxArray_real_T *b_G;
  emxInit_int32_T(&idx, 1);

  // ===========
  kEnd = L1->size[1];
  pEnd = idx->size[0];
  idx->size[0] = kEnd;
  emxEnsureCapacity((emxArray__common *)idx, pEnd, (int)sizeof(int));
  kEnd = L1->size[1];
  emxInit_int32_T(&idx0, 1);
  if (kEnd == 0) {
    k = 1;
    do {
      exitg17 = 0;
      kEnd = L1->size[1];
      if (k <= kEnd) {
        idx->data[k - 1] = k;
        k++;
      } else {
        exitg17 = 1;
      }
    } while (exitg17 == 0);
  } else {
    k = 1;
    do {
      exitg16 = 0;
      kEnd = L1->size[1];
      if (k <= kEnd) {
        idx->data[k - 1] = k;
        k++;
      } else {
        exitg16 = 1;
      }
    } while (exitg16 == 0);

    k = 1;
    do {
      exitg15 = 0;
      kEnd = L1->size[1];
      if (k <= kEnd - 1) {
        if ((L1->data[k - 1] <= L1->data[k]) || rtIsNaN(L1->data[k])) {
          p = true;
        } else {
          p = false;
        }

        if (p) {
        } else {
          idx->data[k - 1] = k + 1;
          idx->data[k] = k;
        }

        k += 2;
      } else {
        exitg15 = 1;
      }
    } while (exitg15 == 0);

    kEnd = L1->size[1];
    pEnd = idx0->size[0];
    idx0->size[0] = kEnd;
    emxEnsureCapacity((emxArray__common *)idx0, pEnd, (int)sizeof(int));
    for (pEnd = 0; pEnd < kEnd; pEnd++) {
      idx0->data[pEnd] = 1;
    }

    i = 2;
    do {
      exitg13 = 0;
      kEnd = L1->size[1];
      if (i < kEnd) {
        i2 = i << 1;
        j = 1;
        pEnd = 1 + i;
        do {
          exitg14 = 0;
          kEnd = L1->size[1];
          if (pEnd < kEnd + 1) {
            b_p = j;
            q = pEnd - 1;
            qEnd = j + i2;
            kEnd = L1->size[1];
            if (qEnd > kEnd + 1) {
              kEnd = L1->size[1];
              qEnd = kEnd + 1;
            }

            k = 0;
            kEnd = qEnd - j;
            while (k + 1 <= kEnd) {
              if ((L1->data[idx->data[b_p - 1] - 1] <= L1->data[idx->data[q] - 1])
                  || rtIsNaN(L1->data[idx->data[q] - 1])) {
                p = true;
              } else {
                p = false;
              }

              if (p) {
                idx0->data[k] = idx->data[b_p - 1];
                b_p++;
                if (b_p == pEnd) {
                  while (q + 1 < qEnd) {
                    k++;
                    idx0->data[k] = idx->data[q];
                    q++;
                  }
                }
              } else {
                idx0->data[k] = idx->data[q];
                q++;
                if (q + 1 == qEnd) {
                  while (b_p < pEnd) {
                    k++;
                    idx0->data[k] = idx->data[b_p - 1];
                    b_p++;
                  }
                }
              }

              k++;
            }

            for (k = 0; k + 1 <= kEnd; k++) {
              idx->data[(j + k) - 1] = idx0->data[k];
            }

            j = qEnd;
            pEnd = qEnd + i;
          } else {
            exitg14 = 1;
          }
        } while (exitg14 == 0);

        i = i2;
      } else {
        exitg13 = 1;
      }
    } while (exitg13 == 0);
  }

  emxInit_real_T(&Label1, 1);
  kEnd = L1->size[1];
  pEnd = Label1->size[0];
  Label1->size[0] = kEnd;
  emxEnsureCapacity((emxArray__common *)Label1, pEnd, (int)sizeof(double));
  k = 0;
  do {
    exitg12 = 0;
    kEnd = L1->size[1];
    if (k + 1 <= kEnd) {
      Label1->data[k] = L1->data[idx->data[k] - 1];
      k++;
    } else {
      exitg12 = 1;
    }
  } while (exitg12 == 0);

  k = 0;
  do {
    exitg11 = 0;
    kEnd = L1->size[1];
    if ((k + 1 <= kEnd) && rtIsInf(Label1->data[k]) && (Label1->data[k] < 0.0))
    {
      k++;
    } else {
      exitg11 = 1;
    }
  } while (exitg11 == 0);

  q = k;
  k = L1->size[1];
  while ((k >= 1) && rtIsNaN(Label1->data[k - 1])) {
    k--;
  }

  kEnd = L1->size[1];
  b_p = kEnd - k;
  while ((k >= 1) && rtIsInf(Label1->data[k - 1]) && (Label1->data[k - 1] > 0.0))
  {
    k--;
  }

  kEnd = L1->size[1];
  kEnd = (kEnd - k) - b_p;
  pEnd = -1;
  if (q > 0) {
    pEnd = 0;
  }

  i2 = (q + k) - q;
  while (q + 1 <= i2) {
    x = Label1->data[q];
    do {
      exitg10 = 0;
      q++;
      if (q + 1 > i2) {
        exitg10 = 1;
      } else {
        absxk = fabs(x / 2.0);
        if ((!rtIsInf(absxk)) && (!rtIsNaN(absxk))) {
          if (absxk <= 2.2250738585072014E-308) {
            Label2 = 4.94065645841247E-324;
          } else {
            frexp(absxk, &exponent);
            Label2 = ldexp(1.0, exponent - 53);
          }
        } else {
          Label2 = rtNaN;
        }

        if ((fabs(x - Label1->data[q]) < Label2) || (rtIsInf(Label1->data[q]) &&
             rtIsInf(x) && ((Label1->data[q] > 0.0) == (x > 0.0)))) {
          p = true;
        } else {
          p = false;
        }

        if (!p) {
          exitg10 = 1;
        }
      }
    } while (exitg10 == 0);

    pEnd++;
    Label1->data[pEnd] = x;
  }

  if (kEnd > 0) {
    pEnd++;
    Label1->data[pEnd] = Label1->data[i2];
  }

  q = i2 + kEnd;
  for (j = 1; j <= b_p; j++) {
    pEnd++;
    Label1->data[pEnd] = Label1->data[(q + j) - 1];
  }

  if (1 > pEnd + 1) {
    i2 = -1;
  } else {
    i2 = pEnd;
  }

  emxInit_real_T(&b_Label1, 1);
  pEnd = b_Label1->size[0];
  b_Label1->size[0] = i2 + 1;
  emxEnsureCapacity((emxArray__common *)b_Label1, pEnd, (int)sizeof(double));
  for (pEnd = 0; pEnd <= i2; pEnd++) {
    b_Label1->data[pEnd] = Label1->data[pEnd];
  }

  pEnd = Label1->size[0];
  Label1->size[0] = b_Label1->size[0];
  emxEnsureCapacity((emxArray__common *)Label1, pEnd, (int)sizeof(double));
  i2 = b_Label1->size[0];
  for (pEnd = 0; pEnd < i2; pEnd++) {
    Label1->data[pEnd] = b_Label1->data[pEnd];
  }

  emxFree_real_T(&b_Label1);
  kEnd = L2->size[1];
  pEnd = idx->size[0];
  idx->size[0] = kEnd;
  emxEnsureCapacity((emxArray__common *)idx, pEnd, (int)sizeof(int));
  kEnd = L2->size[1];
  if (kEnd == 0) {
    k = 1;
    do {
      exitg9 = 0;
      kEnd = L2->size[1];
      if (k <= kEnd) {
        idx->data[k - 1] = k;
        k++;
      } else {
        exitg9 = 1;
      }
    } while (exitg9 == 0);
  } else {
    k = 1;
    do {
      exitg8 = 0;
      kEnd = L2->size[1];
      if (k <= kEnd) {
        idx->data[k - 1] = k;
        k++;
      } else {
        exitg8 = 1;
      }
    } while (exitg8 == 0);

    k = 1;
    do {
      exitg7 = 0;
      kEnd = L2->size[1];
      if (k <= kEnd - 1) {
        if ((L2->data[k - 1] <= L2->data[k]) || rtIsNaN(L2->data[k])) {
          p = true;
        } else {
          p = false;
        }

        if (p) {
        } else {
          idx->data[k - 1] = k + 1;
          idx->data[k] = k;
        }

        k += 2;
      } else {
        exitg7 = 1;
      }
    } while (exitg7 == 0);

    kEnd = L2->size[1];
    pEnd = idx0->size[0];
    idx0->size[0] = kEnd;
    emxEnsureCapacity((emxArray__common *)idx0, pEnd, (int)sizeof(int));
    for (pEnd = 0; pEnd < kEnd; pEnd++) {
      idx0->data[pEnd] = 1;
    }

    i = 2;
    do {
      exitg5 = 0;
      kEnd = L2->size[1];
      if (i < kEnd) {
        i2 = i << 1;
        j = 1;
        pEnd = 1 + i;
        do {
          exitg6 = 0;
          kEnd = L2->size[1];
          if (pEnd < kEnd + 1) {
            b_p = j;
            q = pEnd - 1;
            qEnd = j + i2;
            kEnd = L2->size[1];
            if (qEnd > kEnd + 1) {
              kEnd = L2->size[1];
              qEnd = kEnd + 1;
            }

            k = 0;
            kEnd = qEnd - j;
            while (k + 1 <= kEnd) {
              if ((L2->data[idx->data[b_p - 1] - 1] <= L2->data[idx->data[q] - 1])
                  || rtIsNaN(L2->data[idx->data[q] - 1])) {
                p = true;
              } else {
                p = false;
              }

              if (p) {
                idx0->data[k] = idx->data[b_p - 1];
                b_p++;
                if (b_p == pEnd) {
                  while (q + 1 < qEnd) {
                    k++;
                    idx0->data[k] = idx->data[q];
                    q++;
                  }
                }
              } else {
                idx0->data[k] = idx->data[q];
                q++;
                if (q + 1 == qEnd) {
                  while (b_p < pEnd) {
                    k++;
                    idx0->data[k] = idx->data[b_p - 1];
                    b_p++;
                  }
                }
              }

              k++;
            }

            for (k = 0; k + 1 <= kEnd; k++) {
              idx->data[(j + k) - 1] = idx0->data[k];
            }

            j = qEnd;
            pEnd = qEnd + i;
          } else {
            exitg6 = 1;
          }
        } while (exitg6 == 0);

        i = i2;
      } else {
        exitg5 = 1;
      }
    } while (exitg5 == 0);
  }

  emxFree_int32_T(&idx0);
  emxInit_real_T(&b_Label2, 1);
  kEnd = L2->size[1];
  pEnd = b_Label2->size[0];
  b_Label2->size[0] = kEnd;
  emxEnsureCapacity((emxArray__common *)b_Label2, pEnd, (int)sizeof(double));
  k = 0;
  do {
    exitg4 = 0;
    kEnd = L2->size[1];
    if (k + 1 <= kEnd) {
      b_Label2->data[k] = L2->data[idx->data[k] - 1];
      k++;
    } else {
      exitg4 = 1;
    }
  } while (exitg4 == 0);

  emxFree_int32_T(&idx);
  k = 0;
  do {
    exitg3 = 0;
    kEnd = L2->size[1];
    if ((k + 1 <= kEnd) && rtIsInf(b_Label2->data[k]) && (b_Label2->data[k] <
         0.0)) {
      k++;
    } else {
      exitg3 = 1;
    }
  } while (exitg3 == 0);

  q = k;
  k = L2->size[1];
  while ((k >= 1) && rtIsNaN(b_Label2->data[k - 1])) {
    k--;
  }

  kEnd = L2->size[1];
  b_p = kEnd - k;
  while ((k >= 1) && rtIsInf(b_Label2->data[k - 1]) && (b_Label2->data[k - 1] >
          0.0)) {
    k--;
  }

  kEnd = L2->size[1];
  kEnd = (kEnd - k) - b_p;
  pEnd = -1;
  if (q > 0) {
    pEnd = 0;
  }

  i2 = (q + k) - q;
  while (q + 1 <= i2) {
    x = b_Label2->data[q];
    do {
      exitg2 = 0;
      q++;
      if (q + 1 > i2) {
        exitg2 = 1;
      } else {
        absxk = fabs(x / 2.0);
        if ((!rtIsInf(absxk)) && (!rtIsNaN(absxk))) {
          if (absxk <= 2.2250738585072014E-308) {
            Label2 = 4.94065645841247E-324;
          } else {
            frexp(absxk, &b_exponent);
            Label2 = ldexp(1.0, b_exponent - 53);
          }
        } else {
          Label2 = rtNaN;
        }

        if ((fabs(x - b_Label2->data[q]) < Label2) || (rtIsInf(b_Label2->data[q])
             && rtIsInf(x) && ((b_Label2->data[q] > 0.0) == (x > 0.0)))) {
          p = true;
        } else {
          p = false;
        }

        if (!p) {
          exitg2 = 1;
        }
      }
    } while (exitg2 == 0);

    pEnd++;
    b_Label2->data[pEnd] = x;
  }

  if (kEnd > 0) {
    pEnd++;
    b_Label2->data[pEnd] = b_Label2->data[i2];
  }

  q = i2 + kEnd;
  for (j = 1; j <= b_p; j++) {
    pEnd++;
    b_Label2->data[pEnd] = b_Label2->data[(q + j) - 1];
  }

  if (1 > pEnd + 1) {
    i2 = -1;
  } else {
    i2 = pEnd;
  }

  emxInit_real_T(&c_Label2, 1);
  pEnd = c_Label2->size[0];
  c_Label2->size[0] = i2 + 1;
  emxEnsureCapacity((emxArray__common *)c_Label2, pEnd, (int)sizeof(double));
  for (pEnd = 0; pEnd <= i2; pEnd++) {
    c_Label2->data[pEnd] = b_Label2->data[pEnd];
  }

  pEnd = b_Label2->size[0];
  b_Label2->size[0] = c_Label2->size[0];
  emxEnsureCapacity((emxArray__common *)b_Label2, pEnd, (int)sizeof(double));
  i2 = c_Label2->size[0];
  for (pEnd = 0; pEnd < i2; pEnd++) {
    b_Label2->data[pEnd] = c_Label2->data[pEnd];
  }

  emxFree_real_T(&c_Label2);
  if (Label1->size[0] >= b_Label2->size[0]) {
    i2 = Label1->size[0];
  } else {
    i2 = b_Label2->size[0];
  }

  b_emxInit_real_T(&G, 2);
  pEnd = G->size[0] * G->size[1];
  G->size[0] = i2;
  G->size[1] = i2;
  emxEnsureCapacity((emxArray__common *)G, pEnd, (int)sizeof(double));
  i2 *= i2;
  for (pEnd = 0; pEnd < i2; pEnd++) {
    G->data[pEnd] = 0.0;
  }

  i = 0;
  emxInit_boolean_T(&b_x, 1);
  emxInit_int32_T(&pos, 1);
  emxInit_int32_T(&b_pos, 1);
  while (i <= Label1->size[0] - 1) {
    for (j = 0; j < b_Label2->size[0]; j++) {
      absxk = Label1->data[i];
      Label2 = b_Label2->data[j];
      pEnd = b_x->size[0];
      b_x->size[0] = L1->size[1];
      emxEnsureCapacity((emxArray__common *)b_x, pEnd, (int)sizeof(boolean_T));
      i2 = L1->size[1];
      for (pEnd = 0; pEnd < i2; pEnd++) {
        b_x->data[pEnd] = ((L1->data[pEnd] == absxk) && (L2->data[pEnd] ==
          Label2));
      }

      kEnd = 0;
      pEnd = pos->size[0];
      pos->size[0] = b_x->size[0];
      emxEnsureCapacity((emxArray__common *)pos, pEnd, (int)sizeof(int));
      i2 = 1;
      exitg1 = false;
      while ((!exitg1) && (i2 <= b_x->size[0])) {
        guard1 = false;
        if (b_x->data[i2 - 1]) {
          kEnd++;
          pos->data[kEnd - 1] = i2;
          if (kEnd >= b_x->size[0]) {
            exitg1 = true;
          } else {
            guard1 = true;
          }
        } else {
          guard1 = true;
        }

        if (guard1) {
          i2++;
        }
      }

      if (b_x->size[0] == 1) {
        if (kEnd == 0) {
          pEnd = pos->size[0];
          pos->size[0] = 0;
          emxEnsureCapacity((emxArray__common *)pos, pEnd, (int)sizeof(int));
        }
      } else {
        if (1 > kEnd) {
          i2 = 0;
        } else {
          i2 = kEnd;
        }

        pEnd = b_pos->size[0];
        b_pos->size[0] = i2;
        emxEnsureCapacity((emxArray__common *)b_pos, pEnd, (int)sizeof(int));
        for (pEnd = 0; pEnd < i2; pEnd++) {
          b_pos->data[pEnd] = pos->data[pEnd];
        }

        pEnd = pos->size[0];
        pos->size[0] = b_pos->size[0];
        emxEnsureCapacity((emxArray__common *)pos, pEnd, (int)sizeof(int));
        i2 = b_pos->size[0];
        for (pEnd = 0; pEnd < i2; pEnd++) {
          pos->data[pEnd] = b_pos->data[pEnd];
        }
      }

      G->data[i + G->size[0] * j] = pos->size[0];
    }

    i++;
  }

  emxFree_int32_T(&b_pos);
  emxFree_int32_T(&pos);
  emxFree_boolean_T(&b_x);
  emxFree_real_T(&b_Label2);
  emxFree_real_T(&Label1);
  b_emxInit_real_T(&b_G, 2);
  pEnd = b_G->size[0] * b_G->size[1];
  b_G->size[0] = G->size[0];
  b_G->size[1] = G->size[1];
  emxEnsureCapacity((emxArray__common *)b_G, pEnd, (int)sizeof(double));
  i2 = G->size[0] * G->size[1];
  for (pEnd = 0; pEnd < i2; pEnd++) {
    b_G->data[pEnd] = -G->data[pEnd];
  }
  
      printf("\n");
    for (int i=0; i< b_G->size[1] ; i++)
      {
          for (int j=0; j< b_G->size[0] ; j++)
          {  
              b_G->data[i*b_G->size[0]+j] = b_G->data[i*b_G->size[0]+j] + 0.0;
              printf("%f ",b_G->data[i*b_G->size[0]+j]);
          }
          printf("\n");
      }
  emxFree_real_T(&G);
  hungarian(b_G, C);
  emxFree_real_T(&b_G);
}

//
// File trailer for bestMap.cpp
//
// [EOF]
//
