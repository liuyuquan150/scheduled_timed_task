package indi.ly.crush.service;

import indi.ly.crush.model.User;

import java.util.List;

/**
 * <h2>用户服务</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public interface IUserService {
	/**
	 * <p>
	 *     获取所有用户.
	 * </p>
	 *
	 * @return 所有用户.
	 */
	List<User> getAllUsers();
	/**
	 * <p>
	 *     获取指定名称的用户.
	 * </p>
	 *
	 * @param name 用户名称.
	 * @return 用户.
	 */
	User getUserByName(String name);
}
