package com.mall.b2bp.services.impl.user;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.mall.b2bp.services.user.SessionService;
import com.mall.b2bp.services.user.UserService;
import com.mall.b2bp.vos.user.UserVo;

@Service("sessionService")
public class SessionServiceImpl implements SessionService {
	
//	private static final Logger LOG = LoggerFactory.getLogger(SessionServiceImpl.class);
	
	@Resource
	private UserService userService;

	@Override
	public UserVo getCurrentUser() {
		
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    	if(authentication!=null &&  authentication.getPrincipal() instanceof User){
    		final User user = (User)authentication.getPrincipal();
    		UserVo userVo = userService.selectUserByUserId(user.getUsername());
    		return userVo;
    	}
//    	else if(authentication.getPrincipal() instanceof String){
//    		LOG.info((String)authentication.getPrincipal());
//    	}
		
    	return null;
	}

}
