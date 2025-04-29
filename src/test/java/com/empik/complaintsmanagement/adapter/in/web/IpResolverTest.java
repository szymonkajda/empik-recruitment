package com.empik.complaintsmanagement.adapter.in.web;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

import com.empik.complaintsmanagement.application.datatype.IpAddress;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IpResolverTest {

  private static final String X_FORWARDED_FOR_HEADER = "X-Forwarded-For";
  private static final String PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
  private static final String IP_ADDRESS_VALUE = "8.8.8.8";

  @Mock private HttpServletRequest httpServletRequest;

  @Test
  void shouldResolveIpAddressFromXForwarderForHeader() {
    // given
    given(httpServletRequest.getHeader(X_FORWARDED_FOR_HEADER)).willReturn(IP_ADDRESS_VALUE);

    // when
    IpAddress ipAddress = IpResolver.resolveIp(httpServletRequest);

    // then
    assertThat(ipAddress.value()).isEqualTo(IP_ADDRESS_VALUE);
  }

  @Test
  void shouldResolveIpAddressFromProxyClientIpHeader() {
    // given
    given(httpServletRequest.getHeader(X_FORWARDED_FOR_HEADER)).willReturn(null);
    given(httpServletRequest.getHeader(PROXY_CLIENT_IP)).willReturn(IP_ADDRESS_VALUE);

    // when
    IpAddress ipAddress = IpResolver.resolveIp(httpServletRequest);

    // then
    assertThat(ipAddress.value()).isEqualTo(IP_ADDRESS_VALUE);
  }

  @Test
  void shouldResolveIpAddressFromRemoteAddr() {
    // given
    given(httpServletRequest.getHeader(X_FORWARDED_FOR_HEADER)).willReturn(null);
    given(httpServletRequest.getHeader(PROXY_CLIENT_IP)).willReturn(null);
    given(httpServletRequest.getRemoteAddr()).willReturn(IP_ADDRESS_VALUE);

    // when
    IpAddress ipAddress = IpResolver.resolveIp(httpServletRequest);

    // then
    assertThat(ipAddress.value()).isEqualTo(IP_ADDRESS_VALUE);
  }
}
