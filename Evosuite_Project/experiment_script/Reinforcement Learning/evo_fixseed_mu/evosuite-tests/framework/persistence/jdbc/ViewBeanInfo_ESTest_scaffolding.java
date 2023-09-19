/**
 * Scaffolding file used to store all the setups needed to run 
 * tests automatically generated by EvoSuite
 * Sat Aug 26 15:32:38 GMT 2023
 */

package framework.persistence.jdbc;

import shaded.org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import shaded.org.evosuite.runtime.sandbox.Sandbox;
import shaded.org.evosuite.runtime.sandbox.Sandbox.SandboxMode;

@EvoSuiteClassExclude
public class ViewBeanInfo_ESTest_scaffolding {

  @org.junit.Rule
  public shaded.org.evosuite.runtime.vnet.NonFunctionalRequirementRule nfr = new shaded.org.evosuite.runtime.vnet.NonFunctionalRequirementRule();

  private static final java.util.Properties defaultProperties = (java.util.Properties) java.lang.System.getProperties().clone(); 

  private shaded.org.evosuite.runtime.thread.ThreadStopper threadStopper =  new shaded.org.evosuite.runtime.thread.ThreadStopper (shaded.org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);


  @BeforeClass
  public static void initEvoSuiteFramework() { 
    shaded.org.evosuite.runtime.RuntimeSettings.className = "framework.persistence.jdbc.ViewBeanInfo"; 
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
    java.lang.System.setProperty("user.dir", "E:\\exec_cr"); 
    java.lang.System.setProperty("java.io.tmpdir", "C:\\Users\\ADMINI~1\\AppData\\Local\\Temp\\"); 
  }

  private static void initializeClasses() {
    shaded.org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(ViewBeanInfo_ESTest_scaffolding.class.getClassLoader() ,
      "framework.persistence.jdbc.View",
      "framework.base.ValueListHandler",
      "framework.persistence.jdbc.ViewBeanInfo",
      "framework.persistence.jdbc.Attribute",
      "framework.persistence.jdbc.Module",
      "framework.base.BaseBean",
      "framework.persistence.jdbc.Component"
    );
  } 

  private static void resetClasses() {
    shaded.org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(ViewBeanInfo_ESTest_scaffolding.class.getClassLoader()); 

    shaded.org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
      "framework.persistence.jdbc.ViewBeanInfo"
    );
  }
}
