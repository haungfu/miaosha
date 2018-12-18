package sjzc.hf.miaosha.dao;

import sjzc.hf.miaosha.dataobject.ActivationCodeDO;

public interface ActivationCodeDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivationCodeDO record);

    int insertSelective(ActivationCodeDO record);

    ActivationCodeDO selectByPrimaryKey(Integer id);
    
    ActivationCodeDO selectByActivationCode(String activationCode);

    int updateByPrimaryKeySelective(ActivationCodeDO record);

    int updateByPrimaryKey(ActivationCodeDO record);
}