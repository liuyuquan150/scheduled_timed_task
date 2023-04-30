package indi.ly.crush.service.impl;

import indi.ly.crush.model.User;
import indi.ly.crush.service.IUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>用户服务实现</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
@Service(value = "USER")
public class UserServiceImpl
		implements IUserService {
	static final Log LOG = LogFactory.getLog(UserServiceImpl.class);
	
	@Override
	public List<User> getAllUsers() {
		LOG.info("Parameterless tasks are triggered.");
		return new ArrayList<>(0);
	}
	
	@Override
	public User getUserByName(String name) {
		LOG.info("Tasks with parameters have been triggered.");
		return new User();
	}
}
