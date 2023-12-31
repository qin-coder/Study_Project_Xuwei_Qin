/*
 * This file was automatically generated by EvoSuite
 * Thu Sep 14 11:51:02 GMT 2023
 */

package bcry;

import org.junit.Test;
import static org.junit.Assert.*;
import static shaded.org.evosuite.runtime.EvoAssertions.*;
import bcry.battlecryGUI;
import bcry.bcDictionary;
import bcry.bcVoice;
import org.junit.runner.RunWith;
import shaded.org.evosuite.runtime.EvoRunner;
import shaded.org.evosuite.runtime.EvoRunnerParameters;
import shaded.org.evosuite.runtime.testdata.EvoSuiteFile;
import shaded.org.evosuite.runtime.testdata.FileSystemHandling;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true) 
public class bcDictionary_ESTest extends bcDictionary_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      boolean[] booleanArray0 = new boolean[4];
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = new bcDictionary("", bcVoice0);
      bcDictionary0.saveDB("^;{P$");
      bcDictionary0.loadDB("^;{P$");
      assertEquals(0, bcDictionary0.getSize());
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      EvoSuiteFile evoSuiteFile0 = new EvoSuiteFile("Wd");
      FileSystemHandling.appendLineToFile(evoSuiteFile0, "/9Wy3zXQJp d;TLa0");
      boolean[] booleanArray0 = new boolean[4];
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = new bcDictionary("Wd", bcVoice0);
      int int0 = bcDictionary0.getSize();
      assertEquals(1, int0);
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      boolean[] booleanArray0 = new boolean[2];
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = new bcDictionary("Loading database...", bcVoice0);
      String[] stringArray0 = new String[6];
      stringArray0[0] = "java.lang.String@0000000004 ";
      stringArray0[1] = "+ hR+ hR";
      stringArray0[2] = "Loading database...";
      stringArray0[3] = "Loading database...";
      stringArray0[4] = "";
      stringArray0[5] = "+ hR";
      String string0 = bcDictionary0.getPhonemes(stringArray0);
      assertEquals("     ", string0);
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      boolean[] booleanArray0 = new boolean[4];
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = new bcDictionary("", bcVoice0);
      String[] stringArray0 = new String[1];
      stringArray0[0] = "";
      String string0 = bcDictionary0.getPhonemes(stringArray0);
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      boolean[] booleanArray0 = new boolean[3];
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = new bcDictionary("Saving database...", bcVoice0);
      // Undeclared exception!
      try { 
        bcDictionary0.loadDict((String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("shaded.org.evosuite.runtime.mock.java.io.MockFileInputStream", e);
      }
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      boolean[] booleanArray0 = new boolean[3];
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = new bcDictionary("# ", bcVoice0);
      String[] stringArray0 = new String[0];
      // Undeclared exception!
      try { 
        bcDictionary0.getPhonemes(stringArray0);
        fail("Expecting exception: ArrayIndexOutOfBoundsException");
      
      } catch(ArrayIndexOutOfBoundsException e) {
         //
         // -1
         //
         verifyException("bcry.bcDictionary", e);
      }
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      boolean[] booleanArray0 = new boolean[10];
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = new bcDictionary("Loading database...", bcVoice0);
      // Undeclared exception!
      try { 
        bcDictionary0.getPhonemes((String) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("bcry.bcDictionary", e);
      }
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      boolean[] booleanArray0 = new boolean[7];
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = new bcDictionary(" ", bcVoice0);
      // Undeclared exception!
      try { 
        bcDictionary0.getPhonemes(" ");
        fail("Expecting exception: ArrayIndexOutOfBoundsException");
      
      } catch(ArrayIndexOutOfBoundsException e) {
         //
         // -1
         //
         verifyException("bcry.bcDictionary", e);
      }
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      boolean[] booleanArray0 = new boolean[6];
      EvoSuiteFile evoSuiteFile0 = new EvoSuiteFile("%");
      FileSystemHandling.appendStringToFile(evoSuiteFile0, "%");
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = null;
      try {
        bcDictionary0 = new bcDictionary("%", bcVoice0);
        fail("Expecting exception: StringIndexOutOfBoundsException");
      
      } catch(StringIndexOutOfBoundsException e) {
      }
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      bcDictionary bcDictionary0 = null;
      try {
        bcDictionary0 = new bcDictionary(":Z", (bcVoice) null);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("bcry.bcDictionary", e);
      }
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      EvoSuiteFile evoSuiteFile0 = new EvoSuiteFile("Loading database...");
      FileSystemHandling.appendStringToFile(evoSuiteFile0, "##/9Wy3zXQJp w;TLa0");
      boolean[] booleanArray0 = new boolean[9];
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = new bcDictionary("+ hR", bcVoice0);
      bcDictionary0.loadDict("Loading database...");
      assertEquals(0, bcDictionary0.getSize());
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      boolean[] booleanArray0 = new boolean[5];
      EvoSuiteFile evoSuiteFile0 = new EvoSuiteFile("Loading database...");
      FileSystemHandling.appendLineToFile(evoSuiteFile0, "");
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = new bcDictionary("", bcVoice0);
      bcDictionary0.loadDict("Loading database...");
      assertEquals(0, bcDictionary0.getSize());
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      EvoSuiteFile evoSuiteFile0 = new EvoSuiteFile("Wd");
      FileSystemHandling.appendStringToFile(evoSuiteFile0, "/9Wy3zXQJp d;TLa0");
      boolean[] booleanArray0 = new boolean[4];
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = new bcDictionary("Wd", bcVoice0);
      bcDictionary0.loadDict("Wd");
      assertEquals(1, bcDictionary0.getSize());
  }

  @Test(timeout = 4000)
  public void test13()  throws Throwable  {
      boolean[] booleanArray0 = new boolean[12];
      EvoSuiteFile evoSuiteFile0 = new EvoSuiteFile("Very Large");
      FileSystemHandling.appendLineToFile(evoSuiteFile0, "      tempList.addWord(\"");
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = new bcDictionary("Very Large", bcVoice0);
      bcDictionary0.getPhonemes("");
      assertEquals(1, bcDictionary0.getSize());
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      boolean[] booleanArray0 = new boolean[2];
      EvoSuiteFile evoSuiteFile0 = new EvoSuiteFile("# R{3$S");
      FileSystemHandling.appendStringToFile(evoSuiteFile0, "# R{3$S");
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = new bcDictionary("# R{3$S", bcVoice0);
      bcDictionary0.getPhonemes("# ");
      assertEquals(1, bcDictionary0.getSize());
  }

  @Test(timeout = 4000)
  public void test15()  throws Throwable  {
      boolean[] booleanArray0 = new boolean[3];
      EvoSuiteFile evoSuiteFile0 = new EvoSuiteFile("# R{3$S");
      FileSystemHandling.appendStringToFile(evoSuiteFile0, "# R{3$S");
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = new bcDictionary("# R{3$S", bcVoice0);
      bcDictionary0.getPhonemes("# R{3$S");
      assertEquals(1, bcDictionary0.getSize());
  }

  @Test(timeout = 4000)
  public void test16()  throws Throwable  {
      boolean[] booleanArray0 = new boolean[12];
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = new bcDictionary("Very Large", bcVoice0);
      String string0 = bcDictionary0.getPhonemes("Very Large");
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test17()  throws Throwable  {
      boolean[] booleanArray0 = new boolean[12];
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = new bcDictionary("Very Large", bcVoice0);
      String string0 = bcDictionary0.getPhonemes("");
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test18()  throws Throwable  {
      boolean[] booleanArray0 = new boolean[4];
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = new bcDictionary("", bcVoice0);
      String[] stringArray0 = new String[4];
      // Undeclared exception!
      try { 
        bcDictionary0.getPhonemes(stringArray0);
        fail("Expecting exception: NullPointerException");
      
      } catch(NullPointerException e) {
         //
         // no message in exception (getMessage() returned null)
         //
         verifyException("bcry.bcDictionary", e);
      }
  }

  @Test(timeout = 4000)
  public void test19()  throws Throwable  {
      boolean[] booleanArray0 = new boolean[2];
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = new bcDictionary("2#Ce4:", bcVoice0);
      bcDictionary0.saveDB("2#Ce4:");
      // Undeclared exception!
      try { 
        bcDictionary0.loadDict("2#Ce4:");
        fail("Expecting exception: StringIndexOutOfBoundsException");
      
      } catch(StringIndexOutOfBoundsException e) {
      }
  }

  @Test(timeout = 4000)
  public void test20()  throws Throwable  {
      boolean[] booleanArray0 = new boolean[12];
      bcVoice bcVoice0 = new bcVoice(booleanArray0, (battlecryGUI) null);
      bcDictionary bcDictionary0 = new bcDictionary("Very Large", bcVoice0);
      int int0 = bcDictionary0.getSize();
      assertEquals(0, int0);
  }
}
