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
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.MDC;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
/**
 * Simple servlet filter component that logs incoming requests.
 */
//@Component(immediate=true)
public class LogbackScanFilter extends Filter<ILoggingEvent>{

	
	ServiceRegistration sr;
	
	/*public void FilteringAppender() {
        setName("app");
    }*/
	
	@Override
	public FilterReply decide(ILoggingEvent arg0) {
		System.out.println("LogbackScanFilter:: "+arg0.getThreadName());
		System.out.println("LogbackScanFilter.getCallerData:: "+arg0.getCallerData());
		System.out.println("MDC :: "+MDC.get("whs"));
		if(null != MDC.get("whs") && MDC.get("whs").equalsIgnoreCase("true")){
            return FilterReply.ACCEPT;
        }
        return FilterReply.DENY;
	}
	
	@Activate
    private void activate(BundleContext bundleContext){
		
		Dictionary<String, Object> props = new Hashtable<String, Object>();
		props.put("appenders", "scan");
		
		//LogbackFilter stf = new LogbackFilter();
		sr = bundleContext.registerService(Filter.class.getName(), this, props);
		
		
		
		
		/*sr = bundleContext.registerService(Filter.class.getName(),new ServiceFactory() {
            private Object instance;

            public synchronized Object getService(Bundle bundle, ServiceRegistration serviceRegistration) {
                if(instance == null){
                    instance = new LogbackFilter();
                }
                return instance;
            }

            public void ungetService(Bundle bundle, ServiceRegistration serviceRegistration, Object o) {

            }
        },props);*/
    }

    @Deactivate
    private void deactivate(){
        if(sr != null){
            sr.unregister();
        }
    }


}