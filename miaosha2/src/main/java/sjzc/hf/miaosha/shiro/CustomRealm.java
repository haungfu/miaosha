package sjzc.hf.miaosha.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import sjzc.hf.miaosha.dao.UserDOMapper;
import sjzc.hf.miaosha.dao.UserPasswordDOMapper;
import sjzc.hf.miaosha.dataobject.UserDO;
import sjzc.hf.miaosha.dataobject.UserPasswordDO;

public class CustomRealm extends AuthorizingRealm {
	
	@Autowired
	private UserDOMapper userDOMapper;
	@Autowired
	private UserPasswordDOMapper userPasswordDOMapper;
	
	// 支持什么类型的token
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 授权
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 认证
		//根据用户姓名查询用户相关信息
		UserDO user=userDOMapper.selectByName((String)token.getPrincipal());
		//由用户id查询用户密码
		UserPasswordDO userPassword=userPasswordDOMapper.selectByUserId(user.getId());
		//传给认证器认证用户身份
		// 返回认证信息由父类AuthenticatingRealm进行认证
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
				user, userPassword.getEncrptPassword(), getName());

		return simpleAuthenticationInfo;
	}

}
