#!/bin/sh
java -Xmx1024m -cp i9-weka.jar:weka.jar:i9-subspace.jar:Jama.jar:jsc.jar:commons-math-1.1.jar:vecmath.jar:j3dcore.jar:j3dutils.jar weka.gui.explorer.Explorer
