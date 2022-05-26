import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SignupRequestPayload} from "./signup-request.payload";
import {AuthService} from "../shared/auth.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  signupRequestPayload!: SignupRequestPayload;
  signupForm!: FormGroup;
  isSuccessful = true;

  constructor(private authService: AuthService, private router: Router,private toastr: ToastrService) {
    this.signupRequestPayload = {
      username: '',
      email: '',
      password: ''
    };
  }

  ngOnInit() {
    this.signupForm = new FormGroup({
      username: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required)
    });
  }

  signup() {
    // get value from form and assign to signupRequestPayload object, and test if object in nut null
    if (this.signupForm.valid) {

      this.signupRequestPayload = {
        // @ts-ignore
        username: this.signupForm.get('username').value,
        // @ts-ignore
        email: this.signupForm.get('email').value,
        // @ts-ignore
        password: this.signupForm.get('password').value
      };
      this.authService.signup(this.signupRequestPayload).subscribe(() => {
        this.isSuccessful = true;
        this.router.navigateByUrl(
          '/auth/login'
        ).then(() => {
          // toaste success message and let show for 2 seconds
          this.toastr.success('Vous êtes inscrits avec succès, verifiez votre boite mail pour activer votre compte', 'Inscription réussie', {
            timeOut: 6000
          });

        })
      },() => {
        // log error message
        this.isSuccessful = false;
        this.toastr.error('Sign up failed!');
      });
      }
    }

}
