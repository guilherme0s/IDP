package me.guilherme0s.identity.oauth2;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.serde.config.naming.SnakeCaseStrategy;
import org.jspecify.annotations.Nullable;

@Serdeable(naming = SnakeCaseStrategy.class)
public record OAuth2Error(String error, @Nullable String errorDescription) {

  @Override
  public String toString() {
    var sb = new StringBuilder(error);
    if (errorDescription != null) {
      sb.append(": ").append(errorDescription);
    }
    return sb.toString();
  }
}
