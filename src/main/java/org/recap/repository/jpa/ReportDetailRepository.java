package org.recap.repository.jpa;

import org.recap.model.jpa.ReportEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

/**
 * Created by SheikS on 8/8/2016.
 */
@RepositoryRestResource(collectionResourceRel = "report", path = "report")
public interface ReportDetailRepository extends BaseRepository<ReportEntity> {

    /**
     *To get the list of report entities for the given file name.
     *
     * @param fileName the file name
     * @return the list
     */
    List<ReportEntity> findByFileName(String fileName);

    /**
     * To get the list of
     *
     * @param fileName the file name
     * @param type     the type
     * @return the list
     */
    List<ReportEntity> findByFileNameAndType(String fileName, String type);

    /**
     * Find by file name and institution name list.
     *
     * @param fileName        the file name
     * @param institutionName the institution name
     * @return the list
     */
    List<ReportEntity> findByFileNameAndInstitutionName(String fileName, String institutionName);

    /**
     * Find by file name and institution name and type list.
     *
     * @param fileName        the file name
     * @param institutionName the institution name
     * @param type            the type
     * @return the list
     */
    List<ReportEntity> findByFileNameAndInstitutionNameAndType(String fileName, String institutionName, String type);

    /**
     * Find by file and date range list.
     *
     * @param fileName the file name
     * @param from     the from
     * @param to       the to
     * @return the list
     */
    @Query(value = "select * from report_t where FILE_NAME=?1 and CREATED_DATE >= ?2 and CREATED_DATE <= ?3",  nativeQuery = true)
    List<ReportEntity> findByFileAndDateRange(String fileName, Date from, Date to);

    /**
     * Find by type and date range list.
     *
     * @param type the type
     * @param from the from
     * @param to   the to
     * @return the list
     */
    @Query(value = "select * from report_t where TYPE=?1 and CREATED_DATE >= ?2 and CREATED_DATE <= ?3", nativeQuery = true)
    List<ReportEntity> findByTypeAndDateRange(@Param("type") String type, @Param("from") String from, @Param("to") String to);

    /**
     * Find by file and type and date range list.
     *
     * @param fileName the file name
     * @param type     the type
     * @param from     the from
     * @param to       the to
     * @return the list
     */
    @Query(value = "select * from report_t where FILE_NAME=?1 and TYPE=?2 and CREATED_DATE >= ?3 and CREATED_DATE <= ?4", nativeQuery = true)
    List<ReportEntity> findByFileAndTypeAndDateRange(String fileName, String type, Date from, Date to);

    /**
     * Find by file and institution and type and date range list.
     *
     * @param fileName        the file name
     * @param institutionName the institution name
     * @param type            the type
     * @param from            the from
     * @param to              the to
     * @return the list
     */
    @Query(value = "select * from report_t where FILE_NAME=?1 and INSTITUTION_NAME=?2 and TYPE=?3 and CREATED_DATE >= ?4 and CREATED_DATE <= ?5", nativeQuery = true)
    List<ReportEntity> findByFileAndInstitutionAndTypeAndDateRange(String fileName, String institutionName, String type, Date from, Date to);

    /**
     * Find by institution and type and date range list.
     *
     * @param institutionName the institution name
     * @param type            the type
     * @param from            the from
     * @param to              the to
     * @return the list
     */
    @Query(value = "select * from report_t where INSTITUTION_NAME=?1 and TYPE=?2 and CREATED_DATE >= ?3 and CREATED_DATE <= ?4", nativeQuery = true)
    List<ReportEntity> findByInstitutionAndTypeAndDateRange(String institutionName, String type, Date from, Date to);
}
