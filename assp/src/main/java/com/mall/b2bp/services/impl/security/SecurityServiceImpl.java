package com.mall.b2bp.services.impl.security;

//import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mall.b2bp.models.product.ProductInfoModel;
import com.mall.b2bp.services.security.SecurityService;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {
	
    private static final Logger LOG = LoggerFactory.getLogger(SecurityServiceImpl.class);

	//@Resource(name = "aclService")
	private MutableAclService mutableAclService;

	@Override
	public void addProductPermission(ProductInfoModel productInfoModel) {
		ObjectIdentity oid = new ObjectIdentityImpl(ProductInfoModel.class,
				productInfoModel.getId().longValue());
		
		MutableAcl acl = null;
        try {
            acl = (MutableAcl) mutableAclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = mutableAclService.createAcl(oid);
            LOG.info(nfe.getMessage(), nfe);
        }
		
        //add owner permission
        Sid recipient = new PrincipalSid(getUsername());
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, recipient, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, recipient, true);
        mutableAclService.updateAcl(acl);
        
        //add approver permission
        
        //add admin permission
	}

//	@Override
//	public void addOrderPermission(OrderModel orderModel) {
//
//	}

    protected String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) auth.getPrincipal()).getUsername();
        } else {
            return auth.getPrincipal().toString();
        }
    }
}
