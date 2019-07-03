/**
 *
 */
package com.ace.framework.base;

import com.ace.framework.util.ExecutionContext;
import com.ace.framework.util.PropertiesUtil;
import com.ace.framework.util.common.RequestUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

/**
 * 所有Action的公共继承类 自动封装类数据模型
 *
 * @author WZW
 * @since 2015-11-03 17:23:52
 */
public abstract class BaseAction<M> extends ActionSupport implements
		ModelDriven<M>, Preparable {

	/**
	 *
	 */
	private Boolean isAjax = false;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 数据模型
	 */
	protected M model;
	/**
	 * 目标视图
	 */
	private String target;
	private String module;//逻辑模块
    private String ctx;
    private String openCtx;
    private String openbCtx;
    private List<String> roleCodes;
    private static final String openUrl = PropertiesUtil.getEnv("open_url");
    private static final String openbUrl = PropertiesUtil.getEnv("openb_url");

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getOpenbCtx() {
        return openbUrl;
    }

    public void setOpenbCtx(String openbCtx) {
        this.openbCtx = openbCtx;
    }

    public void setCtx(String ctx) {
        this.ctx = ctx;
    }

	/**
	 * common的常量
	 */
	public static final String COMMON = "common";
	/**
	 * request对象
	 */
	protected HttpServletRequest request = ServletActionContext.getRequest();
	/**
	 * response对象
	 */
	protected HttpServletResponse response = ServletActionContext.getResponse();

	/**
	 * package坐标
	 */
	private String pkgCoordinate;

	/**
	 * 带有package坐标的构造方法
	 *
	 * @param pkgCoordinate
	 */
	public BaseAction(String pkgCoordinate) {
		super();
		this.pkgCoordinate = pkgCoordinate;
	}

	/**
	 * 带有指定model的构造方法
	 *
	 * @param model
	 */
	public BaseAction(M model) {
		super();
		this.model = model;
		initPkgCoordinate();
	}

	/**
	 * 带有pacakge坐标和model的构造方法
	 *
	 * @param pkgCoordinate
	 * @param model
	 */
	public BaseAction(String pkgCoordinate, M model) {
		super();
		this.model = model;
		this.pkgCoordinate = pkgCoordinate;
	}

	/**
	 * 构造方法，自动创建model对象,自动构造package坐标
	 */
	@SuppressWarnings("unchecked")
	public BaseAction() {
		super();
		try {
			Class<M> m = (Class<M>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
			this.model = m.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		initPkgCoordinate();
	}

	/**
	 * 初始化代码坐标
	 */
	private void initPkgCoordinate() {
		String pkg = getClass().getPackage().getName();
		if (pkg.indexOf("com.ace") != -1) {
			pkg = pkg.replace("com.ace", "default");
		}
		pkg = pkg.replace(".", "/");
		pkg = pkg.replace("web", "html");
		this.pkgCoordinate = pkg;
	}

	/**
	 * 默认的处理方法
	 */
	public String execute() throws Exception {
		return COMMON;
	}

	/**
	 * 设定目标视图，返回一个通用结果，该通用结果包含一个target的参数
	 *
	 * @param target 目标视图
	 * @return 通用配置的结果
	 */
	public String render(String target) {
		// 自动加上pacakge坐标
		if (!target.startsWith("/")) {
			target = "/" + this.pkgCoordinate + "/" + target;
		}
		setTarget(target);
		return COMMON;
	}

	/**
	 * 将对象转换为json返回
	 *
	 * @param object 目标对象
	 * @return
	 */
	public String renderJson(Object object) {
		Struts2Utils.renderJson(object, RequestUtil.getParameter("callback", request));
		return null;
	}

	/**
	 * 返回json字符串
	 *
	 * @param jsonString 目标对象
	 * @return
	 */
	public String renderJsonString(String jsonString) {
		Struts2Utils.render("application/json", jsonString);
		return null;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	public String getCtx() {
		return ServletActionContext.getRequest().getContextPath();
	}

    public String getOpenCtx(){
        return openUrl;
    }

    public List<String> getRoleCodes() {
        String roleCodeStr = ExecutionContext.getRoleCode();
        if (StringUtils.isEmpty(roleCodeStr)){
            return null;
        }

        roleCodes = Arrays.asList(roleCodeStr.split(","));

        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }
	@Override
	public M getModel() {
		return model;
	}

	@Override
	public void prepare() throws Exception {
	}

	public Boolean getIsAjax() {
		return isAjax();
	}

	/**
	 * 是否为Ajax请求
	 *
	 * @return
	 */
	private boolean isAjax() {
		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equalsIgnoreCase(header))
			return true;
		else
			return false;
	}

	public void setIsAjax(Boolean isAjax) {
		this.isAjax = isAjax;
	}

}
