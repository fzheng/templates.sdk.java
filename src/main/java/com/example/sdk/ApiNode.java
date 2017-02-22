package com.example.sdk;

import com.google.gson.*;
import com.example.sdk.ApiException.MalformedResponseException;

import java.lang.reflect.Modifier;
import java.util.Date;

/**
 * Abstract class that all entities will inherit from.
 * It defines the logic of how to parse API response into entities.
 *
 * @author Feng Zheng
 * @version 1.0
 * @since 1.0
 */
public abstract class ApiNode implements ApiResponse {
  /**
   * The constant gson.
   */
  private static Gson gson = null;

  /**
   * The API context.
   */
  private transient ApiContext context = null;

  /**
   * The raw value of the API response.
   */
  private transient String rawValue = null;

  /**
   * Gets the entity id.
   *
   * @return the entity id
   */
  public abstract String getId();

  /**
   * Gets the API context of the {@link ApiNode}.
   *
   * @return the instance of {@link ApiContext}
   */
  public ApiContext getContext() {
    return this.context;
  }

  @Override
  public String getRawResponse() {
    return rawValue;
  }

  @Override
  public JsonObject getRawResponseAsJsonObject() {
    JsonParser parser = new JsonParser();
    return parser.parse(rawValue).getAsJsonObject();
  }

  @Override
  public ApiNode head() {
    return this;
  }

  /**
   * Sets the API context for the entity.
   *
   * @param context the API context
   */
  public void setContext(ApiContext context) {
    this.context = context;
  }

  /**
   * Sets the raw value for the {@link ApiNode}.
   *
   * @param value raw value in string
   */
  public void setRawValue(String value) {
    this.rawValue = value;
  }

  /**
   * Serialize the entity into its JSON representation.
   *
   * @return the entity's JSON representation
   */
  public String toString() {
    return getGson().toJson(this);
  }

  /**
   * Clones a date object.
   *
   * @param date source date object
   * @return cloned date object
   */
  Date cloneDate(Date date) {
    return (date == null) ? null : new Date(date.getTime());
  }

  /**
   * Parse the API response into a list of API entities.
   *
   * @param <T>          the type of {@link ApiNode} or its subclass
   * @param cls          the class of {@link ApiNode} or its subclass
   * @param secondaryKey the secondary data key in response JSON
   * @param json         the response JSON string
   * @param context      the API context from API request
   * @param request      the API request
   * @return the list of API entities
   * @throws MalformedResponseException the malformed response exception throws
   *                                    if the response is not a valid JSON
   */
  @SuppressWarnings("unchecked")
  protected static <T extends ApiNode> ApiNodeList<T> parseResponse(Class<T> cls,
                                                                    String secondaryKey,
                                                                    String json,
                                                                    ApiContext context,
                                                                    ApiRequest request)
      throws MalformedResponseException {
    ApiNodeList<T> nodes = new ApiNodeList<>(request, json);
    JsonArray array;
    JsonObject object;
    JsonParser parser = new JsonParser();
    try {
      JsonElement result = parser.parse(json);
      if (result.isJsonArray()) {
        // 1. check if it's a pure JSON Array
        array = result.getAsJsonArray();
        for (JsonElement element : array) {
          nodes.add((T) loadJson(cls, element.getAsJsonObject().toString(), context));
        }
      } else if (result.isJsonObject()) {
        object = result.getAsJsonObject();
        // 2. check if there is a meta key
        if (object.has(ApiConfig.PRIMARY_META_KEY)) {
          JsonObject objMeta = object.get(ApiConfig.PRIMARY_META_KEY).getAsJsonObject();
          if (objMeta != null && objMeta.has(ApiConfig.PAGINATION_KEY)) {
            JsonObject objPage = objMeta.get(ApiConfig.PAGINATION_KEY).getAsJsonObject();
            Pagination p = (Pagination) loadJson(Pagination.class, objPage.toString(), context);
            nodes.setPagination(p);
          }
        }
        // 3. check if there is a primary data key
        if (object.has(ApiConfig.PRIMARY_DATA_KEY)) {
          JsonElement objData = object.get(ApiConfig.PRIMARY_DATA_KEY);
          // Third, check if there is a secondary data key defined
          if (secondaryKey != null) {
            if (objData.getAsJsonObject().has(secondaryKey)) {
              objData = objData.getAsJsonObject().get(secondaryKey);
            } else {
              return nodes;
            }
          }
          if (objData.isJsonArray()) {
            // 4. check if it's a JSON array
            array = objData.getAsJsonArray();
            for (JsonElement element : array) {
              nodes.add((T) loadJson(cls, element.getAsJsonObject().toString(), context));
            }
          } else if (objData.isJsonObject()) {
            // 5. check if it's a JSON object
            object = objData.getAsJsonObject();
            nodes.add((T) loadJson(cls, object.toString(), context));
          }
          return nodes;
        }
        // 6. clear nodes if there is no primary data key
        nodes.clear();
        nodes.add((T) loadJson(cls, json, context));
      }
    } catch (Exception e) {
      throw new MalformedResponseException("Invalid response string: " + json, e);
    }
    return nodes;
  }

  /**
   * Parse the API response into a list of API entities, without a secondary data key.
   *
   * @param <T>     the type of {@link ApiNode} or its subclass
   * @param cls     the class of {@link ApiNode} or its subclass
   * @param json    the response JSON string
   * @param context the API context from API request
   * @param request the API request
   * @return the list of API entities
   * @throws MalformedResponseException the malformed response exception
   */
  protected static <T extends ApiNode> ApiNodeList<T> parseResponse(Class<T> cls,
                                                                    String json,
                                                                    ApiContext context,
                                                                    ApiRequest request)
      throws MalformedResponseException {
    return parseResponse(cls, null, json, context, request);
  }

  /**
   * Deserializes a JSON string into an equivalent entity.
   *
   * @param cls     the class of {@link ApiNode} or its subclass
   * @param json    the JSON string from API response
   * @param context the API context from API request
   * @param <T>     the type of {@link ApiNode} or its subclass
   * @return the equivalent entity
   */
  private static <T extends ApiNode> ApiNode loadJson(Class<T> cls,
                                                      String json,
                                                      ApiContext context) {
    T node = getGson().fromJson(json, cls);
    node.setContext(context);
    node.setRawValue(json);
    if (node instanceof App) {
      context.setAppToken(((App) node).getToken());
      ((App) node).setId(context.getAppId());
    }
    return node;
  }

  /**
   * Gets the Gson instance for entities serialization and deserialization.
   * Gson is a Java library that can be used to convert a Java object into its JSON representation.
   * It can also be used to convert a JSON string into an equivalent Java object.
   * <p>See the <a href="https://sites.google.com/site/gson/gson-user-guide" target="_blank">Gson User Guide</a> for more examples</p>
   *
   * @return the gson instance
   */
  private static synchronized Gson getGson() {
    if (gson == null) {
      gson = new GsonBuilder()
          .excludeFieldsWithModifiers(
              Modifier.FINAL,
              Modifier.TRANSIENT,
              Modifier.PROTECTED,
              Modifier.STATIC
          )
          .serializeNulls()
          .disableHtmlEscaping()
          .setDateFormat(ApiConfig.DATE_FORMAT)
          .create();
    }
    return gson;
  }
}
