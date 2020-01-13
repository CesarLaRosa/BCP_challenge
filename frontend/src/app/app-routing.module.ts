import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { CurrencyConverterComponent } from './currency-converter/currency-converter.component';

const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "converter", component: CurrencyConverterComponent },
  { path: "", redirectTo: "login", pathMatch: "full" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
