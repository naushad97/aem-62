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

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Deactivate;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import ch.qos.logback.core.Appender;

/**
 * Simple servlet filter component that logs incoming requests.
 */
///@Component(immediate = true)
public class LogbackOsgiAppender {

	ServiceRegistration sr;

	@Activate
	private void activate(BundleContext bundleContext) {

		String[] appenders = { "com.aem.community:DEBUG" };
		Dictionary<String, Object> props = new Hashtable<String, Object>();
		props.put("loggers", appenders);

		sr = bundleContext.registerService(Appender.class.getName(), this, props);
	}

	@Deactivate
	private void deactivate() {
		if (sr != null) {
			sr.unregister();
		}
	}

}