package com.wedoqa.qalearning.restapi.generics;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AssertionProvider {

    public void assertObjectsAreEqualExcludingNullables(Object actualObject, Object expectedObject) {
        assertThat(actualObject)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedObject);
    }

}
