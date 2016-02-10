#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
GREP=grep
NM=nm
CCADMIN=CCadmin
RANLIB=ranlib
CC=gcc
CCC=g++
CXX=g++
FC=gfortran
AS=as

# Macros
CND_PLATFORM=GNU-Linux
CND_DLIB_EXT=so
CND_CONF=Debug
CND_DISTDIR=dist
CND_BUILDDIR=build

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=${CND_BUILDDIR}/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/_ext/a845ff68/Cluster.o \
	${OBJECTDIR}/_ext/a845ff68/ClusteringMetrics.o \
	${OBJECTDIR}/_ext/a845ff68/ClusteringSOM.o \
	${OBJECTDIR}/_ext/a845ff68/SSCDataFile.o \
	${OBJECTDIR}/_ext/a845ff68/SubspaceClusteringSOM.o \
	${OBJECTDIR}/_ext/a845ff68/randomnumbers.o \
	${OBJECTDIR}/_ext/a342a8fc/ArffData.o \
	${OBJECTDIR}/_ext/c5140741/DebugOut.o \
	${OBJECTDIR}/_ext/d0624b86/Defines.o \
	${OBJECTDIR}/_ext/d0624b86/StringHelper.o \
	${OBJECTDIR}/_ext/8bffeb2f/MatUtils.o \
	${OBJECTDIR}/_ext/36bbb5bc/Parameters.o \
	${OBJECTDIR}/_ext/15c88dff/DSNode.o \
	${OBJECTDIR}/_ext/15c88dff/NodeW.o \
	${OBJECTDIR}/_ext/418f266/bestMap.o \
	${OBJECTDIR}/_ext/418f266/bestMap_emxAPI.o \
	${OBJECTDIR}/_ext/418f266/bestMap_emxutil.o \
	${OBJECTDIR}/_ext/418f266/bestMap_initialize.o \
	${OBJECTDIR}/_ext/418f266/bestMap_terminate.o \
	${OBJECTDIR}/_ext/418f266/hungarian.o \
	${OBJECTDIR}/_ext/418f266/rtGetInf.o \
	${OBJECTDIR}/_ext/418f266/rtGetNaN.o \
	${OBJECTDIR}/_ext/418f266/rt_nonfinite.o \
	${OBJECTDIR}/_ext/dd71a7b4/hungarian.o \
	${OBJECTDIR}/main.o


# C Compiler Flags
CFLAGS=

# CC Compiler Flags
CCFLAGS=-m64 -I/usr/include/opencv2 -I/usr/local/MATLAB/R2014a/extern/include -I/usr/include
CXXFLAGS=-m64 -I/usr/include/opencv2 -I/usr/local/MATLAB/R2014a/extern/include -I/usr/include

# Fortran Compiler Flags
FFLAGS=

# Assembler Flags
ASFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	"${MAKE}"  -f nbproject/Makefile-${CND_CONF}.mk ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/netbeansproject

${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/netbeansproject: ${OBJECTFILES}
	${MKDIR} -p ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}
	${LINK.cc} -o ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/netbeansproject ${OBJECTFILES} ${LDLIBSOPTIONS} -lX11 -lpthread -L/usr/local/MATLAB/R2014a/bin/glnxa64 -lmat -lmx -leng -Wl,-rpath=/usr/local/MATLAB/R2014a/bin/glnxa64 `pkg-config opencv --cflags --libs` -llapack -lblas -larmadillo 

${OBJECTDIR}/_ext/a845ff68/Cluster.o: ../Libs/Cluster/Cluster.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/a845ff68
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/a845ff68/Cluster.o ../Libs/Cluster/Cluster.cpp

${OBJECTDIR}/_ext/a845ff68/ClusteringMetrics.o: ../Libs/Cluster/ClusteringMetrics.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/a845ff68
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/a845ff68/ClusteringMetrics.o ../Libs/Cluster/ClusteringMetrics.cpp

${OBJECTDIR}/_ext/a845ff68/ClusteringSOM.o: ../Libs/Cluster/ClusteringSOM.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/a845ff68
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/a845ff68/ClusteringSOM.o ../Libs/Cluster/ClusteringSOM.cpp

${OBJECTDIR}/_ext/a845ff68/SSCDataFile.o: ../Libs/Cluster/SSCDataFile.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/a845ff68
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/a845ff68/SSCDataFile.o ../Libs/Cluster/SSCDataFile.cpp

${OBJECTDIR}/_ext/a845ff68/SubspaceClusteringSOM.o: ../Libs/Cluster/SubspaceClusteringSOM.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/a845ff68
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/a845ff68/SubspaceClusteringSOM.o ../Libs/Cluster/SubspaceClusteringSOM.cpp

${OBJECTDIR}/_ext/a845ff68/randomnumbers.o: ../Libs/Cluster/randomnumbers.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/a845ff68
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/a845ff68/randomnumbers.o ../Libs/Cluster/randomnumbers.cpp

${OBJECTDIR}/_ext/a342a8fc/ArffData.o: ../Libs/Data/ArffData.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/a342a8fc
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/a342a8fc/ArffData.o ../Libs/Data/ArffData.cpp

${OBJECTDIR}/_ext/c5140741/DebugOut.o: ../Libs/Debug/DebugOut.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/c5140741
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/c5140741/DebugOut.o ../Libs/Debug/DebugOut.cpp

${OBJECTDIR}/_ext/d0624b86/Defines.o: ../Libs/Defines/Defines.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/d0624b86
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/d0624b86/Defines.o ../Libs/Defines/Defines.cpp

${OBJECTDIR}/_ext/d0624b86/StringHelper.o: ../Libs/Defines/StringHelper.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/d0624b86
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/d0624b86/StringHelper.o ../Libs/Defines/StringHelper.cpp

${OBJECTDIR}/_ext/8bffeb2f/MatUtils.o: ../Libs/MatMatrix/MatUtils.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/8bffeb2f
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/8bffeb2f/MatUtils.o ../Libs/MatMatrix/MatUtils.cpp

${OBJECTDIR}/_ext/36bbb5bc/Parameters.o: ../Libs/Parameters/Parameters.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/36bbb5bc
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/36bbb5bc/Parameters.o ../Libs/Parameters/Parameters.cpp

${OBJECTDIR}/_ext/15c88dff/DSNode.o: ../Libs/SOM/DSNode.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/15c88dff
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/15c88dff/DSNode.o ../Libs/SOM/DSNode.cpp

${OBJECTDIR}/_ext/15c88dff/NodeW.o: ../Libs/SOM/NodeW.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/15c88dff
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/15c88dff/NodeW.o ../Libs/SOM/NodeW.cpp

${OBJECTDIR}/_ext/418f266/bestMap.o: ../Libs/bestMap/bestMap.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/418f266
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/418f266/bestMap.o ../Libs/bestMap/bestMap.cpp

${OBJECTDIR}/_ext/418f266/bestMap_emxAPI.o: ../Libs/bestMap/bestMap_emxAPI.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/418f266
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/418f266/bestMap_emxAPI.o ../Libs/bestMap/bestMap_emxAPI.cpp

${OBJECTDIR}/_ext/418f266/bestMap_emxutil.o: ../Libs/bestMap/bestMap_emxutil.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/418f266
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/418f266/bestMap_emxutil.o ../Libs/bestMap/bestMap_emxutil.cpp

${OBJECTDIR}/_ext/418f266/bestMap_initialize.o: ../Libs/bestMap/bestMap_initialize.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/418f266
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/418f266/bestMap_initialize.o ../Libs/bestMap/bestMap_initialize.cpp

${OBJECTDIR}/_ext/418f266/bestMap_terminate.o: ../Libs/bestMap/bestMap_terminate.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/418f266
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/418f266/bestMap_terminate.o ../Libs/bestMap/bestMap_terminate.cpp

${OBJECTDIR}/_ext/418f266/hungarian.o: ../Libs/bestMap/hungarian.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/418f266
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/418f266/hungarian.o ../Libs/bestMap/hungarian.cpp

${OBJECTDIR}/_ext/418f266/rtGetInf.o: ../Libs/bestMap/rtGetInf.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/418f266
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/418f266/rtGetInf.o ../Libs/bestMap/rtGetInf.cpp

${OBJECTDIR}/_ext/418f266/rtGetNaN.o: ../Libs/bestMap/rtGetNaN.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/418f266
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/418f266/rtGetNaN.o ../Libs/bestMap/rtGetNaN.cpp

${OBJECTDIR}/_ext/418f266/rt_nonfinite.o: ../Libs/bestMap/rt_nonfinite.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/418f266
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/418f266/rt_nonfinite.o ../Libs/bestMap/rt_nonfinite.cpp

${OBJECTDIR}/_ext/dd71a7b4/hungarian.o: ../Libs/libhungarian-0.3/hungarian.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/dd71a7b4
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/_ext/dd71a7b4/hungarian.o ../Libs/libhungarian-0.3/hungarian.cpp

${OBJECTDIR}/main.o: main.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g -I../Libs/MatMatrix -I../Libs/SOM -I../Libs/Parameters -I../Libs/Debug -I../Libs/Defines -I../Libs/Cluster -I../Libs/Data -I. -I../Libs/SOM/delete -I../Libs/CImg -I../Libs/Extern -I../Libs/bestMap -I../Libs/libhungarian-0.3 -std=c++14 -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/main.o main.cpp

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r ${CND_BUILDDIR}/${CND_CONF}
	${RM} ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/netbeansproject

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc
