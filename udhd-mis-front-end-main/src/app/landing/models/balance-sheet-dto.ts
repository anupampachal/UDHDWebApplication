export class BalanceSheetDTO {
  asCurrentOn!: string;
  asPreviousOn!: string;
  id!: number;
  latestAssets!: number;
  latestLiabilities!: number;
  particulars!: string;
  previousAssets!: number;
  previousLiabilities!: number;
}
