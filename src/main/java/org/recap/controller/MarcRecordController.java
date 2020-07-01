package org.recap.controller;

import org.apache.commons.lang3.StringUtils;
import org.recap.model.search.BibliographicMarcForm;
import org.recap.util.MarcRecordViewUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;

/**
 * Created by rajeshbabuk on 22/7/16.
 */
@Controller
public class MarcRecordController {

    @Autowired
    private MarcRecordViewUtil marcRecordViewUtil;

    /**
     * Renders the marc record view UI page for the scsb application.
     *
     * @param bibId the bib id
     * @param model the model
     * @return the string
     */
    @GetMapping(path = "/openMarcRecord")
    public String openMarcRecord(@Valid @ModelAttribute("bibId") Integer bibId, Model model) {
        BibliographicMarcForm bibliographicMarcForm = marcRecordViewUtil.buildBibliographicMarcForm(bibId, null,null);
        model.addAttribute("bibliographicMarcForm", bibliographicMarcForm);
        if (null != bibliographicMarcForm && StringUtils.isNotBlank(bibliographicMarcForm.getErrorMessage())) {
            return "marcRecordErrorMessage";
        } else {
            return "marcRecordView";
        }
    }

}
