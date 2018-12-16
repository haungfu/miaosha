package sjzc.hf.miaosha.service;

import sjzc.hf.miaosha.dataobject.UserDO;
import sjzc.hf.miaosha.model.UserModel;

public interface UserInfoService {

	 public UserModel selectByPrimaryKey(Integer id);
	 
	 public int deleteByPrimaryKey(Integer id);

	 public int insert(UserDO record);

	 public int insertSelective(UserDO record);
	 public void signIn(UserModel user);
}
