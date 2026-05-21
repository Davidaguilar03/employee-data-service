package co.edu.uptc.employee_data_service.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/info")
@CrossOrigin(origins = "*")
public class InfoController {

    @Value("${HOSTNAME:unknown-pod}")
    private String podName;

    @Value("${NODE_NAME:unknown-node}")
    private String nodeName;

    @Value("${app.identifier:Employee Manager}")
    private String appIdentifier;
    
    @GetMapping
    public Map<String, String> getInfo() {
        return Map.of(
                "podName", podName,
                "nodeName", nodeName,
                "appIdentifier", appIdentifier,
                "version", "1.0.0"
        );
    }
}