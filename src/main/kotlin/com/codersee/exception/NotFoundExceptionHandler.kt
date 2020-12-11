package com.codersee.exception

import com.codersee.response.ExceptionResponse
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import javax.inject.Singleton

@Produces
@Singleton
class NotFoundExceptionHandler :
    ExceptionHandler<NotFoundException, HttpResponse<ExceptionResponse>> {

    override fun handle(
        request: HttpRequest<*>,
        exception: NotFoundException
    ): HttpResponse<ExceptionResponse> =
        HttpResponse.notFound(
            ExceptionResponse(
                message = exception.message
            )
        )

}