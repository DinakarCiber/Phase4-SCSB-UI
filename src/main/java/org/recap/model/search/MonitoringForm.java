package org.recap.model.search;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MonitoringForm {
    private List<Monitoring> projects = new ArrayList<>();
}
