export interface AddressDataModel {
  id: number
  zipCode: string;
  city: string;
  street: string;
  houseNumber: string;
  note: string;
  addressOwner: number;
  isDeleted: boolean;
}
