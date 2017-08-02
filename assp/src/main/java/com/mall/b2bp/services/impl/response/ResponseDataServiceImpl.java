package com.mall.b2bp.services.impl.response;

import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import com.mall.b2bp.exception.SystemException;
import com.mall.b2bp.models.system.UserInfoModel;
import com.mall.b2bp.services.response.ResponseDataService;
import com.mall.b2bp.services.system.SessionService;
import com.mall.b2bp.vos.ResponseData;

@Service("responseDataService")
public class ResponseDataServiceImpl implements ResponseDataService{
	   private static final Logger LOG = LoggerFactory.getLogger(ResponseDataServiceImpl.class);
	@Resource
	protected  ResourceBundleMessageSource messageSource;
	
	@Resource(name="sessionService1")
    private SessionService sessionService;
    
	public ResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}

private final static Locale DEFAULT_LOCALE = Locale.ENGLISH;
    
    public final static String IETF_SEPARATOR = "-";  
    
    public final static String SEPARATOR = "_";  
    
    public final static String EMPTY_STRING = ""; 
     
     String language = EMPTY_STRING;
     String country =  EMPTY_STRING;
     String variant =  EMPTY_STRING;
     
    public  Locale toLocale( String language )
    {
        if( StringUtils.isNotEmpty( language ) )
        {
            return langToLocale( language, SEPARATOR );
        }
        return DEFAULT_LOCALE;
    }
      
    @Override
	public  Locale langToLocale( String lang , String separator )
     {
           if( StringUtils.isEmpty( lang ) )
           { 
                return DEFAULT_LOCALE;
           }
  


            int i1 = lang.indexOf( separator );
            if ( i1 < 0 )
            {
                language = lang;
            } else 
            {
                language = lang.substring(0, i1);
                ++i1;
                int i2 = lang.indexOf( separator, i1);
                if (i2 < 0) 
                {
                    country = lang.substring(i1);
                } else 
                {
                    country = lang.substring(i1, i2);
                    variant = lang.substring(i2+1);
                }
            }
            
            if(language.length() == 2)
            {
               language = language.toLowerCase();
            }else 
            {
              language = EMPTY_STRING;
            }
            
            if(country.length() == 2)
            {
               country = country.toUpperCase();
            }else 
            {
              country = EMPTY_STRING;
            }
            
            if( (variant.length() > 0) && 
                ((language.length() == 2) ||(country.length() == 2)) )
            {
               variant = variant.toUpperCase();
            }else
            {
                variant = EMPTY_STRING;
            }
                 
            return new Locale(language, country, variant );
        }

	@Override
	public  ResponseData<?> getReturnData(Class<?> returnType)
			throws SystemException {
		
		UserInfoModel user= sessionService.getCurrentUser();
		String language="";
		if(user!=null){
			language=user.getSessionLang();
		}
		
		try {
			ResponseData<?> data=new ResponseData<>();
			data.setResourceBundleMessageSource(messageSource);
			data.setLanguage(toLocale(language));
			return data;
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new SystemException(e.getMessage(),e);
		}
	
		
	}

}
