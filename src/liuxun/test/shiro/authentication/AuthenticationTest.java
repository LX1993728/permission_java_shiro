package liuxun.test.shiro.authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * 认证测试
 * 
 * @author liuxun
 *
 */
public class AuthenticationTest {

	// 用户登录和退出
	@Test
	public void testLoginAndLogout() {

		// 创建SecurityManager工厂。通过ini配置文件创建securityManager
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-first.ini");

		// 创建SecurityManager
		SecurityManager securityManager = factory.getInstance();

		// 将securityManager设置到当前的运行环境中
		SecurityUtils.setSecurityManager(securityManager);

		// 从SecurityUtils中创建一个subject
		Subject subject = SecurityUtils.getSubject();

		// 在认证提交前准备token(令牌)
		// 这里的账号和密码 将来是由用户输入进去的
		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123456");
       
		//执行认证提交
		try {
			//执行认证提交
			subject.login(token);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		
		// 是否认证通过
		boolean isAuthenticated = subject.isAuthenticated();
		System.out.println("是否认证通过："+isAuthenticated);
		
		//退出操作
		subject.logout();
		
		// 是否认证通过
		isAuthenticated = subject.isAuthenticated();
		System.out.println("是否认证通过："+isAuthenticated);
	}
	
	//自定义Realm
	@Test
	public void testCustomRealm() {
		
		// 创建SecurityManager工厂。通过ini配置文件创建securityManager
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
		
		// 创建SecurityManager
		SecurityManager securityManager = factory.getInstance();
		
		// 将securityManager设置到当前的运行环境中
		SecurityUtils.setSecurityManager(securityManager);
		
		// 从SecurityUtils中创建一个subject
		Subject subject = SecurityUtils.getSubject();
		
		// 在认证提交前准备token(令牌)
		// 这里的账号和密码 将来是由用户输入进去的
		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123456");
		
		//执行认证提交
		try {
			//执行认证提交
			subject.login(token);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		
		// 是否认证通过
		boolean isAuthenticated = subject.isAuthenticated();
		System.out.println("是否认证通过："+isAuthenticated);
		
	}
	//自定义Realm实现散列值匹配
	@Test
	public void testCustomRealmMd5() {
		
		// 创建SecurityManager工厂。通过ini配置文件创建securityManager
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm-md5.ini");
		
		// 创建SecurityManager
		SecurityManager securityManager = factory.getInstance();
		
		// 将securityManager设置到当前的运行环境中
		SecurityUtils.setSecurityManager(securityManager);
		
		// 从SecurityUtils中创建一个subject
		Subject subject = SecurityUtils.getSubject();
		
		// 在认证提交前准备token(令牌)
		// 这里的账号和密码 将来是由用户输入进去的
		UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123456");
		
		//执行认证提交
		try {
			//执行认证提交
			subject.login(token);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		
		// 是否认证通过
		boolean isAuthenticated = subject.isAuthenticated();
		System.out.println("是否认证通过："+isAuthenticated);
		
	}
}
