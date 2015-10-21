//
// File: bestMap_emxutil.h
//
// MATLAB Coder version            : 2.6
// C/C++ source code generated on  : 20-Sep-2015 17:55:26
//
#ifndef __BESTMAP_EMXUTIL_H__
#define __BESTMAP_EMXUTIL_H__

// Include files
#include <math.h>
#include <stddef.h>
#include <stdlib.h>
#include <string.h>
#include "rt_nonfinite.h"
#include "rtwtypes.h"
#include "bestMap_types.h"

// Function Declarations
extern void b_emxInit_boolean_T(emxArray_boolean_T **pEmxArray, int
  numDimensions);
extern void b_emxInit_int32_T(emxArray_int32_T **pEmxArray, int numDimensions);
extern void b_emxInit_real_T(emxArray_real_T **pEmxArray, int numDimensions);
extern void emxEnsureCapacity(emxArray__common *emxArray, int oldNumel, int
  elementSize);
extern void emxFree_boolean_T(emxArray_boolean_T **pEmxArray);
extern void emxFree_int32_T(emxArray_int32_T **pEmxArray);
extern void emxFree_real_T(emxArray_real_T **pEmxArray);
extern void emxInit_boolean_T(emxArray_boolean_T **pEmxArray, int numDimensions);
extern void emxInit_int32_T(emxArray_int32_T **pEmxArray, int numDimensions);
extern void emxInit_real_T(emxArray_real_T **pEmxArray, int numDimensions);

#endif

//
// File trailer for bestMap_emxutil.h
//
// [EOF]
//
