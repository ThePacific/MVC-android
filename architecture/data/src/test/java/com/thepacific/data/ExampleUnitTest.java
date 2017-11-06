package com.thepacific.data;

import com.pacific.data.platform.Decompress;
import java.io.File;
import java.io.IOException;
import java.util.zip.DataFormatException;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

  @Test
  public void testDe() throws DataFormatException, IOException {
    Decompress decompress = new Decompress(new File("test.zip"));
    decompress.unzip();
  }
}