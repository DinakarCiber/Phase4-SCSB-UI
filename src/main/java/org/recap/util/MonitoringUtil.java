package org.recap.util;

import org.recap.RecapConstants;
import org.recap.model.search.Monitoring;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MonitoringUtil {

    @Value("${ui.monitoring.url}")
    private String uiMonitoringUrl;

    @Value("${ui.logging.url}")
    private String uiLoggingUrl;

    @Value("${auth.monitoring.url}")
    private String authMonitoringUrl;

    @Value("${auth.logging.url}")
    private String authLoggingUrl;

    @Value("${gateway.monitoring.url}")
    private String gatewayMonitoringUrl;

    @Value("${gateway.logging.url}")
    private String gatewayLoggingUrl;

    @Value("${doc.monitoring.url}")
    private String docMonitoringUrl;

    @Value("${doc.logging.url}")
    private String docLoggingUrl;

    @Value("${circ.monitoring.url}")
    private String circMonitoringUrl;

    @Value("${circ.logging.url}")
    private String circLoggingUrl;

    @Value("${batch.monitoring.url}")
    private String batchMonitoringUrl;

    @Value("${batch.logging.url}")
    private String batchLoggingUrl;

    /**
     * This will return all monitoring and logging related url's for all projects from properties
     * @return
     */
    public List<Monitoring> getMonitoringProjects() {

        List<Monitoring> projects = new ArrayList<>();
        Monitoring monitoringUi = new Monitoring();
        monitoringUi.setProjectName(RecapConstants.SCSB_UI);
        monitoringUi.setMonitoringUrl(uiMonitoringUrl);
        monitoringUi.setLoggingUrl(uiLoggingUrl);
        projects.add(monitoringUi);

        Monitoring monitoringAuth = new Monitoring();
        monitoringAuth.setProjectName(RecapConstants.SCSB_AUTH);
        monitoringAuth.setMonitoringUrl(authMonitoringUrl);
        monitoringAuth.setLoggingUrl(authLoggingUrl);
        projects.add(monitoringAuth);

        Monitoring monitoringGateway = new Monitoring();
        monitoringGateway.setProjectName(RecapConstants.SCSB_GATEWAY);
        monitoringGateway.setMonitoringUrl(gatewayMonitoringUrl);
        monitoringGateway.setLoggingUrl(gatewayLoggingUrl);
        projects.add(monitoringGateway);

        Monitoring monitoringDoc = new Monitoring();
        monitoringDoc.setProjectName(RecapConstants.SCSB_DOC);
        monitoringDoc.setMonitoringUrl(docMonitoringUrl);
        monitoringDoc.setLoggingUrl(docLoggingUrl);
        projects.add(monitoringDoc);

        Monitoring monitoringCirc = new Monitoring();
        monitoringCirc.setProjectName(RecapConstants.SCSB_CIRC);
        monitoringCirc.setMonitoringUrl(circMonitoringUrl);
        monitoringCirc.setLoggingUrl(circLoggingUrl);
        projects.add(monitoringCirc);

        Monitoring monitoringBatch = new Monitoring();
        monitoringBatch.setProjectName(RecapConstants.SCSB_BATCH);
        monitoringBatch.setMonitoringUrl(batchMonitoringUrl);
        monitoringBatch.setLoggingUrl(batchLoggingUrl);
        projects.add(monitoringBatch);

        return projects;
    }
}
