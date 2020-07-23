package org.recap.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.recap.RecapCommonConstants;
import org.recap.RecapConstants;
import org.recap.model.jpa.BibliographicEntity;
import org.recap.model.jpa.ItemEntity;
import org.recap.model.jpa.InstitutionEntity;
import org.recap.model.jpa.CollectionGroupEntity;
import org.recap.model.jpa.CustomerCodeEntity;
import org.recap.model.jpa.ItemStatusEntity;
import org.recap.model.search.BibDataField;
import org.recap.model.search.BibliographicMarcForm;
import org.recap.model.usermanagement.UserDetailsForm;
import org.recap.repository.jpa.BibliographicDetailsRepository;
import org.recap.repository.jpa.CustomerCodeDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by rajeshbabuk on 17/10/16.
 */
@Service
public class MarcRecordViewUtil {

    @Autowired
    private BibliographicDetailsRepository bibliographicDetailsRepository;

    @Autowired
    private CustomerCodeDetailsRepository customerCodeDetailsRepository;

    /**
     * This method used to get information about the item or bib from the scsb database for the given bib id or item id and
     * build that information in the bibliographic marc form.
     *
     * @param bibId           the bib id
     * @param itemId          the item id
     * @param userDetailsForm the user details form
     * @return the bibliographic marc form
     */
    public BibliographicMarcForm buildBibliographicMarcForm(Integer bibId, Integer itemId,UserDetailsForm userDetailsForm) {
        BibliographicMarcForm bibliographicMarcForm = new BibliographicMarcForm();
        bibliographicMarcForm.setCollectionAction(RecapCommonConstants.UPDATE_CGD);
        BibliographicEntity bibliographicEntity = bibliographicDetailsRepository.findByBibliographicIdAndCatalogingStatusAndIsDeletedFalse(bibId, RecapCommonConstants.COMPLETE_STATUS);
        if (null == bibliographicEntity) {
            bibliographicMarcForm.setErrorMessage(RecapConstants.RECORD_NOT_AVAILABLE);
        } else {
            InstitutionEntity institutionEntity = bibliographicEntity.getInstitutionEntity();
            bibliographicMarcForm.setAllowEdit(true);
            if(userDetailsForm!=null &&  !userDetailsForm.isSuperAdmin() && !userDetailsForm.getLoginInstitutionId().equals(institutionEntity.getId())) {
                bibliographicMarcForm.setErrorMessage(RecapCommonConstants.ACCESS_RESTRICTED);
                bibliographicMarcForm.setAllowEdit(false);
            }
                bibliographicMarcForm.setBibId(bibliographicEntity.getBibliographicId());
                String bibContent = new String(bibliographicEntity.getContent());
                BibJSONUtil bibJSONUtil = new BibJSONUtil();
                List<Record> records = bibJSONUtil.convertMarcXmlToRecord(bibContent);
                Record marcRecord = records.get(0);
                setBibliographicMarcFormWithBibValues(marcRecord, bibJSONUtil, bibliographicMarcForm);
                bibliographicMarcForm.setContent(bibContent);
                if (null != institutionEntity) {
                    bibliographicMarcForm.setOwningInstitution(institutionEntity.getInstitutionCode());
                }
                List<ItemEntity> nonDeletedItemEntities = bibliographicDetailsRepository.getNonDeletedItemEntities(bibliographicEntity.getOwningInstitutionId(), bibliographicEntity.getOwningInstitutionBibId(), RecapCommonConstants.COMPLETE_STATUS);
                if (CollectionUtils.isNotEmpty(nonDeletedItemEntities)) {
                    if (nonDeletedItemEntities.size() == 1 && RecapCommonConstants.MONOGRAPH.equals(bibliographicMarcForm.getLeaderMaterialType())) {
                        CollectionGroupEntity collectionGroupEntity = nonDeletedItemEntities.get(0).getCollectionGroupEntity();
                        if (null != collectionGroupEntity) {
                            bibliographicMarcForm.setMonographCollectionGroupDesignation(collectionGroupEntity.getCollectionGroupCode());
                        }
                    }
                    bibliographicMarcForm.setCallNumber(nonDeletedItemEntities.get(0).getCallNumber());
                    if (null != itemId) {
                        for (ItemEntity itemEntity : nonDeletedItemEntities) {
                            if (itemEntity.getItemId().intValue() == itemId) {
                                bibliographicMarcForm.setItemId(itemEntity.getItemId());
                                bibliographicMarcForm.setBarcode(itemEntity.getBarcode());
                                bibliographicMarcForm.setUseRestriction(itemEntity.getUseRestrictions());
                                bibliographicMarcForm.setCallNumber(itemEntity.getCallNumber());
                                bibliographicMarcForm.setCustomerCode(itemEntity.getCustomerCode());
                                ItemStatusEntity itemStatusEntity = itemEntity.getItemStatusEntity();
                                if (null != itemStatusEntity) {
                                    bibliographicMarcForm.setAvailability(itemStatusEntity.getStatusCode());
                                }
                                CollectionGroupEntity collectionGroupEntity = itemEntity.getCollectionGroupEntity();
                                if (null != collectionGroupEntity) {
                                    bibliographicMarcForm.setCollectionGroupDesignation(collectionGroupEntity.getCollectionGroupCode());
                                    bibliographicMarcForm.setNewCollectionGroupDesignation(collectionGroupEntity.getCollectionGroupCode());
                                    if (RecapCommonConstants.SHARED_CGD.equals(bibliographicMarcForm.getCollectionGroupDesignation())) {
                                        bibliographicMarcForm.setShared(Boolean.TRUE);
                                    }
                                }
                                if (StringUtils.isNotBlank(bibliographicMarcForm.getAvailability())) {
                                    if (RecapCommonConstants.AVAILABLE.equals(bibliographicMarcForm.getAvailability())) {
                                        bibliographicMarcForm.setDeaccessionType(RecapCommonConstants.PERMANENT_WITHDRAWAL_DIRECT);
                                    } else if (RecapCommonConstants.NOT_AVAILABLE.equals(bibliographicMarcForm.getAvailability())) {
                                        bibliographicMarcForm.setDeaccessionType(RecapCommonConstants.PERMANENT_WITHDRAWAL_INDIRECT);
                                    }
                                }
                            }
                            List<CustomerCodeEntity> deliveryLocations = getDeliveryLocationsList(bibliographicMarcForm.getCustomerCode());
                            bibliographicMarcForm.setDeliveryLocations(deliveryLocations);
                        }
                    }
                    if (null == bibliographicMarcForm.getItemId()) {
                        bibliographicMarcForm.setErrorMessage(RecapConstants.RECORD_NOT_AVAILABLE);
                    }
                }
            }
        return bibliographicMarcForm;
    }

    private void setBibliographicMarcFormWithBibValues(Record marcRecord, BibJSONUtil bibJSONUtil, BibliographicMarcForm bibliographicMarcForm) {
        bibliographicMarcForm.setTitle(bibJSONUtil.getTitleDisplay(marcRecord));
        bibliographicMarcForm.setAuthor(bibJSONUtil.getAuthorDisplayValue(marcRecord));
        bibliographicMarcForm.setPublisher(bibJSONUtil.getPublisherValue(marcRecord));
        bibliographicMarcForm.setPublishedDate(bibJSONUtil.getPublicationDateValue(marcRecord));
        bibliographicMarcForm.setLeaderMaterialType(bibJSONUtil.getLeaderMaterialType(marcRecord.getLeader()));
        bibliographicMarcForm.setTag000(bibJSONUtil.getLeader(marcRecord));
        bibliographicMarcForm.setControlNumber001(bibJSONUtil.getControlFieldValue(marcRecord, "001"));
        bibliographicMarcForm.setControlNumber005(bibJSONUtil.getControlFieldValue(marcRecord, "005"));
        bibliographicMarcForm.setControlNumber008(bibJSONUtil.getControlFieldValue(marcRecord, "008"));
        bibliographicMarcForm.setBibDataFields(buildBibDataFields(marcRecord));
    }

    private List<BibDataField> buildBibDataFields(Record marcRecord) {
        List<BibDataField> bibDataFields = new ArrayList<>();
        List<DataField> marcDataFields = marcRecord.getDataFields();
        if (!CollectionUtils.isEmpty(marcDataFields)) {
            for (DataField marcDataField : marcDataFields) {
                BibDataField bibDataField = new BibDataField();
                bibDataField.setDataFieldTag(marcDataField.getTag());
                if (Character.isWhitespace(marcDataField.getIndicator1())) {
                    bibDataField.setIndicator1('_');
                } else {
                    bibDataField.setIndicator1(marcDataField.getIndicator1());
                }
                if (Character.isWhitespace(marcDataField.getIndicator2())) {
                    bibDataField.setIndicator2('_');
                } else {
                    bibDataField.setIndicator2(marcDataField.getIndicator2());
                }
                List<Subfield> subfields = marcDataField.getSubfields();
                if (!CollectionUtils.isEmpty(subfields)) {
                    StringBuilder buffer = new StringBuilder();
                    for (Subfield subfield : subfields) {
                        buffer.append("|").append(subfield.getCode());
                        buffer.append(" ").append(subfield.getData()).append(" ");
                    }
                    bibDataField.setDataFieldValue(buffer.toString());
                }
                bibDataFields.add(bibDataField);
            }
        }
        Collections.sort(bibDataFields);
        return bibDataFields;
    }


    public List<CustomerCodeEntity> getDeliveryLocationsList(String customerCode) {
        CustomerCodeEntity customerCodeEntity = customerCodeDetailsRepository.findByCustomerCode(customerCode);
        List<CustomerCodeEntity> deliveryLocations = new ArrayList<>();
        if (null != customerCodeEntity && StringUtils.isNotBlank(customerCodeEntity.getPwdDeliveryRestrictions())) {
            String pwdDeliveryRestrictions = customerCodeEntity.getPwdDeliveryRestrictions();
            String[] pwdDeliveryRestrictionsArray = StringUtils.split(pwdDeliveryRestrictions, ",");
            String[] pwdDeliveryRestrictionsTrimmed = Arrays.stream(pwdDeliveryRestrictionsArray).map(String::trim).toArray(String[]::new);
            List<CustomerCodeEntity> customerCodeEntities = customerCodeDetailsRepository.findByCustomerCodeIn(Arrays.asList(pwdDeliveryRestrictionsTrimmed));
            if (CollectionUtils.isNotEmpty(customerCodeEntities)) {
                System.out.println("customerCodeEntities.size() " + customerCodeEntities.size());
                deliveryLocations.addAll(customerCodeEntities);
            }
            Collections.sort(deliveryLocations);
        }
        return deliveryLocations;
    }
}
