package liuxun.test.shiro.authorization;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * 授权测试
 * 
 * @author liuxun
 *
 */
public class AuthorizationTest {

	// 角色授权、资源授权测试
	@Test
	public void testAuthorization() {

		// 创建SecurityManager工厂
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-permission.ini");

		// 创建SecurityManager
		SecurityManager securityManager = factory.getInstance();

		// 将SecurityManager设置到系统运行环境，和Spring整合后将SecurityManager配置在Spring容器中，一般单例管理
		SecurityUtils.setSecurityManager(securityManager);

		// 创建subject
		Subject subject = SecurityUtils.getSubject();

		// 创建token令牌
		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123");

		// 执行认证
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}

		System.out.println("认证状态：" + subject.isAuthenticated());

		// 认证通过后执行授权

		// 基于角色的授权
		// hasRole传入角色标识
		boolean ishasRole = subject.hasRole("role1");
		System.out.println("单个角色判断 " + ishasRole);
		// hasAllRoles 是否拥有多个角色
		boolean hasAllRoles = subject.hasAllRoles(Arrays.asList("role1", "role2", "role3"));
		System.out.println("多个角色判断 " + hasAllRoles);

		// 使用check方法进行授权，如果授权不通过会抛出异常,用于断言
		subject.checkRole("role2");

		// 基于资源的权限
		// isPermitted传入权限标识符
		boolean isPermitted = subject.isPermitted("user:create:1");
		System.out.println("单个权限判断 " + isPermitted);

		boolean isPermittedAll = subject.isPermittedAll("user:create:1", "user:delete");
		System.out.println("多个权限判断 " + isPermittedAll);

		// 使用check方法进行授权测试，如果授权不通过会抛出异常
		subject.checkPermission("item:delete");
	}

	// 自定义Realm进行资源授权测试
	@Test
	public void testAuthorizationCustomRealm() {

		// 创建SecurityManager工厂
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");

		// 创建SecurityManager
		SecurityManager securityManager = factory.getInstance();

		// 将SecurityManager设置到系统运行环境，和Spring整合后将SecurityManager配置在Spring容器中，一般单例管理
		SecurityUtils.setSecurityManager(securityManager);

		// 创建subject
		Subject subject = SecurityUtils.getSubject();

		// 创建token令牌
		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123");

		// 执行认证
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}

		System.out.println("认证状态：" + subject.isAuthenticated());

		// 认证通过后执行授权

		// 基于资源的授权，调用isPermitted方法会调用CustomRealm从数据库中查询正确权限数据
		// isPermitted传入权限标识符，判断user:create:1是否在CustomRealm查询到的权限数据之内
		boolean isPermitted = subject.isPermitted("user:create:1");
		System.out.println("单个权限判断 " + isPermitted);

		boolean isPermittedAll = subject.isPermittedAll("user:create:1", "user:delete");
		System.out.println("多个权限判断 " + isPermittedAll);

		// 使用check方法进行授权测试，如果授权不通过会抛出异常
		subject.checkPermission("item:add:1");
	}

}
