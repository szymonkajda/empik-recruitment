package com.empik.complaintsmanagement.adapter.out.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import com.empik.complaintsmanagement.application.datatype.IpAddress;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class ResolveCountryNameWebAdapterTest {

  private static final String EXPECTED_REQUEST_API_URL = "https://ipapi.co/8.8.8.8/json/";
  private static final IpAddress IP_ADDRESS = new IpAddress("8.8.8.8");
  private static final String COUNTRY_NAME = "United States";

  @Mock private RestTemplate restTemplate;
  private ResolveCountryNameWebAdapter resolveCountryNameWebAdapter;

  @BeforeEach
  void setUp() {
    resolveCountryNameWebAdapter = new ResolveCountryNameWebAdapter();
    setField(resolveCountryNameWebAdapter, "restTemplate", restTemplate);
  }

  @Test
  void shouldResolveCountryNameCorrectly() {
    // given
    given(
            restTemplate.getForObject(
                EXPECTED_REQUEST_API_URL, ResolveCountryNameWebAdapter.Response.class))
        .willReturn(new ResolveCountryNameWebAdapter.Response(COUNTRY_NAME));

    // when
    Optional<String> countryName = resolveCountryNameWebAdapter.resolveBy(IP_ADDRESS);

    // then
    assertThat(countryName).hasValue(COUNTRY_NAME);
  }

  private static Stream<ResolveCountryNameWebAdapter.Response> ResponseScenarios() {
    return Stream.of(new ResolveCountryNameWebAdapter.Response(null), null);
  }

  @ParameterizedTest
  @MethodSource("ResponseScenarios")
  void shouldReturnEmptyOptionalWhenItIsNotResolvedCorrectly(
      ResolveCountryNameWebAdapter.Response response) {
    // given
    given(
            restTemplate.getForObject(
                EXPECTED_REQUEST_API_URL, ResolveCountryNameWebAdapter.Response.class))
        .willReturn(response);

    // when
    Optional<String> countryName = resolveCountryNameWebAdapter.resolveBy(IP_ADDRESS);

    // then
    assertThat(countryName).isEmpty();
  }
}
