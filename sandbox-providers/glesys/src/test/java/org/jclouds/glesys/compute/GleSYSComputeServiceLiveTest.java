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
package org.jclouds.glesys.compute;

import org.jclouds.compute.BaseComputeServiceLiveTest;
import org.jclouds.compute.ComputeServiceContextFactory;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.glesys.GleSYSAsyncClient;
import org.jclouds.glesys.GleSYSClient;
import org.jclouds.rest.RestContext;
import org.jclouds.sshj.config.SshjSshClientModule;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Module;

/**
 * 
 * TODO: enable this
 * 
 * @author Adrian Cole
 */
@Test(groups = "live", enabled = true, singleThreaded = true)
public class GleSYSComputeServiceLiveTest extends BaseComputeServiceLiveTest {
   public GleSYSComputeServiceLiveTest() {
      provider = "glesys";
   }

   @Override
   protected Module getSshModule() {
      return new SshjSshClientModule();
   }

   public void testAssignability() throws Exception {
      @SuppressWarnings("unused")
      RestContext<GleSYSClient, GleSYSAsyncClient> tmContext = new ComputeServiceContextFactory()
            .createContext(provider, identity, credential).getProviderSpecificContext();
   }
   
   // GleSYS does not support metadata
   @Override
   protected void checkUserMetadataInNodeEquals(NodeMetadata node, ImmutableMap<String, String> userMetadata) {
   }
}
