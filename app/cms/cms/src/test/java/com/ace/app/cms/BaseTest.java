package com.ace.app.cms;

import com.ace.framework.util.ExecutionContext;
import org.junit.BeforeClass;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * @author WuZhiWei
 * @since 2015-11-19 10:16:21
 */
@ContextConfiguration(locations = {"classpath:spring/*.xml"})
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {

    @BeforeClass
    public static void beforeClass() throws Exception {
        ExecutionContext.setCorpCode("wzw.com");
        ExecutionContext.setAppCode("cms");
        ExecutionContext.setUserId("295bad9293004aadaafd74de98b94823");
    }
}
