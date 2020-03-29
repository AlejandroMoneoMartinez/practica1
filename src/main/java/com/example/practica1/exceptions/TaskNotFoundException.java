package com.example.practica1.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class TaskNotFoundException extends RuntimeException implements GraphQLError {

    private Map<String, Object> extensions = new HashMap<>();

    // public TaskNotFoundException(String exception) {
    // super(exception);
    // }
    public TaskNotFoundException(Long invalidTaskid) {
        super();
        extensions.put("invalidTaskId", invalidTaskid);
    }

    @Override
    public ErrorType getErrorType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SourceLocation> getLocations() {
        // TODO Auto-generated method stub
        return null;
    }
}
