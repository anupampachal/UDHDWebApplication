import { CurrencyPipe } from '@angular/common';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formatAmount'
})
export class FormatAmountPipe implements PipeTransform {
  constructor(private currencyPipe: CurrencyPipe) { }
    transform(amount: number, ...args: unknown[]): string {
     
      if(amount==0 || amount == null)
      return 0 + '';

     let multiplicand=1;
     if(amount<0) {
       multiplicand=-1;
     }
     const amountc= this.currencyPipe.transform(amount*multiplicand,'INR','₹','3.2-2')
    // const amountc = (Math.round(amount*multiplicand * 100) / 100).toFixed(2);
    //  return multiplicand>0? '₹'+amountc+' Cr.': '₹' + amountc+' Dr.';
     return multiplicand>0? amountc+' Cr.': amountc+' Dr.';

    }

  }
