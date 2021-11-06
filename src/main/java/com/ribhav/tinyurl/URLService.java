package com.ribhav.tinyurl;

/**
 * User : ribhavpahuja
 * Date : 05/11/21
 * Time : 5:08 PM
 */

public interface URLService {

    String generateKeyForUrl(String url);

    void assignKeyToUrl(String key, String url);
}
