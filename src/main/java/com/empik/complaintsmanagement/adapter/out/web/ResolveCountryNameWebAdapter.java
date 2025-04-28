package com.empik.complaintsmanagement.adapter.out.web;

import static java.util.Objects.isNull;

import com.empik.complaintsmanagement.application.datatype.IpAddress;
import com.empik.complaintsmanagement.application.port.out.ResolveCountryPort;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
class ResolveCountryNameWebAdapter implements ResolveCountryPort {

  // TODO: Integration test
  private static final String REQUEST_API_URL = "https://ipapi.co/{ip}/json/";

  private final RestTemplate restTemplate = new RestTemplate();

  @Override
  public Optional<String> resolveBy(IpAddress ipAddress) {
    Response response = restTemplate.getForObject(buildUrl(ipAddress), Response.class);
    if (isNull(response)) {
      return Optional.empty();
    }
    return Optional.ofNullable(response.countryName());
  }

  private static String buildUrl(IpAddress ipAddress) {
    return UriComponentsBuilder.fromUriString(REQUEST_API_URL)
        .buildAndExpand(Map.of("ip", ipAddress.getValidatedValue()))
        .toUriString();
  }

  record Response(String countryName) {

    @JsonCreator
    Response(@JsonProperty("country_name") @Nullable String countryName) {
      this.countryName = countryName;
    }
  }
}
