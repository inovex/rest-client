package org.wiztools.restclient;

import java.net.URL;
import java.util.List;
import org.wiztools.commons.MultiValueMap;

/**
 *
 * @author subwiz
 */
public interface Request extends Cloneable {

    String getAuthHost();

    List<HTTPAuthMethod> getAuthMethods();

    char[] getAuthPassword();

    String getAuthRealm();

    String getAuthUsername();

    ReqEntity getBody();

    MultiValueMap<String, String> getHeaders();

    HTTPVersion getHttpVersion();

    HTTPMethod getMethod();

    SSLHostnameVerifier getSslHostNameVerifier();

    String getSslTrustStore();

    char[] getSslTrustStorePassword();

    String getTestScript();

    URL getUrl();

    boolean isAuthPreemptive();

    boolean isFollowRedirect();

    Object clone();
}
