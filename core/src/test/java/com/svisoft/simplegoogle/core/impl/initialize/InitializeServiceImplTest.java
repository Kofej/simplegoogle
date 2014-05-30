package com.svisoft.simplegoogle.core.impl.initialize;

import mockit.Tested;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class InitializeServiceImplTest
{
  @Tested
  InitializeServiceImpl initializeService;

  @DataProvider
  Object[][] htmlTagSamples()
  {
    return new Object[][]
    {
        {"<div>some text</div>", " some text "},
        {"<div><label>some text</label></div>", " some text "},
        {"<!--!@#$%^&*()_+-->some text", " some text"},
        {"<html><body><h1>some text<h1><a class\"some-class\" /></body></html>", " some text "},
        {"<html><body>some text</body><body>some text</body></html>", " some text some text "},
        {"<html><head></head><body>some text</body><body>some text</body></html>", " some text some text "},
//        {"<html><head><script> function myFunction() alert(\"lol\");}</script></head><body>some text</body><body>some text</body></html>", " some text some text "},
        {"<html><head><script src=\"/apps/general/js/config.js\" type=\"text/javascript\"></head><body>some text</body><body>some text</body></html>", " some text some text "},
    };
  }


  @Test(dataProvider = "htmlTagSamples")
  public void test(String htmlTagSample, String result)
  {
    assertEquals(initializeService.extractClearTextFromStringifiedHtml(htmlTagSample), result);
  }
}
