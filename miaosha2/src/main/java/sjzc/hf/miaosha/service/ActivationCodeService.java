package sjzc.hf.miaosha.service;

import sjzc.hf.miaosha.dataobject.ActivationCodeDO;

public interface ActivationCodeService {
	//根据激活码查询出用户id
	public ActivationCodeDO selectByActivationCode(String activationCode) throws Exception;

}
