import {Component, Input, OnInit} from '@angular/core';
import { faArrowUp, faArrowDown } from '@fortawesome/free-solid-svg-icons';
import {PostModel} from "../../post/post/post-Model";
import {VotePayload} from "../vote/vote-payload";
import {VoteService} from "../vote.service";
import {PostService} from "../../post/shared/post.service";
import {ToastrService} from "ngx-toastr";
import {AuthService} from "../../auth/shared/auth.service";
import {VoteType} from "../vote/vote-type";
import {throwError} from "rxjs";

@Component({
  selector: 'app-vote-botton',
  templateUrl: './vote-botton.component.html',
  styleUrls: ['./vote-botton.component.css']
})
export class VoteBottonComponent implements OnInit {

  @Input() post!: PostModel;
  votePayload!: VotePayload;
  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;
  upvoteColor!: string;
  downvoteColor!: string;

  constructor(private voteService: VoteService,
              private authService: AuthService,
              private postService: PostService, private toastr: ToastrService) {
/*
    this.votePayload = {
      voteType: undefined,
      postId: 0,
    }*/
  }

  ngOnInit(): void {
    this.updateVoteDetails();
  }

  upvotePost() {
    this.votePayload.voteType = VoteType.UPVOTE;
    this.vote();
    this.downvoteColor = '';
  }

  downvotePost() {
    this.votePayload.voteType = VoteType.DOWNVOTE;
    this.vote();
    this.upvoteColor = '';
  }

  private vote() {
    this.votePayload.postId = this.post.id;
    this.voteService.vote(this.votePayload).subscribe(() => {
      this.updateVoteDetails();
    }, (error : any ) => { // any est un type qui peut contenir n'importe quel type
      this.toastr.error(error.error.message);
      throwError(error);
    });
  }

  private updateVoteDetails() {
    this.postService.getPostById(this.post.id).subscribe(post => {
      this.post = post;
    });
  }
}
