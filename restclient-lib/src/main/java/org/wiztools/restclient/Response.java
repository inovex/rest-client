package org.wiztools.restclient;

import org.wiztools.commons.MultiValueMap;

/**
 *
 * @author subwiz
 */
public interface Response extends Cloneable {

    long getExecutionTime();

    MultiValueMap<String, String> getHeaders();

    String getResponseBody();

    int getStatusCode();

    String getStatusLine();

    TestResult getTestResult();

    Object clone();
}
