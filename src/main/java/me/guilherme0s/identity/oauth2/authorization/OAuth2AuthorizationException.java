package me.guilherme0s.identity.oauth2.authorization;

import me.guilherme0s.identity.oauth2.OAuth2Error;

public final class OAuth2AuthorizationException extends RuntimeException {

  private final OAuth2Error error;

  public OAuth2AuthorizationException(OAuth2Error error) {
    super(error.toString());
    this.error = error;
  }

  public OAuth2Error getError() {
    return error;
  }
}
