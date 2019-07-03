package com.ace.framework.base;

import org.springframework.dao.DataAccessException;

public class DaoException extends DataAccessException
{
  private static final long serialVersionUID = 1L;

  public DaoException(String message)
  {
    super(message);
  }

  public DaoException(String message, Throwable cause)
  {
    super(message, cause);
  }
}