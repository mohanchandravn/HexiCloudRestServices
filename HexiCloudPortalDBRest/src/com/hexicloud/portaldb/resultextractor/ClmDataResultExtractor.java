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

    private static Map<String, String> uomMap = new HashMap<String, String>();
    private static Map<String, String> tier9ShortMap = new HashMap<String, String>();
    private static Map<String, String> tier9LongMap = new HashMap<String, String>();

    @Override
    public List<ProvisionedService> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<String, ProvisionedService> provisionedServicesMap = new HashMap<String, ProvisionedService>();
        String tier6 = null;
        ProvisionedService provisionedService = null;
        List<ServiceDetail> detailsList = null;
        ServiceDetail detail = null;
        String tier9 = null;
        boolean hasSimilarTier9 = false;
        while (resultSet.next()) {
            hasSimilarTier9 = false;
            tier6 = resultSet.getString("FPH_DESCRIPTION_TIER_6");
            provisionedService = provisionedServicesMap.get(tier6);
            if (provisionedService == null) {
                provisionedService = new ProvisionedService();
                provisionedService.setPlatform(makePlatform(resultSet.getString("FPH_DESCRIPTION_TIER_4")));
                provisionedService.setService(makeService(resultSet.getString("FPH_DESCRIPTION_TIER_6")));
                provisionedService.setServiceType(makeServiceType(resultSet.getString("FPH_DESCRIPTION_TIER_6")));
                provisionedService.setDisplayOrder(makeDisplayOrder(provisionedService.getService(),
                                                                    provisionedService.getServiceType()));
                provisionedServicesMap.put(tier6, provisionedService);
            }
            detailsList = provisionedService.getDetails();
            tier9 = resultSet.getString("PROD_TIER_9");
            if (detailsList == null) {
                detailsList = new ArrayList<ServiceDetail>();
                provisionedService.setDetails(detailsList);
                detailsList.add(makeNewServiceDetail(resultSet, detail));
            } else {
                for (ServiceDetail sDetail : detailsList) {
                    if (null != sDetail.getActualTier9()) {
                        if (sDetail.getActualTier9().equalsIgnoreCase(tier9)) {
                            sDetail.setQuantity(sDetail.getQuantity() + resultSet.getInt("QUANTITY"));
                            hasSimilarTier9 = true;
                        } 
                    }
                }
                if (!hasSimilarTier9) {
                    detailsList.add(makeNewServiceDetail(resultSet, detail));
                }
            }
        }
        ArrayList<ProvisionedService> provisionedServicesList =
            new ArrayList<ProvisionedService>(provisionedServicesMap.values());
        provisionedServicesList.sort(Comparator.comparing(ProvisionedService::getDisplayOrder));
        return provisionedServicesList;
    }

    private static String makePlatform(String fullPlatformName) {
        if (fullPlatformName.length() == 4) {
            return fullPlatformName;
        } else if (fullPlatformName.length() > 4) {
            return fullPlatformName.substring(fullPlatformName.length() - 4);
        } else {
            throw new IllegalArgumentException("fullPlatformName has less than 4 characters!");
        }
    }

    private static String makeService(String tier6) {
        if (tier6.contains("Bare Metal")) {
            return "Bare Metal";
        } else if (tier6.contains(" ")) {
            return tier6.substring(0, tier6.indexOf(" "));
        } else {
            return tier6;
        }
    }

    private static String makeServiceType(String tier6) {
        if (tier6.toLowerCase().contains("non metered")) {
            return "NON METERED";
        } else {
            return "METERED";
        }
    }

    private int makeDisplayOrder(String service, String serviceType) {
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

    private static String makeUom(String fullUom) {
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

    private static String makeShortTier9(String fullTier9) {
        if (tier9ShortMap.isEmpty()) {
            tier9ShortMap.put("Oracle Bare Metal Cloud Service - Standard Compute Capacity - Metered - OCPU Per Hour",
                              "Compute Capicity");
            tier9ShortMap.put("Oracle Compute Cloud Service - Additional Static IP - Static IP Per Hour",
                              "Additional Static IP");
            tier9ShortMap.put("Oracle Compute Cloud Service - Block Storage - Non-metered - Academy - TB of Storage Capacity",
                              "Academy");
            tier9ShortMap.put("Oracle Compute Cloud Service - Block Storage - Non-metered - Public Sector - TB of Storage Capacity",
                              "Public Sector");
            tier9ShortMap.put("Oracle Compute Cloud Service - Block Storage - Non-metered - TB of Storage Capacity",
                              "");
            tier9ShortMap.put("Oracle Compute Cloud Service - Compute Capacity - Model 100 - Hosted Environment",
                              "Model 100");
            tier9ShortMap.put("Oracle Compute Cloud Service - Compute Capacity - Model 50 - Hosted Environment",
                              "Model 50");
            tier9ShortMap.put("Oracle Compute Cloud Service - Compute Capacity - Non-metered - OCPU", "Non-metered");
            tier9ShortMap.put("Oracle Compute Cloud Service - Dedicated Compute Capacity - Model 1000 - Hosted Environment",
                              "Model 1000");
            tier9ShortMap.put("Oracle Compute Cloud Service - Dedicated Compute Capacity - Model 1500 - Hosted Environment",
                              "Model 1500");
            tier9ShortMap.put("Oracle Compute Cloud Service - Dedicated Compute Capacity - Model 2000 - Hosted Environment",
                              "Model 2000");
            tier9ShortMap.put("Oracle Compute Cloud Service - Dedicated Compute Capacity - Model 500 - Hosted Environment",
                              "Model 500");
            tier9ShortMap.put("Oracle Compute Cloud Service - Dedicated Compute Capacity - SPARC Model 300 - Non-metered - Hosted Environment",
                              "SPARC Model 300");
            tier9ShortMap.put("Oracle Compute Cloud Service - High I/O Compute Capacity - Non-metered - Hosted Environment",
                              "High I/O");
            tier9ShortMap.put("Oracle Compute Cloud Service - SPARC Block Storage - Non-metered - TB of Storage Capacity",
                              "SPARC Block Storage");
            tier9ShortMap.put("Oracle Container Cloud Service - Non-metered - Hosted Environment", "");
            tier9ShortMap.put("Oracle Fusion Cloud Storage Free Month Promotion", "Free Month");
            tier9ShortMap.put("Oracle Network Cloud Service - FastConnect Partner Edition - Equinix - Port Speed 10G - Hosted Environment",
                              "Equinix - Port Speed 10G");
            tier9ShortMap.put("Oracle Network Cloud Service - FastConnect Partner Edition - Equinix - Port Speed 1G - Hosted Environment",
                              "Equinix - Port Speed 1G");
            tier9ShortMap.put("Oracle Network Cloud Service - FastConnect Partner Edition - Verizon SCI - Non-metered - Hosted Environment",
                              "Verizon SCI");
            tier9ShortMap.put("Oracle Network Cloud Service - FastConnect Standard Edition - Port Speed 1G - Hosted Environment",
                              "Port Speed 1G");
            tier9ShortMap.put("Oracle Network Cloud Service - VPN for Engineered Systems - Non-metered - VPN Connection",
                              "Engineered Systems");
            tier9ShortMap.put("Oracle Public Cloud Machine X5 Model 288 - Each", "X5 Model 288");
            tier9ShortMap.put("Oracle Public Cloud Machine ZS3 Model 268 - Non-metered - Each", "ZS3 Model 268");
            tier9ShortMap.put("Oracle Public Cloud Machine ZS3 Model 536 - Non-metered - Each", "ZS3 Model 536");
            tier9ShortMap.put("Oracle Storage Cloud Service - Non-metered - Academy - TB of Storage Capacity",
                              "Academy");
            tier9ShortMap.put("Oracle Storage Cloud Service - Non-metered - Public Sector - TB of Storage Capacity",
                              "Public Sector");
            tier9ShortMap.put("Oracle Storage Cloud Service - Non-metered - TB of Storage Capacity", "");


        }
        return tier9ShortMap.get(fullTier9);
    }

    private static String makeLongTier9(String fullTier9) {
        if (tier9LongMap.isEmpty()) {
            tier9LongMap.put("Oracle Bare Metal Cloud Service - Standard Compute Capacity - Metered - OCPU Per Hour",
                             "Standard Compute Capacity");
            tier9LongMap.put("Oracle Compute Cloud Service - Additional Static IP - Static IP Per Hour",
                             "Additional Static IP - Static IP Per Hour");
            tier9LongMap.put("Oracle Compute Cloud Service - Block Storage - Non-metered - Academy - TB of Storage Capacity",
                             "Block Storage - Non-metered - Academy");
            tier9LongMap.put("Oracle Compute Cloud Service - Block Storage - Non-metered - Public Sector - TB of Storage Capacity",
                             "Block Storage - Non-metered - Public Sector");
            tier9LongMap.put("Oracle Compute Cloud Service - Block Storage - Non-metered - TB of Storage Capacity",
                             "Block Storage - Non-metered");
            tier9LongMap.put("Oracle Compute Cloud Service - Compute Capacity - Model 100 - Hosted Environment",
                             "Compute Capacity - Model 100 ");
            tier9LongMap.put("Oracle Compute Cloud Service - Compute Capacity - Model 50 - Hosted Environment",
                             "Compute Capacity - Model 50");
            tier9LongMap.put("Oracle Compute Cloud Service - Compute Capacity - Non-metered - OCPU",
                             " Compute Capacity - Non-metered");
            tier9LongMap.put("Oracle Compute Cloud Service - Dedicated Compute Capacity - Model 1000 - Hosted Environment",
                             "Dedicated Compute Capacity - Model 1000 ");
            tier9LongMap.put("Oracle Compute Cloud Service - Dedicated Compute Capacity - Model 1500 - Hosted Environment",
                             "Dedicated Compute Capacity - Model 1500 ");
            tier9LongMap.put("Oracle Compute Cloud Service - Dedicated Compute Capacity - Model 2000 - Hosted Environment",
                             "Dedicated Compute Capacity - Model 2000 ");
            tier9LongMap.put("Oracle Compute Cloud Service - Dedicated Compute Capacity - Model 500 - Hosted Environment",
                             "Dedicated Compute Capacity - Model 500 ");
            tier9LongMap.put("Oracle Compute Cloud Service - Dedicated Compute Capacity - SPARC Model 300 - Non-metered - Hosted Environment",
                             "Dedicated Compute Capacity - SPARC Model 300 - Non-metered");
            tier9LongMap.put("Oracle Compute Cloud Service - High I/O Compute Capacity - Non-metered - Hosted Environment",
                             "High I/O Compute Capacity - Non-metered");
            tier9LongMap.put("Oracle Compute Cloud Service - SPARC Block Storage - Non-metered - TB of Storage Capacity",
                             "SPARC Block Storage - Non-metered ");
            tier9LongMap.put("Oracle Container Cloud Service - Non-metered - Hosted Environment", "");
            tier9LongMap.put("Oracle Fusion Cloud Storage Free Month Promotion", "Free Month Promotion");
            tier9LongMap.put("Oracle Network Cloud Service - FastConnect Partner Edition - Equinix - Port Speed 10G - Hosted Environment",
                             "FastConnect Partner Edition - Equinix - Port Speed 10G");
            tier9LongMap.put("Oracle Network Cloud Service - FastConnect Partner Edition - Equinix - Port Speed 1G - Hosted Environment",
                             "FastConnect Partner Edition - Equinix - Port Speed 1G");
            tier9LongMap.put("Oracle Network Cloud Service - FastConnect Partner Edition - Verizon SCI - Non-metered - Hosted Environment",
                             "FastConnect Partner Edition - Verizon SCI - Non-metered");
            tier9LongMap.put("Oracle Network Cloud Service - FastConnect Standard Edition - Port Speed 1G - Hosted Environment",
                             "FastConnect Standard Edition - Port Speed 1G");
            tier9LongMap.put("Oracle Network Cloud Service - VPN for Engineered Systems - Non-metered - VPN Connection",
                             "VPN for Engineered Systems - Non-metered");
            tier9LongMap.put("Oracle Public Cloud Machine X5 Model 288 - Each", "X5 Model 288 - Each");
            tier9LongMap.put("Oracle Public Cloud Machine ZS3 Model 268 - Non-metered - Each",
                             "ZS3 Model 268 - Non-metered - Each");
            tier9LongMap.put("Oracle Public Cloud Machine ZS3 Model 536 - Non-metered - Each",
                             "ZS3 Model 536 - Non-metered - Each");
            tier9LongMap.put("Oracle Storage Cloud Service - Non-metered - Academy - TB of Storage Capacity",
                             "Object Storage - Academy");
            tier9LongMap.put("Oracle Storage Cloud Service - Non-metered - Public Sector - TB of Storage Capacity",
                             "Object Storage - Public Sector");
            tier9LongMap.put("Oracle Storage Cloud Service - Non-metered - TB of Storage Capacity", "Object Storage");

        }
        return tier9LongMap.get(fullTier9);
    }

    private ServiceDetail makeNewServiceDetail(ResultSet resultSet, ServiceDetail detail) throws SQLException,
                                                                                                 DataAccessException {
        detail = new ServiceDetail();
        detail.setQuantity(resultSet.getInt("QUANTITY"));
        detail.setUom(makeUom(resultSet.getString("QUANTITY_UNIT_OF_MEASURE")));
        detail.setActualTier9(resultSet.getString("PROD_TIER_9"));
        detail.setTier9ShortDesc(makeShortTier9(resultSet.getString("PROD_TIER_9")));
        detail.setTier9LongDesc(makeLongTier9(resultSet.getString("PROD_TIER_9")));
        return detail;
    }
}
