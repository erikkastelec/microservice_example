// package com.test.microservice_example.exception;

// import java.util.Map;

// import jakarta.ws.rs.core.MediaType;
// import jakarta.ws.rs.core.Response;
// import jakarta.ws.rs.ext.ExceptionMapper;
// import jakarta.ws.rs.ext.Provider;

// @Provider
// class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

//     @Override
//     public Response toResponse(IllegalArgumentException exception) {
//         return Response.status(Response.Status.BAD_REQUEST)
//                     .type(MediaType.APPLICATION_JSON_TYPE)
//                     .entity(Map.of("error", exception.getMessage()))
//                     .build();
//     }
// }


