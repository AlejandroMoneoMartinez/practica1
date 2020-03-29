package com.example.practica1.exceptions;

public class TaskNotFoundException extends RuntimeException {

    /**
   *
   */
  private static final long serialVersionUID = 1L;

  public TaskNotFoundException(String exception) {
        super(exception);
    }
}
