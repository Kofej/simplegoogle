package com.svisoft.simplegoogle.core.impl.storage.utest;

import com.svisoft.simplegoogle.core.impl.storage.StorageServiceImpl;
import mockit.Tested;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class StorageServiceImplTest
{
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
            {"<html><head><script> \nfunction myFunction() alert(\"lol\");}</script></head><body>some text</body><body>some text</body></html>", " some text some text "},
            {"<html><head><script src=\"/apps/general/js/config.js\" type=\"text/javascript\"></head><body>some text</body><body>some text</body></html>", " some text some text "},
        };
  }

}
