package com.ace.app.cms.privilege;

import com.ace.app.cms.privilege.domain.Menu;
import com.ace.app.cms.privilege.domain.RoleSp;
import com.ace.app.cms.privilege.service.MenuService;
import com.ace.app.cms.privilege.service.RoleSpService;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.annotation.Resource;
import java.util.List;

/**
 * 短信发送服务测试类
 * @author WuZhiWei
 * @since 2015-11-16 14:01:05
 */
@ContextConfiguration(locations = {"classpath:spring/*.xml"})
public class MenuTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Resource
    private MenuService menuService;

   /* @BeforeClass
    public static void beforeClass() throws Exception {
        ExecutionContext.setCorpCode("wzw.com");
        ExecutionContext.setAppCode("im");
        ExecutionContext.setUserId("295bad9293004aadaafd74de98b94823");
    }*/

    @Test
    @Rollback(false)
    public void  save(){
        Menu menu;

        menu=new Menu();
        menu.setSeq(0);
        menu.setTitle("清算中心");
        menu.setIcon("menu-icon fa fa-file-o");
        menu.setUrl("");
        menuService.save(menu);
    }
    @Test
    @Rollback(false)
    public void  saveSub(){
        Menu menu=new Menu();
        menu.setParentId("884c2a8553d641f8ba55399195274c35");

        menu.setSeq(1);
        menu.setTitle("提现管理");
        menu.setIcon("");
        menu.setUrl("cashbackWithdraw.index.do");
        menuService.save(menu);



    }

    @Test
    @Rollback(false)
    public void  saveSub2(){
        String pid = "82f556bd5326481fbf2d65426ab5455f";

        Menu menu=new Menu();
        menu.setParentId(pid);

        menu.setSeq(0);
        menu.setTitle("查看");
        menu.setIcon("");
        menu.setUrl("");
        menu.setPrivilege("view");
        String module = "settlement";
        menu.setModule(module);

        menuService.save(menu);




        menu=new Menu();
        menu.setParentId(pid);
        menu.setSeq(0);
        menu.setTitle("添加");
        menu.setIcon("");

        menu.setPrivilege("add");
        menu.setModule(module);
        menuService.save(menu);

        menu=new Menu();
        menu.setParentId(pid);
        menu.setSeq(0);
        menu.setTitle("编辑");
        menu.setIcon("");

        menu.setPrivilege("edit");
        menu.setModule(module);
        menuService.save(menu);

    }


    @Test
    public void  saveSub3(){
        Menu menu=new Menu();
        menu.setParentId("14c223ae480e46dabd3dc5213077aff6");

        menu.setSeq(0);
        menu.setTitle("查看");
        menu.setIcon("");
        menu.setUrl("");
        menu.setPrivilege("view");
        menu.setModule("goodsBrand");
        menuService.save(menu);


        menu=new Menu();
        menu.setParentId("14c223ae480e46dabd3dc5213077aff6");

        menu.setSeq(0);
        menu.setTitle("编辑");
        menu.setIcon("");
        menu.setUrl("");
        menu.setPrivilege("update");
        menu.setModule("goodsBrand");
        menuService.save(menu);

        menu=new Menu();
        menu.setParentId("14c223ae480e46dabd3dc5213077aff6");

        menu.setSeq(0);
        menu.setTitle("删除");
        menu.setIcon("");
        menu.setUrl("");
        menu.setPrivilege("delete");
        menu.setModule("custom");
        menuService.save(menu);

        menu=new Menu();
        menu.setParentId("14c223ae480e46dabd3dc5213077aff6");

        menu.setSeq(0);
        menu.setTitle("添加");
        menu.setIcon("");
        menu.setUrl("");
        menu.setPrivilege("add");
        menu.setModule("custom");
        menuService.save(menu);
    }


    @Test
    @Rollback(false)
    public void  saveSub4(){
        Menu menu=new Menu();
        menu.setParentId("c8165cc8c4244949968f2c236f5e554v");

        menu.setSeq(0);
        menu.setTitle("查看");
//        menu.setIcon("menu-icon fa fa-list");
//        menu.setUrl("pro.goods.do");
        menu.setPrivilege("view");
        menu.setModule("goodsBrand");
        menuService.save(menu);


        menu=new Menu();
        menu.setParentId("c8165cc8c4244949968f2c236f5e554v");

        menu.setSeq(0);
        menu.setTitle("推荐");
        menu.setPrivilege("commend");
        menu.setModule("goodsBrand");
        menuService.save(menu);

        menu=new Menu();
        menu.setParentId("c8165cc8c4244949968f2c236f5e554v");
        menu.setSeq(0);
        menu.setTitle("添加");
        menu.setPrivilege("add");
        menu.setModule("goodsBrand");
        menuService.save(menu);

        menu=new Menu();
        menu.setParentId("c8165cc8c4244949968f2c236f5e554v");
        menu.setSeq(0);
        menu.setTitle("编辑");
        menu.setPrivilege("update");
        menu.setModule("goodsBrand");
        menuService.save(menu);

       /* menu=new Menu();
        menu.setParentId("c8165cc8c4244949968f2c236f5e554v");
        menu.setSeq(0);
        menu.setTitle("上架&下架");
        menu.setPrivilege("delete");
        menu.setModule("goodsBrand");

        menuService.save(menu);*/

    }

    @Test
    @Rollback(false)
    public void  save2() {
        Menu menu = new Menu();
        menu.setSeq(95);
        menu.setTitle("活动管理");
        menu.setIcon("menu-icon fa fa-list-alt");
        menu.setUrl("");
        menuService.save(menu);
    }


    @Test
    @Rollback(false)
    public void  saveSub5(){
        Menu menu=new Menu();
        menu.setParentId("5a91e8df21e5474387df8d9a8495f041");

        menu.setSeq(0);
        menu.setTitle("抽奖活动");

        menuService.save(menu);

    }

    @Test
    @Rollback(false)
    public void  saveSub6(){
        Menu menu=new Menu();
        menu.setParentId("5a91e8df21e5474387df8d9a8495f041");

        menu.setSeq(0);
        menu.setTitle("奖项管理");

        menuService.save(menu);

    }

    @Test
    @Rollback(false)
    public void  saveSub311(){
        Menu menu=new Menu();
        menu.setParentId("00d3090dade84a92921eaf2bfce717aa");

        menu.setSeq(0);
        menu.setTitle("设置首页显示");
        menu.setIcon("");
        menu.setUrl("");
        menu.setPrivilege("sethome");
        menu.setModule("lottery");
        menuService.save(menu);
    }

    @Test
    @Rollback(false)
    public void  saveSub31(){
        Menu menu=new Menu();
        menu.setParentId("00d3090dade84a92921eaf2bfce717aa");

        menu.setSeq(0);
        menu.setTitle("查看");
        menu.setIcon("");
        menu.setUrl("");
        menu.setPrivilege("view");
        menu.setModule("lottery");
        menuService.save(menu);


        menu=new Menu();
        menu.setParentId("00d3090dade84a92921eaf2bfce717aa");

        menu.setSeq(0);
        menu.setTitle("添加");
        menu.setIcon("");
        menu.setUrl("");
        menu.setPrivilege("update");
        menu.setModule("lottery");
        menuService.save(menu);

        menu=new Menu();
        menu.setParentId("00d3090dade84a92921eaf2bfce717aa");

        menu.setSeq(0);
        menu.setTitle("删除");
        menu.setIcon("");
        menu.setUrl("");
        menu.setPrivilege("delete");
        menu.setModule("lottery");
        menuService.save(menu);


    }

    @Test
    @Rollback(false)
    public void  saveSub32() {
        Menu menu = new Menu();
        menu.setParentId("00d3090dade84a92921eaf2bfce717aa");

        menu.setSeq(0);
        menu.setTitle("启用&禁用");
        menu.setIcon("");
        menu.setUrl("");
        menu.setPrivilege("changestatus");
        menu.setModule("lottery");
        menuService.save(menu);
    }

    @Test
    @Rollback(false)
    public void  saveSub37() {
        Menu menu = new Menu();
        menu.setParentId("00d3090dade84a92921eaf2bfce717aa");

        menu.setSeq(0);
        menu.setTitle("兑奖");
        menu.setIcon("");
        menu.setUrl("");
        menu.setPrivilege("cashAwards");
        menu.setModule("lottery");
        menuService.save(menu);
    }

    @Test
    @Rollback(false)
    public void  saveSub33() {
        Menu menu = new Menu();
        menu.setParentId("00d3090dade84a92921eaf2bfce717aa");

        menu.setSeq(0);
        menu.setTitle("奖项管理");
        menu.setIcon("");
        menu.setUrl("");
        menu.setPrivilege("setawards");
        menu.setModule("lottery");
        menuService.save(menu);
    }

    @Test
    @Rollback(false)
    public void  saveSub331() {
        Menu menu = new Menu();
        menu.setParentId("00d3090dade84a92921eaf2bfce717aa");

        menu.setSeq(0);
        menu.setTitle("中奖信息");
        menu.setIcon("");
        menu.setUrl("");
        menu.setPrivilege("showwinrecord");
        menu.setModule("lottery");
        menuService.save(menu);
    }

    @Test
    @Rollback(false)
    public void  saveSub35() {
        /*Menu menu = new Menu();
        menu.setParentId("00d3090dade84a92921eaf2bfce717aa");

        menu.setSeq(0);
        menu.setTitle("添加奖项");
        menu.setIcon("");
        menu.setUrl("");
        menu.setPrivilege("addawards");
        menu.setModule("lottery");
        menuService.save(menu);*/

       /* Menu menu = new Menu();
        menu.setParentId("00d3090dade84a92921eaf2bfce717aa");

        menu.setSeq(0);
        menu.setTitle("修改奖项");
        menu.setIcon("");
        menu.setUrl("");
        menu.setPrivilege("editawards");
        menu.setModule("lottery");
        menuService.save(menu);*/

        Menu menu = new Menu();
        menu.setParentId("00d3090dade84a92921eaf2bfce717aa");

        menu.setSeq(0);
        menu.setTitle("删除奖项");
        menu.setIcon("");
        menu.setUrl("");
        menu.setPrivilege("deleteawards");
        menu.setModule("lottery");
        menuService.save(menu);
    }

    @Test
    @Rollback(false)
    public void  saveSub34() {
        Menu menu = new Menu();
        menu.setParentId("00d3090dade84a92921eaf2bfce717aa");

        menu.setSeq(0);
        menu.setTitle("编辑");
        menu.setIcon("");
        menu.setUrl("");
        menu.setPrivilege("update");
        menu.setModule("lottery");
        menuService.save(menu);
    }


}
