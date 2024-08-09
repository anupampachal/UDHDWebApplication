import { InputDataDTO } from "src/app/deas/models/input-data-dto";

export class PropertyTaxRegisterDTO {
  id?:number;
  fileId?: string;
  file?: File;
  inputData!: InputDataDTO;
  quarter!: string;
  ulbId!: string;
}
