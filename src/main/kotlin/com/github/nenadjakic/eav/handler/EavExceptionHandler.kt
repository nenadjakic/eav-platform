package com.github.nenadjakic.eav.handler

import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.OffsetDateTime
import java.util.stream.Collectors


@ControllerAdvice
@ResponseBody
class EavExceptionHandler : ResponseEntityExceptionHandler() {

    data class ErrorInfo(
        var status: HttpStatus,
        var errors: List<String?>,
        var path: String
    ) {
        val timestamp: OffsetDateTime = OffsetDateTime.now()
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errors = ex.bindingResult
            .fieldErrors
            .stream()
            .map { obj: FieldError -> obj.defaultMessage }
            .collect(Collectors.toList())

        val path = (request as ServletWebRequest).request.requestURI
        val body = getErrorResponse(HttpStatus.BAD_REQUEST, errors, path)
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception, request: WebRequest?): ResponseEntity<ErrorInfo> {
        return getErrorInfoResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex, request as ServletWebRequest)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    open fun handleConstraintViolationException(ex: ConstraintViolationException, request: WebRequest?): ResponseEntity<ErrorInfo> {
        logger.error("Error occurred.", ex)
        return getErrorInfoResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex, request as ServletWebRequest)
    }

    private fun getErrorInfoResponseEntity(
        resultHttpStatus: HttpStatus,
        ex: Exception,
        request: ServletWebRequest
    ): ResponseEntity<ErrorInfo> {
        val path = request.request.requestURI

        logger.error("Exception occurred. in request: $path", ex)
        val body: ErrorInfo = if (ex is ConstraintViolationException && ex.constraintViolations.isNotEmpty()) {
            getErrorResponse(resultHttpStatus,
                ex.constraintViolations.stream()
                    .map { obj: ConstraintViolation<*> -> obj.message }
                    .collect(Collectors.toList()), path)
        } else {
            getErrorResponse(resultHttpStatus, ex.message, path)
        }

        return ResponseEntity(body, resultHttpStatus)
    }

    private fun getErrorResponse(resultHttpStatus: HttpStatus, message: String?, path: String): ErrorInfo {
        return getErrorResponse(resultHttpStatus, mutableListOf(message), path)
    }

    private fun getErrorResponse(resultHttpStatus: HttpStatus, messages: List<String?>, path: String): ErrorInfo {
        return ErrorInfo(resultHttpStatus, messages, path)
    }
}