package sjzc.hf.miaosha.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sjzc.hf.miaosha.VO.UserVo;
import sjzc.hf.miaosha.error.BusinessException;
import sjzc.hf.miaosha.error.EmBusinessError;
import sjzc.hf.miaosha.model.UserModel;
import sjzc.hf.miaosha.response.CommonReturnType;
import sjzc.hf.miaosha.service.UserInfoService;
import sjzc.hf.miaosha.validator.ValidationResult;
import sjzc.hf.miaosha.validator.ValidatorImpl;

@RestController
@RequestMapping("/user")
//@CrossOrigin//解决跨域(通过地址项目地址访问，根路径相同无跨域)
public class userContorller extends BaseController {
	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private HttpSession session;

	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	public CommonReturnType login(HttpServletRequest request,String name,String encrptPassword)  {
	
		if(name==null || encrptPassword==null) {
			return CommonReturnType.creat(EmBusinessError.PARAMETER_VALIDATION_ERROR,"账号或密码不能为空");
		}
		
		// 创建token令牌，记录用户认证的身份和凭证即账号和密码 
		UsernamePasswordToken token = new UsernamePasswordToken(name, encrptPassword);
		Subject subject = SecurityUtils.getSubject();

		
		
		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			return CommonReturnType.creat(e.getMessage());
		} catch (IncorrectCredentialsException e) {
		
			return CommonReturnType.creat(EmBusinessError.LOGIN_ERROR,"账号或密码不正确");
		} catch (LockedAccountException e) {
		
			return CommonReturnType.creat(EmBusinessError.LOGIN_ERROR,"账号已被锁定,请联系管理员");
		} catch (AuthenticationException e) {
		
			return CommonReturnType.creat(EmBusinessError.LOGIN_ERROR,"账户验证失败");
		}
		return CommonReturnType.creat("登陆成功");
	}

	@RequestMapping(value = "/signIn", method = { RequestMethod.POST })
	public CommonReturnType signIn(@ModelAttribute UserModel user,
			HttpServletRequest request) throws Exception {
		
		//调用自己封装的校验方法进行校验
		ValidationResult result=ValidatorImpl.validata(user);
		if(result.isHasErrors()) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrorMsg());
		}
		
		String sessionOtpCode = (String) request.getSession().getAttribute(user.getTelphone());
		if (!sessionOtpCode.equals(user.getOtpCode())) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "验证码错误");
		}
		userInfoService.signIn(user);

		return CommonReturnType.creat("注册成功");
	}

	@RequestMapping(value = "/sendMassage", method = { RequestMethod.POST })
	public CommonReturnType sendMessage(String telphone) {
		// 生成验证码
		Random random = new Random();
		Integer randomInt = random.nextInt(99999);
		randomInt += 1000;
		String randomCode = randomInt.toString();

		// 手机号验证码绑定
		session.setAttribute(telphone, randomCode);

		// 第三方接口发送验证码
		// 第三方接口暂无

		return CommonReturnType.creat(randomCode);
	}

	@RequestMapping(value = "/getUser", method = { RequestMethod.GET })
	public CommonReturnType getUser(Integer id) throws Exception {
		UserModel userModel = userInfoService.selectByPrimaryKey(id);
		UserVo user = null;
		
			if (userModel == null) {
				throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
			}
			user = convertFromModel(userModel);
			System.out.println(SecurityUtils.getSubject().getPrincipal());
		

		return CommonReturnType.creat(user);
	}

	private UserVo convertFromModel(UserModel userModel) {
		if (userModel == null) {
			return null;
		}
		UserVo userVO = new UserVo();
		BeanUtils.copyProperties(userModel, userVO);
		return userVO;
	}

}
