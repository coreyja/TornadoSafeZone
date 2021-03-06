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

package com.appspot.perfect_atrium_421.safezones;

/**
 * Service definition for Safezones (v1).
 *
 * <p>
 * SafeZones API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link SafezonesRequestInitializer} to initialize global parameters via its
 * {@link com.appspot.perfect_atrium_421.safezones.Safezones.Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Safezones extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.17.0-rc of the safezones library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://perfect-atrium-421.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "safezones/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link com.appspot.perfect_atrium_421.safezones.Safezones.Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Safezones(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Safezones(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * An accessor for creating requests from the Hours collection.
   *
   * <p>The typical use is:</p>
   * <pre>
   *   {@code Safezones safezones = new Safezones(...);}
   *   {@code Safezones.Hours.List request = safezones.hours().list(parameters ...)}
   * </pre>
   *
   * @return the resource collection
   */
  public Hours hours() {
    return new Hours();
  }

  /**
   * The "hours" collection of methods.
   */
  public class Hours {

    /**
     * Create a request for the method "hours.delete".
     *
     * This request holds the parameters needed by the safezones server.  After setting any optional
     * parameters, call the {@link com.appspot.perfect_atrium_421.safezones.Safezones.Hours.Delete#execute()} method to invoke the remote operation.
     *
     * @param id
     * @return the request
     */
    public Delete delete(Long id) throws java.io.IOException {
      Delete result = new Delete(id);
      initialize(result);
      return result;
    }

    public class Delete extends SafezonesRequest<com.appspot.perfect_atrium_421.safezones.model.Hours> {

      private static final String REST_PATH = "hours/delete/{id}";

      /**
       * Create a request for the method "hours.delete".
       *
       * This request holds the parameters needed by the the safezones server.  After setting any
       * optional parameters, call the {@link com.appspot.perfect_atrium_421.safezones.Safezones.Hours.Delete#execute()} method to invoke the remote operation.
       * <p> {@link
       * com.appspot.perfect_atrium_421.safezones.Safezones.Hours.Delete#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
       * be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @param id
       * @since 1.13
       */
      protected Delete(Long id) {
        super(Safezones.this, "DELETE", REST_PATH, null, com.appspot.perfect_atrium_421.safezones.model.Hours.class);
        this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
      }

      @Override
      public Delete setAlt(String alt) {
        return (Delete) super.setAlt(alt);
      }

      @Override
      public Delete setFields(String fields) {
        return (Delete) super.setFields(fields);
      }

      @Override
      public Delete setKey(String key) {
        return (Delete) super.setKey(key);
      }

      @Override
      public Delete setOauthToken(String oauthToken) {
        return (Delete) super.setOauthToken(oauthToken);
      }

      @Override
      public Delete setPrettyPrint(Boolean prettyPrint) {
        return (Delete) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public Delete setQuotaUser(String quotaUser) {
        return (Delete) super.setQuotaUser(quotaUser);
      }

      @Override
      public Delete setUserIp(String userIp) {
        return (Delete) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key
      private Long id;

      /**

       */
      public Long getId() {
        return id;
      }

      public Delete setId(Long id) {
        this.id = id;
        return this;
      }

      @Override
      public Delete set(String parameterName, Object value) {
        return (Delete) super.set(parameterName, value);
      }
    }
    /**
     * Create a request for the method "hours.insert".
     *
     * This request holds the parameters needed by the safezones server.  After setting any optional
     * parameters, call the {@link com.appspot.perfect_atrium_421.safezones.Safezones.Hours.Insert#execute()} method to invoke the remote operation.
     *
     * @param content the {@link com.appspot.perfect_atrium_421.safezones.model.Hours}
     * @return the request
     */
    public Insert insert(com.appspot.perfect_atrium_421.safezones.model.Hours content) throws java.io.IOException {
      Insert result = new Insert(content);
      initialize(result);
      return result;
    }

    public class Insert extends SafezonesRequest<com.appspot.perfect_atrium_421.safezones.model.Hours> {

      private static final String REST_PATH = "hours/insert";

      /**
       * Create a request for the method "hours.insert".
       *
       * This request holds the parameters needed by the the safezones server.  After setting any
       * optional parameters, call the {@link com.appspot.perfect_atrium_421.safezones.Safezones.Hours.Insert#execute()} method to invoke the remote operation.
       * <p> {@link
       * com.appspot.perfect_atrium_421.safezones.Safezones.Hours.Insert#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
       * be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @param content the {@link com.appspot.perfect_atrium_421.safezones.model.Hours}
       * @since 1.13
       */
      protected Insert(com.appspot.perfect_atrium_421.safezones.model.Hours content) {
        super(Safezones.this, "POST", REST_PATH, content, com.appspot.perfect_atrium_421.safezones.model.Hours.class);
      }

      @Override
      public Insert setAlt(String alt) {
        return (Insert) super.setAlt(alt);
      }

      @Override
      public Insert setFields(String fields) {
        return (Insert) super.setFields(fields);
      }

      @Override
      public Insert setKey(String key) {
        return (Insert) super.setKey(key);
      }

      @Override
      public Insert setOauthToken(String oauthToken) {
        return (Insert) super.setOauthToken(oauthToken);
      }

      @Override
      public Insert setPrettyPrint(Boolean prettyPrint) {
        return (Insert) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public Insert setQuotaUser(String quotaUser) {
        return (Insert) super.setQuotaUser(quotaUser);
      }

      @Override
      public Insert setUserIp(String userIp) {
        return (Insert) super.setUserIp(userIp);
      }

      @Override
      public Insert set(String parameterName, Object value) {
        return (Insert) super.set(parameterName, value);
      }
    }
    /**
     * Create a request for the method "hours.list".
     *
     * This request holds the parameters needed by the safezones server.  After setting any optional
     * parameters, call the {@link com.appspot.perfect_atrium_421.safezones.Safezones.Hours.List#execute()} method to invoke the remote operation.
     *
     * @return the request
     */
    public List list() throws java.io.IOException {
      List result = new List();
      initialize(result);
      return result;
    }

    public class List extends SafezonesRequest<com.appspot.perfect_atrium_421.safezones.model.HoursCollection> {

      private static final String REST_PATH = "hours/list";

      /**
       * Create a request for the method "hours.list".
       *
       * This request holds the parameters needed by the the safezones server.  After setting any
       * optional parameters, call the {@link com.appspot.perfect_atrium_421.safezones.Safezones.Hours.List#execute()} method to invoke the remote operation. <p>
       * {@link com.appspot.perfect_atrium_421.safezones.Safezones.Hours.List#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
       * must be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @since 1.13
       */
      protected List() {
        super(Safezones.this, "GET", REST_PATH, null, com.appspot.perfect_atrium_421.safezones.model.HoursCollection.class);
      }

      @Override
      public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
        return super.executeUsingHead();
      }

      @Override
      public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
        return super.buildHttpRequestUsingHead();
      }

      @Override
      public List setAlt(String alt) {
        return (List) super.setAlt(alt);
      }

      @Override
      public List setFields(String fields) {
        return (List) super.setFields(fields);
      }

      @Override
      public List setKey(String key) {
        return (List) super.setKey(key);
      }

      @Override
      public List setOauthToken(String oauthToken) {
        return (List) super.setOauthToken(oauthToken);
      }

      @Override
      public List setPrettyPrint(Boolean prettyPrint) {
        return (List) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public List setQuotaUser(String quotaUser) {
        return (List) super.setQuotaUser(quotaUser);
      }

      @Override
      public List setUserIp(String userIp) {
        return (List) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key
      private String pageToken;

      /**

       */
      public String getPageToken() {
        return pageToken;
      }

      public List setPageToken(String pageToken) {
        this.pageToken = pageToken;
        return this;
      }

      @com.google.api.client.util.Key
      private Long limit;

      /**

       */
      public Long getLimit() {
        return limit;
      }

      public List setLimit(Long limit) {
        this.limit = limit;
        return this;
      }

      @com.google.api.client.util.Key
      private String order;

      /**

       */
      public String getOrder() {
        return order;
      }

      public List setOrder(String order) {
        this.order = order;
        return this;
      }

      @Override
      public List set(String parameterName, Object value) {
        return (List) super.set(parameterName, value);
      }
    }

  }

  /**
   * An accessor for creating requests from the Safezone collection.
   *
   * <p>The typical use is:</p>
   * <pre>
   *   {@code Safezones safezones = new Safezones(...);}
   *   {@code Safezones.Safezone.List request = safezones.safezone().list(parameters ...)}
   * </pre>
   *
   * @return the resource collection
   */
  public Safezone safezone() {
    return new Safezone();
  }

  /**
   * The "safezone" collection of methods.
   */
  public class Safezone {

    /**
     * Create a request for the method "safezone.delete".
     *
     * This request holds the parameters needed by the safezones server.  After setting any optional
     * parameters, call the {@link com.appspot.perfect_atrium_421.safezones.Safezones.Safezone.Delete#execute()} method to invoke the remote operation.
     *
     * @param id
     * @return the request
     */
    public Delete delete(Long id) throws java.io.IOException {
      Delete result = new Delete(id);
      initialize(result);
      return result;
    }

    public class Delete extends SafezonesRequest<com.appspot.perfect_atrium_421.safezones.model.SafeZone> {

      private static final String REST_PATH = "safezone/delete/{id}";

      /**
       * Create a request for the method "safezone.delete".
       *
       * This request holds the parameters needed by the the safezones server.  After setting any
       * optional parameters, call the {@link com.appspot.perfect_atrium_421.safezones.Safezones.Safezone.Delete#execute()} method to invoke the remote operation.
       * <p> {@link
       * com.appspot.perfect_atrium_421.safezones.Safezones.Safezone.Delete#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
       * be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @param id
       * @since 1.13
       */
      protected Delete(Long id) {
        super(Safezones.this, "DELETE", REST_PATH, null, com.appspot.perfect_atrium_421.safezones.model.SafeZone.class);
        this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
      }

      @Override
      public Delete setAlt(String alt) {
        return (Delete) super.setAlt(alt);
      }

      @Override
      public Delete setFields(String fields) {
        return (Delete) super.setFields(fields);
      }

      @Override
      public Delete setKey(String key) {
        return (Delete) super.setKey(key);
      }

      @Override
      public Delete setOauthToken(String oauthToken) {
        return (Delete) super.setOauthToken(oauthToken);
      }

      @Override
      public Delete setPrettyPrint(Boolean prettyPrint) {
        return (Delete) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public Delete setQuotaUser(String quotaUser) {
        return (Delete) super.setQuotaUser(quotaUser);
      }

      @Override
      public Delete setUserIp(String userIp) {
        return (Delete) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key
      private Long id;

      /**

       */
      public Long getId() {
        return id;
      }

      public Delete setId(Long id) {
        this.id = id;
        return this;
      }

      @Override
      public Delete set(String parameterName, Object value) {
        return (Delete) super.set(parameterName, value);
      }
    }
    /**
     * Create a request for the method "safezone.insert".
     *
     * This request holds the parameters needed by the safezones server.  After setting any optional
     * parameters, call the {@link com.appspot.perfect_atrium_421.safezones.Safezones.Safezone.Insert#execute()} method to invoke the remote operation.
     *
     * @param content the {@link com.appspot.perfect_atrium_421.safezones.model.SafeZone}
     * @return the request
     */
    public Insert insert(com.appspot.perfect_atrium_421.safezones.model.SafeZone content) throws java.io.IOException {
      Insert result = new Insert(content);
      initialize(result);
      return result;
    }

    public class Insert extends SafezonesRequest<com.appspot.perfect_atrium_421.safezones.model.SafeZone> {

      private static final String REST_PATH = "safezone/insert";

      /**
       * Create a request for the method "safezone.insert".
       *
       * This request holds the parameters needed by the the safezones server.  After setting any
       * optional parameters, call the {@link com.appspot.perfect_atrium_421.safezones.Safezones.Safezone.Insert#execute()} method to invoke the remote operation.
       * <p> {@link
       * com.appspot.perfect_atrium_421.safezones.Safezones.Safezone.Insert#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
       * be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @param content the {@link com.appspot.perfect_atrium_421.safezones.model.SafeZone}
       * @since 1.13
       */
      protected Insert(com.appspot.perfect_atrium_421.safezones.model.SafeZone content) {
        super(Safezones.this, "POST", REST_PATH, content, com.appspot.perfect_atrium_421.safezones.model.SafeZone.class);
      }

      @Override
      public Insert setAlt(String alt) {
        return (Insert) super.setAlt(alt);
      }

      @Override
      public Insert setFields(String fields) {
        return (Insert) super.setFields(fields);
      }

      @Override
      public Insert setKey(String key) {
        return (Insert) super.setKey(key);
      }

      @Override
      public Insert setOauthToken(String oauthToken) {
        return (Insert) super.setOauthToken(oauthToken);
      }

      @Override
      public Insert setPrettyPrint(Boolean prettyPrint) {
        return (Insert) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public Insert setQuotaUser(String quotaUser) {
        return (Insert) super.setQuotaUser(quotaUser);
      }

      @Override
      public Insert setUserIp(String userIp) {
        return (Insert) super.setUserIp(userIp);
      }

      @Override
      public Insert set(String parameterName, Object value) {
        return (Insert) super.set(parameterName, value);
      }
    }
    /**
     * Create a request for the method "safezone.list".
     *
     * This request holds the parameters needed by the safezones server.  After setting any optional
     * parameters, call the {@link com.appspot.perfect_atrium_421.safezones.Safezones.Safezone.List#execute()} method to invoke the remote operation.
     *
     * @return the request
     */
    public List list() throws java.io.IOException {
      List result = new List();
      initialize(result);
      return result;
    }

    public class List extends SafezonesRequest<com.appspot.perfect_atrium_421.safezones.model.SafeZoneCollection> {

      private static final String REST_PATH = "safezone/list";

      /**
       * Create a request for the method "safezone.list".
       *
       * This request holds the parameters needed by the the safezones server.  After setting any
       * optional parameters, call the {@link com.appspot.perfect_atrium_421.safezones.Safezones.Safezone.List#execute()} method to invoke the remote operation. <p>
       * {@link com.appspot.perfect_atrium_421.safezones.Safezones.Safezone.List#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
       * must be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @since 1.13
       */
      protected List() {
        super(Safezones.this, "GET", REST_PATH, null, com.appspot.perfect_atrium_421.safezones.model.SafeZoneCollection.class);
      }

      @Override
      public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
        return super.executeUsingHead();
      }

      @Override
      public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
        return super.buildHttpRequestUsingHead();
      }

      @Override
      public List setAlt(String alt) {
        return (List) super.setAlt(alt);
      }

      @Override
      public List setFields(String fields) {
        return (List) super.setFields(fields);
      }

      @Override
      public List setKey(String key) {
        return (List) super.setKey(key);
      }

      @Override
      public List setOauthToken(String oauthToken) {
        return (List) super.setOauthToken(oauthToken);
      }

      @Override
      public List setPrettyPrint(Boolean prettyPrint) {
        return (List) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public List setQuotaUser(String quotaUser) {
        return (List) super.setQuotaUser(quotaUser);
      }

      @Override
      public List setUserIp(String userIp) {
        return (List) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key
      private String pageToken;

      /**

       */
      public String getPageToken() {
        return pageToken;
      }

      public List setPageToken(String pageToken) {
        this.pageToken = pageToken;
        return this;
      }

      @com.google.api.client.util.Key
      private Long limit;

      /**

       */
      public Long getLimit() {
        return limit;
      }

      public List setLimit(Long limit) {
        this.limit = limit;
        return this;
      }

      @com.google.api.client.util.Key
      private String order;

      /**

       */
      public String getOrder() {
        return order;
      }

      public List setOrder(String order) {
        this.order = order;
        return this;
      }

      @Override
      public List set(String parameterName, Object value) {
        return (List) super.set(parameterName, value);
      }
    }

  }

  /**
   * Builder for {@link com.appspot.perfect_atrium_421.safezones.Safezones}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link com.appspot.perfect_atrium_421.safezones.Safezones}. */
    @Override
    public Safezones build() {
      return new Safezones(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link SafezonesRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setSafezonesRequestInitializer(
        SafezonesRequestInitializer safezonesRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(safezonesRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
