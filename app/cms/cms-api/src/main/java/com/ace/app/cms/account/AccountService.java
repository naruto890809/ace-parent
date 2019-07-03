package com.ace.app.cms.account;

import com.ace.framework.base.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 账户接口
 * @author WuZhiWei
 * @since 2015-11-20 14:18:21
 */
public interface AccountService {
    /**
     * 保存商家基本信息;当{@link com.ace.app.cms.account.Account#roleCodes 不为空时，保存accountRole信息}
     *
     * @param account 账户基本信息
     * @return accountId
     * @throws java.lang.IllegalArgumentException 当account为空时
     */
    String save(Account account);

    /**
     * 登录
     * @param corpCode 商家编号
     * @param accountName 账户名称
     * @return Account
     */
    Account getByCorpCodeAndAccountName(String corpCode ,String accountName);

    /**
     * 商家用户管理分页方法
     * @param condition 用户信息查询条件，此参数为null时，表示无查询条件，可能用到的查询条件：
     *                  <ul>
     *                  <li>{@link com.ace.app.cms.account.Account#employeeCode 用户编号}</li>
     *                  <li>{@link com.ace.app.cms.account.Account#accountName 账户名称}</li>
     *                  <li>{@link com.ace.app.cms.account.Account#accountStatus 账户状态}</li>
     *                  <li>{@link com.ace.app.cms.account.Account#phone 手机号}</li>
     *                  <li>{@link com.ace.app.cms.account.Account#roleCode 角色编号}</li>
     *                  </ul>
     * @param page      分页对象{@link com.ace.framework.base.Page}，此参数不可以为空。用到的属性：
     *                     <ul>
     *                     <li>页码{@link com.ace.framework.base.Page#getPageNo()}，为当前的分页页码，默认为1。</li>
     *                     <li>每页记录数{@link com.ace.framework.base.Page#getPageSize()}，可由前台设置，默认为10。</li>
     *                     <ul/>
     * @return Page<Account> page   Account包含属性有：
     *                  <ul>
     *                   <li>{@link com.ace.app.cms.account.Account#employeeCode 用户编号}</li>
     *                  <li>{@link com.ace.app.cms.account.Account#accountName 账户名称}</li>
     *                  <li>{@link com.ace.app.cms.account.Account#accountStatus 账户状态}</li>
     *                  <li>{@link com.ace.app.cms.account.Account#phone 手机号}</li>
     *                  <li>{@link com.ace.app.cms.account.Account#roleCode 角色编号}</li>
     *                  </ul>
     * @throws IllegalArgumentException <ul>
     *                                  <li>当分页对象{@link com.ace.framework.base.Page}为null时</li>
     *                                  <li>当分页对象的页码{@link com.ace.framework.base.Page#getPageNo()}小于1时</li>
     *                                  <li>当分页对象的每页记录数{@link com.ace.framework.base.Page#getPageSize()}小于1时</li>
     *                                  </ul>
     * @since 2015-12-07 10:59:12 WuZhiWei
     */
    Page<Account> search(Account condition , Page<Account> page);

    /**
     * 根据条件查询
     * @param account 不可为空
     * @return 一组符合条件的Account，包含全部持久化属性
     * @exception java.lang.IllegalArgumentException 当account为空时
     */
    int findTotalByCondition(Account account);

    /**
     * 根据id获取一个账户信息
     * @param accountId 账户id
     * @param corpCode 商家编号
     * @return Account 包含全部持久化属性，密码为解密后的名文
     * @exception java.lang.IllegalArgumentException 当accountId、corpCode任意为空时
     */
    Account getById(String accountId,String corpCode);
    /**
     * 根据条件查询一组账户信息
     * @param account 查询条件（包含全部持久化属性及accountIds）
     * @return List<Account> 包含全部持久化属性
     * @exception java.lang.IllegalArgumentException 当account任意为空时
     */
    List<Account> listByCondition(Account account);

    /**
     * 根据条件更新不为空的字段
     * @param account 账户信息，不可以为空
     * @exception java.lang.IllegalArgumentException 当account为空时
     */
    void updateBySelective (Account account);

    /**
     * 根据一组账户id，更新账户状态
     * @param accountIds 账户id 不可为空
     * @param status 状态 不可为空
     *               <ul>
     *               <li>{@link com.ace.app.cms.CmsConstant#ACCOUNT_STATUS_FROZEN 冻结} </li>
     *               <li>{@link com.ace.app.cms.CmsConstant#ACCOUNT_STATUS_ACTIVATED 激活} </li>
     *              </ul>
     * @param corpCode 公司编号 不可为空
     * @exception java.lang.IllegalArgumentException 当accountIds或者corpCode为空时，当status不等于frozen、normal之一时
     * @since 2015-12-9 16:32:03 WuZhiWei
     */
    void updateStatus(List<String> accountIds,String status,String corpCode);

    /**
     * 重置账户密码
     * @param accountIds 一组账户id
     * @param corpCode 商家编号
     * @exception java.lang.IllegalArgumentException 当accountIds或者corpCode为空时
     */
    void resetAccountPsd(List<String> accountIds,String corpCode);
    /**
     * 重置admin账户密码
     * @param corpCode 商家编号
     * @exception java.lang.IllegalArgumentException 当corpCode为空时
     */
    void resetAdminPsd(String corpCode);

    /**
     * 根据公司编号和一组工号查询工号和账户的map
     * @param corpCode 公司编号 不可为空
     * @param employeeCodes 一组工号 不可为空
     * @return Map  key：用户编号   value：Account （包含所有持久化属性）
     * @exception  java.lang.IllegalArgumentException 当corpCode、employeeCodes任意为空时
     */
    Map<String,Account> getEmployeeCodeAndAccountMap(String corpCode,List<String> employeeCodes);
    /**
     * 根据公司编号和一组账户名查询账户名和账户的map
     * @param corpCode 公司编号 不可为空
     * @param accountNames 一组工号 不可为空
     * @return Map  key：用户编号   value：Account （包含所有持久化属性）
     * @exception  java.lang.IllegalArgumentException 当corpCode、accountNames任意为空时
     */
    Map<String,Account> getAccountNameAndAccountMap(String corpCode,List<String> accountNames);
    /**
     * 根据公司编号和一组手机号查询手机号和账户的map
     * @param corpCode 公司编号 不可为空
     * @param phoneList 一组手机号码 不可为空
     * @return Map  key：用户编号   value：Account （包含所有持久化属性）
     * @exception  java.lang.IllegalArgumentException 当corpCode、accountNames任意为空时
     */
    Map<String,Account> getPhoneAndAccountMap(String corpCode,List<String> phoneList);

    /**
     * 根据手机号取账户信息
     * @param phone
     * @return
     */
    Account  getByPhone(String phone);

    /**
     * 获取在班的人员
     * @param corpCode 商家编号 不能为空
     * @param roleCode 角色编号，不能为空
     * @return 在班的人员
     */
    List<Account> getAllWorkAccount(String corpCode,String roleCode);

    /**
     * 根据商家编号获取当前商家下所有激活状态下的accountId
     * @param corpCode 商家编号 不能为空
     * @return 所有账户id
     * @exception java.lang.IllegalArgumentException 当商家编号为空时
     */
    List<String> getAllAccountIds(String corpCode);

    Map<String,String> getAidNameMap();
}
