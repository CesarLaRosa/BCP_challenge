import { Component, OnInit } from '@angular/core';
import { CurrencyService } from './currency-service.service';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-currency-converter',
  templateUrl: './currency-converter.component.html',
  styleUrls: ['./currency-converter.component.css']
})
export class CurrencyConverterComponent implements OnInit {

  currencyForm: FormGroup;

  constructor( private formBuilder: FormBuilder,
               private currencyService: CurrencyService) { }

  ngOnInit() {
    this.currencyForm = this.formBuilder.group({
      amount: [''],
      sourceCurrency: [''],
      destinationCurrency: ['']
    });
  }

  get form() { return this.currencyForm.controls; }

  converter() {
    this.currencyService.converter(
      {
        amount: this.form.amount.value,
        sourceCurrency: this.form.sourceCurrency.value,
        destinationCurrency: this.form.destinationCurrency.value
      }
    )
    .subscribe(success => {
      if (success) {

        const response = JSON.parse(JSON.stringify(success));
        alert("El valor es: " + response.amountTypeChange);
      }
    });
  }

}
