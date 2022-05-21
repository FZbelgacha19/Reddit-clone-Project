import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {PostModel} from "../post/post-Model";
import {CreatePostPayload} from "../create-post/create-post-payload";

@Injectable({
  providedIn: 'root'
})
export class PostService {


  constructor(private http: HttpClient) { }

  getAllPosts(): Observable<Array<PostModel>> {
    return this.http.get<Array<PostModel>>('http://localhost:8080/api/posts/');
  }

  CreatePost(post: CreatePostPayload): Observable<PostModel> {
    return this.http.post<PostModel>('http://localhost:8080/api/posts/', post);
  }

  getPostById(id: number): Observable<PostModel> {
    return this.http.get<PostModel>('http://localhost:8080/api/posts/' + id);
  }

  getAllPostsByUser(username: string): Observable<Array<PostModel>> {
    return this.http.get<Array<PostModel>>('http://localhost:8080/api/posts/by-user/' + username);
  }
}
