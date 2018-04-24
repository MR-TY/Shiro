package com.ty.shirorealm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.crypto.hash.SimpleHashRequest;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * @Description: realm可以看作验证用户信息的一个DAO
 * @ClassName: ShiroRealm.java
 * @version: v1.0.0
 * @author:
 * @date: 2018年4月23日 上午1:06:45
 */
public class ShiroRealm extends AuthorizingRealm {
	// 这里的token就是前面subject获取当前的用户，没获取成功，然后创建一个token
	// 用于验证
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("doGetAuthenticationInfo:" + token.hashCode());
		// 1.把AuthenticationToken转换为UsernamePasswordToken
		UsernamePasswordToken uPasswordToken = (UsernamePasswordToken) token;
		// 2.从UsernamePasswordToken中获取username
		String userName = uPasswordToken.getUsername();
		// 3.调用数据库的数据
		System.out.println("从数据库中获取userName：" + userName + "所对应的信息");
		// 4.查看数据库是否存在，不存在就抛出UnknownAccountException
		if ("unKnown".equals(userName)) {
			System.out.println("welcome");
			throw new UnknownAccountException("用户不存在");
		}
		// 5.根据用户的信息是否抛出其他的异常
		if ("monster".equals(userName)) {
			throw new LockedAccountException("用户被锁定");
		}
		// 6.根据用户的情况来构建AuthenticationInfo对象并且返回,通常使用的实现类是SimpleAuthenticationInfo
		// 以下信息是从数据库中获取的
		// 参数一：认证的实体信息，可以是username，也可以是用户表对应的实体类对象
		// 参数二：数据表中获取的密码
		// 参数三：当前reaml对象的name，调用父类的getName即可
		// ----------------------密码的加密------------------------
		Object principle = userName;
		Object credentias = null;
		String reamlName = getName();
		System.out.println("reamlName:-----" + reamlName);
		if ("admin".equals(userName)) {
			credentias = "038bdaf98f2037b31f1e75b5b4c9b26e";
		} else if ("user".equals(userName)) {
			credentias = "098d2c478e9c11555ce2823231e02ec1";
		}
		// 盐值加密，作用：即使密码一样最后的值也不一样
		ByteSource Salt = ByteSource.Util.bytes(userName);// 使用用户名作为盐的原始值，这是唯一的标志
		return new SimpleAuthenticationInfo(principle, credentias, Salt, reamlName);
	}

	public static void main(String[] args) {
		String algorithmName = "MD5";
		Object source = "123456";// 密码
		Object salt = ByteSource.Util.bytes("user");// 加入盐值
		int iterations = 1024;// 加密的次数
		Object result = new SimpleHash(algorithmName, source, salt, iterations);// 获取MD5的加密值
		System.out.println(result);
	}

	// 用于授权会被shiro回调的方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("进到授权的页面了-------------");
		// 1.利用PrincipalCollection获取登录用户的信息
		Object principal = principals.getPrimaryPrincipal();
		// 2.利用登录用户的信息，判断用户当前的角色(可能需要查询数据库)
		Set<String> roles = new HashSet<>();
		roles.add("user");// 如果是user的类型，则只有一个角色
		if ("admin".equals(principal)) {
			roles.add("user");// 如果是admin的角色，则还会有一个user的角色
			roles.add("admin");
		}
		// 3.创建SimpleAuthorizationInfo,并设置其reles的属性
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		// 4.返回授权的对象
		return info;
	}

}
