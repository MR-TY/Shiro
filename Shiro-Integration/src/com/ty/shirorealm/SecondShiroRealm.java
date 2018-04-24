package com.ty.shirorealm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.crypto.hash.SimpleHashRequest;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.ByteSource;

public class SecondShiroRealm extends AuthenticatingRealm {
	//这里的token就是前面subject获取当前的用户，没获取成功，然后创建一个token
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
	System.out.println("SecondRealm");
		System.out.println("doGetAuthenticationInfo:"+token.hashCode());
		//1.把AuthenticationToken转换为UsernamePasswordToken
		UsernamePasswordToken uPasswordToken = (UsernamePasswordToken)token;
		//2.从UsernamePasswordToken中获取username
		String userName = uPasswordToken.getUsername();
		//3.调用数据库的数据
		System.out.println("从数据库中获取userName："+userName+"所对应的信息");
		//4.查看数据库是否存在，不存在就抛出UnknownAccountException
		if ("unKnown".equals(userName)) {
			System.out.println("welcome");
			throw new UnknownAccountException("用户不存在");
		}
		//5.根据用户的信息是否抛出其他的异常
		if ("monster".equals(userName)) {
			throw new LockedAccountException("用户被锁定");
		}
		//6.根据用户的情况来构建AuthenticationInfo对象并且返回,通常使用的实现类是SimpleAuthenticationInfo
		//以下信息是从数据库中获取的
		//参数一：认证的实体信息，可以是username，也可以是用户表对应的实体类对象
		//参数二：数据表中获取的密码
		//参数三：当前reaml对象的name，调用父类的getName即可
//----------------------密码的加密------------------------
		Object principle = userName;
		Object credentias = null;
		if ("admin".equals(userName)) {
			credentias="ce2f6417c7e1d32c1d81a797ee0b499f87c5de06";
		}else if ("user".equals(userName)) {
			credentias="073d4c3ae812935f23cb3f2a71943f49e082a718";
		}
		String reamlName = getName();
		//盐值加密，作用：即使密码一样最后的值也不一样
		ByteSource Salt = ByteSource.Util.bytes(userName);//使用用户名作为盐的原始值，这是唯一的标志
		return new SimpleAuthenticationInfo(principle, credentias,Salt, reamlName);
	}
	public static void main(String[] args) {
		String algorithmName = "SHA1";
		Object source ="123456";//密码
		Object salt = ByteSource.Util.bytes("admin");//加入盐值
		int iterations = 1024;//加密的次数
		Object result = new SimpleHash(algorithmName, source, salt, iterations);//获取MD5的加密值
		System.out.println(result);
	}
	
}
