package com.wedoqa.qalearning.e2e;

import com.wedoqa.qalearning.e2e.tests.AddToCartTest;
import com.wedoqa.qalearning.e2e.tests.HealthCheckTest;
import com.wedoqa.qalearning.e2e.tests.LoginTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({
        HealthCheckTest.class,
        LoginTest.class,
        AddToCartTest.class
})
public class E2ETestSuite {
}
