package com.sky.interceptor;

import com.sky.constant.JwtClaimsConstant;
import com.sky.context.BaseContext;
import com.sky.properties.JwtProperties;
import com.sky.utils.JwtUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class JwtInterceptorTest {
    private static final String SECRET = "test-secret-key";

    public static class Controller {
        public void endpoint() { }
    }

    @AfterEach
    void clearContext() {
        BaseContext.removeCurrentId();
    }

    @Test
    void authenticatesAndClearsContextForBothClientTypes() throws Exception {
        JwtProperties properties = new JwtProperties();
        properties.setAdminSecretKey(SECRET);
        properties.setUserSecretKey(SECRET);
        properties.setAdminTokenName("token");
        properties.setUserTokenName("authentication");
        HandlerMethod handler = new HandlerMethod(new Controller(), Controller.class.getMethod("endpoint"));

        for (boolean admin : new boolean[]{true, false}) {
            HandlerInterceptor interceptor = admin ? new JwtTokenAdminInterceptor() : new JwtTokenUserInterceptor();
            ReflectionTestUtils.setField(interceptor, "jwtProperties", properties);
            String claim = admin ? JwtClaimsConstant.EMP_ID : JwtClaimsConstant.USER_ID;
            String header = admin ? "token" : "authentication";
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            request.addHeader(header, JwtUtil.createJWT(SECRET, 60000,
                    new HashMap<>(Collections.singletonMap(claim, 42L))));

            assertTrue(interceptor.preHandle(request, response, handler));
            assertEquals(42L, BaseContext.getCurrentId());
            interceptor.afterCompletion(request, response, handler, null);
            assertNull(BaseContext.getCurrentId());

            BaseContext.setCurrentId(99L);
            MockHttpServletResponse invalidResponse = new MockHttpServletResponse();
            assertFalse(interceptor.preHandle(new MockHttpServletRequest(), invalidResponse, handler));
            assertEquals(401, invalidResponse.getStatus());
            assertNull(BaseContext.getCurrentId());
        }
    }
}
