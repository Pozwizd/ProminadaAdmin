CommercialPropertiesRequest for valid test
```
# Верхний уровень
id:1
realtorId:5
regionId:1
cityId:1
districtId:1
streetId:1
houseId:15
topozoneId:1
houseSection:A
flatNumber:12
ownerName:Иванов Иван Иванович
phoneNumber:+7-999-123-45-67
acquisitionDate:2023-01-15
ownershipDoc:OWNERSHIP_CERTIFICATE
comment:Важная информация об объекте
cadastralNumber:77:01:0001001:1
langPurpose:Для торговли и обслуживания
adminComment:Проверено администратором
dateOfCreating:2024-01-01

# Main-блок (enums приведены к существующим константам)
//commercialPropertiesMain.id:1
commercialPropertiesMain.publicationStatus:PUBLICATED
commercialPropertiesMain.objectCode:COM-001
commercialPropertiesMain.branchCode:101
commercialPropertiesMain.employeeCode:2001
commercialPropertiesMain.personalName:Петров П.П.
commercialPropertiesMain.landmark:Рядом с бизнес-центром
commercialPropertiesMain.housingStateId:1
commercialPropertiesMain.floor:1
commercialPropertiesMain.totalFloor:10
commercialPropertiesMain.roomCount:8
commercialPropertiesMain.bathroom:2
commercialPropertiesMain.price:12500000.0
commercialPropertiesMain.area:320.5
commercialPropertiesMain.livingArea:0.0
commercialPropertiesMain.livingSiteArea:150.0
commercialPropertiesMain.roomSizes:100/80/60/40
commercialPropertiesMain.ceilingHeight:3.2
commercialPropertiesMain.siteArea:15.0
commercialPropertiesMain.viewFromWindows:На парковку

# enums по проекту
commercialPropertiesMain.typeCommercialBuilding:OFFICE
commercialPropertiesMain.designatedUseOfLand:COMMERCIAL
commercialPropertiesMain.conditionInterior:FromOwners
commercialPropertiesMain.conditionBuilding:COMMERCIAL
commercialPropertiesMain.gas:NATURAL_GAS
commercialPropertiesMain.waterSupply:CENTRALIZED
commercialPropertiesMain.sewage:CENTRALIZED
commercialPropertiesMain.heating:CENTRALIZED
commercialPropertiesMain.airConditioner:ALL_ROOMS
commercialPropertiesMain.ventilation:Exhausted
commercialPropertiesMain.stairs:Concrete
commercialPropertiesMain.electrification:V380
commercialPropertiesMain.floorType:LAMINATE
commercialPropertiesMain.typeWindows:ALUMINUM
commercialPropertiesMain.carpentryCondition:NEW
commercialPropertiesMain.entranceDoor:ARMORED

# даты
commercialPropertiesMain.completionDate:2018-06-01
commercialPropertiesMain.commissioningDate:2019-01-01
commercialPropertiesMain.vnpDate:2020-01-01
commercialPropertiesMain.lastCommunication:2024-12-01

# булевы флаги
commercialPropertiesMain.isVnp:false
commercialPropertiesMain.landOwnership:true
commercialPropertiesMain.hasFurnishings:true
commercialPropertiesMain.hasCarPark:true
commercialPropertiesMain.hasHousingStock:false
commercialPropertiesMain.hasFacade:true
commercialPropertiesMain.hasRailwayTracks:false
commercialPropertiesMain.hasTrade:true
commercialPropertiesMain.hasExclusive:false
commercialPropertiesMain.urgent:false
commercialPropertiesMain.isFree:false
commercialPropertiesMain.isOpenObject:true
commercialPropertiesMain.fromMediator:false
commercialPropertiesMain.isAdvertising:true
commercialPropertiesMain.sourceInformation:INTERNET

# Файлы (в Postman отметить тип File)
//commercialPropertiesFiles[0].id:1
//commercialPropertiesFiles[0].name:contract.pdf
//commercialPropertiesFiles[0].path:
#commercialPropertiesFiles[1].id:2
#commercialPropertiesFiles[1].name:certificate.pdf
#commercialPropertiesFiles[1].path:

# Галерея (в Postman отметить тип File)
//commercialPropertiesGalleryImages[0].id:1
//commercialPropertiesGalleryImages[0].name:front.jpg
//commercialPropertiesGalleryImages[0].pathImage:
#commercialPropertiesGalleryImages[1].id:2
#commercialPropertiesGalleryImages[1].name:inside.jpg
#commercialPropertiesGalleryImages[1].pathImage: