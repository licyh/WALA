/*******************************************************************************
 * Copyright (c) 2002 - 2006 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.wala.core.tests.cha;

import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.core.tests.util.TestConstants;
import com.ibm.wala.core.tests.util.WalaTestCase;
import com.ibm.wala.emf.wrappers.EMFScopeWrapper;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.types.TypeReference;

/**
 * A test of support for source file mapping
 * 
 * @author sfink
 */
public class SourceMapTest extends WalaTestCase {
  private static final ClassLoader MY_CLASSLOADER = SourceMapTest.class.getClassLoader();

  private final static String CLASS_IN_PRIMORDIAL_JAR = "Lcom/ibm/wala/model/SyntheticFactory";

  public void testHello() throws ClassHierarchyException {
    AnalysisScope scope = null;
    scope = new EMFScopeWrapper(TestConstants.HELLO, null, MY_CLASSLOADER);
    // TODO: it's annoying to have to build a class hierarchy here.
    // see feature 38676
    ClassHierarchy cha = ClassHierarchy.make(scope);
    TypeReference t = TypeReference.findOrCreate(scope.getApplicationLoader(), TestConstants.HELLO_MAIN);
    IClass klass = cha.lookupClass(t);
    assertTrue("failed to load " + t, klass != null);
    String sourceFile = klass.getSourceFileName();
    System.err.println("Source file: " + sourceFile);
    assertTrue(sourceFile != null);
  }

  public void testFromJar() throws ClassHierarchyException {
    AnalysisScope scope = null;
    scope = new EMFScopeWrapper(TestConstants.HELLO, null, MY_CLASSLOADER);
    // TODO: it's annoying to have to build a class hierarchy here.
    // open a feature to fix this
    ClassHierarchy cha = ClassHierarchy.make(scope);
    TypeReference t = TypeReference.findOrCreate(scope.getPrimordialLoader(), CLASS_IN_PRIMORDIAL_JAR);
    IClass klass = cha.lookupClass(t);
    assertTrue(klass != null);
    String sourceFile = klass.getSourceFileName();
    assertTrue(sourceFile != null);
    System.err.println("Source file: " + sourceFile);
  }
}