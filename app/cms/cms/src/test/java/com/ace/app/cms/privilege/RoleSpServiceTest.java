package com.ace.app.cms.privilege;

import com.ace.app.cms.privilege.domain.RoleSp;
import com.ace.app.cms.privilege.service.RoleSpService;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信发送服务测试类
 * @author WuZhiWei
 * @since 2015-11-16 14:01:05
 */
@ContextConfiguration(locations = {"classpath:spring/*.xml"})
public class RoleSpServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Resource
    private RoleSpService roleSpService;

   /* @BeforeClass
    public static void beforeClass() throws Exception {
        ExecutionContext.setCorpCode("wzw.com");
        ExecutionContext.setAppCode("im");
        ExecutionContext.setUserId("295bad9293004aadaafd74de98b94823");
    }*/


    @Test
//    @Rollback(false)
    public void testAdd(){

        RoleSp roleSp=new RoleSp();
        roleSp.setCorpCode("x");
        roleSpService.add(roleSp);
    }
    @Test
//    @Rollback(false)
    public void testUpdate(){

        RoleSp roleSp=new RoleSp();
        roleSp.setCorpCode("xxxx");
        roleSp.setRoleId("a064769cb537481bba8cc5001836f533");
        roleSp.setRoleSpId("a064769cb537481bba8cc5001836f533");
        roleSpService.update(roleSp);
    }

    @Test
//    @Rollback(false)
    public void testfindList(){

        RoleSp roleSp=new RoleSp();
        List<RoleSp> roleSpList=roleSpService.findList(roleSp);
        for(RoleSp rsp:roleSpList){
            System.out.println(rsp.getRoleSpId());
        }
    }
}
