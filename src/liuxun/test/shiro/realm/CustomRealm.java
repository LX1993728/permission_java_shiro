package liuxun.test.shiro.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class CustomRealm extends AuthorizingRealm {
	// 设置Realm的名称
	@Override
	public String getName() {
		return "CustomRealm";
	}

	// 支持UsernamePasswordToken
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	// 用于认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		// token是用户输入的
		// 第一步从token中取出身份信息
		String usercode = (String) token.getPrincipal();

		// 第二步：根据用户输入的usercode从数据库查询
		// ......

		// 如果查询不到返回null
		// 数据库中用户账号是zhangsan
		if (!usercode.equals("zhangsan")) {
			return null;
		}

		// 模拟从数据库中查询到密码
		String password = "123456";

		// 如果查询到返回认证信息AuthenticationInfo
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(usercode, password,
				this.getName());

		return simpleAuthenticationInfo;
	}

	// 用于授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		//从principals获取身份信息
		//将getPrimaryPrincipal方法返回值转为真实类型
		//(在上边的doGetAuthenticationInfo认证通过后填充到SimpleAuthenticationInfo中身份类型)
		String userCode = (String) principals.getPrimaryPrincipal();
		
		//根据身份信息从数据库中获取权限信息
		//模拟从数据库中取到的数据
		List<String>  permissions = new ArrayList<String>();
		permissions.add("user:create");//用户创建
		permissions.add("items:add");//商品添加权限
		//....
		
		//查询到权限数据，返回授权信息(要包括上边的permissions)
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		//将上边查询到的授权信息填充到simpleAuthorizationInfo对象中
		simpleAuthorizationInfo.addStringPermissions(permissions);
		
		return simpleAuthorizationInfo;
	}
}
