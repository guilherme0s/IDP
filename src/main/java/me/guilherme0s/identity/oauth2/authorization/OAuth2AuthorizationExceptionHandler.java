package me.guilherme0s.identity.oauth2.authorization;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
public final class OAuth2AuthorizationExceptionHandler
    implements ExceptionHandler<OAuth2AuthorizationException, HttpResponse<?>> {

  @Override
  public HttpResponse<?> handle(HttpRequest request, OAuth2AuthorizationException exception) {
    return HttpResponse.badRequest(exception.getError());
  }
}
