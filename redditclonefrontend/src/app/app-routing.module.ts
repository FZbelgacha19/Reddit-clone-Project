import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SignUpComponent} from "./auth/sign-up/sign-up.component";
import {LoginComponent} from "./auth/login/login.component";
import {HomeComponent} from "./home/home/home.component";
import {CreatePostComponent} from "./post/create-post/create-post.component";
import {CreateSebredditComponent} from "./subreddit/create-sebreddit/create-sebreddit.component";
import {ListSubredditsComponent} from "./subreddit/list-subreddits/list-subreddits.component";
import {ViewPostComponent} from "./post/view-post/view-post.component";
import {UserProfileComponent} from "./auth/user-profile/user-profile.component";

const routes: Routes = [
/*  {path:"", loadChildren: () => import('./home/home.module').then(m => m.HomeModule)},
  {path:"auth", loadChildren:() => import('./auth/auth.module').then(m => m.AuthModule)},
  {path:"subreddit", loadChildren:() => import('./subreddit/subreddit.module').then(m => m.SubredditModule)},
  {path: "post", loadChildren:() => import('./post/post.module').then(m => m.PostModule)}*/

  // auth path
  {path: 'auth/sign-up', component: SignUpComponent},
  {path:'auth/login', component: LoginComponent},
  {path:'auth/user-profile', component: UserProfileComponent},


  // home path
  {path: '', component: HomeComponent},


  // post path
  {path: 'post/create', component: CreatePostComponent},
  { path: 'post/view-post/:id', component: ViewPostComponent },

  // subreddit path
  {path: 'subreddit/create', component: CreateSebredditComponent },
  {path: 'subreddit', component: ListSubredditsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
