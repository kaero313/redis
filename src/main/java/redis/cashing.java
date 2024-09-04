package redis;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Controller
@RequestMapping("/redis")
public class cashing {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Value("${redis.masterName}")
    private String redisMasterName;

    @Value("${redis.password}")
    private String redisPassword;

    private static JedisPoolConfig jedisPoolConfig = null;
    private static JedisPool pool = null;

    private void connect() {

        if(jedisPoolConfig == null){
            jedisPoolConfig = new JedisPoolConfig();
        }

        if(pool == null){
            pool = new JedisPool(jedisPoolConfig, redisHost, redisPort, 3000, redisPassword, 5);
        }
    }

    @RequestMapping(value = "add", method = {RequestMethod.POST})
    public void add(@RequestBody String request){

        connect();
        Jedis jedis = pool.getResource();

        JSONObject jsonobj = new JSONObject(request);

        String redisKey = jsonobj.getString("key");

        JSONObject json = new JSONObject();
        json.put("redirect_uri", jsonobj.get("redirect_uri"));
        json.put("id", jsonobj.get("id"));
        json.put("pw", jsonobj.get("pw"));
        json.put("name", jsonobj.get("name"));

        jedis.set(redisKey, String.valueOf(json));

        if (jedis != null) {
            jedis.close();
        }

        pool.close();

    }


}
