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
package org.jclouds.vcloud.director.v1_5.domain;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

/**
 * A link.
 *
 * <pre>
 * &lt;xs:complexType name="LinkType"&gt;
 * </pre>
 *
 * @author Adrian Cole
 */
public class Link extends ReferenceType<Link> {

   @SuppressWarnings("unchecked")
   public static Builder builder() {
      return new Builder();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Builder toBuilder() {
      return new Builder().fromLink(this);
   }

   public static class Builder extends ReferenceType.Builder<Link> {

      protected String rel;

      /**
       * @see Link#getRel()
       */
      public Builder rel(String rel) {
         this.rel = rel;
         return this;
      }

      @Override
      public Link build() {
         Link link = new Link(href, rel);
         link.setId(id);
         link.setName(name);
         link.setType(type);
         return link;
      }

      /**
       * @see ReferenceType#getHref()
       */
      @Override
      public Builder href(URI href) {
         this.href = href;
         return this;
      }

      /**
       * @see ReferenceType#getId()
       */
      @Override
      public Builder id(String id) {
         this.id = id;
         return this;
      }

      /**
       * @see ReferenceType#getType()
       */
      @Override
      public Builder type(String type) {
         this.type = type;
         return this;
      }

      /**
       * @see ReferenceType#getName()
       */
      @Override
      public Builder name(String name) {
         this.name = name;
         return this;
      }

      public Builder fromLink(Link in) {
         return fromReferenceType(in).rel(in.getRel());
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public Builder fromReferenceType(ReferenceType<Link> in) {
         return Builder.class.cast(super.fromReferenceType(in));
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public Builder fromAttributes(Map<String, String> attributes) {
         super.fromAttributes(attributes);
         rel(attributes.get("rel"));
         return this;
      }
   }

   @XmlAttribute
   private String rel;

   private Link(URI href, String rel) {
      super(href);
      this.rel = checkNotNull(rel, "rel");
   }

   private Link() {
      // For JAXB
   }

   /**
    * Defines the relationship of the link to the object that contains it. A relationship can be the
    * name of an operation on the object, a reference to a contained or containing object, or a
    * reference to an alternate representation of the object. The relationship value implies the
    * HTTP verb to use when you use the link's href value as a request URL.
    * 
    * @return relationship of the link to the object that contains it.
    */
   public String getRel() {
      return rel;
   }

   @Override
   public boolean equals(Object o) {
      if (!super.equals(o))
         return false;
      Link that = (Link) o;
      return super.equals(that) && equal(this.rel, that.rel);
   }

   @Override
   public int hashCode() {
      return super.hashCode() + Objects.hashCode(rel);
   }

   @Override
   public ToStringHelper string() {
      return super.string().add("rel", rel);
   }
}