import {Component, Input, OnInit} from '@angular/core';
import { faArrowUp, faArrowDown } from '@fortawesome/free-solid-svg-icons';
import {PostModel} from "../../post/post/post-Model";

@Component({
  selector: 'app-vote-botton',
  templateUrl: './vote-botton.component.html',
  styleUrls: ['./vote-botton.component.css']
})
export class VoteBottonComponent implements OnInit {
  @Input() post!: PostModel;
  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;
  upvoteColor: any;
  downvoteColor: any;
  constructor() { }

  ngOnInit(): void {
  }

  upvotePost() {

  }

  downvotePost() {

  }
}
