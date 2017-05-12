package com.hexicloud.portaldb.resultextractor;

import com.hexicloud.portaldb.bean.Service;
import com.hexicloud.portaldb.bean.UseCase;
import com.hexicloud.portaldb.bean.UseCases;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.util.StringUtils;

public class UseCaseServicesExtractor implements ResultSetExtractor<UseCases> {
    public UseCaseServicesExtractor() {
        super();
    }

    @Override
    public UseCases extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        UseCases useCases = new UseCases();
        Map<Integer, UseCase> useCaseDetailMap = new HashMap<Integer, UseCase>();
        while (resultSet.next()) {
            Integer id = Integer.valueOf(resultSet.getInt("USECASE_ID"));
            UseCase useCaseDetail = useCaseDetailMap.get(id);
            if (useCaseDetail == null) {
                useCaseDetail = new UseCase();
                useCaseDetail.setId(resultSet.getInt("USECASE_ID"));
                useCaseDetail.setTitle(resultSet.getString("TITLE"));
                useCaseDetail.setShortDesc(resultSet.getString("SHORT_DESC"));
                useCaseDetail.setLongDesc(resultSet.getString("LONG_DESC"));
                useCaseDetail.setImage(resultSet.getString("IMAGE"));
                useCaseDetailMap.put(id, useCaseDetail);
            }
            if (!StringUtils.isEmpty(resultSet.getString("SERVICE_ID"))) {
                List servicesList = useCaseDetail.getServices();
                if (servicesList == null) {
                    servicesList = new ArrayList<Service>();
                    useCaseDetail.setServices(servicesList);
                }
                Service service = new Service();
                service.setServiceId(resultSet.getString("SERVICE_ID"));
                service.setLabel(resultSet.getString("LABEL"));
                servicesList.add(service);
            }
        }
        useCases.setUseCases(new ArrayList<UseCase>(useCaseDetailMap.values()));
        return useCases;
    }
}
