package org.recap.model.usermanagement;

import org.junit.Test;
import org.recap.BaseTestCase;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

/**
 * Created by hemalathas on 30/3/17.
 */
public class UserRoleFormUT extends BaseTestCase{

    @Test
    public void testUserRoleForm(){
        UserRoleForm userRoleForm = new UserRoleForm();
        userRoleForm.setUserId(1);
        userRoleForm.setInstitutionId(1);
        userRoleForm.setEditUserId(1);
        int[] roleid = {1};
        int[] edirRoleId = {1};
        userRoleForm.setRoleId(roleid);
        userRoleForm.setEditRoleId(edirRoleId);
        userRoleForm.setPageNumber(10);
        userRoleForm.setPageSize(1);
        userRoleForm.setTotalPageCount(10);
        userRoleForm.setEditInstitutionId(1);
        userRoleForm.setAfterDelPageNumber(1);
        userRoleForm.setAfterDelPageSize(1);
        userRoleForm.setAfterDelTotalPageCount(1);
        userRoleForm.setSearchNetworkId("test");
        userRoleForm.setNetworkLoginId("test");
        userRoleForm.setRoleName("john");
        userRoleForm.setTotalRecordsCount("1");
        userRoleForm.setInstitutionName("PUL");
        userRoleForm.setMessage("Success");
        userRoleForm.setErrorMessage("test");
        userRoleForm.setErrorMessageForEmail("Invalid email address");
        userRoleForm.setEditErromessage("test");
        userRoleForm.setSectionName("test");
        userRoleForm.setButtonName("test");
        userRoleForm.setEditNetworkId("1");
        userRoleForm.setUserDescriptionErrMsg("test");
        userRoleForm.setUserDescription("test");
        userRoleForm.setEditNetworkId("1");
        userRoleForm.setEditUserDescription("test");
        userRoleForm.setUserEmailId("hemalatha.s@htcindia.com");
        userRoleForm.setEmailId("test@gmail.com");
        userRoleForm.setEditEmailId("1");
        userRoleForm.setAllowCreateEdit(true);
        userRoleForm.setCreatedRequest(true);
        userRoleForm.setShowPagination(true);
        userRoleForm.setShowSearch(false);
        userRoleForm.setShowErrorMessage(false);
        userRoleForm.setShowCreateSuccess(true);
        userRoleForm.setShowCreateError(false);
        userRoleForm.setShowEditSuccess(true);
        userRoleForm.setShowEditError(true);
        userRoleForm.setShowCreateEmailError(false);
        userRoleForm.setDeleteSuccessMsg(false);
        userRoleForm.setSelected(true);
        userRoleForm.setSubmitted(true);
        userRoleForm.setShowResults(false);
        userRoleForm.setDeletedSuccessMsg(false);
        userRoleForm.setDeleteErrorMsg(false);
        userRoleForm.setShowUserSearchView(false);
        userRoleForm.setRoles(new ArrayList<Object>());
        userRoleForm.setInstitutions(new ArrayList<Object>());
        userRoleForm.setShowSelectedForCreate(new ArrayList<Integer>());
        userRoleForm.setSelectedForCreate(new ArrayList<Integer>());
        userRoleForm.setEditSelectedForCreate(new ArrayList<Integer>());
        userRoleForm.setUserRoleFormList(Arrays.asList(new UserRoleForm()));
        userRoleForm.setCreatedBy("test");
        userRoleForm.setLastUpdatedBy("test");
        userRoleForm.setEditNetworkLoginId("1");

        assertNotNull(userRoleForm.isShowCreateError());
        assertNotNull(userRoleForm.isShowCreateSuccess());
        assertNotNull(userRoleForm.isShowErrorMessage());
        assertNotNull(userRoleForm.isCreatedRequest());
        assertNotNull(userRoleForm.getSearchNetworkId());
        assertNotNull(userRoleForm.getEditNetworkId());
        assertNotNull(userRoleForm.getEditRoleId());
        assertNotNull(userRoleForm.getRoles());
        assertNotNull(userRoleForm.getInstitutions());
        assertNotNull(userRoleForm.getSectionName());
        assertNotNull(userRoleForm.getButtonName());
        assertNotNull(userRoleForm.isAllowCreateEdit());
        assertNotNull(userRoleForm.isSelected());
        assertNotNull(userRoleForm.isSubmitted());
        assertNotNull(userRoleForm.isShowResults());
        assertNotNull(userRoleForm.getMessage());
        assertNotNull(userRoleForm.getInstitutionName());
        assertNotNull(userRoleForm.getUserRoleFormList());
        assertNotNull(userRoleForm.getUserId());
        assertNotNull(userRoleForm.getEditUserId());
        assertNotNull(userRoleForm.getNetworkLoginId());
        assertNotNull(userRoleForm.getInstitutionId());
        assertNotNull(userRoleForm.getRoleId());
        assertNotNull(userRoleForm.getRoleName());
        assertNotNull(userRoleForm.getTotalRecordsCount());
        assertNotNull(userRoleForm.getPageNumber());
        assertNotNull(userRoleForm.getPageSize());
        assertNotNull(userRoleForm.getTotalPageCount());
        assertNotNull(userRoleForm.getErrorMessage());
        assertNotNull(userRoleForm.getUserDescription());
        assertNotNull(userRoleForm.getUserDescriptionErrMsg());
        assertNotNull(userRoleForm.isShowPagination());
        assertNotNull(userRoleForm.isShowSearch());
        assertNotNull(userRoleForm.getShowSelectedForCreate());
        assertNotNull(userRoleForm.getSelectedForCreate());
        assertNotNull(userRoleForm.isShowEditSuccess());
        assertNotNull(userRoleForm.isShowEditError());
        assertNotNull(userRoleForm.getEditNetworkLoginId());
        assertNotNull(userRoleForm.getEditSelectedForCreate());
        assertNotNull(userRoleForm.getEditUserDescription());
        assertNotNull(userRoleForm.getEditInstitutionId());
        assertNotNull(userRoleForm.getUserEmailId());
        assertNotNull(userRoleForm.getEmailId());
        assertNotNull(userRoleForm.getEditEmailId());
        assertNotNull(userRoleForm.isDeleteSuccessMsg());
        assertNotNull(userRoleForm.isDeletedSuccessMsg());
        assertNotNull(userRoleForm.isDeleteErrorMsg());
        assertNotNull(userRoleForm.getAfterDelPageNumber());
        assertNotNull(userRoleForm.getAfterDelPageSize());
        assertNotNull(userRoleForm.getAfterDelTotalPageCount());
        assertNotNull(userRoleForm.getEditErromessage());
        assertNotNull(userRoleForm.getErrorMessageForEmail());
        assertNotNull(userRoleForm.isShowCreateEmailError());
        assertNotNull(userRoleForm.getCreatedBy());
        assertNotNull(userRoleForm.getLastUpdatedBy());
        assertNotNull(userRoleForm.isShowUserSearchView());




    }

}