package com.svisoft.common.core;

import com.svisoft.simplegoogle.core.storage.StorageService;
import com.svisoft.simplegoogle.core.storage.StorageDao;
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
  @Inject public StorageDao storageDao;
  @Inject public StorageService storageService;

  @AfterMethod
  public void clearDirectory()
      throws
      Exception
  {
    storageDao.deleteAll();
  }
}
