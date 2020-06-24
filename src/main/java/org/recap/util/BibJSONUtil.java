package org.recap.util;

import org.apache.commons.lang3.StringUtils;
import org.marc4j.marc.Leader;
import org.marc4j.marc.Record;
import org.recap.RecapCommonConstants;
import org.recap.RecapConstants;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by pvsubrah on 6/15/16.
 */
public class BibJSONUtil extends MarcUtil {

    /**
     * Gets publisher value from the marc record.
     *
     * @param record the record
     * @return the publisher value
     */
    public String getPublisherValue(Record record) {
        String publisherValue;
        List<String> publisherDataFields = Arrays.asList("260", "261", "262", "264");
        for (String publisherDataField : publisherDataFields) {
            publisherValue = getDataFieldValue(record, publisherDataField, null, null, "b");
            if (StringUtils.isNotBlank(publisherValue)) {
                return publisherValue;
            }
        }
        return null;
    }
    
    /**
     * Gets publication date value from marc record.
     *
     * @param record the record
     * @return the publication date value
     */
    public String getPublicationDateValue(Record record) {
        String publicationDateValue;
        List<String> publicationDateDataFields = Arrays.asList("260", "261", "262", "264");
        for (String publicationDateDataField : publicationDateDataFields) {
            publicationDateValue = getDataFieldValue(record, publicationDateDataField, null, null, "c");
            if (StringUtils.isNotBlank(publicationDateValue)) {
                return publicationDateValue;
            }
        }
        return null;
    }

    /**
     * Gets lccn value from marc record.
     *
     * @param record the record
     * @return the lccn value
     */
    public String getLCCNValue(Record record) {
        String lccnValue = getDataFieldValue(record, "010", null, null, "a");
        if (lccnValue != null) {
            lccnValue = lccnValue.trim();
        }
        return lccnValue;
    }
    
    /**
     * Get isbn number from the marc record.
     *
     * @param record the record
     * @return the list
     */
    public List<String> getISBNNumber(Record record){
        List<String> isbnNumbers = new ArrayList<>();
        List<String> isbnNumberList = getMultiDataFieldValues(record,"020", null, null, "a");
        for(String isbnNumber : isbnNumberList){
            isbnNumbers.add(isbnNumber.replaceAll(RecapConstants.OCLC_NUMBER_PATTERN, ""));
        }
        return isbnNumbers;
    }

    /**
     * Get issn number from the marc record.
     *
     * @param record the record
     * @return the list
     */
    public List<String> getISSNNumber(Record record){
        List<String> issnNumbers = new ArrayList<>();
        List<String> issnNumberList = getMultiDataFieldValues(record,"022", null, null, "a");
        for(String issnNumber : issnNumberList){
            issnNumbers.add(issnNumber.replaceAll(RecapConstants.OCLC_NUMBER_PATTERN, ""));
        }
        return issnNumbers;
    }

    /**
     * Gets leader material type from the marc record.
     *
     * @param leader the leader
     * @return the leader material type
     */
    public String getLeaderMaterialType(Leader leader) {
        String leaderMaterialType = null;
        String leaderFieldValue = leader != null ? leader.toString() : null;
        if ((leaderFieldValue!=null) && StringUtils.isNotBlank(leaderFieldValue) && leaderFieldValue.length() > 7) {
            char materialTypeChar = leaderFieldValue.charAt(7);
            if ('m' == materialTypeChar) {
                leaderMaterialType = RecapCommonConstants.MONOGRAPH;
            } else if ('s' == materialTypeChar) {
                leaderMaterialType = RecapCommonConstants.SERIAL;
            } else {
                leaderMaterialType = RecapCommonConstants.OTHER;
            }
        }
        return leaderMaterialType;
    }

    /**
     * Gets title from the marc record.
     *
     * @param marcRecord the marc record
     * @return the title
     */
    public String getTitle(Record marcRecord) {
        StringBuilder title=new StringBuilder();
        title.append(getDataFieldValueStartsWith(marcRecord, "245", Arrays.asList('a', 'b','n','p')) + " ");
        title.append(getDataFieldValueStartsWith(marcRecord, "246", Arrays.asList('a', 'b')) + " ");
        title.append(getDataFieldValueStartsWith(marcRecord, "130", Arrays.asList('a')) + " ");
        title.append(getDataFieldValueStartsWith(marcRecord, "730", Arrays.asList('a')) + " ");
        title.append(getDataFieldValueStartsWith(marcRecord, "740", Arrays.asList('a')) + " ");
        title.append(getDataFieldValueStartsWith(marcRecord, "830", Arrays.asList('a'))+ " ");
        return title.toString();
    }

    /**
     * Gets title display from the marc record.
     *
     * @param marcRecord the marc record
     * @return the title display
     */
    public String getTitleDisplay(Record marcRecord) {
        StringBuilder titleDisplay = new StringBuilder();
        titleDisplay.append(getDataFieldValueStartsWith(marcRecord, "245", Arrays.asList('a', 'b', 'c', 'f', 'g', 'h', 'k', 'n', 'p', 's')));
        return titleDisplay.toString();
    }

    /**
     * Gets author display value from the marc record.
     *
     * @param marcRecord the marc record
     * @return the author display value
     */
    public String getAuthorDisplayValue(Record marcRecord) {
        StringBuilder author = new StringBuilder();
        author.append(getDataFieldValueStartsWith(marcRecord, "100", Arrays.asList('a','b','c','d','e','f','g','j','k','l','n','p','q','t','u')) + " ");
        author.append(getDataFieldValueStartsWith(marcRecord, "110", Arrays.asList('a','b','c','d','e','f','g','k','l','n','p','t','u')) + " ");
        author.append(getDataFieldValueStartsWith(marcRecord, "111", Arrays.asList('a','c','d','e','f','g','j','k','l','n','p','q','t','u')) + " ");
        author.append(getDataFieldValueStartsWith(marcRecord, "130", Arrays.asList('a','d','f','g','h','k','l','m','n','o','p','r','s','t')));

        return author.toString();
    }

    /**
     * Gets author search value from the marc record.
     *
     * @param marcRecord the marc record
     * @return the author search value
     */
    public List<String> getAuthorSearchValue(Record marcRecord) {
        List<String> authorSearchValues = new ArrayList<>();
        List<String> fieldValues;

        Map<String, List<Character>> authorMap = new HashMap<>();
        authorMap.put("100", Arrays.asList('a','q'));
        authorMap.put("110", Arrays.asList('a','b'));
        authorMap.put("111", Arrays.asList('a'));
        authorMap.put("700", Arrays.asList('a'));
        authorMap.put("710", Arrays.asList('a','b'));
        authorMap.put("711", Arrays.asList('a'));


        for (Map.Entry<String, List<Character>> entry : authorMap.entrySet()) {
            fieldValues = getListOfDataFieldValuesStartsWith(marcRecord, entry.getKey(), entry.getValue());
            if (!CollectionUtils.isEmpty(fieldValues)) {
                authorSearchValues.addAll(fieldValues);
            }
        }
        return authorSearchValues;
    }

    /**
     * Gets leader from the marc record.
     *
     * @param marcRecord the marc record
     * @return the leader
     */
    public String getLeader(Record marcRecord) {
        return marcRecord.getLeader() != null ? marcRecord.getLeader().toString() : null;
    }

    /**
     * Gets title sort from the marc record.
     *
     * @param marcRecord   the marc record
     * @param titleDisplay the title display
     * @return the title sort
     */
    public String getTitleSort(Record marcRecord, String titleDisplay) {
        Integer secondIndicatorForDataField = getSecondIndicatorForDataField(marcRecord, "245");
        if (StringUtils.isNotBlank(titleDisplay) && titleDisplay.length() >= secondIndicatorForDataField) {
            return titleDisplay.substring(secondIndicatorForDataField);
        }
        return "";
    }
}
