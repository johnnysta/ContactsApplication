import {ContactDetailsDataModel} from "./contact-details-data.model";
import {PhoneDataModel} from "./phone-data.model";
import {AddressDataModel} from "./address-data.model";

export interface ContactDataModel {

  contactBasicData: ContactDetailsDataModel;
  phoneList: PhoneDataModel[];
  addressList: AddressDataModel[];

}
