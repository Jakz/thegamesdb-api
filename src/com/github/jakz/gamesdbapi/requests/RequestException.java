package com.github.jakz.gamesdbapi.requests;

public class RequestException extends Exception
{
  private static final long serialVersionUID = 1L;
  
  RequestException(Throwable cause, String message, Object... args)
  {
    super(String.format(message, args), cause);
  }
  
  RequestException(String message, Object... args)
  {
    super(String.format(message, args));
  }
}
