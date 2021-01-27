package com.wedoqa.qalearning.e2e;

import com.wedoqa.qalearning.e2e.tests.AddToCart;
import com.wedoqa.qalearning.e2e.tests.HealthCheck;
import com.wedoqa.qalearning.e2e.tests.Login;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({
        HealthCheck.class,
        Login.class,
        AddToCart.class
})
public class E2ETestSuite {
}
