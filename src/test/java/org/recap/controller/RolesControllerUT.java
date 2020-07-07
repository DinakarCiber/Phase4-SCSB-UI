package org.recap.controller;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCase;
import org.recap.RecapConstants;
import org.recap.model.search.RolesForm;
import org.recap.model.search.RolesSearchResult;
import org.recap.repository.jpa.PermissionsDetailsRepository;
import org.recap.repository.jpa.RolesDetailsRepositorty;
import org.recap.util.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by hemalathas on 23/12/16.
 */
public class RolesControllerUT extends BaseTestCase {

    @Mock
    Model model;

    @Mock
    BindingResult bindingResult;

    @Autowired
    RolesController rolesController;

    @Autowired
    PermissionsDetailsRepository permissionsRepository;

    @Autowired
    RolesDetailsRepositorty rolesDetailsRepositorty;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession session;

    @Mock
    RolesController mockedrolesController;

    @Mock
    UserAuthUtil userAuthUtil;

    @Test
    public void testRoles() throws Exception{

        when(request.getSession(false)).thenReturn(session);
        Mockito.when(mockedrolesController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(mockedrolesController.getUserAuthUtil().isAuthenticated(request, RecapConstants.SCSB_SHIRO_ROLE_URL)).thenReturn(true);
        Mockito.doCallRealMethod().when(mockedrolesController).roles(model,request);
        String response = mockedrolesController.roles(model,request);
        assertNotNull(response);
    }
    @Test
    public void testRoles2() throws Exception{

        when(request.getSession(false)).thenReturn(session);
        Mockito.when(mockedrolesController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(mockedrolesController.getUserAuthUtil().isAuthenticated(request, RecapConstants.SCSB_SHIRO_ROLE_URL)).thenReturn(false);
        Mockito.doCallRealMethod().when(mockedrolesController).roles(model,request);
        String response = mockedrolesController.roles(model,request);
        assertNotNull(response);
    }

    @Test
    public void testSearch(){
        RolesForm rolesForm = new RolesForm();
        rolesForm.setRoleName("SuperAdmin");
        rolesForm.setPermissionNames("Create User");
        ModelAndView modelAndView = rolesController.search(rolesForm,model);
        assertNotNull(modelAndView);
        Map rolesMap = new HashMap();
        rolesMap = modelAndView.getModel();
        rolesForm = (RolesForm) rolesMap.get("rolesForm");
        assertEquals(rolesForm.getRolesSearchResults().size(),0);
    }

    @Test
    public void search() throws Exception{
        RolesForm rolesForm = new RolesForm();
        rolesForm.setRoleName("Admin");
        rolesForm.setPermissionNames("CreateUser");
        ModelAndView modelAndView = rolesController.search(rolesForm,model);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());
    }

    @Test
    public void populatePermissionNames() throws Exception{
        RolesForm rolesForm = new RolesForm();
        ModelAndView modelAndView = rolesController.populatePermissionNames(model);
        assertNotNull(modelAndView);
        assertEquals("roles",modelAndView.getViewName());
    }

    @Test
    public void newRole() throws Exception{
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(RecapConstants.USER_NAME)).thenReturn("userName");
        RolesForm rolesForm = new RolesForm();
        rolesForm.setNewRoleName("test@");
        rolesForm.setNewRoleDescription("test Description");
        rolesForm.setNewPermissionNames("CreateUser");
        ModelAndView modelAndView = rolesController.newRole(rolesForm,model,request);
        assertNotNull(modelAndView);
        assertEquals("roles",modelAndView.getViewName());
    }
    @Test
    public void newRole1() throws Exception{
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(RecapConstants.USER_NAME)).thenReturn("userName");
        RolesForm rolesForm = new RolesForm();
        rolesForm.setNewRoleName("test2");
        rolesForm.setNewRoleDescription("test Description");
        rolesForm.setNewPermissionNames("CreateUser");
        ModelAndView modelAndView = rolesController.newRole(rolesForm,model,request);
        //assertNotNull(modelAndView);
    }

    @Test
    public void editRole() throws Exception{
        RolesForm rolesForm = new RolesForm();
        rolesForm.setRoleId(1);
        rolesForm.setRoleName("Admin");
        rolesForm.setPermissionNames("CreateUser");
        rolesForm.setRoleDescriptionForDelete("test desc");
        ModelAndView modelAndView = rolesController.editRole(rolesForm.getRoleId(),rolesForm.getRoleName(),rolesForm.getRoleDescription(),rolesForm.getPermissionNames());
        assertNotNull(modelAndView);
        assertEquals("roles",modelAndView.getViewName());
    }

    @Test
    public void saveEditedRole() throws Exception{
        RolesForm rolesForm = new RolesForm();
        rolesForm.setRoleId(1);
        rolesForm.setEditRoleName("Admin");
        String[] permissionName ={"CreateUser"};
        when(request.getSession(false)).thenReturn(session);
        when(request.getParameterValues("permissionNames[]")).thenReturn(permissionName);
        when(session.getAttribute(RecapConstants.USER_NAME)).thenReturn("SuperAdmin");
        rolesForm.setEditPermissionName(Arrays.asList(permissionName));
        rolesForm.setEditRoleDescription("test desc");
        ModelAndView modelAndView = rolesController.saveEditedRole(rolesForm.getRoleId(),rolesForm.getEditRoleName(),rolesForm.getEditRoleName(),request);
        assertNotNull(modelAndView);
        assertEquals("roles",modelAndView.getViewName());
    }
    @Test
    public void deleteRole() throws Exception{
        RolesForm rolesForm = new RolesForm();
        rolesForm.setRoleId(1);
        rolesForm.setRoleNameForDelete("Admin");
        rolesForm.setRoleDescriptionForDelete("test desc");
        rolesForm.setPermissionNamesForDelete("CreateUser");
        ModelAndView modelAndView = rolesController.deleteRole(rolesForm.getRoleId(),rolesForm.getRoleName(),rolesForm.getRoleDescription(),rolesForm.getPermissionNamesForDelete(),10,1,2);
        assertNotNull(modelAndView);
        assertEquals("roles",modelAndView.getViewName());
    }

    @Test
    public void delete() throws Exception{
        RolesForm rolesForm = getRolesForm();
        ModelAndView modelAndView = rolesController.delete(rolesForm,model);
        assertNotNull(modelAndView);
        assertEquals("roles",modelAndView.getViewName());
    }

    @Test
    public void searchNext() throws Exception{
        RolesForm rolesForm = new RolesForm();
        rolesForm.setRoleName("Admin");
        rolesForm.setPermissionNames("CreateUser");
        rolesForm.setPageNumber(0);
        rolesForm.setPageSize(10);
        ModelAndView modelAndView = rolesController.searchNext(rolesForm,model);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());
    }

    @Test
    public void searchPrevious() throws Exception{
        RolesForm rolesForm = new RolesForm();
        rolesForm.setRoleName("Admin");
        rolesForm.setPermissionNames("CreateUser");
        rolesForm.setPageNumber(0);
        rolesForm.setPageSize(25);
        ModelAndView modelAndView = rolesController.searchPrevious(rolesForm,model);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());
    }

    @Test
    public void searchFirst() throws Exception{
        RolesForm rolesForm = new RolesForm();
        rolesForm.setRoleName("Admin");
        rolesForm.setPermissionNames("CreateUser");
        rolesForm.setPageNumber(0);
        rolesForm.setPageSize(25);
        ModelAndView modelAndView = rolesController.searchFirst(rolesForm,model);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());
    }
    @Test
    public void goBack() throws Exception{
        RolesForm rolesForm = new RolesForm();
        rolesForm.setRoleName("Admin");
        rolesForm.setPermissionNames("CreateUser");
        rolesForm.setPageNumber(0);
        rolesForm.setPageSize(25);
        ModelAndView modelAndView = rolesController.goBack(rolesForm,model);
        assertNotNull(modelAndView);
    }

    @Test
    public void searchLast() throws Exception{
        RolesForm rolesForm = new RolesForm();
        rolesForm.setRoleName("Admin");
        rolesForm.setPermissionNames("CreateUser");
        rolesForm.setPageNumber(2);
        rolesForm.setPageSize(10);
        rolesForm.setTotalPageCount(3);
        ModelAndView modelAndView = rolesController.searchLast(rolesForm,model);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());
    }

    @Test
    public void onPageSizeChange() throws Exception{
        RolesForm rolesForm = new RolesForm();
        rolesForm.setRoleName("Admin");
        rolesForm.setPermissionNames("CreateUser");
        rolesForm.setPageNumber(2);
        rolesForm.setPageSize(25);
        ModelAndView modelAndView = rolesController.onPageSizeChange(rolesForm,model);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());
    }

    @Test
    public void findByPagination() {
        RolesForm rolesForm = getRolesForm();
        rolesForm.setRoleName("Super Admin");
        rolesForm.setPermissionNames("");
        rolesController.findByPagination(rolesForm);
    }
    @Test
    public void findByPagination1() {
        RolesForm rolesForm = getRolesForm();
        rolesForm.setRoleName("");
        rolesForm.setPermissionNames("Super Admin");
        rolesController.findByPagination(rolesForm);
    }
    @Test
    public void findByPagination2() {
        RolesForm rolesForm = getRolesForm();
        rolesForm.setRoleName("");
        rolesForm.setPermissionNames("");
        rolesController.findByPagination(rolesForm);
    }
    @Test
    public void setRolesFormSearchResults()throws Exception{
        RolesForm rolesForm = getRolesForm();
        rolesForm.setRoleName("Super Admin");
        rolesForm.setPermissionNames("Super Admin");
        rolesController.setRolesFormSearchResults(rolesForm);
    }

    @Test
    public void setRolesFormSearchResults1()throws Exception{
        RolesForm rolesForm = getRolesForm();
        rolesForm.setRoleName("Super@Admin");
        rolesForm.setPermissionNames("");
        rolesController.setRolesFormSearchResults(rolesForm);
    }
    @Test
    public void setRolesFormSearchResults2()throws Exception{
        RolesForm rolesForm = getRolesForm();
        rolesForm.setRoleName("Super");
        rolesForm.setPermissionNames("");
        rolesController.setRolesFormSearchResults(rolesForm);
    }
    @Test
    public void setRolesFormSearchResults3()throws Exception{
        RolesForm rolesForm = getRolesForm();
        rolesForm.setRoleName("SuperAdmin");
        rolesForm.setPermissionNames("SuperAdmin");
        rolesForm.setPageNumber(20);
        rolesForm.setPageSize(10);
        rolesController.setRolesFormSearchResults(rolesForm);
    }

    @Test
    public void setRolesFormSearchResults4()throws Exception{
        RolesForm rolesForm = getRolesForm();
        rolesForm.setRoleName("");
        rolesForm.setPermissionNames("SuperAdmin");
        rolesForm.setPageNumber(20);
        rolesForm.setPageSize(10);
        rolesController.setRolesFormSearchResults(rolesForm);
    }

    @Test
    public void testRolesSearchResult(){
        RolesSearchResult rolesSearchResult = new RolesSearchResult();
        rolesSearchResult.setRolesName("admin");
        rolesSearchResult.setRolesDescription("admin");
        rolesSearchResult.setPermissionName("admin");
        rolesSearchResult.setRoleId(1);

        assertNotNull(rolesSearchResult.getRolesDescription());
        assertNotNull(rolesSearchResult.getRolesName());
        assertNotNull(rolesSearchResult.getPermissionName());
        assertNotNull(rolesSearchResult.getRoleId());
    }

    private RolesForm getRolesForm(){
        RolesForm rolesForm = new RolesForm();
        rolesForm.setRoleId(1);
        rolesForm.setRoleNameForDelete("Admin");
        rolesForm.setRoleDescriptionForDelete("test desc");
        rolesForm.setPermissionNamesForDelete("CreateUser");
        rolesForm.setPageSize(5);
        rolesForm.setPageNumber(10);
        return rolesForm;
    }


}