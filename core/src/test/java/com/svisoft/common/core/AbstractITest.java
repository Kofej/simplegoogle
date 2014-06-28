package com.svisoft.common.core;

import com.svisoft.simplegoogle.core.storage.IndexService;
import com.svisoft.simplegoogle.core.storage.SearchService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;

import javax.inject.Inject;

@ContextConfiguration({
    "classpath*:com/svisoft/**/core/beans.xml",
})
public class AbstractITest
    extends AbstractTestNGSpringContextTests
{
  @Inject public IndexService indexService;
  @Inject public SearchService searchService;

  @AfterMethod
  public void clearDirectory()
      throws
      Exception
  {
    indexService.deleteAll();
  }
}
