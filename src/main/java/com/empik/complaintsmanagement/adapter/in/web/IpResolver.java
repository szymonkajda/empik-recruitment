package com.empik.complaintsmanagement.adapter.in.web;

import com.empik.complaintsmanagement.application.datatype.IpAddress;
import jakarta.servlet.http.HttpServletRequest;

final class IpResolver {

  private static final String X_FORWARDED_FOR_HEADER = "X-Forwarded-For";
  private static final String PROXY_CLIENT_IP_HEADER = "WL-Proxy-Client-IP";

  private IpResolver() {}

  public static IpAddress resolveIp(HttpServletRequest request) {
    IpAddress forwardedForIp = new IpAddress(request.getHeader(X_FORWARDED_FOR_HEADER));
    if (forwardedForIp.isValid()) {
      return forwardedForIp;
    }

    IpAddress proxyClientIp = new IpAddress(request.getHeader(PROXY_CLIENT_IP_HEADER));
    if (proxyClientIp.isValid()) {
      return proxyClientIp;
    }

    return new IpAddress(request.getRemoteAddr());
  }
}
