package com.ace.app.cms.blob;

import com.ace.app.cms.BaseTest;
import com.ace.framework.util.ExecutionContext;
import com.ace.framework.util.redis.MyjJedisCommend;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WuZhiWei
 * @since 2015-11-20 15:26:21
 */
public class RedisTest extends BaseTest {

    @Resource
    private MyjJedisCommend jedis;

    @Test

    public void testSave() {
        jedis.set("logo","http://cloud.zhang-chu.com/resource/resource/user/cloud42/2015/07/04/1435994355403836.jpg");
        System.out.println(jedis.get("logo"));
    }



}
