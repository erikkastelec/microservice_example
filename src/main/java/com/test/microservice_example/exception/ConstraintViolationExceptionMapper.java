// package com.test.microservice_example.exception;

// import jakarta.validation.ConstraintViolation;
// import jakarta.validation.ConstraintViolationException;

// import jakarta.ws.rs.core.MediaType;
// import jakarta.ws.rs.core.Response;
// import jakarta.ws.rs.ext.ExceptionMapper;
// import jakarta.ws.rs.ext.Provider;
// import java.util.HashMap;
// import java.util.Map;


// @Provider
// class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
//     public ConstraintViolationExceptionMapper() {
//     }

//     @Override
//     public Response toResponse(ConstraintViolationException exception) {
//         Map<String, String> errors = new HashMap<>();
//         for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
//             errors.put(violation.getPropertyPath().toString(), violation.getMessage());
//         }
//         return Response.status(Response.Status.BAD_REQUEST)
//                        .type(MediaType.APPLICATION_JSON_TYPE)
//                        .entity(errors)
//                        .build();
//     }
// }