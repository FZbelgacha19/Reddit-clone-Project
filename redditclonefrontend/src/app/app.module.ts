import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {TokenInterceptor} from "./token.interceptor";

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';


import {FormsModule, ReactiveFormsModule} from "@angular/forms";


import {ToastrModule} from "ngx-toastr";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";

import {NgxWebstorageModule} from "ngx-webstorage";
import {VoteBottonComponent} from "./vote/vote-botton/vote-botton.component";
import {LoginComponent} from "./auth/login/login.component";
import {SignUpComponent} from "./auth/sign-up/sign-up.component";
import {HomeComponent} from "./home/home/home.component";
import {PostComponent} from "./post/post/post.component";
import {PostTitleComponent} from "./post/post-title/post-title.component";
import {CreatePostComponent} from "./post/create-post/create-post.component";
import {SideBarComponent} from "./sidebar/side-bar/side-bar.component";
import {ListSubredditsComponent} from "./subreddit/list-subreddits/list-subreddits.component";
import {SubredditSideBarComponent} from "./subreddit/subreddit-side-bar/subreddit-side-bar.component";
import {CreateSebredditComponent} from "./subreddit/create-sebreddit/create-sebreddit.component";

import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {HeaderComponent} from "./header/header.component";
import {EditorModule} from "@tinymce/tinymce-angular";
import { ViewPostComponent } from './post/view-post/view-post.component';
import { CommentComponent } from './comment/comment/comment.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UserProfileComponent } from './auth/user-profile/user-profile.component';



export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

@NgModule({
  declarations: [
    AppComponent,

    //auth
    LoginComponent,
    SignUpComponent,
    UserProfileComponent,

    //home
    HomeComponent,

    //Post
    PostComponent,
    PostTitleComponent,
    CreatePostComponent,
    ViewPostComponent,

    //Sidebar
    SideBarComponent,

    //Subreddit
    ListSubredditsComponent,
    SubredditSideBarComponent,
    CreateSebredditComponent,

    //Vote
    VoteBottonComponent,

    //header
    HeaderComponent,

    //Comment
    CommentComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgxWebstorageModule.forRoot(),
    ToastrModule.forRoot(),
    TranslateModule.forRoot({
      defaultLanguage: 'en-US',
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    FontAwesomeModule,
    EditorModule,
    NgbModule,
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
