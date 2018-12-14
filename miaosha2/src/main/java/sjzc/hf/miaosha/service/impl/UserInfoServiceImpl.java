package sjzc.hf.miaosha.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sjzc.hf.miaosha.dao.UserDOMapper;
import sjzc.hf.miaosha.dao.UserPasswordDOMapper;
import sjzc.hf.miaosha.dataobject.UserDO;
import sjzc.hf.miaosha.dataobject.UserPasswordDO;
import sjzc.hf.miaosha.model.UserModel;
import sjzc.hf.miaosha.service.UserInfoService;
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserDOMapper userDOMapper;
	@Autowired
	private UserPasswordDOMapper userPasswordDOMapper;
	
	
	public UserModel selectByPrimaryKey(Integer id){
		UserDO user=userDOMapper.selectByPrimaryKey(id);
		UserPasswordDO password=userPasswordDOMapper.selectByUserId(id);
		UserModel userModel=convertFromDataObject(user, password);
		return userModel;
	}

	public void signIn(UserModel user) {
		userDOMapper.insertSelective(user);
		UserPasswordDO password=new UserPasswordDO();
		password.setEncrptPassword(user.getEncrptPassword());
		password.setUserId(user.getId());
		userPasswordDOMapper.insertSelective(password);
	}
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(UserDO record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(UserDO record) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private UserModel convertFromDataObject(UserDO user,UserPasswordDO password){
		UserModel userModel=new UserModel();
		BeanUtils.copyProperties(user, userModel);
		userModel.setEncrptPassword(password.getEncrptPassword());
		return userModel;
	}
	
}
