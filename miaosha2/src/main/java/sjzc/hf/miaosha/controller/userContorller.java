package sjzc.hf.miaosha.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sjzc.hf.miaosha.VO.UserVo;
import sjzc.hf.miaosha.error.BusinessException;
import sjzc.hf.miaosha.error.EmBusinessError;
import sjzc.hf.miaosha.model.UserModel;
import sjzc.hf.miaosha.response.CommonReturnType;
import sjzc.hf.miaosha.service.UserInfoService;

@RestController
@RequestMapping("/user")
//@CrossOrigin//解决跨域(通过地址项目地址访问，根路径相同无跨域)
public class userContorller extends BaseController {
	@Autowired
	private UserInfoService userInfoService;

	
	

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
