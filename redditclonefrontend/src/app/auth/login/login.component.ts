import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginRequest} from "./login-request.payload";
import {AuthService} from "../shared/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup
  loginError: boolean = false
  loginRequest!: LoginRequest

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }

  login() {
    this.loginRequest = {
      username: this.loginForm.value.username,
      password: this.loginForm.value.password
    };
    this.authService.login(this.loginRequest).subscribe(data => {
      this.loginError = !data;
      console.log("login est bien passé");
    });


  }

}
