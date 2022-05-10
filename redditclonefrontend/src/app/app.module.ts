import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './auth/login/login.component';
import { SignUpComponent } from './auth/sign-up/sign-up.component';
import {HeaderModule} from "./header/header.module";
import {AuthModule} from "./auth/auth.module";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {TokenInterceptor} from "./token.interceptor";
import { HomeComponent } from './home/home/home.component';
import { PostComponent } from './post/post/post.component';
import { PostTitleComponent } from './post/post-title/post-title.component';
import { SideBarComponent } from './sidebar/side-bar/side-bar.component';
import { SubredditSideBarComponent } from './subreddit/subreddit-side-bar/subreddit-side-bar.component';
import { VoteBottonComponent } from './vote/vote-botton/vote-botton.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';





@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    SignUpComponent,
    HomeComponent,
    PostComponent,
    PostTitleComponent,
    SideBarComponent,
    SubredditSideBarComponent,
    VoteBottonComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HeaderModule,
    AuthModule,
    HttpClientModule,
    FontAwesomeModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
