/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.aem.community.core.filters;

import java.util.Properties;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

@Component(immediate = true)
public class ConfigAppExample {
    private ServiceRegistration registration;

    @Activate
    private void activate(BundleContext context){
        Properties props = new Properties();
        props.setProperty("logbackConfig","true");

        String config  ="<included>\n"+
						"<appender name=\"SIFT\" class=\"ch.qos.logback.classic.sift.SiftingAppender\">\n"+
						"<discriminator>"+
					        "<key>whs</key>"+
					        "<defaultValue>${sling.home}/logs/app.log</defaultValue>"+
					    "</discriminator>"+
					        "<sift>"+
					    
					         
								/*"<filter class=\"com.aem.community.core.filters.LogbackFilter\"/>"+*/
									/*"<evaluator>"+
								        "<expression>return true;</expression>"+
									"</evaluator>"+
								     "<OnMismatch>DENY</OnMismatch>"+
								     "<OnMatch>ACCEPT</OnMatch>"+
								"</filter>"+*/
						 		/*"<file>${sling.home}/logs/app.log</file>\n" +*/
								"<appender name=\"app\" class=\"ch.qos.logback.core.FileAppender\">"+
								"<file>${sling.home}/logs/${whs}.log</file>\n" +
								"<append>true</append>\n"+
								"<encoder>\n"+
									"<pattern>%d %-5level [%thread] %logger{30} %marker- %msg %n</pattern>\n"+
									"<immediateFlush>true</immediateFlush>\n"+
								"</encoder>\n"+
							   "</appender>\n"+
								"</sift>"+
							   " </appender>"+
							   "<logger name=\"com.aem.community\" level=\"DEBUG\" additivity=\"false\">\n"+
							   "     <appender-ref ref=\"SIFT\" />\n" +
				               "  </logger>\n" +
							   "</included>";

        registration = context.registerService(String.class.getName(),config, props);
    }

    @Deactivate
    public void deactivate(){
        if(registration != null){
            registration.unregister();
        }
    }
}
