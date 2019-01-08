/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.aem.community.core.filters;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.felix.scr.annotations.sling.SlingFilter;
import org.apache.felix.scr.annotations.sling.SlingFilterScope;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Simple servlet filter component that logs incoming requests.
 */
@SlingFilter(order = -700, scope = SlingFilterScope.REQUEST)
public class LoggingFilter implements Filter {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
			throws IOException, ServletException {
		try {
			System.out.println("Adding MDC");
			final SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) request;
			logger.debug("request for {}, with selector {}", slingRequest.getRequestPathInfo().getResourcePath(),
					slingRequest.getRequestPathInfo().getSelectorString());

			Enumeration<String> names = slingRequest.getHeaderNames();
			while (names.hasMoreElements()) {
				String name = (String) names.nextElement();
				Enumeration<String> values = slingRequest.getHeaders(name); // support multiple
				if (values != null) {
					while (values.hasMoreElements()) {
						String value = (String) values.nextElement();
						System.out.println(name + ": " + value);
					}
				}
			}
			MDC.put("whs", "true");
			filterChain.doFilter(request, response);
		} finally {
			System.out.println("Removing MDC");
			MDC.remove("whs");
		}
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}

}