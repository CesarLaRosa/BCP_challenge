import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { AuthService } from './auth-service.service';
import { Router } from '@angular/router';
import { AuthenticationService } from './auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private authenticationService: AuthenticationService,
    private router: Router ) {}

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: [''],
      password: ['']
    });
  }

  get form() { return this.loginForm.controls; }

  loginV2() {
    this.authService.login(
      {
        username: this.form.username.value,
        password: this.form.password.value
      }
    )
    .subscribe(success => {
      if (success) {
        this.router.navigate(['/converter']);
      }
    });
  }

  login() {
    this.authenticationService.login(
       'password',
       this.form.username.value,
       this.form.password.value
    )
    .subscribe(success => {
      if (success) {
        this.router.navigate(['/converter']);
      }
    });
  }
}
