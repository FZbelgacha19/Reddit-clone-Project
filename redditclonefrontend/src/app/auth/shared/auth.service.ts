import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SignupRequestPayload} from "../sign-up/signup-request.payload";
import {map, Observable, tap} from "rxjs";
import {LoginRequest} from "../login/login-request.payload";
import {LocalStorageService} from "ngx-webstorage";
import {LoginResponse} from "../login/login-response.payload";

@Injectable({
  providedIn: 'root'
})
export class AuthService {


  constructor(private http: HttpClient, private localStorage: LocalStorageService) { }

  signup(signupRequestPayload: SignupRequestPayload): Observable<any> {
    return this.http.post('http://localhost:8080/api/auth/signup', signupRequestPayload, {responseType: 'text'});
  }

  login(loginRequest: LoginRequest): Observable<boolean> {
    return this.http.post<LoginResponse>('http://localhost:8080/api/auth/login', loginRequest)
      .pipe(map(data => {
        this.localStorage.store('authToken', data.authToken);
        this.localStorage.store('username', data.username);
        this.localStorage.store('refreshToken', data.refreshToken);
        this.localStorage.store('expiresIn', data.expiresIn);
        return true;
      }));
  }

  refreshToken() {
    const refreshTokenPayload = {
      refreshToken: this.getRefreshToken(),
      username: this.getUserName()
    }
    return this.http.post<LoginResponse>('http://localhost:8080/api/auth/refresh/token',
      refreshTokenPayload)
      .pipe(tap(response => {
        this.localStorage.store('authenticationToken', response.authToken);
        this.localStorage.store('expiresAt', response.expiresIn);
      }));
  }

  getJwtToken() {
    return this.localStorage.retrieve('authToken');
  }

  getRefreshToken() {
    return this.localStorage.retrieve('refreshToken');
  }

  getUserName() {
    return this.localStorage.retrieve('username');
  }

  getExpirationTime() {
    return this.localStorage.retrieve('expiresIn');
  }
}
