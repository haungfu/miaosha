package sjzc.hf.miaosha.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sjzc.hf.miaosha.dao.ActivationCodeDOMapper;
import sjzc.hf.miaosha.dataobject.ActivationCodeDO;
import sjzc.hf.miaosha.error.BusinessException;
import sjzc.hf.miaosha.error.EmBusinessError;
import sjzc.hf.miaosha.service.ActivationCodeService;
@Service
public class ActivationCodeServiceImpl implements ActivationCodeService {

	@Autowired
	private ActivationCodeDOMapper activationCodeDOMapper;
	
	
	

	/**
	 * 方法说明：根据激活码查询用户id
	 * @author 皇甫振天
	 * @date 2018-18-17 10:05:06
	 * @param activationCode 激活码
	 * @return 
	 */
	@Override
	public ActivationCodeDO selectByActivationCode(String activationCode) throws Exception {
		//根据激活码查询用户id
		ActivationCodeDO activationCodeDO=activationCodeDOMapper.selectByActivationCode(activationCode);
		if(activationCodeDO==null) {
			throw new BusinessException(EmBusinessError.USER_NOT_EXIST,"激活码错误");
		}
		
		return activationCodeDO;
	}

}
