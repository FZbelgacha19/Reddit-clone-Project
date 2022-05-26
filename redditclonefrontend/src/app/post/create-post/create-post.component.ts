import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CreatePostPayload} from "./create-post-payload";
import {SubredditModel} from "../../subreddit/subreddit/subreddit-model";
import {Router} from "@angular/router";
import {PostService} from "../shared/post.service";
import {SubredditService} from "../../subreddit/shared/subreddit.service";
import {throwError} from "rxjs";

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  createPostForm!: FormGroup;
  postPayload: CreatePostPayload;
  subreddits!: Array<SubredditModel>;

  constructor(private router: Router, private postService: PostService,
              private subredditService: SubredditService) {
    this.postPayload = {
      postName: '',
      url: '',
      description: '',
      subredditName: ''
    }
  }

  ngOnInit() {
    this.createPostForm = new FormGroup({
      postName: new FormControl('', Validators.required),
      subredditName: new FormControl('', Validators.required),
      url: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
    });
    this.subredditService.getAllSubreddits().subscribe((data) => {
      this.subreddits = data;
    }, error => {
      throwError(error);
    });
  }

  createPost() {
    this.postPayload = {
      postName: this.createPostForm.value.postName,
      url: this.createPostForm.value.url,
      description: this.createPostForm.value.description,
      subredditName: this.createPostForm.value.subredditName
    };

    this.postService.CreatePost(this.postPayload).subscribe((data) => {
      this.router.navigateByUrl('/').then(r => {
        window.location.reload();
      }); // then will execute after the navigation is done
    }, error => {
      throwError(error);
    })
  }

  discardPost() {
    this.router.navigateByUrl('/');
  }

}
