import {EventEmitter, Injectable, Output} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SignupRequestPayload} from "../sign-up/signup-request.payload";
import {map, Observable, tap, throwError} from "rxjs";
import {LoginRequest} from "../login/login-request.payload";
import {LocalStorageService} from "ngx-webstorage";
import {LoginResponse} from "../login/login-response.payload";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  /*
  @Output() est une propriété qui permet de créer un événement, c'est à dire un signalement
  le signalement travail comme suit:
  - on crée un événement
  - on lui donne un nom comme : loggedIn
  - on lui donne une valeur par défaut comme : false
  - on lui donne un type comme : EventEmitter<boolean>
  - on lui donne un scope comme : public
  - on lui donne un emetteur comme : this.loggedIn
  - on lui donne un écouteur comme : this.loggedIn.emit(true)
  emit : permet d'envoyer un signalement
   */
  @Output() loggedIn: EventEmitter<boolean> = new EventEmitter();
  @Output() username: EventEmitter<string> = new EventEmitter();

  refreshTokenPayload = {
    refreshToken: this.getRefreshToken(),
    username: this.getUserName()
  }

  constructor(private httpClient: HttpClient,
              private localStorage: LocalStorageService) {
  }

  signup(signupRequestPayload: SignupRequestPayload): Observable<any> {
    return this.httpClient.post('http://localhost:8080/api/auth/signup', signupRequestPayload, { responseType: 'text' });
  }

  login(loginRequestPayload: LoginRequest): Observable<boolean> {
    return this.httpClient.post<LoginResponse>('http://localhost:8080/api/auth/login',
      loginRequestPayload).pipe(map(data => {
      this.localStorage.store('authToken', data.authToken);
      this.localStorage.store('username', data.username);
      this.localStorage.store('refreshToken', data.refreshToken);
      this.localStorage.store('expiresIn', data.expiresIn);

      this.loggedIn.emit(true);
      this.username.emit(data.username);
      return true;
    }));
  }

  getJwtToken() {
    return this.localStorage.retrieve('authToken');
  }

  refreshToken() {
    return this.httpClient.post<LoginResponse>('http://localhost:8080/api/auth/refresh/token',
      this.refreshTokenPayload)
      .pipe(tap(response => {
        this.localStorage.clear('authToken');
        this.localStorage.clear('expiresIn');

        this.localStorage.store('authToken',
          response.authToken);
        this.localStorage.store('expiresIn', response.expiresIn);
      }));
  }

  logout() {
    this.httpClient.post('http://localhost:8080/api/auth/logout', this.refreshTokenPayload,
      { responseType: 'text' })
      .subscribe(data => {
        console.log(data);
      }, error => {
        throwError(error);
      })
    this.localStorage.clear('authToken');
    this.localStorage.clear('username');
    this.localStorage.clear('refreshToken');
    this.localStorage.clear('expiresIn');
  }

  getUserName() {
    return this.localStorage.retrieve('username');
  }
  getRefreshToken() {
    return this.localStorage.retrieve('refreshToken');
  }

  isLoggedIn(): boolean {
    return this.getJwtToken() != null;
  }
}
