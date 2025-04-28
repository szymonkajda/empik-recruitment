package com.empik.complaintsmanagement.application.port.out;

import com.empik.complaintsmanagement.application.datatype.IpAddress;
import java.util.Optional;

public interface ResolveCountryPort {

  Optional<String> resolveBy(IpAddress ipAddress);
}
