/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Thu Sep 14 12:08:09 GMT 2023
 */

package com.ib.client;

import shaded.org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import shaded.org.evosuite.runtime.sandbox.Sandbox;
import shaded.org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

@EvoSuiteClassExclude
public class EWrapperMsgGenerator_ESTest_scaffolding {

  @org.junit.Rule
  public shaded.org.evosuite.runtime.vnet.NonFunctionalRequirementRule nfr = new shaded.org.evosuite.runtime.vnet.NonFunctionalRequirementRule();

  private static final java.util.Properties defaultProperties = (java.util.Properties) java.lang.System.getProperties().clone(); 

  private shaded.org.evosuite.runtime.thread.ThreadStopper threadStopper =  new shaded.org.evosuite.runtime.thread.ThreadStopper (shaded.org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);


  @BeforeClass
  public static void initEvoSuiteFramework() { 
    shaded.org.evosuite.runtime.RuntimeSettings.className = "com.ib.client.EWrapperMsgGenerator"; 
    shaded.org.evosuite.runtime.GuiSupport.initialize(); 
    shaded.org.evosuite.runtime.RuntimeSettings.maxNumberOfThreads = 100; 
    shaded.org.evosuite.runtime.RuntimeSettings.maxNumberOfIterationsPerLoop = 10000; 
    shaded.org.evosuite.runtime.RuntimeSettings.mockSystemIn = true; 
    shaded.org.evosuite.runtime.RuntimeSettings.sandboxMode = shaded.org.evosuite.runtime.sandbox.Sandbox.SandboxMode.RECOMMENDED; 
    shaded.org.evosuite.runtime.sandbox.Sandbox.initializeSecurityManagerForSUT(); 
    shaded.org.evosuite.runtime.classhandling.JDKClassResetter.init();
    setSystemProperties();
    initializeClasses();
    shaded.org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
  } 

  @AfterClass
  public static void clearEvoSuiteFramework(){ 
    Sandbox.resetDefaultSecurityManager(); 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
  } 

  @Before
  public void initTestCase(){ 
    threadStopper.storeCurrentThreads();
    threadStopper.startRecordingTime();
    shaded.org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().initHandler(); 
    shaded.org.evosuite.runtime.sandbox.Sandbox.goingToExecuteSUTCode(); 
    setSystemProperties(); 
    shaded.org.evosuite.runtime.GuiSupport.setHeadless(); 
    shaded.org.evosuite.runtime.Runtime.getInstance().resetRuntime(); 
    shaded.org.evosuite.runtime.agent.InstrumentingAgent.activate(); 
  } 

  @After
  public void doneWithTestCase(){ 
    threadStopper.killAndJoinClientThreads();
    shaded.org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().safeExecuteAddedHooks(); 
    shaded.org.evosuite.runtime.classhandling.JDKClassResetter.reset(); 
    resetClasses(); 
    shaded.org.evosuite.runtime.sandbox.Sandbox.doneWithExecutingSUTCode(); 
    shaded.org.evosuite.runtime.agent.InstrumentingAgent.deactivate(); 
    shaded.org.evosuite.runtime.GuiSupport.restoreHeadlessMode(); 
  } 

  public static void setSystemProperties() {
 
    java.lang.System.setProperties((java.util.Properties) defaultProperties.clone()); 
    java.lang.System.setProperty("user.dir", "E:\\evo_randomseed_mu"); 
    java.lang.System.setProperty("java.io.tmpdir", "C:\\Users\\ADMINI~1\\AppData\\Local\\Temp\\"); 
  }

  private static void initializeClasses() {
    shaded.org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(EWrapperMsgGenerator_ESTest_scaffolding.class.getClassLoader() ,
      "com.ib.client.EWrapperMsgGenerator",
      "com.ib.client.EException",
      "com.ib.client.Contract",
      "com.ib.client.Util",
      "com.ib.client.TickType",
      "com.ib.client.UnderComp",
      "com.ib.client.ContractDetails",
      "com.ib.client.EClientSocket",
      "com.ib.client.OrderState",
      "com.ib.client.Execution",
      "com.ib.client.Order",
      "com.ib.client.AnyWrapperMsgGenerator"
    );
  } 

  private static void resetClasses() {
    shaded.org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(EWrapperMsgGenerator_ESTest_scaffolding.class.getClassLoader()); 

    shaded.org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "com.ib.client.AnyWrapperMsgGenerator",
      "com.ib.client.EWrapperMsgGenerator",
      "com.ib.client.Contract",
      "com.ib.client.TagValue",
      "com.ib.client.TickType",
      "com.ib.client.EClientSocket",
      "com.ib.client.ContractDetails",
      "com.ib.client.Execution",
      "com.ib.client.Order",
      "com.ib.client.OrderState",
      "com.ib.client.Util",
      "com.ib.client.UnderComp"
    );
  }
}