package com.pozwizd.prominadaadmin.models.property.residentialLand.response;

import com.pozwizd.prominadaadmin.entity.SourceInformation;
import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLandMain;
import com.pozwizd.prominadaadmin.entity.property.enums.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * ResidentialLandMainTableResponse for {@link ResidentialLandMain}
 */
@Data
public class ResidentialLandMainTableResponse implements Serializable {
    private String objectCode;        // Код объекта (из ResidentialLandMain)
    private Integer rooms;            // Комнат (из ResidentialLandMain.rooms)
    private Integer floors;           // Этажность (из ResidentialLandMain.floors)
    private Double landAreaAcres;     // Площадь участка (в сотых) (из ResidentialLandMain.landAreaAcres)
    private Double totalArea;         // Общая площадь дома (если есть на участке) (из ResidentialLandMain.totalArea)
    private Double price;             // Цена (из ResidentialLandMain.price)
    private PublicationStatus publicationStatus; // Статус публикации (из ResidentialLandMain.publicationStatus)
    private Boolean isAdvertising;    // Рекламируется ли (из ResidentialLandMain.isAdvertising)

}