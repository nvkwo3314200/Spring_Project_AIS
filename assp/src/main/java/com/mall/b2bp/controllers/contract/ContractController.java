package com.mall.b2bp.controllers.contract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.mall.b2bp.models.contract.ContractModel;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.b2bp.controllers.BaseConroller;
import com.mall.b2bp.models.contract.ContractDeliverMethodModel;
import com.mall.b2bp.models.contract.ContractPayMethodModel;
import com.mall.b2bp.services.contract.ContractService;
import com.mall.b2bp.services.contract.DeliverMethodService;
import com.mall.b2bp.services.contract.PayMethodService;
import com.mall.b2bp.services.parm.ParmService;
import com.mall.b2bp.utils.ConstantUtil;
import com.mall.b2bp.vos.ResponseData;
import com.mall.b2bp.vos.contract.ContractVo;
import com.mall.b2bp.vos.contract.DeliverMethodVo;
import com.mall.b2bp.vos.contract.PayMethodVo;
import com.mall.b2bp.vos.contract.SaveContractPayAndDeliverVo;
import com.mall.b2bp.vos.parm.ParmVo;
import com.mall.b2bp.vos.user.UserVo;

/**
 * Created by Harlan King 2017/3/21.
 */
@Controller("ContractController")
@RequestMapping(value = "/contract")
public class ContractController extends BaseConroller {
    private ContractService contractService;

    private PayMethodService payMethodService;
    private DeliverMethodService deliverMethodService;
    private ParmService parmService;

    public ContractService getContractService() {
        return contractService;
    }

    @Resource
    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }

    @Resource
    public void setPayMethodService(PayMethodService payMethodService) {
        this.payMethodService = payMethodService;
    }

    public DeliverMethodService getDeliverMethodService() {
        return deliverMethodService;
    }

    @Resource
    public void setDeliverMethodService(DeliverMethodService deliverMethodService) {
        this.deliverMethodService = deliverMethodService;
    }

    public ParmService getParmService() {
        return parmService;
    }

    @Resource
    public void setParmService(ParmService parmService) {
        this.parmService = parmService;
    }

    @Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER"})
    @RequestMapping(value = "/contractList", method = {RequestMethod.POST,
            RequestMethod.GET}, produces = {"application/json"})
    @ResponseBody
    public List<ContractVo> selectContracts(BigDecimal supplierId) {
        return contractService.selectContracts(supplierId);
    }

    @Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER"})
    @RequestMapping(value = "/save", method = {RequestMethod.POST,
            RequestMethod.GET}, produces = {"application/json"})
    @ResponseBody
    public ResponseData<ContractVo> save(@RequestBody ContractVo contract) {
        ResponseData<ContractVo> responseDate = new ResponseData<ContractVo>();
        if (contract == null || contract.getRef() == null || contract.getRef().isEmpty()) {
            responseDate.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
            responseDate.setErrorMessage("ref_null");
            return responseDate;
        }
        if (contract.getStartDate() == null) {
            responseDate.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
            responseDate.setErrorMessage("start_date_null");
            return responseDate;

        }
        try {


            int i = contractService.selectCountBySupplierAndRef(contract.getSupplierId(), contract.getRef());
            if (contract.getId() == null) {
                if (i > 0) {
                    responseDate.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
                    responseDate.setErrorMessage("ref_equal");
                    return responseDate;
                }
            } else {
                ContractModel model = contractService.selectBySupplierAndRef(contract.getSupplierId(), contract.getRef());
                if (model.getId().compareTo(contract.getId()) != 0) {
                    responseDate.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
                    responseDate.setErrorMessage("ref_equal");
                    return responseDate;
                }
            }
            if (contract.getEndDate() != null) {
                if (contract.getEndDate().compareTo(contract.getStartDate()) == -1) {
                    responseDate.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
                    responseDate.setErrorMessage("end_date_before_start_date");
                    return responseDate;
                }
            }
            String userId = sessionService.getCurrentUser().getId().toString();
            if (contract.getId() == null || contract.getCreatedBy() == null) {
                contract.setCreatedBy(userId);
                contract.setCreatedDate(new Date());
            }
            contract.setLastUpdatedBy(userId);
            contract.setLastUpdatedDate(new Date());
           if(contract.getPayList()!=null&&contract.getPayList().size()>0){
               for (ContractPayMethodModel pay : contract.getPayList()) {
                   pay.setCreatedBy(userId);
                   pay.setCreatedDate(new Date());
                   pay.setLastUpdatedBy(userId);
                   pay.setLastUpdatedDate(new Date());
               }
           }
            if(contract.getDeliverList()!=null&&contract.getDeliverList().size()>0){
                for (ContractDeliverMethodModel deliver : contract.getDeliverList()) {
                    deliver.setCreatedBy(userId);
                    deliver.setCreatedDate(new Date());
                    deliver.setLastUpdatedBy(userId);
                    deliver.setLastUpdatedDate(new Date());
                }
            }

            ContractModel contractModel =  contractService.save(contract);
            if (contract.getPayList() != null && contract.getPayList().size() > 0) {
                for (ContractPayMethodModel pay : contract.getPayList()) {
                    pay.setContractId(contractModel.getId());
                }
            }
            if (contract.getDeliverList() != null && contract.getDeliverList().size() > 0) {
                for (ContractDeliverMethodModel deliver : contract.getDeliverList()) {
                    deliver.setContractId(contractModel.getId());
                }
            }
            if (contract.getPayList() != null && contract.getPayList().size() > 0) {
                payMethodService.deleteByContractId(contract.getId());
                payMethodService.save(contract.getPayList());
            } else {
                payMethodService.deleteByContractId(contract.getId());
            }
            if (contract.getDeliverList() != null && contract.getDeliverList().size() > 0) {
                deliverMethodService.deleteByContractId(contract.getId());
                deliverMethodService.save(contract.getDeliverList());
            } else {
                deliverMethodService.deleteByContractId(contract.getId());
            }
            responseDate.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
            return responseDate;
        } catch (Exception e) {
            responseDate.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
            e.printStackTrace();
            return responseDate;
        }
    }

    @Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER"})
    @RequestMapping(value = "/toEdit", method = {RequestMethod.POST,
            RequestMethod.GET}, produces = {"application/json"})
    @ResponseBody
    public Map<String, Object> toEdit(BigDecimal id) {

        Map<String, Object> result = new HashMap<>();
        ParmVo parmVo = new ParmVo();
        List<ParmVo> methods;
        parmVo.setSegment("金流");
        methods = parmService.getListByCriteria(parmVo);
        result.put("payMethods", methods);
        parmVo.setSegment("物流");
        methods = parmService.getListByCriteria(parmVo);
        result.put("deliverMethods", methods);
        if(id.compareTo(new BigDecimal(-1))==0){
            return result;
        }
        ContractVo contract = contractService.selectVOById(id);
        result.put("contract",contract);

        result.put("contractPayMethods", (contract==null||contract.getPayList()==null)?payMethodService.selectByContractId(id):contract.getPayList());

        result.put("contractDeliverMethods",(contract==null||contract.getDeliverList()==null)?deliverMethodService.selectByContractId(id):contract.getDeliverList());
        return result;
    }

    @Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER"})
    @RequestMapping(value = "/showContractPay", method = {RequestMethod.POST,
            RequestMethod.GET}, produces = {"application/json"})
    @ResponseBody
    public Map<String, List<?>> showContractPay(BigDecimal id) {
        ParmVo parmVo = new ParmVo();
        parmVo.setSegment("金流");
        List<ParmVo> payMethod = parmService.getListByCriteria(parmVo);
        List<PayMethodVo> contractPayList = payMethodService.selectByContractId(id);
        Map<String, List<?>> result = new HashMap<>();
        result.put("payMethods", payMethod);
        result.put("contractPayMethods", contractPayList);
        return result;
    }

    @Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER"})
    @RequestMapping(value = "/showContractDeliver", method = {RequestMethod.POST,
            RequestMethod.GET}, produces = {"application/json"})
    @ResponseBody
    public Map<String, List<?>> showContractDeliver(BigDecimal id) {
        ParmVo parmVo = new ParmVo();
        parmVo.setSegment("物流");
        List<ParmVo> payMethod = parmService.getListByCriteria(parmVo);
        List<DeliverMethodVo> contractPayList = deliverMethodService.selectByContractId(id);
        Map<String, List<?>> result = new HashMap<>();
        result.put("deliverMethods", payMethod);
        result.put("contractDeliverMethods", contractPayList);
        return result;
    }

    @Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER"})
    @RequestMapping(value = "/saveContractDeliver", method = {RequestMethod.POST}, produces = {"application/json"})
    @ResponseBody
    public ResponseData<ContractVo> saveContractDeliver(@RequestBody SaveContractPayAndDeliverVo toSave) {
        ResponseData<ContractVo> responseDate = new ResponseData<ContractVo>();

        if (toSave.getIds() != null && toSave.getIds().length > 0) {
            ContractDeliverMethodModel model;
            UserVo user = sessionService.getCurrentUser();
            List<ContractDeliverMethodModel> list = new ArrayList<>();
            for (BigDecimal i : toSave.getIds()) {
                model = new ContractDeliverMethodModel();
                model.setContractId(toSave.getId());
                model.setDeliverMethodId(i);
                model.setCreatedBy(user.getId().toString());
                model.setCreatedDate(new Date());
                model.setLastUpdatedBy(user.getId().toString());
                model.setLastUpdatedDate(new Date());
                list.add(model);
            }
            try {
                deliverMethodService.deleteByContractId(toSave.getId());
                deliverMethodService.save(list);
                responseDate.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
            } catch (Exception e) {
                responseDate.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
            }
        } else {
            try {
                deliverMethodService.deleteByContractId(toSave.getId());
                responseDate.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
            } catch (Exception e) {
                responseDate.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);

            }
        }

        return responseDate;
    }

    @Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER"})
    @RequestMapping(value = "/saveContractPay", method = {RequestMethod.POST}, produces = {"application/json"})
    @ResponseBody
    public ResponseData<ContractVo> saveContractPay(@RequestBody SaveContractPayAndDeliverVo toSave) {
        ResponseData<ContractVo> responseDate = new ResponseData<ContractVo>();

        if (toSave.getIds() != null && toSave.getIds().length > 0) {
            ContractPayMethodModel model;
            UserVo user = sessionService.getCurrentUser();
            List<ContractPayMethodModel> list = new ArrayList<>();
            for (BigDecimal i : toSave.getIds()) {
                model = new ContractPayMethodModel();
                model.setContractId(toSave.getId());
                model.setPayMethodId(i);
                model.setCreatedBy(user.getId().toString());
                model.setCreatedDate(new Date());
                model.setLastUpdatedBy(user.getId().toString());
                model.setLastUpdatedDate(new Date());
                list.add(model);
            }
            try {
                payMethodService.deleteByContractId(toSave.getId());
                payMethodService.save(list);
                responseDate.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);

            } catch (Exception e) {
                e.printStackTrace();
                responseDate.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
            }
        } else {
            try {
                payMethodService.deleteByContractId(toSave.getId());
                responseDate.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);

            } catch (Exception e) {
                responseDate.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
            }
        }

        return responseDate;
    }

    @Secured({"ROLE_SYSTEMADMIN", "ROLE_SUPPLIER"})
    @RequestMapping(value = "/deleteContract", method = {RequestMethod.POST,
            RequestMethod.GET}, produces = {"application/json"})
    @ResponseBody
    public ResponseData<ContractVo> deleteContract(@RequestBody BigDecimal[] ids) {
        ResponseData<ContractVo> responseDate = new ResponseData<ContractVo>();
        if (ids != null && ids.length > 0) {
            try {
                contractService.delete(ids);
                responseDate.setErrorType(ConstantUtil.ERROR_TYPE_SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                responseDate.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
            }
        } else {
            responseDate.setErrorType(ConstantUtil.ERROR_TYPE_DANGER);
        }

        return responseDate;
    }
}
