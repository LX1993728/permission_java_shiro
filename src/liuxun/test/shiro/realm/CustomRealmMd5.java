package liuxun.test.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class CustomRealmMd5 extends AuthorizingRealm {
	// 设置Realm名称
	@Override
	public void setName(String name) {
		super.setName("CustomRealmMd5");
	}

	// 支持UsernamePasswordToken
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	// 用于认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		// token保存了用户输入的身份信息userName和password
		// 第一步：从token中取出身份信息
		String userCode = (String) token.getPrincipal();

		// 第二步：根据用户输入的userCode从数据库查询
		// ....
		// 如果查询不到返回null 假设用户输入的账号是zhansgan
		// 模拟从数据库中查询账号是zhangsan的用户
		if (!userCode.equals("zhangsan")) {
			return null;
		}

		// 模拟从数据库中查询到密码(散列值)
		// 按照固定规则加密的结果，此密码是在数据库中存储的，原始密码是123456 盐是qwerty
		String password = "48474f975022f960bc2afbe49be581e8";
		// 盐，随机字符串，此随机字符串也是在数据库中存储的,模拟从数据库中获取
		String salt = "qwerty";

		// 如果查询到则返回认证信息AuthenticationInfo
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userCode, password,
				ByteSource.Util.bytes(salt), this.getName());
		
		return simpleAuthenticationInfo;
	}

	// 用于授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

}
