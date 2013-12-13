/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2013-11-22 19:59:01 UTC)
 * on 2013-12-13 at 08:31:55 UTC 
 * Modify at your own risk.
 */

package com.appspot.perfect_atrium_421.safezones.model;

/**
 * Model definition for SafeZone.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the safezones. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class SafeZone extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("extra_info")
  private String extraInfo;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Hours hours;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private Long id;

  /**
   * ProtoRPC container for GeoPt instances. Attributes: lat: Float; The latitude of the point. lon:
   * Float; The longitude of the point.
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private GeoPtMessage location;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("max_occupancy") @com.google.api.client.json.JsonString
  private Long maxOccupancy;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private Long occupancy;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private String phone;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private String title;

  /**
   * @return value or {@code null} for none
   */
  public String getExtraInfo() {
    return extraInfo;
  }

  /**
   * @param extraInfo extraInfo or {@code null} for none
   */
  public SafeZone setExtraInfo(String extraInfo) {
    this.extraInfo = extraInfo;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Hours getHours() {
    return hours;
  }

  /**
   * @param hours hours or {@code null} for none
   */
  public SafeZone setHours(Hours hours) {
    this.hours = hours;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public SafeZone setId(Long id) {
    this.id = id;
    return this;
  }

  /**
   * ProtoRPC container for GeoPt instances. Attributes: lat: Float; The latitude of the point. lon:
   * Float; The longitude of the point.
   * @return value or {@code null} for none
   */
  public GeoPtMessage getLocation() {
    return location;
  }

  /**
   * ProtoRPC container for GeoPt instances. Attributes: lat: Float; The latitude of the point. lon:
   * Float; The longitude of the point.
   * @param location location or {@code null} for none
   */
  public SafeZone setLocation(GeoPtMessage location) {
    this.location = location;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Long getMaxOccupancy() {
    return maxOccupancy;
  }

  /**
   * @param maxOccupancy maxOccupancy or {@code null} for none
   */
  public SafeZone setMaxOccupancy(Long maxOccupancy) {
    this.maxOccupancy = maxOccupancy;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Long getOccupancy() {
    return occupancy;
  }

  /**
   * @param occupancy occupancy or {@code null} for none
   */
  public SafeZone setOccupancy(Long occupancy) {
    this.occupancy = occupancy;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public String getPhone() {
    return phone;
  }

  /**
   * @param phone phone or {@code null} for none
   */
  public SafeZone setPhone(String phone) {
    this.phone = phone;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param title title or {@code null} for none
   */
  public SafeZone setTitle(String title) {
    this.title = title;
    return this;
  }

  @Override
  public SafeZone set(String fieldName, Object value) {
    return (SafeZone) super.set(fieldName, value);
  }

  @Override
  public SafeZone clone() {
    return (SafeZone) super.clone();
  }

}