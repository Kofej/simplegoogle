package com.svisoft.simplegoogle.core.storage;

public interface IndexService
{
  void index(String id, String title, String context)
      throws
      Exception;

  void deleteById(String id)
      throws
      Exception;

  void deleteAll()
      throws
      Exception;
}
