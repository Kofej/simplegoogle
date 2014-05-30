package com.svisoft.simplegoogle.core.initialize;

public interface InitializeService
{
  //----------------------------------------------------------------------
  // Initializing
  //----------------------------------------------------------------------

  void initByUrl(String url)
      throws
      Exception;

  void initStringifiedHtmlPage(String url, String htmlPageAsString)
      throws
      Exception;

  //----------------------------------------------------------------------
  // Utilz
  //----------------------------------------------------------------------

  String extractClearTextFromStringifiedHtml(String htmlAsString);
}
