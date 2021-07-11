package com.invoiceservice.mapper;

import com.invoiceservice.domain.InvoicePosition;
import com.invoiceservice.domain.InvoicePostionDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoicePositionMapper {


    public InvoicePostionDto mapToInvoicePostionDto(InvoicePosition invoicePosition){
        return new InvoicePostionDto(invoicePosition.getProductName(), invoicePosition.getCategory(), invoicePosition.getPrice());
    }

    public InvoicePosition mapToInvoicePostion(InvoicePostionDto invoicePostionDto){
        return new InvoicePosition(invoicePostionDto.getProductName(), invoicePostionDto.getCategory(), invoicePostionDto.getPrice());
    }

    public List<InvoicePosition> mapToInvoicePositionList(List<InvoicePostionDto> invoicePostionDtoList){
        return  invoicePostionDtoList.stream()
                .map( invoicePositionDTO -> mapToInvoicePostion(invoicePositionDTO) )
                .collect(Collectors.toList());
    }

    public List<InvoicePostionDto> mapToInvoicePositionListDto(List<InvoicePosition> invoicePositionList){
        return  invoicePositionList.stream()
                .map( invoicePosition -> mapToInvoicePostionDto(invoicePosition) )
                .collect(Collectors.toList());
    }
}
