package redis.clients.jedis;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by gunnarkristjansson on 8/21/14.
 */
public class RedisURI extends URI {

    public RedisURI(String str) throws URISyntaxException {
        super(str);
    }

    public static RedisURI create(String str) {
        try {
            return new RedisURI(str);
        } catch (URISyntaxException x) {
            throw new IllegalArgumentException(x.getMessage(), x);
        }
    }

    public String getPassword() {
        String userInfo = getUserInfo();
        if(userInfo != null) {
            if(userInfo.contains(":")) {
                return userInfo.split(":")[1];
            }
            return userInfo;

        }
        return null;
    }

    public int getDatabase() {
        try {
            String path = getPath();
            if(path != null) {
                if(path.contains("/")) {
                    String fields[] = path.split("/");
                    if(fields.length > 3) {
                        throw new URISyntaxException("Path part of URI invalid");
                    } else if(fields.length == 2) {
                        return Integer.parseInt(fields[1]);
                    }

                }

            }
        } catch (NumberFormatException e) {
            return 0;
        }

        return 0;
    }

    public boolean isRedisURI() {
        return getScheme() != null && getScheme().equals("redis");
    }

}
