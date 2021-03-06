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
package org.jclouds.aws.s3.blobstore.strategy;

import org.jclouds.aws.s3.blobstore.strategy.internal.SequentialMultipartUploadStrategy;
import org.jclouds.blobstore.domain.Blob;

import com.google.inject.ImplementedBy;
import org.jclouds.blobstore.options.PutOptions;

/**
 * @see <a href="http://docs.amazonwebservices.com/AmazonS3/latest/dev/index.html?qfacts.html">AWS Documentation</a>
 *
 * @author Tibor Kiss
 */
@ImplementedBy(SequentialMultipartUploadStrategy.class)
public interface MultipartUploadStrategy extends MultipartUpload {
   
   String execute(String container, Blob blob, PutOptions options);
}
