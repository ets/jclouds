/**
 *
 * Copyright (C) 2009 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */
#set( $ucaseProviderName = ${providerName.toUpperCase()} )
/**
 *
 * Copyright (C) 2009 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
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
 * ====================================================================
 */
package ${package}.config;

import java.io.UnsupportedEncodingException;
import java.net.URI;

import javax.inject.Named;
import javax.inject.Singleton;

import org.jclouds.concurrent.internal.SyncProxy;
import org.jclouds.http.RequiresHttp;
import org.jclouds.http.filters.BasicAuthentication;
import org.jclouds.rest.ConfiguresRestClient;
import org.jclouds.rest.RestClientFactory;
import org.jclouds.encryption.EncryptionService;

import ${package}.${providerName};
import ${package}.${providerName}Client;
import ${package}.${providerName}AsyncClient;
import ${package}.reference.${providerName}Constants;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * Configures the ${providerName} connection.
 * 
 * @author ${author}
 */
@RequiresHttp
@ConfiguresRestClient
public class ${providerName}RestClientModule extends AbstractModule {

   @Override
   protected void configure() {
      bindErrorHandlers();
      bindRetryHandlers();
   }

   @Provides
   @Singleton
   public BasicAuthentication provideBasicAuthentication(
            @Named(${providerName}Constants.PROPERTY_${ucaseProviderName}_USER) String user,
            @Named(${providerName}Constants.PROPERTY_${ucaseProviderName}_PASSWORD) String password,
            EncryptionService encryptionService)
            throws UnsupportedEncodingException {
      return new BasicAuthentication(user, password, encryptionService);
   }

   @Provides
   @Singleton
   protected ${providerName}AsyncClient provideClient(RestClientFactory factory) {
      return factory.create(${providerName}AsyncClient.class);
   }

   @Provides
   @Singleton
   public ${providerName}Client provideClient(${providerName}AsyncClient provider) throws IllegalArgumentException,
            SecurityException, NoSuchMethodException {
      return SyncProxy.create(${providerName}Client.class, provider);
   }
   
   @Provides
   @Singleton
   @${providerName}
   protected URI provideURI(@Named(${providerName}Constants.PROPERTY_${ucaseProviderName}_ENDPOINT) String endpoint) {
      return URI.create(endpoint);
   }

   protected void bindErrorHandlers() {
      // TODO
   }

   protected void bindRetryHandlers() {
      // TODO
   }

}