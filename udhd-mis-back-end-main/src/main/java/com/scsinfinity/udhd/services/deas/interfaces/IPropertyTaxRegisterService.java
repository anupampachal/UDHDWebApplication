package com.scsinfinity.udhd.services.deas.interfaces;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestBody;

import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.deas.dto.PropertyTaxRegisterDTO;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;

public interface IPropertyTaxRegisterService {

	PropertyTaxRegisterDTO uploadPropertyTaxStatement(PropertyTaxRegisterDTO propertyTaxData);

	PropertyTaxRegisterDTO getPropertyTaxRegisterDataById(Long id);

	GenericResponseObject<PropertyTaxRegisterDTO> getPropertyTaxRegisterDataByPage(
			@RequestBody Pagination<PropertyTaxRegisterDTO> data);

	Resource getFile(String fileId);

	List<ULBDTO> getULBsMappedToMe();
}
