package me.guilherme0s.identity.oauth2.authorization;

import io.micronaut.core.convert.value.ConvertibleMultiValues;
import io.micronaut.http.HttpParameters;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import me.guilherme0s.identity.oauth2.OAuth2Error;
import me.guilherme0s.identity.oauth2.OAuth2ErrorCodes;
import me.guilherme0s.identity.oauth2.OAuth2ParameterNames;
import org.jspecify.annotations.Nullable;

@Controller("/oauth2/authorize")
@Secured(SecurityRule.IS_ANONYMOUS)
public final class OAuth2AuthorizationController {

  @Get
  public void authorize(HttpRequest<?> request) {
    HttpParameters parameters = request.getParameters();

    String responseType = getSingleParameterOrNull(parameters, OAuth2ParameterNames.RESPONSE_TYPE);
    if (responseType == null || responseType.isBlank()) {
      throw missingRequiredParameter(OAuth2ParameterNames.RESPONSE_TYPE);
    }

    String clientId = getSingleParameterOrNull(parameters, OAuth2ParameterNames.CLIENT_ID);
    if (clientId == null || clientId.isBlank()) {
      throw missingRequiredParameter(OAuth2ParameterNames.CLIENT_ID);
    }

    String redirectUri = getSingleParameterOrNull(parameters, OAuth2ParameterNames.REDIRECT_URI);
    if (redirectUri == null || redirectUri.isBlank()) {
      throw missingRequiredParameter(OAuth2ParameterNames.REDIRECT_URI);
    }

    String scope = getSingleParameterOrNull(parameters, OAuth2ParameterNames.SCOPE);
    if (scope == null || scope.isBlank()) {
      throw missingRequiredParameter(OAuth2ParameterNames.SCOPE);
    }
    Set<String> scopes = Arrays.stream(scope.trim().split("\\s+"))
        .filter(s -> !s.isBlank())
        .collect(Collectors.toUnmodifiableSet());

    String state = getSingleParameterOrNull(parameters, OAuth2ParameterNames.STATE);

    String codeChallenge =
        getSingleParameterOrNull(parameters, OAuth2ParameterNames.CODE_CHALLENGE);
    if (codeChallenge == null || codeChallenge.isBlank()) {
      throw missingRequiredParameter(OAuth2ParameterNames.CODE_CHALLENGE);
    }

    String codeChallengeMethod =
        getSingleParameterOrNull(parameters, OAuth2ParameterNames.CODE_CHALLENGE_METHOD);
    if (codeChallengeMethod == null || codeChallengeMethod.isBlank()) {
      throw missingRequiredParameter(OAuth2ParameterNames.CODE_CHALLENGE_METHOD);
    }
  }

  private static @Nullable String getSingleParameterOrNull(
      ConvertibleMultiValues<String> parameters, String parameterName) {
    List<String> values = parameters.getAll(parameterName);
    if (values.isEmpty()) {
      return null;
    }
    if (values.size() > 1) {
      throw new OAuth2AuthorizationException(
          new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST,
              "The request contains multiple values for the '" + parameterName + "' parameter."));
    }
    return values.getFirst();
  }

  private static OAuth2AuthorizationException missingRequiredParameter(String parameterName) {
    return new OAuth2AuthorizationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST,
        "The request is missing the '" + parameterName + "' parameter."));
  }
}
