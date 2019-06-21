package org.recap.repository.jpa;

import org.recap.model.jpa.RequestItemEntity;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by rajeshbabuk on 26/10/16.
 */
public interface RequestItemDetailsRepository extends JpaRepository<RequestItemEntity, Integer>, JpaSpecificationExecutor {

    /**
     * To get the request item entity for the given request id.
     *
     * @param requestId the request id
     * @return the request item entity
     */
    RequestItemEntity findByRequestId(@Param("requestId") Integer requestId);

    /**
     * To get the pageable request item entities for the given status.
     *
     * @param pageable the pageable
     * @param status   the status
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join request.itemEntity item inner join  request.requestStatusEntity status where status.requestStatusDescription = :status")
    Page<RequestItemEntity> findByStatus(Pageable pageable, @Param("status") String status);

    /**
     * To get the pageable request item entity which has retrieval, recall and edd status for the given item barcode.
     *
     * @param pageable    the pageable
     * @param itemBarcode the item barcode
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join request.itemEntity item inner join request.requestStatusEntity status where (item.barcode = :itemBarcode and status.requestStatusCode in ('RETRIEVAL_ORDER_PLACED','RECALL_ORDER_PLACED', 'EDD_ORDER_PLACED')) OR (item.barcode = :itemBarcode and request.requestingInstitutionId not in (:institutionId) and item.owningInstitutionId = :institutionId and status.requestStatusCode in ('RETRIEVAL_ORDER_PLACED','RECALL_ORDER_PLACED'))")
    Page<RequestItemEntity> findByItemBarcodeAndActive(Pageable pageable, @Param("itemBarcode") String itemBarcode,@Param("institutionId")Integer institutionId);

    /**
     * To get the pageable request item entity which has retrieval, recall and edd status for the given patron barcode.
     *
     * @param pageable      the pageable
     * @param patronBarcode the patron barcode
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join request.itemEntity item inner join request.requestStatusEntity status where (request.patronId = :patronBarcode and status.requestStatusCode in ('RETRIEVAL_ORDER_PLACED', 'RECALL_ORDER_PLACED', 'EDD_ORDER_PLACED')) OR (request.patronId = :patronBarcode and request.requestingInstitutionId not in (:institutionId) and item.owningInstitutionId = :institutionId and status.requestStatusCode in ('RETRIEVAL_ORDER_PLACED', 'RECALL_ORDER_PLACED'))")
    Page<RequestItemEntity> findByPatronBarcodeAndActive(Pageable pageable, @Param("patronBarcode") String patronBarcode,@Param("institutionId")Integer institutionId);

    /**
     *To get the pageable request item entity for the given patron barcode, item barcode, status and institution.
     *
     * @param pageable      the pageable
     * @param patronBarcode the patron barcode
     * @param itemBarcode   the item barcode
     * @param status        the status
     * @param institutionId the institution id
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join request.itemEntity item inner join request.requestStatusEntity status where (request.patronId = :patronBarcode and request.requestingInstitutionId = :institutionId and item.barcode = :itemBarcode and status.requestStatusCode.requestStatusDescription = :status) OR (request.patronId = :patronBarcode and item.barcode = :itemBarcode and request.requestingInstitutionId not in (:institutionId) and item.owningInstitutionId = :institutionId and status.requestStatusCode.requestStatusDescription = :status)")
    Page<RequestItemEntity> findByPatronBarcodeAndItemBarcodeAndStatusAndInstitution(Pageable pageable, @Param("patronBarcode") String patronBarcode, @Param("itemBarcode") String itemBarcode, @Param("status") String status,@Param("institutionId")Integer institutionId);

    /**
     * To get the pageable request item entity which has retrieval, recall and edd status for the given patron barcode, item barcode and institution.
     *
     * @param pageable      the pageable
     * @param patronBarcode the patron barcode
     * @param itemBarcode   the item barcode
     * @param institutionId the institution id
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join request.itemEntity item inner join request.requestStatusEntity status where (request.patronId = :patronBarcode and item.barcode = :itemBarcode and request.requestingInstitutionId = :institutionId and status.requestStatusCode in ('RETRIEVAL_ORDER_PLACED', 'RECALL_ORDER_PLACED', 'EDD_ORDER_PLACED'))  OR (request.patronId = :patronBarcode and item.barcode = :itemBarcode and item.owningInstitutionId = :institutionId and request.requestingInstitutionId not in (:institutionId) and status.requestStatusCode in ('RETRIEVAL_ORDER_PLACED', 'RECALL_ORDER_PLACED','EDD_ORDER_PLACED'))")
    Page<RequestItemEntity> findByPatronBarcodeAndItemBarcodeAndActiveAndInstitution(Pageable pageable, @Param("patronBarcode") String patronBarcode, @Param("itemBarcode") String itemBarcode, @Param("institutionId")Integer institutionId);

    /**
     * To get the pageable request item entity for the given patron barcode and status.
     *
     * @param pageable      the pageable
     * @param patronBarcode the patron barcode
     * @param status        the status
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join request.itemEntity item inner join request.requestStatusEntity status where (request.patronId = :patronBarcode and status.requestStatusDescription = :status) OR (request.patronId = :patronBarcode and request.requestingInstitutionId not in (:institutionId) and item.owningInstitutionId = :institutionId and status.requestStatusDescription = :status)")
    Page<RequestItemEntity> findByPatronBarcodeAndStatus(Pageable pageable, @Param("patronBarcode") String patronBarcode, @Param("status") String status,@Param("institutionId")Integer institutionId);

    /**
     * To get the pageable request item entity for the given item barcode and status.
     *
     * @param pageable    the pageable
     * @param itemBarcode the item barcode
     * @param status      the status
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join request.itemEntity item inner join request.requestStatusEntity status where (item.barcode = :itemBarcode and status.requestStatusDescription = :status) OR (item.barcode = :itemBarcode and request.requestingInstitutionId not in (:institutionId) and item.owningInstitutionId = :institutionId and status.requestStatusDescription = :status)")
    Page<RequestItemEntity> findByItemBarcodeAndStatus(Pageable pageable, @Param("itemBarcode") String itemBarcode, @Param("status") String status,@Param("institutionId")Integer institutionId);

    /**
     * To get the pageable request item entity which has retrieval, recall and edd status.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join request.itemEntity item inner join request.requestStatusEntity status where status.requestStatusCode in ('RETRIEVAL_ORDER_PLACED', 'RECALL_ORDER_PLACED','EDD_ORDER_PLACED')")
    Page<RequestItemEntity> findAllActive(Pageable pageable);

    /**
     * To get the pageable request item entity for the given patron barcode.
     *
     * @param pageable      the pageable
     * @param patronBarcode the patron barcode
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request where request.patronId = :patronBarcode")
    Page<RequestItemEntity> findByPatronBarcode(Pageable pageable, @Param("patronBarcode") String patronBarcode);

    /**
     * To get the pageable request item entity for the given item barcode.
     *
     * @param pageable    the pageable
     * @param itemBarcode the item barcode
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join request.itemEntity item inner join request.requestStatusEntity status where (item.barcode = :itemBarcode) OR (item.barcode = :itemBarcode and request.requestingInstitutionId not in (:institutionId) and item.owningInstitutionId = :institutionId and status.requestStatusCode in ('RETRIEVAL_ORDER_PLACED', 'RECALL_ORDER_PLACED'))")
    Page<RequestItemEntity> findByItemBarcode(Pageable pageable, @Param("itemBarcode") String itemBarcode,@Param("institutionId")Integer institutionId);

    /**
     * To get the pageable request item entity for the given patron barcode and item barcode.
     *
     * @param pageable      the pageable
     * @param patronBarcode the patron barcode
     * @param itemBarcode   the item barcode
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join request.itemEntity item inner join  request.requestStatusEntity status where (request.patronId = :patronBarcode and item.barcode = :itemBarcode)  OR (request.patronId = :patronBarcode and item.barcode = :itemBarcode and request.requestingInstitutionId not in (:institutionId) and item.owningInstitutionId = :institutionId and status.requestStatusCode in ('RETRIEVAL_ORDER_PLACED', 'RECALL_ORDER_PLACED'))")
    Page<RequestItemEntity> findByPatronBarcodeAndItemBarcode(Pageable pageable, @Param("patronBarcode") String patronBarcode, @Param("itemBarcode") String itemBarcode,@Param("institutionId")Integer institutionId);

    /**
     * To get the request item entity for the given item barcode and status.
     *
     * @param itemBarcode       the item barcode
     * @param requestStatusCode the request status code
     * @return the request item entity
     * @throws IncorrectResultSizeDataAccessException the incorrect result size data access exception
     */
    @Query(value = "select requestItemEntity from RequestItemEntity requestItemEntity inner join requestItemEntity.itemEntity ie inner join requestItemEntity.requestStatusEntity as rse  where ie.barcode = :itemBarcode and rse.requestStatusCode= :requestStatusCode ")
    RequestItemEntity findByItemBarcodeAndRequestStaCode(@Param("itemBarcode") String itemBarcode, @Param("requestStatusCode") String requestStatusCode) throws IncorrectResultSizeDataAccessException;


    /**
     * To get the count of edd, recall and retrieval request counts for the given institution.
     *
     * @param fromDate         the from date
     * @param toDate           the to date
     * @param itemOwningInstId the item owning inst id
     * @param requestTypeCode  the request type code
     * @return the bd hold recall retrieval request counts
     */
    @Query(value = "SELECT COUNT(*) FROM REQUEST_ITEM_T,ITEM_T WHERE REQUEST_ITEM_T.ITEM_ID=ITEM_T.ITEM_ID " +
            "AND REQUEST_ITEM_T.CREATED_DATE >= :fromDate AND REQUEST_ITEM_T.CREATED_DATE <= :toDate " +
            "AND REQUEST_ITEM_T.REQUEST_TYPE_ID IN (SELECT REQUEST_TYPE_ID FROM REQUEST_TYPE_T WHERE REQUEST_TYPE_CODE IN (:requestTypeCodes)) " +
            "AND REQUEST_ITEM_T.REQUEST_STATUS_ID IN (SELECT REQUEST_STATUS_ID FROM REQUEST_ITEM_STATUS_T WHERE REQUEST_STATUS_CODE IN (:requestStatusCodes)) " +
            "AND ITEM_T.OWNING_INST_ID = :itemOwningInstId", nativeQuery = true)
    long getEDDRecallRetrievalRequestCounts(@Param("fromDate") Date fromDate,
                                            @Param("toDate") Date toDate,
                                            @Param("itemOwningInstId") int itemOwningInstId,
                                            @Param("requestStatusCodes") List<String> requestTypeCode,
                                            @Param("requestTypeCodes") List<String> requestTypeCodes);

    /**
     * To get the count of physical and edd requests for all the institutions.
     *
     * @param fromDate           the from date
     * @param toDate             the to date
     * @param itemOwningInstId   the item owning inst id
     * @param collectionGroupIds the collection group ids
     * @param requestStatusCodes   the request status codes
     * @return the physical and edd counts
     */
    @Query(value = "SELECT COUNT(*) FROM REQUEST_ITEM_T,ITEM_T WHERE REQUEST_ITEM_T.ITEM_ID=ITEM_T.ITEM_ID " +
            "AND REQUEST_ITEM_T.CREATED_DATE >= :fromDate AND REQUEST_ITEM_T.CREATED_DATE <= :toDate " +
            "AND REQUEST_ITEM_T.REQUESTING_INST_ID NOT IN (:requestInstIds) " +
            "AND REQUEST_ITEM_T.REQUEST_TYPE_ID IN (SELECT REQUEST_TYPE_ID FROM REQUEST_TYPE_T WHERE REQUEST_TYPE_CODE IN (:requestTypeCodes)) " +
            "AND REQUEST_ITEM_T.REQUEST_STATUS_ID IN (SELECT REQUEST_STATUS_ID FROM REQUEST_ITEM_STATUS_T WHERE REQUEST_STATUS_CODE IN (:requestStatusCodes)) " +
            "AND ITEM_T.COLLECTION_GROUP_ID IN (:collectionGroupIds) " +
            "AND ITEM_T.OWNING_INST_ID IN (:itemOwningInstId)", nativeQuery = true)
    long getPhysicalAndEDDCounts(@Param("fromDate") Date fromDate,
                                 @Param("toDate") Date toDate,
                                 @Param("itemOwningInstId") List<Integer> itemOwningInstId,
                                 @Param("collectionGroupIds") List<Integer> collectionGroupIds,
                                 @Param("requestInstIds") List<Integer> requestInstIds,
                                 @Param("requestStatusCodes") List<String> requestStatusCodes,
                                 @Param("requestTypeCodes") List<String> requestTypeCodes);

    /**
     *To get the pageable request item entity for the given patron barcode, item barcode and institution.
     *
     * @param pageable      the pageable
     * @param patronBarcode the patron barcode
     * @param itemBarcode   the item barcode
     * @param institutionId the institution id
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join request.itemEntity item inner join  request.requestStatusEntity status where (request.patronId = :patronBarcode and request.requestingInstitutionId = :institutionId and item.barcode = :itemBarcode)  OR (request.patronId = :patronBarcode and item.barcode = :itemBarcode and request.requestingInstitutionId not in (:institutionId) and item.owningInstitutionId = :institutionId)")
    Page<RequestItemEntity> findByPatronBarcodeAndItemBarcodeAndInstitution(Pageable pageable, @Param("patronBarcode") String patronBarcode, @Param("itemBarcode") String itemBarcode,@Param("institutionId")Integer institutionId);

    /**
     * To get the pageable request item entity which has retrieval, recall and edd status for the given patron barcode and institution.
     *
     * @param pageable      the pageable
     * @param patronBarcode the patron barcode
     * @param institutionId the institution id
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join request.itemEntity item inner join request.requestStatusEntity status where (request.patronId = :patronBarcode and request.requestingInstitutionId = :institutionId and status.requestStatusCode in ('RETRIEVAL_ORDER_PLACED', 'RECALL_ORDER_PLACED', 'EDD_ORDER_PLACED')) OR (request.patronId = :patronBarcode and request.requestingInstitutionId not in (:institutionId) and item.owningInstitutionId = :institutionId and status.requestStatusCode in ('RETRIEVAL_ORDER_PLACED', 'RECALL_ORDER_PLACED','EDD_ORDER_PLACED'))")
    Page<RequestItemEntity> findByPatronBarcodeAndActiveAndInstitution(Pageable pageable, @Param("patronBarcode") String patronBarcode,@Param("institutionId")Integer institutionId);

    /**
     * To get the pageable request item entity for the given patron barcode, status,and institution.
     *
     * @param pageable      the pageable
     * @param patronBarcode the patron barcode
     * @param status        the status
     * @param institutionId the institution id
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join request.itemEntity item inner join request.requestStatusEntity status where (request.patronId = :patronBarcode and request.requestingInstitutionId = :institutionId and status.requestStatusDescription = :status) OR (request.patronId = :patronBarcode and request.requestingInstitutionId not in (:institutionId) and item.owningInstitutionId = :institutionId and status.requestStatusDescription = :status)")
    Page<RequestItemEntity> findByPatronBarcodeAndStatusAndInstitution(Pageable pageable, @Param("patronBarcode") String patronBarcode, @Param("status") String status,@Param("institutionId")Integer institutionId);

    /**
     * To get the pageable request item entity for the given patron barcode, item barcode and status.
     *
     * @param pageable      the pageable
     * @param patronBarcode the patron barcode
     * @param itemBarcode   the item barcode
     * @param status        the status
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join request.itemEntity item inner join request.requestStatusEntity status where (request.patronId = :patronBarcode and item.barcode = :itemBarcode and status.requestStatusDescription = :status)  OR (request.patronId = :patronBarcode and item.barcode = :itemBarcode and request.requestingInstitutionId not in (:institutionId) and item.owningInstitutionId = :institutionId and status.requestStatusDescription = :status)")
    Page<RequestItemEntity> findByPatronBarcodeAndItemBarcodeAndStatus(Pageable pageable, @Param("patronBarcode") String patronBarcode, @Param("itemBarcode") String itemBarcode, @Param("status")String status,@Param("institutionId")Integer institutionId);

    /**
     * To get the pageable request item entity which has retrieval, recall and edd status for the given patron barcode and item barcode.
     *
     * @param pageable      the pageable
     * @param patronBarcode the patron barcode
     * @param itemBarcode   the item barcode
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join request.itemEntity item inner join request.requestStatusEntity status where (request.patronId = :patronBarcode and item.barcode = :itemBarcode and status.requestStatusCode in ('RETRIEVAL_ORDER_PLACED', 'RECALL_ORDER_PLACED', 'EDD_ORDER_PLACED'))  OR (request.patronId = :patronBarcode and item.barcode = :itemBarcode and request.requestingInstitutionId not in (:institutionId) and item.owningInstitutionId = :institutionId and status.requestStatusCode in ('RETRIEVAL_ORDER_PLACED', 'RECALL_ORDER_PLACED'))")
    Page<RequestItemEntity> findByPatronBarcodeAndItemBarcodeAndActive(Pageable pageable, @Param("patronBarcode") String patronBarcode, @Param("itemBarcode") String itemBarcode,@Param("institutionId")Integer institutionId);

    /**
     * To get the pageable request item entity which has which has retrieval, recall and edd status for the given item barcode and institution.
     *
     * @param pageable      the pageable
     * @param itemBarcode   the item barcode
     * @param institutionId the institution id
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join request.itemEntity item inner join request.requestStatusEntity status where (item.barcode = :itemBarcode and request.requestingInstitutionId = :institutionId and status.requestStatusCode in ('RETRIEVAL_ORDER_PLACED','RECALL_ORDER_PLACED', 'EDD_ORDER_PLACED')) OR (item.barcode = :itemBarcode and request.requestingInstitutionId not in (:institutionId) and item.owningInstitutionId = :institutionId and status.requestStatusCode in ('RETRIEVAL_ORDER_PLACED','RECALL_ORDER_PLACED','EDD_ORDER_PLACED'))")
    Page<RequestItemEntity> findByItemBarcodeAndActiveAndInstitution(Pageable pageable, @Param("itemBarcode") String itemBarcode,@Param("institutionId")Integer institutionId);

    /**
     * To get the pageable request item entity for the given item barcode, status and institution.
     *
     * @param pageable      the pageable
     * @param itemBarcode   the item barcode
     * @param status        the status
     * @param institutionId the institution id
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join request.itemEntity item inner join request.requestStatusEntity status where (item.barcode = :itemBarcode and request.requestingInstitutionId = :institutionId and status.requestStatusDescription = :status) OR (item.barcode = :itemBarcode and request.requestingInstitutionId not in (:institutionId) and item.owningInstitutionId = :institutionId and status.requestStatusDescription = :status)")
    Page<RequestItemEntity> findByItemBarcodeAndStatusAndInstitution(Pageable pageable, @Param("itemBarcode") String itemBarcode, @Param("status") String status,@Param("institutionId")Integer institutionId);

    /**
     * To get the pageable request item entity for the given patron barcode and institution.
     *
     * @param pageable      the pageable
     * @param patronBarcode the patron barcode
     * @param institutionId the institution id
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request where request.patronId = :patronBarcode and request.requestingInstitutionId = :institutionId")
    Page<RequestItemEntity> findByPatronBarcodeAndInstitution(Pageable pageable, @Param("patronBarcode") String patronBarcode,@Param("institutionId")Integer institutionId);

    /**
     * To get the pageable request item entity for the given item barcode and institution.
     *
     * @param pageable      the pageable
     * @param itemBarcode   the item barcode
     * @param institutionId the institution id
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join request.itemEntity item inner join request.requestStatusEntity status where (item.barcode = :itemBarcode and request.requestingInstitutionId = :institutionId) OR (item.barcode = :itemBarcode and request.requestingInstitutionId not in (:institutionId) and item.owningInstitutionId = :institutionId)")
    Page<RequestItemEntity> findByItemBarcodeAndInstitution(Pageable pageable, @Param("itemBarcode") String itemBarcode,@Param("institutionId")Integer institutionId);

    /**
     * To get the pageable request item entity which has which has retrieval, recall and edd status for the given institution.
     *
     * @param pageable      the pageable
     * @param institutionId the institution id
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join request.requestStatusEntity status inner join request.itemEntity item where (request.requestingInstitutionId = :institutionId and status.requestStatusCode in ('RETRIEVAL_ORDER_PLACED', 'RECALL_ORDER_PLACED','EDD_ORDER_PLACED')) OR (request.requestingInstitutionId not in (:institutionId) and item.owningInstitutionId = :institutionId and status.requestStatusCode in ('RETRIEVAL_ORDER_PLACED', 'RECALL_ORDER_PLACED','EDD_ORDER_PLACED'))")
    Page<RequestItemEntity> findAllActiveAndInstitution(Pageable pageable,@Param("institutionId")Integer institutionId);

    /**
     * To get the pageable request item entity for the given status and institution.
     *
     * @param pageable      the pageable
     * @param status        the status
     * @param institutionId the institution id
     * @return the page
     */
    @Query(value = "select request from RequestItemEntity request inner join  request.requestStatusEntity status inner join request.itemEntity item where (request.requestingInstitutionId = :institutionId and status.requestStatusDescription = :status)  OR (request.requestingInstitutionId not in (:institutionId) and item.owningInstitutionId = :institutionId and status.requestStatusDescription in (:status))")
    Page<RequestItemEntity> findByStatusAndInstitution(Pageable pageable, @Param("status") String status,@Param("institutionId")Integer institutionId);

    /**
     * To get the list of  request item entities for the given list of request id.
     *
     * @param requestId the request id
     * @return the list
     */
    List<RequestItemEntity> findByRequestIdIn(List<Integer> requestId);
}
