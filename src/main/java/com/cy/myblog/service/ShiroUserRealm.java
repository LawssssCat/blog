package com.cy.myblog.service;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.myblog.common.utils.Assert;
import com.cy.myblog.dao.UserDao;
import com.cy.myblog.pojo.po.User;

@Service("shiroUserRealm")
public class ShiroUserRealm  extends AuthorizingRealm{
	
	@Autowired
	private UserDao userDao ; 

	@Override//授权
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override//认证
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		UsernamePasswordToken upToken = (UsernamePasswordToken) token ; 
		String username = upToken.getUsername(); 
		
		User user = userDao.findObjectByUsername(username);
		
		Assert.isNull(user, new UnknownAccountException("用户名错误!"));
		Assert.isTrue(user.getValid()==0 , new LockedAccountException("用户被禁用!") ) ; //0=禁用,1=启用 
		
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt()); 
		
		
		SimpleAuthenticationInfo info = 
				new SimpleAuthenticationInfo(
						user, //principal-当事人 
						user.getPassword(), //hashedCredentials hash凭证 
						credentialsSalt,//credentialsSalt 凭证的盐值 
						user.getUsername()); ///realmName
		return info;
	}

	
	@Override//凭证-匹配器
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher hashedCredentialsMatcher = 
				new HashedCredentialsMatcher(Md5Hash.ALGORITHM_NAME);
		//默认1 - 加密次数 setHashIterations(1)
		super.setCredentialsMatcher(hashedCredentialsMatcher);
	}

	

}
