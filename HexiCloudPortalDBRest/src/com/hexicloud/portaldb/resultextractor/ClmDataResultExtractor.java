package com.hexicloud.portaldb.resultextractor;

import com.hexicloud.portaldb.bean.ProvisionedService;
import com.hexicloud.portaldb.bean.ServiceDetail;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ClmDataResultExtractor implements ResultSetExtractor<List<ProvisionedService>> {
    public ClmDataResultExtractor() {
        super();
    }

    public static Map<String, String> uomMap = new HashMap<String, String>();

    @Override
    public List<ProvisionedService> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<String, ProvisionedService> provisionedServicesMap = new HashMap<String, ProvisionedService>();
        while (resultSet.next()) {
            String tier6 = resultSet.getString("FPH_DESCRIPTION_TIER_6");
            ProvisionedService provisionedService = provisionedServicesMap.get(tier6);
            if (provisionedService == null) {
                provisionedService = new ProvisionedService();
                provisionedService.setPlatform(makePlatform(resultSet.getString("FPH_DESCRIPTION_TIER_4")));
                provisionedService.setService(makeService(resultSet.getString("FPH_DESCRIPTION_TIER_6")));
                provisionedService.setServiceType(makeServiceType(resultSet.getString("FPH_DESCRIPTION_TIER_6")));
                provisionedService.setDisplayOrder(makeDisplayOrder(provisionedService.getService(), provisionedService.getServiceType()));
                provisionedServicesMap.put(tier6, provisionedService);
            }
            List detailsList = provisionedService.getDetails();
            if (detailsList == null) {
                detailsList = new ArrayList<ServiceDetail>();
                provisionedService.setDetails(detailsList);
            }
            ServiceDetail detail = new ServiceDetail();
            detail.setQuantity(resultSet.getInt("QUANTITY"));
            detail.setUom(makeUom(resultSet.getString("QUANTITY_UNIT_OF_MEASURE")));
            detailsList.add(detail);
        }
        ArrayList<ProvisionedService> provisionedServicesList = new ArrayList<ProvisionedService>(provisionedServicesMap.values());
        provisionedServicesList.sort(Comparator.comparing(ProvisionedService::getDisplayOrder));
        return provisionedServicesList;
    }

    public static String makePlatform(String fullPlatformName) {
        if (fullPlatformName.length() == 4) {
            return fullPlatformName;
        } else if (fullPlatformName.length() > 4) {
            return fullPlatformName.substring(fullPlatformName.length() - 4);
        } else {
            throw new IllegalArgumentException("fullPlatformName has less than 4 characters!");
        }
    }

    public static String makeService(String tier6) {
        if (tier6.contains(" ")) {
            return tier6.substring(0, tier6.indexOf(" "));
        } else {
            return tier6;
        }
    }

    public static String makeServiceType(String tier6) {
        if (tier6.toLowerCase().contains("non metered")) {
            return "NON METERED";
        } else {
            return "METERED";
        }
    }

    public int makeDisplayOrder(String service, String serviceType) {
        if (service.equalsIgnoreCase("compute")) {
            return serviceType.equalsIgnoreCase("metered") ? 1 : 2;
        }
        if (service.equalsIgnoreCase("storage")) {
            return serviceType.equalsIgnoreCase("metered") ? 3 : 4;
        }
        if (service.equalsIgnoreCase("network")) {
            return serviceType.equalsIgnoreCase("metered") ? 5 : 6;
        }
        if (service.equalsIgnoreCase("container")) {
            return serviceType.equalsIgnoreCase("metered") ? 7 : 8;
        }
        if (service.equalsIgnoreCase("ravello")) {
            return serviceType.equalsIgnoreCase("metered") ? 9 : 10;
        }
        if (service.equalsIgnoreCase("cloud machine")) {
            return serviceType.equalsIgnoreCase("metered") ? 11 : 12;
        }
        return 0;
    }

    public static String makeUom(String fullUom) {
        if (uomMap.isEmpty()) {
            uomMap.put("GIGABYTE OUTBOUND DATA TRANS PER MONTH", "GB Data/Month");
            uomMap.put("OCPU PER HOUR", "OCPU/Hour");
            uomMap.put("EACH", "Each");
            uomMap.put("TB OF STORAGE CAPACITY", "TB");
            uomMap.put("CURRENCY UNIT", "Currency");
            uomMap.put("PAGE VIEW", "Page View");
            uomMap.put("HOSTED ENVIRONMENT", "Hosted Env");
            uomMap.put("VPN CONNECTION", "VPN");
            uomMap.put("MBPS", "MBPS");
            uomMap.put("GIGABYTE STORAGE CAPACITY PER MONTH", "GB/Month");
            uomMap.put("PIN ENTRY DEVICE", "Pin Entry Device");
            uomMap.put("STATIC IP PER HOUR", "Static IP/Hour");
            uomMap.put("OCPU", "OCPU");
        }
        return uomMap.get(fullUom);
    }
}
