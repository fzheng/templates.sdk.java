package com.example.sdk;

import com.github.javafaker.Faker;
import com.example.sdk.types.HttpMethod;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

/**
 * The test suite for {@link ApiEndPoints}.
 *
 * @author Feng Zheng
 * @version 1.0
 * @since 1.0
 */
public class ApiEndPointsTest {
    /**
     * Tests the constructor of {@link ApiEndPoints} is private.
     *
     * @throws NoSuchMethodException     the no such method exception
     * @throws IllegalAccessException    the illegal access exception
     * @throws InvocationTargetException the invocation target exception
     * @throws InstantiationException    the instantiation exception
     */
    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        assertTrue(Modifier.isFinal(ApiEndPoints.class.getModifiers()));
        Constructor<ApiEndPoints> constructor = ApiEndPoints.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
        assertTrue(Modifier.isFinal(ApiEndPoints.ApiEndPoint.class.getModifiers()));
        Class[] cArg = new Class[2];
        cArg[0] = String.class;
        cArg[1] = HttpMethod.class;
        Constructor<ApiEndPoints.ApiEndPoint> constructor1 = ApiEndPoints.ApiEndPoint.class.getDeclaredConstructor(cArg);
        assertTrue(Modifier.isPrivate(constructor1.getModifiers()));
        constructor1.setAccessible(true);
        Faker faker = new Faker();
        String url = faker.internet().url();
        HttpMethod httpMethod = HttpMethod.POST;
        ApiEndPoints.ApiEndPoint apiEndPoint = constructor1.newInstance(url, httpMethod);
        assertEquals(url, apiEndPoint.getUri());
        assertEquals(httpMethod, apiEndPoint.getMethod());
    }

    /**
     * Tests {@link HttpMethod}.
     */
    @Test
    public void testHTTPMethods() {
        assertEquals(HttpMethod.POST, ApiEndPoints.CREATE_ACCESS_TOKEN.getMethod());
        assertEquals(HttpMethod.POST, ApiEndPoints.CREATE_PROJECT.getMethod());
        assertEquals(HttpMethod.GET, ApiEndPoints.GET_PROJECTS.getMethod());
        assertEquals(HttpMethod.POST, ApiEndPoints.ADD_REPOS.getMethod());
        assertEquals(HttpMethod.GET, ApiEndPoints.GET_REPOS_BY_PROJECT.getMethod());
        assertEquals(HttpMethod.POST, ApiEndPoints.FIND_PROJECTS_BY_REPO_URLS.getMethod());
        assertEquals(HttpMethod.GET, ApiEndPoints.GET_REPOS.getMethod());
        assertEquals(HttpMethod.POST, ApiEndPoints.SCAN_PROJECT.getMethod());
        assertEquals(HttpMethod.GET, ApiEndPoints.GET_SCAN_BY_ID.getMethod());
        assertEquals(HttpMethod.GET, ApiEndPoints.GET_GUIDANCE_BY_ID.getMethod());
        assertEquals(HttpMethod.GET, ApiEndPoints.GET_GUIDANCES_BY_SCAN.getMethod());
        assertEquals(HttpMethod.GET, ApiEndPoints.GET_USER_INFO.getMethod());
        assertEquals(HttpMethod.GET, ApiEndPoints.GET_ORGANIZATIONS_BY_USER.getMethod());
    }
}