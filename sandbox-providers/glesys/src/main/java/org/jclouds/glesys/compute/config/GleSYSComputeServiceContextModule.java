/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
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
package org.jclouds.glesys.compute.config;

import java.security.SecureRandom;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.jclouds.compute.ComputeServiceAdapter;
import org.jclouds.compute.config.ComputeServiceAdapterContextModule;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.OsFamilyVersion64Bit;
import org.jclouds.compute.domain.TemplateBuilder;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.domain.Location;
import org.jclouds.functions.IdentityFunction;
import org.jclouds.glesys.GleSYSAsyncClient;
import org.jclouds.glesys.GleSYSClient;
import org.jclouds.glesys.compute.GleSYSComputeServiceAdapter;
import org.jclouds.glesys.compute.functions.DatacenterToLocation;
import org.jclouds.glesys.compute.functions.OSTemplateToImage;
import org.jclouds.glesys.compute.functions.ParseOsFamilyVersion64BitFromImageName;
import org.jclouds.glesys.compute.functions.ServerDetailsToNodeMetadata;
import org.jclouds.glesys.compute.options.GleSYSTemplateOptions;
import org.jclouds.glesys.domain.OSTemplate;
import org.jclouds.glesys.domain.ServerDetails;

import com.google.common.base.Function;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

/**
 * 
 * @author Adrian Cole
 */
public class GleSYSComputeServiceContextModule
         extends
         ComputeServiceAdapterContextModule<GleSYSClient, GleSYSAsyncClient, ServerDetails, Hardware, OSTemplate, String> {

   public GleSYSComputeServiceContextModule() {
      super(GleSYSClient.class, GleSYSAsyncClient.class);
   }

   @SuppressWarnings("unchecked")
   @Override
   protected void configure() {
      super.configure();
      bind(new TypeLiteral<ComputeServiceAdapter<ServerDetails, Hardware, OSTemplate, String>>() {
      }).to(GleSYSComputeServiceAdapter.class);
      bind(new TypeLiteral<Function<ServerDetails, NodeMetadata>>() {
      }).to(ServerDetailsToNodeMetadata.class);
      bind(new TypeLiteral<Function<OSTemplate, org.jclouds.compute.domain.Image>>() {
      }).to(OSTemplateToImage.class);
      bind(new TypeLiteral<Function<Hardware, Hardware>>() {
      }).to((Class) IdentityFunction.class);
      bind(new TypeLiteral<Function<String, Location>>() {
      }).to(DatacenterToLocation.class);
      bind(new TypeLiteral<Function<String, OsFamilyVersion64Bit>>() {
      }).to(ParseOsFamilyVersion64BitFromImageName.class);
      bind(TemplateOptions.class).to(GleSYSTemplateOptions.class);
      bind(String.class).annotatedWith(Names.named("PASSWORD")).toProvider(PasswordProvider.class).in(Scopes.SINGLETON);
      // to have the compute service adapter override default locations
      install(new LocationsFromComputeServiceAdapterModule<ServerDetails, Hardware, OSTemplate, String>(){});
   }

   // 128MB is perhaps too little ram
   @Override
   protected TemplateBuilder provideTemplate(Injector injector, TemplateBuilder template) {
      return template.minRam(512).osFamily(OsFamily.UBUNTU).osVersionMatches("1[10].[10][04]").os64Bit(true);
   }

   @Named("PASSWORD")
   @Singleton
   public static class PasswordProvider implements Provider<String> {
      private final SecureRandom random;

      @Inject
      protected PasswordProvider() {
         this.random = new SecureRandom();
      }

      @Override
      public String get() {
         return random.nextLong() + "";
      }
   }
}