package org.recap.util;

import com.csvreader.CsvWriter;
import org.recap.RecapCommonConstants;
import org.recap.model.search.SearchItemResultRow;
import org.recap.model.search.SearchResultRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by chenchulakshmig on 4/7/16.
 */
@Component
public class CsvUtil {

    private static final Logger logger = LoggerFactory.getLogger(CsvUtil.class);

    /**
     * Exports the selected search result rows into a csv file in the search UI page.
     *
     * @param searchResultRows      the search result rows
     * @param fileNameWithExtension the file name with extension
     * @return file
     */
    public File writeSearchResultsToCsv(List<SearchResultRow> searchResultRows, String fileNameWithExtension) {
        File file = new File(fileNameWithExtension);
        CsvWriter csvOutput = null;
        if (!CollectionUtils.isEmpty(searchResultRows)) {
            try (FileWriter fileWriter = new FileWriter(file)){
                csvOutput = new CsvWriter(fileWriter, ',');
                writeMainHeaderRow(csvOutput);
                for (SearchResultRow searchResultRow : searchResultRows) {
                    if (searchResultRow.isSelected()) {
                        writeMainDataRow(searchResultRow, csvOutput);
                    } else if (!CollectionUtils.isEmpty(searchResultRow.getSearchItemResultRows())) {
                        if (searchResultRow.isSelectAllItems() || isAnyItemSelected(searchResultRow.getSearchItemResultRows())) {
                            writeMainDataRow(searchResultRow, csvOutput);
                        }
                        boolean isHeaderExists = false;
                        for (SearchItemResultRow searchItemResultRow : searchResultRow.getSearchItemResultRows()) {
                            if (searchItemResultRow.isSelectedItem()) {
                                if (!isHeaderExists) {
                                    writeChildHeaderRow(csvOutput);
                                    isHeaderExists = true;
                                }
                                writeChildDataRow(searchItemResultRow, csvOutput);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(RecapCommonConstants.LOG_ERROR,e);
            } finally {
                if(csvOutput!=null) {
                    csvOutput.flush();
                    csvOutput.close();
                }
            }
        }
        return file;
    }

    private void writeMainHeaderRow(CsvWriter csvOutput) throws IOException {
        csvOutput.write("Title");
        csvOutput.write("Author");
        csvOutput.write("Publisher");
        csvOutput.write("Publisher Date");
        csvOutput.write("Owning Institution");
        csvOutput.write("Customer Code");
        csvOutput.write("Collection Group Designation");
        csvOutput.write("Use Restriction");
        csvOutput.write("Barcode");
        csvOutput.write("Summary Holdings");
        csvOutput.endRecord();
    }

    private void writeMainDataRow(SearchResultRow searchResultRow, CsvWriter csvOutput) throws IOException {
        csvOutput.write(searchResultRow.getTitle());
        csvOutput.write(searchResultRow.getAuthor());
        csvOutput.write(searchResultRow.getPublisher());
        csvOutput.write(searchResultRow.getPublisherDate());
        csvOutput.write(searchResultRow.getOwningInstitution());
        csvOutput.write(searchResultRow.getCustomerCode());
        csvOutput.write(searchResultRow.getCollectionGroupDesignation());
        csvOutput.write(searchResultRow.getUseRestriction().equalsIgnoreCase(RecapCommonConstants.NO_RESTRICTIONS) ? "" : searchResultRow.getUseRestriction());
        csvOutput.write(searchResultRow.getBarcode());
        csvOutput.write(searchResultRow.getSummaryHoldings());
        csvOutput.endRecord();
    }

    private void writeChildHeaderRow(CsvWriter csvOutput) throws IOException {
        csvOutput.write("");
        csvOutput.write("");
        csvOutput.write("");
        csvOutput.write("Call Number");
        csvOutput.write("Chronology & Enumeration");
        csvOutput.write("Customer Code");
        csvOutput.write("Collection Group Designation");
        csvOutput.write("Use Restriction");
        csvOutput.write("Barcode");
        csvOutput.endRecord();
    }

    private void writeChildDataRow(SearchItemResultRow searchItemResultRow, CsvWriter csvOutput) throws IOException {
        csvOutput.write("");
        csvOutput.write("");
        csvOutput.write("");
        csvOutput.write(searchItemResultRow.getCallNumber());
        csvOutput.write(searchItemResultRow.getChronologyAndEnum());
        csvOutput.write(searchItemResultRow.getCustomerCode());
        csvOutput.write(searchItemResultRow.getCollectionGroupDesignation());
        csvOutput.write(searchItemResultRow.getUseRestriction().equalsIgnoreCase(RecapCommonConstants.NO_RESTRICTIONS) ? "" : searchItemResultRow.getUseRestriction());
        csvOutput.write(searchItemResultRow.getBarcode());
        csvOutput.endRecord();
    }

    private boolean isAnyItemSelected(List<SearchItemResultRow> searchItemResultRows) {
        for (SearchItemResultRow searchItemResultRow : searchItemResultRows) {
            if (searchItemResultRow.isSelectedItem()) {
                return true;
            }
        }
        return false;
    }
}
