package com.mall.b2bp.servlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.b2bp.utils.ConstantUtil;

/**
 * 
 *When Login ,setting the default language 
 */
public class LanguageFilter implements Filter {
	//private static final Logger LOG = LoggerFactory.getLogger(LanguageFilter.class);
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String language = req.getParameter("language");
		if(language != null) {
			req.getSession().setAttribute(ConstantUtil.LOGIN_LANGUAGE, language);
			//LOG.info("Init language:  " + language);
		}
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
		
	}

}
