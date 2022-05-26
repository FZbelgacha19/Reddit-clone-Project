import { Component, OnInit } from '@angular/core';
import {PostModel} from "../../post/post/post-Model";
import {CommentPayload} from "../../comment/comment/comment-payload";
import {ActivatedRoute} from "@angular/router";
import {PostService} from "../../post/shared/post.service";
import {CommentService} from "../../comment/comment.service";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  name!: string;
  posts_tab!: PostModel[];
  comments!: CommentPayload[];
  postLength!: number;
  commentLength!: number;

  constructor(private activatedRoute: ActivatedRoute, private postService: PostService,
              private commentService: CommentService) {
    this.name = this.activatedRoute.snapshot.params['name'];

    this.postService.getAllPostsByUser(this.name).subscribe(data => {
      this.posts_tab = data;
      this.postLength = data.length;
    });
    this.commentService.getAllCommentsByUser(this.name).subscribe(data => {
      this.comments = data;
      this.commentLength = data.length;
    });
  }

  ngOnInit(): void {
  }
}
