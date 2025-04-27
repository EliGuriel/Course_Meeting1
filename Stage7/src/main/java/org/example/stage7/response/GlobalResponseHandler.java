package org.example.stage7.response;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Global response handler to standardize all API responses.
 * Applies to all responses from controller methods.
 *
 * This class works in conjunction with GlobalExceptionHandler:
 * - GlobalExceptionHandler catches exceptions and converts them to StandardResponse
 * - GlobalResponseHandler ensures all other responses also follow the StandardResponse format
 *
 * The combined effect is a consistent API response structure for both success and error cases.
 */
@ControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    /**
     * Determines whether this advice should be applied to the response.
     *
     * By returning true, we ensure this handler processes ALL responses from controllers,
     * regardless of their return type or the converter being used.
     *
     * @param returnType The return type of the controller method
     * @param converterType The message converter type
     * @return true to process all responses, false to skip processing
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // Process all responses from controller methods
        return true;
    }

    /**
     * Processes the response body before it's written to the response.
     * This is where we standardize all responses into the StandardResponse format.
     *
     * The execution flow is:
     * 1. First, check for special cases (204 responses, already wrapped responses, error responses)
     * 2. For normal responses, wrap them in a StandardResponse with "success" status
     *
     * Note: Responses from GlobalExceptionHandler are already StandardResponse objects,
     * so they'll be detected by the instanceof check and passed through unchanged.
     *
     * @param body The response body from the controller
     * @param returnType The return type of the controller method
     * @param selectedContentType The selected content type for the response
     * @param selectedConverterType The selected converter type
     * @param request The current request
     * @param response The current response
     * @return The (potentially modified) response body
     */
    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        // Check for a special header that might be set for 204 responses
        // This allows explicit bypassing of the StandardResponse wrapper when needed
        if (response.getHeaders().getFirst("X-Response-Status") != null &&
                response.getHeaders().getFirst("X-Response-Status").equals("204")) {
            return body; // Don't modify 204 responses
        }

        // If the response is already a StandardResponse, return it unchanged
        // This is the key integration point with GlobalExceptionHandler:
        // - When GlobalExceptionHandler creates a StandardResponse, it passes through here unchanged
        // - This prevents double-wrapping of responses
        if (body instanceof StandardResponse) {
            return body;
        }

        // If the response is an ErrorResponse (from Spring's built-in error handling),
        // convert it to our StandardResponse format for consistency
        if (body instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) body;
            return new StandardResponse("error", null, errorResponse);
        }

        // Special handling for null (from 204 No Content responses)
        // We allow null to pass through without wrapping to maintain HTTP semantics
        // For DELETE operations, Spring often returns null with a 204-status code
        if (body == null) {
            return null; // Allow null to pass through for 204 No Content responses
        }

        // Special handling for string error messages
        // This handles cases where errors might be returned as simple strings
        // containing the word "error"
        if (body instanceof String && ((String) body).contains("error")) {
            return new StandardResponse("error", null, body);
        }

        // Default case: wrap the response body in a success StandardResponse
        // This ensures all regular responses have the standard structure
        // The actual data is placed in the "data" field of StandardResponse
        return new StandardResponse("success", body, null);
    }
}