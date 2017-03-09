package org.recap.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.recap.RecapConstants;
import org.recap.model.jpa.InstitutionEntity;
import org.recap.model.jpa.RoleEntity;
import org.recap.model.jpa.UsersEntity;
import org.recap.model.userManagement.UserDetailsForm;
import org.recap.model.userManagement.UserRoleForm;
import org.recap.model.userManagement.UserRoleService;
import org.recap.repository.jpa.InstitutionDetailsRepository;
import org.recap.repository.jpa.RolesDetailsRepositorty;
import org.recap.repository.jpa.UserDetailsRepository;
import org.recap.security.UserManagement;
import org.recap.util.UserAuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dharmendrag on 23/12/16.
 */
@Controller
public class UserRoleController {

    private static final Logger logger = LoggerFactory.getLogger(UserRoleController.class);

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    RolesDetailsRepositorty rolesDetailsRepositorty;

    @Autowired
    InstitutionDetailsRepository institutionDetailsRepository;

    @Autowired
    private UserAuthUtil userAuthUtil;

    public Logger getLogger() {
        return logger;
    }

    public UserAuthUtil getUserAuthUtil() {
        return userAuthUtil;
    }

    public void setUserAuthUtil(UserAuthUtil userAuthUtil) {
        this.userAuthUtil = userAuthUtil;
    }

    public UserRoleService getUserRoleService() {
        return userRoleService;
    }

    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    public UserDetailsRepository getUserDetailsRepository() {
        return userDetailsRepository;
    }

    public void setUserDetailsRepository(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    public RolesDetailsRepositorty getRolesDetailsRepositorty() {
        return rolesDetailsRepositorty;
    }

    public void setRolesDetailsRepositorty(RolesDetailsRepositorty rolesDetailsRepositorty) {
        this.rolesDetailsRepositorty = rolesDetailsRepositorty;
    }

    public InstitutionDetailsRepository getInstitutionDetailsRepository() {
        return institutionDetailsRepository;
    }

    public void setInstitutionDetailsRepository(InstitutionDetailsRepository institutionDetailsRepository) {
        this.institutionDetailsRepository = institutionDetailsRepository;
    }

    @RequestMapping(value = "/userRoles")
    public String showUserRoles(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        boolean authenticated = getUserAuthUtil().authorizedUser(RecapConstants.SCSB_SHIRO_USER_ROLE_URL, (UsernamePasswordToken) session.getAttribute(UserManagement.USER_TOKEN));
        if (authenticated) {
            logger.info(RecapConstants.USERS_TAB_CLICKED);
            UserRoleForm userRoleForm = new UserRoleForm();
            UserDetailsForm userDetailsForm = getUserAuthUtil().getUserDetails(session, UserManagement.BARCODE_RESTRICTED_PRIVILEGE);
            getAndSetRolesAndInstitutions(userRoleForm, userDetailsForm);
            userRoleForm.setAllowCreateEdit(true);
            model.addAttribute(RecapConstants.USER_ROLE_FORM, userRoleForm);
            model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.USER_ROLES_SEARCH);
            return RecapConstants.VIEW_SEARCH_RECORDS;
        } else {
            return UserManagement.unAuthorizedUser(session,"Users",logger);
        }

    }

    private void getAndSetRolesAndInstitutions(UserRoleForm userRoleForm, UserDetailsForm userDetailsForm) {
        List<Object> roles = getUserRoleService().getRoles(UserManagement.SUPER_ADMIN.getIntegerValues());
        List<Object> institutions = getUserRoleService().getInstitutions(userDetailsForm.isSuperAdmin(), userDetailsForm.getLoginInstitutionId());
        userRoleForm.setRoles(roles);
        userRoleForm.setInstitutions(institutions);
    }

    //Search
    @ResponseBody
    @RequestMapping(value = "/userRoles/searchUsers", method = RequestMethod.POST)
    public ModelAndView searchUserRole(@Valid @ModelAttribute("userRoleForm") UserRoleForm userRoleForm, Model model, HttpServletRequest request) {
        getLogger().info("Users - Search button Clicked");
        HttpSession session = request.getSession();
        boolean authenticated = getUserAuthUtil().authorizedUser(RecapConstants.SCSB_SHIRO_USER_ROLE_URL, (UsernamePasswordToken) session.getAttribute(UserManagement.USER_TOKEN));
        if (authenticated) {
            getLogger().info("Users Tab Clicked");
        try {
            priorSearch(userRoleForm, request);
            userRoleForm.setShowPagination(true);
            model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.USER_ROLES_SEARCH);
        } catch (Exception e) {
            logger.error(RecapConstants.LOG_ERROR,e);
        }
        return new ModelAndView(RecapConstants.VIEW_REQUEST_RESULT_TABLE, RecapConstants.USER_ROLE_FORM, userRoleForm);
        } else {
            return new ModelAndView(RecapConstants.VIEW_LOGIN);
        }
    }


    //DeleteUser Screen
    @ResponseBody
    @RequestMapping(value = "/userRoles/deleteUser", method = RequestMethod.GET)
    public ModelAndView deleteUserRole(String networkLoginId, Integer userId, HttpServletRequest request, Integer pagesize, Integer pageNumber, Integer totalPageCount) {
        HttpSession session = request.getSession();
        boolean authenticated = getUserAuthUtil().authorizedUser(RecapConstants.SCSB_SHIRO_USER_ROLE_URL, (UsernamePasswordToken) session.getAttribute(UserManagement.USER_TOKEN));
        if (authenticated) {
            UserDetailsForm userDetailsForm = getUserAuthUtil().getUserDetails(request.getSession(), UserManagement.BARCODE_RESTRICTED_PRIVILEGE);
            getLogger().info("User - Delete User clicked");
            UsersEntity usersEntity = getUserDetailsRepository().findByUserId(userId);
            UserRoleForm userRoleForm = new UserRoleForm();
            userRoleForm.setAfterDelPageSize(pagesize);
            userRoleForm.setAfterDelPageNumber(pageNumber);
            userRoleForm.setAfterDelTotalPageCount(totalPageCount);
            getAndSetRolesAndInstitutions(userRoleForm, userDetailsForm);
            userRoleForm.setEditNetworkLoginId(usersEntity.getLoginId());
            userRoleForm.setEditUserDescription(usersEntity.getUserDescription());
            userRoleForm.setEditUserId(userId);
            userRoleForm.setUserId(userId);
            userRoleForm.setEmailId(usersEntity.getEmailId());
            userRoleForm.setEditEmailId(usersEntity.getEmailId());
            List<RoleEntity> roleEntityList = usersEntity.getUserRole();
            List<Integer> roleIds = new ArrayList<>();
            if (roleEntityList != null) {
                for (RoleEntity roleEntity : roleEntityList) {
                    roleIds.add(roleEntity.getRoleId());

                }
            }
            userRoleForm.setEditSelectedForCreate(roleIds);
            userRoleForm.setShowSelectedForCreate(userRoleForm.getEditSelectedForCreate());
            userRoleForm.setEditInstitutionId(usersEntity.getInstitutionId());
            userRoleForm.setShowUserSearchView(false);
            return new ModelAndView(RecapConstants.USER_ROLES_SEARCH, RecapConstants.USER_ROLE_FORM, userRoleForm);
        } else {
            return new ModelAndView(RecapConstants.VIEW_LOGIN);
        }
    }


    //DeleteUser On Confirm
    @ResponseBody
    @RequestMapping(value = "/userRoles/delete", method = RequestMethod.GET)
    public ModelAndView deleteUser(@Valid @ModelAttribute("userRoleForm") UserRoleForm userRoleForm, Model model, Integer userId, String networkLoginId, Integer pageNumber, Integer totalPageCount, Integer pageSize, HttpServletRequest request) {
        HttpSession session = request.getSession();
        boolean authenticated = getUserAuthUtil().authorizedUser(RecapConstants.SCSB_SHIRO_USER_ROLE_URL, (UsernamePasswordToken) session.getAttribute(UserManagement.USER_TOKEN));
        if (authenticated) {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUserId(userId);
        try {
            getUserDetailsRepository().delete(usersEntity);
            userRoleForm.setDeletedSuccessMsg(true);
            userRoleForm.setMessage(networkLoginId + RecapConstants.USER_DELETED_SUCCESS_MESSAGE);
            priorSearch(userRoleForm, request);
            userRoleForm.setAfterDelPageNumber(pageNumber);
            userRoleForm.setAfterDelTotalPageCount(totalPageCount);
            userRoleForm.setAfterDelPageSize(pageSize);
            userRoleForm.setShowPagination(true);
            userRoleForm.setShowResults(true);
            model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.USER_ROLES_SEARCH);
        } catch (Exception e) {
            logger.error(RecapConstants.LOG_ERROR,e);
        }
        userRoleForm.setShowUserSearchView(true);
        return new ModelAndView(RecapConstants.USER_ROLES_SEARCH, RecapConstants.USER_ROLE_FORM, userRoleForm);
        } else {
            return new ModelAndView(RecapConstants.VIEW_LOGIN);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/userRoles/first", method = RequestMethod.POST)
    public ModelAndView searchFirstPage(@ModelAttribute("userForm") UserRoleForm userRoleForm, Model model, HttpServletRequest request) {
        logger.info("Users - Search First Page button Clicked");
        HttpSession session = request.getSession();
        boolean authenticated = getUserAuthUtil().authorizedUser(RecapConstants.SCSB_SHIRO_USER_ROLE_URL, (UsernamePasswordToken) session.getAttribute(UserManagement.USER_TOKEN));
        if (authenticated) {
        userRoleForm.resetPageNumber();
        priorSearch(userRoleForm, request);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.USER_ROLES_SEARCH);
        return new ModelAndView(RecapConstants.VIEW_REQUEST_RESULT_TABLE, RecapConstants.USER_ROLE_FORM, userRoleForm);
        } else {
            return new ModelAndView(RecapConstants.VIEW_LOGIN);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/userRoles/next", method = RequestMethod.POST)
    public ModelAndView searchNextPage(@ModelAttribute("userForm") UserRoleForm userRoleForm, Model model, HttpServletRequest request) {
        getLogger().info("Users - Search Next Page button Clicked");
        return getPaginatioinModelAndView(userRoleForm, model, request);
    }

    @ResponseBody
    @RequestMapping(value = "/userRoles/previous", method = RequestMethod.POST)
    public ModelAndView searchPreviousPage(@ModelAttribute("userForm") UserRoleForm userRoleForm, Model model, HttpServletRequest request) {
        getLogger().info("Users - Search Previous Page button Clicked");
        return getPaginatioinModelAndView(userRoleForm, model, request);
    }

    @ResponseBody
    @RequestMapping(value = "/userRoles/last", method = RequestMethod.POST)
    public ModelAndView searchLastPage(@ModelAttribute("userForm") UserRoleForm userRoleForm, Model model, HttpServletRequest request) {
        getLogger().info("Users - Search Last Page button Clicked");
        return getPaginatioinModelAndView(userRoleForm, model, request);
    }

    @ResponseBody
    @RequestMapping(value = "/userRoles/createUser", method = RequestMethod.POST)
    public ModelAndView createUserRequest(@ModelAttribute("userRoleForm") UserRoleForm userRoleForm, HttpServletRequest request) {
        logger.info("User - Create Request clicked");
        HttpSession session = request.getSession();
        boolean authenticated = getUserAuthUtil().authorizedUser(RecapConstants.SCSB_SHIRO_USER_ROLE_URL, (UsernamePasswordToken) session.getAttribute(UserManagement.USER_TOKEN));
        if (authenticated) {
        UserDetailsForm userDetailsForm = userAuthUtil.getUserDetails(request.getSession(), UserManagement.BARCODE_RESTRICTED_PRIVILEGE);
            getAndSetRolesAndInstitutions(userRoleForm, userDetailsForm);
        Object userName = session.getAttribute(UserManagement.USER_NAME);
        userRoleForm.setCreatedBy(String.valueOf(userName));
        UsersEntity usersEntity = userRoleService.saveNewUserToDB(userRoleForm);
        if (usersEntity != null) {
            userRoleForm.setShowCreateSuccess(true);
            userRoleForm.setAllowCreateEdit(true);
        }
        userRoleForm.setShowUserSearchView(false);
        return new ModelAndView(RecapConstants.USER_ROLES_SEARCH, RecapConstants.USER_ROLE_FORM, userRoleForm);
        } else {
        return new ModelAndView(RecapConstants.VIEW_LOGIN);
        }
    }

    //Edit User
    @ResponseBody
    @RequestMapping(value = "/userRoles/editUser", method = RequestMethod.GET)
    public ModelAndView editUser(@ModelAttribute("userId") Integer userId, @ModelAttribute("networkLoginId") String networkLoginId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        boolean authenticated = getUserAuthUtil().authorizedUser(RecapConstants.SCSB_SHIRO_USER_ROLE_URL, (UsernamePasswordToken) session.getAttribute(UserManagement.USER_TOKEN));
        if (authenticated) {
        UserDetailsForm userDetailsForm = userAuthUtil.getUserDetails(request.getSession(), UserManagement.BARCODE_RESTRICTED_PRIVILEGE);
        logger.info("User - editUser clicked");
        UsersEntity usersEntity = userDetailsRepository.findByUserId(userId);
        UserRoleForm userRoleForm = new UserRoleForm();
            getAndSetRolesAndInstitutions(userRoleForm, userDetailsForm);
        userRoleForm.setEditNetworkLoginId(usersEntity.getLoginId());
        userRoleForm.setEditUserDescription(usersEntity.getUserDescription());
        userRoleForm.setEditUserId(userId);
        userRoleForm.setUserId(userId);
        userRoleForm.setEmailId(usersEntity.getEmailId());
        userRoleForm.setEditEmailId(usersEntity.getEmailId());
        List<RoleEntity> roleEntityList = usersEntity.getUserRole();
        List<Integer> roleIds = new ArrayList<>();
        if (roleEntityList != null) {
            for (RoleEntity roleEntity : roleEntityList) {
                roleIds.add(roleEntity.getRoleId());

            }
        }
        userRoleForm.setEditSelectedForCreate(roleIds);
        userRoleForm.setShowSelectedForCreate(userRoleForm.getEditSelectedForCreate());
        userRoleForm.setEditInstitutionId(usersEntity.getInstitutionId());
        userRoleForm.setShowUserSearchView(false);
        return new ModelAndView(RecapConstants.USER_ROLES_SEARCH, RecapConstants.USER_ROLE_FORM, userRoleForm);
        } else {
            return new ModelAndView(RecapConstants.VIEW_LOGIN);
        }
    }

    //Save Edited User
    @ResponseBody
    @RequestMapping(value = "/userRoles/saveEditUserDetails", method = RequestMethod.GET)
    public ModelAndView saveEditUserDetails(@ModelAttribute("userId") Integer userId, @ModelAttribute("networkLoginId") String networkLoginId,
                                            @ModelAttribute("userDescription") String userDescription,
                                            @ModelAttribute("institutionId") Integer institutionId,
                                            @ModelAttribute("userEmailId") String userEmailId,
                                            HttpServletRequest request) {
        HttpSession session = request.getSession();
        boolean authenticated = getUserAuthUtil().authorizedUser(RecapConstants.SCSB_SHIRO_USER_ROLE_URL, (UsernamePasswordToken) session.getAttribute(UserManagement.USER_TOKEN));
        if (authenticated) {
        UserRoleForm userRoleForm = new UserRoleForm();
        UserDetailsForm userDetailsForm = userAuthUtil.getUserDetails(request.getSession(), UserManagement.BARCODE_RESTRICTED_PRIVILEGE);
        List<Integer> roleIds = new ArrayList<>();
        String[] parameterValues = request.getParameterValues("roleIds[]");
        for (String parameterValue : parameterValues) {
            roleIds.add(Integer.valueOf(parameterValue));
        }
        userRoleForm.setMessage(networkLoginId + RecapConstants.EDITED_AND_SAVED);
            getAndSetRolesAndInstitutions(userRoleForm, userDetailsForm);
        userRoleForm.setUserId(userId);
        Object userName = session.getAttribute(UserManagement.USER_NAME);
        userRoleForm.setLastUpdatedBy(String.valueOf(userName));
        UsersEntity usersEntity = userRoleService.saveEditedUserToDB(userId, networkLoginId, userDescription, institutionId, roleIds, userEmailId,userRoleForm);
        if (usersEntity != null) {
            userRoleForm.setShowEditSuccess(true);
            userRoleForm.setEditNetworkLoginId(usersEntity.getLoginId());
            userRoleForm.setEditUserDescription(usersEntity.getUserDescription());
            userRoleForm.setUserId(userRoleForm.getUserId());
            userRoleForm.setEditUserId(userRoleForm.getUserId());
            List<RoleEntity> roleEntityList = usersEntity.getUserRole();
            List<Integer> roleIdss = new ArrayList<>();
            if (roleEntityList != null) {
                for (RoleEntity roleEntity : roleEntityList) {
                    roleIdss.add(roleEntity.getRoleId());
                }
            }
            userRoleForm.setEditSelectedForCreate(roleIdss);
            userRoleForm.setShowSelectedForCreate(userRoleForm.getEditSelectedForCreate());
            userRoleForm.setEditInstitutionId(usersEntity.getInstitutionId());
            userRoleForm.setEditEmailId(usersEntity.getEmailId());
        } else {
            userRoleForm.setShowEditError(true);
            userRoleForm.setEditErromessage(networkLoginId +  RecapConstants.USER_ALREADY_EXISTS);
            userRoleForm.setEditNetworkLoginId(networkLoginId);
            userRoleForm.setEditUserDescription(userDescription);
            userRoleForm.setEditSelectedForCreate(roleIds);
            userRoleForm.setShowSelectedForCreate(userRoleForm.getEditSelectedForCreate());
            userRoleForm.setEditInstitutionId(institutionId);
            userRoleForm.setEditEmailId(userEmailId);
        }
            userRoleForm.setShowUserSearchView(false);
        return new ModelAndView(RecapConstants.USER_ROLES_SEARCH, RecapConstants.USER_ROLE_FORM, userRoleForm);
        } else {
            return new ModelAndView(RecapConstants.VIEW_LOGIN);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/userRoles", method = RequestMethod.POST, params = "action=goBack")
    public ModelAndView goBack(@ModelAttribute("userRoleForm") UserRoleForm userRoleForm,HttpServletRequest request){
        HttpSession session = request.getSession();
        boolean authenticated = userAuthUtil.authorizedUser(RecapConstants.SCSB_SHIRO_USER_ROLE_URL, (UsernamePasswordToken) session.getAttribute(UserManagement.USER_TOKEN));
        if (authenticated) {
            logger.info(RecapConstants.USERS_TAB_CLICKED);
            UserDetailsForm userDetailsForm = getUserAuthUtil().getUserDetails(session, UserManagement.BARCODE_RESTRICTED_PRIVILEGE);
            List<Object> roles = getUserRoleService().getRoles(UserManagement.SUPER_ADMIN.getIntegerValues());
            List<Object> institutions = getUserRoleService().getInstitutions(userDetailsForm.isSuperAdmin(), userDetailsForm.getLoginInstitutionId());
            userRoleForm.setRoles(roles);
            userRoleForm.setInstitutions(institutions);
        }
        userRoleForm.setShowUserSearchView(true);
        return new ModelAndView("userRolesSearch", "userRoleForm", userRoleForm);
    }


    @ResponseBody
    @RequestMapping(value = "/userRoles", method = RequestMethod.POST, params = "action=editClearPage")
    public ModelAndView editClearPage(@ModelAttribute("userRoleForm") UserRoleForm userRoleForm,HttpServletRequest request,
                                      Model model) {
        HttpSession session = request.getSession();
        boolean authenticated = userAuthUtil.authorizedUser(RecapConstants.SCSB_SHIRO_USER_ROLE_URL, (UsernamePasswordToken) session.getAttribute(UserManagement.USER_TOKEN));
        if (authenticated) {
            logger.info(RecapConstants.USERS_TAB_CLICKED);
            UserDetailsForm userDetailsForm = userAuthUtil.getUserDetails(session, UserManagement.BARCODE_RESTRICTED_PRIVILEGE);
            List<Object> roles = userRoleService.getRoles(UserManagement.SUPER_ADMIN.getIntegerValues());
            List<Object> institutions = userRoleService.getInstitutions(userDetailsForm.isSuperAdmin(), userDetailsForm.getLoginInstitutionId());
            userRoleForm.setRoles(roles);
            userRoleForm.setInstitutions(institutions);
            UsersEntity usersEntity = userDetailsRepository.findByUserId(userRoleForm.getUserId());
            if (usersEntity != null){
                userRoleForm.setEditNetworkLoginId(usersEntity.getLoginId());
                userRoleForm.setEditUserDescription(usersEntity.getUserDescription());
                userRoleForm.setUserId(userRoleForm.getUserId());
                userRoleForm.setEditUserId(userRoleForm.getUserId());
                List<RoleEntity> roleEntityList = usersEntity.getUserRole();
                List<Integer> roleIdss = new ArrayList<>();
                if (roleEntityList != null) {
                    for (RoleEntity roleEntity : roleEntityList) {
                        roleIdss.add(roleEntity.getRoleId());
                    }
                }
                userRoleForm.setEditSelectedForCreate(roleIdss);
                userRoleForm.setShowSelectedForCreate(userRoleForm.getEditSelectedForCreate());
                userRoleForm.setEditInstitutionId(usersEntity.getInstitutionId());
                userRoleForm.setEditEmailId(usersEntity.getEmailId());
            }

        }
        userRoleForm.setShowUserSearchView(false);
        return new ModelAndView("userRolesSearch", "userRoleForm", userRoleForm);
    }


    private void priorSearch(UserRoleForm userRoleForm, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(UserManagement.USER_ID);
        UserDetailsForm userDetailsForm = getUserAuthUtil().getUserDetails(session, UserManagement.BARCODE_RESTRICTED_PRIVILEGE);
        List<Object> roles = getUserRoleService().getRoles(UserManagement.SUPER_ADMIN.getIntegerValues());
        List<Object> institutions = getUserRoleService().getInstitutions(userDetailsForm.isSuperAdmin(), userDetailsForm.getLoginInstitutionId());
        userRoleForm.setUserId(userId);
        userRoleForm.setInstitutionId(userDetailsForm.getLoginInstitutionId());
        userRoleForm.setRoles(roles);
        userRoleForm.setInstitutions(institutions);
        userRoleForm.setAllowCreateEdit(true);
        userRoleForm.setSubmitted(true);

        searchAndSetResult(userRoleForm, userDetailsForm.isSuperAdmin(), userId);
    }

    private void searchAndSetResult(UserRoleForm userRoleForm, boolean superAdmin, Integer userId) {
        if (StringUtils.isBlank(userRoleForm.getSearchNetworkId()) && StringUtils.isBlank(userRoleForm.getUserEmailId())) {
            logger.debug("Search All Users");
            Page<UsersEntity> usersEntities = getUserRoleService().searchUsers(userRoleForm, superAdmin);
            userRoleForm.setUserRoleFormList(setFormValues(usersEntities.getContent(), userId));
            userRoleForm.setShowResults(true);
            if(superAdmin){
                userRoleForm.setTotalRecordsCount(String.valueOf(usersEntities.getTotalElements()));
            }else{
                userRoleForm.setTotalRecordsCount(String.valueOf(usersEntities.getTotalElements() - 1));
            }
            userRoleForm.setTotalPageCount(usersEntities.getTotalPages());
        } else if (StringUtils.isNotBlank(userRoleForm.getSearchNetworkId()) && StringUtils.isBlank(userRoleForm.getUserEmailId())) {
            logger.debug("Search Users By NetworkId :" + userRoleForm.getSearchNetworkId());
            Page<UsersEntity> usersEntities = getUserRoleService().searchByNetworkId(userRoleForm, superAdmin);
            getUsersInformation(userRoleForm, superAdmin, userId, usersEntities,RecapConstants.NETWORK_LOGIN_ID_DOES_NOT_EXIST);
        } else if (StringUtils.isBlank(userRoleForm.getSearchNetworkId()) && StringUtils.isNotBlank(userRoleForm.getUserEmailId())) {
            logger.debug("Search Users by Email Id:" + userRoleForm.getUserEmailId());
            Page<UsersEntity> usersEntities = getUserRoleService().searchByUserEmailId(userRoleForm, superAdmin);
            getUsersInformation(userRoleForm, superAdmin, userId, usersEntities, RecapConstants.EMAILID_ID_DOES_NOT_EXIST);
        } else if (StringUtils.isNotBlank(userRoleForm.getSearchNetworkId()) && StringUtils.isNotBlank(userRoleForm.getUserEmailId())) {
            logger.debug("Search Users by Network Id : " + userRoleForm.getSearchNetworkId() + " and Email Id : " + userRoleForm.getUserEmailId());
            Page<UsersEntity> usersEntities = getUserRoleService().searchByNetworkIdAndUserEmailId(userRoleForm, superAdmin);
            getUsersInformation(userRoleForm, superAdmin, userId, usersEntities,RecapConstants.NETWORK_LOGIN_ID_AND_EMAILID_ID_DOES_NOT_EXIST);
        } else {
            userRoleForm.setShowResults(false);
        }
    }

    private void getUsersInformation(UserRoleForm userRoleForm, boolean superAdmin, Integer userId, Page<UsersEntity> usersEntities,String message) {
        List<UsersEntity> userEntity = usersEntities.getContent();
        if (userEntity != null && !userEntity.isEmpty()) {
            userRoleForm.setUserRoleFormList(setFormValues(usersEntities.getContent(), userId));
            userRoleForm.setShowResults(true);
            if(superAdmin){
                userRoleForm.setTotalRecordsCount(String.valueOf(usersEntities.getTotalElements()));
            }else{
                userRoleForm.setTotalRecordsCount(String.valueOf(usersEntities.getTotalElements() - 1));
            }
            userRoleForm.setTotalPageCount(usersEntities.getTotalPages());
        } else {
            userRoleForm.setMessage(message);
            userRoleForm.setShowErrorMessage(true);
            userRoleForm.setShowResults(false);
        }
    }

    private List<UserRoleForm> setFormValues(List<UsersEntity> usersEntities, Integer userId) {
        List<UserRoleForm> userRoleFormList = new ArrayList<>();
        appendValues(usersEntities, userRoleFormList, userId);
        return userRoleFormList;
    }

    private void appendValues(Collection<UsersEntity> usersEntities, List<UserRoleForm> userRoleFormList, Integer userId) {
        for (UsersEntity usersEntity : usersEntities) {
            InstitutionEntity institutionEntity = usersEntity.getInstitutionEntity();
            if (!userId.equals(usersEntity.getUserId()) && !usersEntity.getUserId().equals(UserManagement.SUPER_ADMIN.getIntegerValues())) {
                UserRoleForm userRoleDeatailsForm = new UserRoleForm();
                StringBuilder rolesBuffer = new StringBuilder();
                userRoleDeatailsForm.setUserId(usersEntity.getUserId());
                userRoleDeatailsForm.setInstitutionId(institutionEntity.getInstitutionId());
                userRoleDeatailsForm.setInstitutionName(institutionEntity.getInstitutionName());
                userRoleDeatailsForm.setNetworkLoginId(usersEntity.getLoginId());
                for (RoleEntity roleEntity : usersEntity.getUserRole()) {
                    rolesBuffer.append(roleEntity.getRoleName() + ",");
                }
                userRoleDeatailsForm.setRoleName(roles(rolesBuffer.toString(), ","));
                userRoleFormList.add(userRoleDeatailsForm);//Added all user's details
            }
        }
    }

    private ModelAndView getPaginatioinModelAndView(@ModelAttribute("userForm") UserRoleForm userRoleForm, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        boolean authenticated = getUserAuthUtil().authorizedUser(RecapConstants.SCSB_SHIRO_USER_ROLE_URL, (UsernamePasswordToken) session.getAttribute(UserManagement.USER_TOKEN));
        if (authenticated) {
            priorSearch(userRoleForm, request);
            model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.USER_ROLES_SEARCH);
            return new ModelAndView(RecapConstants.VIEW_REQUEST_RESULT_TABLE, RecapConstants.USER_ROLE_FORM, userRoleForm);
        } else {
            return new ModelAndView(RecapConstants.VIEW_LOGIN);
        }
    }

    private String roles(String rolesBuffer, String seperator) {
        if (rolesBuffer != null && rolesBuffer.endsWith(seperator)) {
            return rolesBuffer.substring(0, rolesBuffer.length() - 1);
        }
        return null;
    }
}
