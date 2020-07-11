package org.recap.controller;

import org.recap.RecapCommonConstants;
import org.recap.RecapConstants;
import org.recap.model.jpa.PermissionEntity;
import org.recap.model.jpa.RoleEntity;
import org.recap.model.search.RolesForm;
import org.recap.model.search.RolesSearchResult;
import org.recap.repository.jpa.PermissionsDetailsRepository;
import org.recap.repository.jpa.RolesDetailsRepositorty;
import org.recap.security.UserManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hemalathas on 22/12/16.
 */
@Controller
public class RolesController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(RolesController.class);

    @Autowired
    private RolesDetailsRepositorty rolesDetailsRepositorty;

    @Autowired
    private PermissionsDetailsRepository permissionsRepository;



    /**
     * Render the roles UI page for the scsb application.
     *
     * @param model   the model
     * @param request the request
     * @return the string
     */
    @GetMapping("/roles")
    public String roles(Model model, HttpServletRequest request) {
        HttpSession session=request.getSession(false);
        boolean authenticated = getUserAuthUtil().isAuthenticated(request, RecapConstants.SCSB_SHIRO_ROLE_URL);
        if(authenticated)
        {
            RolesForm rolesForm = new RolesForm();
            model.addAttribute(RecapConstants.ROLES_FORM, rolesForm);
            model.addAttribute(RecapCommonConstants.TEMPLATE, RecapConstants.ROLES);
            return RecapConstants.VIEW_SEARCH_RECORDS;
        }else{
            return UserManagementService.unAuthorizedUser(session,"Roles",logger);
        }
    }

    /**
     * Gets role search results from scsb database and display them as rows in the roles UI page.
     *
     * @param rolesForm the roles form
     * @param model     the model
     * @return the model and view
     */
    @ResponseBody
    @PostMapping(value = "/roles", params = "action=searchRoles")
    public ModelAndView search(@Valid @ModelAttribute("rolesForm") RolesForm rolesForm,
                               Model model) {
        rolesForm.setShowResults(true);
        model.addAttribute(RecapCommonConstants.TEMPLATE, RecapConstants.ROLES);
        setRolesFormSearchResults(rolesForm);
        return new ModelAndView(RecapConstants.VIEW_SEARCH_RECORDS, RecapConstants.ROLES_FORM, rolesForm);
    }

    /**
     * Populate permission names that are available in scsb to the permission names drop down.
     *
     * @param model the model
     * @return the model and view
     */
    @ResponseBody
    @PostMapping(value = "/roles", params = "action=populatePermissionName")
    public ModelAndView populatePermissionNames(Model model) {
        RolesForm rolesForm = getAllPermissionNames();
        model.addAttribute(RecapConstants.ROLES_FORM, rolesForm);
        model.addAttribute(RecapCommonConstants.TEMPLATE, RecapConstants.ROLES);
        return new ModelAndView(RecapConstants.ROLES, RecapConstants.ROLES_FORM, rolesForm);
    }

    /**
     * This is used to add a new role in scsb.
     *
     * @param rolesForm the roles form
     * @param model     the model
     * @param request   the request
     * @return the model and view
     */
    @ResponseBody
    @PostMapping(value = "/roles", params = "action=loadCreateRole")
    public ModelAndView newRole(@Valid @ModelAttribute("rolesForm") RolesForm rolesForm,
                                Model model, HttpServletRequest request) {
        boolean specialCharacterCheck = isSpecialCharacterCheck(rolesForm.getNewRoleName());
        if(!specialCharacterCheck){
            rolesForm.setErrorMessage(RecapConstants.SPECIAL_CHARACTERS_NOT_ALLOWED_CREATE);
            rolesForm.setSelectedPermissionNames(getSelectedPermissionNames(rolesForm.getNewPermissionNames()));
        }
        else{
            HttpSession session = request.getSession(false);
            String username = (String) session.getAttribute(RecapConstants.USER_NAME);
            RoleEntity roleEntity = saveNewRoleToDB(rolesForm, username);
            if(null != roleEntity){
                rolesForm.setMessage(rolesForm.getNewRoleName()+RecapConstants.ADDED_SUCCESSFULLY);
            }else{
                rolesForm.setErrorMessage(rolesForm.getNewRoleName()+RecapConstants.ALREADY_EXISTS);
            }
            rolesForm.setNewRoleName("");
            rolesForm.setNewRoleDescription("");
        }
        rolesForm.setPermissionNameList(getAllPermissionNames().getPermissionNameList());
        rolesForm.setShowIntial(false);
        return new ModelAndView(RecapConstants.ROLES, RecapConstants.ROLES_FORM, rolesForm);
    }

    /**
     * Provide information about the role which has been selected to edit in scsb.
     *
     * @param roleId          the role id
     * @param roleName        the role name
     * @param roleDescription the role description
     * @param permissionName  the permission name
     * @return the model and view
     */
    @ResponseBody
    @GetMapping(value = "/roles", params = "action=editRole")
    public ModelAndView editRole(Integer roleId, String roleName, String roleDescription, String permissionName) {
        String htmlUnescapePermissionName = HtmlUtils.htmlUnescape(permissionName);
        RolesForm rolesForm = getAllPermissionNames();
        rolesForm.setRoleId(roleId);
        rolesForm.setEditRoleName(roleName);
        rolesForm.setEditRoleDescription(roleDescription);
        rolesForm.setEditPermissionNames(htmlUnescapePermissionName);
        rolesForm.setSelectedPermissionNames(getSelectedPermissionNames(htmlUnescapePermissionName));
        rolesForm.setShowIntial(false);
        return new ModelAndView(RecapConstants.ROLES, RecapConstants.ROLES_FORM, rolesForm);
    }

    /**
     * To save the edited role details in scsb.
     *
     * @param roleId          the role id
     * @param roleName        the role name
     * @param roleDescription the role description
     * @param request         the request
     * @return the model and view
     */
    @ResponseBody
    @GetMapping(value = "/roles", params = "action=saveEditedRole")
    public ModelAndView saveEditedRole(@ModelAttribute("roleId") Integer roleId,
                                       @ModelAttribute("roleName") String roleName,
                                       @ModelAttribute("roleDescription") String roleDescription,
                                      HttpServletRequest request) {
        RolesForm rolesForm = new RolesForm();
        HttpSession session = request.getSession(false);
        rolesForm.setRoleId(roleId);
        rolesForm.setEditRoleName(roleName);
        rolesForm.setEditRoleDescription(roleDescription);
        String[] editPermissionNames = request.getParameterValues("permissionNames[]");
        rolesForm.setEditPermissionName(Arrays.asList(editPermissionNames));
        Optional<RoleEntity> roleEntityByRoleId = rolesDetailsRepositorty.findById(roleId);
        if(roleEntityByRoleId.isPresent()) {
            roleEntityByRoleId.get().setId(roleId);
            roleEntityByRoleId.get().setRoleName(roleName);
            roleEntityByRoleId.get().setRoleDescription(roleDescription);
            roleEntityByRoleId.get().setLastUpdatedDate(new Date());
            roleEntityByRoleId.get().setLastUpdatedBy(String.valueOf(session.getAttribute(RecapConstants.USER_NAME)));
            RoleEntity roleEntity = saveRoleEntity(roleEntityByRoleId.get(), Arrays.asList(editPermissionNames));
            if (null != roleEntity) {
                rolesForm.setMessage(rolesForm.getEditRoleName() + RecapConstants.EDITED_AND_SAVED);
            } else {
                rolesForm.setErrorMessage(rolesForm.getEditRoleName() + RecapConstants.ALREADY_EXISTS);
            }
        }
        rolesForm.setPermissionNameList(getAllPermissionNames().getPermissionNameList());
        rolesForm.setSelectedPermissionNames(Arrays.asList(editPermissionNames));
        rolesForm.setShowIntial(false);
        return new ModelAndView(RecapConstants.ROLES, RecapConstants.ROLES_FORM, rolesForm);
    }

    /**
     *Provide information about the role which has been selected to delete in scsb.
     *
     * @param roleId          the role id
     * @param roleName        the role name
     * @param roleDescription the role description
     * @param permissionName  the permission name
     * @param pageSize        the page size
     * @param pageNumber      the page number
     * @param totalPageCount  the total page count
     * @return the model and view
     */
    @ResponseBody
    @GetMapping(value = "/roles", params = "action=deleteRole")
    public ModelAndView deleteRole(Integer roleId, String roleName, String roleDescription, String permissionName,
                                   Integer pageSize,Integer pageNumber,Integer totalPageCount) {
        String htmlUnescapePermissionName = HtmlUtils.htmlUnescape(permissionName);
        RolesForm rolesForm = getAllPermissionNames();
        rolesForm.setAfterDelPageSize(pageSize);
        rolesForm.setAfterDelPageNumber(pageNumber);
        rolesForm.setAfterDelTotalPageCount(totalPageCount);
        rolesForm.setRoleId(roleId);
        rolesForm.setRoleNameForDelete(roleName);
        rolesForm.setRoleDescriptionForDelete(roleDescription);
        rolesForm.setPermissionNamesForDelete(htmlUnescapePermissionName);
        rolesForm.setSelectedPermissionNames(getSelectedPermissionNames(htmlUnescapePermissionName));
        rolesForm.setPageSize(pageSize);
        rolesForm.setPageNumber(pageNumber);
        rolesForm.setTotalPageCount(totalPageCount);
        rolesForm.setShowIntial(false);
        return new ModelAndView(RecapConstants.ROLES, RecapConstants.ROLES_FORM, rolesForm);
    }

    /**
     * To delete the role permanently in scsb.
     *
     * @param rolesForm the roles form
     * @param model     the model
     * @return the model and view
     */
    @ResponseBody
    @GetMapping(value = "/roles", params = "action=delete")
    public ModelAndView delete(@Valid @ModelAttribute("rolesForm") RolesForm rolesForm,
                               Model model) {
        Optional<RoleEntity> roleEntity = rolesDetailsRepositorty.findById(rolesForm.getRoleId());
        if (roleEntity.isPresent()) {
            try {
                rolesDetailsRepositorty.delete(roleEntity.get());
                rolesForm.setShowResults(true);
                rolesForm.setPageNumber(rolesForm.getAfterDelPageNumber());
                rolesForm.setPageSize(rolesForm.getAfterDelPageSize());
                rolesForm.setTotalPageCount(rolesForm.getAfterDelTotalPageCount());
                rolesForm.setRoleName("");
                rolesForm.setPermissionNames("");
                setRolesFormSearchResults(rolesForm);
                rolesForm.setMessage(rolesForm.getRoleNameForDelete() + RecapConstants.DELETED_SUCCESSFULLY);
            } catch (Exception e) {
                logger.error(RecapCommonConstants.LOG_ERROR, "Role is Null");
            }
         }
        else {
            logger.error(RecapCommonConstants.LOG_ERROR, "e");
        }
        rolesForm.setShowResults(true);
        model.addAttribute(RecapCommonConstants.TEMPLATE, RecapConstants.ROLES);
        return new ModelAndView(RecapConstants.ROLES, RecapConstants.ROLES_FORM, rolesForm);
    }

    /**
     *Gets previous page role search results from scsb database and display them as rows in the roles UI page.
     *
     * @param rolesForm the roles form
     * @param model     the model
     * @return the model and view
     */
    @ResponseBody
    @PostMapping(value = "/roles", params = "action=previous")
    public ModelAndView searchPrevious(@Valid @ModelAttribute("rolesForm") RolesForm rolesForm,
                                       Model model) {
        return searchPage(model, rolesForm);
    }

    /**
     * Gets next page role search results from scsb database and display them as rows in the roles UI page.
     *
     * @param rolesForm the roles form
     * @param model     the model
     * @return the model and view
     */
    @ResponseBody
    @PostMapping(value = "/roles", params = "action=next")
    public ModelAndView searchNext(@Valid @ModelAttribute("rolesForm") RolesForm rolesForm,
                                   Model model) {
        return searchPage(model, rolesForm);
    }

    /**
     * Gets first page role search results from scsb database and display them as rows in the roles UI page.
     *
     * @param rolesForm the roles form
     * @param model     the model
     * @return the model and view
     */
    @ResponseBody
    @PostMapping(value = "/roles", params = "action=first")
    public ModelAndView searchFirst(@Valid @ModelAttribute("rolesForm") RolesForm rolesForm,
                                    Model model) {
        rolesForm.setShowResults(true);
        rolesForm.resetPageNumber();
        return setRolesForm(rolesForm, model);
    }

    /**
     * Gets last page role search results from scsb database and display them as rows in the roles UI page.
     *
     * @param rolesForm the roles form
     * @param model     the model
     * @return the model and view
     */
    @ResponseBody
    @PostMapping(value = "/roles", params = "action=last")
    public ModelAndView searchLast(@Valid @ModelAttribute("rolesForm") RolesForm rolesForm,
                                   Model model) {
        rolesForm.setShowResults(true);
        rolesForm.setPageNumber(rolesForm.getTotalPageCount() - 1);
        return setRolesForm(rolesForm, model);
    }

    /**
     * Based on the selected page size the roles will be displayed in the roles UI page.
     *
     * @param rolesForm the roles form
     * @param model     the model
     * @return the model and view
     * @throws Exception the exception
     */
    @ResponseBody
    @PostMapping(value = "/roles", params = "action=pageSizeChange")
    public ModelAndView onPageSizeChange(@Valid @ModelAttribute("rolesForm") RolesForm rolesForm,
                                         Model model) throws Exception {
        rolesForm.setShowResults(true);
        rolesForm.setPageNumber(0);
        return setRolesForm(rolesForm, model);
    }

    /**
     * Get back to the role search page from edit, delete and create roles UI pages.
     *
     * @param rolesForm the roles form
     * @param model     the model
     * @return the model and view
     */
    @ResponseBody
    @PostMapping(value = "/roles", params = "action=goBack")
    public ModelAndView goBack(RolesForm rolesForm,Model model){
        rolesForm.setShowIntial(true);
        return new ModelAndView("roles", "rolesForm", rolesForm);
    }

    /**
     * Gets role entities result set from scsb database based on the search condition given in the search roles UI page.
     *
     * @param rolesForm the roles form
     */
    public void setRolesFormSearchResults(RolesForm rolesForm) {
        List<RolesSearchResult> rolesSearchResults = new ArrayList<>();
        rolesForm.reset();
        if (rolesForm.getRoleName().equalsIgnoreCase(RecapConstants.ROLES_SUPER_ADMIN) || rolesForm.getPermissionNames().equalsIgnoreCase(RecapConstants.ROLES_SUPER_ADMIN)) {
            if (rolesForm.getRoleName().equalsIgnoreCase(RecapConstants.ROLES_SUPER_ADMIN)){
                rolesForm.setErrorMessage(RecapConstants.INVALID_ROLE_NAME);
            }
            else{
                rolesForm.setErrorMessage(RecapConstants.INVALID_PERMISSION);
            }

        } else if (!StringUtils.isEmpty(rolesForm.getRoleName()) && StringUtils.isEmpty(rolesForm.getPermissionNames())) {
            if (isSpecialCharacterCheck(rolesForm.getRoleName())) {
                Pageable pageable = PageRequest.of(rolesForm.getPageNumber(), rolesForm.getPageSize());
                Page<RoleEntity> rolesEntityListByPagination = rolesDetailsRepositorty.findByRoleName(pageable, rolesForm.getRoleName());
                List<RoleEntity> roleEntityList = rolesEntityListByPagination.getContent();
                rolesForm.setTotalRecordCount(NumberFormat.getNumberInstance().format(rolesEntityListByPagination.getTotalElements()));
                rolesForm.setTotalPageCount(rolesEntityListByPagination.getTotalPages());
                if (!roleEntityList.isEmpty()) {
                    for (RoleEntity roleEntity : roleEntityList) {
                        rolesForm.setTotalRecordCount(String.valueOf(1));
                        RolesSearchResult rolesSearchResult = getRolesSearchResult(roleEntity);
                        rolesSearchResults.add(rolesSearchResult);
                        rolesForm.setRolesSearchResults(rolesSearchResults);
                    }
                } else {
                    rolesForm.setErrorMessage(RecapConstants.INVALID_ROLE_NAME);
                }
            } else {
                rolesForm.setErrorMessage(RecapConstants.SPECIAL_CHARACTERS_NOT_ALLOWED);
            }
            rolesForm.setPageSize(10);
        } else if (!StringUtils.isEmpty(rolesForm.getPermissionNames()) && StringUtils.isEmpty(rolesForm.getRoleName())) {
                if (isSpecialCharacterCheck(rolesForm.getPermissionNames())) {
                    Pageable pageable = PageRequest.of(rolesForm.getPageNumber(), rolesForm.getPageSize());
                    PermissionEntity pemissionEntity = permissionsRepository.findByPermissionName(rolesForm.getPermissionNames());
                    getResultsForNonEmptyRolePermissionName(rolesForm, rolesSearchResults, pageable, pemissionEntity);
                }
            } else if (!StringUtils.isEmpty(rolesForm.getRoleName()) && !StringUtils.isEmpty(rolesForm.getPermissionNames())) {
                if (isSpecialCharacterCheck(rolesForm.getPermissionNames())) {
                    RoleEntity roleEntity = rolesDetailsRepositorty.findByRoleName(rolesForm.getRoleName());
                    if (null != roleEntity) {
                        boolean isExist = false;
                        RolesSearchResult rolesSearchResult = new RolesSearchResult();
                        for (PermissionEntity permissionEnt : roleEntity.getPermissions()) {
                            if (rolesForm.getPermissionNames().equalsIgnoreCase(permissionEnt.getPermissionName())) {
                                isExist = true;
                            }
                        }
                        if (isExist) {
                            StringBuilder allPermissions = new StringBuilder();
                            for (PermissionEntity permissionEnt : roleEntity.getPermissions()) {
                                allPermissions.append(permissionEnt.getPermissionName());
                                allPermissions.append(", ");
                            }
                            rolesForm.setTotalPageCount(1);
                            rolesForm.setTotalRecordCount(String.valueOf(1));
                            rolesForm.setPageSize(10);
                            rolesSearchResult.setRoleId(roleEntity.getId());
                            rolesSearchResult.setPermissionName(allPermissions.toString());
                            rolesSearchResult.setRolesName(roleEntity.getRoleName());
                            rolesSearchResult.setRolesDescription(roleEntity.getRoleDescription());
                            rolesSearchResults.add(rolesSearchResult);
                            rolesForm.setRolesSearchResults(rolesSearchResults);
                        } else {
                            rolesForm.setErrorMessage(RecapConstants.WRONG_PERMISSION);
                        }
                    } else {
                        rolesForm.setErrorMessage(RecapConstants.INVALID_ROLE_NAME);
                    }
                } else {
                    rolesForm.setErrorMessage(RecapConstants.INVALID_ROLE_NAME);
                }
            rolesForm.setPageSize(10);
            } else if (StringUtils.isEmpty(rolesForm.getRoleName()) && StringUtils.isEmpty(rolesForm.getPermissionNames())) {
                Pageable pageable = PageRequest.of(rolesForm.getPageNumber(), rolesForm.getPageSize());
                Page<RoleEntity> rolesEntityListByPagination = rolesDetailsRepositorty.getRolesWithoutSuperAdmin(pageable);
                List<RoleEntity> rolesEntityList = null;
                searchRolesEntity(rolesForm, rolesEntityList, rolesSearchResults, rolesEntityListByPagination);
            }

        }

    private void getResultsForNonEmptyRolePermissionName(RolesForm rolesForm, List<RolesSearchResult> rolesSearchResults, Pageable pageable, PermissionEntity pemissionEntity) {
        if (pemissionEntity != null) {
            List<Integer> roleIdList = rolesDetailsRepositorty.getIdforPermissionName(pemissionEntity.getId());
            Page<RoleEntity> roleEntity = null;
            if (roleIdList != null) {
                roleEntity = rolesDetailsRepositorty.findByRoleIDs(pageable, roleIdList);
                List<RoleEntity> roleEntityList = roleEntity.getContent();
                for (RoleEntity entity : roleEntityList) {
                    RolesSearchResult rolesSearchResult = getRolesSearchResult(entity);
                    rolesSearchResults.add(rolesSearchResult);
                }
            }
            rolesForm.setRolesSearchResults(rolesSearchResults);
            if (roleEntity != null) {
                rolesForm.setTotalRecordCount(String.valueOf(roleEntity.getTotalElements()));
                rolesForm.setTotalPageCount(roleEntity.getTotalPages());
            }
        } else {
            rolesForm.setErrorMessage(RecapConstants.INVALID_PERMISSION);
        }
    }

    private List<String> getSelectedPermissionNames(String permissionName) {
        List<String> permissionNames = new ArrayList<>();
        if(!StringUtils.isEmpty(permissionName)){
            StringTokenizer stringTokenizer = new StringTokenizer(permissionName, ",");
            while(stringTokenizer.hasMoreTokens()){
                String token = stringTokenizer.nextToken();
                String trim = token.trim();
                if(!StringUtils.isEmpty(trim)){
                    permissionNames.add(trim);
                }
            }
        }
        return permissionNames;
    }


    /**
     * Get values from role entity and assign those values to the role search result.
     *
     * @param roleEntity the role entity
     * @return the roles search result
     */
    public RolesSearchResult getRolesSearchResult(RoleEntity roleEntity){
        StringBuilder permission = new StringBuilder();
        RolesSearchResult rolesSearchResult = new RolesSearchResult();
        for(PermissionEntity permissionEntity : roleEntity.getPermissions()){
            permission.append(permissionEntity.getPermissionName());
            permission.append(", ");
        }
        String permissionName = getSelectedPermissionNames(permission.toString()).toString().replaceAll("\\[", "").replaceAll("\\]", "");
        rolesSearchResult.setPermissionName(permissionName);
        rolesSearchResult.setRolesName(roleEntity.getRoleName());
        rolesSearchResult.setRolesDescription(roleEntity.getRoleDescription());
        rolesSearchResult.setRoleId(roleEntity.getId());
        return rolesSearchResult;
    }


    private boolean isSpecialCharacterCheck(String inputString) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9_ ]*");
        Matcher matcher = pattern.matcher(inputString);
        return matcher.matches();
    }

    /**
     *This method is used to paginate the role search results.
     *
     * @param rolesForm the roles form
     */
    public void findByPagination(RolesForm rolesForm){
        List<RolesSearchResult> rolesSearchResults = new ArrayList<>();
        Pageable pageable = PageRequest.of(rolesForm.getPageNumber(), rolesForm.getPageSize());
        List<RoleEntity> rolesEntityList = null;
        if(!StringUtils.isEmpty(rolesForm.getRoleName()) && StringUtils.isEmpty(rolesForm.getPermissionNames())){
            Page<RoleEntity> rolesEntityListByPagination = rolesDetailsRepositorty.findByRoleName(pageable, rolesForm.getRoleName());
            searchRolesEntity(rolesForm, rolesEntityList, rolesSearchResults, rolesEntityListByPagination);
        }
        else if(StringUtils.isEmpty(rolesForm.getRoleName()) && !StringUtils.isEmpty(rolesForm.getPermissionNames())){
            Pageable pageable1 = PageRequest.of(rolesForm.getPageNumber(), rolesForm.getPageSize());
            PermissionEntity permissionEntity = permissionsRepository.findByPermissionName(rolesForm.getPermissionNames());
            getResultsForNonEmptyRolePermissionName(rolesForm, rolesSearchResults, pageable1, permissionEntity);

        }

        else if(StringUtils.isEmpty(rolesForm.getRoleName()) && StringUtils.isEmpty(rolesForm.getPermissionNames())) {
            Page<RoleEntity> rolesEntityListByPagination = rolesDetailsRepositorty.getRolesWithoutSuperAdmin(pageable);
            searchRolesEntity(rolesForm, rolesEntityList, rolesSearchResults, rolesEntityListByPagination);
        }
    }

    /**
     * Save a new role entity in the scsb database.
     *
     * @param rolesForm the roles form
     * @param username  the username
     * @return the role entity
     */
    public RoleEntity saveNewRoleToDB(RolesForm rolesForm, String username){
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleName(rolesForm.getNewRoleName().trim());
        roleEntity.setRoleDescription(rolesForm.getNewRoleDescription());
        roleEntity.setCreatedDate(new Date());
        roleEntity.setCreatedBy(username);
        roleEntity.setLastUpdatedDate(new Date());
        roleEntity.setLastUpdatedBy(username);
        List<String> permissionNameList = splitStringAndGetList(rolesForm.getNewPermissionNames());
        return saveRoleEntity(roleEntity, permissionNameList);
    }

    private RoleEntity saveRoleEntity(RoleEntity roleEntity1, List<String> permissionNameList) {
        PermissionEntity permissionEntity;
        RoleEntity roleEntity = null;
        Set rolesSet = new HashSet();
        try {
            for(String permissionName : permissionNameList){
                permissionEntity = permissionsRepository.findByPermissionName(permissionName);
                rolesSet.add(permissionEntity);
            }
            roleEntity1.setPermissions(rolesSet);
            roleEntity = rolesDetailsRepositorty.save(roleEntity1);
        } catch (Exception e) {
            logger.error(RecapCommonConstants.LOG_ERROR,e);
        }
        return roleEntity;
    }

    private List<String> splitStringAndGetList(String inputString) {
        String[] splittedString = inputString.split(",");
        return Arrays.asList(splittedString);
    }

    private RolesForm getAllPermissionNames(){
        RolesForm rolesForm = new RolesForm();
        List<String> permissionNameList = new ArrayList<>();
        List<PermissionEntity> permissionEntityList = permissionsRepository.findAll();
        for(PermissionEntity permissionEntity : permissionEntityList){
            String permissionName = permissionEntity.getPermissionName();
            permissionNameList.add(permissionName);
        }
        rolesForm.setPermissionNameList(permissionNameList);
        return rolesForm;
    }

    private void searchRolesEntity(RolesForm rolesForm, List<RoleEntity>  rolesEntityList, List<RolesSearchResult> rolesSearchResults, Page<RoleEntity>  rolesEntityListByPagination) {
        List<RoleEntity> rolesEntity = rolesEntityListByPagination.getContent();
        rolesForm.setTotalRecordCount(NumberFormat.getNumberInstance().format(rolesEntityListByPagination.getTotalElements()));
        rolesForm.setTotalPageCount(rolesEntityListByPagination.getTotalPages());
        for(RoleEntity roleEntity : rolesEntity){
            RolesSearchResult rolesSearchResult = getRolesSearchResult(roleEntity);
            rolesSearchResults.add(rolesSearchResult);
        }
        rolesForm.setRolesSearchResults(rolesSearchResults);

    }
    private ModelAndView searchPage(Model model,RolesForm rolesForm) 
    {
        rolesForm.setShowResults(true);
        return setRolesForm(rolesForm, model);
    }

    private ModelAndView setRolesForm(RolesForm rolesForm, Model model)
    {
        findByPagination(rolesForm);
        model.addAttribute(RecapCommonConstants.TEMPLATE, RecapConstants.ROLES);
        return new ModelAndView(RecapConstants.VIEW_SEARCH_RECORDS, RecapConstants.ROLES_FORM, rolesForm);
    }
}
