package com.ribhav.tinyurl;

import com.mongodb.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * User : ribhavpahuja
 * Date : 05/11/21
 * Time : 5:16 PM
 */
@Service
public class URLServiceImpl implements URLService {

    private static final Logger LOGGER = LoggerFactory.getLogger(URLServiceImpl.class);

    private final MongoOperations mongoOps;

    public URLServiceImpl() {
        this.mongoOps = new MongoTemplate(MongoClients.create(), "keyUrl");;
    }

    @Override
    public String generateKeyForUrl(String url) {
        try {
            // 128 bits md5
            // base 64 -> can cover 6 bits in 1 char => total chars = 128/6 =~ 21 chars
            // We can take total 6 chars
            byte[] bytesOfMessage = url.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5ConvertedUrl = md.digest(bytesOfMessage);
            String keyForUrl = Base64.getEncoder().encodeToString(md5ConvertedUrl).substring(0, 6);
            // Need to store in db as well
            boolean ok = false;
            while (!ok) {
                try {
                    assignKeyToUrl(keyForUrl, url);
                    ok = true;
                } catch (Exception e) {
                    keyForUrl = Base64.getEncoder().encodeToString(md.digest(generateRandomString())).substring(0, 6);
                }
            }
            return keyForUrl;
        } catch (Exception e) {
            LOGGER.error("Unable to get key for url: " + url);
            return null;
        }
    }

    @Override
    public void assignKeyToUrl(String key, String url) {
        KeyUrlBean keyUrlBean = new KeyUrlBean();
        keyUrlBean.setKey(key);
        keyUrlBean.setUrl(url);
        keyUrlBean.setModifiedTime(System.currentTimeMillis());
        // No need to take distributed lock
        mongoOps.insert(keyUrlBean);
    }


    private byte[] generateRandomString() {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i <= 21; i++) {
            ans.append((char) ('a' + (int) (Math.random() * 26)));
        }
        try {
            return ans.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
